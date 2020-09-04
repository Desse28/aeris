<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">

      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="primary"
            dark
            v-bind="attrs"
            v-on="on"
        >
          Open Dialog
        </v-btn>
      </template>

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
            :setDialogue="setDialogue"
        />
        <AerisDatavalidationSessionList
            v-if="targetItem === 'Continue session'"
            :setDialogue="setDialogue"
        />
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>
import AerisDatavalidationSessionForm from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-form/components/aeris-datavalition-sessionform"
import AerisDatavalidationSessionList from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-lists/components/aeris-datavalidation-sessionlist"
export default {
  name: "aeris-datavalidation-configuration",
  components: {
    AerisDatavalidationSessionForm,
    AerisDatavalidationSessionList
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
    }
  },
  methods : {
    configutionHandler : function (targetItem) {
      this.targetItem = targetItem
    },
    setDialogue : function () {
      this.dialog = this.dialog !== true;
    },
  }
}
</script>

<style scoped>

</style>