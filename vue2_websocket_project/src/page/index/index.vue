<template>
  <div>
    <carouselIndex></carouselIndex>
    <chatRoom></chatRoom>
  </div>
</template>

<script>
import chatRoom from "@/components/chatRoom/chatRoom";
import headIndex from "@/components/layout/headIndex";
import carouselIndex from "@/components/carousel/index";
import information from "@/components/member/information";
//es6 默认返回
export default {
  components: {
    chatRoom,
    headIndex,
    carouselIndex,
    information
  },
  data() {
    return {};
  },
  computed: {},
  watch: {
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
</style>
