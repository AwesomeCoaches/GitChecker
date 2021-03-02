<template>
  <div class="sidebar">
    <div class="filter">
      <div class="filter-header">필터 설정</div>
      <div class="filter-contents">
        <v-select
          :items="city"
          label="지역 선택"
          outlined
          dense
          v-model="filter.city"
        ></v-select>
        <v-select
          :items="classes"
          label="반 선택"
          outlined
          dense
          v-model="filter.classes"
        ></v-select>
        <v-select
          :items="team"
          label="팀 선택"
          outlined
          dense
          v-model="filter.team"
        ></v-select>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      city: ["", "서울", "대전", "광주", "구미"],
      classes: ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
      team: ["", 1, 2, 3, 4, 5, 6, 7, 8],
      filter: {
        city: "",
        classes: "",
        team: "",
      },
    };
  },
  methods: {},
};
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
  margin: 20px 0 0 0;
  padding: 20px 20px 20px 20px;

  font-size: 0.75rem;
}
.filter-header {
  margin-bottom: 20px;
}
</style>