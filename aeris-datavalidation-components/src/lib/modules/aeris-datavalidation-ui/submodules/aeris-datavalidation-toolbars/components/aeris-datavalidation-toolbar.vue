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
          :instrumentEndDate="instrumentEndDate"
          :instrumentStartDate="instrumentStartDate"
          :notifyDeleteSelection="notifyDeleteSelection"
      />
      <v-divider
          vertical
      ></v-divider>
      <AerisDataValidationOptions
          :charts="charts"
          :addNewChart="addNewChart"
          :removeChart="removeChart"
          :addParameter="addParameter"
          :auxParameters="auxParameters"
          :removeParameter="removeParameter"
          :linkedParameters="linkedParameters"
          :currentParalelChart="currentParalelChart"
          :switchParameterChart="switchParameterChart"
      />
    </v-toolbar>
  </v-card>
</template>
<script>

import AerisDataValidationOptions from "../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-options/components/aeris-datavalidation-configuration"
import AerisDatavalidationWorksFlowDialog from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-dialogs/components/aeris-datavalidation-worksflowdialog"

export default {
  name: "aeris-datavalidation-toolbar",
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
    addNewChart: {
      type : Function,
    },
    addParameter : {
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
    instrumentStartDate : {
      type : String,
      default : () => ""
    },
    instrumentEndDate : {
      type : String,
      default : () => ""
    },
    charts : {
      type : Object,
      default : () => null
    },
    switchParameterChart : {
      type : Function,
      default : () => {}
    },
    currentParalelChart : {
      type : String,
      default : () => ""
    }
  },
  components: {
    AerisDataValidationOptions,
    AerisDatavalidationWorksFlowDialog,
  },
}
</script>

<style scoped>
</style>