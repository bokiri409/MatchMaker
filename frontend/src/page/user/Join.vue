<template>
  <v-container fill-height style="max-width: 400px;">
    <v-layout align-center row wrap>
      <v-flex xs10 pb-16>
        <v-row
          class="fill-height"
          align="center"
          justify="center"
        >
          <v-img
            contain
            src="../../assets/img/signup/MatchMaker_logo.png"
            max-height="500"
            max-width="400"
          >
          </v-img>
        </v-row>
        <v-text-field
            label="nickname"
            v-model="nickName"
            id="nickname"
            placeholder="닉네임을 입력하세요"
            filled
            solo
            rounded
            dense
        ></v-text-field>
        <v-text-field
            label="email"
            v-model="email"
            id="email"
            placeholder="이메일을 입력하세요"
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
        <span id="session">
          <modal name="terms-modal" height="500">
            <p class="modal-title">Match Maker 약관</p>
            <section style="padding-left: 20px; padding-right: 20px">
              <p class="modal-description">환영합니다!</p>
              <em>제 1 장</em><br>
              <p><b>제 1 조 목적 및 정의</b><br>
              본 회원약관은 Match Maker 가 운영하는 서비스를 이용함에 있어 관리자와
              이용자('회원')의 권리, 의무 및 책임사항을 규정함을 목적으로 한다. <br></p>
              <p><b>제 2 조 이용계약의 체결</b><br>
              회원 가입 시 약관 동의 버튼을 누르면 약관에 동의하여 이 계약이 체결된 것으로 간주한다. <br></p>
              <p><b>제 3 조 회원가입의 승낙</b><br>
              회원 가입 신청 양식에 가입 희망 회원이 회원 가입 신청을 하면
              이메일 인증 절차를 거쳐 서비스를 이용할 수 있다.</p>
            </section>
            <button class="modal-hide" @click="hideTermsModal()">닫기</button>
          </modal>
        </span>

        <v-row no-gutters>
          <v-col
            cols="12"
            sm="6"
            md="8"
          >
            <v-checkbox
               v-model="isTerm" id="term" color="#F2CC85"
               label="약관에 동의합니다."
            ></v-checkbox>
          </v-col>
          <v-col
            cols="6"
            md="4"
            class="pt-5 pr-3"
            align="right"
          >
            <a @click="showTermsModal()">
              약관보기
            </a>
          </v-col>
        </v-row>

        <v-btn
          rounded
          block
          color="#F2ACC6"
          @click="checkHandler()"
        >
          Sign up
        </v-btn>

      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  import "../../assets/css/user.scss";
  import "../../assets/css/video.css";
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
        err &&
        !this.isTerm &&
        ((msg =
                "약관에 동의해주세요!"),
                (err = false));

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
                .post(this.$api_url + `/account/signup`, {
                  email: this.email,
                  password: this.password,
                  nickname: this.nickName,
                })
                .then((response) => {
                  if (response.data.data == "success") {
                    alert("인증 메일이 발송되었습니다. 이메일을 확인해주세요");
                    axios
                            .post(this.$api_url + `/email/send`, {
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
        showTermsModal () {
            this.$modal.show('terms-modal');
        },

        hideTermsModal () {
            this.$modal.hide('terms-modal');
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
        show1: false,
        show2: false,
      };
    },
  };
</script>
