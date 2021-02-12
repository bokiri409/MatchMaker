<!--<template>
  <div id="container">
    <div class="user" id="login">
      <div class="wrapC table">
        <div class="middle">
          <h1>로그인</h1>
          <div class="input-wrap">
            <label for="email">Email address</label>
            <input
              v-model="user.email"
              id="email"
              placeholder="이메일을 입력해주세요"
              type="text"
            />
          </div>
          <div class="input-wrap">
            <label for="password">Password</label>
            <input
              v-model="user.password"
              type="password"
              id="password"
              placeholder="영문, 숫자 혼용 8자 이상"
            />
          </div>
          <a class="forgot-password" href="#">Forgot password ?</a>
          <button class="btn btn&#45;&#45;back btn&#45;&#45;login" @click="checkHandler()">
            로그인
          </button>
          <div class="add-option">
            <div class="wrap">
              <p>아직 회원이 아니신가요?</p>
              <router-link
                v-bind:to="{ name: constants.URL_TYPE.USER.JOIN }"
                class="btn&#45;&#45;text"
              >
                회원가입
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>-->
<template>
    <v-container fill-height style="max-width: 400px;">
        <v-layout align-center row wrap>
            <v-flex xs10>
<!--                <v-card class="elevation-12">-->
<!--                    <div class="pa-10">-->
                <v-row
                    class="fill-height ma-0 pa-7"
                    align="center"
                    justify="center"
                >
                    <v-img
                        contain
                        src="../../assets/img/login/user.png"
                        max-height="80"
                        max-width="70"
                    ></v-img>
                </v-row>
                    <v-text-field
                        label="email"
                        placeholder="이메일을 입력하세요."
                        filled
                        solo
                        rounded
                        dense
                    ></v-text-field>
                    <v-text-field
                        label="password"
                        v-model="password"
                        placeholder="비밀번호를 입력하세요."
                        filled
                        solo
                        rounded
                        dense
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="show1 ? 'text' : 'password'"
                        @click:append="show1 = !show1"
                    ></v-text-field>

                    <v-btn
                        rounded
                        block
                        color="#F2ACC6"
                    >
                        login
                    </v-btn>

                <p class="text-center pa-5">
                    아직 회원이 아니신가요?
                <router-link
                        v-bind:to="{ name: constants.URL_TYPE.USER.JOIN }"
                        class="btn&#45;&#45;text"
                >
                    sign up
                </router-link>
                </p>

                <p class="text-center">
                    비밀번호를 잊어버렸나요?
                </p>
<!--                    </div>-->
<!--                </v-card>-->
            </v-flex>
        </v-layout>
    </v-container>
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

      //에러메세지 출력 - 이메일, 패스워드 입력 비었을 때
      !this.user.email && ((msg = "이메일을 입력해주세요"), (err = false));
      err &&
        !this.user.password &&
        ((msg = "패스워드를 입력해주세요"), (err = false));
      err &&
        !this.validEmail(this.user.email) &&
        ((msg = "이메일 형식에 맞춰 입력해주세요 ex) abc@abc.com"),
        (err = false));

      if (!err) alert(msg);
      else this.login(); //err안걸리면 login함수 실행
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
      )
      .catch(() =>
        alert("이메일과 비밀번호를 확인해주세요."),
        this.user.email = '',
        this.user.password = '',
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
