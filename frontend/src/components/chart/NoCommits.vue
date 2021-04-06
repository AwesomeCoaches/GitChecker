<template>
  <div>
    <span v-for="contrib in noCommitStudents" :key="contrib.id">
      {{ contrib.name }}
      {{ contrib.commits.filter((e) => 0 === e).length }}회
    </span>
  </div>
</template>

<script>
export default {
  computed: {
    contributions() {
      return this.$store.getters.datesContributions;
    },
    noCommitStudents() {
      return this.$store.getters.datesContributions.filter((contrib) =>
        contrib.commits.includes(0)
      );
    },
  },
};
</script>

<style lang="scss" scoped>
</style>