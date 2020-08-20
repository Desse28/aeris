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
  </div>
</template>
<script>
import {
  AerisDatavalidationSelection,
  AerisDatavalidationPortraitLayaout,
} from "./../../aeris-datavalidation-components"

export default {
  name: "aeris-datavalidation-homepage",
  components: {
    AerisDatavalidationSelection,
    AerisDatavalidationPortraitLayaout,
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