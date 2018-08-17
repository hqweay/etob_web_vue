<template>
  <div>
    <headIndex></headIndex> 
    <router-view></router-view>
    <footerIndex></footerIndex>
  </div>
</template>

<script>
import headIndex from "@/components/layout/headIndex";
import footerIndex from "@/components/layout/footerIndex";
import index from "@/page/index/index";
export default {
  components: {
    headIndex,
    index,
    footerIndex
  }
};
</script>

<style>
body {
  font: 400 16px / 1.62 "PT Sans", "HelveticaNeue", "Helvetica Neue", Helvetica,
    Arial, Hiragino Sans GB, Microsoft YaHei, sans-serif;
  line-height: 1.62;
  line-height: 1.9;
  width: 80%;
  margin: 0 auto;
  max-width: 920px;
}
</style>

<style scoped>
div {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  margin-top: 60px;
}
</style>
