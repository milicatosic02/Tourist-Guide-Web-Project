package rs.raf.backend.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import rs.raf.backend.entiteti.Korisnik;
import rs.raf.backend.repositories.korisnik.KorisnikRepository;
import org.apache.commons.codec.digest.DigestUtils;


import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

public class KorisnikServis {
    //umetanje bez samog instanciranja
    @Inject
    KorisnikRepository korisnikRepository;

    public String login(String email, String password){
        String hesiranaLozinka  = DigestUtils.sha256Hex(password);
        Korisnik korisnik = this.korisnikRepository.pronadjiKorisnikaPoEmailu(email);

        if(korisnik == null || !korisnik.getHesiranaLozinka().equals(hesiranaLozinka)){
            return null;
        }

        Date issuedAt = new Date();

        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withSubject(email)
                .withClaim("tip_korisnika",korisnik.getTip_korisnika())
                .withClaim("status",korisnik.isStatus())
                .sign(algorithm);
    }

    public boolean isAuthotized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();
        String tip_korisnika = jwt.getClaim("tip_korisnika").asString();

        Korisnik korisnik = this.korisnikRepository.pronadjiKorisnikaPoEmailu(email);
        if(korisnik == null || !korisnik.getTip_korisnika().equals("admin")||
        korisnik.getTip_korisnika().equals("admin") && !tip_korisnika.equals("admin")){
            return false;
        }
        return true;
    }

    public List<Korisnik> sviKorisnici(){
        return korisnikRepository.sviKorisnici();
    }

    public Korisnik pronadjiKorisnika(Integer id){
        return korisnikRepository.pronadjiKorisnikaPoId(id);
    }
    public Response dodajKorinsika(Korisnik korisnik){
        String hesiranaLozinka = DigestUtils.sha256Hex(korisnik.getHesiranaLozinka());
        korisnik.setHesiranaLozinka(hesiranaLozinka);
        return korisnikRepository.dodajKorisnika(korisnik);
    }
    public void promeniStatusKorisniku(Integer korisnik_id){
        korisnikRepository.promeniStatusKorisniku(korisnik_id);
    }

    public Korisnik izmeniKorisnika(Integer id, Korisnik korisnik){
        return korisnikRepository.izmeniKorisnika(id,korisnik);
    }
}
