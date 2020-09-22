<template>
  <div>
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
                @click="deleteSelection(item)"
            >
              mdi-delete
            </v-icon>
          </template>
        </v-data-table>
      </template>
    <div class="text-center pt-2" :disabled="selections.length === 0">
      <v-pagination v-model="page" :length="pageCount"></v-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "aeris-datavalidation-selectionstable",
  props : {
    selections : {
      type : Array,
      default : () => [],
    },
  },
  data () {
    return {
      selected: [],
      editedIndex: -1,
      editedItem: {
        name: '',
        lastUpdate: 0,
      },
      defaultItem: {
        name: '',
        lastUpdate: 0,
      },
      page: 1,
      pageCount: 0,
      itemsPerPage: 5,
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
      console.log("Test edit selection , ", selection)
    },
    deleteSelection (selection) {
      //const index = this.selections.indexOf(item)
      //confirm('Are you sure you want to delete this item?') && this.selections.splice(index, 1)
      console.log("Test delete selection", selection)
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