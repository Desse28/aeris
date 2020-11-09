<template>
  <div class="text-center">
    <v-dialog
        v-model="dialog"
        persistent
        width="900"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn class="mb-2 mt-2 blue--text"
               color="rgb(255, 255, 255)"
               depressed
               v-on:click="()=>{}"
        >
          <v-icon left>mdi-view-list</v-icon> {{$t('session.label_sessions')}}
        </v-btn>
        <v-btn class="mb-2 mt-2 blue--text"
               color="rgb(255, 255, 255)"
               depressed
               v-on="on" v-bind="attrs"
               v-on:click="switchCurrentView($t('session.label_selections'), null, true)"
        >
          <v-icon left> mdi-selection-drag</v-icon> {{$t('session.label_selections')}}
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-2" v-if="IsEditView">
          {{$t('session.label_edit')}}
        </v-card-title>
        <v-card-title class="headline grey lighten-2" v-if="isSelectionView">
          {{$t('session.label_selection')}}
        </v-card-title>
        <v-card-title class="headline grey lighten-2" v-if="isSelectionsView">
          {{ $t("session.label_selections") }}
        </v-card-title>
          <AerisDatavalidationSelectionform
              v-if="isSelectionView || IsEditView"
              :session="session"
              :selection="selection"
              :currentView="currentView"
              :qualityFlags="qualityFlags"
              :notifySelection="notifySelection"
              :targetSelection="currentSelection"
              :sessionSelection="sessionSelection"
              :instrumentEndDate="instrumentEndDate"
              :instrumentStartDate="instrumentStartDate"
              :notifyDeleteSelection="notifyDeleteSelection"
              :notifyCancelPopUp="notifyCancelPopUp"
              :switchCurrentView="switchCurrentView"
          />
        <AerisDatavalidationSelectionsTable
            v-else
            :session="session"
            :selection="selection"
            :resetWorkDialog="resetWorkDialog"
            :notifySelection="notifySelection"
            :notifyCancelPopUp="notifyCancelPopUp"
            :notifyDeleteSelection="notifyDeleteSelection"
        />
        <v-divider></v-divider>
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
    qualityFlags : {
      type: Array,
      default: () => []
    },
    selection : {
      type: Object,
      default: null
    },
    notifySelection : {
      type: Function
    },
    notifyDeleteSelection : {
      type: Function,
      default : () => {}
    },
    instrumentStartDate : {
      type : String,
      default : () => ""
    },
    instrumentEndDate : {
      type : String,
      default : () => ""
    }
  },
  data() {
    return {
      dialog: false,
      currentView : "",
      sessionSelection: null,
      currentSelection : null,
      isResetSelection: false,
    }
  },
  watch: {
    selection: function () {
      let startDate = this.selection.startDate
      let endDate = this.selection.endDate
      let selections = this.session.charts[0].selections

      if(this.currentSelection === null && !this.isResetSelection) {
        this.dialog = true
        if(this.$root.isSelectionExist(selections, startDate, endDate)) {
          this.switchCurrentView(this.$t('session.label_edit'))
        } else if(startDate !== "" && endDate !== "") {
          this.switchCurrentView( this.$t('session.label_selection'))
        }
      } else {
        this.isResetSelection = false
      }
    }
  },
  computed: {
    isSelectionView: function () {
      return this.currentView === this.$t('session.label_selection')
    },
    isSelectionsView: function() {
      return this.currentView === this.$t('session.label_selections')
    },
    IsEditView: function() {
      return this.currentView === this.$t('session.label_edit')
    },
  },
  methods: {
    switchCurrentView: function(viewName) {
      this.currentView = viewName
      this.currentSelection = this.selection
    },
    resetWorkDialog : function() {
      this.currentSelection = null
      this.isResetSelection = false
    },
    notifyCancelPopUp : function (targetSelection) {
      let startDate, endDate, selections
      this.dialog = false
      if (this.currentSelection) {
        selections = this.session.charts[0].selections
        startDate = this.currentSelection.startDate
        endDate = this.currentSelection.endDate
        this.currentSelection = null

        if(this.currentView === this.$t('session.label_selection') && targetSelection) {
          this.notifyDeleteSelection()
        } else if(this.currentView === this.$t('session.label_edit') && targetSelection &&
            !this.$root.isSelectionExist(selections,targetSelection.startDate, targetSelection.endDate)) {
          this.isResetSelection = true
          this.notifySelection(startDate, endDate)
        }
      }

    }
  },
}
</script>

<style scoped>
</style>