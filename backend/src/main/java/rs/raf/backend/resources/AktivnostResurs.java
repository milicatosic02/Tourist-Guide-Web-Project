package rs.raf.backend.resources;

import rs.raf.backend.entiteti.Aktivnost;
import rs.raf.backend.services.AktivnostServis;


import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/aktivnosti")
public class AktivnostResurs {

    @Inject
    AktivnostServis aktivnostService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(){
        return Response.ok(this.aktivnostService.sveAktivnosti()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void dodajAktivnost(@Valid Aktivnost aktivnost){
        this.aktivnostService.dodajAktivnost(aktivnost);
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Aktivnost pronadjiAktivnost(@PathParam("id")Integer aktivnost_id){
        return aktivnostService.pronadjiAktivnost(aktivnost_id);
    }

}
