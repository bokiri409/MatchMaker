import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VModal from 'vue-js-modal'
import vuetify from './plugins/vuetify';
import './plugins/base'

// mdbvue
import 'bootstrap-css-only/css/bootstrap.min.css'
import 'mdbvue/lib/css/mdb.min.css'
import '@fortawesome/fontawesome-free/css/all.min.css'

// 배포서버 or 개발(로컬)에 필요한 api_url
export const api_url = "http://localhost:8080/api";
// export const api_url = "https://i4a208.p.ssafy.io:8080/api";
Vue.prototype.$api_url = api_url;


Vue.use(VModal);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,

  created(){
    const userData = localStorage.getItem("userData");
    if (userData) {
      //userData가 있는지 확인
      const userInfo = JSON.parse(userData); //객체로 변환
      // Vuex token state에 저장하기 위해 mutation을 사용한다.
      this.$store.commit('CHECKUSER', userInfo);
    }
  },

  components: { App },
  vuetify,
  template: '<App/>'
})
 