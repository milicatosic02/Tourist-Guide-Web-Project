package rs.raf.backend.entiteti;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Destinacija {

    @NotNull(message = "Obavezno polje! Destinacija mora imati ime!")
    @NotEmpty(message = "Obavezno polje! Destinacija mora imati ime!")
    private String ime;
    private String opis;
    private Integer id;

    public Destinacija(){

    }

    public Destinacija(String ime, String opis){
        this.ime = ime;
        this.opis = opis;
    }
    public Destinacija(Integer id, String ime, String opis){
        this.id = id;
        this.ime = ime;
        this.opis = opis;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }
}
