<template>
	<div class="content">
		<h2>{{ data }}</h2>
	</div>
</template>

<script>
	export default {
		props: {
			data: Object,
		},

	}
</script>

<style scoped>
.content {
	/* width: 100%;
	height: 100%; */
	/* min-width: 500px;
	min-height: 500px; */
	
	border: dashed 4px lightsalmon;
}
</style>