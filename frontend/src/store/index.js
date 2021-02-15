import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';
import '../main.js';
import {api_url} from "../main";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    email: '',
    nickname: '',
    token: '',
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
    CHECKUSER(state, userData) {
      state.email = userData.email;
      state.nickname = userData.nickname;
      state.token = userData.token;
      //반환된 유저 정보를 로컬스토리지에 저장
      localStorage.setItem("userData", JSON.stringify(userData));
      //token(토큰)을 Axios Header에 추가
      // Axios instance Authorization Header에 JWT Token을 포함
      axios.defaults.headers.common["x-auth-token"] = `${state.token}`;
    },
    LOGIN(state, payload) { //로그인 성공 시
        state.email = payload.object.email;
        state.nickname = payload.object.nickname;
        state.token = payload.data;
        const userData = {
          email: state.email,
          nickname: state.nickname,
          token: state.token
        };
        //반환된 토큰을 로컬스토리지에 저장
        localStorage.setItem("userData", JSON.stringify(userData));
        //token(토큰)을 Axios Header에 추가
        // Axios instance Authorization Header에 JWT Token을 포함
        axios.defaults.headers.common["x-auth-token"] = `${state.token}`;
    },
    LOGOUT(state) {
      state.email = '';
      state.nickname = '';
      state.token = '';
      // 로그아웃 시 저장된 토큰 삭제
      localStorage.removeItem("userData");
      // reload 인자값 없을 때, 디폴트 false 처리 되어 새로고침 하면서 클라이언트 캐쉬에서 페이지를 불러옴 헤더 세팅값이 리셋됨
      location.reload();
    },
  },
  actions: {
    // 로그인 시도 -> mutations로 넘어감
    LOGIN(context, user) {
      // return axios.post("http://localhost:8080/account/login", user)
      return axios.post(api_url + `/account/login`, user)
      .then((response) => { //로그인 성공 시 토큰(data) 반환
            context.commit('LOGIN', response.data);
          })
    },
    LOGOUT(context) {
      context.commit('LOGOUT');
    },
  },
});