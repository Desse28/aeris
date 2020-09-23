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
               v-on:click="switchCurrentView($t('session.selections'))"
        >
          <v-icon left>mdi-send-check-outline</v-icon> {{ $t("session.selections") }}
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-2" v-if="currentViewIsEdit">
          {{$t('session.edit')}}
        </v-card-title>
        <v-card-title class="headline grey lighten-2" v-if="currentViewIsSelection">
          {{$t('worksFlow.view_selection')}}
        </v-card-title>
        <v-card-title class="headline grey lighten-2" v-if="currentViewIsSelections">
          {{ $t("session.selections") }}
        </v-card-title>
          <AerisDatavalidationSelectionform
              v-if="currentViewIsSelection || currentViewIsEdit"
              :session="session"
              :selection="selection"
              :qualityFlags="qualityFlags"
              :notifySelection="notifySelection"
              :isSelectionMode="isSelectionMode"
              :sessionSelection="sessionSelection"
          />
        <AerisDatavalidationSelectionsTable
            v-else
            :notifyEditMode="activeEditMode"
            :selections="session.sessionSelections"
        />
        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              text
              @click="switchCurrentView($t('session.selections'))"
              v-if="currentViewIsSelection || currentViewIsEdit"
          >
            {{$t('session.selections')}}
          </v-btn>
          <v-btn
              color="primary"
              text
              @click="submitSelection"
              v-else
          >
            {{$t('session.submit_selections')}}
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
  data() {
    return {
      dialog: false,
      isSelectionMode: true,
      sessionSelection: null,
      currentView : this.$t('worksFlow.view_selection')
    }
  },
  watch : {
    selection: function () {
      if(this.selection && this.selection.startDate !== "" && this.selection.endDate !== "") {
        this.dialog = true
        if(this.$root.isSelectionExist( this.session, this.selection.startDate, this.selection.endDate)) {
          this.activeEditMode(this.$root.getTargetSelection(this.session, this.selection.startDate, this.selection.endDate))
        } else {
          this.activeSelectionMode()
        }
      }
    }
  },
  computed : {
    currentViewIsSelection : function () {
      return this.currentView === this.$t('worksFlow.view_selection')
    },
    currentViewIsSelections : function() {
      return this.currentView === this.$t('session.selections')
    },
    currentViewIsEdit: function() {
      return this.currentView === this.$t('session.edit')
    }
  },
  methods : {
    activeEditMode : function(selection) {
      this.isSelectionMode = false
      this.switchCurrentView(this.$t('session.edit'))
      this.sessionSelection = selection
    },
    activeSelectionMode : function() {
      this.isSelectionMode = true;
      this.switchCurrentView( this.$t('worksFlow.view_selection'))
    },
    switchCurrentView : function(viewName) {
      if(viewName) {
        this.currentView = viewName
        this.dialog = true
      }
    },
    submitSelection : function() {
      //Here submit all selections
    }
  },
}
</script>

<style scoped>
</style>