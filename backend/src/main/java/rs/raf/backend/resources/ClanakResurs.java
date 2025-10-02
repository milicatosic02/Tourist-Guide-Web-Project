package rs.raf.backend.resources;


import rs.raf.backend.entiteti.Clanak;
import rs.raf.backend.services.ClanakServis;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clanci")
public class ClanakResurs {
    @Inject
    private ClanakServis clanakService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(){
        return Response.ok(this.clanakService.sviClanci()).build();
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Clanak create(@PathParam("id") Integer destinacija_id, @Valid Clanak clanak){
        return this.clanakService.dodajClanak(destinacija_id, clanak);
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Clanak find(@PathParam("id") Integer id){
        return this.clanakService.pronadjiClanak(id);
    }


    @GET
    @Path("/destFilter/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrirajPoDestinaciji(@PathParam("id") Integer destinacija_id){
        return Response.ok(this.clanakService.filtrirajPoDestinaciji(destinacija_id)).build();
    }


    @GET
    @Path("/aktFilter/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrirajPoAktivnostima(@PathParam("id") Integer aktivnost_id){
        return Response.ok(this.clanakService.filtrirajPoAktivnostima(aktivnost_id)).build();
    }


    @GET
    @Path("/najcitanije")
    @Produces(MediaType.APPLICATION_JSON)
    public Response najcitanijiClanci(){
        return Response.ok(this.clanakService.najcitanijiClanci()).build();
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id){
        this.clanakService.obrisiClanak(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Clanak izmeniClanak(@PathParam("id")Integer id, Clanak clanak){
        return clanakService.izmeniClanak(id, clanak);
    }
}
