package rs.raf.backend.resources;



import rs.raf.backend.entiteti.Komentar;
import rs.raf.backend.services.KomentarServis;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/komentari")
public class KomentarResurs {
    @Inject
    private KomentarServis komentarService;

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void addComment(@PathParam("id") Integer clanak_id, @Valid Komentar komentar){
        this.komentarService.dodajKomentar(clanak_id, komentar);
    }

    @DELETE
    @Path("/clanak/{clanakId}/komentar/{komentarId}")
    public void obrisiKomentar(@PathParam("clanakId") Integer clanakId, @PathParam("komentarId") Integer komentarId) {
        this.komentarService.obrisiKomentar(clanakId, komentarId);
    }
}
