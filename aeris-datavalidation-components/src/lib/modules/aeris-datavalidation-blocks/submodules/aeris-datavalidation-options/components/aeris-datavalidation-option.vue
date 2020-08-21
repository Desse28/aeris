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
              <v-list-item v-for="label in parametersLabel" :key="label">
                <v-list-item-action>
                  <v-switch v-model="parameters" :value="label" color="blue"></v-switch>
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
                      :disabled="!parameters.includes(parametersLabel[index])"
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
  props: {
    parametersLabel : {
      type : Array,
      default : () => []
    },
    parallelsLabel : {
      type : Array,
      default : () => []
    },
    addNewParameter : {
      type : Function
    },
    removeParameter : {
      type : Function
    },
    addNewParallel : {
      type : Function
    },
    removeParallel : {
      type : Function
    },
  },
  data() {
    return {
      parameters: [],
      parallels: [],
    }
  },
  watch: {
    parameters : function (newOptions, oldOptons) {
      if(oldOptons.length < newOptions.length)
        this.addNewParameter(newOptions)
      else if (newOptions.length < oldOptons.length)
        this.removeParameter(newOptions, oldOptons)
    },
    parallels : function (newParalles, oldParalles) {
      if(oldParalles.length < newParalles.length)
        this.addNewParallel(newParalles)
      else if ( newParalles.length < oldParalles.length )
        this.removeParallel(newParalles, oldParalles)
    },
  },

}
</script>

<style scoped>
</style>