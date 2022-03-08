<!--
 * @Descripttion: 教师端添加团队人员管理
 * @version: 
 * @Author: 
 * @Date: 
 * @LastEditors: 张艳伟
 * @LastEditTime: 2021年11月7日16:26:40
 -->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <el-button type="primary" @click="addPerson()">添加</el-button>
      <el-button @click="handleOpen" type="text">批量添加</el-button>
      <ele-import
        :fields="fields"
        :filepath="filepath"
        :requestFn="requestFn"
        :rules="rules"
        :tips="tips"
        :title="title"
        :visible.sync="visible"
        @close="handleCloseImport"
        @finish="handleFinishImport"
      />
      <div style="width: 100%; margin-top: 10px; margin-bottom: 10px">
        <el-table :data="teamPersonData" style="width: 100%">
          <el-table-column
            prop="userCode"
            label="学号"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="userName"
            label="姓名"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="teamId"
            label="团队名称"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="className"
            label="班级名称"
            style="width: 10%"
          ></el-table-column>
          <el-table-column label="操作" style="width: 10%">
            <template v-slot:default="slotProps"
              >　　　　　
              <el-button type="primary" @click="modifyUser(slotProps.row)"
                >编辑</el-button
              >
              <el-button type="warning" @click="deleteUser(slotProps.row)"
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
          title="添加人员"
          :visible.sync="addPersonDialogVisible"
          width="350px"
          :before-close="handleClose"
        >
          <p class="word">
            学 号：
            <input
              oninput="value=value.replace(/[^\d]/g,'')"
              class="el-dialog-inputs"
              placeholder="请输入学号"
              v-model="addUserCode"
              maxlength="11"
            />
          </p>
          <div style="margin: 15px 0"></div>
          <p class="word">
            姓 名：
            <el-input
              class="el-dialog-input"
              placeholder="请输入姓名"
              maxlength="22"
              v-model="addUserName"
            ></el-input>
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            团队名称：
            <el-input
              class="el-dialog-input"
              placeholder="请输入团队名称"
              maxlength="22"
              v-model="addTeamName"
            ></el-input>
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            班级名称：
            <el-input
              class="el-dialog-input"
              placeholder="请输入班级名称"
              maxlength="22"
              v-model="addClassName"
            ></el-input>
          </p>

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
            姓 名：
            <el-input
              class="el-dialog-input"
              placeholder="请输入姓名"
              maxlength="22"
              v-model="editUserName"
            ></el-input>
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            团队名称：
            <el-input
              class="el-dialog-input"
              placeholder="请输入团队名称"
              maxlength="22"
              v-model="editTeamName"
            ></el-input>
          </p>
          <div style="margin: 15px 0"></div>
          <p>
            班级名称：
            <el-input
              class="el-dialog-input"
              placeholder="请输入班级名称"
              maxlength="22"
              v-model="editClassName"
            ></el-input>
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
      title: "导入人员",
      tips: ["班级必填", "组名必填", "姓名必填", "学号必填"],
      fields: {
        className: "班级",
        teamId: "组名",
        userName: "姓名",
        userCode: "学号",
      },
      rules: {
        className: { type: "string", required: true, message: "班级必填" },
        teamId: [
          { type: "number", message: "组名必须为数字" },
          { required: true, message: "组名必须填写" },
        ],
        userName: { type: "string", required: true, message: "名字必填" },
        userCode: [
          { type: "number", message: "学号必须为数字" },
          { required: true, message: "学号必须填写" },
        ],
      },
      filepath: `${process.env.BASE_URL}各班分组情况数据统计表.xlsx`, // 模板下载文件地址
      visible: false,
      tableData: "",
      // activeName: "personList1",
      //添加人员对话框是否显示
      addPersonDialogVisible: false,
      //编辑人员对话框是否显示
      editDialogVisible: false,
      //用来接收团队成员信息表格的数据
      teamPersonData: [],
      //总条数默认为0
      total: 0,
      //当前页默认值是1
      currentPage: 1,
      //默认每页显示10条
      pagesize: 10,
      //用来存放添加对话框学号
      addUserCode: "",
      //用来存放添加对话框姓名
      addUserName: "",
      //用来存放添加对话框团队名称
      addTeamName: "",
      //用来存放添加对话框班级名称
      addClassName: "",

      editId: "",
      //用来存放编辑对话框学号
      editUserCode: "",
      //用来存放编辑对话框姓名
      editUserName: "",
      //用来存放编辑对话框团队名称
      editTeamName: "",
      //用来存放编辑对话框班级名称
      editClassName: "",
      //存放添加人员信息model
      addData: {},
      //存放编辑人员信息model
      editData: {},
      //存放删除人员信息model
      deleteData: {},
    };
  },
  created() {
    var vm = this;
    //获取团队人员数据
    vm.selectTeamPersonData(vm.currentPage, vm.pagesize);
  },
  mounted() {
    localStorage.setItem("size", this.pagesize);
  },
  methods: {
    async requestFn(data) {
      this.tableData = JSON.stringify(data);

      return Promise.resolve();
      // eslint-disable-next-line
      // return Promise.reject({ 1: { age: '名字错了' } })
    },
    handleCloseImport() {
      // console.log('弹窗关闭了~')
    },
    handleFinishImport() {
      // console.log('导入完毕了~')
    },
    handleOpen() {
      this.visible = true;
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
    //查询团队成员
    selectTeamPersonData(page, size) {
      var vm = this;
      vm.$axios
        .get("/personTeam/querPersonTeam/" + page + "/" + size)
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
    //添加成员信息
    addOk() {
      if (
        this.addUserCode === "" ||
        this.addUserName === "" ||
        this.addTeamName === "" ||
        this.addClassName === ""
      ) {
        this.$message("不能为空哦！");
      } else {
        this.addData.userCode = this.addUserCode;
        this.addData.userName = this.addUserName;
        this.addData.teamId = this.addTeamName;
        this.addData.className = this.addClassName;
        this.addPersonDialogVisible = false;
        var vm = this;
        vm.$axios
          .post("/personTeam/insertPersonTeam", this.addData)
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
    //编辑成员信息
    editOk() {
      if (
        this.editUserName === "" ||
        this.editTeamName === "" ||
        this.editClassName === ""
      ) {
        this.$message("不能为空哦！");
      } else {
        this.editData.userCode = this.editUserCode;
        this.editData.userName = this.editUserName;
        this.editData.teamId = this.editTeamName;
        this.editData.className = this.editClassName;
        var vm = this;
        vm.$axios
          .post("/personTeam/updatePersonTeam", this.editData)
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
    //删除人员
    deleteUser(info) {
      this.deleteData.userCode = info.userCode;
      var vm = this;
      vm.$axios
        .post("/personTeam/deletePersonTeam", vm.deleteData)
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
      this.addUserCode = "";
      this.addUserName = "";
      this.addTeamName = "";
      this.addClassName = "";
    },
    //修改按钮
    modifyUser(info) {
      this.editUserCode = info.userCode;
      this.editUserName = info.userName;
      this.editTeamName = info.teamId;
      this.editClassName = info.className;
      this.editDialogVisible = true;
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
}
.el-dialog-inputs {
  width: 39%;
  height: 30px;
  border-radius: 5px;
  color: #7c7c7c;
  border: 0.1px solid #ebecf0;
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
