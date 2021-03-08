<template>
  <div style="width:100%; height:100%;">
    <div class="dayRect" style="position:absolute; background-color:white; box-shadow:1px 1px 1px black;" :style="{display:thisDay.ondisplay,'margin-left':thisDay.marginLeft+'px','margin-top':thisDay.marginTop+'px'}">{{thisDay.day}}</div>
    <v-simple-table
    fixed-header
    padding="0"
    height="80vh"
    width="90vw"
    >
    <template v-slot:default >
      <thead >
        <tr>
          <th rowspan="2" style="width:5%;">
            이름
          </th>
           <th rowspan="2" style="width:4%;">
            지역/반
          </th>
          <th rowspan="2" style="width:7%;">
            지라코드
          </th>
          <th style="width:14%;" v-for="week in 6" :key="week.id">
            <td colspan="5" style="width:100%;">{{week}}주차</td>
          </th>
        </tr>
        <tr>
          <th style="width:13%;" v-for="week in 6" :key="week.id">
              <div class="weekday cell" @mouseover="mouseoverDay($event,week*7-7)" @mouseout="mouseoutDay">월</div>
              <div class="weekday cell" @mouseover="mouseoverDay($event,week*7-6)" @mouseout="mouseoutDay">화</div>
              <div class="weekday cell" @mouseover="mouseoverDay($event,week*7-5)" @mouseout="mouseoutDay">수</div>
              <div class="weekday cell" @mouseover="mouseoverDay($event,week*7-4)" @mouseout="mouseoutDay">목</div>
              <div class="weekday cell" @mouseover="mouseoverDay($event,week*7-3)" @mouseout="mouseoutDay">금</div>
              <div class="weekend cell" @mouseover="mouseoverDay($event,week*7-2)" @mouseout="mouseoutDay">토</div>
              <div class="weekend cell" @onClick="mouseoverDay($event,week*7-1)" @mouseout="mouseoutDay">일</div>
              <div class="cell">C</div>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="student in students"
          :key="student.id"
        >
          <td class="fix">{{student.name}}</td>
          <td class="fix">{{student.city}}/{{student.class}}</td>
          <td class="fix">s04p13{{student.team_id}}</td>
          <td v-for="week in 6" :key="week.id">
            <div 
              v-for="day in 7" 
              :key="day" 
              class="cell" 
              :style="{'box-shadow':rows[(week-1)*5+(day-1)]}" 
              @mouseover="mouseoverDay($event,(week-1)*5+(day))" 
              @mouseout="mouseoutDay"
            >{{ student.commits[(week - 1) * 7 + day - 1] }}</div>
            <div class="cell">X</div>
          </td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
  </div>
</template>

