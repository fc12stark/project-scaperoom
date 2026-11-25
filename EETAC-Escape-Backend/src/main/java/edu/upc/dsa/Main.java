package edu.upc.dsa;

import edu.upc.dsa.config.ServletConfigBinder;
import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:8080/dsaApp/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("edu.upc.dsa.services");

        rc.register(JacksonFeature.class);
        rc.register(new ServletConfigBinder());
        rc.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        rc.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/dsaApp");
        beanConfig.setDescription("API REST d'EETAC Escape");
        beanConfig.setResourcePackage("edu.upc.dsa.services");
        beanConfig.setTitle("EETAC Escape API");
        beanConfig.setVersion("1.0.0");
        beanConfig.setScan(true);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

        StaticHttpHandler staticHttpHandler = new StaticHttpHandler("./public/");
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");

        System.out.println("Servidor arrencat a " + BASE_URI);
        System.out.println("Swagger JSON a http://localhost:8080/dsaApp/swagger.json");
        System.out.println("Prova browser a http://localhost:8080/index.html");
        System.out.println("Swagger index.html a http://localhost:8080/swagger/index.html");
        System.out.println("Prem Enter per aturar el servidor");

        System.in.read();
        server.stop();
    }
}
