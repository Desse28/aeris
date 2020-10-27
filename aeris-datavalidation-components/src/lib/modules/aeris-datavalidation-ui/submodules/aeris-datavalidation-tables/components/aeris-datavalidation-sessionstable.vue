<template>
  <AerisDataValidationServices
      :url="currentUrl"
      :callBack="callBack"
  >
    <div>
      <template>
        <v-data-table
            show-select
            item-key="id"
            v-model="selected"
            :items="sessions"
            :headers="tableHeaders"
            class="elevation-1"
            hide-default-footer
            :single-select="singleSelect"
        >
          <template v-slot:item.startDate="{ item }">
            <div>{{getDateGoodFormat(item.startDate)}}</div>
          </template>
          <template v-slot:item.linkedParameters="{ item}">
            <div v-for="parameter in item.linkedParameters"
                :key="item.linkedParameters.indexOf(parameter)"
            >
              <div>{{ parameter.name}}</div>
            </div>
          </template>
        </v-data-table>
      </template>
      <div class="text-center pt-2">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
      </div>
      <v-card-actions class="align-end">
        <v-btn type="submit"
               color="blue darken-1"
               :disabled="disabledContinueButton"
               text @click="continueSession"
        >
          Continue
        </v-btn>
        <v-btn type="submit"
               color="blue darken-1"
               text @click="createNewSession"
        >
          {{ $t("configuration.label_newSession") }}
        </v-btn>
      </v-card-actions>
    </div>
  </AerisDataValidationServices>
</template>

<script>
import AerisDataValidationServices from "./../../../../aeris-datavalidation-services/components/aeris-datavalidation-services"

export default {
  name: "aeris-datavalidation-selectionstable",
  props : {
    setSessions : {
      type : Function,
    },
    initNewSession : {
      type : Function,
    },
    setCurrentItem : {
      type : Function,
    },
  },
  components : {
    AerisDataValidationServices,
  },
  data () {
    return {
      singleSelect: true,
      selected: [],
      sessions: [],
      page: 1,
      pageCount: 0,
      itemsPerPage: 5,
      currentUrl : "",
      callBack : null,
    }
  },
  watch : {
    page : function () {
      this.switchTablecurrentPage()
    },
  },
  computed : {
    disabledContinueButton : function() {
      return  this.selected.length === 0 || this.selected[0].state
    },
    tableHeaders : function() {
      return [
        {
          text: this.$t('configuration.label_creationDate'),
          align: 'start',
          sortable: false,
          value: 'startDate',
        },
        {
          text: this.$t('configuration.label_instrumentName'),
          align: 'start',
          sortable: false,
          value: 'instrumentName',
        },
        {
          text: this.$t('configuration.label_mainParameter'),
          align: 'start',
          sortable: false,
          value: 'mainParameter.name',
        },
        {
          text: this.$t('configuration.label_linkedParameters'),
          align: 'start',
          sortable: false,
          value: 'linkedParameters',
        }
      ]
    },
  },
  mounted() {
    this.switchTablecurrentPage()
  },
  methods: {
    switchTablecurrentPage : function() {
      this.callBack = ((currentPageData) => {
        if(currentPageData) {
          this.page = currentPageData.number
          this.sessions = currentPageData.content
          this.pageCount = currentPageData.totalPages
          this.setSessions(this.sessions)
        }
      })
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions?page=" + this.page + "&size=" + this.itemsPerPage
    },
    continueSession : function() {
      let session = this.selected[0]
      let instrumentName = session.instrumentName

      this.callBack = (instrument) => {
        if(instrument) {
          this.currentUrl = ""
          this.getInstrumentInfos(instrument['uuid'], (infos) => {
            this.initNewSession(session, instrument, infos)
          })
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/instruments?name=" + instrumentName
    },
    getInstrumentInfos : function (uuid, callBack) {
      this.callBack = callBack
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/instruments/infos/" + uuid
    },
    getDateGoodFormat : function(date) {
      let timePart, datePart
      if (date) {
        timePart = this.$root.getTimeUniverselFormat(date)
        datePart = this.$root.formatDate(this.$root.getDatePikerDateFormat(date))
        return datePart + ", "+ timePart
      }
    },
    createNewSession : function() {
      this.setCurrentItem('configuration.label_newSession')
    },
  },
}
</script>
<style scoped>
</style>