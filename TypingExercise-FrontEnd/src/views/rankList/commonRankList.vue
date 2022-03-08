<!--
 * @Descripttion: 
 * @version: 
 * @Author: 冯佳兴
 * @Date: 2019-09-24 20:05:47
 * @LastEditors: 
 * @LastEditTime: 
 -->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <el-tabs v-model="activeName" @tab-click="handleClickTable">
  <!-- 查询进步英雄榜的数据 -->
        <el-tab-pane label="进步英雄排行榜" name="battleTeamRankList1">  
          <div style="width: 100%">
            <div class="center">   
              <!-- 选择级别 -->
              <el-select
                v-model="selectMenu1"
                style="margin-right: 30px"
                placeholder="小组"
                @change="selectClassRankTableData(1, 10)"
              >
                <el-option
                  v-for="item in option"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
            <el-table
              :data="arrayImproved"
              style="width: 100%"
              @row-dblclick="gotoPersonResultChart"
              :default-sort="{ prop: 'date', order: 'descending' }"
            >
              <el-table-column
                prop="id"
                label="名次"
                style="width: 10%"
                :formatter="formatter"
              ></el-table-column>
              <el-table-column
                prop="userName"
                label="姓名"
                style="width: 10%"
              ></el-table-column>
              <el-table-column
                prop="ratingName"
                label="等级"
                style="width: 10%"
              ></el-table-column>
              <el-table-column label="徽章" style="width: 10%">
              <template slot-scope="scope">
                <el-popover placement="top-start" title="" trigger="hover">
                  <img :src="scope.row.icon" alt="" style="width: 150px;height: 150px">
                  <img slot="reference" :src="scope.row.icon" style="width: 30px;height: 30px">
                </el-popover>
              </template>
              </el-table-column>
              <el-table-column
                prop="teamId"
                label="小组"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="articleName"
                label="文章名称"
                style="width: 10%"
              ></el-table-column>
              <!-- <el-table-column prop="progressTime" label="设定时间" style="width:10%"></el-table-column> -->
              <el-table-column
                prop="speed"
                label="速度"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="rightRate"
                label="正确率"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="score"
                label="进步分数"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="grade"
                label="进步率(%)"
                style="width: 10%"
                sortable
                :sort-method="
                  (a, b) => {
                    return a.grade - b.grade;
                  }
                "
                ><template slot-scope="scope">
                  {{ fun(scope.row.grade) }}
                </template></el-table-column
              >
              <!-- <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                  <el-button @dblclick="gotoPersonResultChart(scope.row)" type="text" size="small">查看</el-button>
                </template>
              </el-table-column> -->
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
              v-if="total != 0"
            ></el-pagination>
          </div>
        </el-tab-pane>
<!-- 查询英雄榜排名 -->
        <el-tab-pane label="英雄排行榜" name="battleTeamRankList2">
          <div style="width: 100%">
            <div class="center">
              <!-- 选择级别 -->
              <el-select
                v-model="selectMenu"
                style="margin-right: 30px"
                placeholder="小组"
                @change="selectClassUpRankTableData(1, 10)"
              >
                <el-option
                  v-for="item in option"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <!-- 查询 -->
              <!-- <el-button
                type="primary"
                @click="selectClassUpRankTableData(1, 10)"
                >查询</el-button
              > -->
            </div>
            <el-table
              :data="arrayRank"
              style="width: 100%"
              :default-sort="{ prop: 'date', order: 'descending' }"
            >
              <el-table-column
                prop="id"
                label="名次"
                style="width: 10%"
                :formatter="formatter"
              ></el-table-column>
              <el-table-column
                prop="userName"
                label="姓名"
                style="width: 10%"
              ></el-table-column>
              <el-table-column
                prop="ratingName"
                label="等级"
                style="width: 10%"
              ></el-table-column>
              <el-table-column label="徽章" style="width: 10%">
              <template slot-scope="scope">
                <el-popover placement="top-start" title="" trigger="hover">
                  <img :src="scope.row.icon" alt="" style="width: 150px;height: 150px">
                  <img slot="reference" :src="scope.row.icon" style="width: 30px;height: 30px">
                </el-popover>
              </template>
              </el-table-column>
              <!-- <el-table-column prop="upRank" label="进步名次" style="width:10%"></el-table-column> -->
              <el-table-column
                prop="teamId"
                label="小组"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="articleName"
                label="文章名称"
                style="width: 10%"
              ></el-table-column>

              <el-table-column
                prop="speed"
                label="速度"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="rightRate"
                label="正确率"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="score"
                label="得分"
                style="width: 10%"
                sortable
              ></el-table-column>
            </el-table>

            <br />
            <el-pagination
              @size-change="handleSizeChangeUp"
              @current-change="handleCurrentChangeUp"
              :current-page="currentPageUp"
              :page-sizes="[10, 20, 40]"
              :page-size="10"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              v-if="total != 0"
            ></el-pagination>
          </div>
        </el-tab-pane>
