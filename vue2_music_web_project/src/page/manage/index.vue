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
  
   
   <!-- <router-view></router-view> -->
 </div>
</template>

<script>
import leftpForm from "@/components/manager/left";
import mainForm from "@/components/manager/main";
import songForm from "@/components/manager/song/song";
import userForm from "@/components/manager/user";
export default {
  data() {
    return {};
  },
  components: {
    leftpForm,
    mainForm,
    songForm,
    userForm
  }
};
</script>

<!-- Add scoped attribute to limit CSS to this component only -->
<style scoped>
</style>
