<template>
  <div style="max-height: 70px; max-width: 90px">
      <v-select
          v-model="$i18n.locale"
          :items="langs"
          prepend-icon="mdi-translate"
      />
  </div>
</template>

<script>
export default {
  name: "aeris-datavalidation-langswitcher",
  data () {
    return { langs: ['en', 'fr'] }
  }
}
</script>

<style scoped>

</style>