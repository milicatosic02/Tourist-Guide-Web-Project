<template>
  <div class="container">
    <div class="form-wrapper" v-if="destinacija">
      <!-- Forma za izmenu destinacije -->
      <form @submit.prevent="sacuvajIzmene" class="izmeni-destinaciju-form">
        <div class="form-group naslov-group">
          <label for="ime" class="form-label">Ime:</label>
          <input v-model="destinacija.ime" type="text" id="ime" class="form-control naslov" required>
        </div>
        <div class="form-group opis-group">
          <label for="opis" class="form-label">Opis:</label>
          <textarea v-model="destinacija.opis" id="opis" class="form-control tekst" rows="20" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Sačuvaj izmene</button>
      </form>
      <p v-if="greska" class="greska-poruka">{{ greska }}</p>
    </div>
    <p v-else>Učitavanje...</p>
  </div>
</template>


<script>
export default {
  name: "IzmeniDestinacijuPage",
  data() {
    return {
      destinacija: null,
      greska: '' // Inicijalizacija promenljive za grešku
    };
  },
  created() {
    this.ucitajDestinaciju();
  },
  methods: {
    ucitajDestinaciju() {
      this.$axios.get(`/api/destinacije/${this.$route.params.id}`)
          .then((response) => {
            this.destinacija = response.data;
          })
          .catch(error => {
            console.error('Greška prilikom preuzimanja destinacije:', error);
            this.destinacija = null; // Postaviti destinaciju na null u slučaju greške
          });
    },
    sacuvajIzmene() {
      this.$axios.put(`/api/destinacije/${this.destinacija.id}`, this.destinacija)
          .then(() => {
            this.$emit('destinacija-izmenjena'); // Emituj događaj
            this.$router.push({name: 'DestinacijePage'});
          })
          .catch(error => {
            if (error.response && error.response.status === 409) {
              this.greska = 'Destinacija sa tim imenom već postoji.'; // Postavite poruku o grešci
            } else {
              console.error('Greška prilikom čuvanja izmena:', error);
              this.greska = 'Došlo je do greške prilikom čuvanja izmena. Pokušajte ponovo.';
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

.form-wrapper {
  width: 80%;
  background: transparent;
}

.izmeni-destinaciju-form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-group {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.naslov-group {
  margin-top: 30px; /* Razmak između gornje ivice stranice i naslova */
}

.form-label {
  margin-bottom: 5px;
}

.form-control {
  border-radius: 25px;
  border: 1px solid #ccc;
  padding: 10px 15px;
  margin-bottom: 10px;
}

.form-control.naslov {
  width: 80%;
}

.form-control.tekst {
  width: 80%;
  height: 300px; /* Prilagodi visinu textarea polja */
}

.btn-primary {
  display: block;
  width: 150px;
  padding: 5px;
  margin-top: 20px; /* Dodaj razmak između dugmeta i textarea polja */
  margin-bottom: 50px;
}

.greska-poruka {
  color: red;
  margin-top: 20px;
}
</style>
