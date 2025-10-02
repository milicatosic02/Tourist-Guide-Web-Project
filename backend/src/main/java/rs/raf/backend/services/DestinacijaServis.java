package rs.raf.backend.services;



import rs.raf.backend.entiteti.Destinacija;
import rs.raf.backend.repositories.destinacija.DestinacijaRepository;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class DestinacijaServis extends IsAuthorized{
    public DestinacijaServis(){
        System.out.println(this);
    }

    @Inject
    DestinacijaRepository destinacijaRepository;
    public Response obrisiDestinaciju(Integer destinacija_id){
        return destinacijaRepository.obrisiDestinaciju(destinacija_id);
    }

    public Response dodajDestinaciju(Destinacija destinacija){
        return this.destinacijaRepository.dodajDestinaciju(destinacija);
    }
    public List<Destinacija> sveDestinacije (){
        return destinacijaRepository.sveDestinacije();
    }
    public Destinacija izmeniDestinaciju(Integer id, Destinacija destinacija){
        return destinacijaRepository.izmeniDestinaciju(id,destinacija);
    }
    public Destinacija pronadjiDestinaciju(Integer destinacija_id){
        return destinacijaRepository.pronadjiDestinaciju(destinacija_id);
    }
}
