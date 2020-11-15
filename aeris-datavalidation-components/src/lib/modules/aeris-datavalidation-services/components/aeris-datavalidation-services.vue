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
      typeOfRequest : {
        type : String,
        default: "GET"
      },
      requestData : {
        type : Object,
        default: null
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
      },
    },
    methods: {
      refresh() {
        if(this.url !== "") {
          if(this.typeOfRequest === "GET")
            this.getData()
          else if(this.typeOfRequest === "POST")
            this.postData()
          else if(this.typeOfRequest === "PUT")
            this.putData()
        }
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
      postData() {
        api.create(this.url, this.requestData)
            .then(response => {
              if(this.callBack)
                this.callBack(response.data)
            })
            .catch(error => {
              console.log("Test create (Error) : ")
              console.log(error)
              this.error = "Failed to load create" + error
            })
            .finally(() => this.loading = false)
      },
      putData() {
        api.update(this.url, this.requestData)
            .then(response => {
              if(this.callBack)
                this.callBack(response.data)
            })
            .catch(error => {
              console.log("Test update (Error) : ")
              console.log(error)
              this.error = "Failed to load update" + error
            })
            .finally(() => this.loading = false)
      }
    },
  }
</script>
<style scoped>
</style>