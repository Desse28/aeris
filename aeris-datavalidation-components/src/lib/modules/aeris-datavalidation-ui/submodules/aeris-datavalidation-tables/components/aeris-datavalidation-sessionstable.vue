<template>
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
        <template v-slot:item.creationDate="{ item }">
          <div>{{getDateGoodFormat(item.creationDate)}}</div>
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
             text
             @click="continueTargetSession"
      >
        {{ $t("configuration.label_continue") }}
      </v-btn>
      <v-btn type="submit"
             color="blue darken-1"
             text
             @click="setCurrentView('configuration.label_newSession')"
      >
        {{ $t("configuration.label_newSession") }}
      </v-btn>
    </v-card-actions>
  </div>
</template>
<script>

export default {
  name: "aeris-datavalidation-selectionstable",
  props : {
    page : {
      type : Number
    },
    pageCount : {
      type : Number
    },
    sessions : {
      type : Array
    },
    itemsPerPage : {
      type : Number
    },
    initNewSession : {
      type : Function,
    },
    setCurrentView : {
      type : Function,
    },
    continueSession : {
      type : Function
    },
    switchTablecurrentPage : {
      type : Function
    }
  },
  data () {
    return {
      singleSelect: true,
      selected: [],
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
          value: 'creationDate',
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
    continueTargetSession : function() {
      let session = this.selected[0]
      this.continueSession(session)
    },
    getDateGoodFormat : function(date) {
      let timePart, datePart
      if (date) {
        timePart = this.$root.getTimeUniverselFormat(date)
        datePart = this.$root.formatDate(this.$root.getDatePikerDateFormat(date))
        return datePart + ", "+ timePart
      }
    }
  },
}
</script>
<style scoped>
</style>