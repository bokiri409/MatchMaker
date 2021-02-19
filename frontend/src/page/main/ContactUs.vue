<template>
  <section
    id="contact-us"
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
          color="#a1cae4"
          height="360"
          width="900"
          elevation="2"
          rounded
          >
            <v-row>

              <v-col
              cols="12"
              md="6"
              >
                <v-layout
                column align-baseline
                >

                  <v-icon x-large id="contact_icon">
                    mdi-city
                  </v-icon>
                  <v-text id="contact_text">
                    Seoul Sangrok Building 508 Eonju-ro,<br>
                  </v-text>
                  <v-text id="contact_text">
                    Gangnam-gu, Seoul
                  </v-text><br><br>

                  <v-icon x-large id="contact_icon">
                    mdi-phone
                  </v-icon>
                  <v-text id="contact_text">
                    010-1234-5678
                  </v-text><br><br>

                  <v-icon x-large id="contact_icon">
                    mdi-email
                  </v-icon>
                  <v-text id="contact_text">
                    match_maker@multicampus.ac.kr
                  </v-text>
                </v-layout>

              </v-col>
              <v-col>
                <v-img
                src="../../assets/map.png"
                id="map_img"
                >

                </v-img>
              </v-col>

            </v-row>

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
  background-color: #bcd9ec;
  height: 910px;
}

#contact_text {
  font: italic;
  font-size: large;
  font-weight: 500;
  text-indent: 0.5em;
}

#map_img {
  height: 335px;
  width: 425px;
  border-radius: 10px;
}

#contact_icon {
  text-indent: 0.1em;
}
</style>