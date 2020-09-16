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
               v-on="on"
               v-bind="attrs"
               v-on:click="setCurrentWorkflow('selection')"
        >
          <v-icon left>mdi-selection-drag</v-icon>{{ $t("worksFlow.view_selection") }}
        </v-btn>
        <v-btn class="ma-2"
               tile
               outlined
               color="blue"
               v-on="on" v-bind="attrs"
               v-on:click="setCurrentWorkflow('validation')"
        >
          <v-icon left>mdi-send-check-outline</v-icon> {{ $t("worksFlow.validation") }}
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-2" v-if="isSelectionCurrentView">
          Selection
        </v-card-title>
        <v-card-title class="headline grey lighten-2" v-else>
          Validation
        </v-card-title>
          <AerisDatavalidationSelectionform v-if="isSelectionCurrentView"/>
          <div v-else>
            <AerisDatavalidationSelectionsTable/>
            <AerisDatavalidationSelectionform
                :session="session"
                :currentSelection="currentSelection"
                :notifyNewSelection="notifyNewSelection"
            />
          </div>
        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              text
              @click="dialog = false"
              v-if="isSelectionCurrentView"
          >
            Validation
          </v-btn>
          <v-btn
              color="primary"
              text
              @click="dialog = false"
              v-else
          >
            Selection
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
  props : {
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
    }
  },
  data() {
    return {
      dialog: false,
      currentWorkFlow : this.$t('worksFlow.view_selection')
    }
  },
  computed : {
    isSelectionCurrentView : function () {
      return this.currentWorkFlow === this.$t('worksFlow.view_selection')
    }
  },
  methods : {
    setCurrentWorkflow : function (targetButton) {
      let validation = this.$t('worksFlow.validation')
      let selection = this.$t('worksFlow.view_selection')
      this.currentWorkFlow = targetButton === "selection" ? selection : validation
    }
  },
}
</script>

<style scoped>
</style>