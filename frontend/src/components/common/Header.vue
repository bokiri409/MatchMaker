<template>
  <div id="header" v-if="isHeader">
    <h1>
      <router-link v-bind:to="{ name: constants.URL_TYPE.POST.MAIN }">
        SS_log
      </router-link>
    </h1>
    <div class="right">
      <div class="search-input">
        <i class="fas fa-search"></i>
        <input v-model="keyword" type="text" />
      </div>
      <div v-if="!getEmail">
        <router-link
          v-bind:to="{ name: constants.URL_TYPE.USER.LOGIN }"
          class="login-btn"
        >
          로그인
        </router-link>
      </div>
      <div v-else>
        <span>{{ getNickname }} 님 환영합니다.</span>
        <button @click.prevent="onClickLogout">로그아웃</button>
        <router-link v-bind:to="{ name: constants.URL_TYPE.USER.MYPAGE }">
          마이페이지
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import constants from "../../lib/constants";
import { mapGetters } from "vuex";

export default {
  name: "Header",
  components: {},
  props: ["isHeader"],
  computed: {
    ...mapGetters(["getEmail", "getNickname"]),
  },
  watch: {},
  created() {},
  methods: {
    onClickLogout() {
      this.$store
        .dispatch("LOGOUT")
        .then(() => this.$router.replace("/").catch(() => {}));
    },
  },
  data: function() {
    return {
      constants,
      keyword: "",
    };
  },
};
</script>
