<template>
    <el-scrollbar 
      class="el-scrollbar__wrap"
      :native="true">
    <div class="select">
      <el-select
        v-model="value"
        filterable
        multiple
        placeholder="请选择班级"
        @change="getitemId"
      >
        <el-option
          v-for="item in optionsType"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button
        type="primary"
        @click="exportExcel"
        style="margin-left: 28px"
        >导出</el-button
      >
    </div>
    <div class="main">
      <!-- 头部 -->
      <div class="screen-header">
        <div class="showtime">{{ nowDate }},{{ nowTime }}</div>
      </div>
      <!-- 主体部分 -->
      <section class="mainbox">
        <!-- 左侧部分 -->
        <div class="column">
          <!-- 小白键盘手—饼状图 -->
          <div class="panel bar">
            <h2>等级——小白键盘手</h2>
            <div class="chart">
              <beginner-pie
                v-bind:Table="Table"
                v-if="DestoryBeginnerPie == true"
              />
            </div>
            <div class="panel-footer"></div>
          </div>
          <!-- 初级键盘手—饼状图 -->
          <div class="panel line">
            <h2>等级——初级键盘手</h2>
            <div class="chart">
              <primary-pie
                v-bind:Table="Table"
                v-if="DestoryBeginnerPie == true"
              />
            </div>
            <div class="panel-footer"></div>
          </div>
          <!-- 柱状图-纵向 -->
          <div class="panel pie">
            <h2>团队进步前10名</h2>
            <div class="chart">
              <team-growth-bar
                v-bind:tableGrowthBar="tableGrowthBar"
                v-if="DestoryGrowthBar == true"
              />
            </div>
            <div class="panel-footer"></div>
          </div>
        </div>
        <!-- 中间部分 -->
        <div class="column">
          <div class="no">
            <div class="no-hd">
              <ul>
                <li>团队积分排行榜</li>
              </ul>
            </div>
          </div>
          <div class="map">
            <teamPointsList
              v-bind:teamPointsData="teamPointsData"
              id = teamPointsData
              v-if="DestoryTable == true"
            ></teamPointsList>
          </div>
        </div>
        <!-- 右侧部分 -->
        <div class="column">
          <!-- 高级键盘手—饼状图 -->
          <div class="panel bar1">
            <h2>等级——高级键盘手</h2>
            <div class="chart">
              <senior-pie
                v-bind:Table="Table"
                v-if="DestoryBeginnerPie == true"
              />
            </div>
            <div class="panel-footer"></div>
          </div>
          <!-- 键盘侠—饼状图 -->
          <div class="panel line1">
            <h2>等级——键盘侠</h2>
            <div class="chart">
              <key-man-pie
                v-bind:Table="Table"
                v-if="DestoryBeginnerPie == true"
              />
            </div>
            <div class="panel-footer"></div>
          </div>
          <!-- 柱状图——横向 -->
          <div class="panel pie1">
            <h2>当天晋级人次</h2>
            <div class="chart">
              <rise-bar
                v-bind:tableRiseBar="tableRiseBar"
                v-if="DestoryBar == true"
              />
            </div>
            <div class="panel-footer"></div>
          </div>
        </div>
      </section>
    </div>
    </el-scrollbar>
</template>

<script>
import riseBar from "./components/riseBar.vue";
import beginnerPie from "./components/beginnerPie.vue";
import primaryPie from "./components/primaryPie.vue";
import seniorPie from "./components/seniorPie.vue";
import keyManPie from "./components/keyManPie.vue";
import teamPointsList from "./components/teamPointsList.vue";
import teamGrowthBar from "./components/teamGrowthBar.vue";
import FileSaver from "file-saver";
import XLSX from "xlsx";

