<template>
 <div>
   <ul>
       <li><router-link to="/user/info">个人信息</router-link></li>
        <li><router-link to="/user/edit">修改信息</router-link></li>
       <li><router-link to="/user/playlist">我的歌单</router-link></li>
        <!-- <li><router-link to="/user/songScore">评分管理</router-link></li> -->
      
   </ul>
 </div>
</template>

<script>
//用了 props 解耦
export default {
  data() {
    return {};
  },
  props: ["name"],
  components: {}
};
</script>

<!-- Add scoped attribute to limit CSS to this component only -->
<style scoped>
ul {
  list-style-type: none;
}
li {
  margin-top: 2px;
}
a {
  color: brown;
  text-decoration: none;
}
a:hover {
  color: black;
}
</style>
