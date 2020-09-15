<template>
  <v-card
      color="grey lighten-4"
      flat
      tile
  >
    <v-toolbar dense class="d-flex justify-center">
      <AerisDatavalidationWorksFlowDialog/>
      <AerisDataValidationOptions
          :parametersLabel="parametersLabel"
          :parallelsLabel="parallelsLabel"
          :addNewParallel="addNewParallel"
          :removeParallel="removeParallel"
          :addNewParameter="addNewParameter"
          :removeParameter="removeParameter"
      />
    </v-toolbar>
  </v-card>
</template>
<script>
import {
  AerisDataValidationOptions,
} from "@/lib/modules/aeris-datavalidation-components";

import AerisDatavalidationWorksFlowDialog from "./../../aeris-datavalidation-dialogs/components/aeris-datavalidation-worksflowdialog"

export default {
  name: "aeris-datavalidation-simpletoolbar",
  props: {
    addNewParameter : {
      type : Function,
    },
    addNewParallel : {
      type : Function,
    },
    removeParallel : {
      type : Function,
    },
    removeParameter: {
      type : Function,
    },
    parametersLabel : {
      type : Array,
      default : () => [],
    },
    parallelsLabel : {
      type : Array,
      default : () => [],
    },
  },
  components: {
    AerisDataValidationOptions,
    AerisDatavalidationWorksFlowDialog,
  },
}
</script>

<style scoped>
</style>