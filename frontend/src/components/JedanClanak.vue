<template>
  <div class="clanak-container">
    <h2 class="clanak-title">{{ clanak.naslov }}</h2>
    <p class="clanak-text">{{ clanak.tekst }}</p>
    <div class="clanak-info">
      <p><strong>Autor:</strong> {{ clanak.autor }}</p>
      <p><strong>Datum:</strong> {{ formatDate(clanak.datum) }}</p>
    </div>
  </div>
</template>


<script>
export default {
  name: "JedanClanak",
  props: {
    clanak: {
      type: Object,
      required: true,
    }
  },
  methods: {
    formatDate(dateString) {
      const date = new Date(dateString);
      const day = date.getDate().toString().padStart(2, '0');
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const year = date.getFullYear();
      return `${day}.${month}.${year}`;
    }
  }
}
</script>

<style scoped>
.clanak-container {
  text-align: center; /* Center align the text */
  margin: 20px; /* Add some margin around the container */
}

.clanak-title {
  margin-top: 50px;
  margin-bottom: 20px; /* Add space between the title and the text */
  font-size: 2em; /* Increase the font size of the title */
  color: #333; /* Optional: Change the color of the title */
}

.clanak-text {
  font-size: 1.2em; /* Increase the font size of the text */
  line-height: 1.6; /* Increase line height for better readability */
  color: #333; /* Optional: Change the color of the text */
}

.clanak-info {
  margin-top: 20px; /* Add margin above the info section */
  text-align: left; /* Align info text to the left */
}

.clanak-info p {
  margin: 5px 0; /* Add margin between paragraphs */
}
</style>

<style scoped>
.clanak-container {
  text-align: center; /* Center align the text */
  margin: 20px; /* Add some margin around the container */
}

.clanak-title {
  margin-top: 50px;
  margin-bottom: 20px; /* Add space between the title and the text */
  font-size: 2em; /* Increase the font size of the title */
  color: #333; /* Optional: Change the color of the title */
}

.clanak-text {
  font-size: 1.2em; /* Increase the font size of the text */
  line-height: 1.6; /* Increase line height for better readability */
  color: #333; /* Optional: Change the color of the text */
}
</style>