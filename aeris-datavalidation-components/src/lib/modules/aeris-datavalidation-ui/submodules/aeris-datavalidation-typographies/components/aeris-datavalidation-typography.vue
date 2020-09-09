<template>
  <v-item-group mandatory>
    <v-sheet
        tile
    >
      <v-item>
          <div
              :class="[`text-${type}`, `mb-${margin}`]"
              class="transition-swing"
              v-text="text"
          >
          </div>
      </v-item>
    </v-sheet>
  </v-item-group>
</template>
<script>
export default {
  name: "aeris-datavalidation-typography",
  props : {
    margin : {
      type : String,
      default : () => '2.125rem'
    },
    text : {
      type : String,
      default : () => "Heading 4"
    },
    type : {
      type : String,
      default : () => "h4"
    }
  }
}
</script>

<style scoped>
</style>