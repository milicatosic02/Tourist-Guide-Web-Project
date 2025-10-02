package rs.raf.backend.services;


import rs.raf.backend.entiteti.Komentar;
import rs.raf.backend.repositories.komentar.KomentarRepository;

import javax.inject.Inject;

public class KomentarServis extends IsAuthorized{
    public KomentarServis(){
        System.out.println(this);
    }

    @Inject
    private KomentarRepository komentarRepository;
    public void dodajKomentar(Integer clanak_id, Komentar komentar){
       komentarRepository.dodajKomentar(clanak_id,komentar);
    }
    public void obrisiKomentar(Integer clanak_id, Integer komentar_id){
        komentarRepository.obrisiKomentar(clanak_id,komentar_id);
    }
}
