package rs.raf.backend.resources;



import rs.raf.backend.entiteti.Korisnik;
import rs.raf.backend.requests.LoginRequest;
import rs.raf.backend.services.KorisnikServis;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/korisnici")
public class KorisnikResurs {

    @Inject
    private KorisnikServis korisnikService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.korisnikService.login(loginRequest.getEmail(), loginRequest.getSifra());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(){
        return Response.ok(this.korisnikService.sviKorisnici()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik pronadjiKorisnika(@PathParam("id") Integer id){
        return this.korisnikService.pronadjiKorisnika(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response dodajKorisnika(@Valid Korisnik korisnik){
        return this.korisnikService.dodajKorinsika(korisnik);
    }

    @PUT
    @Path("suspend/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void promeniStatusKorisniku(@PathParam("id") Integer korisnik_id){
        korisnikService.promeniStatusKorisniku(korisnik_id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik izmeniKorisnika(@PathParam("id")Integer id, Korisnik korisnik){
        return korisnikService.izmeniKorisnika(id, korisnik);
    }
}
