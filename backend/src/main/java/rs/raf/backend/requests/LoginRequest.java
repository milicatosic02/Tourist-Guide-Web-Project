package rs.raf.backend.requests;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull(message = "Email je obavezan")
    @NotEmpty(message = "Email je obavezan")
    private String email;

    @NotNull(message = "Sifra je obavezna")
    @NotEmpty(message = "Sifra je obavezna")
    private String sifra;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }


}
