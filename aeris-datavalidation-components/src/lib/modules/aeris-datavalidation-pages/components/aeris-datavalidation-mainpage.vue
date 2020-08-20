<template>
  <div>
    <AerisDataValidationOptions/>
    <AerisDatavalidationPortraitLayaout
        padding="pa-8"
        justify="center"
        :nbrChildElement="2"
        :cols="[12, 12]"
    >
      <template v-slot:portrait1>
        <AerisDatavalidationChart/>
      </template>
      <template v-slot:portrait2>
        <AerisDatavalidationSelection/>
      </template>
    </AerisDatavalidationPortraitLayaout>
    <AerisDatavalidationLandScapeLayaout
        justify="center"
        padding="pa-8"
        :cols="[7, 5]"
        :nbrChildElement="2"
    >
      <template v-slot:land1>
        <AerisDatavalidationPortraitLayaout
            padding="pa-8"
            justify="center"
            :nbrChildElement="2"
            :cols="[12, 12]"
        >
          <template v-slot:portrait1>
            <AerisDatavalidationChart/>
          </template>
          <template v-slot:portrait2>
            <AerisDatavalidationSelection/>
          </template>
        </AerisDatavalidationPortraitLayaout>
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
  AerisDataValidationOptions,
  AerisDatavalidationSelection,
  AerisDatavalidationPortraitLayaout,
  AerisDatavalidationLandScapeLayaout,
} from "./../../aeris-datavalidation-components"

export default {
  name: "aeris-datavalidation-homepage",
  components: {
    AerisDatavalidationChart,
    AerisDataValidationOptions,
    AerisDatavalidationSelection,
    AerisDatavalidationPortraitLayaout,
    AerisDatavalidationLandScapeLayaout,
  },
  data() {
    return {
    }
  },
}
</script>
<style scoped>
</style>