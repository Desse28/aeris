<template>
  <v-list-item  v-if="!authenticated">
    <v-tab
        v-on:click="login"
    >
      <v-list-item-content>
        <v-list-item-title>
          {{ $t('appbar.login') }}
        </v-list-item-title>
      </v-list-item-content>
      <v-list-item-action>
        <v-icon>mdi-login</v-icon>
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
      if(this.authenticated)
        this.$router.push( '/data-validation-tool').catch(()=>{})
    }
  },
  data: function() {
    return {
      authenticated : false,
    }
  },
  methods: {
    login : function() {
      keycloak.login({ redirectUri: process.env.VUE_APP_ORIGN_URL + '/data-validation-tool' })
    },
  },
}
</script>

<style scoped>

</style>