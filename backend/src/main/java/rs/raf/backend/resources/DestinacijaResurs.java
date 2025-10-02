package rs.raf.backend.resources;



import rs.raf.backend.entiteti.Destinacija;
import rs.raf.backend.services.DestinacijaServis;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/destinacije")
public class DestinacijaResurs {
    @Inject
    DestinacijaServis destinacijaService;

    @DELETE
    @Path("/{id}")
    public Response obrisiDestinaciju(@PathParam("id") Integer id) {
        return this.destinacijaService.obrisiDestinaciju(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response dodajDestinaciju(@Valid Destinacija destinacija){
        return this.destinacijaService.dodajDestinaciju(destinacija);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(){
        return Response.ok(this.destinacijaService.sveDestinacije()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Destinacija pronadjiDestinaciju(@PathParam("id")Integer destinacija_id){
        return destinacijaService.pronadjiDestinaciju(destinacija_id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Destinacija izmeniDestinaciju(@PathParam("id")Integer id, Destinacija destinacija){
        return destinacijaService.izmeniDestinaciju(id, destinacija);
    }
}
