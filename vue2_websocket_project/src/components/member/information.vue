<template>
 <div>
   <h1>{{ name }}</h1>
   <h2>this is a cool guy.</h2>
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
