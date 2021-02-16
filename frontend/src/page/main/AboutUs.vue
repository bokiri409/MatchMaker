<template>
  <section
    id="about-us"
    class="overflow-hidden"
  >
    <v-row no-gutters>
      <v-col
        class="align-content-space-between layout wrap"
        cols="12"
        md="6"
        id="about_us_left"
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
            <h1 class="display-3 v-heading mb-3 font-weight-bold"><font color="#f2acc6">매치메이커</font>는</h1>
            <base-heading>
            완벽한<font color="#79b4d9">소개팅</font><br>
            <font color="#f2cc85">서비스</font>를 제공합니다
            </base-heading>

          </v-col>
        </v-row>

        <base-bubble-2
          style="transform: rotate(180deg) translate(-200px, -15%)"
        />
      </v-col>

      
      <v-col
        class="text-center pa-5"
        cols="12"
        md="6"
        id="about_us_right"
      >
        <v-row
          class="fill-height"
          align="center"
          justify="center"
        >
          <v-col cols="12">
            <base-bubble-2
              style="transform: translateX(55%)"
            />

          </v-col>
          <v-sheet
            color="#F5C4D7"
            width="750"
            elevation="2"
            rounded
          >
            <mdb-container class="mt-5">

              <a @click="showModal = true"><img class="img-fluid z-depth-1" src="../../assets/123.jpg" alt="video" data-toggle="modal" data-target="#modal1"></a>
              <mdb-modal size="lg" :show="showModal" @close="showModal = false">
                <mdb-modal-body class="mb-0 p-0">
                  <div class="embed-responsive embed-responsive-16by9 z-depth-1-half">
                    <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/A3PDXmYoF5U"
                      allowfullscreen></iframe>
                  </div>
                </mdb-modal-body>
                <mdb-modal-footer class="justify-content-center">
                  <span class="mr-4">Spread the word!</span>
                  <a class="btn-floating btn-sm btn-fb"><i class="fab fa-facebook"></i></a>
                  <!--Twitter-->
                  <a class="btn-floating btn-sm btn-tw"><i class="fab fa-twitter"></i></a>
                  <!--Google +-->
                  <a class="btn-floating btn-sm btn-gplus"><i class="fab fa-google-plus"></i></a>
                  <!--Linkedin-->
                  <a class="btn-floating btn-sm btn-ins"><i class="fab fa-linkedin-in"></i></a>
                  <mdb-btn outline="primary" rounded size="md" class="ml-4" @click.native="showModal = false">Close</mdb-btn>
                </mdb-modal-footer>
              </mdb-modal>

            </mdb-container>





            <!-- <v-row>

              <v-col cols="12" md="6">
                <figure class="snip1504">
                  <img src="../../assets/service1.png" alt="sample57">
                  <figcaption id="about_us_fig">
                    <h3>Benjamin Evalent</h3>
                    <h5>좋아요</h5>
                  </figcaption>
                </figure>
              </v-col>

              <v-col cols="12" md="6">
                <figure class="snip1504">
                  <img src="../../assets/service1.png" alt="sample57">
                  <figcaption id="about_us_fig">
                    <h3>Benjamin Evalent</h3>
                    <h5>아주 좋아요</h5>
                  </figcaption>
                </figure>
              </v-col>
            </v-row>
            <v-row>

              <v-col cols="12" md="6">
                <figure class="snip1504">
                  <img src="../../assets/service1.png" alt="sample57">
                  <figcaption id="about_us_fig">
                    <h3>Benjamin Evalent</h3>
                    <h5>진짜루</h5>
                  </figcaption>
                </figure>
              </v-col>

              <v-col cols="12" md="6">
                <figure class="snip1504">
                  <img src="../../assets/service1.png" alt="sample57">
                  <figcaption id="about_us_fig">
                    <h3>Benjamin Evalent</h3>
                    <h5>좋아요</h5>
                  </figcaption>
                </figure>
              </v-col>
            </v-row> -->

          </v-sheet>
        </v-row>
      </v-col>
    </v-row>
  </section>
</template>

<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/mdbvue/lib/index.js"></script>
<script>
import { mdbContainer, mdbRow, mdbCol, mdbIcon, mdbModal, mdbModalHeader, mdbModalTitle, mdbModalBody, mdbModalFooter, mdbBtn, mdbNavItem } from 'mdbvue';

export default {
  data() {
    return {
      showModal: false,
    };
  },
  components: {
    mdbContainer,
    mdbRow,
    mdbCol, 
    mdbIcon,
    mdbModal,
    mdbModalHeader,
    mdbModalTitle,
    mdbModalBody,
    mdbModalFooter,
    mdbBtn,
    mdbNavItem
  }
};
</script>

<style scoped>
/* @import '../../assets/css/review_style.css'; */


#input_url {
  border: 1px solid #79b4d9;
  border-radius: 5px;
}

#about_us_left {
    height: 950px;
}

#about_us_right {
    background-color: #f7cfdf;
    
}

#about_us_fig {
  width: 300px;
  height: 300px;
}

.snip1504 {
  width: 300px;
  height: 300px;
}
</style>
