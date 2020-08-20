<template>
  <div>
    <AerisDatavalidationPortraitLayaout
        :padding="padding"
        :justify="justify"
        :nbrChildElement="2"
    >
      <template v-slot:portrait1>
        <AerisDatavalidationChart/>
      </template>
      <template v-slot:portrait2>
        <AerisDatavalidationSelection/>
      </template>
    </AerisDatavalidationPortraitLayaout>
    <AerisDatavalidationLandScapeLayaout
        :nbrChildElement="2"
    >
      <template v-slot:land1>
        <AerisDatavalidationChart/>
      </template>
      <template v-slot:land2>
        <AerisDatavalidationChart/>
      </template>
    </AerisDatavalidationLandScapeLayaout>
  </div>
</template>
<script>
import {
  AerisDatavalidationChart,
  AerisDatavalidationSelection,
  AerisDatavalidationPortraitLayaout,
  AerisDatavalidationLandScapeLayaout,
} from "./../../aeris-datavalidation-components"

export default {
  name: "aeris-datavalidation-homepage",
  components: {
    AerisDatavalidationChart,
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