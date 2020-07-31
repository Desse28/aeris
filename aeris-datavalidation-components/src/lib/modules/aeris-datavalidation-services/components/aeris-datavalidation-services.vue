<template>
  <div></div>
</template>

<script>
  import  api from  './../Api/DataApi'

  export default {
    name: "aeris-datavalidation-services",
    props: {
      serverUrl : {
        type : String,
        default : ""
      },
      flagUrl : {
        type : String,
        default : ""
      },
      groundDataUrl : {
        type : String,
        default : ""
      }
    },
    data() {
      return {
        currentGroundData: null
      }
    },
    watch: {
    },
    mounted() {
      this.refresh();
    },
    methods: {
      refresh() {
        this.getFlags();
      },
      getFlags () {
        api.getFlagData(this.serverUrl, this.flagUrl)
            .then(response => {
              console.log("Test get flag : ");
              console.log(response.data);
              //this.chart_data = response.data;
            })
            .catch(error => {
              console.log("Test flag (Error) : ");
              console.log(error);
              //this.error = "Failed to load groundData"
            })
            .finally(() => this.loading = false)
      }
    },
  }
</script>
<style scoped>
</style>