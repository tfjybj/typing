<!--
 * @Descripttion: 教师端键位练习成绩
 * @version: 
 * @Author: 张艳伟
 * @Date: 2021年10月1日15:18:51
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
            <!-- 查询 -->
            <el-button
              type="primary"
              @click="(addPersonDialogVisible = false), queryNotKey(selectMenu)"
              >查询未练习人员</el-button
            >
          </div>
          <el-table
            :data="keyTableData"
            id="keyTableData"
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
              prop="strTime"
              label="练习时长"
              style="width: 10%"
              sortable
            >
            </el-table-column>
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
              prop="teamId"
              label="小组"
              style="width: 10%"
              sortable
            ></el-table-column>
            <el-table-column
              prop="articleName"
              label="课程名称"
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
              prop="sum"
              label="练习总次数"
              style="width: 10%"
              sortable
            ></el-table-column>
          </el-table>
          <br />
          <!-- 弹出框显示的内容：查看未练习人员 -->
          <el-dialog
            title="查看未练习人员"
            :visible.sync="addPersonDialogVisible"
            width="700px"
            >
           <!-- <el-button type="primary" @click="report" style="margin-bottom: 10px" >钉钉消息提醒</el-button> -->
            <el-table :data="selectTableData" style="width: 100%" height="500">
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
                prop="teamId"
                label="小组"
                style="width: 10%"
                sortable
              ></el-table-column>
            </el-table>
            共{{ total2 }}条数据
          </el-dialog>
        </div>
      </el-tabs>
      共{{ total1 }}条数据
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
      total1: 0, //键位练习人员的总数据
      total2: 0, //未参与键位练习人员的总数据
      className: "",
      // 个人成绩
      personTableData: [],
      //查看未练习人员对话框是否显示
      addPersonDialogVisible: false,
      userid: "",
      multipleTable: [], //存放选中值的数组

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
      keyTableData: [],
      selectTableData: [],
      dingIds:[],//钉钉id
    };
  },
  created() {
    // 获取个人成绩
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
    //是否发送钉钉消息
    // handleClose() {
    //   this.$confirm("真的要发送钉钉消息提醒吗?")
    //     .then((_) => {
    //       this.$message.success("消息发送中，可能有延迟，请勿多次点击！");
    //       var vm = this;
    //       var body = {
    //       // dingIds: [
    //       //   "193155041423363469",
    //       //   "190740332624457772",
    //       //   "2669160061179688068",
    //       //   "183703270221533242"
    //       // ],  //测试数据

    //         dingIds: this.dingIds,   //真实数据
    //         groupName: "打字消息通知",
    //         messageContent: "系统检测到您未进行键位练习，请您尽快进行哦！",
    //         messageTitle: "通知",
    //         sender: "markdown"
    //       };
    //       // console.log(body);
    //       vm.$axios
    //         .post("/ty_key/sendMessage", body)
    //         .then(function (response) {
    //           if (response.data.code === "0000") {
    //             vm.$message.success("发送成功");
    //           } else {
    //             vm.$message.error("发送失败");
    //             return false;
    //           }
    //         }); 
    //     })
    //     .catch((_) => {});
    // },
    //发送消息
    // report(){
    //   this.handleClose();
    // },
    exportKeyExcel() {
      if(this.keyTableData == "")
      {
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
        document.querySelector("#keyTableData"),
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
          this.selectMenu + "键位练习排行榜.xlsx"
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

    //查询键位练习数据
    selectTime(startTime, endTime, selectMenu) {
      if (this.selectMenu == "全部") {
        //根据时间和班级查询键位练习数据
        var vm = this;
        vm.$axios
          .get("/ty_key/selectKeyByTime/" + startTime + "/" + endTime)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total1 = response.data.data.length;
              vm.keyTableData = response.data.data;
            } else if (response.data.code === "1111") {
              vm.keyTableData = "";
              vm.total1="0";
            }
          });
      } else {
        //根据时间查询键位练习数据
        var vm = this;
        this.className = this.selectMenu;

        vm.$axios
          .get(
            "/ty_key/selectKeyByTimeClass/" +
              startTime +
              "/" +
              endTime +
              "/" +
              vm.className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total1 = response.data.data.length;
              vm.keyTableData = response.data.data;
            } else if (response.data.code === "1111") {
              vm.keyTableData = "";
              vm.total1="0";
            }
          });
      }
    },

    //查询未参与键位练习人员
    queryNotKey(selectMenu) {
      this.addPersonDialogVisible = true;
      if (this.selectMenu == "全部") {
        var vm = this;
        //获取当前时间
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
        }

        //查询后端接口
        vm.$axios
          .get("/ty_key/selectNotKey/" + this.startTime + "/" + this.endTime)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total2 = response.data.data.length;
              vm.selectTableData = response.data.data;
              vm.dingIds = [];
              for (let i = 0; i < vm.selectTableData.length; i++) {
                vm.dingIds.push(vm.selectTableData[i].dingId)
              }
              // console.log(vm.dingIds);
            } else if (response.data.code === "1111") {
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.selectTableData = "";
              vm.total2="0";
            }
          });
      } else {
        //根据时间查询键位练习数据
        var vm = this;
        this.className = this.selectMenu;
        //获取当前时间
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
        }

        vm.$axios
          .get(
            "/ty_key/selectNotKeyByClass/" +
              this.startTime +
              "/" +
              this.endTime +
              "/" +
              vm.className
          )
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.total2 = response.data.data.length;
              vm.selectTableData = response.data.data;
              vm.dingIds = [];
              for (let i = 0; i < vm.selectTableData.length; i++) {
                vm.dingIds.push(vm.selectTableData[i].dingId)
              }
              // console.log(vm.dingIds);

            } else if (response.data.code === "1111") {
              vm.$message({
                message: "暂无数据",
                type: "warning",
              });
              vm.selectTableData = "";
              vm.total2="0";
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
