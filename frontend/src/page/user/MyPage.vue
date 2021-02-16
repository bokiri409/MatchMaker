<!--<template>
  <div class="user" id="mypage">
    <div class="wrapC table">
      <div class="middle">
        <h1>마이페이지</h1>
        <div class="form-wrap">
          <div class="input-wrap">
            <input v-model="email" id="email" type="text" readonly />
          </div>

          <div class="input-wrap">
            <input
              v-model="nickname"
              placeholder="수정할 닉네임을 넣어주세요"
              id="nickname"
              type="text"
            />
          </div>

          <div class="input-wrap password-wrap">
            <input
              v-model="password"
              id="password"
              :type="passwordType"
              placeholder="수정할 비밀번호를 입력해주세요"
            />
            <span :class="{ active: passwordType === 'text' }">
              <i class="fas fa-eye"></i>
            </span>
          </div>

          <div class="input-wrap password-wrap">
            <input
              v-model="passwordConfirm"
              id="password-confirm"
              :type="passwordConfirmType"
              placeholder="비밀번호를 한번 더 입력해주세요"
            />
            <span :class="{ active: passwordConfirmType === 'text' }">
              <i class="fas fa-eye"></i>
            </span>
          </div>
        </div>

        <button class="btn" @click="updateHandler()">
          <span>
            수정하기
          </span>
        </button>
        <button class="btn" @click="dropOutHandler()">
          <span>
            탈퇴하기
          </span>
        </button>
      </div>
    </div>
  </div>
</template>-->

<template>
  <v-container fill-height style="max-width: 400px;">
    <v-layout align-center row wrap>
      <v-flex xs10 pb-16>
        <v-row
          class="fill-height mt-16 py-10"
          align="center"
          justify="center"
        >
          <v-img
            contain
            src="../../assets/img/mypage/user.png"
            max-height="130"
            max-width="130"
            class="mb-auto"
          >
          </v-img>
        </v-row>

        <v-row>
          <v-text-field
              label="email"
              v-model="email"
              id="email"
              placeholder="이메일을 입력하세요."
              filled
              solo
              rounded
              dense
              disabled
          ></v-text-field>
          <v-text-field
              label="nickname"
              v-model="nickname"
              id="nickname"
              placeholder="수정할 닉네임을 입력하세요"
              filled
              solo
              rounded
              dense
          ></v-text-field>
          <v-text-field
              label="password"
              v-model="password"
              id="password"
              placeholder="비밀번호를 입력하세요"
              filled
              solo
              rounded
              dense
              :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
              :type="show1 ? 'text' : 'password'"
              counter
              @click:append="show1 = !show1"
          ></v-text-field>
          <v-text-field
              label="password check"
              v-model="passwordConfirm"
              id="password-confirm"
              placeholder="비밀번호를 한번 더 입력하세요"
              filled
              solo
              rounded
              dense
              :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
              :type="show2 ? 'text' : 'password'"
              counter
              @click:append="show2 = !show2"
          ></v-text-field>
        </v-row>

        <v-row class="pb-4">
          <v-btn
              rounded
              block
              color="#F2ACC6"
              @click="updateHandler()"
          >
            수정하기
          </v-btn>
        </v-row>
        <v-row class="pb-16">
          <v-btn
              rounded
              block
              color="#F2ACC6"
              @click="dropOutHandler()"
          >
            탈퇴하기
          </v-btn>
        </v-row>

      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import "../../assets/css/user.scss";
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  components: {},
  created() {
    this.email = this.$store.state.email;
    this.nickname = this.$store.state.nickname;
  },
  computed: {
    ...mapGetters(["getEmail", "getNickname"]),
  },
  methods: {
    checkHandler() {
      let err = true;
      let msg = "";

      !this.password && ((msg = "패스워드를 입력해주세요"), (err = false));
      err &&
        !this.passwordConfirm &&
        ((msg = "패스워드 확인란을 입력해주세요"), (err = false));
      err &&
        !this.validPassword(this.password) &&
        ((msg =
          "비밀번호 형식에 맞춰 입력해주세요 (영문,숫자,특수문자 포함 8글자 이상)"),
        (err = false));
      err &&
        !(this.password == this.passwordConfirm) &&
        ((msg = "비밀번호를 다시 확인해주세요!"), (err = false));

      if (!err) alert(msg);
      else this.updateHandler();
    },
    validPassword: function(password) {
      // 비밀번호 형식 체크
      // Minimum eight characters, at least one letter, one number and one special character:
      var re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,}$/;
      return re.test(password);
    },
    updateHandler: function() {
      axios
        // .put("http://localhost:8080/account/update", {
        .put(this.$api_url + `/account/update`, {
          email: this.$store.state.email,
          nickname: this.nickname,
          password: this.password,
        })
        .then((response) => {
          if (response.data.data == "success") {
            alert("회원 정보가 수정되었습니다");
            this.$router.push({
              path: "/",
            })
          } else {
            alert(response.data.data);
          }
        })
        .catch((error) => {
          console.dir(error);
          if (error.response.data.message.slice(0,35) == "io.jsonwebtoken.ExpiredJwtException") {
            alert("로그인 시간이 만료되었습니다. 다시 로그인 해주세요.");
            this.$store.dispatch("LOGOUT", this.user).then(() =>
                this.$router.push({
                  path: "/account/login",
                })
            )
            .catch(() => {});
          }
        });
    },
    dropOutHandler: function() {
      axios
        .delete(
          // "http://localhost:8080/account/delete?email=" +
          this.$api_url +`/account/delete?email=` +
            this.$store.state.email
        )
        .then((response) => {
          if (response.data.data == "success") {
            alert("회원 정보가 삭제되었습니다");
            this.$router.push({
              path: "/",
            })
          } else {
            alert(response.data.data);
          }
        })
        .catch((error) => {
          console.dir(error);
        });
    },
  },
  watch: {},
  data: () => {
    return {
      email: "",
      nickname: "",
      password: "",
      passwordConfirm: "",
      passwordType: "password",
      passwordConfirmType: "password",
      show1: false,
      show2: false
    };
  },
};
</script>
