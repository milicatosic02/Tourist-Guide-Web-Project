<template>
  <div class="container">
    <!-- Forma za kreiranje korisnika -->
    <form @submit.prevent="kreirajKorisnika" class="kreiraj-korisnika-form">
      <div class="form-group">
        <label for="ime" class="form-label">Ime:</label>
        <input v-model="korisnik.ime" type="text" id="ime" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="prezime" class="form-label">Prezime:</label>
        <input v-model="korisnik.prezime" type="text" id="prezime" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="email" class="form-label">Email:</label>
        <input v-model="korisnik.email" type="email" id="email" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="role" class="form-label">Tip Korisnika:</label>
        <input v-model="korisnik.tip_korisnika" type="text" id="tip_korisnika" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="hashedPassword" class="form-label">Lozinka:</label>
        <input v-model="hesiranaLozinka" type="password" id="hesiranaLozinka" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="potvrdaLozinke" class="form-label">Potvrda lozinke:</label>
        <input v-model="potvrdaLozinke" type="password" id="potvrdaLozinke" class="form-control" required>
      </div>
      <div v-if="errorLozinke || errorEmail" class="alert alert-danger" role="alert">
        <template v-if="errorLozinke">
          Lozinke se ne poklapaju.
        </template>
        <template v-else-if="errorEmail">
          Korisnik sa unetom email adresom već postoji.
        </template>
      </div>
      <button type="submit" class="btn btn-primary">Dodaj Korisnika</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "KreirajKorisnikaPage",
  data() {
    return {
      korisnik: {
        ime: "",
        prezime: "",
        email: "",
        tip_korisnika: ""
      },
      hesiranaLozinka: "",
      potvrdaLozinke: "",
      errorLozinke: false,
      errorEmail: false
    };
  },
  methods: {
    kreirajKorisnika() {
      if (this.hesiranaLozinka !== this.potvrdaLozinke) {
        this.errorLozinke = true;
        this.errorEmail = false;
        return;
      }

      this.errorLozinke = false;

      this.$axios.post('/api/korisnici', {
        ime: this.korisnik.ime,
        prezime: this.korisnik.prezime,
        email: this.korisnik.email,
        tip_korisnika: this.korisnik.tip_korisnika,
        hesiranaLozinka: this.hesiranaLozinka
      })
          .then(() => {
            console.log('Korisnik uspešno kreiran.');
            this.$router.push({ name: 'KorisniciPage' });
          })
          .catch(error => {
            if (error.response.status === 409) {
              this.errorEmail = true;
              this.errorLozinke = false;
            } else {
              console.error('Greška prilikom kreiranja korisnika:', error);
              alert('Došlo je do greške prilikom kreiranja korisnika. Pokušajte ponovo.');
            }
          });
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

.kreiraj-korisnika-form {
  width: 50%;
  margin-top: 50px;
  padding: 20px;
  background-color: transparent; /* Promenite boju pozadine na transparent */
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  margin-bottom: 5px;
}

.form-control {
  border-radius: 5px;
  border: 1px solid #ccc;
  padding: 10px 15px;
  width: 100%;
}

.btn-primary {
  display: block;
  width: 40%; /* Širina dugmeta */
  margin: 20px auto; /* Centriranje dugmeta */
  padding: 12px;
  background-color: lightskyblue; /* Roze boja */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-primary:hover {
  background-color: #C6E2FF;
}

.alert {
  background-color: #f8d7da;
  color: #721c24;
  padding: 10px;
  border-radius: 5px;
  margin-top: 10px;
}
</style>
