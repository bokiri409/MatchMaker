<template>
  <v-app-bar
    v-scroll="onScroll"
    :color="!isScrolling ? 'transparent' : 'white'"
    fixed
    flat
  >
    <v-slide-x-transition>
      <v-img
        v-if="showLogo"
        :src="require('@/assets/matchmaker.png')"
        class="shrink"
        contain
        height="50"
      />
    </v-slide-x-transition>

    <v-spacer />
    <v-btn>
      하이
    </v-btn>
    <base-btn
      class="ml-3"
      large
      href="http://localhost:8081/#/user/join"
      color="#79b4d9"
    >
      Sign Up

    </base-btn>

    <base-btn
      class="ml-3"
      large
      href="http://localhost:8081/#/user/login"
      color="#79b4d9"
    >
      Login

    </base-btn>
  </v-app-bar>
</template>

<script>
  export default {
    name: 'CommonAppBar',

    data: () => ({
      showLogo: false,
      isScrolling: false,
    }),

    methods: {
      onScroll () {
        const offset = window.pageYOffset
        this.isScrolling = offset > 50
        this.showLogo = offset > 200
      },
    },
  }
</script>
