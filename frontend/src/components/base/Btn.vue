<template>
  <v-btn
    color="#d96c9f"
    rounded
    v-bind="$attrs"
    v-on="$listeners"
  >
    <slot />
  </v-btn>
</template>

<script>
  export default {
    name: 'BaseBtn',
  }
</script>
