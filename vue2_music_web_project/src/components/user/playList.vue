<template>
     <div>
       <h1>歌单</h1>
       <ul v-for="song in songList" :key="song.id">
        <li>
          <div class="song_info">
              歌名：<a class="name" :href="'#/song/' + song.id" > {{ song.name}}</a>
               <!-- 歌名：<router-link class="song_name" :to="{path:'/song/', params:{song_id: song.id }}" > {{ song.name}}</router-link> -->
      艺术家：<a class="singer song" href="##"> {{ song.singer}}</a>
      专辑：<a class="album song" href="##"> {{ song.album}}</a>
         </div>
        </li>

       </ul>
    </div>
</template>
<script>
export default {
  data() {
    return {
      songList: []
    };
  },
  created: function() {
    let username = this.$cookieStore.getCookie("username");
    this.$ajax({
      // 用户的api
      url: "/api/score/getScoreSongsByUser",
      method: "get",
      params: {
        username: username
      }
    }).then(res => {
      this.songList = res.data.songs;
    });
  }
};
</script>

<style scoped>
.name {
  color: brown;
}
.singer,
.album {
  text-decoration: none;
}
.song {
  margin-right: 15px;
}
</style>
