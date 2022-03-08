/*
 * @Author: 冯佳兴
 * @Date: 2019-09-22 12:59:53 
 * @Last Modified by:冯佳兴
 * @Last Modified time: 2019年9月23日11:34:59
 */
<template>
  <el-card class="box-card">
    <div slot="header">
      <span>注册账号</span>
      <el-button style="float: right; padding: 3px 0" type="text" @click="login()">已有账号？去登录</el-button>
    </div>
    <el-form
      :model="ruleForm"
      status-icon
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="手机号" prop="telephone" class="pass">
        <el-input  type="text" v-model.number="ruleForm.telephone" autocomplete="off" class="input"></el-input>
      </el-form-item>
      
      <el-form-item label="姓名" prop="name" class="pass">
        <el-input  type="text" v-model="ruleForm.name" autocomplete="off" class="input"></el-input>
      </el-form-item>

      <el-form-item label="新密码" prop="newpass" class="pass">
        <el-input type="password" v-model="ruleForm.newpass" autocomplete="off" class="input"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass" class="pass">
        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" class="input"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')" style="margin-left:1%">提交</el-button>
        <el-button @click="resetForm('ruleForm')" style="margin-left:10%">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
export default {
  data() {
    var validatePass3 = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("手机号不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 18) {
            callback(new Error("请输入11位的手机号"));
          } else {
            callback();
          }
        }
      }, 1000);
    };

    var validatePass4 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入姓名"));
      }
    };
    var validatePass1 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入新密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.newpass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      labelPosition: "left",
      // 实体，分别为电话，姓名，密码，确认密码
      ruleForm: {
        telephone: "",
        name: "",
        newpass: "",
        checkPass: ""
      },
      rules: {
        newpass: [{ validator: validatePass1, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        telephone: [{ validator: validatePass3, trigger: "blur" }],
        name: [{ validator: validatePass4, trigger: "blur" }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      var vm = this;
      if (
        vm.ruleForm.telephone == "" ||
        vm.ruleForm.name == "" ||
        vm.ruleForm.newpass == "" ||
        vm.ruleForm.checkPass == ""
      ) {
        vm.$message.error("请将信息补充完整");
        return;
      } else {
        var body = {
          password: vm.ruleForm.newpass,
          userCode: vm.ruleForm.telephone,
          userName: vm.ruleForm.name,
          telNum: vm.ruleForm.telephone
        };
        vm.$axios
          .post("http://192.168.26.205//auth-web/access/registUser", body)
          .then(function(response) {
            // console.log(body);
            if (response.data.code === "0000") {
              vm.$message.success("注册成功");
              vm.$router.push("/");
            } else {
              vm.$message.error("添加用户失败，该用户已经注册");
              return false;
            }
          });
      }
    },
    login() {
      this.$router.push("/");
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  // 初始化界面，信息置空
  updated: function() {
    resetForm(formName)
  }
};
</script>

<style scoped>
.box-card {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}
.input {
  width: 79%;
}
</style>