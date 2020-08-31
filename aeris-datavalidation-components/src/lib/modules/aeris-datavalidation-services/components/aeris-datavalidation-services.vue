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
      url : {
        type : String,
        default : ""
      },
      callBack : {
        type : Function,
      },
    },
    data() {
      return {
        currentGroundData: null,
        error: null,
      }
    },
    watch: {
      '$store.state.common.authenticated': function() {
        if(this.$store.state.common.authenticated ) {
          this.refresh();
        }
      },
      url : function () {
        if(this.$store.state.common.authenticated ) {
          this.refresh();
        }
      }
    },
    methods: {
      refresh() {
        if(this.url !== "")
          this.getData();
      },
      getData() {
        api.getData(this.url)
            .then(response => {
              if(this.callBack)
                this.callBack(response.data)
            })
            .catch(error => {
              console.log("Test getData (Error) : ")
              console.log(error)
              this.error = "Failed to load groundData" + error
            })
            .finally(() => this.loading = false)
      },
    },
  }
</script>
<style scoped>
</style>