<template>
  <div style="width: 100%; height: 100%" >
    <div
      class="dayRect"
      style="
        position: absolute;
        background-color: white;
        box-shadow: 1px 1px 1px black;
      "
    ></div>
    <div class="individual_modal" v-if="showindiModal" @click="closeIndividual">
      <Individual :student = "clickedStudent" class="modal"/>
    </div>
    <v-simple-table fixed-header padding="0" height="80vh">
      <template v-slot:default>
        <thead>
          <tr>
            <th rowspan="2" style="width: 10%">이름</th>
            <th rowspan="2" style="width: 10%">지역</th>
            <th colspan="7" style="width: 14%" v-for="week in 7" :key="week.id">
              {{ week }}주차
            </th>
          </tr>
          <tr>
            <th
              v-for="day in 50"
              :key="day"
              :class="{ weekend: isWeekend(day - 1) }"
              class="cell"
            >
              {{ days[day % 7] }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="student in students" :key="student.git_id">
            <td class="fix" @click="showIndividual(student)">{{ student.name }}</td>
            <td class="fix">{{ student.region.toUpperCase() }}</td>
            <td
              v-for="(commit, index) in student.commits.slice(10, 60)"
              :key="index"
              :class="{ weekend: isWeekend(index) }"
            >
              {{ commit }}
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import Individual from './Individual.vue'
export default {
  components: {Individual},
  data() {
    return {
      updated_time: "",
      students: [],
      totalDays: 0,
      days: ["일", "월", "화", "수", "목", "금", "토"],
      startDays: [10, 60],
      clickedStudent : '',
      showindiModal : false
    };
  },
  computed: {
    
  },
  created() {
    this.getContributions();
  },
  methods: {
    isWeekend: (num) => {
      let mod = num % 7;
      return mod === 6 || mod === 5;
    },
    getContributions() {
      this.students = this.$store.state.contributions.students;
      this.updated_time = this.$store.state.contributions.updated_time;
      this.totalDays = this.students[0].commits.length;
    },
    showIndividual(student){
      this.clickedStudent = student;
      this.showindiModal = true;
    },
    closeIndividual(){
      this.showindiModal = false;
    }
  },
};
</script>

<style scoped>
.v-window {
  height: 100%;
}

.v-window-item {
  height: 100%;
}

th,
td {
  text-align: center;
  padding: 0 0 !important;
  border: none;
}

.container--fluid {
  max-width: 100%;
  /* overflow-y: scroll; */
  /* height: 100%; */
}

.cell {
  width: 35px;
}
td,
th {
  padding: 0 0 !important;
  border: 1px solid #00000005;
}

.fix {
  position: inherit;
}

.weekday {
  color: black;
}

.weekend {
  color: red !important;
}
.individual_modal{
    position: fixed;
    width: 100vw;
    height: 100vh;
    left: 0;
    z-index: 121;
    top: 0;
    padding: 5vw 10vh;
}
.modal{
  background-color : white;
  width : 90vw;
  height : 80vh;
}
</style>