<script>
import axios from "axios";
const URL = "http://t4coach44.p.ssafy.io/api";
// const URL = 'http://localhost:8080';
export default {
  components: {},
  data() {
    return {
      studentNum: 0,
      students: [],
      contributions: [],
      projectTerms: [],
      getitems: "",
      nowProjectType: 2, //현재 진행하고 있는 프로젝트 타입
      days: [],
      Today: "",
      thisDay: "",
      rows: [],
      commitDatas: {},
    };
  },
  computed: {
    st: (name) => {
      return this.commitDatas[name][0];
    },
  },
  created() {
    this.getStudents();
    this.getProjectTerms();
  },
  methods: {
    mouseoverDay(event, day) {
      var clientRect = event.target.getBoundingClientRect();
      var dayRectMarginLeft = clientRect.left - 300;
      var dayRectMarginTop = clientRect.top - 100;
      this.thisDay = {
        day: this.days[day],
        ondisplay: "block",
        marginLeft: dayRectMarginLeft,
        marginTop: dayRectMarginTop,
      };
    },
    mouseoutDay() {
      this.thisDay = { day: "", ondisplay: "none" };
    },
    /**
     * In a real application this would be a call to fetch() or axios.get()
     */
    getStudents() {
      axios
        .get(`${URL}/students/`)
        .then((res) => {
          this.students = res.data;
          this.students.forEach((student) => {
            this.commitDatas[student.username] = student;
            this.commitDatas[student.username].commits = [];
          });
          this.totalDesserts = res.data.length;
          this.getContributions();
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getContributions() {
      axios
        .get(`${URL}/contributions/`)
        .then((res) => {
          this.contributions = res.data;
          this.setContributions();
        })
        .catch(function (error) {
            console.log(error)
        }
        )
      },
    // setContributions(){
    //   // for(var j = 0; j<this.studentNum;j++){
    //   //   for(var i = 0; i<42;i++){
    //   //     this.commitDatas[j*42+i] = {num:0,color:''};
    //   //   }
    //   // }
    //   // console.log(this.commitDatas);

    //   // var index = 0;
    //   // this.contributions.forEach(contribution =>{
    //   //     // console.log(contribution);
    //   //     var conDay = contribution.date.split("T")[0];
    //   //     var computingDayNum = 0;
    //   //     for(var i=0; i<42;i++){
    //   //       if(conDay==this.days[i]){
    //   //         computingDayNum = i;
    //   //         console.log(this.days[i]);
    //   //     }
    //   //     var commitColor = '';
    //   //     if (contribution.cnt>40) {
    //   //       commitColor = "green";
    //   //     }else{
    //   //       commitColor = "yellow"
    //   //     }
    //   //     this.commitDatas[index*42+computingDayNum] = {num:contribution.cnt,color:commitColor}
    //   //     index = index +1;
    //   //   } 
    //   // });//end of forEach
    //   console.log(this.contributions)
    //     this.contributions.forEach(contribution =>{
    //       if (!Object.prototype.hasOwnProperty.call(this.commitDatas, contribution.student.id)) {
    //         this.commitDatas[contribution.student.id] = {};
    //       }
    //       this.commitDatas[contribution.student.id][contribution.date] = contribution.cnt;
    //     });
    //   console.log(this.commitDatas);
    // },
    setContributions() {
      this.contributions.forEach((contribution) => {
        this.commitDatas[contribution.student.username].commits.push(
          contribution.cnt
        );
        // console.log(contribution);
        // var conDay = contribution.date.split("T")[0];
        // var computingDayNum = 0;
        // for(var i=0; i<42;i++){
        //   if(conDay==this.days[i]){
        //     computingDayNum = i;
        //     console.log(this.days[i]);
        // }
        // var commitColor = '';
        // if (contribution.cnt>40) {
        //   commitColor = "green";
        // }else{
        //   commitColor = "yellow"
        // }
        // this.commitDatas[contribution.student.id*100+computingDayNum] = {num:contribution.cnt,color:commitColor}
        // }
      }); //end of forEach
    },
    getProjectTerms() {
      // axios.get(`http://localhost:8080/contributions/`)
      // .then((res)=>{
      //   console.log(res);
      //   this.students = res.data;
      //   this.totalDesserts = res.data.length;
      // })
      // .catch(function (error) {
      //     console.log(error)
      // }
      // )
      this.projectTerms = [
        {
          id: 1,
          end_date: "2021-04-09",
          grp: 1,
          start_date: "2021-03-01",
          type: 2,
        },
      ];

      var moment = require("moment");
      var day = moment(this.projectTerms[0].start_date);
      var today = moment().format("YYYY-MM-DD");
      // // for(var w=0; w<6; w++){
      // //   for(var i=0; i<5; i++){
      // //     // console.log(day)
      // //     this.days[w*5+i] =  day.format('YYYY-MM-DD');
      // //     day.add(1,'days');
      // //     if(day.format('YYYY-MM-DD') == today){
      // //       this.Today = w*5+i+1;
      // //       this.rows[this.Today] = '1px 1px 6px #0000004d';
      // //       console.log(day.format('YYYY-MM-DD'))
      // //     }
      // //   }
      // //       day.add(2,'days')
      // // }
      for (var w = 0; w < 42; w++) {
        this.days[w] = day.format("YYYY-MM-DD");
        day.add(1, "days");
        if (day.format("YYYY-MM-DD") == today) {
          this.Today = w + 1;
          this.rows[this.Today] = "1px 1px 6px #0000004d";
        }
      }

      // console.log(this.rows);
    },
  },
};
</script>

<style>
.v-window {
  height: 100%;
}

.v-window-item {
  height: 100%;
}

.charts {
  /* height:50% !important; */
}
.container--fluid {
  max-width: 100%;
  /* overflow-y: scroll; */
  /* height: 100%; */
}

.cell {
  width: 12.5%;
  float: left;
  text-align: center;
  cursor: pointer;
}
tr {
  width: 100%;
}
td,
th {
  padding: 0 0 !important;
  border: 1px solid #00000005;
}

th > td {
  padding: 0 0 !important;
  border: none;
}

.fix {
  position: inherit;
}
.weekday {
  color: black;
}
.weekend {
  color: red;
}
</style>