<template>
  <div id="app">
    <v-app>
      <v-app-bar
              app
              color="primary"
              dark
      >
        <div class="d-flex align-center">
          <v-img
                  alt="Vuetify Logo"
                  class="shrink mr-2"
                  contain
                  src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
                  transition="scale-transition"
                  width="40"
          />
          <v-img
                  alt="Vuetify Name"
                  class="shrink mt-1 hidden-sm-and-down"
                  contain
                  min-width="100"
                  src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png"
                  width="100"
          />
        </div>

        <v-spacer></v-spacer>

        <v-list
                class="d-flex flex-row"
                color="primary"
        >
          <v-list-item
                  v-for="item in items"
                  :key="item.text"
                  link
          >
            <v-tab :to="item.to">
              <v-list-item-content>
                <v-list-item-title>
                  {{ item.text }}
                </v-list-item-title>
              </v-list-item-content>
              <v-list-item-action>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-item-action>
            </v-tab>
          </v-list-item>
        </v-list>

        <v-btn
                href="https://github.com/vuetifyjs/vuetify/releases/latest"
                target="_blank"
                text
        >
          <span class="mr-2">Latest Release</span>
          <v-icon>mdi-open-in-new</v-icon>
        </v-btn>
      </v-app-bar>
      <v-main>
        <router-view/>
      </v-main>
    </v-app>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  },
  data: function() {
    return {
      items: [
        { icon: 'mdi-home', text: 'Data validation tool', to : "/data-validation-tool" },
        { icon: 'mdi-cog', text: 'statistics', to : "/settings" },
      ],
    }
  },
};
</script>
