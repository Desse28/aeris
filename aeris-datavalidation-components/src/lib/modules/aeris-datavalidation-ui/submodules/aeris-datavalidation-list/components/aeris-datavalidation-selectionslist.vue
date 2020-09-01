<template>
  <v-card
      min-height="400"
      class="mx-auto"
  >
    <v-list
        subheader
        two-line
        flat
    >
      <v-subheader>Selections</v-subheader>

      <v-list-item-group
          v-model="item"
          color="primary"
      >

        <v-list-item
            v-for="(item, i) in items"
            :key="i"
        >
          <template v-slot:default="{ active }">
            <v-checkbox
                :input-value="active"
                color="primary"
            ></v-checkbox>

            <v-list-item-content>
              <v-list-item-title>{{item.text}}</v-list-item-title>
              <v-list-item-subtitle>{{item.lastUpdate}}</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

      </v-list-item-group>
    </v-list>
  </v-card>

</template>
<script>
export default {
  name: "aeris-datavalidation-selectionslist",
  data () {
    return {
      settings: [],
      item: 1,
      items: [
        { text: 'Selection1', lastUpdate : '01/09/2020' },
        { text: 'Selection2', lastUpdate : '01/08/2020' },
        { text: 'Selection3', lastUpdate : '01/07/2020' },
      ],
    }
  },
}
</script>

<style scoped>

</style>