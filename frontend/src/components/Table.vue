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
    <div class="individual_modal" v-if="showindiModal">
      <v-btn @click="compare" class="VS">VS</v-btn>
      <div @click="closeIndividual" class="close">X</div>
      <Individual :student = "clickedStudent" :class="modalclass"/>
      <Individual :student = "compareStudent" :class="modalclass" v-if="modal_saperate"/>
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
  components: {
    Individual,
    // plus:{
    //   template:`<Individual :student = "clickedStudent" class="modal2"/>`,
    //     }
  },
  data() {
    return {
      updated_time: "",
      students: [],
      totalDays: 0,
      days: ["일", "월", "화", "수", "목", "금", "토"],
      startDays: [10, 60],
      clickedStudent : '',
      compareStudent:'',
      showindiModal : false,
      modalclass:'modal',
      modal_saperate : false
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
      let tempStudent = {
        class:student.class,
        commits : '',
        git_id: '',
        name:'',
        region: student.region
      };
      this.compareStudent = tempStudent;
      this.showindiModal = true;
      // console.log(this.student);
    },
    closeIndividual(){
      this.showindiModal = false;
    },
    compare(){
      if(this.modalclass=='modal'){
        this.modalclass = 'modal2';
        this.modal_saperate = true;
      }else{
        this.modalclass = 'modal';
        this.modal_saperate = false;
      }
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
    background-color: #0000000d;
}
.modal{
  background-color : white;
  width : 90vw;
  height : 80vh;
  box-shadow: 0px 0px 13px #0000001c;
  border-radius: 30px;
  border: 15px solid white;
}
.modal2{
  background-color : white;
  width : 45vw;
  height : 80vh;
  box-shadow: 0px 0px 13px #0000001c;
  border-radius: 30px;
  border: 15px solid white;
  float: left;
}
.VS{
    position: absolute;
    height: 47px;
    margin-top: -18px;
    margin-left: 42.5vw;
    width: 5vw;
    
}
.close{
   position: absolute;
    height: 47px;
    margin-top: -18px;
    right: 5vw;
    width: 5vw;
    text-align: right;
    font-size: xxx-large;
    font-weight: bold;
}
</style>