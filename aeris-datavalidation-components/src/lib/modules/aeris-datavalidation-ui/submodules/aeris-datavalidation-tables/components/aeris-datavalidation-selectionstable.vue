<template>
  <div>
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <v-alert type="success" v-if="submittedSelections">
        {{$t('session.label_submittedSelections')}}
      </v-alert>
      <AerisDatavalidationDeleteDialog
          :dialog="dialog"
          :ok="$t('session.label_yes')"
          :cancel="$t('session.label_no')"
          :okCallBack="validateDelete"
          :cancelCallBack="cancelDelete"
          :title="$t('session.label_deletion')"
          :message="$t('session.message_delete')"
      />
      <AerisDatavalidationSelectionsDialog
          ok="continuer"
          cancel="Annuler"
          :dialog="selectionsDialog"
          :okCallBack="submitSelection"
          title="Soumission de la session"
          :cancelCallBack="cancelSubmitSelection"
          :message="$t('session.message_closeSession')"
      />
      <template>
        <v-data-table
            :headers="tableHeaders"
            :items="getSelections"
            class="elevation-1"
            item-key="name"
            v-model="selected"
            hide-default-footer
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
            height="400"
            :disabled="getSelectionsLen === 0"
        >
          <template v-slot:item.startDate="{ item }">
            <div>{{getDateGoodFormat(item.startDate)}}</div>
          </template>
          <template v-slot:item.endDate="{ item }">
            <div class="pa-4" v-if="item.endDate === null">/</div>
            <div v-else >{{getDateGoodFormat(item.endDate)}}</div>
          </template>
          <template v-slot:item.flags="{ item}">
            <div v-for="flag in item.flags"
                 :key="item.flags.indexOf(flag)"
            >
              <div>{{ flag.label}}</div>
            </div>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-icon
                small
                class="mr-2"
                @click="editSelection(item)"
            >
              mdi-pencil
            </v-icon>
            <v-icon
                small
                @click="setDeleteItem(item)"
            >
              mdi-delete
            </v-icon>
          </template>
        </v-data-table>
      </template>
      <div class="text-center pt-2" :disabled="getSelectionsLen === 0">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
      </div>
      <v-card-actions>
        <v-btn
            color="primary"
            text
            v-if="!this.isEditSelection && getSelectionsLen > 0"
            @click="alertSubmitSelection"
        >
          {{$t('session.label_submit')}}
        </v-btn>
        <v-btn
            color="primary"
            text
            @click="notifyCancelPopUp"
        >
          {{$t('session.label_close')}}
        </v-btn>
      </v-card-actions>
    </AerisDataValidationServices>
  </div>
</template>

<script>
import AerisDataValidationServices from "./../../../../aeris-datavalidation-services/components/aeris-datavalidation-services"
import AerisDatavalidationDeleteDialog from "./../../aeris-datavalidation-dialogs/components/aeris-datavalidation-deletedialog"
import AerisDatavalidationSelectionsDialog from "./../../aeris-datavalidation-dialogs/components/aeris-datavalidation-selectionsdialog"

export default {
  name: "aeris-datavalidation-selectionstable",
  components : {
    AerisDataValidationServices,
    AerisDatavalidationDeleteDialog,
    AerisDatavalidationSelectionsDialog,
  },
  props : {
    isEditSelection : {
      type : Boolean,
      default : () => false
    },
    session : {
      type : Object,
      default : () => null
    },
    selection : {
      type : Object,
      default : () => null,
    },
    notifyEditMode: {
      type : Function,
      default : ()=>{}
    },
    notifySelection : {
      type : Function,
      default : ()=>{}
    },
    notifyDeleteSelection : {
      type : Function,
      default : () => {}
    },
    notifyCancelPopUp : {
      type : Function,
      default : () => {}
    }
  },
  data () {
    return {
      page: 1,
      pageCount: 0,
      selected: [],
      dialog: false,
      callBack : null,
      itemsPerPage: 5,
      currentUrl : "",
      deleteItem: null,
      requestData: null,
      typeOfRequest: "",
      isChartSignal: true,
      selectionsDialog: false,
      submittedSelections: false,
    }
  },
  computed : {
    tableHeaders : function() {
      let headers = [
        {
          text: this.$t('session.label_startDate'),
          align: 'start',
          sortable: false,
          value: 'startDate',
        },
        {
          text: this.$t('session.label_endDate'),
          align: 'start',
          sortable: false,
          value: 'endDate',
        },
        {
          text: this.$t('session.label_flags'),
          align: 'start',
          sortable: false,
          value: 'flags',
        }
      ]

      if(this.isEditSelection)
        headers.push({text: this.$t('session.label_actions'), value: 'actions', sortable: false})

      return headers
    },
    getSelections : function () {
      if(this.session)
        return this.session.sessionSelections
      else
        return []
    },
    getSelectionsLen : function () {
      if(this.session)
        return this.session.sessionSelections.length
      else
        return 0
    }
  },
  methods: {
    editSelection (selection) {
      if(selection)
        this.notifyEditMode(selection)
    },
    setDeleteItem (selection) {
      this.dialog = true
      this.deleteItem = selection
      this.isChartSignal = false
    },
    getDateGoodFormat : function(date) {
      let timePart, datePart
      if (date) {
        timePart = this.$root.getTimeUniverselFormat(date)
        datePart = this.$root.formatDate(this.$root.getDatePikerDateFormat(date))
        return datePart + ", "+ timePart
      }
    },
    cancelDelete : function () {
      this.dialog = false
    },
    validateDelete : function () {
      this.dialog = false
      this.removeTargetSelection()
      this.updateSession()
    },
    removeTargetSelection : function () {
      let index
      if(this.deleteItem !== null) {
        index = this.session.sessionSelections.indexOf(this.deleteItem)
        this.session.sessionSelections.splice(index, 1)
      }
    },
    updateSession : function() {
      this.typeOfRequest = "PUT"
      this.requestData = this.session
      this.callBack = (selection) => {
        if(selection) {
          console.info("delete response : ", selection)
          this.notifyDeleteSelection()
        }
        this.currentUrl=""
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/update"
    },
    alertSubmitSelection : function() {
      this.selectionsDialog = true
    },
    cancelSubmitSelection : function () {
      this.selectionsDialog = false
    },
    submitSelection: function() {
      this.selectionsDialog = false
      this.typeOfRequest = "POST"
      this.requestData = this.session

      this.callBack = (selection) => {
        if(selection) {
          console.info("Submit session response : ", selection)
          this.$router.go()
        }
        this.currentUrl=""
      }

      this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/submit-session"
    },
  },
}
</script>

<style scoped>
</style>