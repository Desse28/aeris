<template>
  <div class="text-center">
    <v-dialog
        v-model="dialog"
        width="900"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn class="ma-2"
               tile
               outlined
               color="blue"
               v-on="on" v-bind="attrs"
               v-on:click="switchCurrentView($t('worksFlow.validation'))"
        >
          <v-icon left>mdi-send-check-outline</v-icon> {{ $t("worksFlow.validation") }}
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-2" v-if="currentViewIsSelection">
          {{$t('worksFlow.view_selection')}}
        </v-card-title>
        <v-card-title class="headline grey lighten-2" v-else>
          Validation
        </v-card-title>
          <AerisDatavalidationSelectionform
              v-if="currentViewIsSelection"
              :session="session"
              :selection="selection"
              :qualityFlags="qualityFlags"
              :notifySelection="notifySelection"
          />
        <AerisDatavalidationSelectionsTable  v-else />
        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              text
              @click="switchCurrentView($t('worksFlow.validation'))"
              v-if="currentViewIsSelection"
          >
            {{$t('worksFlow.validation')}}
          </v-btn>
          <v-btn
              color="primary"
              text
              @click="switchCurrentView($t('worksFlow.view_selection'))"
              v-else
          >
            {{$t('worksFlow.view_selection')}}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import AerisDatavalidationSelectionform from "./../../aeris-datavalidation-form/components/aeris-datavalidation-selectionform"
import AerisDatavalidationSelectionsTable from "./../../aeris-datavalidation-tables/components/aeris-datavalidation-selectionstable"
export default {
  name: "aeris-datavalidation-worksflowdialog",
  components : {
    AerisDatavalidationSelectionform,
    AerisDatavalidationSelectionsTable
  },
  props: {
    session : {
      type: Object,
      default: null
    },
    qualityFlags : {
      type: Array,
      default: () => []
    },
    selection : {
      type: Object,
      default: null
    },
    notifySelection : {
      type: Function,
      default: () => {}
    }
  },
  watch : {
    selection: function () {
      if(this.selection && this.selection.startDate !== "" && this.selection.endDate !== "") {
        this.switchCurrentView( this.$t('worksFlow.view_selection'))
        this.dialog = true
      }
    }
  },
  data() {
    return {
      dialog: false,
      currentView : this.$t('worksFlow.view_selection')
    }
  },
  computed : {
    currentViewIsSelection : function () {
      return this.currentView === this.$t('worksFlow.view_selection')
    }
  },
  methods : {
    switchCurrentView : function(viewName) {
      if(viewName) {
        this.currentView = viewName
        this.dialog = true
      }
    }
  },
}
</script>

<style scoped>
</style>