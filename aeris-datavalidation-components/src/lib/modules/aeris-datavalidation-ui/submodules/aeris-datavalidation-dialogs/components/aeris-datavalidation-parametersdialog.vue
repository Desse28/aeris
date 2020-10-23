<template>
  <div>
    <v-row justify="center">
      <v-col cols="6">
        <v-list>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>{{ $t("session.label_parameter") }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-divider></v-divider>
        <v-list>
          <v-list-item v-for="label in parametersLabel" :key="label.name" class="ma-3">
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
      <v-col cols="6">
        <v-list>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>Chart</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-divider></v-divider>
        <v-list>
          <v-list-item v-for="(label, index) in parametersLabel"
                       :key="label.name"
          >
            <v-list-item-action>
              <AerisDatavalidationChartsSelect
                  :selectIndex="index"
                  :charts="getCharts"
                  :currentParameter="parametersLabel[index]"
                  :switchParameterChart="switchParameterChart"
              />
            </v-list-item-action>
          </v-list-item>
        </v-list>
      </v-col>

    </v-row>
  </div>
</template>

<script>
import AerisDatavalidationChartsSelect from "./../../aeris-datavalidation-selects/components/aeris-datavalidation-chartsselect"
export default {
  name: "aeris-datavalidation-parametersdialog",
  components : {
    AerisDatavalidationChartsSelect
  },
  props: {
    linkedParameters: {
      type : Array,
      default : () => []
    },
    auxParameters : {
      type : Array,
      default : () => []
    },
    addNewChart: {
      type: Function,
      default: () => {}
    },
    removeParameter : {
      type : Function
    },
    addParameter : {
      type : Function
    },
    removeChart: {
      type : Function,
    },
    charts : {
      type : Object,
      default : () => null
    },
    switchParameterChart : {
      type : Function,
      default : () => {}
    },
    parametersLabel : {
      type : Array,
      default : () => {}
    },
    setParameters : {
      type : Function,
      default : () => {}
    }
  },
  data() {
    return {
      parameters: [],
    }
  },
  watch : {
    parameters : function (newParameters) {
      this.setParameters(newParameters)
    }
  },
}
</script>

<style scoped>

</style>