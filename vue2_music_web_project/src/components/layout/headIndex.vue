<template>
 <header id="head_top">

   <div class="nav_wrap">
    <ul class="wrap">
     <li><router-link to="/">首页</router-link></li>
     <li v-if="!isLogined"><router-link to="/login">登录</router-link></li>
     <li v-else><a @click.prevent="toInfo" href="##" >{{ this.$store.state.user.username }}</a></li>
     <li v-if="isManager"><router-link to="/manage">后台</router-link></li>
     <li v-if="isLogined"><a @click="logout" href="##">退出</a></li>
  </ul>
 </div>
 </header>
</template>

<script>
// import chat from "@/components/chat/chatRoom";
export default {
  components: {
    // chat
  },
  data() {
    return {};
  },
  //computed 的用法！！！！！
  computed: {
    isLogined: function() {
      let username = window.localStorage.getItem("username");
      this.$store.state.user.username = username;
      if (this.$store.state.user.username !== "" || username !== "") {
        return true;
      } else {
        return false;
      }
    },
    isManager: function() {
      let type = window.localStorage.getItem("type");
      this.$store.state.user.type = type;
      //type === 1 管理员
      if (this.$store.state.user.type === 1 || type === "1") {
        return true;
      } else {
        return false;
      }
    }
  },
  methods: {
    toInfo: function() {
      console.log("ssssss");
      let url = "/user"; //+ this.$store.state.user.username;
      this.$router.push(url);
      console.log("ssssss");
    },
    //退出
    logout: function() {
      this.$store.state.user.username = "";
      this.$store.state.user.type = -1;
      this.$store.commit("clearUserInfo");
      this.$cookieStore.delCookie("username");
      this.$cookieStore.delCookie("type");

      this.$ajax({
        method: "post",
        url: "/api/user/logout"
      }).then(res => {
        if (res.data.status === "logout") {
          this.$router.push("/");
        } else {
          alert("失败");
        }
      });
    }
  }
};
</script>

<style scoped>
.chat {
  margin-bottom: 5%;
}
header {
  height: 50px;
  border-bottom: 1px solid #eee;
}
.nav_wrap {
  text-align: right;
  float: right;
  margin-bottom: 5%;
}

.wrap ul {
  font-size: 14px;
  list-style: none;
  margin-top: 20px;
}
.wrap li {
  float: left;
  margin-right: 50px;
  list-style-type: none;
}
li a {
  font-size: 20px;
  color: #111;
  text-decoration: none;
}
</style>
