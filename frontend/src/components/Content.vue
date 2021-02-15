<template>
  <div class="content">
    Content<br />
    {{ dummy }}
  </div>
</template>

<script>
export default {
  mounted() {
    console.log(this.dummy);
  },
  props: {
    dummy: Object,
  },
};
</script>

<style scoped>
.content {
  /* width: 100%;
	height: 100%; */
  /* min-width: 500px;
	min-height: 500px; */

  height: 200px;
  border: dashed 4px lightsalmon;
  background: lightyellow;
}
</style>