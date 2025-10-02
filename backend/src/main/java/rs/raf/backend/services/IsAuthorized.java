package rs.raf.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import rs.raf.backend.entiteti.Korisnik;
import rs.raf.backend.repositories.korisnik.KorisnikRepository;


import javax.inject.Inject;

public abstract class IsAuthorized {
    @Inject
    KorisnikRepository korisnikRepository;

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String email = jwt.getSubject();

        Korisnik korisnik = this.korisnikRepository.pronadjiKorisnikaPoEmailu(email);

        if(korisnik == null){
            return false;
        }

        return true;
    }
}
