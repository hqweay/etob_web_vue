<template>
 <div>
   <ul>
     <li v-for="song in songList" :key="song.id">
      歌名：<a :href="'#/song/' + song.id" > {{ song.name}}</a>
      艺术家：<a href="##"> {{ song.singer}}</a>
      专辑：<a href="##"> {{ song.album}}</a>
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
  components: {},
  created: function() {
    this.$ajax({
      method: "get",
      url: "/api/song/getsong"
    }).then(res => {
      // console.log(res);
      this.songList = res.data;
      // console.log(this.songList);
    });
  }
};
</script>

<!-- Add scoped attribute to limit CSS to this component only -->
<style scoped>
div {
  text-align: center;
}
li {
  list-style-type: none;
}
a {
  margin-right: 15px;
  color: brown;
  text-decoration: none;
}
a:hover {
  color: black;
}
</style>
