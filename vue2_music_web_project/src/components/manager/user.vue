<template>
 <div>

   <div class="nav">
     <div class="search">
      <el-input
  placeholder="按 Enter 键进行搜索"
  v-model="input10"
  clearable>
</el-input>
     </div>
     <div class="delete">
       <el-button
          size="medium  "
          type="danger"
          @click="handleEdit(scope.$index, scope.row)">删除选中</el-button>
     </div>
      <div class="addSong">
       <el-button
          size="medium"
          type="primary"
          @click="addFormvisable = true">添加</el-button>
     </div>
   </div>
<!-- add form -->
<el-dialog title="收货地址" :visible.sync="addFormvisable">
  <el-form :model="addForm">
    <el-form-item label="活动名称" :label-width="formLabelWidth">
      <el-input v-model="addForm.name" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item label="活动区域" :label-width="formLabelWidth">
      <el-select v-model="addForm.name" placeholder="请选择活动区域">
        <el-option label="区域一" value="shanghai"></el-option>
        <el-option label="区域二" value="beijing"></el-option>
      </el-select>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
  </div>
</el-dialog>
<!-- -->


   <el-table
  @select="select"
    :data="songs"
    style="width: 100%">
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      label="id"
      width="180">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.id }}</span>
      </template>
    </el-table-column>
    <el-table-column
      label="歌名"
      width="180">
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
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <!-- <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
      </template> 
    </el-table-column>
  </el-table>
 
 <div class="block" style="float:right;margin-top:20px;">
  <el-pagination
    layout="prev, pager, next"
    :total="50">
  </el-pagination>
 </div>
</div>


</template>

<script>
//用了 props 解耦
export default {
  data() {
    return {
      addFormvisable: false,
      addForm: {
        name: "ss"
      },
      deleteSelect: [{}],
      songs: [
        {
          id: "1",
          name: "爱的供养",
          singer: "假森林",
          album: "地球爆炸",
          frequency: "128",
          duration: "3:40",
          link: "http://baidu.com/fffffffffffffffffffffffffffffff",
          score: "5"
        },
        {
          id: "12",
          name: "爱的供养",
          singer: "假森林",
          album: "地球爆炸",
          frequency: "128",
          duration: "3:40",
          link: "http://baidu.com/fffffffffffffffffffffffffffffff",
          score: "5"
        },
        {
          id: "3",
          name: "爱的供养",
          singer: "假森林",
          album: "地球爆炸",
          frequency: "128",
          duration: "3:40",
          link: "http://baidu.com/fffffffffffffffffffffffffffffff",
          score: "5"
        },
        {
          id: "3",
          name: "爱的供养",
          singer: "假森林",
          album: "地球爆炸",
          frequency: "128",
          duration: "3:40",
          link: "http://baidu.com/fffffffffffffffffffffffffffffff",
          score: "5"
        },
        {
          id: "3",
          name: "爱的供养",
          singer: "假森林",
          album: "地球爆炸",
          frequency: "128",
          duration: "3:40",
          link: "http://baidu.com/fffffffffffffffffffffffffffffff",
          score: "5"
        },
        {
          id: "3",
          name: "爱的供养",
          singer: "假森林",
          album: "地球爆炸",
          frequency: "128",
          duration: "3:40",
          link: "http://baidu.com/fffffffffffffffffffffffffffffff",
          score: "5"
        }
      ]
    };
  },
  props: ["name"],
  components: {},
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    select(selection, row) {
      console.log(row);
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
</style>
