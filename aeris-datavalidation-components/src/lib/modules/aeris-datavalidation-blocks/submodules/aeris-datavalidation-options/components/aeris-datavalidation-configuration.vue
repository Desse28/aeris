<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="1500px">
      <v-card min-height="500" max-height="900">
        <v-row class="text-center" align="start" justify="center">
          <v-card-title>
            <AerisDatavalidationTypography
                :text="currentItem"
                type="h5"
            />
          </v-card-title>
        </v-row>
        <AerisDatavalidationSessionsTable
            v-if="currentItem === 'Continue session'"
            :setCurrentSessionId="setCurrentSessionId"
            :setDialogue="setDialogue"
            :setCurrentItem="setCurrentItem"
        />
        <AerisDatavalidationSessionForm
            v-if="currentItem === 'New session'"
            :initNewSession="initNewSession"
            :setCurrentItem="setCurrentItem"
        />
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>
import AerisDatavalidationSessionForm from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-form/components/aeris-datavalition-sessionform"
import AerisDatavalidationSessionsTable from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-tables/components/aeris-datavalidation-sessionstable"
import AerisDatavalidationTypography from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-typographies/components/aeris-datavalidation-typography"

export default {
  name: "aeris-datavalidation-configuration",
  props : {
    setCurrentSessionId : {
      type : Function,
    },
  },
  components: {
    AerisDatavalidationTypography,
    AerisDatavalidationSessionForm,
    AerisDatavalidationSessionsTable
  },
  data() {
    return {
      dialog: true,
      currentItem : "Continue session",
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
    }
  },
  methods : {
    setDialogue : function () {
      this.dialog = this.dialog !== true;
    },
    setCurrentItem : function(item) {
      this.currentItem = item
    },
    initNewSession : function (currentSession, currentInstrument) {
      //this.dialog = true;
      console.log("Test init new session : ", currentSession, currentInstrument)
    }
  }
}
</script>

<style scoped>

</style>