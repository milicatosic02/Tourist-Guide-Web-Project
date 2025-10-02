package rs.raf.backend.entiteti;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Clanak {
    @NotNull(message = "Obavezno polje! Naslov Clanka je obavezan")
    @NotEmpty(message = "Obavezno polje! Naslov Clanka je obavezan")
    private String naslov;

    @NotNull(message = "Obavezno polje! Tekst Clanka je obavezan")
    @NotEmpty(message = "Obavezno polje! Tkest Clanka je obavezan")
    private String tekst;
    private String autor_id;
    private List<Komentar> komentari;
    private String datum;
    private int destinacija_id;
    private Integer id;
    private List<Aktivnost> aktivnosti;

    public Clanak(){

    }
    public Clanak(String naslov, String tekst, String autor_id, String datum) {
        this.naslov = naslov;
        this.tekst = tekst;
        this.autor_id = autor_id;
        this.datum = datum;
        komentari = new ArrayList<>();
        aktivnosti = new ArrayList<>();
    }

    public Clanak(String naslov, String tekst, Integer destinacija_id, List<Aktivnost> aktivnosti){
        this.naslov = naslov;
        this.tekst = tekst;
        this.destinacija_id = destinacija_id;
        this.aktivnosti = aktivnosti;
    }

    public Clanak(Integer id, String naslov, String tekst, String autor_id, String datum, Integer destinacija_id) {
        this.id = id;
        this.naslov = naslov;
        this.tekst = tekst;
        this.autor_id = autor_id;
        this.datum = datum;
        this.destinacija_id = destinacija_id;
        komentari = new ArrayList<>();
        aktivnosti = new ArrayList<>();
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getAutor_id() {
        return autor_id;
    }

    public void setAutor_id(String autor_id) {
        this.autor_id = autor_id;
    }

    public List<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Aktivnost> getAktivnosti() {
        return aktivnosti;
    }

    public void setAktivnosti(List<Aktivnost> aktivnosti) {
        this.aktivnosti = aktivnosti;
    }

    public int getDestinacija_id() {
        return destinacija_id;
    }

    public void setDestinacija_id(int destinacija_id) {
        this.destinacija_id = destinacija_id;
    }
}
