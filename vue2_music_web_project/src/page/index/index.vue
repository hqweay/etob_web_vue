<template>
  <div>
    <keep-alive>
    <carouselIndex></carouselIndex>
    </keep-alive>

  
    <musicIndex></musicIndex>
    
  </div>
</template>

<script>
import headIndex from "@/components/layout/headIndex";
import carouselIndex from "@/components/carousel/index";
import musicList from "@/components/music/musicList";
import musicIndex from "@/components/music/musicIndex";
//es6 默认返回
export default {
  components: {
    headIndex,
    carouselIndex,
    musicList,
    musicIndex
  },
  data() {
    return {};
  },
  computed: {}
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
