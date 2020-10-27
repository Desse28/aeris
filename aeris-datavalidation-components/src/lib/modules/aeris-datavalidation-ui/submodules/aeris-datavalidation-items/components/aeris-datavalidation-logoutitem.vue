<template>
  <v-list-item>
    <v-tab
        v-on:click="logout"
    >
      <v-list-item-content>
        <v-list-item-title>
          {{ $t('appbar.logout') }}
        </v-list-item-title>
      </v-list-item-content>
      <v-list-item-action>
        <v-icon>mdi-logout</v-icon>
      </v-list-item-action>
    </v-tab>
  </v-list-item>
</template>

<script>
import {keycloak}  from '../../../../../../../plugins/keycloak'

export default {
  name: "aeris-datavalidation-loginmenu",
  watch: {
    '$store.state.common.authenticated': function() {
      this.authenticated = this.$store.state.common.authenticated
    }
  },
  data: function() {
    return {
      authenticated : false,
    }
  },
  methods: {
    logout : function() {
      keycloak.logout({ redirectUri: process.env.VUE_APP_ORIGN_URL })
    }
  },
}
</script>

<style scoped>
</style>