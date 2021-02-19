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
            id="about_sheet"
          >
            <mdb-container class="mt-5">

              <a @click="showModal = true"><img id="thumbnail" class="img-fluid z-depth-1" src="../../assets/thumbnail.png" alt="video" data-toggle="modal" data-target="#modal1"></a>
              <mdb-modal size="lg" :show="showModal" @close="showModal = false">
                <mdb-modal-body class="mb-0 p-0">
                  <div class="embed-responsive embed-responsive-16by9 z-depth-1-half">
                    <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/Mt9HUj-bu7Q"
                      allowfullscreen></iframe>
                  </div>
                </mdb-modal-body>
              </mdb-modal>
            </mdb-container>
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

#input_url {
  border: 1px solid #79b4d9;
  border-radius: 5px;
}

#about_us_left {
  height: 910px;
}

#about_us_right {
  background-color: #f7cfdf;
    
}

#about_us_fig {
  width: 300px;
  height: 300px;
}

#about_sheet {
  margin-bottom: 80px;     
}

#thumbnail {
  width: 100%;
  height: 450px;
}

.snip1504 {
  width: 300px;
  height: 300px;
}
</style>
