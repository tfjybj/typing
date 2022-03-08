<template>
  <div>
    <div class="header">
      <!-- 折叠按钮 -->
      <div class="collapse-btn" @click="collapseChage">
        <!-- <i v-if="!collapse" class="el-icon-s-fold"></i>
      <i v-else class="el-icon-s-unfold"></i> -->
      </div>
      <!-- <div class="logo">工作，游戏快人一步！</div> -->
      <div class="logo1">
        <span class="el-dropdown-link1"> 称号： {{ level }} </span>
        <span class="el-dropdown-link1"> ({{ levelNum }}/10) </span>
        <span class="el-dropdown-link1"> 积分：{{ totalGrade }} </span>
        <span class="el-dropdown-link1">
          进步积分：{{ progressGrade }}
          <span class="el-dropdown-link1"> 小组积分：{{ teamGrade }} </span>
        </span>
      </div>

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
          <el-menu-item index="/Main">首页</el-menu-item>
          <el-menu-item index="/personResultChart">个人成绩</el-menu-item>
          <el-menu-item index="/personBattleResult">助力详情</el-menu-item>
          <el-menu-item index="/commonRankList">英雄榜</el-menu-item>
          <el-menu-item index="/boardHome">看板</el-menu-item>
          <el-menu-item index="/index">奖状</el-menu-item>
          <el-menu-item index="/lottery">抽奖</el-menu-item>
          <!-- <el-menu-item index="/sign">打卡</el-menu-item> -->
        </el-menu>
        <div class="header-user-con">
          <!-- 全屏显示 -->
          <!-- <div
            class="btn-fullscreen"
            @click="handleFullScreen"
            ref="fullscreenselect"
          >
            <el-tooltip
              effect="dark"
              :content="fullscreen ? `取消全屏` : `全屏`"
              placement="bottom"
            >
              <i class="el-icon-rank"></i>
            </el-tooltip>
          </div> -->

          <!-- 用户头像 -->
          <img
            v-if="photo == 1"
            class="photoImg"
            src="../../assets/img/copper.png"
          />
          <img
            v-if="photo == 2"
            class="photoImg"
            src="../../assets/img/silver.png"
            slot="reference"
          />
          <img
            v-if="photo == 3"
            class="photoImg"
            src="../../assets/img/gold.png"
            slot="reference"
          />
          <img
            v-if="photo == 4"
            class="photoImg"
            src="../../assets/img/masonry.png"
            slot="reference"
          />
          <!-- <div class="user-avator">
            <img src="../../assets/img/img.jpg" />
          </div> -->

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
              <!-- <el-dropdown-item divided command="personManagement"
                >配置管理</el-dropdown-item
              > -->
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
      <div>
        <el-dialog
          title="二次校验"
          :visible.sync="dialogFormVisible"
          :show-close="false"
          :close-on-press-escape="false"
          :close-on-click-modal="false"
          width="30%"
        >
          <el-form
            :model="ruleForm"
            status-icon
            :rules="rules"
            ref="ruleForm"
            label-width="100px"
            class="demo-ruleForm"
          >
            <el-form-item label="密码" prop="pass">
              <el-input
                type="password"
                v-model="ruleForm.pass"
                autocomplete="off"
                style="width: 80%"
                @keydown.enter.native="submitForm('ruleForm')"
              ></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="goto">取 消</el-button>
            <el-button type="primary" @click="submitForm('ruleForm')"
              >确 定</el-button
            >
          </div>
        </el-dialog>
      </div>
    </div>
    <el-dialog
      title="证书预览和下载"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose"
    >
      <div id="pdfDom">
        <div class="proBox">
          <p class="tit"><center>荣誉证书</center></p>
          <p class="con">
            <span>恭喜</span>
            <span class="con-name">{{ userName }}</span
            >同学：<br />
          </p>
          <p class="con-text"><span>在《“键盘侠”成长记》活动中荣获</span></p>
          <p class="con-height">
            <span
              ><center>{{ personLevel }}</center></span
            >
          </p>
          <p class="con-text">荣誉称号，特发此状，以资鼓励。</p>

          <div class="con-unit">
            <p class="time">{{ this.start }}</p>
          </div>
          <div class="chapter" v-show="isShow">
            <canvas id="chapterCanvas" width="150" height="150"></canvas>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-switch
          inactive-color="#67c23a"
          v-model="downType"
          active-text="图片下载"
          inactive-text="pdf下载"
          style="margin-right: 20px"
        >
        </el-switch>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="getPdf('#pdfDom')">下载</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import bus from "../common/bus";

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("密码不能为空"));
      }
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };
    return {
      activeIndex:"",
      photo: "",
      className1: [], //自己班级
      page: 1,
      size: 10,
      userName: localStorage.getItem("username"),
      start: "",
      personLevel: localStorage.getItem("level"),
      dialogVisible: false,
      pageData: null, //接收html格式代码
      htmlTitle: "荣誉证书",
      isShow: true,
      isCanvas: false,
      downType: true, // false为 pdf , true为图片

      collapse: false,

      fullscreen: false,

      name: "admin",

      message: 2,

      level: "小白键盘手",

      levelNum: "0",

      progressGrade: 0,

      teamGrade: 0,

      totalGrade: 0,

      dialogFormVisible: false,
      ruleForm: {
        pass: null,
      },
      formLabelWidth: "120px",
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
      },
    };
  },
  created() {
    // 获取个人称号

    var vm = this;

    vm.selectPersonResult();

    vm.selectLevel();
    // vm.selectPhoto();
    vm.selectClassName();//获取班级
    // vm.selectTeamData();//获取晋级率
  },
  watch: {
    donenum(val, newval) {
      this.selectPersonResult();
      this.selectLevel();
    },
  },
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
    //显示个人头像
    selectPhoto() {
      console.log(localStorage.getItem("level"));
      if (localStorage.getItem("level") == "小白键盘手") {
        this.photo = 1;
      } else if (localStorage.getItem("level") == "初级键盘手") {
        this.photo = 2;
      } else if (localStorage.getItem("level") == "高级键盘手") {
        this.photo = 3;
      } else if (localStorage.getItem("level") == "键盘侠") {
        this.photo = 4;
      }
      console.log(this.photo);
    },
    //查询班级 放入缓存
    selectClassName() {
      this.$axios
        .get(
          "/personResult/selectClass?userCode=" +
            localStorage.getItem("usercode")
        )
        .then((res) => {
          if (res.data.code == "0000") {
            localStorage.setItem("className", res.data.data.className);
            this.selectTeamData();
          }
        });
    },

    //获取到小组晋级率
    selectTeamData() {
      var vm = this;
      let a = localStorage.getItem("className");
      vm.className1.push(a);
      vm.$axios
        .post("/personResult/selectTeamRanking", vm.className1)
        .then((res) => {
          var i = 0;
          if (res.data.code === "0000") {
            for (i = 0; i < res.data.data.length; i++) {
              if (
                res.data.data[i].userNames.indexOf(
                  localStorage.getItem("username")
                ) != -1
              ) {
                vm.promotion = res.data.data[i].promotion * 100; //获取到小组晋级率
                vm.teamName = res.data.data[i].teamName;
                localStorage.setItem("promotion", vm.promotion);
                localStorage.setItem("teamName", vm.teamName);
                break;
              }
            }
          } else if (res.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        });
    },

    handleClose() {
      this.dialogVisible = false;
    },
    preview() {
      var vm = this;
      vm.personLevel = localStorage.getItem("level");
      var data = new Date(new Date().setHours(0, 0, 0, 0));
      vm.start =
        data.getFullYear() + "-" + (data.getMonth() + 1) + "-" + data.getDate();
      // console.log(vm.start);
      this.dialogVisible = true;
      this.$nextTick(() => {
        if (!this.isCanvas) {
          // 只绘画一次
          this.isCanvas = true;
          // this.getChapter();
        }
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.pass == "dmsd123") {
            this.ruleForm.pass = "";
            this.$router.push("/personManagement");
            this.$message({
              message: "密码校验成功",
              type: "success",
            });
            this.dialogFormVisible = false;
          } else {
            this.$message.error("密码错误");
          }
        } else {
          return false;
        }
      });
    },
    goto() {
      this.ruleForm.pass = "";
      this.dialogFormVisible = false;
      // if (this.$route.query.goindex === "true") {
      //   this.$router.push("/");
      // } else {
      //   this.$router.back(-1);
      // }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    // 用户名下拉菜单选择事件

    handleCommand(command) {
      if (command == "loginout") {
        localStorage.clear();

        this.$router.push("/");

        return;
      }

      if (command == "changePassword") {
        this.$router.push("/ChangePassword");

        return;
      }

      if (command == "personManagement") {
        if (this.$route.path != "/personManagement") {
          this.dialogFormVisible = true;
        }
        return;
      }
    },

    // 查询个人成绩
    selectPersonResult(page, size) {
      var vm = this;
      var userId = localStorage.getItem("userid");
      vm.$axios
        .get("/personResult/selectPersonIntegral/" + userId)
        .then(function (response) {
          if (response.data.code === "0000") {
            // if(vm.promotion<60){
            //   vm.level = "小白键盘手";
            // }else{
            // vm.level = response.data.data.level;

            // }
            vm.progressGrade = response.data.data.progressGrade;
            vm.teamGrade = response.data.data.teamGrade;
            vm.totalGrade = response.data.data.totalGrade;
            //将个人积分和小组积分存到localStorage中
            localStorage.setItem("totalGrade", vm.totalGrade);
            localStorage.setItem("teamGrade", vm.teamGrade);
          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        });
    },
    selectLevel() {
      var vm = this;
      var userCode = localStorage.getItem("usercode");
      vm.$axios
        .get("personResult/selectGradeAndNum" + "?" + "userCode=" + userCode)
        .then(function (response) {
          if (response.data.code == "0000") {
            // if(vm.promotion<60){
            //   vm.level = "小白键盘手";
            // }else{
            vm.level = response.data.data.levelName;
            // console.log(vm.level);
            if (vm.level == "小白键盘手") {
              vm.photo = 1;
            } else if (vm.level == "初级键盘手") {
              vm.photo = 2;
            } else if (vm.level == "高级键盘手") {
              vm.photo = 3;
            } else if (vm.level == "键盘侠") {
              vm.photo = 4;
            }
            // console.log(vm.photo);
            // }
            // vm.level = response.data.data.levelName;
            vm.levelNum = response.data.data.levelNum;
            localStorage.setItem("level", vm.level);
            localStorage.setItem("levelNum", vm.levelNum);

            //获取积分规则中的积分
            vm.$axios
              .get("/ty_integral_rule/selectIntegralRuleByLevel/" + vm.level)
              .then(function (response) {
                if (response.data.code === "0000") {
                  let level = localStorage.getItem("level");
                  vm.smallSource = response.data.data[0].smallSource;
                  vm.integralReward = response.data.data[0].integralReward;
                  // console.log(vm.integralReward);
                  if (level == vm.smallSource) {
                    localStorage.setItem("integralReward", vm.integralReward);
                  }
                }
              });

            // 奖状自动弹出
            if (vm.levelNum == 1) {
              // 后端加积分
              vm.userCode = localStorage.getItem("usercode");
              vm.smallSource = localStorage.getItem("level");
              // console.log(vm.smallSource);
              // console.log(vm.userCode);
              vm.$axios
                .get(
                  "/ty_person_integral/selectLevelByUserCode/" +
                    vm.userCode +
                    "/" +
                    vm.smallSource
                )
                .then(function (response) {
                  if (response.data.code === "0000") {
                    // console.log("数据库中有证书积分");
                  } else if (response.data.code === "1111") {
                    // console.log("数据库中没有证书积分，进行加积分");
                    vm.preview();
                    // var vm = this;
                    // 后端加积分
                    var body = {
                      userCode: localStorage.getItem("usercode"),
                      userName: localStorage.getItem("username"),
                      integralSource: "荣誉证书",
                      smallSource: localStorage.getItem("level"),
                      integralReward: localStorage.getItem("integralReward"),
                    };
                    // console.log(body);
                    vm.$axios
                      .post("ty_person_integral/insertIntegral", body)
                      .then(function (response) {
                        if (response.data.code == "0000") {
                          // console.log("积分添加成功");
                          vm.$message({
                            message: "积分添加成功",
                          });
                        } else {
                          // console.log("积分添加失败");
                          vm.$message({
                            message: "积分添加失败",
                            type: "warning",
                          });
                        }
                      });
                  }
                });
            }
          }
        });
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
          // IE11

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
.photoImg {
  /* float: left; */
  width: 30px;
  height: 30px;
  /* margin-left: -7%;
  margin-top: -4%; */
}
.header {
  position: fixed;

  box-sizing: border-box;

  width: 100%;

  height: 60px;
  z-index: 5;
  font-size: 22px;

  color: #fff;
}

.collapse-btn {
  float: left;

  padding: 0 21px;

  cursor: pointer;

  line-height: 70px;
}

.header .logo {
  float: left;

  width: 250px;

  line-height: 70px;
}

.header .logo1 {
  padding-left: 1%;

  float: left;

  width: 680px;

  line-height: 70px;

  font-size: 16px;
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

.btn-bell-badge {
  position: absolute;

  right: 0;

  top: -2px;

  width: 8px;

  height: 8px;

  border-radius: 4px;

  background: #f56c6c;

  color: #fff;
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

.el-dropdown-link1 {
  padding-left: 0.8%;

  color: yellow;

  cursor: pointer;

  font-weight: bold;
}

.el-dropdown-menu__item {
  text-align: center;
}

::v-deep .el-dialog__body {
  padding: 0px;
  display: flex;
  justify-content: center;
}
#pdfDom {
  /* 要想pdf周边留白，要在这里设置 */
  padding: 20px;
  width: 750px;
}
.proBox {
  /* 奖状的模板 */
  background: url("~@/assets/img/zs.jpg") no-repeat;

  background-size: cover;
  width: 750px;
  height: 525px;
  padding: 90px 94px;
  box-sizing: border-box;
  margin: 0 auto;
  position: relative;
  color: #000;
  font-family: SimSun;
}
.tit {
  color: #cf0c0c;
  font-size: 36px;
  font-weight: 700;
  position: relative;
  top: -6px;
  left: 8px;
  letter-spacing: 20px;
  font-family: STHeiti;
  margin: 20px 0;
}
.proid {
  text-align: right;
  margin: 0;
  font-weight: 500;
}

.con {
  font-size: 20px;
  font-weight: 700;
  text-align: left;
  margin: 10px 0;
  line-height: 32px;
  text-indent: 2em;
}
.con-text {
  font-size: 20px;
  font-weight: 700;
  text-align: left;
  margin: 10px 0;
  line-height: 32px;
  text-indent: 5em;
}
.con-name {
  font-family: 华文行楷, STXingkai;
  border-bottom: 2px solid #000;
}
.con-height {
  font-family: 华文行楷, STXingkai;
  font-weight: 700;
  font-size: 2em;
}
.con-unit {
  font-size: 18px;
  font-weight: 700;
  position: absolute;
  right: 130px;
  bottom: 100px;
  text-align: center;
  letter-spacing: 3px;
}
.con-unit p {
  margin: 5px 0;
}
.con-footer {
  font-size: 18px;
  font-weight: 700;
  position: absolute;
  bottom: 45px;
  left: 0;
  right: 0;
  text-align: center;
}
.chapter {
  border-radius: 50%;
  position: absolute;
  bottom: 75px;
  right: 134px;
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
  margin-right: 160px;
  overflow: hidden;
}
</style>


