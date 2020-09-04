<template>
  <div>
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
    >
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12" sm="12">
              <v-select
                  :items="instrumentsName"
                  v-model="currentInstrument"
                  name="instrument"
                  item-text="name"
                  label="Instrument name"
                  return-object
                  required
              ></v-select>
            </v-col>
            <v-col cols="12" sm="12">
              <v-select
                  :items="parameters"
                  v-model="mainParameter"
                  name="parameter"
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
                  name="parameter"
                  return-object
                  multiple
                  :disabled="linkedParameters.length === 0"
              ></v-autocomplete>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
    </AerisDataValidationServices>
    <v-card-actions>
      <v-btn color="blue darken-1" text @click="createSession">Create</v-btn>
    </v-card-actions>
  </div>
</template>

<script>
import {
  AerisDataValidationServices
} from "@/lib/modules/aeris-datavalidation-components";

const baseUrl = "http://localhost:9001";

export default {
  name: "aeris-datavalition-sessionform",
  components : {
    AerisDataValidationServices
  },
  props : {
    setDialogue : {
      type : Function,
    },
  },
  watch : {
    currentInstrument : function(currentInstrument) {
      let instrumentId = currentInstrument['_id']['$oid']
      console.log("Test currentIstrument : ", currentInstrument['_id']['$oid'])
      this.callBack = this.initNewSessionParameters
      this.currentUrl = baseUrl + "/instruments/" + instrumentId
    }
  },
  data() {
    return {
      currentUrl : baseUrl + "/instruments/ids",
      mainParameter : null,
      currentInstrument : null,
      linkedParametersSelection : [],
      instrumentsName : [/*'InstrumentId1', 'InstrumentId2', 'InstrumentId3', 'InstrumentId4'*/],
      parameters : [/*'Air Temp', 'Cell Temp'*/],
      linkedParameters : [],
      callBack : this.initNewSessionForm,
    }
  },
  methods : {
    createSession : function () {
      console.log("Test create new session")
      this.setDialogue()
    },
    initNewSessionForm : function (instruments) {
      let instrumentObj

      if(instruments) {
        instruments.forEach((instrument) => {
          instrumentObj = JSON.parse(instrument)
          this.instrumentsName.push(instrumentObj)
        });
      }

    },
    initNewSessionParameters : function(instrument) {
      if(instrument) {
        this.parameters = instrument.parameters
        this.linkedParameters = [...instrument['parameters'], ...instrument['auxParameters']]
        console.log("Test initNewSessionParameters : ", instrument, instrument['auxParameters'])
      }
    },
  }
}
</script>

<style scoped>

</style>