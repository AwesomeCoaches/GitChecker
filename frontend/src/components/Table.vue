<template>
  <div style="width: 100%; height: 100%">
    <v-data-table
      :headers="headers"
      :items="students"
      :options.sync="options"
      :server-items-length="studentNum"
      :loading="loading"
      class="elevation-1"
    ></v-data-table>
  </div>
</template>

<script>
import axios from "axios";
export default {
  components: {},
  data() {
    return {
      studentNum: 2,
      students: [
        {
          city: "B",
          class: "1",
          email: "a",
          git_id: "a",
          group: 1,
          id: 1,
          image_url: "a",
          name: "a",
          team_id: "2",
        },
      ],
      loading: false,
      options: {},
      headers: [
        { text: "지역", value: "city" },
        { text: "반", value: "class" },
        { text: "팀", value: "team_id" },
        { text: "이름", value: "name" },
      ],
      getitems: "",
    };
  },
  mounted() {
    // setTimeout(this.getStudents (), 1000)
    this.getStudents();
    // console.log(this.getitems)
    // this.getDataFromApi();
  },
  methods: {
    getDataFromApi() {
      this.loading = true;
      this.fakeApiCall().then((data) => {
        console.log(data);
        this.desserts = data.items;
        this.totalDesserts = data.total;
        this.loading = false;
      });
    },
    /**
     * In a real application this would be a call to fetch() or axios.get()
     */
    fakeApiCall() {
      return new Promise((resolve) => {
        const { sortBy, sortDesc, page, itemsPerPage } = this.options;

        // this.getStudents()
        let items = this.getitems;
        console.log(items);
        const total = this.items.length;

        if (sortBy.length === 1 && sortDesc.length === 1) {
          items = items.sort((a, b) => {
            const sortA = a[sortBy[0]];
            const sortB = b[sortBy[0]];

            if (sortDesc[0]) {
              if (sortA < sortB) return 1;
              if (sortA > sortB) return -1;
              return 0;
            } else {
              if (sortA < sortB) return -1;
              if (sortA > sortB) return 1;
              return 0;
            }
          });
        }

        if (itemsPerPage > 0) {
          items = items.slice((page - 1) * itemsPerPage, page * itemsPerPage);
        }

        setTimeout(() => {
          resolve({
            items,
            total,
          });
        }, 1000);
      });
    },
    getStudents() {
      axios
        .get(`http://localhost:8082/students/`)
        .then((res) => {
          console.log(res);
          this.students = res.data;
          this.totalDesserts = res.data.length;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style>
</style>