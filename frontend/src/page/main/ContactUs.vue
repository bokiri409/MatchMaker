<template>
  <section
    id="get-in-touch"
    class="overflow-hidden"
  >
    <v-row
      no-gutters
    >
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
            <base-heading>개선사항은<br>언제든<br>환영합니다</base-heading>
            <base-text>
              부담없이 연락해주세요<br>
              가능한 빨리 문제를 해결하겠습니다
            </base-text>
          </v-col>
        </v-row>

        <base-bubble-2
          style="transform: rotate(180deg) translate(-200px, -15%)"
        />
      </v-col>
      <v-col
        class="pa-5"
        cols="12"
        md="6"
        id="contact_left_side"
      >
        <base-bubble-1 />

        <base-heading class="mb-5">
          Contact Us
        </base-heading>

        <v-sheet
          color="transparent"
          max-width="600"
        >
          <v-text-field
            color="info"
            label="Name"
            solo
            flat
          />

          <v-text-field
            color="info"
            label="Email"
            solo
            flat
          />

          <v-text-field
            color="info"
            label="Subject"
            solo
            flat
          />

          <v-textarea
            color="info"
            label="Message"
            solo
            flat
          />

          <base-btn>Send</base-btn>
        </v-sheet>
      </v-col>

      
    </v-row>
  </section>
</template>

<script>
  export default {
    name: 'ContactUs',
  }
</script>

<style scoped>
#contact_left_side {
  background-color: #79b4d9;
  height: 910px;
}
</style>