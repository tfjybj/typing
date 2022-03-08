<!--
 * @Descripttion: 教师端等级管理
 * @version: 
 * @Author: 
 * @Date: 
 * @LastEditors: 张艳伟
 * @LastEditTime: 2021年11月7日16:27:51
 -->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <el-button type="primary" @click="addLevel()">添加</el-button>
      <div style="width: 100%; margin-top: 10px; margin-bottom: 10px">
        <el-table :data="teamPersonData" style="width: 100%">
          <el-table-column
            prop="basicscore"
            label="基础分"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="beginspeed"
            label="最低速度"
            style="width: 10%"
          ></el-table-column>
          <!-- <el-table-column
          prop="endspeed"
          label="最高速度"
          style="width: 10%"
        ></el-table-column> -->
          <el-table-column
            prop="beginaccuracy"
            label="最低正确率"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="endaccuracy"
            label="最高正确率"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="level"
            label="称号"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="coefficient"
            label="级差系数"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="qualified"
            label="速度助力系数"
            style="width: 10%"
          ></el-table-column>
          <!-- <el-table-column
                prop="qualified"
                label="及格分数"
                style="width: 10%"
              ></el-table-column> -->
          <el-table-column label="操作" style="width: 10%">
            <template v-slot:default="slotProps"
              >　　　　　
              <el-button
                type="primary"
                size="mini"
                @click="modifyLevel(slotProps.row)"
                >编辑</el-button
              >
              <el-button
                type="warning"
                size="mini"
                @click="deleteLevel(slotProps.row)"
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
          title="添加等级"
          :visible.sync="addLevelDialogVisible"
          width="350px"
          :before-close="handleClose"
        >
          <p class="word">
            称号：
            <input
              class="el-dialog-input"
              placeholder="请输入称号"
              maxlength="10"
              v-model="addTitle"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            基础 分数：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入基础分数"
              v-model="addScore"
              maxlength="10"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            最低 速度：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入最低速度"
              v-model="addBasicSpeed"
              maxlength="10"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            最高 速度：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入最高速度"
              maxlength="10"
              v-model="addHighSpeed"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            最低正确率：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入最低正确率"
              maxlength="10"
              v-model="addLowAccuracy"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            最高正确率：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入最高正确率"
              maxlength="10"
              v-model="addHighAccuracy"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            级差 系数：
            <input
              class="el-dialog-input"
              placeholder="请输入级差系数"
              maxlength="10"
              v-model="addCoe"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            速度助力系数：
            <input
              class="el-dialog-input"
              placeholder="请输入速度助力系数"
              maxlength="10"
              v-model="addqualified"
            />
          </p>

          <span slot="footer" class="dialog-footer">
            <el-button @click="addLevelDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOk()">确 定</el-button>
          </span>
        </el-dialog>

        <el-dialog
          title="编辑等级信息"
          :visible.sync="editDialogVisible"
          width="350px"
          :before-close="handleClose"
        >
          <p class="editWord">
            称号：
            <input
              class="el-dialog-input"
              placeholder="请输入称号"
              maxlength="10"
              v-model="editTitle"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            基础分数：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入基础分数"
              maxlength="10"
              v-model="editScore"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            最低速度：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入最低速度"
              maxlength="10"
              v-model="editBasicSpeed"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            最高速度：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入最高速度"
              maxlength="10"
              v-model="editHighSpeed"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            最低正确率：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入最低正确率"
              maxlength="10"
              v-model="editLowerAccuracy"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            最高正确率：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-input"
              placeholder="请输入最高正确率"
              maxlength="10"
              v-model="editHighAccuracy"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            级差系数：
            <input
              class="el-dialog-input"
              placeholder="请输入级差系数"
              maxlength="10"
              v-model="editCoe"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            速度助力系数：
            <input
              class="el-dialog-input"
              placeholder="请输入速度助力系数"
              maxlength="10"
              v-model="editqualified"
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
      activeName: "personList1",
      //添加等级对话框是否显示
      addLevelDialogVisible: false,
      //编辑等级对话框是否显示
      editDialogVisible: false,
      //用来接收团队成员信息表格的数据
      teamPersonData: [],
      //总条数默认为0
      total: 0,
      //当前页默认值是1
      currentPage: 1,
      //默认每页显示10条
      pagesize: 10,
      //用来存放添加对话框称号
      addTitle: "",
      //用来存放添加对话框基础分
      addScore: "",
      //用来存放添加对话框最低速度
      addBasicSpeed: "",
      //用来存放添加对话框最高速度
      addHighSpeed: "",
      //用来存放添加对话框最低正确率
      addLowAccuracy: "",
      //用来存放添加对话框最高正确率
      addHighAccuracy: "",
      //用来存放添加对话框级差系数
      addCoe: "",
      //用来存放添加对话框速度助力系数
      addqualified: "",

      //存放修改id
      id: "",
      //用来存放编辑对话框称号
      editTitle: "",
      //用来存放编辑对话框的基础分数
      editScore: "",
      //用来存放编辑对话框最低速度
      editBasicSpeed: "",
      //用来存放编辑对话框最高速度
      editHighSpeed: "",
      //用来存放编辑对话框的最低正确率
      editLowerAccuracy: "",
      //用来存放编辑对话库康的最高正确率
      editHighAccuracy: "",
      //用来存放编辑对话框的级差系数
      editCoe: "",
      //用来存放编辑对话框的速度助力系数
      editqualified: "",

      //存放添加等级信息model
      addData: {},
      //存放编辑等级信息model
      editData: {},
    };
  },
  created() {
    var vm = this;
    //获取团队人员数据
    vm.selectLevelData(vm.currentPage, vm.pagesize);
  },
  mounted() {
    localStorage.setItem("size", this.pagesize);
  },
  methods: {
    //每页的记录数改变
    handleSizeChange(val) {
      var vm = this;
      localStorage.setItem("size", val); //让缓存中存储size--当前页有多少条数据
      vm.selectLevelData(vm.currentPage, val);
    },
    //当前页改变
    handleCurrentChange(val) {
      var vm = this;
      this.currentPage = val;
      vm.pagesize = localStorage.getItem("size"); // currentPage  存放当前为第几页
      vm.selectLevelData(val, vm.pagesize);
    },
    //查询等级信息
    selectLevelData(page, size) {
      var vm = this;
      vm.$axios
        .get("/level/querLevel/" + page + "/" + size)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.total = response.data.data.total;
            vm.teamPersonData = response.data.data.list;
          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        });
    },
    //添加等级信息
    addOk() {
      if (
        this.addTitle === "" ||
        this.addScore === "" ||
        this.addBasicSpeed === "" ||
        this.addHighSpeed === "" ||
        this.addLowAccuracy === "" ||
        this.addHighAccuracy === "" ||
        this.addCoe === "" ||
        this.addqualified === ""
      ) {
        this.$message("不能为空哦！");
      } else {
        this.addData.level = this.addTitle;
        this.addData.basicscore = this.addScore;
        this.addData.beginspeed = this.addBasicSpeed;
        this.addData.endspeed = this.addHighSpeed;
        this.addData.beginaccuracy = this.addLowAccuracy;
        this.addData.endaccuracy = this.addHighAccuracy;
        this.addData.coefficient = this.addCoe;
        this.addData.qualified = this.addqualified;
        this.addLevelDialogVisible = false;
        var vm = this;
        vm.$axios
          .post("/level/insertLevel", this.addData)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.$message.success("插入成功");
              vm.selectLevelData(1, 10);
            } else {
              vm.$message.error("插入失败");
              return false;
            }
          });
      }
    },
    //编辑等级信息
    editOk() {
      if (
        this.editTitle === "" ||
        this.editScore === "" ||
        this.editBasicSpeed === "" ||
        this.editHighSpeed === "" ||
        this.editLowerAccuracy === "" ||
        this.editHighAccuracy === "" ||
        this.editCoe === "" ||
        this.editqualified === ""
      ) {
        this.$message("不能为空哦！");
      } else {
        this.editData.id = this.id;
        this.editData.level = this.editTitle;
        this.editData.beginspeed = this.editBasicSpeed;
        this.editData.endspeed = this.editHighSpeed;
        this.editData.beginaccuracy = this.editLowerAccuracy;
        this.editData.endaccuracy = this.editHighAccuracy;
        this.editData.coefficient = this.editCoe;
        this.editData.basicscore = this.editScore;
        this.editData.qualified = this.editqualified;
        var vm = this;
        vm.$axios
          .post("/level/updateLevel", this.editData)
          .then(function (response) {
            if (response.data.code === "0000") {
              vm.$message.success("修改成功");
              vm.selectLevelData(1, 10);
            } else {
              vm.$message.error("修改失败");
              return false;
            }
          });
        this.editDialogVisible = false;
      }
    },
    //删除等级信息
    deleteLevel(info) {
      var vm = this;
      vm.$axios
        .post("/level/deleteLevel?id=" + info.id)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.$message.success("删除成功");
            //调用查询方法
            vm.selectLevelData(1, 10);
          } else {
            vm.$message.error("删除失败");
            return false;
          }
        });
    },
    //添加按钮
    addLevel() {
      this.addLevelDialogVisible = true;
      this.addTitle = "";
      this.addScore = "";
      this.addBasicSpeed = "";
      this.addHighSpeed = "";
      this.addLowAccuracy = "";
      this.addHighAccuracy = "";
      this.addCoe = "";
      this.addqualified = "";
    },
    //修改按钮
    modifyLevel(info) {
      this.editTitle = info.level;
      this.editBasicSpeed = info.beginspeed;
      this.editHighSpeed = info.endspeed;
      this.editLowerAccuracy = info.beginaccuracy;
      this.editHighAccuracy = info.endaccuracy;
      this.editCoe = info.coefficient;
      this.editScore = info.basicscore;
      this.editDialogVisible = true;
      this.editqualified = info.qualified;
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
  margin-left: 30px;
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
