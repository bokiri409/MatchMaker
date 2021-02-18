<template>
  <v-app>
    <common-app-bar />

    <router-view/>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  components: {
    CommonAppBar: () => import('../src/components/common/AppBar'),
    CommonFooter: () => import('../src/components/common/Footer'),
    CommonView: () => import('../src/components/common/View'),
  },
}
</script>
