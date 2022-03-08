<!--
 * @Descripttion: 每日打卡数据
 * @version: 
 * @Author: 张艳伟
 * @Date: 2021年10月21日20:26:50
 * @LastEditors: 
 * @LastEditTime: 
 -->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <el-tabs v-model="activeName">
        <div style="width: 100%">
          <!-- 时间控件 -->

          <el-table
            :data="timeTableData"
            id="timeTableData"
            style="width: 100%"
            height="500"
          >
            <el-table-column
              prop="createTime"
              label="时间"
              style="width: 10%"
              sortable
            ></el-table-column>
            <!-- <el-table-column
              prop="userCode"
              label="学号"
              style="width: 10%"
           
            ></el-table-column>
            <el-table-column
              prop="userName"
              label="姓名"
              style="width: 10%"
            ></el-table-column> -->

            <el-table-column
              prop="integralSource"
              label="积分来源"
              style="width: 10%"
            ></el-table-column>
            <el-table-column
              prop="smallSource"
              label="来源细分"
              style="width: 10%"
            ></el-table-column>
            <el-table-column
              prop="integralReward"
              label="获得积分"
              style="width: 10%"
              sortable
            ></el-table-column>
          </el-table>
          <br />
        </div>
        共{{total}}条数据
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import FileSaver from "file-saver";
import XLSX from "xlsx";
export default {
  data() {
    return {
      total:0,
      className: "",
      // 个人成绩
      personTableData: [],
      fn1: "",
      userid: "",
      userCode: localStorage.getItem("usercode"),

      //接收后端返回来的根据时间查询的表格数据
      timeTableData: [],
    };
  },
  created() {
    // 获取个人成绩
    var vm = this;
    vm.handleUserid();
    vm.selectPunch();
  },

  methods: {
    formatter(row, column) {
      return row.id;
    },
    handleUserid(val) {
      var vm = this;
      vm.userid = localStorage.getItem("userid");
    },

    //根据时间查询各个阶段的人名单
    selectPunch() {
      var vm = this;
      // console.log("进入方法");
      vm.$axios
        .get("/ty_person_integral/selectPunchByUserCode/" + vm.userCode)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.timeTableData = response.data.data;
            vm.total= response.data.data.length;
          } else if (response.data.code === "1111") {
            // vm.$message({
            //   message: "暂无数据",
            //   type: "warning",
            // });
            vm.timeTableData = "";
          }
        });
    },
  },
};
</script>
<style scoped>
.breadcrumb {
  margin-top: 2%;
  margin-left: 2%;
}
.MainCard {
  margin-top: 3% !important;
}
.center {
  margin-bottom: 20px;
}
</style>
