<!--
 * @Descripttion: 教师端打字练习排行榜
 * @version: 
 * @Author: 张艳伟
 * @Date: 2021年9月24日17:01:02
 * @LastEditors: 
 * @LastEditTime: 
 -->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <el-tabs v-model="activeName">
        <el-tab-pane label="进步英雄排行榜" name="battleTeamRankList1">
          <div style="width: 100%">
            <div class="center">
              <!-- 选择级别 -->
              <el-select
                v-model="selectMenu1"
                style="margin-right: 30px"
                placeholder="全部"
                @change="selectClassRankTableData(1, 200)"
              >
                <el-option
                  v-for="item in optionType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <!-- 查询 -->
              <!-- <el-button type="primary" @click="selectClassRankTableData(1, 100)"
              >查询</el-button
            > -->
              <!-- 导出进步英雄榜数据 -->
              <el-button
                type="primary"
                @click="exportImprovedExcel"
                style="margin-left: 28px"
                >导出</el-button
              >
            </div>
            <el-table
              :data="rankTableData"
              style="width: 100%"
              height="500"
              id="improvedTableData"
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
                prop="teamId"
                label="小组"
                style="width: 10%"
                sortable
              ></el-table-column>
              <el-table-column
                prop="ratingName"
                label="等级"
                style="width: 10%"
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
              >
                <template slot-scope="scope">
                  {{ fun(scope.row.grade) }}
                </template></el-table-column
              >
            </el-table>
          </div>
          共{{ total1 }}条数据
        </el-tab-pane>

        <el-tab-pane label="英雄排行榜" name="battleTeamRankList2">
          <div style="width: 100%">
            <div class="center">
              <!-- 选择级别 -->
              <el-select
                v-model="selectMenu"
                style="margin-right: 30px"
                placeholder="全部"
                @change="selectClassUpRankTableData(1, 200)"
              >
                <el-option
                  v-for="item in optionType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <!-- 查询 -->
              <!-- <el-button
              type="primary"
              @click="selectClassUpRankTableData(1, 100)"
              >查询</el-button
            > -->
              <!-- 导出英雄榜数据 -->
              <el-button
                type="primary"
                @click="exportPersonExcel"
                style="margin-left: 28px"
                >导出</el-button
              >
            </div>
            <el-table
              :data="upRankTableData"
              style="width: 100%"
              height="500"
              id="RankTableData"
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
                prop="teamId"
                label="小组"
                style="width: 10%"
                sortable
              ></el-table-column>

              <el-table-column
                prop="ratingName"
                label="等级"
                style="width: 10%"
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
          </div>
          共{{ total2 }}条数据
        </el-tab-pane>

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
                placeholder="全部"
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
              <!-- 选择等级 -->
              <el-select
                v-model="selectMenu3"
                style="margin-right: 30px"
                placeholder="全部"
                @change="query()"
              >
                <el-option
                  v-for="item in optionType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <!-- 查询 -->
              <!-- <el-button type="primary" @click="query()">查询</el-button> -->
              <!-- 导出时间榜数据 -->
              <el-button
                type="primary"
                @click="exportTimeExcel"
                style="margin-left: 28px"
                >导出</el-button
              >
            </div>
            <el-table
              :data="timeTableData"
              id="timeTableData"
              style="width: 100%"
              height="500"
            >
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
              <el-table-column
                prop="userName"
                label="姓名"
                style="width: 10%"
              ></el-table-column>

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
              <el-table-column
                prop="level"
                label="等级"
                style="width: 10%"
              ></el-table-column>
            </el-table>
            <br />
          </div>
          共{{ total3 }}条数据
        </el-tab-pane>

        <el-tab-pane label="晋级榜" name="battleTeamRankList4">
          <div style="width: 100%">
            <!-- 时间控件 -->
            <div class="center">
              <!-- 选择级别 -->
              <el-select
                v-model="selectValue5"
                style="margin-right: 30px"
                placeholder="全部"
                @change="queryRank()"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <!-- 选择班级 -->
              <el-select
                v-model="selectValues"
                style="margin-right: 30px"
                placeholder="全部"
                @change="queryRank()"
              >
                <el-option
                  v-for="item in optionType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <!-- 导出晋级榜数据 -->
              <el-button
                type="primary"
                @click="exportRankExcel"
                style="margin-left: 28px"
                >导出</el-button
              >
              <!-- 查询未练习人员 -->
              <el-button
                type="primary"
                @click="(addPersonDialogVisible = false), queryNotRank()"
                >查询未练习人员</el-button
              >
            </div>
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
            <!-- 弹出框显示的内容：查看未练习人员 -->
            <el-dialog
              title="查看未练习人员"
              :visible.sync="addPersonDialogVisible"
              width="700px"
            >
              <el-button
                type="primary"
                @click="report"
                style="margin-right: 20px"
                >导出数据</el-button
              >

              <el-table
                :data="selectTableData"
                id="selectTableData"
                style="width: 100%"
                height="500"
              >
                <el-table-column
                  prop="userCode"
                  label="学号"
                  style="width: 10%"
                  sortable
                ></el-table-column>
                <el-table-column
                  prop="userName"
                  label="姓名"
                  style="width: 10%"
                ></el-table-column>
                <el-table-column
                  prop="teamName"
                  label="小组"
                  style="width: 10%"
                ></el-table-column>
              </el-table>
              共{{ total5 }}条数据
            </el-dialog>
          </div>
          共{{ total4 }}条数据
        </el-tab-pane>
      </el-tabs>
      <el-backtop>
        <img
          src="../../assets/img/backTop.png"
          style="width: 60%; height: 60%"
        />
      </el-backtop>
    </el-card>
  </div>
