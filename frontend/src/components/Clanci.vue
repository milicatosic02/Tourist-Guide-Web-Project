
<template>
  <div class="clanci">
    <h2 class="title">{{ title }}</h2>
    <div class="card-container">
      <div class="row">
        <div class="col-12">
          <div v-for="clanak in paginatedClanci" :key="clanak.id">
            <div class="card mb-4 card-zoom">
              <div class="card-body">
                <router-link :to="{ name: 'ClanakPage', params: { id: clanak.id } }" target="_blank" class="router-link-title">
                  <h5 class="card-title">{{ clanak.naslov | capitalize }}</h5>
                </router-link>
                <p class="card-text">{{ clanak.tekst | shortText }}</p>
                <p class="card-date">{{ formatDate(clanak.datum) }}</p>

                <div class="button-container">
                  <router-link
                      v-if="isAuthenticated"
                      :to="{ name: 'IzmeniClanakPage', params: { id: clanak.id } }"
                      class="btn btn-edit router-link"
                  >
                    Izmeni
                  </router-link>

                  <!-- Dugme za brisanje -->
                  <button
                      v-if="isAuthenticated"
                      @click.stop="deleteClanak(clanak.id)"
                      class="btn btn-delete"
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
<script >
export default {
  name: "SviClanci",
  props: {
    title: {
      type: String,
      required: true
    },
    clanci: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      isAuthenticated: false
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.clanci.length / this.pageSize);
    },
    paginatedClanci() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      return this.clanci.slice(startIndex, startIndex + this.pageSize);
    }
  },
  methods: {
    deleteClanak(clanakId) {
      if (confirm('Da li ste sigurni da želite da obrišete ovaj članak?')) {
        this.$axios.delete(`/api/clanci/${clanakId}`)
            .then(() => {
              console.log('Članak je uspešno obrisan.');
              this.$emit('clanci-updated'); // Emitujemo događaj za ažuriranje clanci propsa
            })
            .catch(error => {
              console.error('Greška prilikom brisanja članka:', error);
            });
      }
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
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      const day = date.getDate().toString().padStart(2, '0');
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const year = date.getFullYear();
      return `${day}.${month}.${year}`;
    }
  },
  created() {
    this.updateAuthState();
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
  margin-bottom: 20px;
}

.clanak-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  padding-right: 120px;
  width: 100%;
  margin-bottom: 20px;
}

.card-body {
  color: black;
}

.card-zoom {
  transition: transform 0.3s ease;
  text-decoration: none;
  color: black;
}

.card-zoom:hover {
  transform: scale(1.05);
}

.card-zoom:hover .card-title,
.card-zoom:hover .card-text,
.card-zoom:hover .card-date {
  text-decoration: none;
  color: black;
}

.button-container {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn-edit,
.btn-delete {
  background-color: lightblue;
  border: none;
  color: white;
  padding: 5px 10px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-edit:hover,
.btn-delete:hover {
  background-color: #b3e0ff;
}

.card {
  max-width: 700px;
  width: 100%;
  margin: 10px;
  padding: 15px;
  background-color: #f0f8ff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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

.title {
  margin-top: 20pt;
  text-align: center;
  margin-bottom: 20px;
}

.card-date {
  font-style: italic;
  color: #666;
}

.router-link-title {
  color: black;
  text-decoration: none;
}

.router-link-title:hover {
  color: black;
  text-decoration: underline;
}
</style>
