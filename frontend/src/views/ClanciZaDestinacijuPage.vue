<template>
  <div>
    <SviClanci :title="destinacijaIme" :clanci="clanci" />
  </div>
</template>

<script>
import SviClanci from '@/components/Clanci.vue';

export default {
  components: {SviClanci},
  data() {
    return {
      clanci: [],
      destinacijaIme:''
    };
  },
  created() {
    this.pronadjiClankeZaDestinaciju();
    this.pronadjiImeDestinacije();
  },
  watch: {
    '$route.params.id': function() {
      this.pronadjiClankeZaDestinaciju();
      this.pronadjiImeDestinacije();
    }
  },
  methods: {
    pronadjiClankeZaDestinaciju() {
      const id = this.$route.params.id;
      this.$axios.get(`/api/clanci/destFilter/${id}`)
          .then(response => {
            this.clanci = response.data;
            console.log(response.data)
          })
          .catch(error => {
            console.error('Error fetching articles for destination:', error);
          });
    },
    pronadjiImeDestinacije() {
      const id = this.$route.params.id;
      this.$axios.get(`/api/destinacije/${id}`)
          .then(response => {
            this.destinacijaIme = response.data.ime;
          })
          .catch(error => {
            console.error('Error fetching destination name:', error);
          });
    }
  }
}
</script>
