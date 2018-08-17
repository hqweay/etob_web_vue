<template>
 <div>
   <div class="search">
      <el-input
  v-model="searchName"
  @keyup.enter.native="searchSong"
  placeholder="按 Enter 键进行搜索(支持歌名，歌手，专辑查询)"
  clearable>
</el-input>
   </div>
   <ul>
     <li @click="toSongInfo" :id="song.id" v-for="song in songList" :key="song.id">
         <div class="song_img">
             <img :src="song.song_image" alt="">
         </div>
         <div class="song_info">
              歌名：<a class="song_name" :href="'#/song/' + song.id" > {{ song.name}}</a>
               <!-- 歌名：<router-link class="song_name" :to="{path:'/song/', params:{song_id: song.id }}" > {{ song.name}}</router-link> -->
      艺术家：<a href="##"> {{ song.singer}}</a>
      专辑：<a href="##"> {{ song.album}}</a>
         </div>
     </li>
   </ul>
   <div class="message" v-show="!hasDate">没有数据啊，去<a href="http://baidu.com">百度</a>找找吧。或者<a href="">通知我们</a>，让森林和浩林大佬爬一点来。</div>
 </div>
</template>

<script>
export default {
  data() {
    return {
      searchName: "",
      hasDate: true,
      songList: [
        {
          id: 1,
          name: "Rock and Roll",
          singer: "senolin",
          album: "爱的供养",
          img_url:
            "http://p1.music.126.net/r7Bw4ZDpb-GRKGK3Roq9og==/109951163426228260.jpg?param=140y140"
        }
      ]
    };
  },
  components: {},
  created: function() {
    let random_url = "/api/song/getRandomSong";
    let all_url = "/api/song/getSong";
    this.$ajax({
      method: "get",
      url: random_url
    }).then(res => {
      this.songList = res.data;
    });
  },
  methods: {
    toSongInfo: function(e) {
      // console.log(e.target);
      // this.$router.push(url);
    },
    searchSong() {
      this.$ajax({
        method: "get",
        url: "/api/song/getSong",
        params: {
          name: this.searchName
        }
      }).then(res => {
        console.log(res);
        if (res.data.length == 0) {
          this.hasDate = false;
          this.songList = res.data;
        } else {
          this.hasDate = true;
          this.songList = res.data;
        }
      });
    }
  }
};
</script>

<!-- Add scoped attribute to limit CSS to this component only -->
<style scoped>
li {
  /* text-align: center; */
  display: flex;
  margin-top: 20px;
  list-style-type: none;
}
li:hover {
  border: 1px solid rgb(70, 185, 42);
  box-shadow: 4px 4px rgb(22, 102, 75);
}
.song_info {
  display: flex;
  align-items: center;
  margin-left: 12%;
}
img {
  width: 150px;
  display: flex;
  align-items: center;
}
a {
  margin-right: 15px;
  color: black;
  text-decoration: none;
}
.song_name {
  font-size: 150%;
  color: brown;
}
.song_name:hover {
  color: darkorchid;
}

.search {
  width: 50%;
  margin: 0 auto;
}
.message {
  text-align: center;
}
.message a {
  margin: 0 auto;
  color: crimson;
}
</style>
