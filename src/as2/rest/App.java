package as2.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import as2.rest.resources.PersonCollectionResource;

@ApplicationPath("as2")
public class App {
	private static final URI BASE_URI = URI.create("http://localhost:5900/as2/");	
    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException
    {
    	System.out.println("Starting as2 standalone HTTP server...");
    	ResourceConfig config = new ResourceConfig();
    	config.packages("as2.rest");
        JdkHttpServerFactory.createHttpServer(BASE_URI, config);
        System.out.println("Server started on " + BASE_URI + "\n[kill the process to exit]");
    }
}
