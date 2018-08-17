<template>
    <div>
        <el-row type="flex" class="row-bg" justify="space-around">
        <div :span="6">
          <h2>若您尚未注册，会为您自动注册。</h2>
          <div>
              请保证您在使用本网站时已明白相关法律法规对您的行为的限制，并保证不违反当地法律。
          </div>
      </div>

      
           <el-col :span="12"><div class="grid-content bg-purple-light">
       <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" label-width="100px" class="demo-ruleForm">
  <el-form-item  label="用户名" prop="username">
    <el-input v-model.number="loginForm.username" ></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="pass">
    <el-input type="password" v-model="loginForm.pass" auto-complete="off"></el-input>
  </el-form-item>

  <el-form-item label="确认密码" prop="checkPass">
    <el-input type="password" v-model="loginForm.checkPass" auto-complete="off"></el-input>
  </el-form-item>

  
  <transition name="el-zoom-in-top">
     <el-form-item v-show="isCompleted" label="验证码" prop="verification">
    <div class="transition-box">
   <div class="vertification-info">
     <div class="info">
        {{ this.verification.x }}  {{ this.verification.op }}  {{ this.verification.y }} = ？
     </div>
     <div class="button">
      <el-button style="margin-left: 25 px;" size="small" @click="changeV" plain>太难了！！换一个</el-button>
     </div>
      
   </div>

   <div class="input">
     <el-input placeholder="" v-model.number="loginForm.vertication"
      v-on:input.native="checkVertication" 
     
      auto-complete="off"></el-input>
   </div>
   
    </div>
    </el-form-item>
  </transition>

  <el-form-item>
    <el-button type="primary" :disabled="!canLogin" @click="submitForm('loginForm')">登录</el-button>
    <el-button @click="resetForm('loginForm')">重置</el-button>
  </el-form-item>
</el-form>
      </div></el-col>
      

</el-row>
       

    </div>
</template>

<script>
export default {
  data() {
    var checkUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("用户名不能为空"));
      } else {
        return callback(); //在这里出错啦
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.loginForm.checkPass !== "") {
          this.$refs.loginForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.loginForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      isCompleted: false,
      canLogin: false,
      loginForm: {
        username: "",
        pass: "",
        checkPass: "",
        vertication: ""
      },
      rules: {
        username: [{ validator: checkUsername, trigger: "blur" }],
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }]
        // verification: [{ validator: isVerification, trigger: "blur" }]
      },
      verification: {
        x: 0,
        y: 0,
        op: "+",
        temp: 0
      }
    };
  },
  watch: {
    loginForm: {
      handler(newValue, oldValue) {
        if (
          this.loginForm.username === "" ||
          this.loginForm.pass === "" ||
          this.loginForm.checkPass === ""
        ) {
          this.getVerification(this);
          this.isCompleted = false;
        } else {
          this.isCompleted = true;
        }
      },
      //这个 element 组件必须要下面这句 不然报错
      deep: true
    }
  },
  methods: {
    submitForm(formName) {
      //写 登录逻辑
      //官网例子有错误
      //没获取到 valid
      this.$refs[formName].validate(valid => {
        if (valid) {
          //首先尝试登录
          this.$ajax({
            method: "post",
            url: "/api/user/validate",
            data: {
              username: this.loginForm.username,
              password: this.loginForm.pass
            }
          }).then(res => {
            // console.log(res.data);
            if (res.data.status === "success") {
              // console.log("cookie?");
              //登陆成功 就跳转嘛
              //存 cookie
              //index 的 head 也相应设置
              let expireDays = 1000 * 60 * 60;
              this.$cookieStore.setCookie(
                "username",
                this.loginForm.username,
                expireDays
              ); //设置用户名
              this.$cookieStore.setCookie("type", res.data.type, expireDays); //设置用户类型
              this.$store.state.user.username = this.loginForm.username;
              this.$store.state.user.type = res.data.type;
              //存 localStore
              this.$store.commit("initUserInfo");
              //跳首页
              this.$router.push("/");
            } else if (res.data.status === "noUser") {
              //登录失败就注册
              //然后存 cookie 登录跳转
              //先不急 森林表单来搞吧
              this.$ajax({
                method: "post",
                url: "/api/user/register",
                data: {
                  username: this.loginForm.username,
                  password: this.loginForm.pass
                }
              }).then(res => {
                if (res.data.status == 1) {
                  let expireDays = 1000 * 60 * 60;
                  this.$cookieStore.setCookie(
                    "username",
                    this.loginForm.username,
                    expireDays
                  ); //设置用户名
                  this.$cookieStore.setCookie(
                    "type",
                    res.data.type,
                    expireDays
                  ); //设置用户编号
                  this.$store.state.user.username = this.loginForm.username;
                  this.$store.state.user.type = res.data.type;
                  //存 localStore
                  this.$store.commit("initUserInfo");
                  //跳首页
                  this.$router.push("/");
                } else {
                  //注册失败
                }
              });
            } else {
              //密码错误
              this.getVerification(this);
              this.canLogin = false;
              alert("密码错误");
            }
          });
          return true;
        } else {
          console.log("error submit!!");
          return true;
        }
      });
    },
    resetForm(formName) {
      this.canLogin = false;
      this.$refs[formName].resetFields();
    },
    getVerification(me) {
      me.verification.temp = 0;
      const ops = ["+", "-", "*"];
      var index = Math.round(Math.random() * (ops.length - 1));
      // me.verification.op = ops[index];
      // me.verification.x = Math.round(Math.random() * 10);
      // me.verification.y = Math.round(Math.random() * 10);
      let op = ops[index];
      // console.log("op :" + op);
      let x = Math.round(Math.random() * 198);
      let y = Math.round(Math.random() * 256);
      let temp = 0;
      switch (op) {
        case "+":
          temp = x + y; //me.verification.x + me.verification.x;
          break;
        case "-":
          temp = x - y; //me.verification.x - me.verification.x;
          break;
        case "*":
          temp = x * y; //me.verification.x * me.verification.x;
          break;
        default:
          break;
      }
      me.verification.x = x;
      me.verification.y = y;
      me.verification.op = op;
      me.verification.temp = temp;
    },
    checkVertication() {
      if (this.verification.temp == this.loginForm.vertication) {
        this.canLogin = true;
      } else {
        this.canLogin = false;
        // this.getVerification(this);
      }
    },
    changeV() {
      this.getVerification(this);
    }
  }
};
</script>
<style scoped>
.vertification-info {
  display: flex;
  justify-content: space-around;
}
</style>
