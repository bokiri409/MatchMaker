<template>
  <section
    id="welcome"
    class="overflow-hidden"
  >
    <v-row no-gutters>
      <v-col
        class="hidden-sm-and-down"
        md="6"
      >
        <v-img
          :src="require('@/assets/main.jpg')"
          height="100vh"
        />
      </v-col>

      <v-col
        class="align-content-space-between layout wrap"
        cols="12"
        md="6"
      >
        <base-bubble-1
          style="transform: rotate(180deg) translateY(25%)"
        />

        <v-row
          align="center"
          justify="center"
        >
          <v-col
            cols="10"
            md="6"
          >
            <base-heading>Welcome!</base-heading>
            <base-text>
              안전한 장소에서<br>
              새로운 사람을 만나보세요
            </base-text>
            <input
              type="url"
              id="input_url"
            >
            <v-btn
              class="ma-2"
              outlined
              color="#79b4d9"
              small
              href="http://localhost:8081/#/user/joinsession"
            >
              Enter
            </v-btn>
          </v-col>
        </v-row>

        <base-bubble-2
          style="transform: rotate(180deg) translate(-200px, -15%)"
        />
      </v-col>
    </v-row>
  </section>
</template>

<style scoped>
#input_url {
  border: 1px solid #79b4d9;
  border-radius: 5px;
}
</style>
