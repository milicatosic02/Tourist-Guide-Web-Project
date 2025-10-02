
<template>
  <div class="container">
    <!-- Kreiraj članak button -->
    <button v-if="jwt" @click="kreirajClanak" class="kreiraj-dugme">Kreiraj članak</button>

    <!-- List of articles -->
    <svi-clanci :title="''" :clanci="clanci" @clanci-updated="refreshClanci" />
  </div>
</template>
<script >
import SviClanci from '@/components/Clanci.vue';
export default {
  components: {SviClanci},
  data() {
    return {
      clanci: [],
      jwt: null
    };
  },
  created() {
    this.jwt = localStorage.getItem('jwt');
    this.refreshClanci();
  },
  methods: {
    kreirajClanak() {
      this.$router.push({name: 'KreirajClanakPage'});
    },
    refreshClanci() {
      // Osvježi članke nakon promene
      this.$axios.get('/api/clanci').then((response) => {
        this.clanci = response.data;
      }).catch((error) => {
        console.error('Error fetching articles:', error);
      });
    }
  }
};
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
  margin: 30px 5px; /* Smanjite marginu na 10px gore i dole */
  cursor: pointer;
  border-radius: 5px;
}
</style>