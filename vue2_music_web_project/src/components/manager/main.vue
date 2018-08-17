<template>
 <div>
  <h1>这里是管理系统</h1>
  <h2>点击右边导航栏开始管理吧宝贝</h2>
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
</style>
