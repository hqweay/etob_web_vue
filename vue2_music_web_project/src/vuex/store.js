import Vue from 'vue'
import Vuex from 'vuex'


Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    user: {
      username: "",
      type: -1,
    }
  },
  mutations: {
    initUserInfo(state) {
      // state.type = parseInt(type)
      console.log("存了没.......");
      // console.log(state.user);
      window.localStorage.setItem('username', state.user.username);
      window.localStorage.setItem('type', state.user.type);
      console.log(window.localStorage.getItem("username"));
    },
    // updateUserStatus(state, username, type) {
    //   state.type = parseInt(type)
    //   window.localStorage.setItem('username', state.username);
    //   window.localStorage.setItem('type', state.type);
    // },
    clearUserInfo(state) {
      state.user.type = -1;
      window.localStorage.setItem('username', "");
      window.localStorage.setItem('type', state.user.type);
    }
  }
})

export default store