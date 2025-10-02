package rs.raf.backend.entiteti;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Komentar {
    private String autor_komentara;
    @NotNull(message = "Obavezno polje! Sadrzaj komentara je obavezan!")
    @NotEmpty(message = "Obavezno polje! Sadrzaj komentara je obavezan!")
    private String tekst;
    private String datum_kreiranja;
    private Integer id;
    public Komentar() {
    }

    public Komentar(String autor_komentara, String tekst, String datum_kreiranja) {
        this.autor_komentara = autor_komentara;
        this.tekst = tekst;
        this.datum_kreiranja = datum_kreiranja;
    }

    public Komentar(Integer id,String autor_komentara, String tekst, String datum_kreiranja) {
        this.id = id;
        this.autor_komentara = autor_komentara;
        this.tekst = tekst;
        this.datum_kreiranja = datum_kreiranja;
    }


    public String getAutor_komentara() {
        return autor_komentara;
    }

    public void setAutor_komenatara(String autor_komenatara) {
        this.autor_komentara = autor_komentara;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getDatum_kreiranja() {
        return datum_kreiranja;
    }

    public void setDatum_kreiranja(String datum_kreiranja) {
        this.datum_kreiranja = datum_kreiranja;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
