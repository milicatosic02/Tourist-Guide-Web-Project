package rs.raf.backend.repositories.korisnik;




import rs.raf.backend.entiteti.Korisnik;

import javax.ws.rs.core.Response;
import java.util.List;

public interface KorisnikRepository {

    public Korisnik pronadjiKorisnikaPoEmailu(String email);
    public Korisnik pronadjiKorisnikaPoId(Integer korisnik_id);
    public List<Korisnik> sviKorisnici();
    public Response dodajKorisnika(Korisnik korisnik);
    public void obrisiKorisnika(Integer korisnik_id);
    public void promeniStatusKorisniku(Integer korisnik_id);
    public Korisnik izmeniKorisnika(Integer id, Korisnik izmenjenKorisnik);

}
