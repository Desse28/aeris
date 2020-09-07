<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-row class="text-center" align="start" justify="center">
          <v-card-title>
            <v-breadcrumbs
                :items="items"
            >
              <template v-slot:item="{ item }">
                <v-breadcrumbs-item
                    :key="item.text"
                    @click="configutionHandler(item.text)"
                    :disabled="item.disabled"
                >
                  {{ item.text.toUpperCase() }}
                </v-breadcrumbs-item>

              </template>
            </v-breadcrumbs>
          </v-card-title>
        </v-row>
        <AerisDatavalidationSessionForm
            v-if="targetItem === 'New session'"
            :parameters="parameters"
            :instruments="instruments"
            :linkedParameters="linkedParameters"
            :setDialogue="setDialogue"
            :currentInstrument="currentInstrument"
            :initNewSessionForm="initNewSessionForm"
            :setCurrentSessionId="setCurrentSessionId"
            :setCurrentInstrument="setCurrentInstrument"
            :initNewSessionParameters="initNewSessionParameters"
        />
        <AerisDatavalidationSessionsTable
            v-if="targetItem === 'Continue session'"
            :setDialogue="setDialogue"
            :setCurrentSessionId="setCurrentSessionId"
        />
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>
import AerisDatavalidationSessionForm from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-form/components/aeris-datavalition-sessionform"
import AerisDatavalidationSessionsTable from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-tables/components/aeris-datavalidation-sessionstable"

export default {
  name: "aeris-datavalidation-configuration",
  props : {
    setCurrentSessionId : {
      type : Function,
    },
  },
  components: {
    AerisDatavalidationSessionForm,
    AerisDatavalidationSessionsTable
  },
  data() {
    return {
      dialog: true,
      targetItem : "New session",
      items: [
        {
          text: 'New session',
          disabled: false,
          href: '/new-session',
        },
        {
          text: 'Continue session',
          disabled: false,
          href: '/continue-session',
        },
      ],
      parameters : [],
      instruments : [],
      linkedParameters : [],
      currentInstrument : null,
    }
  },
  methods : {
    configutionHandler : function (targetItem) {
      this.targetItem = targetItem
    },
    setDialogue : function () {
      this.dialog = this.dialog !== true;
    },
    initNewSessionForm : function (instruments) {
      let instrumentObj
      if(instruments) {
        instruments.forEach((instrument) => {
          instrumentObj = JSON.parse(instrument)
          this.instruments.push(instrumentObj)
        });
      }
    },
    initNewSessionParameters : function(instrument) {
      if(instrument) {
        this.parameters = instrument.parameters
        this.linkedParameters = [...instrument['parameters'], ...instrument['auxParameters']]
      }
    },
    setCurrentInstrument : function (instrument) {
      this.currentInstrument = instrument
    },
  }
}
</script>

<style scoped>

</style>