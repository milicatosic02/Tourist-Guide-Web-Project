package rs.raf.backend.repositories.komentar;



import rs.raf.backend.entiteti.Komentar;

import java.util.List;

public interface KomentarRepository {
    public List<Komentar> pronadjiKomentareZaClanak(Integer clanak_id);
    public void dodajKomentar(Integer clanak_id,Komentar komentar);
    public void obrisiKomentar(Integer clanakId, Integer komentarId);
}
