<template>
    <el-form :model="account" status-icon :rules="rules" ref="SignForm" label-width="100px" class="Signin_container">
  <el-form-item label="用户" prop="username">
    <el-input type="text" v-model="account.username" auto-complete="off"></el-input>
  </el-form-item>
  <el-form-item label="邮箱" prop="email">
    <el-input type="text" v-model="account.email" auto-complete="off"></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="password">
    <el-input type="password" v-model="account.password" auto-complete="off"></el-input>
  </el-form-item>
  <el-form-item label="性别" prop="sex">
    <el-radio v-model="account.sex" label="0">男</el-radio>
    <el-radio v-model="account.sex" label="1">女</el-radio>
    <el-radio v-model="account.sex" label="2">其他</el-radio>
  </el-form-item>
  <el-form-item prop="profile_img" label="头像">
    <input type="file" accept="image/*" @change="uploadImage">
    <img :src="account.profileImg" class="avatar" width="50px" height="50px">
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="SignIn" :disabled='!isSubmit'>更改</el-button>
    <el-button @click="resetForm('SignForm')">重置</el-button>
  </el-form-item>
</el-form>
</template>

<script>
export default {
  data() {
    return {
      img: "",
      account: {
        username: "",
        password: "",
        //   type: 0,
        email: "",
        profileImg: "http://www.86y.org/images/loading.gif",
        filename: "",
        sex: ""
      },
      rules: {
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        username: [{ required: true, message: "请输入用户名", tigger: "blur" }],
        password: [{ required: true, message: "密码", tigger: "blur" }]
      },
      isSubmit: false
    };
  },
  created() {
    let username = this.$cookieStore.getCookie("username");
    this.account.username = username;
    console.log("市书法家");
    console.log(username);
  },
  watch: {
    account: {
      handler(newValue, oldValue) {
        if (this.account === "" || this.account.password === "") {
          this.isSubmit = false;
        } else {
          this.isSubmit = true;
        }
      },
      //这个 element 组件必须要下面这句 不然报错
      deep: true
    }
  },
  methods: {
    SignIn() {
      if (this.account === "" || this.account.password === "") {
        alert("请输入用户名或者密码");
        // location.href = "/sign";
      } else {
        let config = {
          headers: { "Content-Type": "multipart/form-data" }
        };
        this.account.profileImg = this.account.profileImg.replace(
          "data:image/jpeg;base64,",
          ""
        );
        this.$ajax({
          //更新
          url: "/api/user/updateInfo",
          method: "post",
          data: this.account
        }).then(res => {
          console.log(res);
          if (res.data.status === "success") {
            this.$store.state.user.username = "";
            this.$store.state.user.type = -1;
            this.$cookieStore.delCookie("username");
            this.$cookieStore.delCookie("type");
            this.$store.commit("clearUserInfo");
            this.$router.push("/");
          } else {
            //更改失败
          }
        });
      }
    },
    // 将表单设置为空
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    uploadImage(e) {
      var files = e.target.files;
      var file = files[0];
      var render = new FileReader();
      render.onload = data => {
        let res = data.target;
        this.account.profileImg = res.result;
      };
      render.readAsDataURL(file);
      this.account.filename = file.name;
    }
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
  /* margin: 20px auto; */
  margin-left: 5%;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
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
