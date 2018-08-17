<template>
 <div>

    <div class="nav">
     <div class="search">
  <el-input
  v-model="searchName"
  @keyup.enter.native="searchSong"
  placeholder="按 Enter 键进行搜索(支持歌名，歌手，专辑查询)"
  clearable>
</el-input>
     </div>
 <div class="delete">
       <el-button
          size="medium  "
          type="danger"
          @click="deleSeletions">删除选中</el-button>
     </div>
      <div class="addSong">
       <el-button
          size="medium"
          type="primary"
          @click="addEditFormVisable = true;addForm = true;editForm = false;toAdd()">添加</el-button>
     </div>
   </div> 

<!-- add and edit form -->
<el-dialog  title="操作歌曲" :visible.sync="addEditFormVisable" width="25%">
  <el-form>
    <!-- <el-form-item label="id" >
      <el-input v-model="addEditForm.id" auto-complete="off"></el-input>
    </el-form-item> -->
    <el-form-item label="歌名" >
      <el-input v-model="addEditForm.name" auto-complete="off"></el-input>
    </el-form-item>
     <el-form-item label="艺术家" >
      <el-input v-model="addEditForm.singer" auto-complete="off"></el-input>
    </el-form-item>
     <el-form-item label="专辑" >
      <el-input v-model="addEditForm.album" auto-complete="off"></el-input>
    </el-form-item>
    <!-- <el-form-item label="比特" >
      <el-input v-model="addEditForm.frequency" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item label="时长" >
      <el-input v-model="addEditForm.duration" auto-complete="off"></el-input>
    </el-form-item> -->
     <!-- <el-form-item label="分数" >
      <el-input v-model="addEditForm.score" auto-complete="off"></el-input>
    </el-form-item> -->
    <el-form-item label="链接" >
      <el-input v-model="addEditForm.link" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item label="图片链接" >
      <el-input v-model="addEditForm.song_image" auto-complete="off"></el-input>
    </el-form-item>
  </el-form>
   <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="addEditSong()">确 定</el-button>
   </span>
</el-dialog>
<!-- -->

<!-- search form -->
<el-dialog title="搜索" :visible.sync="searchDialogVisable" width="60%">
   <el-table
   class="song_list"
   @select="select"
   @select-all="selectAll"
    :data="searchSongList"
    style="width: 100%">
 
    <el-table-column
      label="id"
      width="150">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.id }}</span>
      </template>
    </el-table-column>
    <el-table-column
      label="歌名"
      width="400">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>歌名: {{ scope.row.name }}</p>
          <p>歌手: {{ scope.row.singer }}</p>
          <p>专辑: {{ scope.row.album }}</p>
          <p>比特: {{ scope.row.frequency }}</p>
          <p>时长: {{ scope.row.duration }}</p>
          <p>链接: {{ scope.row.link }}</p>
          <p>评分: {{ scope.row.score }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.name }}</el-tag>
          </div>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column
    label="操作">
      <template slot-scope="scope">
        <el-button
              size="mini"
          @click="addEditFormVisable = true;editForm = true;addForm =false;toEdit(scope.$index, scope.row)">编辑</el-button>
           <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template> 
    </el-table-column>
  </el-table>
</el-dialog>
<!-- -->


  
   <el-table
   class="song_list"
   @select="select"
   @select-all="selectAll"
    :data="songList"
    style="width: 100%">
    
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      label="id"
      width="150">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.id }}</span>
      </template>
    </el-table-column>
    <el-table-column
      label="歌名"
      width="400">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>歌名: {{ scope.row.name }}</p>
          <p>歌手: {{ scope.row.singer }}</p>
          <p>专辑: {{ scope.row.album }}</p>
          <p>比特: {{ scope.row.frequency }}</p>
          <p>时长: {{ scope.row.duration }}</p>
          <p>链接: {{ scope.row.link }}</p>
          <p>评分: {{ scope.row.score }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.name }}</el-tag>
          </div>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column
    label="操作">
      <template slot-scope="scope">
        <el-button
              size="mini"
              @click="addEditFormVisable = true;editForm = true;addForm =false;toEdit(scope.$index, scope.row)"
         >编辑</el-button>
      </template> 
    </el-table-column>
  </el-table>

 
  <div class="block page">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="page.pageNum"
      :pager-count="5"
      layout="prev, pager, next, jumper"
      :total="page.count">
    </el-pagination>
  </div>
</div>


</template>

