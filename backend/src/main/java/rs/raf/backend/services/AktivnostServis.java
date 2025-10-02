package rs.raf.backend.services;




import rs.raf.backend.entiteti.Aktivnost;
import rs.raf.backend.repositories.aktivnost.AktivnostRepository;

import javax.inject.Inject;
import java.util.List;

public class AktivnostServis extends IsAuthorized{

    public AktivnostServis() {
        System.out.println(this);
    }
    @Inject
    AktivnostRepository aktivnostRepository;

    public void dodajAktivnost(Aktivnost aktivnost){
         aktivnostRepository.dodajAktivnost(aktivnost);
    }

    public List<Aktivnost> sveAktivnosti(){
        return aktivnostRepository.sveAktivnosti();
    }

    public Aktivnost pronadjiAktivnost(Integer aktivnost_id) {
        return aktivnostRepository.pronadjiAktivnost(aktivnost_id);
    }
}
