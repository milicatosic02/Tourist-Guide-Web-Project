<template>
  <div class="container">
    <!-- Kreiraj korisnika button -->
    <button v-if="jwt" @click="kreirajKorisnika" class="kreiraj-dugme">Kreiraj korisnika</button>

    <!-- List of users -->
    <div v-if="korisniciLoaded" class="row">
      <div class="col-12">
        <div class="korisnik-card" v-for="korisnik in korisnici" :key="korisnik.id">
          <div class="card mb-4">
            <div class="card-body">
              <h5 class="card-title">{{ korisnik.ime }} {{ korisnik.prezime }}</h5>
              <p class="card-text"><strong>Email:</strong> {{ korisnik.email }}</p>
              <p class="card-text"><strong>Role:</strong> {{ korisnik.tip_korisnika }}</p>
              <p class="card-text"><strong>Status:</strong> {{ korisnik.status ? 'Active' : 'Inactive' }}</p>
            </div>

            <div class="card-footer">
              <button @click="goToEditUser(korisnik.id)" class="btn btn-edit" :data-korisnik-id="korisnik.id">
                Izmeni
              </button>

              <button @click="toggleUserStatus(korisnik)" class="btn btn-status" :data-korisnik-id="korisnik.id">
                Promeni status
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-else>
      <p>Loading...</p>
    </div>

  </div>
</template>

<script>
export default {
  name: "KorisniciPage",
  data() {
    return {
      korisnici: [],
      jwt: null, // Token iz local storage
      korisniciLoaded: false
    };
  },
  created() {
    // Učitavanje tokena iz local storage
    this.jwt = localStorage.getItem('jwt');

    // Zahtev za dobijanje korisnika
    this.$axios.get('/api/korisnici')
        .then((response) => {
          if (response.status === 422) {
            // Neautorizovan pristup, ostati na trenutnoj stranici ili preusmeriti na drugu
            console.error('Nemate dozvolu za pristup ovoj stranici.');
            // Primer preusmeravanja na početnu stranicu
            this.$router.push({ name: 'LoginPage' });
          } else {
            this.korisnici = response.data;
            this.korisniciLoaded = true;
          }
        })
        .catch((error) => {
          console.error('Greška prilikom dobijanja korisnika:', error);
        });
  },
  methods: {
    kreirajKorisnika() {
      this.$router.push({ name: 'KreirajKorisnikaPage' });
    },
    goToEditUser(korisnikId) {
      this.$router.push({ name: 'IzmeniKorisnikaPage', params: { id: korisnikId } });
    },
    toggleUserStatus(korisnik) {
      const endpoint = `/api/korisnici/suspend/${korisnik.id}`;

      this.$axios.put(endpoint)
          .then(() => {
            // Ažurirati status u lokalnom nizu korisnika
            korisnik.status = !korisnik.status;
          })
          .catch((error) => {
            console.error(`Greška prilikom promene statusa korisnika sa ID ${korisnik.id}:`, error);
          });
    }
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.kreiraj-dugme {
  background-color: lightskyblue;
  border: none;
  color: white;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 30px 0;
  cursor: pointer;
  border-radius: 5px;
}

.korisnik-card {
  margin-bottom: 20px;
}

.card {
  cursor: pointer;
  transition: transform 0.2s;
  color: black;
  position: relative; /* Make the card position relative */
  height: 220px; /* Adjust height as needed */
  width: 420px;
  border-radius: 5px; /* Add border radius */
  border-color: lightskyblue;
  background: transparent; /* Set background to transparent */
}

.card:hover {
  transform: scale(1.05);
}

.card-body {
  padding: 20px; /* Add padding to the card body */
}

.card-footer {
  position: absolute;
  bottom: 10px;
  right: 10px;
}

.btn-edit {
  background-color: lightblue; /* Change the background color to lightblue */
  color: white;
  padding: 5px 10px;
  cursor: pointer;
  outline: none; /* Remove default outline */
}

.btn-edit:hover {
  background-color: #87cefa; /* Change the hover color to a darker shade of lightblue */
}

.btn-status {
  background-color: lightskyblue;
  color: white;
  padding: 5px 10px;
  cursor: pointer;
  outline: none; /* Remove default outline */
  margin-left: 10px; /* Add margin between Edit and Status buttons */
}

.btn-status:hover {
  background-color: #C6E2FF;
}
</style>
