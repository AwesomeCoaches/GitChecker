<template>
    <div>
        {{student.name}}
        <canvas id="studentChart" ></canvas>
    </div>
</template>

<script>
import { Line } from "vue-chartjs";

export default {
      extends: Line,
    components: {},
    props: ['student'],
  data() {
    return {
      totalCommit : 0,

    };
  },
  computed: {
    
  },
  mounted() {

  },
  methods: {
    
  },
}
</script>

<style>

</style>