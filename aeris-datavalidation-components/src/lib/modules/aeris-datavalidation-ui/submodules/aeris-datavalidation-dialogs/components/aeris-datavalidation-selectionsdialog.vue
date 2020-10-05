<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="800">
      <v-card>
         <v-alert type="warning">
           {{title}}
         </v-alert>
        <v-card-text class="d-flex justify-center">
          <AerisDatavalidationTypography
              :text="message"
              type="h6"
          />
        </v-card-text>
        <v-card-actions class="d-flex justify-center">
          <v-btn color="green darken-1" text @click="okCallBack">
            {{ok}}
          </v-btn>
          <v-btn color="green darken-1" text @click="cancelCallBack">
            {{cancel}}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>
import AerisDatavalidationTypography from "./../../aeris-datavalidation-typographies/components/aeris-datavalidation-typography"
export default {
  name: "aeris-datavalidation-deletedialog",
  props: {
    dialog : {
      type : Boolean,
      default: () => false
    },
    title : {
      type : String,
      default: () => "Dialog title"
    },
    message : {
      type : String,
      default: () => "Dialog message"
    },
    ok : {
      type : String,
      default: () => "OK"
    },
    cancel: {
      type: String,
      default: () => "cancel"
    },
    okCallBack: {
      type: Function,
      default: () => {}
    },
    cancelCallBack: {
      type: Function,
      default: () => {}
    }
  },
  components : {
    AerisDatavalidationTypography
  }
}
</script>

<style scoped>

</style>