<!-- 时间排行榜 -->
        <el-tab-pane label="时间排行榜" name="battleTeamRankList3">
          <div style="width: 100%">
            <!-- 时间控件 -->
            <div class="center">
              <el-date-picker
                style="margin-right: 30px"
                v-model="timeValue"
                type="datetimerange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions"
                @change="query()"
              ></el-date-picker>

              <!-- 选择级别 -->
              <el-select
                v-model="selectValue"
                style="margin-right: 30px"
                placeholder="小白键盘手"
                @change="query()"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <!-- 查询 -->
              <!-- <el-button type="primary" @click="query()">查询</el-button> -->
            </div>
            
            <el-table :data="arrayTime" style="width: 100%" height="500" >
           
              <el-table-column
                prop="time"
                label="时间"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="ranking"
                label="排名"
                style="width: 10%"
              ></el-table-column>
              <el-table-column prop="userName" label="姓名" style="width: 10%">
              </el-table-column>
              <el-table-column prop="level" label="等级" style="width: 10%">
              </el-table-column>
              <el-table-column label="徽章" style="width: 10%">
              <template slot-scope="scope">
                <el-popover placement="top-start" title="" trigger="hover">
                  <img :src="scope.row.icon" alt="" style="width: 150px;height: 150px">
                  <img slot="reference" :src="scope.row.icon" style="width: 30px;height: 30px">
                </el-popover>
              </template>
              </el-table-column>
              <el-table-column
                prop="teamId"
                label="小组"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="integral"
                label="积分"
                style="width: 10%"
                sortable
              ></el-table-column>
            </el-table>
            <br />
          </div>
       共{{total2}}条数据
        </el-tab-pane>
