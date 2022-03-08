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
      <span>重置密码</span>
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

        <el-form-item label="新密码" prop="newPassword" class="pass">
          <el-input
            type="password"
            v-model="ruleForm.newPassword"
            autocomplete="off"
            class="input"
            ref="newPassword"
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
var num = 1;
export default {
  data() {
    //判空处理
    var validatePass1 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入新密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("newPassword");
        }
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
        newPassword: [{ validator: validatePass1, trigger: "blur" }],
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
 if (this.ruleForm.newPassword == "") {
        this.$refs.newPassword.focus();
      }  else {
        var vm = this;
        // if (num == undefined ) {
        //   var num = 1;
        // }
        let id = localStorage.getItem("userid"); //获取用户id
        let code = localStorage.getItem("usercode"); //获取用户账号
        let passWord=vm.ruleForm.newPassword;

        vm.$axios
          .post("https://dmsdbj.com/auth-web/user/updateUserPassword/"+id+"/"+code+"/"+passWord)
          .then(function(response) {
            //console.log(body)
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