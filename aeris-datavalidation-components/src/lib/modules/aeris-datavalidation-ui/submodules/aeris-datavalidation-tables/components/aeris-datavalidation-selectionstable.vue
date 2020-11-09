<template>
  <div>
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <AerisDatavalidationDeleteDialog
          :dialog="dialog"
          :ok="$t('session.label_yes')"
          :cancel="$t('session.label_no')"
          :okCallBack="validateDelete"
          :cancelCallBack="cancelDelete"
          :title="$t('session.label_deletion')"
          :message="$t('session.message_delete')"
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

export default {
  name: "aeris-datavalidation-selectionstable",
  components : {
    AerisDataValidationServices,
    AerisDatavalidationDeleteDialog,
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
        },
        {text: this.$t('session.label_actions'), value: 'actions', sortable: false}
      ]

      if(this.isEditSelection)
        headers.push({text: this.$t('session.label_actions'), value: 'actions', sortable: false})

      return headers
    },
    getSelections : function () {
      if(this.session)
        return this.session.charts[0].selections
      else
        return []
    },
    getSelectionsLen : function () {
      let mainChart, selectionsLen = 0
      if(this.session.charts) {
        mainChart = this.session.charts[0]
        selectionsLen = mainChart.selections.length
      }
      return selectionsLen
    }
  },
  methods: {
    editSelection (selection) {
      const startDate = this.$root.getCleanDate(selection.startDate)
      const endDate = this.$root.getCleanDate(selection.endDate)
      this.notifySelection(startDate, endDate)
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
      this.notifyDeleteSelection()
    },
  },
}
</script>

<style scoped>
</style>