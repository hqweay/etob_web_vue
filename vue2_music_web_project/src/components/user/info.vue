<template>
     <div>
        <el-form :model="account" status-icon :rules="rules" ref="SignForm" label-width="100px" class="Signin_container">
  <el-form-item label="用户：" prop="username">
    <label>{{account.username}}</label>
  </el-form-item>
  <el-form-item label="邮箱：" prop="email">
    <label>{{account.email}}</label>
  </el-form-item>
  <el-form-item label="密码：" prop="password">
    <label>{{account.password}}</label>
  </el-form-item>
  <el-form-item label="性别：" prop="sex">
    <label>{{account.sex}}</label>
  </el-form-item>
  <el-form-item prop="profileImg" label="头像：">
    <img :src="account.profileImg" class="avatar" width="50px" height="50px">
  </el-form-item>
</el-form>
    </div>
</template>
<script>
export default {
  data() {
    return {
      account: {
        username: "123456",
        password: "",
        type: 0,
        email: "",
        profileImg: "",
        sex: ""
      }
    };
  },
  created: function() {
    this.account.username = this.$cookieStore.getCookie("username");
    this.$ajax({
      // 用户的api
      url: "/api/user/getUser",
      method: "get",
      params: {
        username: this.account.username
      }
    }).then(res => {
      console.log(res);
      this.account = res.data;
      if (this.account.sex === 0) {
        this.account.sex = "男";
      } else if (this.account.sex === 1) {
        this.account.sex = "女";
      } else {
        this.account.sex = "保密";
      }
    });
  }
};
</script>

<style>
.Signin_container {
  box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);
  -webkit-border-radius: 5px;
  border-radius: 5px;
  -moz-border-radius: 5px;
  background-clip: padding-box;
  /* margin: 180px auto; */
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.el-form-item {
  /* background: #cecece; */
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
