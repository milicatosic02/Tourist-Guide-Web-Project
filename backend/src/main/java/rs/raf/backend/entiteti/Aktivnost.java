package rs.raf.backend.entiteti;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Aktivnost {

    @NotNull(message = "Naziv je obavezan!")
    @NotEmpty(message = "Naziv je obavezan!")
    private String naziv;

    private Integer id;

    public Aktivnost() {
    }

    public Aktivnost(String kljucneReci) {
        this.naziv = naziv;
    }

    public Aktivnost(Integer id, String naziv) {
        this.naziv = naziv;
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
