<template>
  <div class="container">
    <div class="form-wrapper">
      <!-- Forma za dodavanje destinacije -->
      <form @submit.prevent="dodajDestinaciju" class="dodaj-destinaciju-form">
        <div class="form-group naslov-group">
          <label for="ime" class="form-label">Ime:</label>
          <input v-model="novaDestinacija.ime" type="text" id="ime" class="form-control naslov" required>
        </div>
        <div class="form-group opis-group">
          <label for="opis" class="form-label">Opis:</label>
          <textarea v-model="novaDestinacija.opis" id="opis" class="form-control tekst" rows="20" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Dodaj destinaciju</button>
      </form>
    </div>
  </div>
</template>

<script>
import Swal from 'sweetalert2';

export default {
  name: "DodajDestinacijuPage",
  data() {
    return {
      novaDestinacija: {
        ime: '',
        opis: ''
      }
    };
  },
  methods: {
    dodajDestinaciju() {
      this.$axios.get('/api/destinacije')
          .then(response => {
            // Filtrirajte rezultate na klijentskoj strani
            const postojiDestinacija = response.data.some(destinacija => destinacija.ime === this.novaDestinacija.ime);
            if (postojiDestinacija) {
              // Destinacija sa istim imenom već postoji
              Swal.fire({
                title: 'Greška',
                text: 'Destinacija sa tim imenom već postoji.',
                icon: 'error',
                confirmButtonText: 'OK'
              });
            } else {
              // Dodaj novu destinaciju jer ne postoji destinacija sa istim imenom
              this.$axios.post('/api/destinacije', this.novaDestinacija)
                  .then(() => {
                    this.$root.$emit('destinacija-izmenjena'); // Emituj globalni događaj
                    this.$router.push({ name: 'DestinacijePage' });
                    Swal.fire({
                      title: 'Uspeh!',
                      text: 'Nova destinacija je uspešno dodata.',
                      icon: 'success',
                      confirmButtonText: 'OK'
                    });
                  })
                  .catch(error => {
                    console.error('Greška prilikom dodavanja destinacije:', error);
                    Swal.fire({
                      title: 'Greška',
                      text: 'Došlo je do greške prilikom dodavanja destinacije. Pokušajte ponovo.',
                      icon: 'error',
                      confirmButtonText: 'OK'
                    });
                  });
            }
          })
          .catch(error => {
            console.error('Greška prilikom provere imena destinacije:', error);
            Swal.fire({
              title: 'Greška',
              text: 'Došlo je do greške prilikom provere imena destinacije. Pokušajte ponovo.',
              icon: 'error',
              confirmButtonText: 'OK'
            });
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

.dodaj-destinaciju-form {
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
  color: white;
  background: steelblue;
  border: none;
}
</style>
