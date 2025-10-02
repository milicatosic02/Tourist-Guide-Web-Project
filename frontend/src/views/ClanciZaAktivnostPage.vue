<template>
  <div>
    <SviClanci :title="'#' + aktivnost" :clanci="clanci" />
  </div>
</template>

<script>
import SviClanci from '@/components/Clanci.vue';

export default {
  components: {SviClanci},
  data() {
    return {
      clanci: [],
      aktivnost:''
    };
  },
  created() {
    this.pronadjiClankeZaAktivnost();
    this.pronadjiImeAktivnosti();
  },
  watch: {
    '$route.params.id': function() {
      this.pronadjiClankeZaAktivnost();
      this.pronadjiImeAktivnosti();
    }
  },
  methods: {
    pronadjiClankeZaAktivnost() {
      const id = this.$route.params.id;
      this.$axios.get(`/api/clanci/aktFilter/${id}`)
          .then(response => {
            this.clanci = response.data;
          })
          .catch(error => {
            console.error('Error fetching articles for destination:', error);
          });
    },
    pronadjiImeAktivnosti() {
      const id = this.$route.params.id;
      this.$axios.get(`/api/aktivnosti/${id}`)
          .then(response => {
            this.aktivnost = response.data.naziv;
          })
          .catch(error => {
            console.error('Error fetching aktivnost ime:', error);
          });
    }
  }
}
</script>
