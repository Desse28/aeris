<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="1000px">
      <v-card min-height="500" max-height="900">
        <v-row>
          <v-col offset="5">
            <v-card-title>
              <AerisDatavalidationTypography
                  :text="getTitle"
                  type="h5"
              />
            </v-card-title>
          </v-col>
          <v-col>
            <AerisDatavalidationLangSwitcher/>
          </v-col>
        </v-row>
        <AerisDatavalidationSessionsTable
            v-if="currentItem === 'Continue session'"
            :initNewSession="initNewSession"
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
import AerisDatavalidationLangSwitcher from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-switchers/aeris-datavalidation-langswitcher"
export default {
  name: "aeris-datavalidation-configuration",
  props : {
    newSession : {
      type : Function,
      default: () => {}
    },
  },
  components: {
    AerisDatavalidationTypography,
    AerisDatavalidationSessionForm,
    AerisDatavalidationLangSwitcher,
    AerisDatavalidationSessionsTable
  },
  data() {
    return {
      dialog: true,
      currentItem : "Continue session",
    }
  },
  computed : {
    getTitle : function() {
      if(this.currentItem === "Continue session")
        return this.$t('session.continue_session')
      else if(this.currentItem === 'New session')
        return this.$t('session.new_session')
      return ""
    },
  },
  methods : {
    setCurrentItem : function(item) {
      this.currentItem = item
    },
    initNewSession : function (currentSession, currentInstrument, infos) {
      if(currentSession && currentInstrument) {
        this.newSession(currentSession, currentInstrument, infos)
        this.dialog = false;
      }
    }
  }
}
</script>

<style scoped>

</style>