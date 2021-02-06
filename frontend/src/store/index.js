import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    email: '',
    nickname: '',
  },
  getters: {
    getEmail(state) {
      return state.email;
    },
    getNickname(state) {
      return state.nickname;
    },
  },
  mutations: {
    LOGIN(state, payload) { //로그인 성공 시
      state.email = payload.object.email;
      state.nickname = payload.object.nickname;
    },
    LOGOUT(state) {
      state.email = '';
      state.nickname = '';
      // 로그아웃 시 저장된 토큰 삭제
      localStorage.removeItem("access-token");
      location.reload();
    },
  },
  actions: {
    // 로그인 시도 -> mutations로 넘어감
    LOGIN(context, user) {
      return axios.post("http://localhost:8080/account/login", user)
      .then((response) => { //로그인 성공 시 토큰(data) 반환
          //토큰을 헤더에 포함시켜서 유저 정보를 요청
          let token = response.data.data;
          console.log(token);
          //반환된 토큰을 로컬스토리지에 저장
          localStorage.setItem("access-token", token);
          context.commit('LOGIN', response.data);
          console.log(response);
      })
      .catch(err => { //로그인 실패 시
        alert("이메일과 비밀번호를 확인하세요.");
        console.log(err);
      })
    },
    LOGOUT(context) {
      context.commit('LOGOUT');
    },
  },
});