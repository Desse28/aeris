<template>
  <div>
    <slot/>
  </div>
</template>

<script>
  import  api from '../api/api'

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
      '$store.state.common.authenticated': function() {
        if( this.$store.state.common.authenticated ) {
          //this.refresh();
        }
      }
    },
    methods: {
      refresh() {
        this.getFlags();
        this.getDataInfo();
        this.getGroundData();
      },
      getFlags () {
        api.getFlagData(this.flagUrl)
            .then(response => {
              this.$emit("metadata", response.data, "flags")
            })
            .catch(error => {
              console.log("Test get flags (Error) : ")
              console.log(error)
              this.error = "Failed to load groundData" + error
            })
            .finally(() => this.loading = false)
      },
      getGroundData () {
        api.getFlagData(this.groundDataUrl)
            .then(response => {
              this.$emit("metadata", response.data, "data");
            })
            .catch(error => {
              this.error = "Failed to load groundData" + error
            })
            .finally(() => this.loading = false)
      },
      getDataInfo() {
        api.getFlagData(this.dataInfoUrl)
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