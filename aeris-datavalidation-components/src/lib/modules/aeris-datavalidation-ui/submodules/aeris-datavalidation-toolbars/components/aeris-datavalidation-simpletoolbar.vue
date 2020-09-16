<template>
  <v-card
      color="grey lighten-4"
      flat
      tile
  >
    <v-toolbar dense class="d-flex justify-center">
      <AerisDatavalidationWorksFlowDialog
          :session="session"
          :currentSelection="currentSelection"
          :notifyNewSelection="notifyNewSelection"
      />
      <AerisDataValidationOptions
          :linkedParameters="linkedParameters"
          :auxParameters="auxParameters"
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
    session : {
      type: Object,
      default: null
    },
    currentSelection : {
      type: Object,
      default: null
    },
    notifyNewSelection : {
      type: Function,
      default: () => {}
    },
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
    linkedParameters: {
      type : Array,
      default : () => [],
    },
    auxParameters : {
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