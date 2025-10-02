<template>
  <div class="pt-5">
    <h1 v-if="email">Hello, {{email}}</h1>
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="email">Email</label>
        <input v-model="email" type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email">
        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Sifra</label>
        <input v-model="sifra" type="sifra" class="form-control" id="exampleInputPassword1" placeholder="Sifra">
      </div>
      <button type="submit" class="btn btn-primary mt-2">Login</button>
    </form>
    <div v-if="errorMessage" class="alert alert-danger mt-3" role="alert">
      {{ errorMessage }}
    </div>
  </div>
</template>

<script>
import jwtDecode from 'jwt-decode';

export default {
  name: "LoginPage",
  data() {
    return {
      email: '',
      sifra: '',
      errorMessage: '',
    }
  },
  methods: {
    login() {
      console.log('Pokušaj prijave:', this.email, this.sifra);
      this.$axios.post('/api/korisnici/login', {
        email: this.email,
        sifra: this.sifra,
      }).then(response => {
        console.log('Odgovor sa servera:', response.data);

        const token = response.data.jwt;
        const decodedToken = jwtDecode(token);
        console.log('Decoded::', decodedToken);
        console.log('Decoded status::', decodedToken.status);

        if (decodedToken.status === true) {
          localStorage.setItem('jwt', token);
          console.log("JWT token je sačuvan:", token);

          const autor = localStorage.getItem('jwt');
          console.log("AUTOR U LOGIN PAGE ZA PROVERU",autor);
          this.$router.push({name: 'PocetnaPage'});
        } else {
          this.errorMessage = 'Nemate pristup CMS-u.';
          setTimeout(() => {
            this.errorMessage = '';
          }, 5000); // Poruka će nestati nakon 5 sekundi
        }
      }).catch(() => {
        this.errorMessage = 'Korisnik nije pronađen!';
        setTimeout(() => {
          this.errorMessage = '';
        }, 5000); // Poruka će nestati nakon 5 sekundi
      });
    }
  },
}
</script>

<style scoped>
.alert {
  transition: opacity 0.5s ease-out;
}
</style>