export default {
  components: {
    beginnerPie,
    primaryPie,
    seniorPie,
    keyManPie,
    riseBar,
    teamPointsList,
    teamGrowthBar,
  },
  data() {
    return {
      optionsType:[],
      className: [],
      value: [],
      label: "",
      nowDate: null, //存放年月日变量
      nowTime: null, //存放时分秒变量
      timer: "", //定义一个定时器的变量
      currentTime: new Date(), // 获取当前时间
      Table: [], //接收后端返回来的饼状图数据（4个）
      tableRiseBar: [], //接收各个班晋级人次数据（横向柱状图）
      DestoryBeginnerPie: false,
      DestoryBar: false,
      DestoryTable: false,
      DestoryGrowthBar: false,
      list: [], //获取的数据列表
      teamPointsData: [], //接收后端返回来的团队积分数据
      tableGrowthBar: [], //接收后端返回来的团队进步排名数据（竖状柱状图）
      scrollTimer:"",//滚屏计时器
      scrollY:20,//位移的距离
    };
  },
  //调用getTime函数，在实例创建前调用
  created() {
    this.timer = setInterval(this.getTime, 1000);
    this.Echarts_list();
    this.initRise();
    this.selectTeamPointData();
    this.initGrowthData();
    this.selectClass();
  },
  mounted() {
    this.selectShowClassName();
    this.DestoryBeginnerPie = false;
    this.DestoryBar = false;
    this.DestoryTable = false;
    this.DestoryGrowthBar = false;
    this.Echarts_list(); //调用后端接口返回数据，饼状图
    this.initRise(); //调用后端接口返回数据，横向柱状图
    this.selectTeamPointData(); //调用后端接口返回数据，列表
    this.initGrowthData();

    // this.scrollTimer = setInterval(() => {  
    //      this.scrollWindow(this.scrollY);
    //      this.scrollY +=20;
    //   }, 2000)//每2秒往下滚屏
  },
  methods: {
    //导出团队排行榜数据
    exportExcel() {
      if(this.teamPointsData == ""){
        this.$message({
          message: "当前内容为空，不能导出",
          type: "warning",
        });
      }else{
        this.exportTeamExcel(); 
      }
      
    },
    exportTeamExcel(){
      /* generate workbook object from table */
      var xlsxParam = { raw: true }; // 导出的内容只做解析，不进行格式转换
      var wb = XLSX.utils.table_to_book(
        document.querySelector("#teamPointsData"),
        xlsxParam
      );

      /* get binary string as output */
      var wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array",
      });
      try {
        FileSaver.saveAs(
          new Blob([wbout], { type: "application/octet-stream" }),
          this.value + "团队积分排行榜.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") {
          // console.log(e, wbout);
        }
      }
      return wbout;
    },
    //自动滚屏
    // scrollWindow(scrollY){
    //   // console.log(scrollY);
    //   if(scrollY >= 380){
    //     window.scrollTo(0,0)
    //     this.scrollY=0
    //   }else{
    //     window.scrollTo(0,scrollY);
    //   }
    // },
    load() {},
    //查询教师端设置展示的班级
    selectShowClassName(){
      this.$axios
        .get("/personTeam/selectClassName")
        .then((res) => {
          if (res.data.code == "0000") {
            this.optionsType=[];
            var i = 0;
            for (i = 0; i < res.data.data.length; i++) {
              var o =new Object();
              o.value = res.data.data[i].className;
              o.label = res.data.data[i].className;
              this.optionsType.push(o);
            }
          }
        });
    },

    getitemId() {
      
      //竖状图
      this.$axios
        .post("/teamDetailResult/selectTeamGrowthChart", this.value)
        .then((res) => {
          var i = 0;
          if (res.data.code == "0000") {
            this.tableGrowthBar =[];//先清空数据
          this.DestoryGrowthBar = false;//先清除 再渲染

            for (i = 0; i < res.data.data.length; i++) {
              var table1Bar = res.data.data[i];
              this.tableGrowthBar.push(table1Bar);
              this.$nextTick(() => {
                this.DestoryGrowthBar = true;
              });
            }
          }
        });

      //四个饼状图
      this.$axios
        .post("/personResult/selectClassLevelCount", this.value)
        .then((res) => {
          this.Table = []; //先清空数据
          this.DestoryBeginnerPie = false; //先清除 再渲染
          var i = 0;
          if (res.data.code == "0000") {
            for (i = 0; i < res.data.data.length; i++) {
              var tableData = res.data.data[i];
              this.Table.push(tableData);

              this.$nextTick(() => {
                this.DestoryBeginnerPie = true;
              });
            }
          }
        });
      //中间团队积分排行榜
      this.$axios
        .post("/personResult/selectTeamRanking", this.value)
        .then((res) => {
          var i = 0;
          if (res.data.code === "0000") {
            this.teamPointsData = []; //先清空数据
            this.DestoryTable = false; //先清除 再渲染
            for (i = 0; i < res.data.data.length; i++) {
              var tableData = res.data.data[i];
              this.teamPointsData.push(tableData);
             
                this.teamPointsData[i].promotion=(this.teamPointsData[i].promotion* 100).toFixed(0)+"%";
              this.$nextTick(() => {
                this.DestoryTable = true;
              });
            }
          } else if (res.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        });
    },
    //获取当前系统时间，并实时刷新，和系统保持一致
    getTime() {
      const date = new Date();
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();
      const hour = date.getHours();
      const minute = date.getMinutes();
      const second = date.getSeconds();
      const str = "";
      if (this.hour > 12) {
        this.hour -= 12;
        this.str = " PM";
      } else {
        this.str = " AM";
      }
      this.month = check(month);
      this.day = check(day);
      this.hour = check(hour);
      this.minute = check(minute);
      this.second = check(second);
      function check(i) {
        const num = i < 10 ? "0" + i : i;
        return num;
      }
      this.nowDate = year + "年" + this.month + "月" + this.day + "日";
      this.nowTime =
        this.hour + ":" + this.minute + ":" + this.second + this.str;
    },
    //发送请求，返回数据（4个饼状图）
    Echarts_list() {
      this.$axios
        .post("/personResult/selectClassLevelCount", this.value)
        .then((res) => {
          var i = 0;
          if (res.data.code == "0000") {
            for (i = 0; i < res.data.data.length; i++) {
              var tableData = res.data.data[i];
              this.Table.push(tableData);
              this.$nextTick(() => {
                this.DestoryBeginnerPie = true;
              });
            }
          }
        });
    },
    //发送请求，返回数据（横向柱状图）
    initRise() {
      this.$axios.get("/personResult/selectRiseCount").then((res) => {
        var i = 0;
        if (res.data.code == "0000") {
          if (res.data.data != undefined) {
            for (i = 0; i < res.data.data.length; i++) {
              var tableBar = res.data.data[i];
              this.tableRiseBar.push(tableBar);
              this.$nextTick(() => {
                this.DestoryBar = true;
              });
            }
          }
        }
      });
    },
    //发送请求，返回数据（竖状柱状图）
    initGrowthData() {
      this.$axios
        .post("/teamDetailResult/selectTeamGrowthChart", this.value)
        .then((res) => {
          var i = 0;
          if (res.data.code == "0000") {
            for (i = 0; i < res.data.data.length; i++) {
              var table1Bar = res.data.data[i];
              this.tableGrowthBar.push(table1Bar);
              this.$nextTick(() => {
                this.DestoryGrowthBar = true;
              });
            }
          }
        });
    },
    //发送请求，查询团队积分排名
    selectTeamPointData() {
      this.$axios
        .post("/personResult/selectTeamRanking", this.value)
        .then((res) => {
          var i = 0;
          if (res.data.code === "0000") {
            this.teamPointsData=[]
            for (i = 0; i < res.data.data.length; i++) {
              
              var tableData = res.data.data[i];
              this.teamPointsData.push(tableData);
              let data=(this.teamPointsData[i].promotion*100)+'%'
            
              this.teamPointsData[i].promotion=data
              this.$nextTick(() => {
                this.DestoryTable = true;
              });
            }
          } else if (res.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        });
    },
    // time1() {
    //   return setTimeout(() => {
    //     this.Echarts_list();
    //     this.initRise();
    //     this.selectTeamPointData();
    //     this.initGrowthData();
    //   }, 5000);
    // },
    selectClass() {
      this.$axios
        .get(
          "/personResult/selectClass?userCode=" +
            localStorage.getItem("usercode")
        )
        .then((res) => {
          if (res.data.code == "0000") {
            localStorage.setItem("className", res.data.data.className);
          }
        });
    },
  },
  watch: {
    list() {
      this.timer1();
    },
  },
  destroyed() {
    clearTimeout(this.timer1);
  },
  //离开页面使用beforeDestroy() 销毁
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer); // 在Vue实例销毁前，清除定时器
    }
    // if(this.scrollTimer){
    //   clearInterval(this.scrollTimer); 
    // }
  },
};
</script>

