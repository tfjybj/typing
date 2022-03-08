<!--
 * @描述: 
 * @版本: 
 * @作者: 冯佳兴
 * @Date: 2019年9月24日08:04:43
 * @最后修改人:冯佳兴
-->
<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <span>修改密码</span>
    </div>
    <div class="text item">
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="旧密码" prop="originPassword" class="pass">
          <el-input
            type="password"
            v-model="ruleForm.originPassword"
            autocomplete="off"
            class="input"
            ref="originPassword"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword" class="pass">
          <el-input
            type="password"
            v-model="ruleForm.newPassword"
            autocomplete="off"
            class="input"
            ref="newPassword"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass" class="pass">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
            class="input"
            ref="checkPass"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')" style="margin-left:10%">提交</el-button>
          <el-button @click="resetForm('ruleForm')" style="margin-left:10%">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-card>
</template>


<script>
import loginVue from '../login/login.vue';
var num = 1;
export default {
  data() {
    //判空处理
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入旧密码"));
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
      } else if (value !== this.ruleForm.newPassword) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      userCode: "",
      userId: "",
      ruleForm: {
        originPassword: "",
        newPassword: "",
        checkPass: ""
      },
      rules: {
        originPassword: [{ validator: validatePass, trigger: "blur" }],
        newPassword: [{ validator: validatePass1, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }]
      }
    };
  },
  methods: {
    backNum(a) {
      setTimeout(() => {
        return 0;
      }, 1000 * 5);
    },

    submitForm(formName) {
      if (this.ruleForm.originPassword == "") {
        this.$refs.originPassword.focus();
      } else if (this.ruleForm.newPassword == "") {
        this.$refs.newPassword.focus();
      } else if (this.ruleForm.checkPass == "") {
        this.$refs.checkPass.focus();
      } else if (this.ruleForm.checkPass != this.ruleForm.newPassword) {
        this.$refs.newPassword.focus();
      } else {
        var vm = this;
        // if (num == undefined ) {
        //   var num = 1;
        // }
        let id = localStorage.getItem("userid"); //获取用户id
        let code = localStorage.getItem("usercode"); //获取用户账号
        //新版权限
        vm.body = {
          newPassword: vm.ruleForm.newPassword,
          oldPassword: vm.ruleForm.originPassword,
          userCode: code,
          id: id
        };
         //旧版权限
        // this.body = {
        //   newPassword: vm.ruleForm.newPassword,
        //   originPassword: vm.ruleForm.originPassword,
        //   userCode: code,
        //   userId: id
        // };
        // console.log(this.body);
        vm.$axios
        //旧版权限接口
          // .post("https://dmsdbj.com/auth-web/user/updateUserPassword", this.body)
        
          //新版权限接口
          // .post("http://d-authtemp.dmsd.tech:9002/rbac/user/updateUserPassword",body)
          .post("https://tfjybj.com/rbac/user/updateUserPassword",vm.body)//新版权限外网地址
          .then(function(response) {
            if (response.data.code === "0000") {
              vm.$message.success("修改成功");
              vm.$router.push("/");
            } else if (num == 2) {
            } else {
              num = 2;
              vm.$message.error("修改密码失败，请联系管理员");
              setTimeout(() => {
                num = 0;
              }, 3000 );
            }
          });
      }
    },
    // 置空
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>
<style scoped>
.input {
  width: 67%;
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.pass {
  margin-left: 10%;
}
.box-card {
  position: absolute;
  margin-top: 10%;
  margin-left: 30%;
  width: 35%;
  font-size: 20px;
}
</style>