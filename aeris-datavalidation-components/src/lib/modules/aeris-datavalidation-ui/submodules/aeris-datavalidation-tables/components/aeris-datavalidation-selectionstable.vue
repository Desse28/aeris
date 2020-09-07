<template>
  <div>
      <template>
        <v-data-table
            :headers="headers"
            :items="selections"
            class="elevation-1"
            item-key="name"
            show-select
            v-model="selected"
            hide-default-footer
            :page.sync="page"
            :items-per-page="itemsPerPage"
            @page-count="pageCount = $event"
            height="400"
            :disabled="selections.length === 0"
        >
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
                @click="deleteItem(item)"
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
    sessionSelections : {
      type : Array,
      default : () => [],
    },
    setSelectionDateTime : {
      type : Function,
    },
    setQualityFlagsDefaultValue : {
      type : Function,
    },
    setSelectionPreconfData : {
      type : Function,
    },
  },
  watch : {
    sessionSelections : function () {
      this.selections = this.sessionSelections
      this.selections.forEach((selection, index) => {
        selection.name = "Selection" + (index + 1)
        selection.startDate = this.getDateGoodFormat(new Date(selection.startDate))
        this.setSelectionPreconfData([selection.startDate, selection.endDate])
      })
    }
  },
  data () {
    return {
      selected: [],
      headers: [
        {
          text: 'Selection',
          align: 'start',
          sortable: false,
          value: 'name',
        },
        { text: 'Creation date', value: 'startDate' },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      selections: [],
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
  methods: {
    editSelection (selection) {
      this.setSelectionDateTime(new Date(selection.startDate), new Date(selection.endDate))
      this.setQualityFlagsDefaultValue(selection.flags)
      console.log("Test editItem : ", selection)
      //this.editedIndex = this.selections.indexOf(item)
      //this.editedItem = Object.assign({}, item)
    },
    deleteItem (item) {
      const index = this.selections.indexOf(item)
      confirm('Are you sure you want to delete this item?') && this.selections.splice(index, 1)
    },
    completeNumber : function(number) {
      let result = Math.floor(number / 10) <= 0 ? "0" + number : number
      return result
    },
    getDateGoodFormat : function(currentDate) {
      let day = this.completeNumber(currentDate.getUTCDay())
      let month = this.completeNumber(currentDate.getUTCMonth())
      let year = currentDate.getUTCFullYear()
      return day + "-" + month + "-" + year
    },
  },
}
</script>

<style scoped>
</style>