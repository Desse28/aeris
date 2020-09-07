<template>
  <AerisDataValidationServices
      :url="currentUrl"
      :callBack="callBack"
  >
    <div>
      <template>
        <v-data-table
            show-select
            height="290"
            item-key="name"
            :items="sessions"
            :page.sync="page"
            v-model="selected"
            :headers="headers"
            class="elevation-1"
            hide-default-footer
            :single-select="singleSelect"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
        >
          <template v-slot:item.state="{ item }">
            <h3
                class="mr-2 green accent-2"
                v-if="!item.state"
            >
              In progress
            </h3>
            <h3
                v-else
                class="red accent-3"
            >
              Close
            </h3>
          </template>
        </v-data-table>
      </template>
      <div class="text-center pt-2">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
      </div>
      <v-card-actions>
        <v-btn
            type="submit"
            color="blue darken-1"
            :disabled="sessions.length === 0 || selected === null"
            text @click="continueSession"
        >
          Continue
        </v-btn>
      </v-card-actions>
    </div>
  </AerisDataValidationServices>
</template>

<script>
const baseUrl = "http://localhost:9001";
import AerisDataValidationServices from "./../../../../aeris-datavalidation-services/components/aeris-datavalidation-services"
export default {
  name: "aeris-datavalidation-selectionstable",
  props : {
    setDialogue : {
      type : Function,
    },
    setCurrentSessionId : {
      type : Function,
    },
  },
  components : {
    AerisDataValidationServices,
  },
  watch : {
    selected : function (sessionObj) {
      let sessionId = sessionObj[0]._id['$oid']
      this.currentSessionId = sessionId
    }
  },
  data () {
    return {
      singleSelect: true,
      selected: null,
      headers: [
        {
          text: 'Session',
          align: 'start',
          sortable: false,
          value: 'name',
        },
        { text: 'Creation date', value: 'startDate.$date' },
        { text: 'State', value: 'state', align: 'center',},
      ],
      sessions: [],
      page: 1,
      pageCount: 0,
      itemsPerPage: 5,
      currentUrl : "",
      currentSessionId : null,
      callBack : this.initSessionsTable,
    }
  },
  mounted() {
    this.currentUrl = baseUrl + "/sessions/ids"
  },
  methods: {
    continueSession : function() {
      this.setCurrentSessionId(this.currentSessionId)
      this.setDialogue()
    },
    completeNumber : function(number) {
      let result = Math.floor(number / 10) <= 0 ? "0" + number : number
      return result
    },
    getDateGoodFormat : function(currentDate) {
      let day = this.completeNumber(currentDate.getUTCDate())
      let month = this.completeNumber(currentDate.getUTCMonth()+1)
      let year = currentDate.getUTCFullYear()
      return day + "-" + month + "-" + year
    },
    initSessionsTable : function (data) {
      let session
      if(data) {
        data.forEach((sessionObj, index) => {
          session = JSON.parse(sessionObj)
          session.name = "Session" + (index+1)
          session.startDate.$date = this.getDateGoodFormat(new Date(session.startDate.$date))
          this.sessions.push(session)
        })
      }
    }
  },
}
</script>

<style scoped>
</style>