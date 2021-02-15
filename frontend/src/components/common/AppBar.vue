<template>
  <v-app-bar
          v-scroll="onScroll"
          :color="!isScrolling ? 'transparent' : 'white'"
          fixed
          flat
  >
    <v-slide-x-transition>
      <router-link v-bind:to="{ name: constants.URL_TYPE.COMMON.VIEW }">
        <v-img
                :src="require('../../assets/img/matchmaker2.png')"
                class="shrink ma-0"
                contain
                width="250"
                height="50"
        />
      </router-link>
    </v-slide-x-transition>

    <v-spacer />

    <div v-if="!getEmail">
      <router-link
              v-bind:to="{ name: constants.URL_TYPE.USER.JOIN }"
              class="btn--text"
              style="text-decoration: none"
      >
        <base-btn
                class="ml-3"
                large
                color="#79b4d9"
        >
          Sign Up

        </base-btn>
      </router-link>

      <router-link
              v-bind:to="{ name: constants.URL_TYPE.USER.LOGIN }"
              class="login-btn"
              style="text-decoration: none"
      >
        <base-btn
                class="ml-3"
                large
                color="#79b4d9"
        >
          Login

        </base-btn>
      </router-link>
    </div>

    <div v-else>
      <span>{{ getNickname }} 님 환영합니다.</span>
      <!--      <button @click.prevent="onClickLogout">로그아웃</button>-->
      <base-btn
              class="ml-3"
              large
              color="#79b4d9"
              @click.prevent="onClickLogout"
      >
        Logout

      </base-btn>

      <router-link
              v-bind:to="{ name: constants.URL_TYPE.USER.MYPAGE }"
              style="text-decoration: none"
      >
        <base-btn
                class="ml-3"
                large
                color="#79b4d9"
        >
          Mypage
        </base-btn>
      </router-link>
    </div>

  </v-app-bar>
</template>

<script>
  import constants from "../../lib/constants";
  import { mapGetters } from "vuex";

  export default {
    name: 'CommonAppBar',
    computed: {
      ...mapGetters(["getEmail", "getNickname"]),
    },

    data: () => ({
      showLogo: false,
      isScrolling: false,
      constants,
      keyword: "",
    }),

    methods: {
      onScroll () {
        const offset = window.pageYOffset
        this.isScrolling = offset > 50
        this.showLogo = offset > 200
      },
      onClickLogout() {
        this.$store
                .dispatch("LOGOUT")
                // .then(() => this.$router.replace("/").catch(() => {}));
                .then(() =>
                        this.$router.push({
                          path: "/",
                        })
                )
                .catch(() => {});
      },
    },
  }
</script>
