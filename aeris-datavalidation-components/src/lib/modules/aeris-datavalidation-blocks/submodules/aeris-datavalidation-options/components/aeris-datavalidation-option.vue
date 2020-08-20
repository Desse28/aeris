<template>
  <div class="text-center">
    <v-menu
        v-model="menu"
        :close-on-content-click="false"
        :nudge-width="200"
        offset-x
    >
      <template v-slot:activator="{ on, attrs }">

        <v-btn class="ma-2" tile outlined color="blue" v-on="on" v-bind="attrs">
          <v-icon left>mdi-plus-circle-outline</v-icon> Add parameter(s)
        </v-btn>

      </template>

      <v-card>
        <v-row justify="center">
          <v-col cols="6">
            <v-list>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>Options</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-divider></v-divider>
            <v-list>
              <v-list-item v-for="label in optionsLabel" :key="label">
                <v-list-item-action>
                  <v-switch v-model="options" :value="label" color="blue"></v-switch>
                </v-list-item-action>
                <v-list-item-title>{{label}}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-col>
          <v-col cols="6">
            <v-list>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>Parallel Mode</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-divider></v-divider>
            <v-list>
              <v-list-item  v-for=" (parallelLabel, index) in parallelsLabel" :key="parallelLabel">
                <v-list-item-action>
                  <v-switch
                      v-model="parallels"
                      :value="parallelLabel"
                      color="blue"
                      :disabled="!options.includes(optionsLabel[index])"
                  >
                  </v-switch>
                </v-list-item-action>
                <v-list-item-title>{{parallelLabel}}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-col>
        </v-row>
      </v-card>
    </v-menu>
  </div>
</template>
<script>
export default {
  name: "aeris-datavalidation-options",
  data() {
    return {
      options: [],
      parallels: [],
      optionsLabel: ["options1", "options2", "options3"],
      parallelsLabel: ["parallel1", "parallel2", "parallel3"],
    }
  },
}
</script>

<style scoped>
</style>