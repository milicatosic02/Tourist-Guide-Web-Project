package rs.raf.backend.services;


import rs.raf.backend.entiteti.Clanak;
import rs.raf.backend.repositories.clanak.ClanakRepositroy;

import javax.inject.Inject;
import java.util.List;

public class ClanakServis extends IsAuthorized {
    public ClanakServis() {
        System.out.println(this);
    }

    @Inject
    private ClanakRepositroy clanakRepository;

    public Clanak dodajClanak(Integer destinacija_id, Clanak clanak){
        return clanakRepository.dodajClanak(destinacija_id, clanak);
    }

    public void obrisiClanak(Integer id){
        this.clanakRepository.obrisiClanak(id);
    }

    public Clanak pronadjiClanak(Integer id){
        return clanakRepository.pronadjiClanak(id);
    }

    public List<Clanak> sviClanci(){
        return clanakRepository.sviClanci();
    }

    public List<Clanak> filtrirajPoDestinaciji(Integer destinacija_id){
        return clanakRepository.filtrirajPoDestinaciji(destinacija_id);
    }

    public List<Clanak> najcitanijiClanci(){
        return clanakRepository.najcitanijiClanci();
    }

    public List<Clanak> filtrirajPoAktivnostima(Integer aktivnost_id){
        return clanakRepository.filtrirajPoAktivnostima(aktivnost_id);
    }

    public Clanak izmeniClanak(Integer id, Clanak clanak){
        return clanakRepository.izmeniClanak(id, clanak);
    }

}
