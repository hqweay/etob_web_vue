<template>
 <div>
   <el-row type="flex" class="row-bg" justify="space-around">
  <el-col :span="7">
     <leftpForm></leftpForm>
  </el-col>
  <el-col :span="17">
    <keep-alive>
    <router-view></router-view>
    </keep-alive>
  </el-col>
</el-row>
  
 </div>
</template>

<script>
import leftpForm from "@/components/user/left";
import infoForm from "@/components/user/edit";
export default {
  data() {
    return {};
  },
  components: {
    leftpForm,
    infoForm
  }
};
</script>

<!-- Add scoped attribute to limit CSS to this component only -->
<style scoped>
</style>
