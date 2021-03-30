<template>
    <div>
      <div  class="student">
        <div v-if="student.name==''">
            <v-select
              :items="region"
              :label="now.region+'지역'"
              outlined
            ></v-select>
            <v-select
              :items="classes"
              :label="now.class+'반'"
              outlined
            ></v-select>
            <v-select
              :items="team"
              :label="now.team+'팀'"
              outlined
            ></v-select>
            <v-select
              :items="member"
              label="이름"
              outlined
            ></v-select>
          <v-btn>비교하기</v-btn>
        </div>

        <div v-if="student.name!=''" class="Chart">
          <h1 style="text-align:center;">{{student.region}}{{student.class[1]}} {{student.name}}( {{student.git_id}} )</h1>
          <div class= "cards">
            <div class="card">
              <h3>
                총 커밋 수
              </h3>
              <div class="article">
                {{totalCommitNum}}회
              </div>
            </div>

              <div class="card">
              <h3>
                커밋 안한 날 수
              </h3>
              <div class="article">
                {{notCommitedDayNum}}일
              </div>
            </div>

              <div class="card">
              <h3>
                팀 기여도
              </h3>
              <div class="article">
                {{notCommitedDayNum}}%
              </div>
            </div>
          </div>
          <line-example :chartdata="Object(student.commits)" :options="options" style="height:50%; width:100%; margin-bottom:0px"/>
        </div>
      </div>
    </div>
</template>

<script>
import LineExample from './chart/IndividualChar'

export default {
    components: {LineExample},
    props: ['student'],
  data() {
    return {
      now:{
        region:'',
        class:'',
        team:'',
        name:'',
      },
      region:
        ["",
        '서울',
        '대전',
        '광주',
        '구미'
        ],
      classes:["",1,2,3,4,5,6],
      team:["",1,2,3,4,5,6,7,8,9,10,11],
      member:[],
      totalCommitNum : 0,
      notCommitedDayNum : 0,
      options:[]
    };
  },
  computed: {
    
  },
  mounted() {
    if(this.student.name!=''){
      for (let day = 0; day < this.student.commits.length; day++) {
        let value = this.student.commits[day];
        this.options.push(day);
        if(value==0){
          this.notCommitedDayNum +=1;
        }else{
          this.totalCommitNum += value; 
        }  
      }
    }else{ //비교될 대상
      this.now.region = this.student.region;
      this.now.class = this.student.class[1].charAt(0);
      this.now.team = this.student.class[1].charAt(2);
    }
  },
  methods: {
    
  },
}
</script>

<style>
h1{
  padding : 30px 0px;
}

h3{
  padding:20px 0px;
  color:#232121;
}
.Chart{
  width:100%;
  height:100%;
}
.student{
  width: 100%;
    height: 100%;
    border-radius: 18px;
    box-shadow: 0px 0px 8px inset #00000030;
}
.cards{
    height: 35%;
    width:100%;
    padding-top: 2%;
}
.card{
  width: 20%;
  height: 70%;
  float: left;
  text-align: center;
  margin-left: 10%;
  box-shadow: 0px 0px 9px inset #00000038;
  border-radius: 32px;
}

.article{
  font-size: xxx-large;
  font-weight: bold;
}
</style>