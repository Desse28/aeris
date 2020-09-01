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
          v-model="settings"
          multiple
      >
        <v-list-item>
          <template v-slot:default="{ active }">
            <v-list-item-action>
              <v-checkbox
                  :input-value="active"
                  color="primary"
              ></v-checkbox>
            </v-list-item-action>

            <v-list-item-content>
              <v-list-item-title>Selection1</v-list-item-title>
              <v-list-item-subtitle>last update : 01/09/2020</v-list-item-subtitle>
            </v-list-item-content>
          </template>
        </v-list-item>

        <v-list-item>
          <template v-slot:default="{ active }">
            <v-list-item-action>
              <v-checkbox
                  :input-value="active"
                  color="primary"
              ></v-checkbox>
            </v-list-item-action>

            <v-list-item-content>
              <v-list-item-title>Selection2</v-list-item-title>
              <v-list-item-subtitle>last update : 01/09/2020</v-list-item-subtitle>
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
  data: () => ({
    settings: [],
  }),
}
</script>

<style scoped>

</style>