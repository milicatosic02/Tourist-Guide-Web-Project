package rs.raf.backend.repositories.aktivnost;




import rs.raf.backend.entiteti.Aktivnost;

import java.util.List;

public interface AktivnostRepository {

    public Aktivnost pronadjiAktivnost(Integer aktivnost_id);

    public List<Aktivnost> pronadjiAktivnostiZaClanak(Integer clanak_id);

    public void dodajAktivnost(Aktivnost aktivnost);

    public List<Aktivnost> sveAktivnosti();



}
