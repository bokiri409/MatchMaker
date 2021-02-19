<template>
    <v-container fill-height style="max-width: 400px;" fluid>
        <v-layout align-center row wrap>
            <v-flex xs10>
                <v-row
                    class="fill-height ma-14 pa-7"
                    align="center"
                    justify="center"
                >
                    <v-img
                        contain
                        src="../../assets/img/login/love2.png"
                        max-height="130"
                        max-width="130"
                    ></v-img>
                </v-row>
                <v-text-field
                        label="email"
                        v-model="user.email"
                        id="email"
                        placeholder="이메일을 입력하세요."
                        filled
                        solo
                        rounded
                        dense
                ></v-text-field>
                <v-text-field
                        label="password"
                        v-model="user.password"
                        id="password"
                        placeholder="비밀번호를 입력하세요."
                        filled
                        solo
                        rounded
                        dense
                        counter
                        :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                        :type="show ? 'text' : 'password'"
                        @click:append="show = !show"
                ></v-text-field>

                <v-btn
                        rounded
                        block
                        color="#F2ACC6"
                        @click="checkHandler()"
                >
                    login
                </v-btn>

                <p class="text-center pt-5">
                    아직 회원이 아니신가요?
                    <router-link
                            v-bind:to="{ name: constants.URL_TYPE.USER.JOIN }"
                            class="btn&#45;&#45;text"
                    >
                        sign up
                    </router-link>
                </p>

                <p class="text-center">
                    <router-link
                        v-bind:to="{ name: constants.URL_TYPE.USER.FINDPASSWORD }"
                        class="btn&#45;&#45;text"
                    >
                        비밀번호를 잊어버렸나요?
                    </router-link>
                </p>
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
                .catch((error) => {
                    console.log(error);
                    // 인증된 이메일이 아닐 때 에러처리
                    if(error.response.data.data == "Not Certified Email"){
                        alert("인증된 이메일이 아닙니다. 이메일 인증을 완료해주세요.");
                    }
                    else{ //인증된 이메일이지만 이메일과 비밀번호 잘못 입력 시 에러처리
                        alert("이메일과 비밀번호를 확인해주세요."),
                        this.user.email = '',
                        this.user.password = ''
                    }
                });
            },
        },
        data: () => {
            return {
                constants,
                user: {
                    email: "",
                    password: "",
                },
                show: false,
                password: 'Password'
            };
        },
    };
</script>
