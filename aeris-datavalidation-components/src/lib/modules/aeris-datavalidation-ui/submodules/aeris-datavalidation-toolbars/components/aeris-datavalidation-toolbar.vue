<template>
  <v-card
      color="grey lighten-4"
      flat
      tile
  >
    <v-toolbar
        :dense="getDenseState"
        :prominent="getProminentState"
        :class="getClassState"
    >
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
          :session="session"
          :removeChart="removeChart"
          :addParameter="addParameter"
          :removeParameter="removeParameter"
          :chartTabsHandler="chartTabsHandler"
          :currentSecondChart="currentSecondChart"
          :switchParameterChart="switchParameterChart"
          :secondChartsParameters="secondChartsParameters"
      />
    </v-toolbar>
  </v-card>
</template>
<script>

import AerisDataValidationOptions from "../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-options/components/aeris-datavalidation-option"
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
    chartTabsHandler : {
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
    secondChartsParameters : {
      type : Array
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
    currentSecondChart : {
      type : String,
      default : () => ""
    },
  },
  components: {
    AerisDataValidationOptions,
    AerisDatavalidationWorksFlowDialog,
  },
  computed : {
    getDenseState : function() {
      switch (this.$vuetify.breakpoint.name) {
        case 'xs': return false
        case 'sm': return false
          //case 'md': return 500
          //case 'lg': return 600
          //case 'xl': return 800
        default : return true
      }
    },
    getProminentState : function() {
      switch (this.$vuetify.breakpoint.name) {
        case 'xs': return true
        case 'sm': return true
          //case 'md': return 500
          //case 'lg': return 600
          //case 'xl': return 800
        default : return false
      }
    },
    getClassState : function() {
      const state = "d-flex justify-center ml-3 mt-2 mb-0 mr-1 overflow-y-auto"

      switch (this.$vuetify.breakpoint.name) {
        case 'xs': return  state
        case 'sm': return state
          //case 'md': return 500
          //case 'lg': return 600
          //case 'xl': return 800
        default : return "d-flex justify-center ml-3 mt-2 mb-0 mr-1"
      }
    }
  }
}
</script>

<style scoped>
</style>