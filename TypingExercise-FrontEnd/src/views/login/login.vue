<!--
 * @Descripttion: 
 * @version: 
 * @Author: 冯佳兴
 * @Date: 2019-09-23 16:38:32
 * @LastEditors: sueRimn
 * @LastEditTime: 2019-09-28 16:04:45
 -->
/*
 * @Author: 冯佳兴
 * @Date: 2019-09-23 12:59:53 
 * @Last Modified by:冯佳兴
 * @Last Modified time: 2019-09-24 17:58:07
 */
<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">打字系统</div>
      <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
        <el-form-item prop="usercode">
          <el-input v-model="param.usercode" placeholder="用户名"  onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;">
            <el-button slot="prepend" icon="el-icon-user"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="用户密码"
            v-model="param.password"
            @keyup.enter.native="submitForm()"  onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;">
            <el-button slot="prepend" icon="el-icon-lock"></el-button>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>

        <!-- <el-button type="text" class="login-tips" @click="registUser()">tips：立即注册</el-button> -->
      </el-form>
    </div>
    <div class="footer">
      <p>
        © 2019 METADATA MANAGE. All Rights Reserved | Design by
        <a
          href="http://www.dmsdbj.com/"
        >DynamicTime</a>
      </p>
    </div>
  </div>
</template>

