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

        <base-heading id="reviews_headertext">
          Recent Reviews
        </base-heading><br>

        <!-- <base-text class="mb-5">
          Lorem ipsum dolor sit amet, consectetur adipi<br>
          scin elit. Etiam vulputate augue vel felis gravida<br>
          porta. Lorem ipsum dolor sit amet.
        </base-text> -->
      
        <v-card id="review_card">
          <v-container class="pa-2">
            <v-carousel cycle hide-delimiters>
              <v-carousel-item
                v-for="item in items"
                :key="item.id"
              >
                <figure class="snip1504">
                  <img :src="item.src" alt="">
                  <figcaption>
                    <h3>{{ item.title }}</h3><br>
                    <h5>{{ item.content }}</h5>
                    <h5>{{ item.content2 }}</h5>
                  </figcaption>
                </figure>
              
              
              </v-carousel-item>
            </v-carousel>
            
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
    data: () => ({
      items: [
        {
          src: require('../../assets/woman1.jpg'),
          title: '김주선(28), 직장인',
          content: '퇴근 후 사람을 만나는게 쉽지 않았는데,',
          content2: '매치메이커를 통해 분위기 있는 곳에서의 만남을 즐길 수 있었습니다.'
        },
        {
          src: require('../../assets/woman2.jpg'),
          title: '임나리(26), 대학생',
          content: '새로운 사람을 직접 만나는 것이 불편했는데,',
          content2: '매치메이커를 통해서 편하게 만날 수 있었습니다.'
        },
        {
          src: require('../../assets/woman3.jpg'),
          title: '강주은(23), 대학생',
          content: '코로나 때문에 남자친구 만드는게 쉽지 않았는데,',
          content2: '매치메이커 덕분에 걱정이 없어졌어요'
        },
        {
          src: require('../../assets/man.jpg'),
          title: '울프강(36), 직장인',
          content: '현재 한국에서 살고 있지만',
          content2: '매주 Kölle에 있는 친구들과의 만날 수 있습니다.'
        },
      ],
    }),
    
  }
</script>

<style scoped>
#reviews_background {
  background-color: #D3C3BC;
  height: 910px;
}

#review_card {
  background-color: #BFADA5;
}

@import '../../assets/css/review_style.css';
/* @import '../../script.js'; */
</style>