package de.dhbw.christian;

import com.google.gson.Gson;
import de.dhbw.christian.application.ProductApplicationService;
import de.dhbw.christian.application.SectionApplicationService;
import de.dhbw.christian.domain.section.SectionDomainService;
import de.dhbw.christian.javalin.extension.EndpointMapping;
import de.dhbw.christian.javalin.extension.JavalinController;
import de.dhbw.christian.plugins.persistence.hibernate.Product.HibernateProductRepository;
import de.dhbw.christian.plugins.persistence.hibernate.Section.HibernateSectionRepository;
import io.javalin.Javalin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class InventurApplication {
    public static void main(String[] args) {
        Gson gson = new Gson();

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath()).setScanners(Scanners.TypesAnnotated));
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(JavalinController.class);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("de.dhbw.christian");
        EntityManager entityManager = emf.createEntityManager();

        Javalin app = Javalin.create();
        for (Class<?> type : types) {
            JavalinController javalinControllerAnnotation = type.getAnnotation(JavalinController.class);
            final String endpoint;
            if (!javalinControllerAnnotation.endpoint().endsWith("/")) {
                endpoint = javalinControllerAnnotation.endpoint() + "/";
            } else {
                endpoint = javalinControllerAnnotation.endpoint();
            }

            Object javalinController;

            try {
                javalinController = dependencyInjection(type, entityManager);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Error initializing");
            }

            Arrays.stream(type.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(EndpointMapping.class)).forEach(method -> {
                EndpointMapping endpointMapping = method.getAnnotation(EndpointMapping.class);
                app.addHandler(endpointMapping.handlerType(), endpoint + endpointMapping.endpoint().replaceFirst("^/", ""),
                        ctx -> {
                            try {
                                Object object = method.invoke(javalinController, ctx);
                                String result = gson.toJson(object, method.getReturnType());

                                ctx.header("Content-Type", "application/json");
                                ctx.result(result);
                            } catch (InvocationTargetException e) {
                                ctx.status(400);
                                ctx.result("Bad request");
                                e.printStackTrace();
                            }
                        }
                );
            });

        }
        app.start(args.length >= 1 ? Integer.parseInt(args[0]) : 8080);
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
}