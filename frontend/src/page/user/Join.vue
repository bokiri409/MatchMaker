<template>
  <div class="user" id="join">
    <div class="wrapC table">
      <div class="middle">
        <h1>회원가입</h1>
        <div class="form-wrap">
          <div class="input-wrap">
            <input
              v-model="nickName"
              id="nickname"
              placeholder="닉네임을 입력해주세요"
              type="text"
            />
          </div>

          <div class="input-wrap">
            <input
              v-model="email"
              id="email"
              placeholder="이메일을 입력해주세요"
              type="text"
            />
          </div>

          <div class="input-wrap password-wrap">
            <input
              v-model="password"
              id="password"
              :type="passwordType"
              placeholder="비밀번호를 입력해주세요"
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

        <label>
          <input v-model="isTerm" type="checkbox" id="term " />
          <span>약관에 동의합니다</span>
        </label>

        <span class="go-term">약관 보기</span>

        <button class="btn" @click="checkHandler()">
          <span>
            작성완료
          </span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/user.scss";
// import AxiosInterceptor from "../../utils/AxiosInterceptor.js";
import axios from "axios";

export default {
  components: {},
  created() {},
  methods: {
    checkHandler: function() {
      let err = true;
      let msg = "";

      !this.nickName && ((msg = "닉네임을 입력해주세요"), (err = false));
      err && !this.email && ((msg = "이메일을 입력해주세요"), (err = false));
      err &&
        !this.password &&
        ((msg = "패스워드를 입력해주세요"), (err = false));
      err &&
        !this.passwordConfirm &&
        ((msg = "패스워드 확인란을 입력해주세요"), (err = false));
      err &&
        !this.validEmail(this.email) &&
        ((msg = "이메일 형식에 맞춰 입력해주세요 ex) abc@abc.com"),
        (err = false));
      err &&
        !this.validPassword(this.password) &&
        ((msg =
          "비밀번호 형식에 맞춰 입력해주세요 (영문,숫자,특수문자 포함 8글자 이상)"),
        (err = false));
      err &&
        !(this.password == this.passwordConfirm) &&
        ((msg = "비밀번호를 다시 확인해주세요!"), (err = false));

      if (!err) alert(msg);
      else this.joinHandler();
    },
    validEmail: function(email) {
      // 이메일 형식 체크
      var re = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
      return re.test(email);
    },
    validPassword: function(password) {
      // 비밀번호 형식 체크
      // Minimum eight characters, at least one letter, one number and one special character:
      var re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,}$/;
      return re.test(password);
    },
    joinHandler: function() {
      axios
        .post("http://localhost:8080/account/signup", {
          email: this.email,
          password: this.password,
          nickname: this.nickName,
        })
        .then((response) => {
          console.log(response.data);
          if (response.data.data == "success") {
            alert("인증 메일이 발송되었습니다. 이메일을 확인해주세요");
            axios
              .post("http://localhost:8080/email/send", {
                email: this.email,
                password: this.password,
                nickname: this.nickName,
                certified: response.data.object.certified,
              })
              .then((response) => {
                if (response.data.data == "success") {
                  alert("메일을 확인해주세요!");
                } else {
                  alert(response.data.data);
                }
              })
              .catch((error) => {
                console.dir(error);
              });
          } else {
            alert(response.data.data);
          }
        })
        .catch((error) => {
          console.dir(error);
        });

      this.$router.push({
        path: "/user/login",
      });
    },
  },
  watch: {},
  data: () => {
    return {
      email: "",
      nickName: "",
      password: "",
      passwordConfirm: "",
      isTerm: false,
      passwordType: "password",
      passwordConfirmType: "password",
    };
  },
};
</script>
