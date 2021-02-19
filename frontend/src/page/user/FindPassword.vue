<template>
    <v-container fill-height style="max-width: 400px;" fluid>
        <v-layout align-center row wrap>
            <v-flex xs10>
                <v-row
                    class="fill-height ma-0 pa-7"
                    align="center"
                    justify="center"
                >
                    <v-img
                        contain
                        src="../../assets/img/login/message.png"
                        max-height="150"
                        max-width="150"
                    ></v-img>
                </v-row>
                <h6 class="pb-5 pa-3">가입한 이메일과 닉네임 확인 후 임시 비밀번호를 발송해드립니다.</h6>
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
                        label="nickname"
                        v-model="user.nickname"
                        id="nickname"
                        placeholder="가입한 닉네임을 입력하세요"
                        filled
                        solo
                        rounded
                        dense
                ></v-text-field>

                <v-btn
                        rounded
                        block
                        color="#F2ACC6"
                        @click="forgetPassword()"
                >
                    send mail
                </v-btn>

            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import "../../assets/css/user.scss";
    import constants from "../../lib/constants";
    import axios from "axios";


    export default {
        components: {},
        created() {},
        watch: {},
        methods: {
            forgetPassword: function () {
                axios
                    .get(this.$api_url + `/email/find-password?email=${this.user.email}&nickname=${this.user.nickname}`, {
                    })
                    .then((response) => {
                        console.log(response.data);
                        if (response.data.data == "success") {
                            axios
                                .post(this.$api_url + `/email/find-password/send-mail`, {
                                    email: this.user.email,
                                    nickname: this.user.nickname,
                                })
                                .then(() => {
                                    alert("임시 비밀번호가 메일로 발송되었습니다. 이메일을 확인해주세요.");
                                })
                                .catch((error) => {
                                    console.log(error);
                                    alert("메일 전송에 실패했습니다.");
                                })
                        }
                    })
                    .catch((error) => {
                        alert("이메일과 닉네임을 다시 확인해주세요.");
                    })
            }
        },
        data: () => {
            return {
                constants,
                user: {
                    email: "",
                    nickname: "",
                },
            };
        },
    };
</script>
