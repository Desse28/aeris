<template>
  <v-card
      color="grey lighten-4"
      flat
      tile
  >
    <v-toolbar dense class="d-flex justify-center ml-3 mt-2 mb-0 mr-1">
      <AerisDatavalidationWorksFlowDialog
          :session="session"
          :selection="selection"
          :qualityFlags="qualityFlags"
          :notifySelection="notifySelection"
          :setSessionsDialog="setSessionsDialog"
          :notifyDeleteSelection="notifyDeleteSelection"
      />
      <AerisDataValidationOptions
          :addNewChart="addNewChart"
          :removeChart="removeChart"
          :auxParameters="auxParameters"
          :addNewParallel="addNewParallel"
          :removeParallel="removeParallel"
          :addNewParameter="addNewParameter"
          :removeParameter="removeParameter"
          :linkedParameters="linkedParameters"
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
    session: {
      type: Object,
      default: null
    },
    qualityFlags: {
      type: Array,
      default: () => []
    },
    selection: {
      type: Object,
      default: null
    },
    notifyDeleteSelection: {
      type: Function,
      default : () => {}
    },
    notifySelection: {
      type: Function,
      default: () => {}
    },
    addNewParameter : {
      type : Function,
    },
    addNewChart: {
      type : Function,
    },
    addNewParallel: {
      type : Function,
    },
    removeParallel: {
      type : Function,
    },
    removeParameter: {
      type : Function,
    },
    removeChart: {
      type : Function,
    },
    linkedParameters: {
      type : Array,
      default : () => [],
    },
    auxParameters: {
      type : Array,
      default : () => [],
    },
    setSessionsDialog: {
      type : Function,
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