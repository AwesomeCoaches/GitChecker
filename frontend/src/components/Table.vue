<template>
  <div style="width:100%; height:100%;">
     <v-simple-table
    fixed-header
    height="100%"
  >
    <template v-slot:default>
      <thead>
        <tr>
          <th>
            이름
          </th>
          <th>
            지라코드
          </th>
          <th v-for="week in 6" :key="week">
            <tr>{{week}}주차</tr>
            <tr>
              <th v-for="day in 5" :key="day">{{day}}</th>
            </tr>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(student,index) in students"
          :key="(student,index)"
        >
          <td>{{ student.name }}</td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
  </div>
</template>

<script>
import axios from 'axios';
export default {
    components: {
    
  },
    data () {
      return {
        studentNum: 0,
        students: [],
        loading: false,
        options: {},
        headers: [

          { text: '지역', value: 'city' },
          { text: '반', value: 'class' },
          { text: '팀', value: 'team_id' },
          { text: '이름', value: 'name' }
        ],
        getitems:''
      }
  },
    mounted(){
      // setTimeout(this.getStudents (), 1000)
        this.getStudents ()
        // console.log(this.getitems)
        // this.getDataFromApi();

    },
    methods:{
      getDataFromApi () {
        this.loading = true
        this.fakeApiCall().then(data => {
          console.log(data);
          this.desserts = data.items
          this.totalDesserts = data.total
          this.loading = false
        })
      },
      /**
       * In a real application this would be a call to fetch() or axios.get()
       */
      fakeApiCall () {
        return new Promise((resolve) => {
          const { sortBy, sortDesc, page, itemsPerPage } = this.options

          // this.getStudents()
          let items = this.getitems    
          console.log(items)
          const total = this.items.length

          if (sortBy.length === 1 && sortDesc.length === 1) {
            items = items.sort((a, b) => {
              const sortA = a[sortBy[0]]
              const sortB = b[sortBy[0]]

              if (sortDesc[0]) {
                if (sortA < sortB) return 1
                if (sortA > sortB) return -1
                return 0
              } else {
                if (sortA < sortB) return -1
                if (sortA > sortB) return 1
                return 0
              }
            })
          }

          if (itemsPerPage > 0) {
            items = items.slice((page - 1) * itemsPerPage, page * itemsPerPage)
          }

          setTimeout(() => {
            resolve({
              items,
              total,
            })
          }, 1000)
        })
      },
      getStudents () {
        axios.get(`http://localhost:8080/students/`)
        .then((res)=>{
          console.log(res);
          this.students = res.data;
          this.totalDesserts = res.data.length;
        })
        .catch(function (error) {
            console.log(error)
        }
        )
      },
      getContributions () {
        axios.get(`http://localhost:8080/contributions/`)
        .then((res)=>{
          console.log(res);
          this.students = res.data;
          this.totalDesserts = res.data.length;
        })
        .catch(function (error) {
            console.log(error)
        }
        )
      },
    }
}
</script>

<style>
.v-window{
 height: 100%;
}

.v-window-item{
  height: 100%;
}

.charts{
  /* height:50% !important; */
}
.container--fluid {
    max-width: 100%;
    overflow-y: scroll;
    height: 100%;
}
</style>