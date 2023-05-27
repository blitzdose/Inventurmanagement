package de.dhbw.christian.javalin;

import com.google.gson.Gson;
import de.dhbw.christian.application.ProductApplicationService;
import de.dhbw.christian.application.SectionApplicationService;
import de.dhbw.christian.domain.section.SectionDomainService;
import de.dhbw.christian.javalin.annotations.EndpointMapping;
import de.dhbw.christian.javalin.annotations.JavalinController;
import de.dhbw.christian.plugins.persistence.hibernate.Product.HibernateProductRepository;
import de.dhbw.christian.plugins.persistence.hibernate.Section.HibernateSectionRepository;
import io.javalin.Javalin;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JavalinWebServer {

    int port;
    Javalin javalin;
    static Gson gson = new Gson();

    public JavalinWebServer(int port) {
        this.port = port;

        Set<Class<?>> types = getJavalinControllerClasses();
        EntityManager entityManager = initializeEntityManager();

        javalin = Javalin.create();
        configureJavalin(javalin, types, entityManager);
    }

    private static void configureJavalin(Javalin javalin, Set<Class<?>> types, EntityManager entityManager) {
        for (Class<?> type : types) {
            final String endpoint = getClassEndpoint(type);

            Object javalinController = initializeJavalinController(entityManager, type);

            Arrays.stream(type.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(EndpointMapping.class)).forEach(method -> {
                EndpointMapping endpointMapping = method.getAnnotation(EndpointMapping.class);
                javalin.addHandler(endpointMapping.handlerType(), endpoint + endpointMapping.endpoint().replaceFirst("^/", ""),
                        ctx -> handleEndpoints(javalinController, method, ctx)
                );
            });

        }
    }

    private static void handleEndpoints(Object javalinController, Method method, Context ctx) throws IllegalAccessException {
        try {
            Object object = method.invoke(javalinController, ctx);
            String result = gson.toJson(object, method.getReturnType());

            ctx.header("Content-Type", "application/json");
            ctx.result(result);
        } catch (InvocationTargetException e) {
            ctx.status(400);
            ctx.result("Bad request");
        }
    }

    @NotNull
    private static Object initializeJavalinController(EntityManager entityManager, Class<?> type) {
        try {
            return dependencyInjection(type, entityManager);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing");
        }
    }

    @NotNull
    private static String getClassEndpoint(Class<?> type) {
        JavalinController javalinControllerAnnotation = type.getAnnotation(JavalinController.class);
        if (!javalinControllerAnnotation.endpoint().endsWith("/")) {
            return javalinControllerAnnotation.endpoint() + "/";
        } else {
            return javalinControllerAnnotation.endpoint();
        }
    }

    private static Set<Class<?>> getJavalinControllerClasses() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath()).setScanners(Scanners.TypesAnnotated));
        return reflections.getTypesAnnotatedWith(JavalinController.class);
    }

    private static EntityManager initializeEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("de.dhbw.christian");
        return emf.createEntityManager();
    }

    private static Object dependencyInjection(Class<?> type, EntityManager entityManager) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Object> parameterObjects = new ArrayList<>();
        Arrays.stream(type.getConstructors()[0].getParameterTypes()).forEach(aClass -> {
            if (aClass == SectionApplicationService.class) {
                parameterObjects.add(new SectionApplicationService(new HibernateSectionRepository(entityManager), new SectionDomainService()));
            } else if (aClass == ProductApplicationService.class) {
                parameterObjects.add(new ProductApplicationService(new HibernateProductRepository(entityManager), new SectionApplicationService(new HibernateSectionRepository(entityManager), new SectionDomainService())));
            }
        });
        return type.getConstructors()[0].newInstance(parameterObjects.toArray());
    }

    public void start() {
        javalin.start(port);
    }
}
