import Vue from "vue";
import Router from "vue-router";

import constants from "../lib/constants";

// 유저
import Login from "../page/user/Login.vue";
import Join from "../page/user/Join.vue";
import JoinSession from "../page/user/JoinSession.vue";
import MyPage from "../page/user/MyPage.vue";
import FindPassword from "../page/user/FindPassword.vue";

// 메인 페이지
import View from "../components/common/View.vue";

Vue.use(Router);

export default new Router({
  routes: [
    // 로그인/가입
    {
      path: "/user/login",
      name: constants.URL_TYPE.USER.LOGIN,
      component: Login,
    },
    {
      path: "/user/join",
      name: constants.URL_TYPE.USER.JOIN,
      component: Join,
    },
    {
      path: "/user/joinSession",
      name: constants.URL_TYPE.USER.JOINSESSION,
      component: JoinSession,
    },
    // 마이페이지
    {
      path: "/user/mypage",
      name: constants.URL_TYPE.USER.MYPAGE,
      component: MyPage,
    },

    //비밀번호 찾기 페이지
    {
      path: "/user/findpassword",
      name: constants.URL_TYPE.USER.FINDPASSWORD,
      component: FindPassword,
    },
    // 메인 페이지
    {
      path: "/",
      name: constants.URL_TYPE.COMMON.VIEW,
      component: View,
    },
  ],
});
