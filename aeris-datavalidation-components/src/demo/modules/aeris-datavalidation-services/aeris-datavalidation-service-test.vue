<template>
  <div>
    <AerisMetadataServices
        serverUrl="http://localhost:9001"
        flagUrl="/flags"
        groundDataUrl="/ground-data"
        dataInfoUrl="/data-information"
    >
    </AerisMetadataServices>
  </div>
</template>
<script>
import  AerisDataValidationServices from "../../../lib/modules/aeris-datavalidation-services/components/aeris-datavalidation-services"
  export default {
    name: "aeris-datavalidation-service-test",
    components: {
      AerisMetadataServices: AerisDataValidationServices,
    }
  }
</script>
<style scoped>
</style>