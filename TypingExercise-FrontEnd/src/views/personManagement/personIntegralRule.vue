<!--
 * @Descripttion: 积分规则制定
 * @version: 
 * @Author: 张艳伟
 * @Date: 2021年10月19日20:18:38
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
            label="积分奖励"
            style="width: 10%"
          ></el-table-column>

          <el-table-column label="操作" style="width: 10%">
            <template v-slot:default="slotProps"
              >　　　　　
              <el-button type="primary" @click="modifyIntegral(slotProps.row)"
                >编辑</el-button
              >
              <el-button type="warning" @click="deleteIntegral(slotProps.row)"
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
          title="添加积分规则"
          :visible.sync="addPersonDialogVisible"
          width="350px"
          :before-close="handleClose"
        >
          <p class="word">
            积分来源：
            <input
              class="el-dialog-input"
              placeholder="请输入积分来源"
              v-model="addIntegralSource"
              maxlength="10"
            />
          </p>
          <div style="margin: 15px 0"></div>

          <p class="word">
            来源细分：
            <input
              class="el-dialog-input"
              placeholder="请输入来源细分"
              maxlength="10"
              v-model="addSmallSource"
            />
          </p>
          <div style="margin: 15px 0"></div>

          <p class="word">
            积分奖励：
            <input
              class="el-dialog-input"
              placeholder="请输入数字"
              oninput="value=value.replace(/[^\d]/g,'')"
              maxlength="10"
              v-model="addIntegralReward"
            />
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
          width="300px"
          :before-close="handleClose"
        >
          <p class="editWord">
            积分来源：
            <input
              class="el-dialog-input"
              placeholder="请输入积分来源"
              maxlength="10"
              v-model="editIntegralSource"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p class="editWord">
            来源细分：
            <input
              class="el-dialog-input"
              placeholder="请输入来源细分"
              maxlength="10"
              v-model="editSmallSource"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p class="editWord">
            积分奖励：
            <input
              class="el-dialog-input"
              placeholder="请输入积分奖励"
              oninput="value=value.replace(/[^\d]/g,'')"
              maxlength="10"
              v-model="editIntegralReward"
            />
          </p>
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
      fields: {
        integralSource: "积分来源",
        smallSource: "来源细分",
        integralReward: "积分奖励(+-)",
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
      addIntegralSource: "",
      //用来存放添加对话框内容
      addSmallSource: "",
      //用来存放添加对话框播放时长
      addIntegralReward: "",

      //存放修改id
      id: "",
      //用来存放编辑对话框播放时长
      editIntegralSource: "",
      //用来存放编辑对话框内容
      editSmallSource: "",
      //用来存放编辑对话框主题
      editIntegralReward: "",
      //存放添加信息model
      addData: {},
      //存放编辑信息model
      editData: {},
      //存放删除信息model
      deleteData: {},
    };
  },
  created() {
    var vm = this;
    //获取消息数据
    vm.selectTeamPersonData(vm.currentPage, vm.pagesize);
  },
  mounted() {
    localStorage.setItem("size", this.pagesize);
  },
  methods: {
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
        .get("/ty_integral_rule/selectIntegralRule/" + page + "/" + size)
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
      if (
        this.addIntegralSource === "" ||
        this.addSmallSource === "" ||
        this.addIntegralReward === ""
      ) {
        this.$message("不能为空哦！");
      } else {
        this.addData.integralSource = this.addIntegralSource;
        this.addData.smallSource = this.addSmallSource;
        this.addData.integralReward = this.addIntegralReward;
        this.addPersonDialogVisible = false;
        var vm = this;
        vm.$axios
          .post("/ty_integral_rule/insertIntegralRule", this.addData)
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
      if (
        this.editIntegralSource === "" ||
        this.editSmallSource === "" ||
        this.editIntegralReward === ""
      ) {
        this.$message("不能为空哦！");
      } else {
        this.editData.id = this.id;
        this.editData.integralSource = this.editIntegralSource;
        this.editData.smallSource = this.editSmallSource;
        this.editData.integralReward = this.editIntegralReward;

        var vm = this;
        vm.$axios
          .post("/ty_integral_rule/updateIntegralRule", this.editData)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.$message.success("修改成功");
              // console.log("成功");
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
    deleteIntegral(info) {
      this.deleteData.smallSource = info.smallSource;
      var vm = this;
      vm.$axios
        .post("/ty_integral_rule/deleteIntegralRule?id=" + info.id)
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
      this.addIntegralSource = "";
      this.addSmallSource = "";
      this.addIntegralReward = "";
    },
    //修改按钮
    modifyIntegral(info) {
      this.editIntegralSource = info.integralSource;
      this.editSmallSource = info.smallSource;
      this.editIntegralReward = info.integralReward;
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
  width: 50%;
  height: 30px;
  border-radius: 5px;
  color: #606266;
  border: 0.1px solid #dcdfe6;
  outline: none;
  padding: 0 15px;
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
