<template>
 <div>
   <h1>{{name}}</h1>
   <p>I`m {{name}}, a member in nb_net_music, I enjoy coding.</p>
   <div v-if="name == 'Hqweay'">
      hh
   </div>
   <div v-if="name == 'Silver'">
     <p>我是一个忧郁冷漠的男孩.</p>
   </div>
    <div v-if="name == 'Hollin'">
      <p>我是一个开朗大方的男孩.</p>
   </div>
    <div v-if="name == 'Adams'">
      <p>我是一个帅气阳光的男孩.</p>
   </div>
   <p><a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=09af832588634a9d3f9d16ab9a4e710f572c1679ec07c88a77a54d749d167901">Join us</a></p>
  <p>Welecome to our music website.</p>
 </div>
</template>

<script>
//["Silver", "Hqweay", "Hollin", "Adams"]
export default {
  data() {
    return {
      name: this.$route.params.name,
      intro: ""
    };
  },
  created: function() {
    this.name = this.$route.params.name;
  },
  components: {}
};
</script>

<!-- Add scoped attribute to limit CSS to this component only -->
<style scoped>
</style>
