 <template>
        <!-- update dialog-->
<el-dialog title="修改信息" :visible.sync="ShowThis" @close="Close()" >
    <el-form :model="account" status-icon :rules="rules" ref="Update" label-width="70px">
  <el-form-item label="用户名" prop="username">
    <el-input type="text" v-model="account.username" auto-complete="off"></el-input>
  </el-form-item>
  <el-form-item label="邮箱" prop="email">
    <el-input type="text" v-model="account.email" auto-complete="off"></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="password">
    <el-input type="text" v-model="account.password" auto-complete="off"></el-input>
  </el-form-item>
  <el-form-item label="性别" prop="sex">
    <el-radio v-model="account.sex" label="0">男</el-radio>
  <el-radio v-model="account.sex" label="1">女</el-radio>
  <el-radio v-model="account.sex" label="2">其他</el-radio>
  </el-form-item>
  <el-form-item prop="头像">
    <input type="file" accept="image/*" @change="uploadImage">
    <img :src="account.profileImg" class="avatar" width="50px" height="50px">
  </el-form-item>
</el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="Cancle()">取 消</el-button>
    <el-button type="primary" @click="Submit()">确 定</el-button>
  </div>
</el-dialog>
<!-- update dialog-->
 </template>

<script>
var vaildateEmail = (rule, value, callback) => {
  if (value === "") {
    callback(new Error("请正确输入邮箱"));
  } else {
    if (value !== "") {
      var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (!reg.test(value)) {
        callback(new Error("请正确输入邮箱"));
      }
    }
  }
};
export default {
  props: {
    show: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: "修改信息"
    },
    User: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      formLabelWidth: "60px",
      ShowThis: this.IsShow,
      img: "",
      account: {
        username: "",
        password: "",
        type: 0,
        email: "",
        profileImg: "/images/hollin1.jpg",
        sex: null,
        filename: "error.jpg"
      },
      rules: {
        username: [{ required: true, message: "请输入用户名", tigger: "blur" }],
        password: [{ required: true, message: "密码", tigger: "blur" }],
        email: [{ message: "邮箱", tigger: "blur", validator: vaildateEmail }]
      }
    };
  },
  watch: {
    User() {
      this.account = this.User;
      console.log(this.User.password);
      if (this.account.sex === "男") {
        this.account.sex = "0";
      } else if (this.account.sex === "女") {
        this.account.sex = "1";
      } else {
        this.account.sex = "2";
      }
      if (this.account.profileImg === "") {
        this.account.profileImg = "http://www.86y.org/images/loading.gif";
      }
    },
    show() {
      // 显示次对话框
      this.ShowThis = this.show;
    }
  },
  methods: {
    Close() {
      this.ShowThis = false;
      // 使父组件不显示这个对话框
      this.$emit("Close", this.ShowThis);
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
    },
    Submit() {
      this.account.profileImg = this.account.profileImg.replace(
        "data:image/jpeg;base64,",
        ""
      );
      console.log(this.account.profileImg);
      // this.account.filename = this.account.username + ".gif";
      if (this.account.filename === undefined) {
        this.account.filename = this.account.username + ".gif";
      }
      console.log(this.account.filename);
      this.$ajax({
        // 更新用户的API
        url: "/api/user/updateInfo",
        method: "post",
        data: this.account
      }).then(res => {
        console.log(res);
        if (res.data.status === "success") {
          alert("修改成功");
          this.Close();
        } else {
          alert("修改失败");
        }
      });
    },
    // 取消修改
    Cancle() {
      this.Close();
    }
  }
};
</script>

<style>
.el-dialog {
  width: 360px;
  height: 600px;
}
.el-input {
  position: relative;
  text-align: center;
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
  font-size: 24px;
  color: #8c939d;
  width: 80px;
  height: 80px;
  line-height: 80px;
  text-align: center;
}
.avatar {
  width: 80px;
  height: 80px;
  display: block;
}
</style>
