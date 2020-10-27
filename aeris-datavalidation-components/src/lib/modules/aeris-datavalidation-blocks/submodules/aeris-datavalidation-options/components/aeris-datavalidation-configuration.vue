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

import {
  AerisDatavalidationLogoutItem,
  AerisDatavalidationTypography,
  AerisDatavalidationSessionForm,
  AerisDatavalidationLangSwitcher,
  AerisDatavalidationSessionsTable
} from "@/lib/modules/aeris-datavalidation-components";

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