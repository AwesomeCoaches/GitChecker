<template>
  <div style="width:100%; height:100%;">
      <div v-for="(student,index) in dummyData" :key="(student,index)">
      {{student}}
      </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
    components: {
    
  },
    data() {
    return {
      dummyData: '',
    };
  },
    mounted(){
        axios.get(`http://localhost:8080/students/`)
        .then((res)=>{
          console.log(res);
          this.dummyData = res.data;
        })
        .catch(function (error) {
            console.log(error)
        }
        )

    }
}
</script>

<style>

</style>