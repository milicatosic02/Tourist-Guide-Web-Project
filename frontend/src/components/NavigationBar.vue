<template>
  <div>
    <nav class="navbar navbar-expand-lg custom-navbar">
      <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link v-if="!isAuthenticated" to="/" tag="a" class="nav-link" active-class="active" exact>Početna stranica</router-link>
              <router-link v-else to="/" tag="a" class="nav-link" active-class="active" exact>Članci</router-link>
            </li>
            <li v-if="!isAuthenticated" class="nav-item">
              <router-link to="/najcitanije" tag="a" class="nav-link" active-class="active" exact>Najčitanije</router-link>
            </li>
            <li v-if="isAuthenticated" class="nav-item">
              <router-link to="/destinacije" tag="a" class="nav-link" active-class="active" exact>Destinacije</router-link>
            </li>
            <li v-if="isAuthenticated && user && user.tip_korisnika === 'admin'" class="nav-item">
              <router-link to="/korisnici" tag="a" class="nav-link" active-class="active" exact>Korisnici</router-link>
            </li>
            <li v-if="!isAuthenticated" class="nav-item dropdown" @mouseleave="closeDestinacijeBox">
              <a class="nav-link dropdown-toggle" href="#" role="button" aria-expanded="false" @click="toggleDestinacijeBox">
                Destinacije
              </a>
              <ul v-if="showDestinacijeBox" class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li v-for="destinacija in destinacije" :key="destinacija.id">
                  <router-link :to="{ name: 'ClanciZaDestinaciju', params: { id: destinacija.id }}" class="dropdown-item">{{ destinacija.ime }}</router-link>
                </li>
              </ul>
            </li>
          </ul>
          <form v-if="isAuthenticated" class="d-flex" @submit.prevent="logout">
            <button class="btn btn-outline-secondary" type="submit">Logout</button>
          </form>
          <router-link v-else to="/login" class="btn btn-outline-secondary">Login</router-link>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import jwtDecode from 'jwt-decode';

export default {
  data() {
    return {
      showDestinacijeBox: false,
      destinacije: [],
      isAuthenticated: false,
      user: null // Dodato za čuvanje dekodiranog korisničkog tokena
    };
  },
  methods: {
    logout() {
      localStorage.removeItem('jwt');
      this.isAuthenticated = false;
      this.user = null; // Očisti dekodirane podatke korisnika
      this.$router.push({ name: 'LoginPage' });
    },
    toggleDestinacijeBox() {
      this.showDestinacijeBox = !this.showDestinacijeBox;
    },
    closeDestinacijeBox() {
      this.showDestinacijeBox = false;
    },
    pronadjiDestinacije() {
      this.$axios.get('/api/destinacije').then((response) => {
        this.destinacije = response.data;
      }).catch((error) => {
        console.error('Error fetching destinacije:', error);
      });
    },
    osveziDestinacije() {
      this.pronadjiDestinacije();
    },
    updateAuthState() {
      const token = localStorage.getItem('jwt');
      this.isAuthenticated = !!token;
      if (this.isAuthenticated) {
        try {
          this.user = jwtDecode(token); // Dekodiraj JWT i sačuvaj korisničke podatke
        } catch (e) {
          console.error('Invalid token:', e);
          this.isAuthenticated = false;
          this.user = null;
        }
      } else {
        this.user = null;
      }
    }
  },
  created() {
    this.pronadjiDestinacije();
    this.updateAuthState(); // Odmah proveri status autentifikacije pri kreiranju komponente
    window.addEventListener('storage', this.updateAuthState);
    this.$root.$on('destinacija-izmenjena', this.osveziDestinacije); // Presreći događaj
  },
  destroyed() {
    window.removeEventListener('storage', this.updateAuthState);
    this.$root.$off('destinacija-izmenjena', this.osveziDestinacije); // Ukloni presretanje
  },
  watch: {
    // eslint-disable-next-line no-unused-vars
    '$route'(to, from) {
      this.updateAuthState();
    }
  }
}
</script>

<style scoped>
.custom-navbar {
  background-color: lightblue;
}

.nav-link.active {
  font-weight: bold;
  color: #ffffff !important;
}

.nav-link {
  color: #000000 !important;
}

.dropdown-menu {
  display: block;
}
</style>
