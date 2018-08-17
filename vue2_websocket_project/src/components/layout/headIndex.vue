<template>
 <header id="head_top">
   <div class="nav_wrap">
    <ul class="wrap">
     <li><router-link to="/">Home</router-link></li>
     <li><router-link to="/about">About</router-link></li>
     <li><router-link to="/copyright">CopyRight</router-link></li>
   </ul>
 </div>
 </header>
</template>

<script>
export default {
  name: "headIndex",
  data() {
    return {};
  }
};
</script>

<style scoped>
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
  color: #111;
  text-decoration: none;
}
</style>
