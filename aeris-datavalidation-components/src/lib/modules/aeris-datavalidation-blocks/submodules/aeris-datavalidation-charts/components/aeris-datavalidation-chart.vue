<template>
  <div class="v-sheet v-sheet--outlined theme--light grey--text--">
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <AerisDatavalidationDeleteDialog
          :dialog="deleteDialog"
          :ok="$t('session.label_yes')"
          :cancel="$t('session.label_no')"
          :okCallBack="validateDelete"
          :cancelCallBack="cancelDelete"
          :title="$t('session.label_deletion')"
          :message="$t('session.message_delete')"
      />
      <v-alert type="error" v-if="deleteAlert">
        {{$t('session.message_chooseSelection')}}
      </v-alert>
      <v-tooltip
          bottom
          :position-x="positionX"
          :position-y="positionY"
          v-model="tooltipState"
      >
        <div v-html="tooltipContent">
        </div>
      </v-tooltip>
      <v-row justify="center">
        <v-col cols="12">
          <div
              :id="chartId"
              :key="componentKey"
              ref="graph"
          >
          </div>
        </v-col>
      </v-row>
    </AerisDataValidationServices>
  </div>
</template>

<script>
  import AerisDataValidationServices from "../../../../aeris-datavalidation-services/components/aeris-datavalidation-services"
  import AerisDatavalidationDeleteDialog from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-dialogs/components/aeris-datavalidation-deletedialog"
  export default {
    name: "aeris-datavalidation-chart",
    components: {
      AerisDataValidationServices,
      AerisDatavalidationDeleteDialog
    },
    props : {
      chartId : {
        type : String,
        default : () => "defaultId"
      },
      componentKey : {
        type : Number,
        default : () => 0
      },
      currentUrl : {
        type : String,
        default : () => ""
      },
      callBack : {
        type : Function,
        default : ()=>{}
      },
      requestData : {
        type : Object,
        default : () => null
      },
      typeOfRequest : {
        type : String,
        default : () => ""
      },
      positionX : {
        type : Number,
        default : () => 0
      },
      positionY : {
        type : Number,
        default : () => 0
      },
      tooltipContent : {
        type : String,
        default : () => ""
      },
      tooltipState : {
        type : Boolean,
        default : () => false
      },
      deleteDialog : {
        type : Boolean,
        default : () => false
      },
      deleteAlert : {
        type : Boolean,
        default : () => false
      },
      validateDelete : {
        type : Function,
        default : () => {}
      },
      cancelDelete : {
        type : Function,
        default : Function
      }
    }
  }
</script>

<style scoped>

</style>