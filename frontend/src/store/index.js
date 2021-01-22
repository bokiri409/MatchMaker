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
    LOGIN(state, payload) {
      state.email = payload.object.email;
      state.nickname = payload.object.nickname;
    },
    LOGOUT(state) {
      state.email = '';
      state.nickname = '';
    },
  },
  actions: {
    LOGIN(context, user) {
      return axios.get("http://localhost:8080/account/login?email=" 
      + user.email + "&password=" + user.password)
      .then((response) => {
          context.commit('LOGIN', response.data);
      })
    },
    LOGOUT(context) {
      context.commit('LOGOUT');
    },
  },
});