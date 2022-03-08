<!--
 * @Descripttion: 教师端查看邀请助力详情
 * @version: 
 * @Author: 张艳伟
 * @Date: 2021年10月11日21:55:40
 * @LastEditors: 
 * @LastEditTime: 
 -->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <el-tabs>
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

            <!-- 选择等级 -->
            <el-select
              v-model="selectMenu"
              style="margin-right: 30px"
              placeholder="全部"
              @change="query()"
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
              @click="exportKeyExcel"
              style="margin-left: 28px"
              >导出</el-button
            >
          </div>
          <el-table
            :data="timeBattleTableData"
            id="timeBattleTableData"
            style="width: 100%"
            height="500"
          >
            <el-table-column
              prop="time"
              label="创建时间"
              style="width: 10%"
              sortable
            ></el-table-column>
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
              label="战队名称"
              style="width: 10%"
              sortable
            ></el-table-column>
            <el-table-column
              prop="realTime"
              label="标识"
              style="width: 10%"
              sortable
            ></el-table-column>
            <el-table-column
              prop="speed"
              label="打字速度"
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
              prop="setTime"
              label="设定时间"
              style="width: 10%"
              sortable
            ></el-table-column>

            <el-table-column
              prop="articleName"
              label="文章名称"
              style="width: 10%"
              sortable
            ></el-table-column>
            <el-table-column
              prop="score"
              label="个人成绩"
              style="width: 10%"
              sortable
            ></el-table-column>
            <el-table-column
              prop="improvedScore"
              label="助力成绩"
              style="width: 10%"
              sortable
            ></el-table-column>
          </el-table>
          <br />
        </div>
        共{{total}}条数据
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
import FileSaver from "file-saver";
import XLSX from "xlsx";
export default {
  data() {
    return {
      total:0,
      className: "",
      // 个人成绩
      userid: "",
      //查询时间榜
      selectMenu: "全部",
      optionsType: [],

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
      timeBattleTableData: [],
    };
  },

  created() {
    var vm = this;
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
  mounted() {
    this.selectShowClassName();
  },
  //上下箭头
  methods: {
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
    exportKeyExcel() {
      if(this.timeBattleTableData == ""){
        this.$message({
          message: "当前内容为空，不能导出",
          type: "warning",
        });
      }else{
        this.exportExcelKey(); //导出时间排行榜数据
      }
    },
    exportExcelKey() {
      /* generate workbook object from table */
      var xlsxParam = { raw: true }; // 导出的内容只做解析，不进行格式转换
      var wb = XLSX.utils.table_to_book(
        document.querySelector("#timeBattleTableData"),
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
          this.selectMenu + "邀请助力详情.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") {
          // console.log(e, wbout);
        }
      }
      return wbout;
    },
    formatter(row, column) {
      return row.id;
    },
    //根据时间查询记录
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
        vm.selectTime(this.startTime, this.endTime, this.selectMenu);
      }
    },

    //查询邀请助力次数
    selectTime(startTime, endTime, selectContent) {
      if (this.selectMenu == "全部") {
        //根据时间和班级查询邀请助力次数
        var vm = this;
        // console.log(startTime);

        vm.$axios
          .get(
            "/teamDetailResult/selectTeamByTime/" + startTime + "/" + endTime
          )
          .then(function (response) {
            // console.log(response);
            if (response.data.code === "0000") {
              vm.total= response.data.data.length;
              vm.timeBattleTableData = response.data.data;
              var i = 0;
              for (i = 0; i < response.data.data.length; i++) {
                var tableData = response.data.data[i];
                if (tableData.realTime == "1") {
                  tableData.realTime = "队长";
                  // console.log("我是队长");
                } else if (tableData.realTime == "0") {
                  tableData.realTime = "队员";
                  // console.log("我是队员");
                }
              }
            } else if (response.data.code === "1111") {
              // vm.$message({
              //   message: "暂无数据",
              //   type: "warning",
              // });
              vm.timeBattleTableData = "";
              vm.total="0";
            }
          });
      } else {
        //根据时间查询邀请助力详情
        var vm = this;
        this.className = this.selectMenu;
        // console.log(this.className);
        vm.$axios
          .get(
            "/teamDetailResult/selectTeamByTimeClass/" +
              startTime +
              "/" +
              endTime +
              "/" +
              vm.className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total= response.data.data.length;
              vm.timeBattleTableData = response.data.data;
              var i = 0;
              for (let i = 0; i < response.data.data.length; i++) {
                var tableData = response.data.data[i];
                if (tableData.realTime == "1") {
                  tableData.realTime = "队长";
                  // console.log("我是队长");
                } else if (tableData.realTime == "0") {
                  tableData.realTime = "队员";
                  // console.log("我是队员");
                }
              }
            } else if (response.data.code === "1111") {
              // vm.$message({
              //   message: "暂无数据",
              //   type: "warning",
              // });
              vm.timeBattleTableData = "";
              vm.total="0";
            }
          });
      }
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
