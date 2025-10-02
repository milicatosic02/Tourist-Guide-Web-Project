<template>
  <div>
    <jedan-clanak v-if="clanak" :clanak="clanak"></jedan-clanak>
    <p v-else>Loading...</p>
    <div class="komentari-container">
      <h3>Komentari:</h3>
      <div v-if="clanak && clanak.komentari && clanak.komentari.length > 0">
        <div v-for="komentar in clanak.komentari" :key="komentar.id" class="komentar">
          <strong>{{ komentar.autor_komentara }}:</strong> {{ komentar.tekst }}
          <div class="komentar-datum">{{ komentar.datum_kreiranja }}</div>
        </div>
      </div>
      <div v-else>
        Nema komentara.
      </div>
    </div>

    <!-- Add Comment form -->
    <div class="dodaj-komentar-form" v-if="clanak">
      <form @submit.prevent="dodajKomentar">
        <div class="form-group">
          <label for="ime">Vaše ime:</label>
          <input v-model="noviKomentar.autor_komentara" type="text" id="ime" class="form-control" required>
        </div>
        <div class="form-group">
          <label for="komentar">Komentar:</label>
          <textarea v-model="noviKomentar.tekst" id="komentar" class="form-control" rows="3" required></textarea>
        </div>
        <button type="submit" class="submit-button">Dodaj komentar</button>
      </form>
    </div>

    <div class="aktivnosti" v-if="clanak && clanak.aktivnosti">
      <div class="links">
        <router-link v-for="(aktivnost, index) in clanak.aktivnosti" :key="index" :to="{ name: 'ClanciZaAktivnostPage', params: { id: aktivnost.id } }" class="btn btn-link">{{ aktivnost.naziv }}</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import JedanClanak from "../components/JedanClanak.vue";

export default {
  name: "ClanakPage",
  components: { JedanClanak },
  data() {
    return {
      clanak: null,
      noviKomentar: {
        autor: '',
        sadrzaj: ''
      }
    }
  },
  created() {
    this.ucitajClanak();
  },
  methods: {
    ucitajClanak() {
      this.$axios.get(`/api/clanci/${this.$route.params.id}`).then((response) => {
        this.clanak = response.data;
      }).catch(error => {
        console.error('Error fetching article:', error);
        this.clanak = null; // Postavi clanak na null u slucaju greske
      });
    },
    dodajKomentar() {
      if (!this.noviKomentar.autor_komentara || !this.noviKomentar.tekst) {
        alert('Popunite sva polja!');
        return;
      }

      const noviKomentar = {
        autor_komentara: this.noviKomentar.autor_komentara,
        tekst: this.noviKomentar.tekst,
        datum_kreiranja: new Date().toLocaleString()
      };

      this.$axios.post(`/api/komentari/${this.clanak.id}`, noviKomentar)
          .then(() => {
            // Dodavanje novog komentara u listu komentara
            this.clanak.komentari.push(noviKomentar);

            // Resetovanje forme
            this.noviKomentar.autor_komentara = '';
            this.noviKomentar.tekst = '';
          })
          .catch(error => {
            console.error('Greška prilikom dodavanja komentara:', error);
            alert('Došlo je do greške prilikom dodavanja komentara. Pokušajte ponovo.');
          });
    }
  }
}
</script>

<style scoped>
.komentari-container {
  margin-top: 60px;
}

.komentar {
  margin-bottom: 10px;
}

.komentar-datum {
  font-size: 0.8em;
  color: gray;
}

.dodaj-komentar-form {
  margin-top: 20px;
}

.form-control {
  border-radius: 25px;
  background-color: rgba(255, 255, 255, 0.8);
  border: 1px solid #ccc;
  padding: 10px 15px;
  margin-bottom: 10px;
  width: 20%;
}

.btn-primary {
  display: block;
  width: 150px;
  padding: 5px;
}

.submit-button{
  color: white;
  background: lightskyblue;
  display: block;
  width: 150px;
  padding: 5px;
  border: none;
}

.aktivnosti {
  margin-top: 100px;
  margin-bottom: 30px;
  display: flex;
  justify-content: center; /* Centriranje elemenata horizontalno */
}
</style>
