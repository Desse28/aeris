<template>
  <div>
    <AerisDatavalidationPortraitLayaout
        :padding="padding"
        :justify="justify"
        :nbrChildElement="2"
    >
      <template v-slot:portrait1>
        <h1>Le titre de ma page</h1>
      </template>
      <template v-slot:portrait2>
        <AerisDatavalidationSelection/>
      </template>
    </AerisDatavalidationPortraitLayaout>
    <AerisDatavalidationLandScapeLayaout
        :nbrChildElement="2"
    >
      <template v-slot:land1>
        <h1>Le titre de ma page</h1>
      </template>
      <template v-slot:land2>
        <p>Ici les infos de contact</p>
      </template>
    </AerisDatavalidationLandScapeLayaout>
  </div>
</template>
<script>
import {
  AerisDatavalidationSelection,
  AerisDatavalidationPortraitLayaout,
  AerisDatavalidationLandScapeLayaout,
} from "./../../aeris-datavalidation-components"

export default {
  name: "aeris-datavalidation-homepage",
  components: {
    AerisDatavalidationSelection,
    AerisDatavalidationPortraitLayaout,
    AerisDatavalidationLandScapeLayaout,
  },
  data() {
    return {
      justify : "center",
      padding: "pa-8"
    }
  },
}
</script>
<style scoped>
</style>