<!--
 * @Descripttion: 公告消息提醒管理
 * @version: 
 * @Author: 张艳伟
 * @Date: 2021-10-18 15:47:44
 * @LastEditors: 
 * @LastEditTime:
-->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <el-button type="primary" @click="addPerson()">添加</el-button>
      <div style="width: 100%; margin-top: 10px; margin-bottom: 10px">
        <el-table :data="teamPersonData" style="width: 100%">
          <el-table-column
            prop="noticeName"
            label="消息主题"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="notice"
            label="消息内容"
            style="width: 80%"
          ></el-table-column>
          <el-table-column
            prop="setTime"
            label="播放时长(秒)"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="startTime,endTime"
            label="播放时间"
            style="width: 10%"
            ><template slot-scope="scope"
              >{{ scope.row.startTime }}-{{ scope.row.endTime }}</template
            ></el-table-column
          >
          <el-table-column
            prop="remark"
            label="展示范围"
            style="width: 10%"
          ></el-table-column>

          <el-table-column label="操作" style="width: 10%">
            <template v-slot:default="slotProps"
              >　　　　　
              <el-button type="primary" @click="modifyNotice(slotProps.row)"
                >编辑</el-button
              >
              <el-button type="warning" @click="deleteNotice(slotProps.row)"
                >删除</el-button
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

        <el-dialog
          title="添加消息"
          :visible.sync="addPersonDialogVisible"
          width="600px"
          :before-close="handleClose"
        >
          <p class="word">
            消息主题：
            <el-select
              v-model="selectMenus"
              style="margin-right: 75px"
              placeholder="请选择消息主题"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </p>
          <div style="margin: 15px 0"></div>

          <p class="word">
            消息内容：
            <el-input
              type="textarea"
              :rows="2"
              class="el-dialog-input"
              placeholder="请输入消息内容"
              maxlength="255"
              v-model="addNotice"
            ></el-input>
          </p>
          <div style="margin: 15px 0"></div>

          <p class="word">
            播放时长：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-inputs"
              placeholder="请输入播放时长"
              maxlength="10"
              v-model="addSetTime"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <!-- 播放时间 -->
          <p class="word">
            播放时间：
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
            ></el-date-picker>
          </p>
          <div style="margin: 15px 0"></div>

          <!-- 选择等级 -->
          <p class="word">
            展示范围：
            <el-select
              v-model="selectMenu"
              style="margin-right: 30px"
              placeholder="请选择班级"
            >
              <el-option
                v-for="item in option"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </p>
          <div style="margin: 15px 0"></div>

          <span slot="footer" class="dialog-footer">
            <el-button @click="addPersonDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOk()">确 定</el-button>
          </span>
        </el-dialog>

        <el-dialog
          title="编辑"
          :visible.sync="editDialogVisible"
          width="600px"
          :before-close="handleClose"
        >
          <!-- 选择消息主题 -->
          <p class="word">
            消息主题：
            <el-select
              v-model="selectMenus"
              style="margin-right: 30px"
              placeholder="请选择消息主题"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </p>
          <div style="margin: 15px 0"></div>
          <p class="editWord">
            消息内容：
            <el-input
              type="textarea"
              :rows="2"
              class="el-dialog-input"
              placeholder="请输入消息内容"
              maxlength="255"
              v-model="editNotice"
            ></el-input>
          </p>
          <div style="margin: 15px 0"></div>
          <p class="editWord">
            播放时长：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-inputs"
              placeholder="请输入播放时长"
              maxlength="10"
              v-model="editSetTime"
            />
          </p>
          <div style="margin: 15px 0"></div>

          <!-- 播放时间 -->
          <p class="word">
            播放时间：
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
            ></el-date-picker>
          </p>
          <div style="margin: 15px 0"></div>

          <!-- 选择等级 -->
          <p class="word">
            展示范围：
            <el-select v-model="selectMenu" style="margin-right: 30px">
              <el-option
                v-for="item in option"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </p>
          <div style="margin: 15px 0"></div>

          <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editOk()">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //查询展示范围
      selectMenu: "",
      option: [
        {
          value: "师范学院",
        },
        {
          value: "计算机协会",
        },
      ],

      //查询消息主题
      selectMenus: "",
      options: [
        {
          value: "公告",
        },
        {
          value: "弹窗",
        },
      ],
      fields: {
        setTime: "播放时长",
        notice: "消息内容",
        noticeName: "消息主题",
      },

      visible: false,
      tableData: "",

      //添加对话框是否显示
      addPersonDialogVisible: false,
      //编辑对话框是否显示
      editDialogVisible: false,
      //用来接收信息表格的数据
      teamPersonData: [],
      //总条数默认为0
      total: 0,
      //当前页默认值是1
      currentPage: 1,
      //默认每页显示10条
      pagesize: 10,
      //用来存放添加对话框主题
      selectMenu: "",
      //用来存放添加对话框内容
      addNotice: "",
      //用来存放添加对话框播放时长
      addSetTime: "",

      //存放修改id
      id: "",
      //用来存放编辑对话框播放时长
      editSetTime: "",
      //用来存放编辑对话框内容
      editNotice: "",
      //用来存放编辑对话框主题
      selectMenus: "",
      //存放添加信息model
      addData: {},
      //存放编辑信息model
      editData: {},
      //存放删除信息model
      deleteData: {},

      timeValue: [], //时间控件值
      //开始时间
      startTime: "",
      //结束时间
      endTime: "",
      //接收后端返回来的根据时间查询的表格数据
      timeNumTableData: [],
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

    //获取消息数据
    vm.selectTeamPersonData(vm.currentPage, vm.pagesize);
  },
  mounted() {
    localStorage.setItem("size", this.pagesize);
  },
  methods: {
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
      }
    },
    async requestFn(data) {
      this.tableData = JSON.stringify(data);

      return Promise.resolve();
    },
    //每页的记录数改变
    handleSizeChange(val) {
      var vm = this;
      localStorage.setItem("size", val); //让缓存中存储size--当前页有多少条数据
      vm.selectTeamPersonData(vm.currentPage, val);
    },
    //当前页改变
    handleCurrentChange(val) {
      var vm = this;
      this.currentPage = val;
      vm.pagesize = localStorage.getItem("size"); // currentPage  存放当前为第几页
      vm.selectTeamPersonData(val, vm.pagesize);
    },
    //查询消息数据
    selectTeamPersonData(page, size) {
      var vm = this;
      vm.$axios
        .get("/ty_notice/selectNotice/" + page + "/" + size)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.total = response.data.data.total;
            vm.teamPersonData = response.data.data.list;
          } else if (response.data.code === "1111") {
            vm.$message({
              message: "暂无数据",

              type: "warning",
            });
          }
        });
    },
    //添加消息
    addOk() {
      this.query();
      this.addSetTime = parseInt(this.addSetTime);
      if (
        this.selectMenus == "" ||
        this.addNotice == "" ||
        this.startTime == "" ||
        this.endTime == "" ||
        this.selectMenu == ""
      ) {
        this.$message("不能为空哦！");
      } else if (this.addSetTime == 0 || this.addSetTime < 0) {
        this.$message.error("播放时间不能为0哦！");
        return;
      } else if (this.selectMenus == "公告" && this.addNotice.length > 30) {
        this.$message.error("公告内容不能超过30字哦！");
        return;
      } else {
        this.addData.noticeName = this.selectMenus;
        this.addData.notice = this.addNotice;
        this.addData.setTime = this.addSetTime;
        this.addData.startTime = this.startTime;
        this.addData.endTime = this.endTime;
        this.addData.remark = this.selectMenu;
        this.addPersonDialogVisible = false;
        var vm = this;
        vm.$axios
          .post("/ty_notice/insertNotice", this.addData)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.$message.success("插入成功");
              vm.selectTeamPersonData(1, 10);
            } else {
              vm.$message.error("插入失败");
              return false;
            }
          });
      }
    },
    //编辑消息
    editOk() {
      this.query();
      this.editSetTime = parseInt(this.editSetTime);
      if (
        this.selectMenus == "" ||
        this.editNotice == "" ||
        this.endTime == "" ||
        this.startTime == "" ||
        this.selectMenu == ""
      ) {
        this.$message("不能为空哦！");
      } else if (this.editSetTime == 0 || this.editSetTime < 0) {
        this.$message.error("播放时间不能为0哦！");
        return;
      } else if (this.selectMenus == "公告" && this.editNotice.length > 30) {
        this.$message.error("公告内容不能超过30字哦！");
        return;
      } else {
        this.editData.id = this.id;
        this.editData.noticeName = this.selectMenus;
        this.editData.notice = this.editNotice;
        this.editData.setTime = this.editSetTime;
        this.editData.startTime = this.startTime;
        this.editData.endTime = this.endTime;
        this.editData.remark = this.selectMenu;

        var vm = this;
        vm.$axios
          .post("/ty_notice/updateNotice", this.editData)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.$message.success("修改成功");

              vm.selectTeamPersonData(1, 10);
            } else {
              vm.$message.error("修改失败");
              return false;
            }
          });
        this.editDialogVisible = false;
      }
    },
    //删除消息
    deleteNotice(info) {
      this.deleteData.noticeName = info.noticeName;
      var vm = this;
      vm.$axios
        .post("/ty_notice/deleteNotice?id=" + info.id)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.$message.success("删除成功");
            //调用查询方法
            vm.selectTeamPersonData(1, 10);
          } else {
            vm.$message.error("删除失败");
            return false;
          }
        });
    },
    //添加按钮
    addPerson() {
      this.addPersonDialogVisible = true;
      this.selectMenus = "";
      this.addNotice = "";
      this.addSetTime = "";
      this.selectMenu = "";
    },
    //修改按钮
    modifyNotice(info) {
      this.selectMenus = info.noticeName;
      this.editNotice = info.notice;
      this.editSetTime = info.setTime;
      this.selectMenu = info.remark;
      this.editDialogVisible = true;
      this.id = info.id;
    },
    //添加和编辑对话框取消按钮
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
  },
};
</script>
<style scoped>
.el-dialog-input {
  width: 75%;
}
.el-dialog-inputs {
  width: 69%;
  height: 30px;
  border-radius: 5px;
  color: #606266;
  border: 0.1px solid #dcdfe6;
  outline: none;
  padding: 0 15px;
}
input::-webkit-input-placeholder {
  color: rgb(203, 207, 202);
  font-size: 3px;
  /* font-style:oblique; */
  opacity: 1;
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
