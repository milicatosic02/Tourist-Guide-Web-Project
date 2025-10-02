package rs.raf.backend.filters;

import rs.raf.backend.resources.*;
import rs.raf.backend.services.ClanakServis;
import rs.raf.backend.services.DestinacijaServis;
import rs.raf.backend.services.KorisnikServis;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    KorisnikServis korisnikService;

    @Inject
    ClanakServis clanakService;

    @Inject
    DestinacijaServis destinacijaService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {


        if (!this.isAuthRequired(requestContext)) {
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            List<Object> matchedResources = requestContext.getUriInfo().getMatchedResources();

            for (Object matchedResource : matchedResources) {

                if (matchedResource instanceof KorisnikResurs) {
                    if (!this.korisnikService.isAuthotized(token)) {
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    }
                } else if (matchedResource instanceof ClanakResurs) {
                    if (!this.clanakService.isAuthorized(token)) {
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    }
                }
                else if (matchedResource instanceof DestinacijaResurs) {
                    if (!this.destinacijaService.isAuthorized(token)) {
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    }
                }
                else if (matchedResource instanceof AktivnostResurs) {
                    if (!this.destinacijaService.isAuthorized(token)) {
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    }
                }
                else if (matchedResource instanceof KorisnikResurs) {
                    if (!this.destinacijaService.isAuthorized(token)) {
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    }
                }
            }
        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login")) {
            return false;
        }

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {

            if (matchedResource instanceof KorisnikResurs) {
                return true;
            }
            else if((matchedResource instanceof ClanakResurs)){
                if(req.getMethod().equals("PUT") || req.getMethod().equals("POST") || req.getMethod().equals("DELETE")){
                    return true;
                }
            }
            else if((matchedResource instanceof DestinacijaResurs)){
                if(req.getMethod().equals("PUT") || req.getMethod().equals("POST") || req.getMethod().equals("DELETE")){
                    return true;
                }
            }
            else if((matchedResource instanceof AktivnostResurs)){
                if(req.getMethod().equals("PUT") || req.getMethod().equals("POST") || req.getMethod().equals("DELETE")){
                    return true;
                }
            }
            else if((matchedResource instanceof KomentarResurs)){
                if(req.getMethod().equals("DELETE")){
                    return true;
                }
            }
        }

        return false;
    }


}
