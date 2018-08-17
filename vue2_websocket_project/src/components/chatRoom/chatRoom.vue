<template>
<div class="chat_room">
   <ul class="comments">
      <li v-for="message in messages" class="comment">
        <small class="c_info">{{ message.date }}</small>
        <a class="c_info username" href="">{{ message.username }}</a>
        <div class="c_info message">
          {{ message.message }}
        </div>
      </li>
    </ul>
 <el-form :model="chatRoom" ref="chatRoom"  >
   <el-form-item :rules="[
            { required: true, message: '请填写留言内容', trigger: 'blur' }
          ]">
    <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 4}"
  placeholder="message"
  v-model="chatRoom.message">
    </el-input>
   </el-form-item>
    <ul class="input_body">
      <li>
        <el-form-item
    prop="email"
    :rules="[
      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
      ]">
         <el-input v-model="chatRoom.email" placeholder="email" clearable></el-input>
        </el-form-item>
      </li>
      <li>
         <el-input v-model="chatRoom.username" name="auther" placeholder="auther" clearable></el-input>
      </li>
      <li>
         <el-button  :disabled='is_legaled' size="medium" @click="addMessage('chatRoom')" round>提交</el-button>
      </li>
    </ul>
 
 </el-form>
</div>
</template>
<script>
//导入操作 websocket 的两个包
//本来应用 stockjs，但是 vue 中报错，要使用支持 es6 的 sockjs-client
import Stomp from "stompjs";
import SockJS from "sockjs-client";

const sock = new SockJS("/api/StompPort");
const stomp = Stomp.over(sock);
//es6 默认返回
export default {
  name: "chatRoom",
  data() {
    return {
      rules: {},
      dialogVisible: false,
      chatRoom: {
        email: "",
        username: "",
        message: ""
      },
      is_legaled: true,
      messages: [{}, {}]
    };
  },
  created: function() {
    //载入页面的时候订阅一次
    this.subscribe();
    this.$ajax({
      method: "get",
      url: "/api/charRoom/getNewTenMessage"
    }).then(data => {
      this.messages = data.data;
    });
  },
  watch: {
    chatRoom: {
      //对表单的值进行判断
      handler(newValue, oldValue) {
        //email 正则
        var regEmail = /^[a-zA-Z0-9_-]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
        if (
          this.chatRoom.message == "" ||
          this.chatRoom.username == "" ||
          this.chatRoom.email == "" ||
          !regEmail.test(this.chatRoom.email)
        ) {
          this.is_legaled = true;
        } else {
          this.is_legaled = false;
        }
      },
      //这个 element 组件必须要下面这句 不然报错
      deep: true
    }
  },
  methods: {
    addMessage(chatRoom) {
      this.$refs[chatRoom].validate(valid => {
        if (valid) {
          this.dialogVisible = true;
        } else {
          console.log("提交出错");
          return false;
        }
      });
      this.$ajax({
        method: "post",
        url: "/api/charRoom/addMessage",
        data: {
          username: this.chatRoom.username,
          email: this.chatRoom.email,
          message: this.chatRoom.message
        }
      }).then(data => {
        var message = JSON.stringify({
          username: this.chatRoom.username,
          email: this.chatRoom.email,
          message: this.chatRoom.message
        });
        //这一行是前端向后端的 websocket 代理发消息
        //但是后端在插消息的时候顺便进行了消息转发的操作
        //不需要前端再发了
        // stomp.send("/app/api/charRoom/sendNewMessage", {}, message);
        // }
      });
    },
    subscribe() {
      stomp.connect("guest", "guest", frame => {
        stomp.subscribe("/topic/chatRoom", message => {
          //每触发行为 该箭头函数执行一次
          var spittle = JSON.parse(message.body);
          this.messages = spittle;
        });
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.chat_room {
  width: 80%;
  margin: 0 auto;
}
.input_body {
  display: flex;
  justify-content: center;
}
ul {
  padding-left: 0px;
}
li {
  margin-top: 0.5%;
  list-style: none;
  float: left;
  margin-right: 2.2%;
}
/* input {
  width: 80%;
} */
.comments {
  display: flex;
  flex-direction: column;
}
.comment {
  display: flex;
}
.c_info {
  margin-right: 20px;
}
a {
  text-decoration: none;
}
.username::after {
  content: ":";
  margin-left: 5px;
}
</style>