<!-- 晋级榜单数据 -->
        <el-tab-pane label="晋级榜" name="battleTeamRankList4">
          <div style="width: 100%">        
            <el-table
              :data="allRankTableData"
              id="allRankTableData"
              style="width: 100%"
              height="500"
            >
              <el-table-column
                prop="ranking"
                label="排名"
                style="width: 10%"
              ></el-table-column>
              <el-table-column
                prop="userName"
                label="姓名"
                style="width: 10%"
              ></el-table-column>
              <el-table-column
                prop="level"
                label="等级"
                style="width: 10%"
              ></el-table-column>
              <el-table-column
                prop="teamName"
                label="小组"
                style="width: 10%"
                sortable
              ></el-table-column>
               <el-table-column
                prop="className"
                label="班级"
                style="width: 10%"
              ></el-table-column>
            </el-table>
            <br />            
          </div>
          共{{total4}}条数据
        </el-tab-pane>
      </el-tabs>

      <el-backtop >
       <img src="../../assets/img/backTop.png"
          style="width: 60%; height: 60%"
        />
      </el-backtop>

    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      total2:0,
      total4:"0",
      icon:["../../assets/img/copper.png",
      "../../assets/img/silver.png",
      "../../assets/img/gold.png",
      "../../assets/img/masonry.png"],
      // level: {},
      className: "",
      activeName: "battleTeamRankList1",
      // 个人成绩
      personTableData: [],
      allRankTableData:[],
      // // 战队成绩
      // teamTableData: [],
      fn1: "",
      // 总条数
      total: 0,
      pagesize: 10,
      pagesizeUp: 10,
      userid: "",
      currentPage: 1, //当前页默认值是1
      currentPageUp: 1,
      rankTableData: [], //英雄排行榜表单数据
      upRankTableData: [], //进步排行榜表单数据
      multipleTable: [], //存放选中值的数组
      //级别选择
      selectValue: "全部", //默认选择全部
      options: [
        {
          value: "小白键盘手",
        },
        {
          value: "初级键盘手",
        },
        {
          value: "高级键盘手",
        },
        {
          value: "键盘侠",
        },
        {
          value: "全部",
        },
      ],
      //级别选择
      selectMenu: "班级", //默认选择全部
      option: [
        {
          value: "小组",
        },
        {
          value: "班级",
        },
      ],
      //查询级别英雄榜
      selectMenu1:"班级",
      option:[
        {
          value: "小组",
        },
        {
          value: "班级",
        },
      ],

      //点击时间控件显示时间
      pickerOptions: {
        shortcuts: [
          {
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
      timeValue: [], //时间控件值
      //开始时间
      startTime: "",
      //结束时间
      endTime: "",
      //接收后端返回来的根据时间查询的表格数据
      timeTableData: [],
      arrayTime:[],//时间排行榜
      arrayRank:[],//英雄排行榜
      arrayImproved:[],//进步排行榜
    };
  },
  created() {
    this.selectTeam();
    // 获取个人成绩
    var vm = this;
    this.handleUserid();
    //获取进步排名
    vm.selectUpRankTableData(vm.currentPage, vm.pagesize);
    //获取英雄榜排名
    vm.selectRankTableData(vm.currentPageUp, vm.pagesizeUp);
    //获取晋级榜单
    vm.queryRank();
   //获取默认时间
    let nowDate = new Date();
    let year = nowDate.getFullYear();
    let month = nowDate.getMonth() + 1;
    let day = nowDate.getDate();
    let endTime = `${year}-${month}-${day}`;
    this.nowTime = endTime; // 当前的时间点
    let befDate = new Date(nowDate.getTime() - 0 * 24 * 3600 * 1000);
    let byear = befDate.getFullYear();
    let bmonth = befDate.getMonth() + 1;
    let bday = befDate.getDate();
    let startTime = `${byear}-${bmonth}-${bday}`;

    // 默认日期赋值
    this.timeValue = [
      new Date(byear + ", " + bmonth + ", " + bday),
      new Date(),
    ];
    //获取时间排行榜数据
    vm.query();
  },

  methods: {
    //进步率截取两位小数
    fun(val) {
      return Number(val).toFixed(2);
    },
    formatter(row, column) {
      return row.id;
    },
    selectTeam() {
      this.$axios
        .get(
          "/personResult/selectClass?userCode=" +
            localStorage.getItem("usercode")
        )
        .then((res) => {
          if (res.data.code == "0000") {
            localStorage.setItem("className", res.data.data.className);
            localStorage.setItem("teamId", res.data.data.teamId);
          }
        });
    },
    handleClickTable(tab, event) {
      if (tab.name == "battleTeamRankList1") {
         
        this.selectUpRankTableData(1, 10); //查询进步排行榜表单数据
   
      } else if (tab.name == "battleTeamRankList2") {
           this.selectRankTableData(1, 10); //查询英雄排行榜表单数据
      } else if(tab.name == "battleTeamRankList3") {
        
        this.query();//查询时间排行榜数据
      }  else if(tab.name == "battleTeamRankList4"){
        this.queryRank();}
    },
    handleSizeChange(val) {
      var vm = this;
      vm.pagesize = val;
      vm.selectUpRankTableData(vm.currentPage, val);
    },
    handleSizeChangeUp(val) {
      var vm = this;
      vm.pagesizeUp = val;
      vm.selectRankTableData(vm.currentPageUp, val);
    },
    handleCurrentChange(val) {
      var vm = this;
      this.currentPage = val;
      vm.selectUpRankTableData(val, vm.pagesize);
    },
    handleCurrentChangeUp(val) {
      var vm = this;
      this.currentPageUp = val;
      vm.selectRankTableData(val, vm.pagesizeUp);
    },
    handleUserid(val) {
      var vm = this;
      vm.userid = localStorage.getItem("userid");
    },
    handleClick(row) {
      localStorage.setItem("teamName", row.teamName);
      // 跳转到组员成绩界面
      this.$router.push("/inBattleTeamRankList");
    },
    //根据时间查询各个阶段的记录
    query() {
      if (this.timeValue.length == "0") {
        this.$message({
          message: "请选择日期和时间",
          type: "warning",
        });
      } else {
        var vm = this;
        var s = new Date(this.timeValue[0]);
        var e = new Date(this.timeValue[1]);
        this.startTime =
          s.getFullYear() +
          "-" +
          (s.getMonth() + 1) +
          "-" +
          s.getDate() +
          " " +
          s.getHours() +
          ":" +
          s.getMinutes() +
          ":" +
          s.getSeconds();
        this.endTime =
          e.getFullYear() +
          "-" +
          (e.getMonth() + 1) +
          "-" +
          e.getDate() +
          " " +
          e.getHours() +
          ":" +
          e.getMinutes() +
          ":" +
          e.getSeconds();
        vm.selectTime(this.startTime, this.endTime, this.selectValue);
      }
    },

    // 查询英雄榜排名
    selectRankTableData(page, size) {
      var vm = this;
      let className = localStorage.getItem("className");

      vm.$axios
        .get(
          "/personResult/selectPersonClassProgress/" +
            page +
            "/" +
            size +
            "/" +
            className
        )
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.total = response.data.data[1];
            vm.upRankTableData = response.data.data[0];
            vm.arrayRank=[];
            for (let i = 0; i < vm.upRankTableData.length; i++) {
                var object =new Object();
                object.id=vm.upRankTableData[i].id;
                object.time=vm.upRankTableData[i].time;
                object.teamId=vm.upRankTableData[i].teamId;
                object.userName=vm.upRankTableData[i].userName;
                object.speed=vm.upRankTableData[i].speed;
                object.articleName=vm.upRankTableData[i].articleName;
                object.ratingName=vm.upRankTableData[i].ratingName;
                object.score=vm.upRankTableData[i].score;
                object.rightRate=vm.upRankTableData[i].rightRate;
                if(vm.upRankTableData[i].ratingName=="小白键盘手"){
                  object.icon=require("../../assets/img/copper.png")
                }else if (vm.upRankTableData[i].ratingName=="初级键盘手"){
                  object.icon=require("../../assets/img/silver.png")
                }else if (vm.upRankTableData[i].ratingName=="高级键盘手"){
                  object.icon=require("../../assets/img/gold.png")
                }else if (vm.upRankTableData[i].ratingName=="键盘侠"){
                  object.icon=require("../../assets/img/masonry.png")
                }
                vm.arrayRank.push(object);
              }
          } else if (response.data.code === "1111") {
            vm.$$message({
              message: "暂无数据",
              type: "warning",
            });
            vm.upRankTableData = "";
          }
        });
    },

    //查询进步英雄排行榜---张艳伟-2021年9月19日
    selectClassRankTableData(page, size) {
      //根据班级查询
      if (this.selectMenu1 == "班级") {
        var vm = this;

        let classname = localStorage.getItem("className");
        // console.log(classname);
        vm.$axios
          .get(
            "/personResult/selectPersonClassRanking/" +
              page +
              "/" +
              size +
              "/" +
              classname
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              // console.log(response.data.data[0]);
              vm.total = response.data.data[1];
              vm.rankTableData = response.data.data[0];
              vm.arrayImproved=[];
              for (let i = 0; i < vm.rankTableData.length; i++) {
                var object =new Object();
                object.id=vm.rankTableData[i].id;
                object.time=vm.rankTableData[i].time;
                object.teamId=vm.rankTableData[i].teamId;
                object.userName=vm.rankTableData[i].userName;
                object.speed=vm.rankTableData[i].speed;
                object.articleName=vm.rankTableData[i].articleName;
                object.ratingName=vm.rankTableData[i].ratingName;
                object.score=vm.rankTableData[i].score;
                object.rightRate=vm.rankTableData[i].rightRate;
                object.grade=vm.rankTableData[i].grade;
                if(vm.rankTableData[i].ratingName=="小白键盘手"){
                  object.icon=require("../../assets/img/copper.png")
                }else if (vm.rankTableData[i].ratingName=="初级键盘手"){
                  object.icon=require("../../assets/img/silver.png")
                }else if (vm.rankTableData[i].ratingName=="高级键盘手"){
                  object.icon=require("../../assets/img/gold.png")
                }else if (vm.rankTableData[i].ratingName=="键盘侠"){
                  object.icon=require("../../assets/img/masonry.png")
                }
                vm.arrayImproved.push(object);
              }

            } else if (response.data.code === "1111") {
              vm.$$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.rankTableData = "";
            }
          });
      }
      if (this.selectMenu1 == "小组") {
        //查询小组排行
        var vm = this;

        let teamId = localStorage.getItem("teamId");
        let className = localStorage.getItem("className");
        // let className="21级生物科学类本科1班"
        vm.$axios
          .get(
            "/personResult/selectPersonTeamRanking/" +
              page +
              "/" +
              size +
              "/" +
              teamId +
              "/" +
              className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              // console.log(response.data.data[0]);
              vm.total = response.data.data[1];
              vm.rankTableData = response.data.data[0];
              vm.arrayImproved=[];
              for (let i = 0; i < vm.rankTableData.length; i++) {
                var object =new Object();
                object.id=vm.rankTableData[i].id;
                object.time=vm.rankTableData[i].time;
                object.teamId=vm.rankTableData[i].teamId;
                object.userName=vm.rankTableData[i].userName;
                object.speed=vm.rankTableData[i].speed;
                object.articleName=vm.rankTableData[i].articleName;
                object.ratingName=vm.rankTableData[i].ratingName;
                object.score=vm.rankTableData[i].score;
                object.rightRate=vm.rankTableData[i].rightRate;
                object.grade=vm.rankTableData[i].grade;
                if(vm.rankTableData[i].ratingName=="小白键盘手"){
                  object.icon=require("../../assets/img/copper.png")
                }else if (vm.rankTableData[i].ratingName=="初级键盘手"){
                  object.icon=require("../../assets/img/silver.png")
                }else if (vm.rankTableData[i].ratingName=="高级键盘手"){
                  object.icon=require("../../assets/img/gold.png")
                }else if (vm.rankTableData[i].ratingName=="键盘侠"){
                  object.icon=require("../../assets/img/masonry.png")
                }
                vm.arrayImproved.push(object);
              }
            } else if (response.data.code === "1111") {
              vm.$$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.rankTableData = "";
            }
          });
      }
    },
    //查询英雄排行榜-张艳伟-2021年9月21日
    selectClassUpRankTableData(page, size) {
      if (this.selectMenu == "班级") {
        var vm = this;
        let className = localStorage.getItem("className");
        vm.$axios
          .get(
            "/personResult/selectPersonClassProgress/" +
              page +
              "/" +
              size +
              "/" +
              className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total = response.data.data[1];
              vm.upRankTableData = response.data.data[0];
              vm.arrayRank=[];
              for (let i = 0; i < vm.upRankTableData.length; i++) {
                var object =new Object();
                object.id=vm.upRankTableData[i].id;
                object.time=vm.upRankTableData[i].time;
                object.teamId=vm.upRankTableData[i].teamId;
                object.userName=vm.upRankTableData[i].userName;
                object.speed=vm.upRankTableData[i].speed;
                object.articleName=vm.upRankTableData[i].articleName;
                object.ratingName=vm.upRankTableData[i].ratingName;
                object.score=vm.upRankTableData[i].score;
                object.rightRate=vm.upRankTableData[i].rightRate;
                if(vm.upRankTableData[i].ratingName=="小白键盘手"){
                  object.icon=require("../../assets/img/copper.png")
                }else if (vm.upRankTableData[i].ratingName=="初级键盘手"){
                  object.icon=require("../../assets/img/silver.png")
                }else if (vm.upRankTableData[i].ratingName=="高级键盘手"){
                  object.icon=require("../../assets/img/gold.png")
                }else if (vm.upRankTableData[i].ratingName=="键盘侠"){
                  object.icon=require("../../assets/img/masonry.png")
                }
                vm.arrayRank.push(object);
              }
            } else if (response.data.code === "1111") {
              vm.$$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.upRankTableData = "";
            }
          });
      }
      if (this.selectMenu == "小组") {
        //查询小组进步排行
        var vm = this;

        let teamId = localStorage.getItem("teamId");
        let className = localStorage.getItem("className");
        vm.$axios
          .get(
            "/personResult/selectPersonTeamProgress/" +
              page +
              "/" +
              size +
              "/" +
              teamId +
              "/" +
              className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              // console.log(response.data.data[0]);
              vm.total = response.data.data[1];
              vm.upRankTableData = response.data.data[0];
              vm.arrayRank=[];
            for (let i = 0; i < vm.upRankTableData.length; i++) {
                var object =new Object();
                object.id=vm.upRankTableData[i].id;
                object.time=vm.upRankTableData[i].time;
                object.teamId=vm.upRankTableData[i].teamId;
                object.userName=vm.upRankTableData[i].userName;
                object.speed=vm.upRankTableData[i].speed;
                object.articleName=vm.upRankTableData[i].articleName;
                object.ratingName=vm.upRankTableData[i].ratingName;
                object.score=vm.upRankTableData[i].score;
                object.rightRate=vm.upRankTableData[i].rightRate;
                if(vm.upRankTableData[i].ratingName=="小白键盘手"){
                  object.icon=require("../../assets/img/copper.png")
                }else if (vm.upRankTableData[i].ratingName=="初级键盘手"){
                  object.icon=require("../../assets/img/silver.png")
                }else if (vm.upRankTableData[i].ratingName=="高级键盘手"){
                  object.icon=require("../../assets/img/gold.png")
                }else if (vm.upRankTableData[i].ratingName=="键盘侠"){
                  object.icon=require("../../assets/img/masonry.png")
                }
                vm.arrayRank.push(object);
              }
              // console.log(vm.upRankTableData);
            } else if (response.data.code === "1111") {
              vm.$$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.upRankTableData = "";
            }
          });
      }
    },
    // 查询进步排名---切换页面的时候调用该方法
    selectUpRankTableData(page, size) {
      var vm = this;
      let classname = localStorage.getItem("className");
      vm.$axios
        .get(
          "/personResult/selectPersonClassRanking/" +
            page +
            "/" +
            size +
            "/" +
            classname
        )
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.total = response.data.data[1];
            vm.rankTableData = response.data.data[0];
            vm.arrayImproved=[];
           for (let i = 0; i < vm.rankTableData.length; i++) {
                var object =new Object();
                object.id=vm.rankTableData[i].id;
                object.time=vm.rankTableData[i].time;
                object.teamId=vm.rankTableData[i].teamId;
                object.userName=vm.rankTableData[i].userName;
                object.speed=vm.rankTableData[i].speed;
                object.articleName=vm.rankTableData[i].articleName;
                object.ratingName=vm.rankTableData[i].ratingName;
                object.score=vm.rankTableData[i].score;
                object.rightRate=vm.rankTableData[i].rightRate;
                object.grade=vm.rankTableData[i].grade;
                if(vm.rankTableData[i].ratingName=="小白键盘手"){
                  object.icon=require("../../assets/img/copper.png")
                }else if (vm.rankTableData[i].ratingName=="初级键盘手"){
                  object.icon=require("../../assets/img/silver.png")
                }else if (vm.rankTableData[i].ratingName=="高级键盘手"){
                  object.icon=require("../../assets/img/gold.png")
                }else if (vm.rankTableData[i].ratingName=="键盘侠"){
                  object.icon=require("../../assets/img/masonry.png")
                }
                vm.arrayImproved.push(object);
              }
          } else if (response.data.code === "1111") {
            vm.$$message({
              message: "暂无数据",
              type: "warning",
            });
            vm.rankTableData = "";
          }
        });
    },
    //根据时间查询各个阶段的人名单
    selectTime(startTime, endTime, selectContent) {
      if (this.selectValue == "全部") {
        //根据班级查询全部时间段的人名单
        var vm = this;
        let className = localStorage.getItem("className");
        vm.$axios
          .get(
            "/personResult/selectRankingByTimeLevelAll/" +
              startTime +
              "/" +
              endTime +
              "/" +
              className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total2= response.data.data.length;
              vm.arrayTime=[];
              vm.timeTableData = response.data.data;
              for (let i = 0; i < vm.timeTableData.length; i++) {
                var object =new Object();
                object.time=vm.timeTableData[i].time;
                object.ranking=vm.timeTableData[i].ranking;
                object.userName=vm.timeTableData[i].userName;
                object.teamId=vm.timeTableData[i].teamId;
                object.integral=vm.timeTableData[i].integral;
                object.level=vm.timeTableData[i].level;

                if(vm.timeTableData[i].level=="小白键盘手"){
                  object.icon=require("../../assets/img/copper.png")
                }else if (vm.timeTableData[i].level=="初级键盘手"){
                  object.icon=require("../../assets/img/silver.png")
                }else if (vm.timeTableData[i].level=="高级键盘手"){
                  object.icon=require("../../assets/img/gold.png")
                }else if (vm.timeTableData[i].level=="键盘侠"){
                  object.icon=require("../../assets/img/masonry.png")
                }
                vm.arrayTime.push(object);
              }
            } else if (response.data.code === "1111") {
              vm.timeTableData = "";
              vm.total2="0";
            }
          });
      } else {
        //按照等级来查询时间段内的人名单
        var vm = this;
        let className = localStorage.getItem("className");
        vm.$axios
          .get(
            "/personResult/selectRankingByTimeLevel/" +
              startTime +
              "/" +
              endTime +
              "/" +
              selectContent +
              "/" +
              className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total2= response.data.data.length;
              vm.arrayTime=[];
              vm.timeTableData = response.data.data;
              for (let i = 0; i < vm.timeTableData.length; i++) {
                var object =new Object();
                object.time=vm.timeTableData[i].time;
                object.ranking=vm.timeTableData[i].ranking;
                object.userName=vm.timeTableData[i].userName;
                object.teamId=vm.timeTableData[i].teamId;
                object.integral=vm.timeTableData[i].integral;
                object.level=vm.timeTableData[i].level;

                if(vm.timeTableData[i].level=="小白键盘手"){
                  object.icon=require("../../assets/img/copper.png")
                }else if (vm.timeTableData[i].level=="初级键盘手"){
                  object.icon=require("../../assets/img/silver.png")
                }else if (vm.timeTableData[i].level=="高级键盘手"){
                  object.icon=require("../../assets/img/gold.png")
                }else if (vm.timeTableData[i].level=="键盘侠"){
                  object.icon=require("../../assets/img/masonry.png")
                }
                vm.arrayTime.push(object);
                }
            } else if (response.data.code === "1111") {
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.timeTableData = "";
              vm.arrayTime="";
              vm.total2="0";
            }
          });
      }
    },
     // 查询晋级榜单数据——张艳伟-2021年11月11日11:43:46
      queryRank() {  
        var vm = this;
        vm.className = localStorage.getItem("className");
        vm.$axios
          .get( "/personResult/selectAllTeamRankingByClass/" +vm.className)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total4= response.data.data.length;
              vm.allRankTableData = response.data.data;
            } else if (response.data.code === "1111") {
              console.log("查询失败");
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.allRankTableData = "";
               vm.total4="0";
            }
          });
      
    },
    gotoPersonResultChart(row) {
      // console.log("触发双击事件");
      // console.log(row.userId);
      this.$router.push({
        path: "/personResultChart",
        query: { userid: row.userId, username: row.userName },
      });
    },
  },
};
</script>
<style scoped>
.teamImg {
  /* float: left; */
  width: 30px;
  height: 30px;
}
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
