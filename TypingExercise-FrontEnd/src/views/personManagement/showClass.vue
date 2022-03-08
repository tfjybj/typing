<!--
 * @Descripttion: 教师端选择要展示的班级
 * @version: 
 * @Author: 
 * @Date: 
 * @LastEditors: 张艳伟
 * @LastEditTime: 2021年11月7日16:25:53
 -->

<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <el-table :data="classNameData" style="width: 100%">
        <el-table-column
          prop="number"
          label="序号"
          type="index"
          style="width: 30%"
        ></el-table-column>
        <el-table-column
          prop="className"
          label="班级名称"
          style="width: 30%"
        ></el-table-column>
        <el-table-column
          prop="isDelete"
          label="当前状态"
          style="width: 30%"
        ></el-table-column>
        <el-table-column label="看板是否显示" style="width: 30%">
          <template v-slot:default="slotProps"
            >　　　　　
            <el-button type="primary" @click="showClassName(slotProps.row)"
              >展示</el-button
            >
            <el-button type="warning" @click="noShowClassName(slotProps.row)"
              >不展示</el-button
            >　　　
          </template>
        </el-table-column>
      </el-table>
      <br />

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 40]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeName: "personList6",
      //用来接收表格的数据
      classNameData: [],
      //总条数默认为0
      total: 0,
      //当前页默认值是1
      currentPage: 1,
      //默认每页显示10条
      pagesize: 10,
    };
  },
  created() {
    var vm = this;
    //获取班级数据
    vm.selectLevelData(vm.currentPage, vm.pagesize);
  },
  mounted() {
    localStorage.setItem("size", this.pagesize);
  },
  methods: {
    //每页的记录数改变
    handleSizeChange(val) {
      var vm = this;
      localStorage.setItem("size", val); //让缓存中存储size--当前页有多少条数据
      vm.selectLevelData(vm.currentPage, val);
    },
    //当前页改变
    handleCurrentChange(val) {
      var vm = this;
      this.currentPage = val;
      vm.pagesize = localStorage.getItem("size"); // currentPage  存放当前为第几页
      vm.selectLevelData(val, vm.pagesize);
    },
    //查询班级信息
    selectLevelData(page, size) {
      var vm = this;
      vm.$axios
        .get("/personTeam/querClassName/" + page + "/" + size)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.total = response.data.data.total;
            vm.classNameData = response.data.data.list;
          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        });
    },
    //不展示班级信息
    noShowClassName(info) {
      var vm = this;
      vm.size = localStorage.getItem("size");
      vm.$axios
        .post("/personTeam/updateClassNameIsDelete", info.className)
        .then(function (response) {
          // console.log(info.isDelete);
          if (info.isDelete == "展示") {
            if (response.data.code === "0000") {
              vm.$message.success("修改成功");
              //调用查询方法
              vm.selectLevelData(1, vm.size);
            } else {
              vm.$message.error("修改失败");
              return false;
            }
          } else {
            vm.$message.info("当前为不展示状态");
          }
        });
    },
    //展示班级信息按钮
    showClassName(info) {
      var vm = this;
      vm.size = localStorage.getItem("size");
      vm.$axios
        .post("/personTeam/updateClassName", info.className)
        .then(function (response) {
          // console.log(info.isDelete);
          if (info.isDelete == "不展示") {
            if (response.data.code === "0000") {
              vm.$message.success("修改成功");
              //调用查询方法
              vm.selectLevelData(1, vm.size);
            } else {
              vm.$message.error("修改失败");
              return false;
            }
          } else {
            vm.$message.info("当前为展示状态");
          }
        });
    },
  },
};
</script>
<style scoped>
.el-dialog-input {
  width: 50%;
}
.word {
  margin-left: 24px;
}
.editWord {
  margin-left: 24px;
}
/deep/.el-table th > .cell {
  text-align: center;
}
/deep/.el-table .cell {
  text-align: center;
}
</style>
