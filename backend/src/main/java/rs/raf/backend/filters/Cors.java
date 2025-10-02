package rs.raf.backend.filters;



import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class Cors implements ContainerResponseFilter {
    ///COntainerResponseFIlter moze presresti i modifikovati odgovore pre slanja klijentu
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        System.out.println("CORS filter activated for request: " + requestContext.getUriInfo().getRequestUri());

        responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:8080");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        System.out.println("Response headers: " + responseContext.getHeaders());

    }
}