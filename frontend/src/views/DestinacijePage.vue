<template>
  <div class="destinacije">
    <!-- Dodaj Destinaciju button -->
    <button v-if="isAuthenticated" @click="goToCreatePage" class="kreiraj-dugme">Dodaj Destinaciju</button>

    <div class="card-container">
      <div class="row">
        <div class="col-12">
          <div v-for="destinacija in paginatedDestinacije" :key="destinacija.id">
            <div class="card mb-4 card-zoom">
              <div class="card-body">
                <router-link v-if="destinacija.ime" :to="{ name: 'ClanciZaDestinaciju', params: { id: destinacija.id } }" class="no-underline">
                  <h5 class="card-title">{{ destinacija.ime | capitalize }}</h5>
                </router-link>
                <p v-if="destinacija.opis" class="card-text">{{ destinacija.opis | shortText }}</p>

                <!-- Dugmad za izmenu i brisanje -->
                <div class="button-container">
                  <router-link
                      v-if="isAuthenticated"
                      :to="{ name: 'IzmeniDestinacijuPage', params: { id: destinacija.id } }"
                      class="btn btn-edit"
                      :data-destinacija-id="destinacija.id"
                  >
                    Izmeni
                  </router-link>

                  <button
                      v-if="isAuthenticated"
                      @click="deleteDestinacija(destinacija.id)"
                      class="btn btn-delete"
                      :data-destinacija-id="destinacija.id"
                  >
                    Obriši
                  </button>
                </div>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1" class="page-link">Previous</button>
      <span>{{ currentPage }} / {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages" class="page-link">Next</button>
    </div>
  </div>
</template>

<script>
import Swal from 'sweetalert2';

export default {
  name: "DestinacijePage",
  data() {
    return {
      title: 'Destinacije',
      destinacije: [],
      currentPage: 1,
      pageSize: 10,
      isAuthenticated: false
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.destinacije.length / this.pageSize);
    },
    paginatedDestinacije() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      return this.destinacije.slice(startIndex, startIndex + this.pageSize);
    }
  },
  methods: {
    fetchDestinacije() {
      this.$axios.get('/api/destinacije')
          .then(response => {
            console.log(response.data); // Proverite podatke ovde
            this.destinacije = response.data;
          })
          .catch(error => {
            console.error('Error fetching destinations:', error);
          });
    },
    getDestinacijaUrl(destinacijaId) {
      return this.$router.resolve({ name: 'DestinacijaPage', params: { id: destinacijaId } }).href;
    },
    goToCreatePage() {
      this.$router.push({ name: 'DodajDestinacijuPage' });
    },
    deleteDestinacija(destinacijaId) {
      Swal.fire({
        title: 'Da li ste sigurni?',
        text: "Ova akcija se ne može poništiti!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Da, obriši!',
        cancelButtonText: 'Ne, otkaži!',
      }).then((result) => {
        if (result.isConfirmed) {
          this.$axios.delete(`/api/destinacije/${destinacijaId}`)
              .then(() => {
                this.fetchDestinacije();
                this.$root.$emit('destinacija-izmenjena'); // Emitovanje događaja
                Swal.fire({
                  title: 'Obrisano!',
                  text: 'Destinacija je uspešno obrisana.',
                  icon: 'success',
                  confirmButtonText: 'OK'
                });
              })
              .catch(error => {
                console.error('Greška prilikom brisanja destinacije:', error);
                Swal.fire({
                  title: 'Greška',
                  text: 'Došlo je do greške prilikom brisanja destinacije. Pokušajte ponovo.',
                  icon: 'error',
                  confirmButtonText: 'OK'
                });
              });
        }
      });
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    updateAuthState() {
      this.isAuthenticated = !!localStorage.getItem('jwt');
    }
  },
  created() {
    this.updateAuthState();
    this.fetchDestinacije();
    window.addEventListener('storage', this.updateAuthState);
  },
  destroyed() {
    window.removeEventListener('storage', this.updateAuthState);
  },
  filters: {
    shortText(value) {
      if (value.length < 300) {
        return value;
      }
      return value.slice(0, 300) + '...';
    },
    capitalize(value) {
      if (!value) return '';
      value = value.toString();
      return value.charAt(0).toUpperCase() + value.slice(1);
    }
  }
};
</script>

<style scoped>
.card-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}

.card-body {
  color: black;
}

.card-zoom {
  transition: transform 0.3s ease;
  text-decoration: none;
}

.card-zoom:hover {
  transform: scale(1.05);
}

.card-zoom:hover .card-title,
.card-zoom:hover .card-text,
.card-zoom:hover .card-date {
  text-decoration: inherit; /* Ovo će zadržati postojeću dekoraciju */
}

.button-container {
  display: flex;
  justify-content: flex-end;
  gap: 10px; /* Razmak između dugmadi */
}

.btn-edit,
.btn-delete {
  background-color: lightskyblue;
  border: none;
  color: white;
  padding: 5px 10px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-edit:hover,
.btn-delete:hover {
  background-color: #C6E2FF;
}

.card {
  width: 600px;
  margin: 10px;
  background: transparent;
  border-color: deepskyblue;
}

.card-title {
  color: black;
  text-decoration: none; /* Uklanjanje podvlačenja sa naslova */
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

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.pagination button {
  margin: 0 5px;
  background: none;
  border: none;
  color: gray;
  cursor: pointer;
  text-decoration: underline;
}

.pagination button:hover {
  color: black;
}

.kreiraj-dugme {
  background-color: royalblue;
  border: none;
  color: white;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: block;
  font-size: 16px;
  margin: 30px auto; /* Ovo centrira dugme */
  cursor: pointer;
  border-radius: 5px;
}

.card-date {
  font-style: italic;
  color: #666;
}

.no-underline {
  text-decoration: none !important; /* Ovo osigurava da router-link nema podvlačenje */
}
</style>
