<template>
  <section
    id="recent-projects"
    class="overflow-hidden"
  >
    <v-row no-gutters>
      <v-col
        id = "reviews_background"
        class="text-center pa-5 white--text"
        cols="12"
        md="6"
      >
        <base-bubble-1
          style="transform: rotate(180deg) translateX(25%)"
        />

        <base-heading>
          Recent Reviews
        </base-heading>

        <base-text class="mb-5">
          Lorem ipsum dolor sit amet, consectetur adipi<br>
          scin elit. Etiam vulputate augue vel felis gravida<br>
          porta. Lorem ipsum dolor sit amet.
        </base-text>
      
        <v-card id="review_card">
          <v-container class="pa-2">
            <v-row>
              <v-col cols="12" md="6">
                <figure class="snip1504">
                  <img src="../../assets/11.jpg" alt="sample57">
                  <figcaption>
                    <h3>Benjamin Evalent</h3>
                    <h5>좋아요</h5>
                  </figcaption>
                  <a href="#"></a>
                </figure>
              </v-col>
              <v-col cols="12" md="6">

                <figure class="snip1504">
                  <img src="../../assets/22.jpg" alt="sample57">
                  <figcaption>
                    <h3>Benjamin Evalent</h3>
                    <h5>아주 좋아요</h5>
                  </figcaption>
                  <a href="#"></a>
                </figure>
              </v-col>
              <v-col cols="12" md="6">
                <figure class="snip1504">
                  <img src="../../assets/33.jpg" alt="sample57">
                  <figcaption>
                    <h3>Benjamin Evalent</h3>
                    <h5>진짜루</h5>
                  </figcaption>
                  <a href="#"></a>
                </figure>
              </v-col>
              <v-col cols="12" md="6">

                <figure class="snip1504">
                  <img src="../../assets/44.png" alt="sample57">
                  <figcaption>
                    <h3>Benjamin Evalent</h3>
                    <h5>좋아요</h5>
                  </figcaption>
                  <a href="#"></a>
                </figure>
              </v-col>


              
            </v-row>
          </v-container>
        </v-card>
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
            <base-heading>다른 사람들의<br>경험을<br>확인해보세요</base-heading>
            <base-text>
              이미 많은 사람들이<br>
              새로운 인연을 만들었습니다
            </base-text>
            
          </v-col>
        </v-row>

        <base-bubble-2
          style="transform: rotate(180deg) translate(-200px, -15%)"
        />
      </v-col>

      

      
    </v-row>
  </section>
</template>

<script>
  export default {
    name: 'RecentReviews',

    
  }
</script>

<style scoped>
#reviews_background {
  background-color: #D3C3BC;
}

#review_card {
  background-color: #BFADA5;
}

@import '../../assets/css/review_style.css';
/* @import '../../script.js'; */
</style>