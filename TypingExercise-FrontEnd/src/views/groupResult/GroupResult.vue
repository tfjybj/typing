<!--
 * @Descripttion: 
 * @version: 
 * @Author: 冯佳兴
 * @Date: 2019-09-24 20:05:47
 * @LastEditors: sueRimn
 * @LastEditTime: 2019-09-26 12:23:37
 -->
<template>
  <div>
    <el-card style="margin-top:2.5%;width:95.5%;margin-left:2%">
      <el-tabs >
        <el-tab-pane label="小组成绩" name="groupDataList">
          <div style="width:100%">
            <el-table :data="groupData" style="width: 100%">
              <el-table-column prop="rank" label="名次" style="width:10%"></el-table-column>
              <el-table-column prop="userName" label="姓名" style="width:10%"></el-table-column>
              <el-table-column prop="articleName" label="文章名称" style="width:10%"></el-table-column>
              <el-table-column prop="setTime" label="设定时间" style="width:10%"></el-table-column>
              <el-table-column prop="speed" label="速度" style="width:10%"></el-table-column>
              <el-table-column prop="rightRate" label="正确率" style="width:10%"></el-table-column>
             
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
     // activeName: "personalRankList",
      // 组内成绩
      groupData:[],
     
    };
  },
 // 页面初始化
 created(){
     // 获取队员成绩
   this.teamMemberResult();
 },
  methods: {
    // 查询战队组员成绩
    teamMemberResult(){
      var vm = this;
      // console.log(localStorage.getItem('teamName'))
      vm.$axios.get("/teamDetailResult/TeamDetailResult?teamName="+localStorage.getItem('teamName')).then(function(response) {
        if (response.data.code === "0000") {
            //   console.log(response.data)
            // console.log(response.data.data)
            vm.groupData = response.data.data;
        } else if (response.data.code === "1111") {
          vm.$message({
            message: response.data.message,
            type: "warning"
          });
        }
      });
    }
  },
};
</script>

