<!--
 * @Descripttion: 教师端头
 * @version: 
 * @Author: 张艳伟
 * @Date: 2021年11月7日16:29:28
 * @LastEditors: 
 * @LastEditTime: 
 -->
<template>
  <div>
    <div class="header">
      <!-- 折叠按钮 -->
      <div class="collapse-btn" @click="collapseChage"></div>
      <div class="header-right">

        <el-menu
          router
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          background-color="#242F42"
          text-color="#61AFFE"
          active-text-color="#FFFFFF"
        >
          <el-menu-item index="/boardHomeTeacher">看板</el-menu-item>
          <el-menu-item index="/personRankList">打字排行榜</el-menu-item>
          <el-menu-item index="/personKeyBoard">键位排行榜</el-menu-item>
          <el-menu-item index="/personBattleTeam">助力次数</el-menu-item>
          <el-menu-item index="/personBattle">助力详情</el-menu-item>
          <el-menu-item index="/personPunchDay">打卡数据</el-menu-item>
          <el-menu-item index="/personTeam">团队管理</el-menu-item>
          <el-menu-item index="/personLevel">等级管理</el-menu-item>
          <el-menu-item index="/showClass">展示班级</el-menu-item>
          <el-menu-item index="/personNotice">公告管理</el-menu-item>
          <el-menu-item index="/personIntegralRule">积分管理</el-menu-item>
        </el-menu>

        <div class="header-user-con">
          <!-- 用户头像 -->
          <div class="user-avator">
            <img src="../../assets/img/img.jpg" />
          </div>
          <!-- 用户名下拉菜单 -->
          <el-dropdown
            class="user-name"
            trigger="click"
            @command="handleCommand"
          >
            <span class="el-dropdown-link">
              {{ username }}

              <i class="el-icon-caret-bottom"></i>
            </span>

            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item divided command="changePassword"
                >修改密码</el-dropdown-item
              >
              <el-dropdown-item divided command="loginout"
                >退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import bus from "../common/bus";

export default {
  data() {
    return {
      activeIndex:"",
      photo: "",
      size: 10,
      collapse: false,
      fullscreen: false,
      name: "admin",
      dialogFormVisible: false,
      ruleForm: {
        pass: null,
      },
    };
  },
  created() {},
  computed: {
    username() {
      let username = localStorage.getItem("username");

      return username ? username : this.name;
    },
    donenum() {
      return this.$store.state.donenum;
    },
  },

  methods: {
    goto() {
      this.ruleForm.pass = "";
      this.dialogFormVisible = false;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    // 用户名下拉菜单选择事件

    handleCommand(command) {
      // 退出登录
      if (command == "loginout") {
        localStorage.clear();
        this.$router.push("/");
        return;
      }
      //   修改密码
      if (command == "changePassword") {
        this.$router.push("/ChangePassword");
        return;
      }
    },
    // 侧边栏折叠
    collapseChage() {
      this.collapse = !this.collapse;
      bus.$emit("collapse", this.collapse);
    },

    // 全屏事件
    handleFullScreen() {
      let element = document.documentElement;
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          this.$refs.fullscreenselect.className = "btn-fullscreen";
          document.exitFullscreen();
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
        }
      } else {
        if (element.requestFullscreen) {
          this.$refs.fullscreenselect.className = "btn-emptyscreen";
          element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
        } else if (element.msRequestFullscreen) {
          element.msRequestFullscreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    },
  },

  mounted() {
    if (document.body.clientWidth < 1500) {
      this.collapseChage();
    }
  },
};
</script>

<style scoped>
.menu {
  font-size: 16px;
}

.header {
  position: fixed;
 z-index: 5; 
  box-sizing: border-box;

  width: 100%;

  height: 60px;
 
  font-size: 22px;

  color: #fff;
}

.collapse-btn {
  float: left;

  padding: 0 21px;

  cursor: pointer;

  line-height: 70px;
}

.header-right {
  float: right;

  padding-right: 50px;
}

.header-user-con {
  display: flex;

  height: 70px;

  align-items: center;
}

.btn-fullscreen {
  transform: rotate(45deg);

  margin-right: 5px;

  font-size: 24px;
}

.btn-bell,
.btn-fullscreen {
  position: relative;

  width: 30px;

  height: 30px;

  text-align: center;

  border-radius: 15px;

  cursor: pointer;
}

.btn-bell .el-icon-bell {
  color: #fff;
}

.user-name {
  margin-left: 10px;
}

.user-avator {
  margin-left: 20px;
}

.user-avator img {
  display: block;

  width: 40px;

  height: 40px;

  border-radius: 50%;
}

.el-dropdown-link {
  font-size: 16px;
  color: #fff;

  cursor: pointer;
}

.el-dropdown-menu__item {
  text-align: center;
}

::v-deep .el-dialog__body {
  padding: 0px;
  display: flex;
  justify-content: center;
}

.button {
  margin: 50px 50px;
  position: absolute;
  font-family: 华文行楷, STXingkai;
  font-weight: 700;
  font-size: 2em;
}
.el-menu-demo {
  position: absolute;
  right: 0;
  margin-left: 20px;
  margin-right: 180px;
  overflow: hidden;
}
</style>


