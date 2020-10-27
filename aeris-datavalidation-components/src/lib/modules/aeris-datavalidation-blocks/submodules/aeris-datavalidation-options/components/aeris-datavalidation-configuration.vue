<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="1000px">
      <v-card min-height="500" max-height="900">
        <v-row>
          <v-col cols="2" offset="1">
            <AerisDatavalidationLangSwitcher/>
          </v-col>
          <v-col offset="1">
            <v-card-title>
              <AerisDatavalidationTypography
                  :text="getTitle"
                  type="h5"
              />
            </v-card-title>
          </v-col>
          <v-col offset="1">
            <AerisDatavalidationLogoutItem/>
          </v-col>
        </v-row>
        <AerisDatavalidationSessionsTable
            v-if="!isCreateMode"
            :setSessions="setSessions"
            :initNewSession="initNewSession"
            :setCurrentItem="setCurrentItem"
        />
        <AerisDatavalidationSessionForm
            v-if="isCreateMode"
            :sessions="sessions"
            :initNewSession="initNewSession"
            :setCurrentItem="setCurrentItem"
        />
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>

import AerisDatavalidationSessionForm from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-form/components/aeris-datavalition-sessionform"
import AerisDatavalidationLogoutItem from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-items/components/aeris-datavalidation-logoutitem"
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
    sessionsDialog : {
      type : Boolean
    },
    setSessionsDialog : {
      type : Function,
    },
  },
  components: {
    AerisDatavalidationTypography,
    AerisDatavalidationLogoutItem,
    AerisDatavalidationSessionForm,
    AerisDatavalidationLangSwitcher,
    AerisDatavalidationSessionsTable
  },
  watch : {
    sessionsDialog : function() {
      if(this.sessionsDialog)
        this.dialog = true
    },
  },
  data() {
    return {
      dialog: true,
      sessions : [],
      currentItem : 'configuration.label_continueSession',
    }
  },
  computed : {
    getTitle : function() {
      return this.$t(this.currentItem)
    },
    isCreateMode : function () {
      return this.currentItem === 'configuration.label_newSession'
    }
  },
  methods : {
    setCurrentItem : function(item) {
      this.currentItem = item
    },
    setSessions : function(sessions) {
      if(sessions) {
        this.sessions = sessions
      }
    },
    initNewSession : function (currentSession, currentInstrument, infos) {
      if(currentSession && currentInstrument) {
        this.dialog = false
        this.setSessionsDialog(false)
        this.newSession(currentSession, currentInstrument, infos)
      }
    }
  }
}
</script>

<style scoped>

</style>