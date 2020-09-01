<template>
  <div>
    <v-data-table
        v-model="selected"
        :headers="headers"
        :items="desserts"
        item-key="name"
        show-select
        :page.sync="page"
        :items-per-page="itemsPerPage"
        hide-default-footer
        @page-count="pageCount = $event"
        class="elevation-1"
    >
      <template v-slot:item.actions="{ item }">
        <v-icon
            small
            class="mr-2"
            @click="editItem(item)"
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
    <div class="text-center pt-2">
      <v-pagination v-model="page" :length="pageCount"></v-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "aeris-datavalidation-selectionstable",
  data () {
    return {
      selected: [],
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      headers: [
        {
          text: 'Selection',
          align: 'start',
          sortable: false,
          value: 'name',
        },
        { text: 'last update', value: 'updateDate' },
        { text: 'Action', value: 'action', sortable: false },
      ],
      desserts: [
        {
          name: 'selection1',
          updateDate : '01/09/2020',
        },
        {
          name: 'Selection2',
          updateDate : '01/09/2020',
        },
      ],
    }
  },
  methods : {
    editItem (item) {
      this.editedIndex = this.desserts.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },
    deleteItem (item) {
      const index = this.desserts.indexOf(item)
      confirm('Are you sure you want to delete this item?') && this.desserts.splice(index, 1)
    },
    save () {
      if (this.editedIndex > -1) {
        Object.assign(this.desserts[this.editedIndex], this.editedItem)
      } else {
        this.desserts.push(this.editedItem)
      }
      this.close()
    },
  },
}
</script>

<style scoped>
</style>