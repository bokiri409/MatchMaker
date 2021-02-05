<template>
<div v-if="streamManager">
	<ov-video :stream-manager="streamManager"/>
</div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
	},

	methods: {
		
	},
};
</script>