</template>

<script>
import FileSaver from "file-saver";
import XLSX from "xlsx";
export default {
  data() {
    return {
      total1: "0",
      total2: "0",
      total3: "0",
      total4: "0",
      total5: "0",
      activeName: "battleTeamRankList1",
      className: "",
      //查看未练习人员对话框是否显示
      addPersonDialogVisible: false,
      // 个人成绩
      personTableData: [],
      allRankTableData: [], //晋级榜单数据
      fn1: "",
      userid: "",
      rankTableData: [], //英雄排行榜表单数据
      upRankTableData: [], //进步排行榜表单数据
      multipleTable: [], //存放选中值的数组
      //级别选择
      selectValue: "全部", //默认选择全部
      options: [
        {
          value: "全部",
        },
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
      ],

      selectValue5: "全部", //默认选择全部
      //查询晋级榜单
      selectValues: "全部", //默认选择全部
      //级别选择
      selectMenu: "全部", //默认选择全部
      //查询级别英雄榜
      selectMenu1: "全部",
      optionType: [],
      //查询时间榜
      selectMenu3: "全部",
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
      currentPag: 1,
      currentPageUp: 1,
      pagesize: 200,
      pagesizeUp: 200,
      selectTableData: [], //接收后端返回的未练习人员
    };
  },
  mounted() {
    this.selectShowClassName();
  },
  created() {
    // 获取个人成绩
    var vm = this;
    this.handleUserid();
    //获取英雄榜排名
    vm.selectClassUpRankTableData(vm.currentPageUp, vm.pagesizeUp);

    //获取进步排名
    vm.selectClassRankTableData(vm.currentPageUp, vm.pagesizeUp);
    //获取晋级榜单
    vm.queryRank();
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
    vm.query();
  },

  methods: {
    //查询教师端设置展示的班级
    selectShowClassName(){
      this.$axios
        .get("/personTeam/selectClassName")
        .then((res) => {
          if (res.data.code == "0000") {
            this.optionType=[];
            var i = 0;
            for (i = 0; i < res.data.data.length; i++) {
              var o =new Object();
              o.value = res.data.data[i].className;
              o.label = res.data.data[i].className;
              this.optionType.push(o);
            }
          }
        });
    },
    //进步率截取两位小数
    fun(val) {
      return Number(val).toFixed(2);
    },
    exportImprovedExcel() {
      if(this.improvedTableData){
        this.$message({
          message: "当前内容为空，不能导出",
          type: "warning",
        });
      }else{
        this.exportExcel(); //导出进步英雄排行榜数据
      }
      
    },
    exportExcel() {
      /* generate workbook object from table */
      var xlsxParam = { raw: true }; // 导出的内容只做解析，不进行格式转换
      var wb = XLSX.utils.table_to_book(
        document.querySelector("#improvedTableData"),
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
          this.selectMenu1 + "进步英雄排行榜.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") {
        }
      }
      return wbout;
    },
    exportPersonExcel() {
      if(this.RankTableData==""){
        this.$message({
          message: "当前内容为空，不能导出",
          type: "warning",
        });
      }else{
        this.exportExcelPerson(); //导出英雄排行榜数据
      }
      
    },
    exportExcelPerson() {
      /* generate workbook object from table */
      var xlsxParam = { raw: true }; // 导出的内容只做解析，不进行格式转换
      var wb = XLSX.utils.table_to_book(
        document.querySelector("#RankTableData"),
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
          this.selectMenu + "英雄排行榜.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") {
        }
      }
      return wbout;
    },
    exportTimeExcel() {
      if(this.timeTableData==""){
        this.$message({
          message: "当前内容为空，不能导出",
          type: "warning",
        }); 
      }else{
        this.exportExcelTime(); //导出时间排行榜数据
      }
      
    },
    exportExcelTime() {
      /* generate workbook object from table */
      var xlsxParam = { raw: true }; // 导出的内容只做解析，不进行格式转换
      var wb = XLSX.utils.table_to_book(
        document.querySelector("#timeTableData"),
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
          this.selectValue + this.selectMenu3 + "时间排行榜.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") {
        }
      }
      return wbout;
    },
    exportRankExcel() {
      if(this.allRankTableData == ""){
        this.$message({
          message: "当前内容为空，不能导出",
          type: "warning",
        });
      }else{
        this.exportRank(); //导出晋级榜数据
      }
      
    },
    exportRank() {
      /* generate workbook object from table */
      var xlsxParam = { raw: true }; // 导出的内容只做解析，不进行格式转换
      var wb = XLSX.utils.table_to_book(
        document.querySelector("#allRankTableData"),
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
          this.selectValues + "晋级榜.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") {
        }
      }
      return wbout;
    },

    report() {
      this.exportNotRank(); //导出未练习人员数据
    },
    exportNotRank() {
      /* generate workbook object from table */
      var xlsxParam = { raw: true }; // 导出的内容只做解析，不进行格式转换
      var wb = XLSX.utils.table_to_book(
        document.querySelector("#selectTableData"),
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
          this.selectValues + "未练习人员名单.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") {
        }
      }
      return wbout;
    },

    formatter(row, column) {
      return row.id;
    },
    // 页面切换进行查询
    handleClickTable(tab, event) {
      if (tab.name == "battleTeamRankList1") {
        //查询英雄排行榜表单数据
        var vm = this;
        this.selectClassRankTableData(1, 200);
      } else if (tab.name == "battleTeamRankList2") {
        //查询进步排行榜表单数据
        var vm = this;
        this.selectClassUpRankTableData(1, 200);
      } else if (tab.name == "battleTeamRankList3") {
        //查询时间排行榜数据
        var vm = this;
        this.query();
      } else if (tab.name == "battleTeamRankList4") {
        //查询晋级榜数据
        var VM = this;
        this.queryRank();
      }
    },
    handleSizeChange(val) {
      var vm = this;
      vm.pagesize = val;
      vm.selectClassRankTableData(vm.currentPage, val);
    },
    handleSizeChangeUp(val) {
      var vm = this;
      vm.pagesizeUp = val;
      vm.selectClassUpRankTableData(vm.currentPageUp, val);
    },
    handleCurrentChange(val) {
      var vm = this;
      this.currentPage = val;
      vm.selectClassRankTableData(val, vm.pagesize);
    },
    handleCurrentChangeUp(val) {
      var vm = this;
      this.currentPageUp = val;
      vm.selectClassUpRankTableData(val, vm.pagesizeUp);
    },
    handleUserid(val) {
      var vm = this;
      vm.userid = localStorage.getItem("userid");
    },

    //根据时间查询各个阶段的记录
    query() {
      if (this.timeValue == null || this.timeValue.length == "0") {
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
        vm.selectTime(
          this.startTime,
          this.endTime,
          this.selectValue,
          this.selectMenu3
        );
      }
    },

    //查询英雄排行榜---张艳伟-2021年9月26日10:23:49
    selectClassRankTableData(page, size) {
      if (this.selectMenu1 == "全部") {
        var vm = this;
        vm.$axios
          .get(
            "/personResult/selectPersonClassRankingClass/" + page + "/" + size
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.rankTableData = response.data.data[0];
              vm.total1 = response.data.data[1];
            } else if (response.data.code === "1111") {
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.rankTableData = "";
              vm.total1 = "0";
            }
          });
      } else {
        var vm = this;
        vm.className = this.selectMenu1;

        vm.$axios
          .get(
            "/personResult/selectPersonClassRanking/" +
              page +
              "/" +
              size +
              "/" +
              vm.className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.rankTableData = response.data.data[0];
              vm.total1 = response.data.data[1];
            } else if (response.data.code === "1111") {
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.rankTableData = "";
              vm.total1 = "0";
            }
          });
      }
    },
    //查询进步排行榜-张艳伟-2021年9月26日10:25:19
    selectClassUpRankTableData(page, size) {
      if (this.selectMenu == "全部") {
        var vm = this;
        vm.$axios
          .get(
            "/personResult/selectPersonClassProgressClass/" + page + "/" + size
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total2 = response.data.data[1];
              vm.upRankTableData = response.data.data[0];
            } else if (response.data.code === "1111") {
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.upRankTableData = "";
              vm.total2 = "0";
            }
          });
      } else {
        var vm = this;
        vm.className = this.selectMenu;
        vm.$axios
          .get(
            "/personResult/selectPersonClassProgress/" +
              page +
              "/" +
              size +
              "/" +
              vm.className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total2 = response.data.data[1];
              vm.upRankTableData = response.data.data[0];
            } else if (response.data.code === "1111") {
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.upRankTableData = "";
              vm.total2 = "0";
            }
          });
      }
    },
    //根据时间查询各个阶段的人名单
    selectTime(startTime, endTime, selectContent) {
      if (this.selectValue == "全部") {
        if (this.selectMenu3 == "全部") {
          //班级模糊查询当前时间段的所有等级人员
          var vm = this;
          vm.$axios
            .get(
              "/personResult/selectRankingByTimeLevelAllClass/" +
                startTime +
                "/" +
                endTime
            )
            .then(function (response) {
              if (response.data.code === "0000") {
                vm.timeTableData = response.data.data;
                vm.total3 = response.data.data.length;
              } else if (response.data.code === "1111") {
                vm.timeTableData = "";
                vm.total3 = "0";
              }
            });
        } else {
          //根据班级查询全部时间段的人名单-等级模糊查询
          var vm = this;
          this.className = this.selectMenu3;
          vm.$axios
            .get(
              "/personResult/selectRankingByTimeLevelAll/" +
                startTime +
                "/" +
                endTime +
                "/" +
                vm.className
            )
            .then(function (response) {
              if (response.data.code === "0000") {
                vm.timeTableData = response.data.data;
                vm.total3 = response.data.data.length;
              } else if (response.data.code === "1111") {
                vm.$message({
                  message: "暂无数据",
                  type: "warning",
                });
                vm.timeTableData = "";
                vm.total3 = "0";
              }
            });
        }
      } else {
        if (this.selectMenu3 == "全部") {
          //按照等级来查询时间段内的人名单
          var vm = this;
          vm.$axios
            .get(
              "/personResult/selectRankingByTimeLevelClass/" +
                startTime +
                "/" +
                endTime +
                "/" +
                selectContent
            )
            .then(function (response) {
              if (response.data.code === "0000") {
                vm.timeTableData = response.data.data;
                vm.total3 = response.data.data.length;
              } else if (response.data.code === "1111") {
                vm.$message({
                  message: "暂无数据",
                  type: "warning",
                });
                vm.timeTableData = "";
                vm.total3 = "0";
              }
            });
        } else {
          //按照等级来查询时间段内的人名单
          var vm = this;
          this.className = this.selectMenu3;
          vm.$axios
            .get(
              "/personResult/selectRankingByTimeLevel/" +
                startTime +
                "/" +
                endTime +
                "/" +
                selectContent +
                "/" +
                vm.className
            )
            .then(function (response) {
              if (response.data.code === "0000") {
                vm.total3 = response.data.data.length;
                vm.timeTableData = response.data.data;
              } else if (response.data.code === "1111") {
                vm.$message({
                  message: "暂无数据",
                  type: "warning",
                });
                vm.timeTableData = "";
                vm.total3 = "0";
              }
            });
        }
      }
    },

    // 查询晋级榜单数据——张艳伟-2021年11月8日14:43:39
    queryRank() {
      if (this.selectValues == "全部") {
        if (this.selectValue5 == "全部") {
          var vm = this;
          vm.$axios
            .get("/personResult/selectAllTeamRanking")
            .then(function (response) {
              if (response.data.code === "0000") {
                vm.total4 = response.data.data.length;
                vm.allRankTableData = response.data.data;
              } else if (response.data.code === "1111") {
                vm.$message({
                  message: "暂无数据",
                  type: "warning",
                });
                vm.allRankTableData = "";
                vm.total4 = "0";
              }
            });
        } else {
          var vm = this;
          vm.level = this.selectValue5;
          vm.$axios
            .get("/personResult/selectAllTeamRankingByLevel/" + vm.level)
            .then(function (response) {
              if (response.data.code === "0000") {
                vm.total4 = response.data.data.length;
                vm.allRankTableData = response.data.data;
              } else if (response.data.code === "1111") {
                vm.$message({
                  message: "暂无数据",
                  type: "warning",
                });
                vm.allRankTableData = "";
                vm.total4 = "0";
              }
            });
        }
      } else {
         if (this.selectValue5 == "全部") {
        var vm = this;
        vm.className = this.selectValues;
        vm.$axios
          .get("/personResult/selectAllTeamRankingByClass/" + vm.className)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total4 = response.data.data.length;
              vm.allRankTableData = response.data.data;
            } else if (response.data.code === "1111") {
              console.log("查询失败");
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.allRankTableData = "";
              vm.total4 = "0";
            }
          });
      
      }else{
         var vm = this;
        vm.className = this.selectValues;
        vm.level = this.selectValue5;
        vm.$axios
          .get("/personResult/selectAllTeamRankingByClassLevel/" + vm.className+"/"+vm.level)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total4 = response.data.data.length;
              vm.allRankTableData = response.data.data;
            } else if (response.data.code === "1111") {
              console.log("查询失败");
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.allRankTableData = "";
              vm.total4 = "0";
            }
          });

      }}
    },

    //查询未练习人员数据——张艳伟-2021年11月8日16:24:16
    queryNotRank() {
      this.addPersonDialogVisible = true;
      if (this.selectValues == "全部") {
        var vm = this;
        vm.$axios
          .get("/personResult/selectAllNotRanking")
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total5 = response.data.data.length;
              vm.selectTableData = response.data.data;
            } else if (response.data.code === "1111") {
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.selectTableData = "";
              vm.total5 = "0";
            }
          });
      } else {
        var vm = this;
        vm.className = this.selectValues;
        vm.$axios
          .get("/personResult/selectAllNotRankingByClass/" + vm.className)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total5 = response.data.data.length;
              vm.selectTableData = response.data.data;
            } else if (response.data.code === "1111") {
              console.log("查询失败");
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.selectTableData = "";
              vm.total5 = "0";
            }
          });
      }
    },

    // 双击事件
    gotoPersonResultChart(row) {
      this.$router.push({
        path: "/personResultChart",
        query: { userid: row.userId, username: row.userName },
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