<script>
//
export default {
  data() {
    return {
      addEditFormVisable: false,
      editForm: false,
      addForm: false,
      searchDialogVisable: false,
      addEditForm: {
        id: 100,
        name: "爱的供养",
        singer: "假森林",
        album: "Rock and Roll",
        frequency: "555",
        duration: 0,
        link: "5",
        score: "5",
        song_image: "55"
      },
      deleteIds: new Array(),
      searchSongList: [],
      songList: [],
      page: {
        pageNum: 0,
        pzgeSize: 10,
        count: 0
      },
      searchName: ""
    };
  },
  props: ["name"],
  created: function() {
    this.$ajax({
      method: "get",
      url: "/api/manager/getSongsByPage",
      params: {
        pageNum: 1,
        pageSize: 10
      }
    }).then(res => {
      this.page.count = res.data.count;
      this.songList = res.data.songs;
    });
  },
  methods: {
    handleEdit(index, row) {
      console.log("handleEdit");
      ///api/song/editSong
      this.$ajax({
        methos: "post",
        url: "/api/song/editSong",
        data: {
          id: row.id
        }
      }).then(res => {
        if (res.data.status == "success") {
          alert(成功);
        } else {
          alert(失败);
        }
      });
    },
    handleDelete(index, row) {
      var deleteIds = [row.id];
      this.$ajax({
        method: "post",
        url: "/api/manager/deleteSong",
        data: {
          ids: deleteIds
        }
      }).then(res => {
        if (res.data.status === "success") {
          //更新
          this.$ajax({
            method: "get",
            url: "/api/song/getSong",
            params: {
              name: this.searchName
            }
          }).then(res => {
            // this.page.count = res.data.count;
            this.searchSongList = res.data;
            this.searchDialogVisable = true;
          });
        } else {
          alert("失败");
        }
      });
    },
    select(selection, row) {
      this.deleteIds = [];
      selection.forEach(element => {
        this.deleteIds.push(element.id);
      });
    },
    selectAll(selection) {
      this.deleteIds = [];
      selection.forEach(element => {
        this.deleteIds.push(element.id);
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      this.page.pageNum = val;
      //当前页改变 请求一次
      this.$ajax({
        method: "get",
        url: "/api/manager/getSongsByPage",
        params: {
          pageNum: this.page.pageNum,
          pageSize: 10
        }
      }).then(res => {
        // this.songList.count = res.data.count;
        this.songList = res.data.songs;
      });
    },
    addEditSong() {
      console.log("ss" + this.editForm + "ss" + this.addForm);
      var url = "";
      var data = {};
      if (this.addForm == true) {
        url = "/api/manager/addSong";
      } else {
        url = "/api/manage/editSong";
      }
      this.$ajax({
        method: "post",
        url: url, //"/api/manager/addSong",
        data: this.addEditForm
      }).then(res => {
        if (res.data.status == "success") {
          alert("添加成功");
        } else if (res.data.status == "true") {
          alert("编辑成功");
        } else if (res.data.status != "true") {
          alert("编辑失败");
        } else {
          alert("添加失败");
        }

        this.addEditFormVisable = false;
      });
      this.addFormvisable = false;
    },
    deleSeletions() {
      this.$ajax({
        method: "post",
        url: "/api/manager/deleteSong",
        data: {
          ids: this.deleteIds
        }
      }).then(res => {
        if (res.data.status === "success") {
          //更新
          this.$ajax({
            method: "get",
            url: "/api/manager/getSongsByPage",
            params: {
              pageNum: 1,
              pageSize: 10
            }
          }).then(res => {
            this.page.count = res.data.count;
            this.songList = res.data.songs;
          });
        } else {
          alert("失败");
        }
      });
    },
    searchSong() {
      this.$ajax({
        method: "get",
        url: "/api/song/getSong",
        params: {
          name: this.searchName
        }
      }).then(res => {
        // this.page.count = res.data.count;
        this.searchSongList = res.data;
        this.searchDialogVisable = true;
      });
    },
    toEdit(index, row) {
      this.addEditForm.id = row.id;
      this.addEditForm.name = row.name;
      this.addEditForm.singer = row.singer;
      this.addEditForm.album = row.album;
      this.addEditForm.frequency = row.frequency;
      this.addEditForm.duration = row.duration;
      this.addEditForm.link = row.link;
      this.addEditForm.song_image = row.song_image;
      this.addEditForm.score = row.score;
    },
    toAdd() {
      this.addEditForm.name = "Rock and Roll";
      this.addEditForm.singer = "迦森林";
      this.addEditForm.album = "爱的供养";
      this.addEditForm.frequency = "";
      this.addEditForm.duration = 0;
      this.addEditForm.link = "";
      this.addEditForm.song_image = "";
      this.addEditForm.score = "";
    }
  }
};
</script>
<!-- Add scoped attribute to limit CSS to this component only -->
<style scoped>
.nav {
  display: flex;
  justify-content: space-around;
}
.song_list {
  margin-top: 4%;
}
.page {
  margin-top: 3%;
  float: right;
}
</style>
