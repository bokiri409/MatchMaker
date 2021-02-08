import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VModal from 'vue-js-modal'

Vue.use(VModal);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router, 
  store,
  components: { App }, 
  template: '<App/>'
})
 