package com.github.leifoolsen.simplejaxrs2.rest.application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.ApplicationPath;

@WebServlet(loadOnStartup = 1)
@ApplicationPath("/api/*")

/*
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet();
        classes.add(BookResource.class);

        return classes;
    }
}
*/

public class ApplicationConfig extends ResourceConfig {
    public static final String APPLICATION_PATH = "api";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ApplicationConfig() {
        // Jersey uses java.util.logging - bridge to slf4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Scans during deployment for JAX-RS components in packages
        packages("com.github.leifoolsen.simplejaxrs2.rest");


        // Enables sending validation errors in response entity to the client.
        // See: https://jersey.java.net/documentation/latest/user-guide.html#bv.ValidationError
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Enable LoggingFilter & output entity.
        //registerInstances(new LoggingFilter(java.util.logging.Logger.getLogger(this.getClass().getName()), true));

        logger.debug("'{}' initialized", getClass().getName());
    }
}
