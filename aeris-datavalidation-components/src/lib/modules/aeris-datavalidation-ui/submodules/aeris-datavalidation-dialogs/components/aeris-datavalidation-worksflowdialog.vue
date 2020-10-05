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
              :switchCurrentView="switchCurrentView"
          />
        <AerisDatavalidationSelectionsTable
            v-else
            :notifyEditMode="activeEditMode"
            :session="session"
            :selection="selection"
            :notifySelection="notifySelection"
            :notifyDeleteSelection="notifyDeleteSelection"
        />
        <v-divider></v-divider>

        <v-card-actions
            v-if="currentViewIsSelection || currentViewIsEdit"
        >
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              text
              @click="switchCurrentView($t('session.selections'))"
          >
            {{$t('session.selections')}}
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
      currentView : this.$t('worksFlow.view_selection')
    }
  },
  watch: {
    dialog: {
      immediate: true,
      handler() {
        let startDate, endDate

        if(!this.dialog) {
          if(this.sessionSelection && this.currentView === this.$t('session.edit')) {
            startDate = this.$root.takeOfDateMilliseconds(this.sessionSelection.startDate)
            endDate = this.$root.takeOfDateMilliseconds(this.sessionSelection.endDate)
            if(this.$root.isSelectionExist(this.session, startDate, endDate)) {
              this.isResetSelection = true
              this.notifySelection(startDate, endDate)
            }
          }
          this.currentView = this.$t('session.selections')
        }
      },
    },
    selection: function () {
      let targetSelection
      let startDate = this.selection.startDate
      let endDate = this.selection.endDate

      if(!this.isResetSelection && !this.isDeleteMode) {
        this.dialog = true

        if(this.selection && startDate !== "" && endDate !== "") {

          if(this.currentView === this.$t('session.edit'))
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
      return this.currentView === this.$t('worksFlow.view_selection')
    },
    currentViewIsSelections: function() {
      return this.currentView === this.$t('session.selections')
    },
    currentViewIsEdit: function() {
      return this.currentView === this.$t('session.edit')
    }
  },
  methods: {
    activeEditMode: function(selection) {
      this.isSelectionMode = false
      this.switchCurrentView(this.$t('session.edit'), selection)
    },
    activeSelectionMode: function() {
      this.isSelectionMode = true
      this.switchCurrentView( this.$t('worksFlow.view_selection'))
    },
    switchCurrentView: function(viewName, selection) {
      let startDate, endDate
      if(viewName && (this.selection || selection)) {
        this.currentView = viewName
        startDate = selection ? selection.startDate : this.selection.startDate
        endDate = selection ? selection.endDate : this.selection.endDate
        this.sessionSelection = this.$root.getTargetSelection(this.session, startDate, endDate)
        this.dialog = true
      }
      if( this.currentView !== this.$t('worksFlow.view_selection'))
        this.isSelectionMode = false
    },
  },
}
</script>

<style scoped>
</style>