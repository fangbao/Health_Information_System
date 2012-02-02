package edu.stevens.cs.cs548.clinic.rest.server;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;

public class Main {


	private Main() {
		BASE_URI = getBaseURI();	
    }
    
    private URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9090).build();
    }

    public URI BASE_URI;

    protected HttpServer startServer() throws IOException {
        final Map<String, String> initParams = new HashMap<String, String>();

        initParams.put("com.sun.jersey.config.property.packages", 
                "edu.stevens.cs.cs548.clinic.rest.resource");

        System.out.println("Starting grizzly...");
        return GrizzlyWebContainerFactory.create(BASE_URI, initParams);
    }
    
    
    public static void main(String[] args) throws IOException {
    	
    	Main main = new Main();
        HttpServer httpServer = main.startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nTry out %shello\nHit enter to stop it...",
                main.BASE_URI, main.BASE_URI));
        System.in.read();
        httpServer.stop();
    }  
}
