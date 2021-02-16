<template>
  <v-footer
    color="#79b4d9"
    padless
    fixed
  >
    <v-row
      justify="space-around"
      no-gutters
    >
      <v-btn
        v-for="link in links"
        :key="link"
        color="white"
        text
        rounded
        class="my-2"
        @click="$emit('gotolink', link)"
      >
        
        {{ link }}
        
      </v-btn>
    </v-row>
  </v-footer>
</template>

<script>
  export default {
    name: 'CommonFooter',
    data: () => ({
      links: [
        'Home',
        'aboutus',
        'reviews',
        'contact',
      ],
    }),
  }
</script>
