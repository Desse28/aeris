<template>
  <div class="text-center">
    <v-menu
        :close-on-content-click="false"
        :nudge-width="200"
        offset-x
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn class="ma-2" tile outlined color="blue" v-on="on" v-bind="attrs">
          <v-icon left>mdi-plus-circle-outline</v-icon> {{ $t("session.label_addParameters") }}
        </v-btn>
        <v-btn class="ma-2" tile outlined color="blue" v-on:click="removeChart">
          <v-icon left>mdi-delete</v-icon> {{ $t("session.label_removeChart") }}
        </v-btn>
        <v-btn class="ma-2" tile outlined color="blue" v-on:click="addNewChart">
          <v-icon left>mdi-plus-circle-outline</v-icon> {{ $t("session.label_addChart") }}
        </v-btn>
      </template>
      <v-card>
        <v-row justify="center">

          <v-col cols="4">
            <v-list>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>Parameter(s)</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-divider></v-divider>
            <v-list>
              <v-list-item v-for="label in parametersLabel" :key="label.name">
                <v-list-item-action>
                  <v-checkbox
                      v-model="parameters"
                      :label="label.name"
                      :color="label.color"
                      :value="label"
                      hide-details
                  ></v-checkbox>

                </v-list-item-action>
              </v-list-item>
            </v-list>
          </v-col>

          <v-col cols="4">
            <v-list>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>{{ $t("session.label_layering") }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-divider></v-divider>
            <v-list>
              <v-list-item  v-for="(parallelLabel, index) in parallelsLabel" :key="parallelLabel">
                <v-list-item-action>
                  <v-checkbox
                      v-model="parallels"
                      color="red"
                      :value="parallelLabel"
                      :disabled="!parameters.includes(parametersLabel[index])"
                      hide-details
                  ></v-checkbox>

                </v-list-item-action>
              </v-list-item>
            </v-list>
          </v-col>

          <v-col cols="4">
            <v-list>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>Chart(s)</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-divider></v-divider>
            <v-list>
              <v-list-item v-for="label in parametersLabel" :key="label.name">
                <v-list-item-action>
                  <div style="max-height: 40px;">
                    <v-select :items="charts">
                    </v-select>
                  </div>
                </v-list-item-action>
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
    addNewChart: {
      type: Function,
      default: () => {}
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
    removeChart: {
      type : Function,
    }
  },
  data() {
    return {
      parallels: [],
      parameters: [],
      parallelsLabel: [],
      parametersLabel : [],
      charts: ['chart1', 'chart2', 'chart3'],
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
      if(oldParalles.length < newParalles.length) {
        this.flushNewParameter(newParalles)
      } else if(newParalles.length < oldParalles.length ) {
        this.disabledParallel(newParalles, oldParalles)
      }
    },
    auxParameters: function () {
      this.auxParameters.forEach((parameter)=> {
        this.parametersLabel.push(parameter)
        this.parameters.push(parameter)
      })
      this.pushLayering(this.auxParameters.length)
    },
    linkedParameters: function () {
      this.linkedParameters.forEach((parameter)=> {
        if(parameter) {
          this.parametersLabel.unshift(parameter)
        }
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
        if(this.parameters.includes(targetParameter))
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
      let deletedElement

      let intersection = oldParameters.filter(param => {
        return !newParameters.some(newParam => newParam.name === param.name)
      })

      deletedElement = oldParameters.length === 0 ? newParameters : intersection[0]
      this.removeParameter(deletedElement)
    },
    flushNewParallel : function(parameters) {
      let newParameter = parameters[parameters.length - 1]
      this.addNewParallel(newParameter)
    },
    flushRemoveParallel: function (newParameters, oldParameters) {
      let targetParallelIndex
      let deletedElement

      let intersection = oldParameters.filter(param => {
        return !newParameters.some(newParam => newParam.name === param.name)
      })

      deletedElement = intersection[0]
      targetParallelIndex = this.parametersLabel.indexOf(deletedElement);
      if(0 <= targetParallelIndex) {
        if(this.parallels.includes(this.parallelsLabel[targetParallelIndex])) {
          this.parallels.splice(this.parallels.indexOf(this.parallelsLabel[targetParallelIndex]), 1)
        }
        this.removeParameter(deletedElement)
      }
    }
},

}
</script>

<style scoped>
</style>