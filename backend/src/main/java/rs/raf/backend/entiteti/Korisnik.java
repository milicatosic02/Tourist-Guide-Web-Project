package rs.raf.backend.entiteti;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Korisnik {

    @Email
    private String email;

    @NotNull(message = "Obavezno polje! Korisnik mora imati ime!")
    @NotEmpty(message = "Obavezno polje! Korisnik mora imati ime!")
    private String ime;

    @NotNull(message = "Obavezno polje! Korisnik mora imati prezime!")
    @NotEmpty(message = "Obavezno polje! Korisnik mora imati prezime!")
    private String prezime;

    @NotNull(message = "Obavezno polje! Korisnik mora imati status!")
    @NotEmpty(message = "Obavezno polje! Korisnik mora imati status!")
    private String tip_korisnika;

    @NotNull(message = "Obavezno polje! Korisnik mora imati sifru!")
    @NotEmpty(message = "Obavezno polje! Korisnik mora imati sifru!")
    private String hesiranaLozinka;

    private Integer id;
    private boolean status;

    public Korisnik(String ime, String prezime, String email, String tip_korisnika, String hesiranaLozinka){
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.tip_korisnika = tip_korisnika;
        this.hesiranaLozinka = hesiranaLozinka;
    }
    public Korisnik(Integer id, String ime, String prezime, String email, String tip_korisnika, String hesiranaLozinka){
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.tip_korisnika = tip_korisnika;
        this.hesiranaLozinka = hesiranaLozinka;
    }
    public Korisnik(Integer id, String ime, String prezime, String email, String tip_korisnika, Boolean status){
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.tip_korisnika = tip_korisnika;
        this.status = status;
    }
    public Korisnik(Integer id, String ime, String prezime, String email, String tip_korisnika, Boolean status, String hesiranaLozinka){
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.tip_korisnika = tip_korisnika;
        this.status = status;
        this.hesiranaLozinka  = hesiranaLozinka;
    }

    public Korisnik(){}

    public String getEmail() {
        return email;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public String getIme(){return ime;}
    public void setIme(String ime){this.ime = ime;}

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public boolean isStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTip_korisnika( String tip_korisnika) {
        this.tip_korisnika = tip_korisnika;
    }

    public String getTip_korisnika() {
        return tip_korisnika;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getHesiranaLozinka() {
        return hesiranaLozinka;
    }

    public void setHesiranaLozinka(String hesiranaLozinka) {
        this.hesiranaLozinka = hesiranaLozinka;
    }
}
