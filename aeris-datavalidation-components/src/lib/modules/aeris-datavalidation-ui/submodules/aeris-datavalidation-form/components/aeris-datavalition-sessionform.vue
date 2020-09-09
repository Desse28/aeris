<template>
  <div>
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" sm="12">
              <v-select
                  :items="instruments"
                  v-model="instrument"
                  name="name"
                  item-text="name"
                  item-value="name"
                  label="Instrument name"
                  return-object
                  required
              ></v-select>
            </v-col>
            <v-col cols="12" sm="12">
              <v-select
                  :items="parameters"
                  v-model="mainParameter"
                  name="mainParameter"
                  item-text="name"
                  label="Main Parameter"
                  return-object
                  required
                  :disabled="parameters.length === 0"
              ></v-select>
            </v-col>
            <v-col cols="12" sm="12">
              <v-autocomplete
                  :items="linkedParameters"
                  v-model="linkedParametersSelection"
                  item-text="name"
                  label="Linked Parameters"
                  name="linkedParameters"
                  return-object
                  multiple
                  :disabled="linkedParameters.length === 0"
              ></v-autocomplete>
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationDateMounthPicker
                  date_label="Start date"
                  :setCurrentDate="()=>{}"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationDateMounthPicker
                  date_label="End date"
                  :setCurrentDate="()=>{}"
              />
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-btn
            type="submit"
            color="blue darken-1"
            :disabled="mainParameter === null || linkedParametersSelection.length === 0"
            text @click="createSession"
        >
          Create
        </v-btn>
        <v-btn
            type="submit"
            color="blue darken-1"
            text @click="continueSession"
        >
          Continue session
        </v-btn>
      </v-card-actions>
    </AerisDataValidationServices>
  </div>
</template>

<script>
import {
  AerisDataValidationServices,
} from "@/lib/modules/aeris-datavalidation-components";
import AerisDatavalidationDateMounthPicker from "./../../aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-pickers/aeris-datavalidation-datemounthpicker"

const baseUrl = "http://localhost:9001";

export default {
  name: "aeris-datavalition-sessionform",
  components : {
    AerisDataValidationServices,
    AerisDatavalidationDateMounthPicker
  },
  props : {
    setDialogue : {
      type : Function,
    },
    setCurrentSessionId : {
      type : Function,
    },
    initNewSessionForm : {
      type : Function,
    },
    initNewSessionParameters : {
      type : Function,
    },
    instruments : {
      type : Array,
      default : () => [],
    },
    parameters : {
      type : Array,
      default : () => [],
    },
    linkedParameters : {
      type : Array,
      default : () => [],
    },
    currentInstrument : {
      type : Object
    },
    setCurrentItem : {
      type : Function
    },
    setCurrentInstrument : {
      type : Function,
    },
  },
  watch : {
    instrument : function(currentInstrument) {
      let instrumentId = currentInstrument['_id']['$oid']
      this.setCurrentInstrument(currentInstrument)
      this.callBack = this.initNewSessionParameters
      this.currentUrl = baseUrl + "/instruments/" + instrumentId
    }
  },
  data() {
    return {
      requestData : null,
      typeOfRequest : "GET",
      currentUrl : baseUrl + "/instruments/ids",
      mainParameter : null,
      linkedParametersSelection : [],
      instrument : this.currentInstrument,
      callBack : this.initNewSessionForm,
    }
  },
  methods : {
    continueSession : function () {
      this.setCurrentItem("Continue session")
    },
    createSession : function () {
      let instrumentId = this.instrument['_id']['$oid']
      this.requestData = {
        instrumentId : instrumentId,
        mainParameter : this.mainParameter,
        linkedParameters : this.linkedParametersSelection,
      }
      this.typeOfRequest = "POST"
      this.callBack = this.createSessionResponse
      this.currentUrl = baseUrl + "/sessions"
    },
    createSessionResponse : function (data) {
      let sessionId = data.id
      if(sessionId) {
        this.setCurrentSessionId(sessionId)
        this.setDialogue()
      }
    },
  }
}
</script>

<style scoped>

</style>