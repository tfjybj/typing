<!--
 * @Descripttion: 
 * @version: 
 * @Author: 冯佳兴
 * @Date: 2019-09-24 20:05:47
 * @LastEditors: 刘延强
 * @LastEditTime: 2021-09-19 21:23:01
 -->
<template>
  <div class="user_skills">
      <el-tabs class="tab">
        <div style="width: 100%">
          <el-table
            :data="pointsTableList"
            style="width: 100%"
            height="610"
            border
            :row-class-name="tableRowClassName"
            ref = "table"
            @row-click="Pause"
          >
            <el-table-column
              prop="ranking"
              label="排名"
              width="50"
            ></el-table-column>
           <el-table-column
                prop="teamName"
                label="小组"
                style="width: 10%"
              
                ></el-table-column
              >
            <el-table-column
              prop="teamGrade"
              label="积分"
              width="100"
            ></el-table-column>
            <el-table-column
              prop="userNames"
              label="小组成员"
              style="width: 20%"
              
            ></el-table-column>
              <el-table-column
              prop="promotion"
              label="晋级率"
              style="width: 20%"
            >
            </el-table-column>
          </el-table>
          <br />
        </div>
      </el-tabs>
    
  </div>
</template>

<script>
export default {
  props: {
    teamPointsData: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      pointsTableList: [], //存放列表数据
      nameList: [], //用来存放小组成员名数据
      list: [],
      scrollerTimer:"",//滚动的计时器
      flag:true,//true 可以滚动
    };
  },
  mounted() {
    this.selectTeamPointsData();
    this.scroller();
  },
  methods: {
    //首次点击排行榜表格 暂停滚动 
    //再次点击 开始自动滚动
    Pause(row){
      // console.log(row);
      if (this.flag) {
        this.flag = false;
        clearInterval(this.scrollerTimer);//停止滚动
      } else {
        this.scroller();
      }
    },
    //滚动
    scroller(){
      // 拿到表格挂载后的真实DOM
      const table = this.$refs.table
      // 拿到表格中承载数据的div元素
      const divData = table.bodyWrapper
      this.flag = true;
      // 拿到元素后，对元素进行定时增加距离顶部距离，实现滚动效果(此配置为每100毫秒移动1像素)
      this.scrollerTimer=setInterval(() => {
        // 元素自增距离顶部1像素
        divData.scrollTop +=20;
        // 判断元素是否滚动到底部(可视高度+距离顶部=整个高度)
        if (divData.clientHeight + divData.scrollTop == divData.scrollHeight) {
        // 重置table距离顶部距离
          divData.scrollTop = 0
        }
      }, 1000)
    },
    tableRowClassName({row, rowIndex}) {
      var str=row.userNames;
      if(str.indexOf(localStorage.getItem("username")) !=-1){
      // var returnTop=this.pointsTableList[rowIndex];
      // this.pointsTableList.splice(rowIndex,1)
      // this.pointsTableList.unshift(returnTop)//将新项添加到数组起始位置:
        return 'highlight-row';
      }
    },
    //接收父组件传来的值并渲染到列表中
    selectTeamPointsData() {
      // console.log(this.teamPointsData);
      var i = 0;
      var j = 0;
      var str = "";
      for (i = 0; i < this.teamPointsData.length; i++) {
        var str=this.teamPointsData[i].userNames;
        var teamPointsList = this.teamPointsData[i];
        this.pointsTableList.push(teamPointsList);
        if(str.indexOf(localStorage.getItem("username")) !=-1){
            this.pointsTableList.unshift(this.teamPointsData[i])//将新项添加到数组起始位置
        }
        var name = this.teamPointsData[i].userNames;
        this.nameList.push(name);
      }
    },
  },
};

</script>


<style scoped>
.user_skills /deep/  .el-table .highlight-row {
    background: #b713ed !important;
    font-size: 23px !important;
  }

.user_skills /deep/  .el-table, .el-table__expanded-cell {
    background-color: transparent;
}
.user_skills /deep/ .el-table th {
    background-color: transparent!important;
    color:#ffeb7b;
    text-align: center !important;
}
.user_skills /deep/ .el-table tr {
    background-color: transparent!important;
    color:#ffeb7b;
}
.user_skills /deep/ .el-table td {
  text-align: center !important;
}
.user_skills /deep/  .el-table--enable-row-transition .el-table__body td, .el-table .cell{
   background-color: transparent;
}
.el-table::before {
	 left: 0;
	 bottom: 0;
	 width: 100%;
	 height: 0px;
}
</style>
