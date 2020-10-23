<template>
  <div class="text-center ml-4">
    <AerisDatavalidationDeleteDialog
        :dialog="deleteDialog"
        :hideOkButton="hideOkButton"
        :ok="$t('session.label_yes')"
        :cancel="cancelDeleteLabel"
        :okCallBack="validateDelete"
        :cancelCallBack="cancelDelete"
        :title="$t('session.label_deletion')"
        :message="deleteTabMessage"
    />
    <v-menu
        v-for="([text], index) in btns"
        :key="text"
        :close-on-content-click="closeMenu"
        :nudge-width="text ===  $t('session.label_addChart') ? 80 : 200"
        :offset-y="text ===  $t('session.label_addChart') && offset"
        :offset-x="text === $t('session.label_addParameters')&& offset"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            class="mb-2 mt-2 blue--text"
            color="rgb(255, 255, 255)"
            v-if="text !== $t('session.label_removeChart')"
            depressed
            v-bind="attrs"
            v-on="on"
        >
          <v-icon left>{{icons[index]}}</v-icon> {{ text }}
        </v-btn>
        <v-btn class="mb-2 mt-2 blue--text"
               color="rgb(255, 255, 255)"
               depressed
               v-else
               v-on:click="deleteCurrentParalelChart"
        >
          <v-icon left>{{icons[index]}}</v-icon> {{ text }}
        </v-btn>
      </template>
      <v-card>
        <div v-if="text === $t('session.label_addParameters')">
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
                    <v-list-item-title>{{$t('session.label_chart')}}</v-list-item-title>
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
        <div v-if="text ===  $t('session.label_addChart')" class="pa-4">
          <v-alert
              dense
              outlined
              type="error"
              v-if="isEmptyName"
          >
            {{tabAlertMess}}
          </v-alert>
          <v-text-field
              v-model="tabName"
              :label="$t('session.label_chartName')"
              hide-details="auto"
          ></v-text-field>
          <v-btn class="mb-2 mt-2"
                 color="blue"
                 depressed
                 v-on:click="addChart"
          >
            {{$t('session.label_add')}}
          </v-btn>
        </div>
      </v-card>
    </v-menu>
  </div>
</template>
<script>
import AerisDatavalidationChartsSelect from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-selects/components/aeris-datavalidation-chartsselect"
import AerisDatavalidationDeleteDialog from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-dialogs/components/aeris-datavalidation-deletedialog"

export default {
  name: "aeris-datavalidation-options",
  components : {
    AerisDatavalidationChartsSelect,
    AerisDatavalidationDeleteDialog
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
    currentParalelChart : {
      type : String,
      default : () => ""
    }
  },
  data() {
    return {
      tabName: "",
      offset: true,
      parameters: [],
      currentItem : "",
      closeMenu : false,
      tabAlertMess : "",
      hideOkButton:false,
      isEmptyName: false,
      deleteDialog: false,
      parametersLabel : [],
      deleteTabMessage : "",
      tabNames : ["CHART2"],
      btns: [
        [this.$t("session.label_addParameters")],
        [this.$t("session.label_addChart")],
        [this.$t("session.label_removeChart")],
      ],
      cancelDeleteLabel: this.$t('session.label_no'),
      icons : ['mdi-chevron-down', 'mdi-tab-plus', 'mdi-tab-remove']
    }
  },
  watch: {
    parameters : function (newParameters, oldParameters) {
      if(oldParameters.length < newParameters.length) {
        this.switchParameterState(newParameters[newParameters.length - 1])
        this.addParameter(newParameters[newParameters.length - 1])
      } else if (newParameters.length < oldParameters.length) {
        this.flushRemoveParameter(newParameters, oldParameters)
      }
    },
    auxParameters: function () {
      let charts = Object.values(this.charts)

      this.auxParameters.forEach((parameter)=> {
        parameter.chartName = charts[1].name
        this.parametersLabel.push(parameter)
        this.parameters.push(parameter)
      })
    },
    linkedParameters: function () {
      let charts = Object.values(this.charts)

      this.linkedParameters.forEach((parameter)=> {
        if(parameter) {
          parameter.chartName = charts[1].name
          this.parametersLabel.unshift(parameter)
        }
      })
    }
  },
  computed : {
    getCharts : function () {
      return Object.values(this.charts)
    }
  },
  methods : {
    setCurrentItem : function (targetItem) {
      console.log("Test setCurrentItem : ", targetItem)
      if(targetItem)
        this.currentItem = targetItem
    },
    flushRemoveParameter : function (newParameters, oldParameters) {
      let intersection = oldParameters.filter(param => {
        return !newParameters.some(newParam => newParam.name === param.name)
      })
      this.switchParameterState(intersection[0])
      this.removeParameter(intersection[0])
    },
    addChart : function () {
     if(this.tabName === "") {
       this.turnTabAlert(true)
     } else if(this.tabNames.includes(this.tabName.toUpperCase())) {
       this.turnTabAlert(false)
     } else {
       this.addNewChart(this.tabName)
       this.closeMenu = true
       this.tabNames.push(this.tabName.toUpperCase())
       setTimeout(() => {
         this.closeMenu = false
         this.tabName = ""
       }, 100)
     }
    },
    turnTabAlert : function (isEmptyName) {
      this.tabAlertMess = isEmptyName ? this.$t("session.label_emptyName") : this.$t("session.label_chartExist")
      this.isEmptyName = true
      setTimeout(() => {
        this.isEmptyName = false
      }, 1000)
    },
    switchParameterState : function(targetParameter) {
      this.parametersLabel.forEach((parameter)=> {
        if(parameter.name === targetParameter.name) {
          if(!parameter.isOn)
            parameter.isOn = true
          else
            parameter.isOn = false
        }
      })
    },
    deleteCurrentParalelChart : function() {
      if(this.currentParalelChart.toUpperCase() === "CHART2") {
        this.hideOkButton = true
        this.cancelDeleteLabel = this.$t('session.label_close')
        this.deleteTabMessage = this.$t("session.label_nonDeletableChart")
      } else {
        this.deleteTabMessage = this.$t("session.message_deleteChart")
      }
      this.deleteDialog = true
    },
    validateDelete : function () {
      let intersection
      let targetParameters = this.charts[this.currentParalelChart].parameters
      this.removeChart()
      this.deleteDialog = false
      if(targetParameters) {
        intersection = this.parameters.filter(param => {
          return !targetParameters.some(newParam => newParam.name === param.name)
        })
        if(intersection)
          this.parameters = intersection
      }

      this.tabNames =  this.tabNames.filter(tabName => tabName.toUpperCase() !== this.currentParalelChart.toUpperCase())
    },
    cancelDelete : function () {
      this.deleteDialog = false
      this.hideOkButton = false
      this.cancelDeleteLabel = this.$t('session.label_no')
    },
  },

}
</script>

<style scoped>
</style>