<script>
//import { setCookie, getCookie } from "@/cookie.js";
export default {
  name: "Login",
  data: function() {
    return {
      param: {
        usercode: "",
        password: ""
      },
      year:"",
      month:"",
      date:"",
      dateNow:"",//当前日期
      flag:false,//没有登录过
      page:1,
      size:40,
      num:1,//获取i的值
      IntegralRule:"",//积分规则数据
      integralSource:"每日打卡",//积分来源
      smallSource:"登录打卡",//来源细分
      integralReward:1,//积分奖励几分
      rules: {
        usercode: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      }
    };
  },

  methods: {
    //获取当前日期
    getDate(){
      const nowDate = new Date();
      const date = {
          year: nowDate.getFullYear(),
          month: nowDate.getMonth()+1,
          date: nowDate.getDate(),
      }
      const newmonth = date.month>=10?date.month:'0'+date.month
      const day = date.date>=10?date.date:'0'+date.date
      this.dateNow = date.year + '-' + newmonth + '-' + day
    },
    submitForm() {
      // 动态变化
      // 向后端发送请求npm
      var vm = this;
      //存储token信息
      let token = JSON.stringify({
        token_type: "Basic",
        access_token: "emVybzp6ZXJv"
      });
      localStorage.setItem("token", token);
      
      //新版权限
      var body = { 
        password: vm.param.password, 
        userCode: vm.param.usercode,
        productId:"EuQDykEnxgCt6wgEcAErAH"};
        // console.log(body);
      //   旧版权限
      // var body = { password: vm.param.password, userCode: vm.param.usercode };
      vm.$axios
        // .post("http://192.168.26.206/auth-web/access/login", body)//501机房206服务器部署使用
      //  旧版权限
      //  .post("https://dmsdbj.com/auth-web/access/login", body)//其他环境部署使用
      // 新版权限     
        // .post("http://d-authtemp.dmsd.tech:9006/login/users", body)//开发环境配置
        .post("https://tfjybj.com/auth2-web/login/users", body)//新版权限外网环境配置
        //本地新版权限
        // .post("http://localhost:9006/login/users",body)
        .then(function(response) {
          if (response.data.code === "0000") {
            // vm.$message.success("登录成功");
            //新版
            localStorage.setItem("username", response.data.data.name); //将返回来的信息存入localStorage           
            localStorage.setItem("userid", response.data.data.userId);

            localStorage.setItem("usercode", response.data.data.userCode);
            //旧版
            // localStorage.setItem("username", response.data.data.userName); //将返回来的信息存入localStorage
            // localStorage.setItem("userid", response.data.data.id);
            let token = JSON.stringify({
              token_type: "",
              access_token: response.data.data.token  //声明并存储token信息
            });
            localStorage.setItem("token", token); // 将token信息存在localStorage
            // 判断是否是教师权限，是的话进入教师端，否则进入学生端
            if(response.data.data.userCode=='19931867073' ){
              vm.$router.push("/boardHomeTeacher");//跳转到首页
            }else{
            // vm.selectIntegralRule();//查询到登录的积分
            vm.getDate();
            // vm.selectIsSign();//判断今天是否登录过 如果是就不加分 没有登录就加分
            vm.$router.push("/Main");//跳转到首页
            }
          } else {
            vm.$message.error("账号或密码错误 ，请重新登录");
            return false;
          }
        });
    },
    //判断今天是否登录过
    // selectIsSign(){
    //   var vm = this;
    //   vm.$axios
    //     .get("/ty_person_integral/selectByUserCode/" + vm.param.usercode +"/" +vm.integralSource + "/"+ vm.smallSource )
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         for (let i = 0; i < response.data.data.length; i++) {
    //           if (response.data.data[i].createDay==vm.dateNow){
    //               vm.flag=true;//今天登录过
    //           }
    //         }
    //         if(vm.flag==true){
    //           //今天已经登录过了
    //           vm.$message.success("登录成功");
    //         }else{
    //           //今天没有登录过
    //           vm.sign();
    //         }
    //       } else if (response.data.code === "1111") {
    //         //第一次登录
    //         vm.sign();
    //       }
    //     });
    // },

    //查询规则(登录打卡的积分奖励)数据
    // selectIntegralRule() {
    //   var vm = this;
    //   vm.$axios
    //     .get("/ty_integral_rule/selectIntegralRule/" + vm.page + "/" + vm.size)
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         vm.IntegralRule = response.data.data.list;
    //         for (let i = 0; i < response.data.data.list.length; i++) {
    //           if(response.data.data.list[i].smallSource === "登录打卡"){
    //             vm.num = i;
    //           }
    //         }
    //         vm.integralReward=response.data.data.list[vm.num].integralReward;
    //         // console.log(vm.integralReward);//查询到登录打卡的积分
    //       } else if (response.data.code === "1111") {
    //         vm.$message({
    //           message:"暂无数据",
    //           type: "warning",
    //         });
    //       }
    //     });
    // },

    //登录打卡加积分
    // sign(){
    //   var vm = this;
    //   var body = {
    //     userCode: vm.param.usercode,
    //     integralSource: vm.integralSource,//积分来源
    //     smallSource: vm.smallSource,//来源细分
    //     integralReward: vm.integralReward,//积分奖励几分
    //     userName:localStorage.getItem("username")
    //   };
    //   vm.$axios
    //     .post("ty_person_integral/insertIntegral", body)
    //     .then(function (response) {
    //       if (response.data.code == "0000") {
    //         vm.$message.success("恭喜您今日已完成 登录打卡 任务，并获得学习通"+vm.integralReward+"积分。");
    //       } else {
    //         // vm.$message({
    //         //   message: "积分添加失败",
    //         //   type: "warning",
    //         // });
    //       }
    //     });
    // },
    // 跳转到注册
    registUser() {
      //存储token信息
      let token = JSON.stringify({
        token_type: "Basic",
        access_token: "emVybzp6ZXJv"
      });
      const role = localStorage.getItem("token");
      if (role === token) {
        this.$router.push("/RegistUser");
      } else {
        this.$router.push("/RegistUser");
      }
    }
  },
 
};
</script>

<style scoped>
.login-wrap {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url(../../assets/img/login-bg.jpg);
  background-size: 100%;
}
.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  font-weight: bolder;
  color: rgb(4, 98, 175);
  border-bottom: 1px solid #ddd;
}
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(245, 241, 241, 0.3);
  overflow: hidden;
}
.ms-content {
  padding: 30px 30px;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}
.login-tips {
  font-size: 12px;
  line-height: 30px;
}
.footer {
  margin-top: 30%;
  text-align: center;
  padding-top: 13%;
  letter-spacing: 1.6px;
  line-height: 22px;
  color: white;
}
</style>