<template>
<div>
    <updateInfo :show="editShow" title="修改" v-on:Close="Close" :User="user"></updateInfo>
  <el-table :data="users" class="userForm">
    <el-table-column label="用户名" width="180">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.username }}</span>
      </template>
    </el-table-column>
    <el-table-column label="邮箱" width="180">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>姓名: {{ scope.row.username }}</p>
          <p>邮箱: {{ scope.row.email }}</p>
          <p>性别：{{ scope.row.sex}}</p>
          <img :src='scope.row.profile_img' width="10px" height="10px">
          <div slot="reference" class="name-wrapper">
            <el-tag >{{ scope.row.email}}</el-tag>
          </div>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column label="性别" width="180">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.sex }}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
        <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <br />
  <div class="block">
    <el-pagination id='separate'
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="count">
    </el-pagination>
    </div>
</div>
</template>

<script>
import Edit from "./Edit";
export default {
  components: {
    updateInfo: Edit
  },
  data() {
    return {
      user: {},
      editShow: false,
      users: [],
      currentPage: 1,
      pageSize: 10,
      count: 0
    };
  },
  created: function() {
    this.$ajax({
      url: "/api/manage/user/GetUserByPage",
      method: "get",
      params: {
        pageNum: this.currentPage,
        pageSize: this.pageSize
      }
    }).then(res => {
      this.count = res.data.count;
      this.users = res.data.users;
      this.users.forEach(e => {
        if (e.sex === 0) {
          e.sex = "男";
        } else if (e.sex === 1) {
          e.sex = "女";
        } else {
          e.sex = "保密";
        }
        if (e.email === "") {
          e.email = "无";
        }
      });
    });
  },
  methods: {
    handleCurrentChange: function(val) {
      this.currentPage = val;
      this.$ajax({
        url: "/api/manage/user/GetUserByPage",
        method: "get",
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.count = res.data.count;
        this.users = res.data.users;
        this.users.forEach(e => {
          if (e.sex === 0) {
            e.sex = "男";
          } else if (e.sex === 1) {
            e.sex = "女";
          } else {
            e.sex = "保密";
          }
          if (e.email === "") {
            e.email = "无";
          }
        });
      });
    },
    // 编辑用户信息
    handleEdit(row) {
      this.editShow = true;
      this.user = row;
    },
    // 删除用户信息
    handleDelete(row) {
      this.$ajax({
        // 根据用户名删除
        url: "/api/manage/deleteUser",
        method: "post",
        data: {
          username: row.username // 通过用户名来删除
        }
      }).then(res => {
        console.log(res);
        this.handleCurrentChange(this.currentPage);
      });
    },
    // 防止出错
    handleSizeChange() {},
    // 修改后关闭对话框
    Close() {
      this.editShow = false;
      this.handleCurrentChange(this.currentPage);
    }
  }
};
</script>

<style>
.userForm {
  /* width: 80%; */
  /* position: left; */
  /* top: 20px; */
  /* left: 320px; */
}
#separate {
  position: relative;
  bottom: 0;
  left: 320px;
}
</style>
