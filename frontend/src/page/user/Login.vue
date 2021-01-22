<template>
  <div class="user" id="login">
    <div class="wrapC table">
      <div class="middle">
        <h1>SS_log</h1>
        <div class="input-wrap">
          <input
            v-model="user.email"
            id="email"
            placeholder="이메일을 입력해주세요"
            type="text"
          />
        </div>
        <div class="input-wrap">
          <input
            v-model="user.password"
            type="password"
            id="password"
            placeholder="영문, 숫자 혼용 8자 이상"
          />
        </div>
        <button class="btn btn--back btn--login" @click="checkHandler()">
          로그인 하기
        </button>
        <div class="add-option">
          <div class="wrap">
            <p>아직 회원이 아니신가요?</p>
            <router-link
              v-bind:to="{ name: constants.URL_TYPE.USER.JOIN }"
              class="btn--text"
            >
              회원가입
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/user.scss";
import constants from "../../lib/constants";

export default {
  components: {},
  created() {},
  watch: {},
  methods: {
    checkHandler: function() {
      let err = true;
      let msg = "";

      !this.user.email && ((msg = "이메일을 입력해주세요"), (err = false));
      err &&
        !this.user.password &&
        ((msg = "패스워드를 입력해주세요"), (err = false));
      err &&
        !this.validEmail(this.user.email) &&
        ((msg = "이메일 형식에 맞춰 입력해주세요 ex) abc@abc.com"),
        (err = false));

      if (!err) alert(msg);
      else this.login();
    },
    validEmail: function(email) {
      // 이메일 형식 체크
      var re = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
      return re.test(email);
    },
    login: function() {
      this.$store.dispatch("LOGIN", this.user).then(() =>
        this.$router.push({
          path: "/",
        })
      );
    },
  },
  data: () => {
    return {
      constants,
      user: {
        email: "",
        password: "",
      },
    };
  },
};
</script>
