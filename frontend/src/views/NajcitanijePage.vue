<template>
  <div>
    <SviClanci :title="'Najcitaniji clanci'" :clanci="clanci" @clanci-updated="refreshClanci" />
  </div>
</template>

<script>
import SviClanci from '@/components/Clanci.vue';

export default {
  components: {SviClanci},
  data() {
    return {
      clanci: []
    };
  },
  created() {
    this.refreshClanci();
  },

  methods: {
    refreshClanci() {
      // Osvježi članke nakon promene
      this.$axios.get('/api/clanci/najcitanije').then((response) => {
        this.clanci = response.data;
      }).catch((error) => {
        console.error('Error fetching articles:', error);
      });
    }
  }
}




</script>
