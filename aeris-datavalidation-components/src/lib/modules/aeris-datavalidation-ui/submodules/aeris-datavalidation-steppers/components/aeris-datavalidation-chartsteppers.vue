<template>
  <v-stepper v-model="e1">
    <v-stepper-header>
      <v-stepper-step
          step="1"
          editable
      >
        Selection
      </v-stepper-step>
      <v-stepper-step
          step="2"
          editable
      >
        Validation
      </v-stepper-step>
    </v-stepper-header>

    <v-stepper-items>
      <v-stepper-content step="1">
        <v-card
            class="mb-12"
            color="grey lighten-1"

        >
          <AerisDatavalidationSelectionform
              :qualityFlags="qualityFlags"
              :selectionDate="selectionDate"
              :selectionEndTime="selectionEndTime"
              :selectionStartTime="selectionStartTime"
          />
        </v-card>
      </v-stepper-content>

      <v-stepper-content step="2">
        <div>
          <v-card
              color="grey lighten-1"
          >
            <AerisDatavalidationLandScapeLayaout
                justify="center"
                padding="pa-8"
                :cols="[6, 6]"
                :nbrChildElement="2"
            >
              <template v-slot:land1>
                <AerisDatavalidationSelectionsList/>
              </template>
              <template v-slot:land2>
                <AerisDatavalidationSelectionform
                    :qualityFlags="qualityFlags"
                    :selectionDate="selectionDate"
                    :selectionEndTime="selectionEndTime"
                    :selectionStartTime="selectionStartTime"
                />
              </template>
            </AerisDatavalidationLandScapeLayaout>
            <v-col cols="12">
              <div class="my-2">
                <v-btn >
                  submit
                </v-btn>
              </div>
            </v-col>
          </v-card>
        </div>
      </v-stepper-content>

    </v-stepper-items>
  </v-stepper>
</template>

<script>
import AerisDatavalidationLandScapeLayaout from "./../../aeris-datavalidation-layouts/components/aeris-datavalidation-landscapelayout"
import AerisDatavalidationSelectionsList from "./../../aeris-datavalidation-list/components/aeris-datavalidation-selectionslist"
import AerisDatavalidationSelectionform from "./../../aeris-datavalidation-form/components/aeris-datavalidation-selectionform"

export default {
  name: "aeris-datavalidation-chartsteppers",
  components: {
    AerisDatavalidationSelectionform,
    AerisDatavalidationSelectionsList,
    AerisDatavalidationLandScapeLayaout,

  },
  props: {
    selectionDate : {
      type : String,
      default : ""
    },
    selectionStartTime : {
      type : String,
      default : ""
    },
    selectionEndTime : {
      type : String,
      default : ""
    },
    qualityFlags : {
      type : Array,
      default : () => [],
    }
  },
  data () {
    return {
      e1: 2,
    }
  },
}
</script>