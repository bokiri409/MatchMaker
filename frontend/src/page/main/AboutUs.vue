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

          <v-col
            v-for="service in services"
            :key="service"
            class="text-center mb-3"
            md="6"
          >
            <v-img
              :src="require(`@/assets/${service.img}.png`)"
              height="300px"
            >
            </v-img>

          </v-col>
        </v-row>
      </v-col>

    </v-row>
  </section>
</template>

<script>
export default {
    data: () => ({
      services: [
        {
          name: 'Research',
          icon: 'mdi-clipboard-text-outline',
          img: 'service1',
        },
        {
          name: 'Design',
          icon: 'mdi-pencil-outline',
          img: 'service2',
        },
        {
          name: 'Development',
          icon: 'mdi-settings-outline',
          img: 'service3',
        },
        {
          name: 'Support',
          icon: 'mdi-account',
          img: 'service4',
        },
      ],
    }),
}
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
    background-color: #f2acc6;
}
</style>
