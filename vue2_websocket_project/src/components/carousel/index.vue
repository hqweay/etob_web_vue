// 轮播组件
<template>
  <el-carousel :interval="4000" type="card" height="200px">
    <el-carousel-item @click.native="show_info" v-for="info in informations" :key="info">
      <h1>
        {{ info }}
      </h1>
    </el-carousel-item>
  </el-carousel>
</template>

<script>
//注意 原生组件 .native 事件才可用
export default {
  data() {
    return {
      informations: ["hqweay", "silvercv", "mrblankspace"]
    };
  },
  methods: {
    show_info: function(e) {
      //取得点击的组件的值
      //转发路由到详情页
      if (e.target.innerText) {
        this.$store.state.index.member_info = "information";
        let url = "/member/" + e.target.innerText;
        this.$router.push(url);
      }
    }
  },
  components: {}
};
</script>

<!-- Add scoped attribute to limit CSS to this component only -->
<style scoped>
.el-carousel {
  margin: 75px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.el-carousel__item--card {
  display: flex;
  align-items: center;
}
h1 {
  margin: 0 auto;
}
</style>
