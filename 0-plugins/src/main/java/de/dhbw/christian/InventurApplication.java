package de.dhbw.christian;

import de.dhbw.christian.javalin.extension.EndpointMapping;
import de.dhbw.christian.javalin.extension.JavalinController;
import io.javalin.Javalin;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;


public class InventurApplication {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Javalin app = Javalin.create();

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath()).setScanners(Scanners.TypesAnnotated));
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(JavalinController.class);

        for (Class<?> type : types) {
            JavalinController javalinControllerAnnotation = type.getAnnotation(JavalinController.class);
            String endpoint = javalinControllerAnnotation.endpoint();

            Object javalinController = type.getConstructor().newInstance();

            Arrays.stream(type.getDeclaredMethods()).filter(method -> method.isAnnotationPresent(EndpointMapping.class)).forEach(method -> {
                EndpointMapping endpointMapping = method.getAnnotation(EndpointMapping.class);
                app.addHandler(endpointMapping.handlerType(), endpoint + endpointMapping.endpoint().replaceFirst("^/", ""), ctx -> method.invoke(javalinController, ctx));
            });

        }
        app.start(8080);
    }
}