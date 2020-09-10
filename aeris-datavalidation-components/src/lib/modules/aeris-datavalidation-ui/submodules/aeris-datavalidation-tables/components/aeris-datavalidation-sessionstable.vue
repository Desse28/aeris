<template>
  <AerisDataValidationServices
      :url="currentUrl"
      :callBack="callBack"
  >
    <div>
      <template>
        <v-data-table
            show-select
            item-key="startDate"
            v-model="selected"
            :page.sync="page"
            :items="sessions"
            :headers="headers"
            class="elevation-1"
            hide-default-footer
            :single-select="singleSelect"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
        >
          <template v-slot:item.startDate="{ item }">
            <div>{{getDateGoodFormat(item.startDate)}}</div>
          </template>
          <template v-slot:item.endDate="{ item }">
            <div class="pa-4" v-if="item.endDate === null">/</div>
            <div v-else >{{getDateGoodFormat(item.endDate)}}</div>
          </template>
          <template v-slot:item.linkedParameters="{ item}">
            <div
                v-for="parameter in item.linkedParameters"
                :key="item.linkedParameters.indexOf(parameter)"
            >
              <div>{{ parameter.name}}</div>
            </div>
          </template>
          <template v-slot:item.state="{ item }">
            <h3 class="mr-2 green accent-2" v-if="!item.state"> In progress</h3>
            <h3 v-else class="red accent-3">Close</h3>
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
            :disabled="continueButtonState"
            text @click="continueSession"
        >
          Continue
        </v-btn>
        <v-btn
            type="submit"
            color="blue darken-1"
            text @click="createNewSession"
        >
          Create new session
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
    setDialogue : {
      type : Function,
    },
    setCurrentItem : {
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
      console.log("Test selected : ", sessionObj)
      /* let sessionId
      if(sessionObj[0]) {
        sessionId = sessionObj[0]._id['$oid']
        this.currentSessionId = sessionId
      }*/
      this.continueButtonState = this.selected.length === 0
    }
  },
  data () {
    return {
      singleSelect: true,
      selected: [],
      headers: [
        {
          text: 'Start date',
          align: 'startDate',
          sortable: false,
          value: 'startDate',
        },
        {
          text: 'End date',
          align: 'start',
          sortable: false,
          value: 'endDate',
        },
        {
          text: 'Instrument name',
          align: 'start',
          sortable: false,
          value: 'instrumentName',
        },
        {
          text: 'Main parameter',
          align: 'start',
          sortable: false,
          value: 'mainParameter.name',
        },
        {
          text: 'Linked parameters',
          align: 'start',
          sortable: false,
          value: 'linkedParameters',
        },
        { text: 'State', value: 'state', align: 'center',},
      ],
      sessions: [],
      page: 1,
      pageCount: 0,
      itemsPerPage: 5,
      currentUrl : "",
      currentSessionId : null,
      callBack : null,
      continueButtonState : true,
    }
  },
  mounted() {
    this.callBack = ((data) => {
      this.sessions = data
    })
    this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions"
    this.continueButtonState = this.sessions.length === 0
  },
  methods: {
    createNewSession : function() {
      this.setCurrentItem("New session")
    },
    continueSession : function() {
      this.setCurrentSessionId(this.currentSessionId)
      this.setDialogue()
    },
    completeNumber : function(number) {
      let result = Math.floor(number / 10) <= 0 ? "0" + number : number
      return result
    },
    getDateGoodFormat : function(date) {
      let timePart, datePart
      if (date) {
        timePart = this.$root.getTimePickerTimeFormat(date)
        datePart = this.$root.getDatePikerDateFormat(date, "fr")
        return datePart + ", "+ timePart
      }
    },
  },
}
</script>
<style scoped>
</style>