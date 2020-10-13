<template>
  <div
      fluid
      v-if="authenticated"
      style="height: 300px"
      class="mr-3"
  >
    <v-row justify="center">
      <v-menu
          min-width="200px"
          rounded
          offset-y
      >
        <template v-slot:activator="{ on }">
          <v-btn
              icon
              x-large
              v-on="on"
          >
            <v-avatar
                color="brown"
            >
              <span class="white--text headline">{{ user.initials }}</span>
            </v-avatar>
          </v-btn>
        </template>
        <v-card>
          <v-list-item-content class="justify-center">
            <div class="mx-auto text-center">
              <v-avatar
                  color="brown"
              >
                <span class="white--text headline">{{ user.initials }}</span>
              </v-avatar>
              <h3>{{ user.fullName }}</h3>
              <p class="caption mt-1">
                {{ user.email }}
              </p>
              <v-divider class="my-3"></v-divider>
              <v-btn
                  depressed
                  rounded
                  text
                  v-on:click="logout"
              >
                {{$t('appbar.logout')}}
              </v-btn>
            </div>
          </v-list-item-content>
        </v-card>
      </v-menu>
    </v-row>
  </div>
</template>
<script>
import {keycloak}  from '../../../../../../../plugins/keycloak'

export default {
  name: "aeris-datavalidation-accountdialog",
  data() {
    return {
      user: {
        initials: '',
        fullName: '',
        email: '',
      },
      authenticated : null,
    }
  },
  watch: {
    '$store.state.common.authenticated': function() {
      this.authenticated = this.$store.state.common.authenticated
      if(this.authenticated)
        this.$router.push( '/data-validation-tool')
    },
    '$store.state.common.user': function() {
      this.user = this.$store.state.common.user
    }
  },
  mounted() {
    if(!keycloak.authenticated && this.$router.currentRoute.path !== '/')
      this.$router.push( '/')
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