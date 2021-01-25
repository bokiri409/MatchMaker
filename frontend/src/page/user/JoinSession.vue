<template>
  <div class="user">
    <div class="wrapC table">
      <div class="middle">
        <div class="input-wrap">
          <input
            v-model="user.name"
            id="name"
            placeholder="사용자명을 입력해주세요"
            type="text"
          />
        </div>
        <button class="btn btn--back btn--login" @click="checkHandler()">
          입장
        </button>
      </div>
    </div>
  </div>
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

      !this.user.name && ((msg = "사용자명을 입력해주세요"), (err = false));

      if (!err) alert(msg);
      else this.joinSession();
    },
    joinSession: function() {
      /**
       * Session Join Code
       * - 페이지 이동 
       * - 사용자명 넘겨주기
       * */ 
    },
  },
  data: () => {
    return {
      constants,
      user: {
        name: "",
      },
    };
  },
};
</script>
