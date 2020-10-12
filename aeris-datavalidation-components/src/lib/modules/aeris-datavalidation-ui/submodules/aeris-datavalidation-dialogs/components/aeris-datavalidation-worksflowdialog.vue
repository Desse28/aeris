<template>
  <div class="text-center">
    <v-dialog
        v-model="dialog"
        persistent
        width="900"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn class="ma-2"
               tile
               outlined
               color="blue"
               v-on="on" v-bind="attrs"
               v-on:click="refreshCurrentWindow"
        >
          <v-icon left>mdi-view-list</v-icon> Sessions
        </v-btn>
        <v-btn class="ma-2"
               tile
               outlined
               color="blue"
               v-on="on" v-bind="attrs"
               v-on:click="switchCurrentView($t('session.label_selections'), null, false)"
        >
          <v-icon left>mdi-send-check-outline</v-icon> {{ $t("session.label_selections") }}
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
              :isSelectionMode="isSelectionMode"
              :sessionSelection="sessionSelection"
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

        <v-card-actions
            v-if="currentViewIsEdit"
        >
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              text
              @click="switchCurrentView($t('session.label_selections'), null, true)"
          >
            {{$t('session.label_selections')}}
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
    isDeleteMode: {
      type: Boolean,
      default: () => false
    },
    notifySelection: {
      type: Function,
      default: () => {}
    },
    notifyDeleteSelection: {
      type: Function,
      default : () => {}
    },
  },
  data() {
    return {
      dialog: false,
      isSelectionMode: true,
      sessionSelection: null,
      isResetSelection: false,
      isEditSelection : false,
      currentView : this.$t('session.label_selection')
    }
  },
  watch: {
    dialog: {
      immediate: true,
      handler() {
        let startDate, endDate

        if(!this.dialog) {
          if(this.sessionSelection && this.currentView === this.$t('session.label_edit')) {
            startDate = this.$root.takeOfDateMilliseconds(this.sessionSelection.startDate)
            endDate = this.$root.takeOfDateMilliseconds(this.sessionSelection.endDate)
            if(this.$root.isSelectionExist(this.session, startDate, endDate)) {
              this.isResetSelection = true
              this.notifySelection(startDate, endDate)
            }
          }
          this.currentView = this.$t('session.label_selections')
        }
      },
    },
    selection: function () {
      let targetSelection
      let startDate = this.selection.startDate
      let endDate = this.selection.endDate

      if(!this.isResetSelection/* && !this.isDeleteMode*/) {
        this.dialog = true

        if(this.selection && startDate !== "" && endDate !== "") {

          if(this.currentView === this.$t('session.label_edit'))
            return

          if(this.$root.isSelectionExist(this.session, startDate, endDate)) {
            targetSelection = this.$root.getTargetSelection(this.session, startDate, endDate)
            this.activeEditMode(targetSelection)
          } else {
            this.activeSelectionMode()
          }
        }

      } else {
        this.isResetSelection = false
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
      this.isSelectionMode = false
      this.switchCurrentView(this.$t('session.label_edit'), selection)
    },
    activeSelectionMode: function() {
      this.isSelectionMode = true
      this.switchCurrentView( this.$t('session.label_selection'))
    },
    switchCurrentView: function(viewName, selection, isEditSelection) {
      let startDate, endDate

      if(viewName && (this.selection || selection)) {
        this.currentView = viewName
        startDate = selection ? selection.startDate : this.selection.startDate
        endDate = selection ? selection.endDate : this.selection.endDate
        this.sessionSelection = this.$root.getTargetSelection(this.session, startDate, endDate)
        this.dialog = true
      }

      if( this.currentView !== this.$t('session.label_selection'))
        this.isSelectionMode = false

      this.isEditSelection = isEditSelection ?? undefined
    },
    refreshCurrentWindow : function () {
      this.$router.go()
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