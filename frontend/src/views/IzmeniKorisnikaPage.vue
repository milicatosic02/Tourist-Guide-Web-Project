<template>
  <div class="container">
    <div class="form-wrapper" v-if="korisnik">
      <!-- Forma za izmenu korisnika -->
      <form @submit.prevent="sacuvajIzmene" class="izmeni-korisnika-form">
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
          <input v-model="korisnik.tip_korisnika" type="text" id="role" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Sačuvaj izmene</button>
      </form>
    </div>
    <p v-else>Učitavanje...</p>
  </div>
</template>

<script>
export default {
  name: "IzmeniKorisnikaPage",
  data() {
    return {
      korisnik: null
    };
  },
  created() {
    this.ucitajKorisnika();
  },
  methods: {
    ucitajKorisnika() {
      const korisnikId = this.$route.params.id;

      this.$axios.get(`/api/korisnici/${korisnikId}`)
          .then((response) => {
            this.korisnik = response.data;
          })
          .catch(error => {
            console.error('Greška prilikom preuzimanja korisnika:', error);
            this.korisnik = null; // Postaviti korisnika na null u slučaju greške
          });
    },
    sacuvajIzmene() {
      const korisnikId = this.$route.params.id;
      this.$axios.put(`/api/korisnici/${korisnikId}`, this.korisnik)
          .then(() => {
            console.log(`Izmene za korisnika ${korisnikId} su sačuvane.`);
            this.$router.push({name: 'KorisniciPage'});
          })
          .catch(error => {
            console.error('Greška prilikom čuvanja izmena:', error);
            alert('Došlo je do greške prilikom čuvanja izmena. Pokušajte ponovo.');
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

.form-wrapper {
  width: 80%;
  background: transparent;
}

.izmeni-korisnika-form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-group {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-bottom: 20px;
}

.form-label {
  margin-bottom: 5px;
}

.form-control {
  border-radius: 5px;
  border: 1px solid #ccc;
  padding: 10px 15px;
  width: 80%;
}

.btn-primary {
  display: block;
  width: 150px;
  padding: 10px;
  margin-top: 20px;
  background-color: cornflowerblue;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #C6E2FF;
}
</style>
