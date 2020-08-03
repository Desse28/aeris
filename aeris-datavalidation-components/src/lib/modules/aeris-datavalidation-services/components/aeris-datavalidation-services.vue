<template>
  <div>
  </div>
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
      },
      dataInfoUrl : {
        type : String,
        default : ""
      }
    },
    data() {
      return {
        currentGroundData: null,
        error: null,
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
        this.getDataInfo();
        this.getGroundData();
      },
      getFlags () {
        api.getFlagData(this.serverUrl, this.flagUrl)
            .then(response => {
              this.$emit("metadata", response.data, "flags");
            })
            .catch(error => {
              this.error = "Failed to load groundData" + error
            })
            .finally(() => this.loading = false)
      },
      getGroundData () {
        api.getFlagData(this.serverUrl, this.groundDataUrl)
            .then(response => {
              this.$emit("metadata", response.data, "data");
            })
            .catch(error => {
              this.error = "Failed to load groundData" + error
            })
            .finally(() => this.loading = false)
      },
      getDataInfo() {
        api.getFlagData(this.serverUrl, this.dataInfoUrl)
            .then(response => {
              this.$emit("metadata", response.data, "dataInfo");
            })
            .catch(error => {
              this.error = "Failed to load groundData" + error
            })
            .finally(() => this.loading = false)
      }
    },
  }
</script>
<style scoped>
</style>