<style lang="less" scoped>
.el-scrollbar__wrap { 
  overflow-x: hidden;
  // overflow: scroll; 
  width: 100%; 
  } 
.select {
  margin-bottom: 8px;
}
.main {
  width: 100%;
  height: 100%;
  padding: 0 10px;
  max-width: 1920px;
  min-width: 1180px;
  background: url(../../assets/img/board-body.jpg) no-repeat;
  color: #fff;
  box-sizing: border-box;
  position: relative;
}
.screen-header {
  width: 100%;
  height: 60px;
  font-size: 60px;
  background: url(../../assets/img/board-header.png) no-repeat;
  background-size: 100% 100%;
  position: relative;

  .title {
    position: absolute;
    left: 50%;
    top: 50%;
    font-size: 25px;
    text-align: center;
    transform: translate(-50%, -50%);
  }
  .showtime {
    position: absolute;
    top: 0;
    right: 30px;
    line-height: 75px;
    font-size: 20px;
    color: rgba(255, 255, 255, 0.8);
  }
  .datetime {
    font-size: 15px;
    margin-left: 10px;
  }
}
.mainbox {
  min-width: 1024px;
  max-width: 1920px;
  padding: 0.125rem 0.125rem 0;
  display: flex;
  .column {
    flex: 3;
    &:nth-child(2) {
      flex: 5;
      margin: 0 0.125rem 0.1875rem;
      overflow: hidden;
    }
  }
}
.panel {
  position: relative;
  height: 3.875rem;
  border: 1px solid rgba(25, 186, 139, 0.17);
  padding: 0 0.1875rem 0.5rem;
  margin-bottom: 0.1875rem;
  &::before {
    position: absolute;
    top: 0;
    left: 0;
    content: "";
    width: 10px;
    height: 10px;
    border-top: 2px solid #02a6b5;
    border-left: 2px solid #02a6b5;
  }
  &::after {
    position: absolute;
    top: 0;
    right: 0;
    content: "";
    width: 10px;
    height: 10px;
    border-top: 2px solid #02a6b5;
    border-right: 2px solid #02a6b5;
  }
  .panel-footer {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    &::before {
      position: absolute;
      bottom: 0;
      left: 0;
      content: "";
      width: 10px;
      height: 10px;
      border-bottom: 2px solid #02a6b5;
      border-left: 2px solid #02a6b5;
    }
    &::after {
      position: absolute;
      bottom: 0;
      right: 0;
      content: "";
      width: 10px;
      height: 10px;
      border-bottom: 2px solid #02a6b5;
      border-right: 2px solid #02a6b5;
    }
  }
  h2 {
    height: 0.6rem;
    line-height: 0.6rem;
    text-align: center;
    color: #fff;
    font-size: 0.25rem;
    font-weight: 400;
    a {
      margin: 0 0.1875rem;
      color: #fff;
      text-decoration: underline;
    }
  }
  .chart {
    height: 3rem;
  }
}
.no {
  background: rgba(101, 132, 226, 0.1);
  padding: 0.1875rem;
  .no-hd {
    position: relative;
    border: 1px solid rgba(25, 186, 139, 0.17);
    &::before {
      content: "";
      position: absolute;
      width: 30px;
      height: 10px;
      border-top: 2px solid #02a6b5;
      border-left: 2px solid #02a6b5;
      top: 0;
      left: 0;
    }
    &::after {
      content: "";
      position: absolute;
      width: 30px;
      height: 10px;
      border-bottom: 2px solid #02a6b5;
      border-right: 2px solid #02a6b5;
      right: 0;
      bottom: 0;
    }
    ul {
      display: flex;
      li {
        position: relative;
        flex: 1;
        text-align: center;
        height: 1rem;
        line-height: 1rem;
        font-size: 0.875rem;
        color: #ffeb7b;
        padding: 0.05rem 0;
        font-family: electronicFont;
        font-weight: bold;
        &:first-child::after {
          content: "";
          position: absolute;
          height: 50%;
          width: 1px;
          background: rgba(255, 255, 255, 0.2);
          right: 0;
          top: 25%;
        }
      }
    }
  }
  .no-bd ul {
    display: flex;
    li {
      flex: 1;
      height: 0.5rem;
      line-height: 0.5rem;
      text-align: center;
      font-size: 0.225rem;
      color: rgba(255, 255, 255, 0.7);
      padding-top: 0.125rem;
    }
  }
}
.map {
  position: relative;
  height: 10.125rem;
  .chart {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 5;
    height: 10.125rem;
    width: 100%;
  }
}
</style>