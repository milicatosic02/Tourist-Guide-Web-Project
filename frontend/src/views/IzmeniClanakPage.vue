<template>
  <div class="container">
    <div class="form-wrapper" v-if="clanak">

      <!-- Forma za izmenu članka -->
      <form @submit.prevent="sacuvajIzmene" class="izmeni-clanak-form">
        <div class="form-group naslov-group">
          <label for="naslov" class="form-label">Naslov:</label>
          <input v-model="clanak.naslov" type="text" id="naslov" class="form-control naslov" required>
        </div>
        <div class="form-group tekst-group">
          <label for="tekst" class="form-label">Tekst:</label>
          <textarea v-model="clanak.tekst" id="tekst" class="form-control tekst" rows="10" required></textarea>
        </div>

        <!-- Destinacija i dodaj aktivnost -->
        <div class="form-group destinacija-aktivnost-group" style="display: flex; justify-content: space-between;">

          <!-- Destinacija -->
          <div style="width: 40%; height: auto;">
            <label for="destinacija" class="small-label">Destinacija:</label>
            <select v-model="clanak.destinacija_id" id="destinacija" class="form-control" required>
              <option v-for="destinacija in dostupneDestinacije" :key="destinacija.id" :value="destinacija.id">{{ destinacija.ime }}</option>
            </select>
          </div>

          <!-- Dodaj aktivnost -->
          <div style="width: 40%;">
            <label for="nova-aktivnost" class="small-label">Dodaj aktivnost:</label>
            <select v-model="novaAktivnostId" id="nova-aktivnost" class="form-control" @change="dodajAktivnost">
              <option value="">Izaberite aktivnost...</option>
              <option v-for="aktivnost in dostupneAktivnosti" :key="aktivnost.id" :value="aktivnost.id">{{ aktivnost.naziv }}</option>
            </select>
          </div>

        </div>

        <!-- Lista veza -->
        <div class="aktivnosti" v-if="clanak.aktivnosti && clanak.aktivnosti.length > 0">
          <div class="links">
            <div v-for="(aktivnost, index) in clanak.aktivnosti" :key="index" class="aktivnost-link">
              <span class="btn btn-link" @click="nistaNeRadi">{{ aktivnost.naziv }}</span>
              <button @click="ukloniAktivnost(aktivnost.id, $event)" class="btn btn-link remove-link">x</button>
            </div>
          </div>
        </div>

        <button type="submit" class="btn btn-primary">Sačuvaj izmene</button>
      </form>
    </div>
    <p v-else>Učitavanje...</p>
  </div>
</template>

<script>
export default {
  name: "IzmeniClanakPage",
  data() {
    return {
      clanak: null,
      novaAktivnostId: null, // ID nove aktivnosti koju želimo da dodamo
      dostupneAktivnosti: [], // Lista dostupnih aktivnosti iz API-ja
      dostupneDestinacije: [] // Lista dostupnih destinacija iz API-ja
    };
  },
  created() {
    this.ucitajClanak();
    this.ucitajDostupneAktivnosti();
    this.ucitajDostupneDestinacije();
  },
  methods: {
    ucitajClanak() {
      this.$axios.get(`/api/clanci/${this.$route.params.id}`)
          .then((response) => {
            this.clanak = response.data;
          })
          .catch(error => {
            console.error('Greška prilikom preuzimanja članka:', error);
            this.clanak = null; // Postaviti članak na null u slučaju greške
          });
    },
    sacuvajIzmene() {
      this.$axios.put(`/api/clanci/${this.clanak.id}`, this.clanak)
          .then(() => {
            this.$router.push({ name: 'PocetnaPage' });
          })
          .catch(error => {
            console.error('Greška prilikom čuvanja izmena:', error);
            alert('Došlo je do greške prilikom čuvanja izmena. Pokušajte ponovo.');
          });
    },
    ukloniAktivnost(aktivnostId, event) {
      event.preventDefault(); // Sprečavanje podrazumevanog ponašanja submit događaja
      const index = this.clanak.aktivnosti.findIndex(akt => akt.id === aktivnostId);
      if (index !== -1) {
        this.clanak.aktivnosti.splice(index, 1);
      }
    },
    ucitajDostupneAktivnosti() {
      this.$axios.get('/api/aktivnosti')
          .then(response => {
            this.dostupneAktivnosti = response.data;
          })
          .catch(error => {
            console.error('Greška prilikom preuzimanja aktivnosti:', error);
            this.dostupneAktivnosti = [];
          });
    },
    ucitajDostupneDestinacije() {
      this.$axios.get('/api/destinacije')
          .then(response => {
            this.dostupneDestinacije = response.data;
          })
          .catch(error => {
            console.error('Greška prilikom preuzimanja destinacija:', error);
            this.dostupneDestinacije = [];
          });
    },
    dodajAktivnost() {
      if (!this.novaAktivnostId) {
        return; // Ako nije izabrana nova aktivnost, prekini funkciju
      }

      // Provera da li je nova aktivnost već dodata
      const vecDodata = this.clanak.aktivnosti.some(akt => akt.id === this.novaAktivnostId);
      if (!vecDodata) {
        // Pronalaženje aktivnosti na osnovu ID-ja
        const novaAktivnost = this.dostupneAktivnosti.find(akt => akt.id === this.novaAktivnostId);
        if (novaAktivnost) {
          // Dodavanje nove aktivnosti u listu aktivnosti članka
          this.clanak.aktivnosti.push(novaAktivnost);
          // Resetovanje selektovanog ID-ja nove aktivnosti
          this.novaAktivnostId = null;
        }
      }
    },
    nistaNeRadi() {
      // Ne radi ništa
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

.izmeni-clanak-form {
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
  height: 1000px;
}

.small-label {
  font-size: 0.85em;
}

.destinacija-aktivnost-group {
  margin-top: 20px; /* Smanjenje razmaka između sekcija */
}

.form-control {
  height: 50px; /* Povećava visinu select polja */
  padding: 10px 15px; /* Dodaje padding za bolju vidljivost teksta */
}

.btn-primary {
  display: block;
  width: 150px;
  padding: 5px;
  margin-bottom: 50px;
}

.aktivnosti {
  margin-top: 5px; /* Smanjuje razmak između sekcije aktivnosti i ostatka forme */
}

.aktivnost-link {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.remove-link {
  margin-left: 5px;
  cursor: pointer;
  background: none;
  border: none;
  color: red;
}
</style>
