package rs.raf.backend.repositories.destinacija;



import rs.raf.backend.entiteti.Destinacija;

import javax.ws.rs.core.Response;
import java.util.List;

public interface DestinacijaRepository {
    public Response dodajDestinaciju(Destinacija destinacija);

    public Response obrisiDestinaciju(Integer destinacija_id);

    public List<Destinacija> sveDestinacije();

    public Destinacija izmeniDestinaciju(Integer id, Destinacija izmenjenaDestinacija);

    public Destinacija pronadjiDestinaciju(Integer destinacija_id);
}
