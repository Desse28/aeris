<template>
  <div class="text-center">
    <v-menu
        v-model="menu"
        :close-on-content-click="false"
        :nudge-width="200"
        offset-x
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="indigo"
            dark
            v-bind="attrs"
            v-on="on"
        >
          Add option(s)
        </v-btn>
      </template>

      <v-card>
        <v-row justify="center">
          <v-col cols="6">
            <v-list>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>Options</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-divider></v-divider>
            <v-list>
              <v-list-item>
                <v-list-item-action>
                  <v-switch v-model="opt1" color="purple"></v-switch>
                </v-list-item-action>
                <v-list-item-title>Options1</v-list-item-title>
              </v-list-item>
              <v-list-item>
                <v-list-item-action>
                  <v-switch v-model="opt2" color="purple"></v-switch>
                </v-list-item-action>
                <v-list-item-title>Options2</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-col>
          <v-col cols="6">
            <v-list>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title>Mode</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
            <v-divider></v-divider>
            <v-list>
              <v-list-item>
                <v-list-item-action>
                  <v-switch v-model="mode1" color="purple"></v-switch>
                </v-list-item-action>
                <v-list-item-title>Mode1</v-list-item-title>
              </v-list-item>
              <v-list-item>
                <v-list-item-action>
                  <v-switch v-model="mode2" color="purple"></v-switch>
                </v-list-item-action>
                <v-list-item-title>Mode2</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-col>
        </v-row>
      </v-card>
    </v-menu>
  </div>
</template>
<script>
export default {
  name: "aeris-datavalidation-options",
  data() {
    return {
      opt1: false,
      opt2: false,
      mode1: true,
      mode2: false,
    }
  },
}
</script>

<style scoped>
</style>