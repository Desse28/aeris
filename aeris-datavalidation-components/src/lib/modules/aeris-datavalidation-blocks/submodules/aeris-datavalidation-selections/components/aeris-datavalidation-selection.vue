<template>
  <div class="ma-8">
    <AerisDatavalidationChartSteppers/>
  </div>
</template>
<script>

import AerisDatavalidationChartSteppers from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-steppers/components/aeris-datavalidation-chartsteppers"

export default {
  name: "aeris-datavalidation-selection",
  components: {
    AerisDatavalidationChartSteppers,
  },
  data() {
    return {
      data: "",
    }
  },
}
</script>
<style scoped>
</style>