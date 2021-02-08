import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

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
  template: '<App/>'
})
 