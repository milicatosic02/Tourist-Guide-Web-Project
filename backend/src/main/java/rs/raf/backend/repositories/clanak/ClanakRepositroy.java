package rs.raf.backend.repositories.clanak;



import rs.raf.backend.entiteti.Clanak;

import java.util.List;

public interface ClanakRepositroy {
    public Clanak dodajClanak(Integer ddestinacija_id, Clanak clanak);
    public List<Clanak> sviClanci();
    public Clanak pronadjiClanak(Integer id);
    public void obrisiClanak(Integer id);

    public void povecajBrojPoseta(Integer clanakId);

    public List<Clanak> filtrirajPoDestinaciji(Integer destinacija_id);

    public List<Clanak> najcitanijiClanci();

    public List<Clanak> filtrirajPoAktivnostima(Integer aktivnost_id);

    public Clanak izmeniClanak(Integer id, Clanak izmenjeniClanak);
}
