<template>
  <div>
    <v-alert type="success">
      I'm a success alert.
    </v-alert>
    <AerisDatavalidationDeleteDialog
        :dialog="dialog"
        :ok="$t('session.yes')"
        :cancel="$t('session.no')"
        :okCallBack="validateDelete"
        :cancelCallBack="cancelDelete"
        :title="$t('session.delete_title')"
        :message="$t('session.delete_message')"
    />
    <template>
      <v-data-table
          :headers="tableHeaders"
          :items="selections"
          class="elevation-1"
          item-key="name"
          v-model="selected"
          hide-default-footer
          :page.sync="page"
          :items-per-page="itemsPerPage"
          @page-count="pageCount = $event"
          height="400"
          :disabled="selections.length === 0"
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
    <div class="text-center pt-2" :disabled="selections.length === 0">
      <v-pagination v-model="page" :length="pageCount"></v-pagination>
    </div>
    <v-card-actions>
      <v-btn
          color="primary"
          text
          @click="submitSelection"
      >
        {{$t('session.submit_selections')}}
      </v-btn>
    </v-card-actions>
  </div>
</template>

<script>
import AerisDatavalidationDeleteDialog from "./../../aeris-datavalidation-dialogs/components/aeris-datavalidation-deletedialog"
export default {
  name: "aeris-datavalidation-selectionstable",
  components : {
    AerisDatavalidationDeleteDialog,
  },
  props : {
    selections : {
      type : Array,
      default : () => [],
    },
    notifyEditMode: {
      type : Function,
      default: ()=>{}
    }
  },
  data () {
    return {
      selected: [],
      page: 1,
      pageCount: 0,
      dialog: false,
      itemsPerPage: 5,
      deleteItem: null,
    }
  },
  computed : {
    tableHeaders : function() {
      return [
        {
          text: this.$t('session.start_date_input_label'),
          align: 'start',
          sortable: false,
          value: 'startDate',
        },
        {
          text: this.$t('session.end_date_input_label'),
          align: 'start',
          sortable: false,
          value: 'endDate',
        },
        {
          text: this.$t('session.flags'),
          align: 'start',
          sortable: false,
          value: 'flags',
        },
        {text: 'Actions', value: 'actions', sortable: false},
      ]
    }
  },
  methods: {
    editSelection (selection) {
      this.notifyEditMode(selection)
    },
    setDeleteItem (item) {
      this.dialog = true
      this.deleteItem = item
    },
    getDateGoodFormat : function(date) {
      let timePart, datePart
      if (date) {
        timePart = this.$root.getTimePickerTimeFormat(date, true)
        datePart = this.$root.getDatePikerDateFormat(date, "fr")
        return datePart + ", "+ timePart
      }
    },
    cancelDelete : function () {
      this.dialog = false
    },
    validateDelete : function () {
      this.dialog = false
      console.log("Test delete selection", this.deleteItem)
    },
    submitSelection : function() {
      //Here submit all selections
    }
  },
}
</script>

<style scoped>
</style>