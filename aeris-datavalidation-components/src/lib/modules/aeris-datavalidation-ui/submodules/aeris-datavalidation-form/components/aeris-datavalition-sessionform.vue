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
                  label="Main Parameter"
                  required
              ></v-select>
            </v-col>
            <v-col cols="12" sm="12">
              <v-autocomplete
                  :items="[...parameters, ...auxParameters]"
                  label="Linked Parameters"
                  multiple
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

const baseUrl = "http://localhost:9001/";

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
      console.log("Test currentIstrument : ", currentInstrument)
    }
  },
  data() {
    return {
      currentUrl : baseUrl + "instruments/ids",
      currentInstrument : "",
      instrumentsName : [/*'InstrumentId1', 'InstrumentId2', 'InstrumentId3', 'InstrumentId4'*/],
      parameters : ['Air Temp', 'Cell Temp'],
      auxParameters : [],
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
      instruments.forEach((instrument) => {
        instrumentObj = JSON.parse(instrument)
        this.instrumentsName.push(instrumentObj)
      });
      console.log("Test initNewSessionForm : ", instruments )
    }
  }
}
</script>

<style scoped>

</style>