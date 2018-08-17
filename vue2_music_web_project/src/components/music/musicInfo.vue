<template>
  <div class="main">
   <div class="chat">
      <chat></chat>
   </div>
  <div class="index">
    <div class="music-player">
      <h1>{{ this.name }}</h1>
      <img :src="img"  id="albumImg">
      <audio :src="link" controls="controls" id="music"></audio>
      <div class="change">
       <el-button-group>
  <el-button type="info" size="small" @click="preSong" icon="el-icon-arrow-left">上一曲</el-button>
  <el-button type="info" size="small" @click="nextSong" icon="el-icon-arrow-left">下一曲</el-button>
</el-button-group>
      </div>
    </div>
   <div class="score">
      <label>平均分:</label>
      <el-rate
      v-model="score"
      disabled
      show-score
      text-color="#ff9900">
      </el-rate>
      <div class="count">
        评价人数：{{ song_score_num }}
      </div>
      
   </div>
    <el-button type="text" v-show="isLogined" @click="dialogFormVisible = true">我要评价</el-button>
    <el-dialog title="评分" :visible.sync="dialogFormVisible">

    <el-form  :model="judge" ref="ScoreForm">
    <el-form-item label="评价" :label-width="formLabelWidth">
    <el-rate v-model="judge.score" ></el-rate>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取消</el-button>
    <el-button type="primary" @click="addScore()">确 定</el-button>
  </div>
</el-dialog>
  </div>
    </div>
</template>
<script>
import chat from "@/components/chat/chatRoom";

export default {
  components: {
    chat
  },
  data() {
    return {
      isLogined: false,
      formLabelWidth: "60px",
      dialogFormVisible: false,
      name: "",
      id: 0,
      score: 0,
      img: "",
      link: "http://music.163.com/song/media/outer/url?id=26508186.mp3",
      judge: {
        score: 0
      },
      song_score_num: 0,
      flag: false
    };
  },
  watch: {
    $route(to, from) {
      //在这里重新刷新一下
      this.getParams();
    }
  },
  created: function() {
    let username = this.$cookieStore.getCookie("username");
    if (username != null || username != undefined) {
      console.log("ss");
      this.isLogined = true;
    } else {
      this.isLogined = false;
    }
    this.id = this.$route.params.song_id;
    this.$ajax({
      url: "/api/song/getSongById",
      method: "post",
      data: {
        id: this.$route.params.song_id
      }
    }).then(res => {
      // console.log(res);
      if (res.data.status === "success") {
        this.name = res.data.song.name;
        this.id = res.data.song.id;
        this.img = res.data.song.song_image;
        this.link = res.data.song.link;
        this.score = res.data.song.score;
      }
    });

    this.$ajax({
      url: "/api/score/getSongScoreNum",
      method: "get",
      params: {
        id: this.id
      }
    }).then(res => {
      this.song_score_num = res.data.num;
    });
  },
  methods: {
    preSong: function() {
      this.song_score_num = 0;
      this.$ajax({
        url: "/api/song/formerSong",
        method: "get",
        params: {
          id: this.id
        }
      }).then(res => {
        this.name = res.data.name;
        this.id = res.data.id;
        this.img = res.data.song_image;
        this.link = res.data.link;
        this.score = res.data.score;
        this.getSongScoreNum(this);
      });

      this.$ajax({
        url: "/api/score/getSongScoreNum",
        method: "get",
        params: {
          id: this.id
        }
      }).then(res => {
        this.song_score_num = res.data.num;
      });
    },
    nextSong: function() {
      this.song_score_num = 0;

      this.$ajax({
        url: "/api/song/nextSong",
        method: "get",
        params: {
          id: this.id
        }
      }).then(res => {
        this.name = res.data.name;
        this.id = res.data.id;
        this.img = res.data.song_image;
        this.link = res.data.link;
        this.score = res.data.score;
        this.getSongScoreNum(this);
      });

      this.$ajax({
        url: "/api/score/getSongScoreNum",
        method: "get",
        params: {
          id: this.id
        }
      }).then(res => {
        this.song_score_num = res.data.num;
      });
    },
    addScore: function() {
      this.$ajax({
        url: "/api/score/insertScore",
        method: "post",
        data: {
          username: this.$cookieStore.getCookie("username"),
          id: this.id,
          score: this.judge.score
        }
      }).then(res => {
        this.getSongScoreNum(this);
        if (res.data.status === "success") {
          this.$ajax({
            url: "/api/song/getSongById",
            method: "post",
            data: {
              id: this.$route.params.song_id
            }
          }).then(res => {
            if (res.data.status === "success") {
              this.score = res.data.song.score;
            }
          });
          this.dialogFormVisible = false;
          //成功
        } else {
          //失败
          this.dialogFormVisible = false;
        }
      });
    },
    getSongScoreNum: function(me) {
      this.$ajax({
        url: "/api/score/getSongScoreNum",
        method: "get",
        params: {
          id: me.id
        }
      }).then(function(res) {
        me.song_score_num = res.data.num;
      });
    }
  }
};
</script>

<style>
.main {
  display: flex;
}
.chat {
}
.index {
  box-shadow: 0 0px 8px 0 rgba(235, 231, 231, 0.06),
    0 1px 0px 0 rgba(150, 149, 149, 0.02);
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
.music-player {
  margin: 0 auto;
  text-align: center;
}
.change {
  display: flex;
  /* justify-content: center; */
  justify-content: space-around;
}
.score {
  margin-top: 5%;
  display: flex;
  justify-content: space-around;
}
img {
  width: 240px;
  /* height: 240px; */
}
.count {
  float: left;
}
</style>
