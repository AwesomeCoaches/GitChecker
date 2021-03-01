<template>
  <div class=sidebar>
    <div class="filter">
      <div>지역</div>
      <div v-for="item in city" :key="item.id">
        <!-- <router-link :to="`city=${item}`"> {{item}} </router-link> -->
        <input type="checkbox" v-model="checkbox.city" :value="item">
        <label> {{ item }}</label>
      </div>
    </div>
    <div class="filter">
      <div>반</div>
      <div v-for="item in classes" :key="item.id">
        <!-- <router-link :to="`class=${item}`"> {{item}} </router-link> -->
        <input type="checkbox" v-model="checkbox.classes" :value="item">
        <label> {{ item }}</label>
      </div>
    </div>
    <div class="filter">
      <div>팀</div>
      <div v-for="item in team" :key="item.id">
        <!-- <router-link :to="`team=${item}`"> {{item}} </router-link> -->
        <input type="checkbox" v-model="checkbox.team" :value="item">
        <label> {{ item }}</label>
      </div>
    </div>
    <v-btn @click="applyFilter"> 적용 </v-btn>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        city: ["서울", "대전", "광주", "구미"],
        classes: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
        team: [1, 2, 3, 4, 5, 6, 7, 8],
        checkbox: {
          city: [],
          classes: [],
          team: [],
        }
      }
    },
    methods: {
      applyFilter() {
        console.log(this.checkbox); 
      }
    },
  }
</script>

<style scoped>
.sidebar {
    width: 250px;
    height: 100%;
    min-width: 200px;
    min-height: 500px;
    padding: 12px;
    /* border: dashed 4px skyblue; */
    box-shadow: 1px -10px 15px #0000002b;
    z-index: 2;
}

.filter {
  margin: 20px 0;
}
</style>