package rs.raf.backend;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.backend.repositories.aktivnost.AktivnostRepository;
import rs.raf.backend.repositories.aktivnost.MySqlAktivnostRepository;
import rs.raf.backend.repositories.clanak.ClanakRepositroy;
import rs.raf.backend.repositories.clanak.MySqlClanakRepository;
import rs.raf.backend.repositories.destinacija.DestinacijaRepository;
import rs.raf.backend.repositories.destinacija.MySqlDestinacijaRepository;
import rs.raf.backend.repositories.komentar.KomentarRepository;
import rs.raf.backend.repositories.komentar.MySqlKomentarRepository;
import rs.raf.backend.repositories.korisnik.KorisnikRepository;
import rs.raf.backend.repositories.korisnik.MySqlKorisnikRepository;
import rs.raf.backend.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {
    //ResoucreCOnfig koj nasledjuje application
    public HelloApplication() {

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
      //  binder sluzi d kzemo kako ce da nam se povezuju klase koje imejau inject
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlClanakRepository.class).to(ClanakRepositroy.class).in(Singleton.class);

                this.bind(MySqlKorisnikRepository.class).to(KorisnikRepository.class).in(Singleton.class);

                this.bind(MySqlAktivnostRepository.class).to(AktivnostRepository.class).in(Singleton.class);
                this.bind(MySqlKomentarRepository.class).to(KomentarRepository.class).in(Singleton.class);
                this.bind(MySqlDestinacijaRepository.class).to(DestinacijaRepository.class).in(Singleton.class);

                this.bindAsContract(ClanakServis.class);
                this.bindAsContract(KorisnikServis.class);
                this.bindAsContract(AktivnostServis.class);
                this.bindAsContract(KomentarServis.class);
                this.bindAsContract(DestinacijaServis.class);

            }
        };
        register(binder);

        packages("rs.raf.backend");
    }
}