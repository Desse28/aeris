<template>
  <v-card
      color="grey lighten-4"
      flat
      tile
  >
    <v-toolbar dense class="d-flex justify-center">
      <AerisDataValidationOptions/>
    </v-toolbar>
  </v-card>
</template>
<script>
import {
  AerisDataValidationOptions,
} from "@/lib/modules/aeris-datavalidation-components";

export default {
  name: "aeris-datavalidation-simpletoolbar",
  components: {
    AerisDataValidationOptions
  },
  data() {
    return {
    }
  },
}
</script>

<style scoped>
</style>