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
        <v-card
            class="mb-12"
            color="grey lighten-1"
            height="200px"
        ></v-card>

        <v-btn
            color="primary"
            @click="e1 = 1"
        >
          Continue
        </v-btn>

        <v-btn text>Cancel</v-btn>
      </v-stepper-content>

    </v-stepper-items>
  </v-stepper>
</template>

<script>
import AerisDatavalidationSelectionform from "./../../aeris-datavalidation-form/components/aeris-datavalidation-selectionform"
export default {
  name: "aeris-datavalidation-chartsteppers",
  components: {
    AerisDatavalidationSelectionform
  },
  props: {
    selectionDate : {
      type : Date,
      default : null
    },
    selectionStartTime : {
      type : Date,
      default : null
    },
    selectionEndTime : {
      type : Date,
      default : null
    },
    qualityFlags : {
      type : Array,
      default : () => [],
    }
  },
  data () {
    return {
      e1: 1,
    }
  },
}
</script>