<template>
  <div>
    <h3>Ground data page</h3>
    <AerisDataValidationServicesTest/>
  </div>
</template>
<script>
    import  AerisDataValidationServicesTest from "../../aeris-datavalidation-services/aeris-datavalidation-service-test"

    export default {
      name: 'catalogue-ground-data.vue',
      components: {
        AerisDataValidationServicesTest: AerisDataValidationServicesTest,
      },
    };
</script>
