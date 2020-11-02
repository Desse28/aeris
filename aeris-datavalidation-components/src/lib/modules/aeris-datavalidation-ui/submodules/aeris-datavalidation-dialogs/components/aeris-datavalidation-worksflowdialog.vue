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
        <v-btn class="mb-2 mt-2 blue--text"
               color="rgb(255, 255, 255)"
               depressed
               v-on="on" v-bind="attrs"
               v-on:click="switchCurrentView($t('session.label_selections'), null, false)"
        >
          <v-icon left>mdi-send-outline</v-icon> {{$t('session.label_sendMySession')}}
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-2" v-if="currentViewIsEdit">
          {{$t('session.label_edit')}}
        </v-card-title>
        <v-card-title class="headline grey lighten-2" v-if="currentViewIsSelection">
          {{$t('session.label_selection')}}
        </v-card-title>
        <v-card-title class="headline grey lighten-2" v-if="currentViewIsSelections">
          {{ $t("session.label_selections") }}
        </v-card-title>
          <AerisDatavalidationSelectionform
              v-if="currentViewIsSelection || currentViewIsEdit"
              :session="session"
              :selection="selection"
              :qualityFlags="qualityFlags"
              :notifySelection="notifySelection"
              :sessionSelection="sessionSelection"
              :instrumentEndDate="instrumentEndDate"
              :instrumentStartDate="instrumentStartDate"
              :notifyDeleteSelection="notifyDeleteSelection"
              :notifyCancelPopUp="notifyCancelPopUp"
              :switchCurrentView="switchCurrentView"
          />
        <AerisDatavalidationSelectionsTable
            v-else
            :notifyEditMode="activeEditMode"
            :session="session"
            :selection="selection"
            :isEditSelection="isEditSelection"
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
      type: Function,
      default: () => {}
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
      sessionSelection: null,
      isResetSelection: false,
      isEditSelection : false,
      currentView : this.$t('session.label_selection')
    }
  },
  watch: {
    selection: function () {
      let selection
      let startDate = this.selection.startDate
      let endDate = this.selection.endDate
      let selections = this.session.charts[0].selections

      this.dialog = true

      if(this.$root.isSelectionExist(selections, startDate, endDate)) {
        selection = this.$root.getTargetSelection(selections, startDate, endDate)
        this.activeEditMode(selection)
      } else if(startDate !== "" && endDate !== "") {
            this.activeSelectionMode()
      } else {
        this.dialog = false
      }
    }
  },
  computed: {
    currentViewIsSelection: function () {
      return this.currentView === this.$t('session.label_selection')
    },
    currentViewIsSelections: function() {
      return this.currentView === this.$t('session.label_selections')
    },
    currentViewIsEdit: function() {
      return this.currentView === this.$t('session.label_edit')
    }
  },
  methods: {
    activeEditMode: function(selection) {
      this.switchCurrentView(this.$t('session.label_edit'), selection)
    },
    activeSelectionMode: function() {
      this.switchCurrentView( this.$t('session.label_selection'))
    },
    switchCurrentView: function(viewName, selection) {
      let startDate, endDate
      let selections = this.session.charts[0].selections

      if(viewName && (this.selection || selection)) {
        this.currentView = viewName
        startDate = selection ? selection.startDate : this.selection.startDate
        endDate = selection ? selection.endDate : this.selection.endDate
        this.sessionSelection = this.$root.getTargetSelection(selections, startDate, endDate)
        this.dialog = true
      }
    },
    notifyCancelPopUp : function () {
      this.dialog = false
      if(this.currentView === this.$t('session.label_selection'))
        this.notifyDeleteSelection()
    }
  },
}
</script>

<style scoped>
</style>