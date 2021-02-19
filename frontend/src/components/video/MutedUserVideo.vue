<template>
<div v-if="streamManager">
	<muted-ov-video :stream-manager="streamManager"/>
</div>
</template>

<script>
import MutedOvVideo from './MutedOvVideo';

export default {
	name: 'UserVideo',

	components: {
		MutedOvVideo,
	},

	props: {
		streamManager: Object,
	},

	methods: {
		
	},
};
</script>
