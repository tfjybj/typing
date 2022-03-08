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
      <!-- <el-tabs v-model="activeName"> -->
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
            <!-- 查询未打卡人员 -->
            <!-- <el-button type="primary" @click="queryNo()"
            >查询未打卡人员</el-button
          > -->
            <!-- 导出时间榜数据 -->
            <el-button
              type="primary"
              @click="exportTimeExcel"
              style="margin-left: 28px"
              >导出</el-button
            >
          </div>
          <el-table
            :data="timeSignTableData"
            id="timeSignTableData"
            style="width: 100%"
            height="500"
          >
            <el-table-column
              prop="createTime"
              label="时间"
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
      <!-- </el-tabs> -->
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
      personTableData: [],
      fn1: "",
      userid: "",

      multipleTable: [], //存放选中值的数组
      //级别选择
      selectValue: "全部", //默认选择全部
      options: [
        {
          value: "全部",
        },
        {
          value: "登录打卡",
        },
        // {
        //   value: "文章练习",
        // },
        {
          value: "键位练习",
        },
        {
          value: "邀请助力",
        },
      ],
      //查询时间榜
      selectMenu3: "全部",
      optionType: [],
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
      timeSignTableData: [],
    };
  },
  created() {
    // 获取个人成绩
    var vm = this;
    this.handleUserid();

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
    //导出数据
    exportTimeExcel() {
      if(this.timeSignTableData == ""){
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
        document.querySelector("#timeSignTableData"),
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
          this.selectValue + this.selectMenu3 + "每日打卡数据.xlsx"
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

    //根据时间查询各个阶段的人名单
    selectTime(startTime, endTime, selectContent) {
      if (this.selectValue == "全部") {
        if (this.selectMenu3 == "全部") {
          //班级模糊查询当前时间段的所有等级人员
          var vm = this;
          vm.$axios
            .get(
              "/ty_person_integral/selectPunchByTime/" +
                startTime +
                "/" +
                endTime
            )
            .then(function (response) {
              if (response.data.code === "0000") {
                 vm.total= response.data.data.length;
                vm.timeSignTableData = response.data.data;
              } else if (response.data.code === "1111") {
                vm.timeSignTableData = "";
                vm.total="0";
              }
            });
        } else {
          //根据班级查询全部时间段的人名单-等级模糊查询
          var vm = this;
          this.className = this.selectMenu3;
          vm.$axios
            .get(
              "/ty_person_integral/selectPunchByTimeClass/" +
                startTime +
                "/" +
                endTime +
                "/" +
                vm.className
            )
            .then(function (response) {
              if (response.data.code === "0000") {
                 vm.total= response.data.data.length;
                vm.timeSignTableData = response.data.data;
              } else if (response.data.code === "1111") {
                vm.$message({
                  message: "暂无数据",
                  type: "warning",
                });
                vm.timeSignTableData = "";
                vm.total="0";
              }
            });
        }
      } else {
        if (this.selectMenu3 == "全部") {
          //按照细节积分来查询时间段内的人名单
          var vm = this;
          vm.$axios
            .get(
              "/ty_person_integral/selectPunchByTimeSource/" +
                startTime +
                "/" +
                endTime +
                "/" +
                selectContent
            )
            .then(function (response) {
              if (response.data.code === "0000") {
                 vm.total= response.data.data.length;
                vm.timeSignTableData = response.data.data;
              } else if (response.data.code === "1111") {
                vm.$message({
                  message: "暂无数据",
                  type: "warning",
                });
                vm.timeSignTableData = "";
                vm.total="0";
              }
            });
        } else {
          //按照积分和班级来查询时间段内的人名单
          var vm = this;
          this.className = this.selectMenu3;
          vm.$axios
            .get(
              "/ty_person_integral/selectPunchByTimeSourceClass/" +
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
                 vm.total= response.data.data.length;
                vm.timeSignTableData = response.data.data;
              } else if (response.data.code === "1111") {
                vm.$message({
                  message: "暂无数据",
                  type: "warning",
                });
                vm.timeSignTableData = "";
                vm.total="0";
              }
            });
        }
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
