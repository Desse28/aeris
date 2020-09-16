<template>
  <div class="text-center">
    <v-menu
        :close-on-content-click="false"
        :nudge-width="200"
        offset-x
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn class="ma-2" tile outlined color="blue" v-on="on" v-bind="attrs">
          <v-icon left>mdi-plus-circle-outline</v-icon> {{ $t("chartconf.add-parameters") }}
        </v-btn>
      </template>
      <v-card>
        <v-row justify="center">
          <v-col cols="6">
            <v-list>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>Parameter(s)</v-list-item-title>
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
                  <v-list-item-title>Layering(s)</v-list-item-title>
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
    linkedParameters: {
      type : Array,
      default : () => []
    },
    auxParameters : {
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
      parallelsLabel: [],
      parametersLabel : [],
    }
  },
  watch: {
    parameters : function (newParameters, oldParameters) {
      if(oldParameters.length < newParameters.length) {
        this.flushNewParallel(newParameters)
      } else if (newParameters.length < oldParameters.length) {
        this.flushRemoveParallel(newParameters, oldParameters)
      }
    },
    parallels : function (newParalles, oldParalles) {
      if(oldParalles.length < newParalles.length)
        this.flushNewParameter(newParalles)
      else if(newParalles.length < oldParalles.length )
        this.disabledParallel(newParalles, oldParalles)
    },
    auxParameters: function () {
      this.auxParameters.forEach((parameter)=> {
        this.parametersLabel.push(parameter.name)
        this.parameters.push(parameter.name)
      })
      this.pushLayering(this.auxParameters.length)
    },
    linkedParameters: function () {

      this.linkedParameters.forEach((parameter)=> {
        this.parametersLabel.unshift(parameter.name)
      })

      this.pushLayering(this.linkedParameters.length)
    }
  },
  methods : {
    pushLayering : function(nbrLayering) {
      let parallelLen = this.parallelsLabel.length
      let len = parallelLen + nbrLayering
      for(let i = parallelLen; i < len; i++) {
        this.parallelsLabel.push("Layering" + (i + 1))
      }
    },
    turnLayering : function(start, end) {
      for(let i = start; i < end; i++) {
        this.parallels.push("Layering" + i)
      }
    },
    disabledParallel : function (newParallels, oldParrales) {
      let deletedIndex
      let targetParameter
      let intersection

      if(newParallels && oldParrales) {
        intersection = oldParrales.filter(value => !newParallels.includes(value))
        deletedIndex = intersection[0].split("Layering")[1]
        targetParameter = this.parametersLabel[deletedIndex - 1]
        this.addNewParallel(targetParameter)
      }
    },
    flushNewParameter : function (parallels) {
      let lastIndex = parallels.length - 1
      let newParallelIndex = parallels[lastIndex].split("Layering")[1]
      let targetParameter = this.parametersLabel[newParallelIndex-1]
      this.addNewParameter(targetParameter)
    },
    flushRemoveParameter : function (newParameters, oldParameters) {
      let intersection = oldParameters.filter(value => !newParameters.includes(value))
      let deletedElement = oldParameters.length === 0 ? newParameters : intersection[0]
      this.removeParameter(deletedElement)
    },
    flushNewParallel : function(parameters) {
      let newParameter = parameters[parameters.length - 1]
      this.addNewParallel(newParameter)
    },
    flushRemoveParallel: function (newParameters, oldParameters) {
      let intersection = oldParameters.filter(value => !newParameters.includes(value))
      let deletedElement = intersection[0]
      const targetParallelIndex = this.parametersLabel.indexOf(deletedElement);
      if(-1 < targetParallelIndex) {
        this.parallels.splice(targetParallelIndex, 1);
        this.removeParameter(deletedElement)
      }
    },
},

}
</script>

<style scoped>
</style>