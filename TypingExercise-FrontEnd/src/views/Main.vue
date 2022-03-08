<!--
 * @Descripttion: 
 * @version: 
 * @Author: 冯佳兴
 * @Date: 2019-09-23 16:38:32
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2020-11-27 10:56:04
 -->
<template>
  <div class=".div">
    <div>
      <!-- 这里是横屏滚动的样式 -->
      <div id="box" ref="box">
        <div
          class="marquee-box"
          ref="marquee"
          @mouseover="menter"
          @mouseleave="mleave"
        >
          <p ref="cmdlist" id="pWidth">
            <!-- <img
              style="width: 28px; height: 28px; margin-right: 10px"
              src="../../src/assets/img/notice.png"
              alt=""
            /> -->
            <span style="color: blue; font-size: 36px; text-align: center">{{
              rollNotice
            }}</span>
          </p>
        </div>
      </div>
    </div>
    <div>
      <el-card shadow="hover" :body-style="{ padding: '0px' }" class="card">
        <!-- 竖屏滚动的样式   -->
        <div class="scroll-wrap">
          <div
            class="scroll-content"
            :style="{ top }"
            @mouseenter="Stop()"
            @mouseleave="Up()"
          >
            <p v-for="item in reportList">
              恭喜{{ item.className }}{{ item.userName }}晋级为{{ item.level }}
            </p>
          </div>
        </div>

        <div class="title">
          <div
            style="
              color: white;
              font-size: 40px;
              text-align: center;
              margin-top: 2%;
            "
          >
            打字练习
          </div>
          <div
            style="
              color: white;
              font-size: 20px;
              text-align: center;
              margin-top: 2%;
            "
          >
            从大学基础到行业应用，信息技术助力您腾飞
          </div>
        </div>
      </el-card>
    </div>
    <div class="img">
      <img
        src="../../src/assets/img/personal.png"
        @click="personal()"
        class="personal"
        alt
      />
      <img
        src="../../src/assets/img/keyboard.png"
        @click="keyboard()"
        class="keyboard"
        alt
      />
      <img
        src="../../src/assets/img/team.png"
        @click="team()"
        class="team"
        alt
      />
    </div>
    <!-- 正常的普通的公告  -->
    <el-dialog
      title=""
      :visible.sync="successdialog"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      width="60%"
      center
    >
      <div class="proBox">
        <span class="tit">{{ pop }}</span> <br />
        <br />
        <span slot="footer" class="dialog-footer" v-show="timeClock == 1">
          <center>{{ sencond }}秒后自动关闭</center>
        </span>
        <el-button
          @click="goRevive"
          class="dialog-button"
          type="primary"
          :disabled="closeIsDisabled"
          v-show="reviveButton == 1"
          >进入复活赛</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>


<script>
export default {
  name: "Main",
  data() {
    return {
      school:"师范学院",
      remark:"",
      startTime:"",
      endTime:"",
      numRevive: 0, //自己当前参与复活赛的次数
      closeIsDisabled: false,
      promotion: "", //晋级率
      className1: [], //自己班级
      timeClock: 1, //倒计时显示
      reviveButton: 0, //进入复活赛按钮默认不显示
      getTime: "", //获取当前时间
      getDate: "", //获取当前日期
      getDateTime: "", //获取当前日期+时间
      value: 0,
      timer: "", //计时器
      pwidth: 0, //公告文本的宽度
      windowWidth: document.documentElement.clientWidth, // 设备屏幕的宽度
      successdialog: false, //控制弹出
      sencond: 20, //设置初始倒计时
      pop: "", //弹框内容
      rollNotice: "",
      isDisabled: false,
      /////路由带参数跳转用到的变量
      //用于路由的数组名字，没定义不能传参！
      params: [],
      //文章名称
      articleName: "",
      //战队名称
      teamName: "",
      //房间编号
      teamNo: "",
      //测试时间
      inputTime: "",
      //定时查询成员timer
      findTeamMemberTimer: "",
      // reportList: [
      //   { name: '城轨采购网', src: 'http://www.railunique.com' },
      //   { name: '天津地铁电子采购平台', src: 'http://www.railunique.com' },
      //   { name: '南昌地铁', src: 'http://www.railunique.com' },
      //   { name: '南昌地铁', src: 'http://www.railunique.com' },
      //   { name: '兰州地铁招标信息', src: 'http://www.railunique.com' },
      //   { name: '西安公共资源交易中心', src: 'http://www.railunique.com' }
      // ],
      //滚动播报内容list
      reportList: [],
      activeIndex: 0,
      intnum: undefined,
    };
  },
  created() {
    this.getReportList();
    this.ScrollUp();
    this.selectNoticeData();
    this.getCurrentTime(); //获取当前日期时间
    this.selectTeamData(); //查询自己小组的晋级率
    // this.selectRevive(); //查询当天有没有进入复活赛
    // this.selectPopData(); //弹窗信息
  },
  computed: {
    top() {
      return -this.activeIndex * 30 + "px";
    },
  },
  mounted() {
    //判断页面进入情况
    if (window.name === "") {
      // console.log("页面首次被加载")
      // this.successdialog = true;
      this.selectPopData();
      this.selectNoticeData();
      // this.getSencond();
      window.name = "isRefresh";
    } else {
      // console.log("页面被刷新")
    }

    let element = this.$refs.cmdlist;
    this.pwidth = document.defaultView
      .getComputedStyle(element, "")
      .width.split("px");
    this.timer = setInterval(this.clickCommend, 15);
    
  },
  watch: {
    value(newValue, oldValue) {
      let allWidth = parseInt(this.windowWidth) + parseInt(this.pwidth[0]);
      if (newValue <= -allWidth) {
        this.$refs.cmdlist.style.marginLeft = this.windowWidth + "px";
        this.value = 0;
      }
    },
  },

  beforeDestroy() {
    clearInterval(this.timer);
  },
  methods: {
    //进入复活赛
    goRevive() {
      // console.log("点击进入复活赛按钮");
      this.$router.push("/PracticeSettingRevive");
    },

    //查询自己小组的晋级率
    selectTeamData() {
      var vm = this;
      let a = localStorage.getItem("className");
      vm.className1.push(a);

      vm.$axios
        .post("/personResult/selectTeamRanking", this.className1)
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
                localStorage.setItem("promotion", vm.promotion);
                // console.log(vm.promotion);
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

    //获取当前时间并打印
    getCurrentTime() {
      var vm = this;
      let yy = new Date().getFullYear();
      // let mm = (new Date().getMonth() + 1).slice(-2);
      // let dd = (new Date().getDate()).slice(-2);
      let mm = (new Date().getMonth() + 1) < 10 ? "0"+(new Date().getMonth() + 1):(new Date().getMonth() + 1);
      let dd = (new Date().getDate()) < 10 ? "0"+(new Date().getDate()):(new Date().getDate());
      
      let hh = new Date().getHours();
      let mf =new Date().getMinutes() < 10? "0" + new Date().getMinutes(): new Date().getMinutes();
      let ss =new Date().getSeconds() < 10? "0" + new Date().getSeconds(): new Date().getSeconds();
      vm.getDate = yy + "-" + mm + "-" + dd; //获取当前日期
      vm.getTime = hh + ":" + mf + ":" + ss; //获取当前时间

      vm.getDateTime =
        yy + "-" + mm + "-" + dd + " " + hh + ":" + mf + ":" + ss; //获取当前日期+时间
    },
    //滚屏信息
    selectNoticeData() {
      var vm = this;
      vm.noticeName = "公告";
      vm.$axios
        .get("/ty_notice/selectNoticeByName/" + vm.noticeName)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.remark =  response.data.data[0].remark;
            vm.startTime = response.data.data[0].startTime;
            vm.endTime = response.data.data[0].endTime;
            //如果当前时间在教师端设置的公告时间内
            if (vm.getDateTime > vm.startTime && vm.getDateTime < vm.endTime ){
              //如果学生的班级是计算机协会 或者为空为null 不显示滚屏信息
              if(vm.remark == localStorage.getItem("className") || localStorage.getItem("className") == "" || localStorage.getItem("className") == null){
                   vm.rollNotice = "";
              }
              //如果学生的班级不是计算机协会 显示滚屏信息
              else if(localStorage.getItem("className") != "计算机协会"){
                vm.rollNotice = response.data.data[0].notice;
              }
            } 
            //当前时间不在教师端设置的公告时间内
            else {
              // vm.successdialog = false;
              vm.rollNotice = "";
            }

          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        });
    },
    //查询当前有没有参加过复活赛
    // selectRevive() {
    //   var vm = this;
    //   vm.$axios
    //     .get("/personResult/selectByUserId/" + localStorage.getItem("userid"))
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         vm.numRevive = response.data.data.length;
    //         // console.log(vm.numRevive);
    //       } else if (response.data.code === "1111") {
    //         vm.numRevive = 0;
    //       }
    //     });
    // },

    //弹窗信息
    selectPopData() {
      this.getCurrentTime();
      var vm = this;
      var popName = "弹窗";
        vm.$axios
          .get("/ty_notice/selectNoticeByName/" + popName)
          .then(function (response) {
            if (response.data.code === "0000") {
              // console.log(response.data.data[0]);
              vm.pop = response.data.data[0].notice;
              vm.sencond = response.data.data[0].setTime;
              vm.remark =  response.data.data[0].remark;
              vm.startTime = response.data.data[0].startTime;
              vm.endTime = response.data.data[0].endTime;
              //如果当前时间在教师端设置的公告时间内
              if (vm.getDateTime > vm.startTime && vm.getDateTime < vm.endTime ){
                //如果学生的班级是计算机协会 或者为空 为null 不显示弹窗
                if(vm.remark == localStorage.getItem("className") || localStorage.getItem("className") == "" || localStorage.getItem("className") == null){                  
                    vm.successdialog = false;
                }
                //如果学生的班级不是计算机协会 显示弹窗
                else if(localStorage.getItem("className") != "计算机协会"){ 
                  vm.successdialog = true;
                  vm.getSencond();
                }
              } 
              //当前时间不在教师端设置的公告时间内
              else {
                vm.successdialog = false;
              }
            } else if (response.data.code === "1111") {
              vm.$message({
                message: response.data.message,
                type: "warning",
              });
            }
          });
    },
    clickCommend(e) {
      let _this = this;
      this.$nextTick(() => {
        this.value -= 1;
        this.$refs.cmdlist.style.marginLeft =
          _this.windowWidth + this.value + "px";
      });
    },
    menter() {
      clearInterval(this.timer);
    },
    mleave() {
      this.timer = setInterval(this.clickCommend, 20);
    },

    //获取滚动播报内容list
    //查询晋级名单
    getReportList() {
      var vm = this;
      vm.$axios
        .get("/personResult/selectImprovementList")
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.reportList = response.data.data;
            // console.log(vm.reportList);
            // vm.reportList.username = response.data.data.username;
            // vm.reportList.level = response.data.data.level;
            // vm.reportList.className = response.data.data.className;
          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        });
    },
    //滚动播报方法
    ScrollUp() {
      this.intnum = setInterval((_) => {
        if (this.activeIndex < this.reportList.length) {
          this.activeIndex += 1;
        } else {
          this.activeIndex = 0;
        }
      }, 1000);
    },
    Stop() {
      clearInterval(this.intnum);
    },
    Up() {
      this.ScrollUp();
    },
    //弹框倒计时
    getSencond() {
      const that = this;
      const interval = window.setInterval(function () {
        --that.sencond;
        if (that.sencond === 0) {
          that.isDisabled = false;
          window.clearInterval(interval);
          that.successdialog = false;
        }
      }, 1000);
    },

    personal() {
      this.$router.push("/PracticeSetting");
    },
    keyboard() {
      this.$router.push("/keyBoard");
    },
    team() {
      var vm = this;
      vm.userId=localStorage.getItem("userid");
      vm.$axios
        .get("/teamResult/IsBeforeInBattleTeam?userId="+vm.userId)
        .then(function (response) {
          if (response.data.code === "0000") {
            // console.log(response.data);
            //             data: {state: 1, isCaptain: 1, teamName: "9"}
            // isCaptain: 1
            // state: 1
            // teamName: "9"
            // message: "查询成功
            // articleName
            //如果是队长
            if (response.data.data.state === 0) {
              vm.$router.push({
                name: "battleTeamRoomByMember",
                params: {
                  articleName: response.data.data.articleName,
                  inputTime: response.data.data.setTime,
                  teamName: response.data.data.teamName,
                },
              });
              // localStorage.setItem("teamName",response.data)
            } else if (response.data.data.state === 1) {
              vm.$router.push({
                name: "battleTeamRoomByLeader",
                params: {
                  articleName: response.data.data.articleName,
                  inputTime: response.data.data.setTime,
                  teamName: response.data.data.teamName,
                },
              });
            }
          } else if (response.data.code === "500") {
            // vm.$message({
            //   message: response.data.message,
            //   type: "warning",
            // });
          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        })
        .catch(function (error) {
          // console.log("失败");
        });
      this.$router.push("/witeBattleTeam");
    },
  },
};
</script>
<style scoped>
.proBox {
  /* 背景图的模板 */
  background: url("../assets/img/BK.png") no-repeat;
  background-size: cover;
  width: 700px;
  height: 500px;
  padding: 190px 93px;
  box-sizing: border-box;
  margin: 0 auto;
  position: relative;
  color: #000;
  font-family: SimSun;
}
.tit {
  font-size: 20px;
  font-weight: 100;
  letter-spacing: 10px;
  margin: 20px 0px;
}
.dialog-button {
  margin-left: 40%;
  margin-top: 30%;
}
.dialog-footer {
  font-size: 20px;
  font-weight: 100;
  /* margin: 20px 0px; */
  margin-bottom: 5%;
}
#box {
  width: 100%;
  height: 42px;
  position: relative;
}
.marquee-box {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
  background-color: #f8f8f8;
}
#pWidth {
  width: 100%;
  height: 42px;
  padding: 0;
  margin: 0;
  line-height: 42px;
  display: block;
  word-break: keep-all;
  white-space: nowrap;
  overflow: hidden;
  font-family: 微软雅黑;
  fontsize: 14px;
  background-color: #f8f8f8;
}
::-webkit-scrollbar {
  width: 0 !important;
}
::-webkit-scrollbar {
  width: 0 !important;
  height: 0;
}

.scroll-wrap {
  /* background-color:rgb(4, 170, 247); */
  /* border-radius: 25px; */
  position: relative;
  z-index: 2;
  float: left;
  margin-left: 5%;
  /* height: 80%;
    width: 100%; */
  overflow: hidden;
}
.scroll-content {
  position: relative;
  transition: top 0.5s;
}
.scroll-content p {
  /* 每行信息间隔高度 */
  line-height: 30px;
  font-size: 20px;
  color: yellow;
  text-align: center;
}
.title {
  z-index: 1;
  position: relative;
  float: center;
  margin-top: 2%;
  /* margin-left: 13%; */
}
.card {
  position: absolute;
  width: 98.5%;
  height: 50%;
  background: rgb(45, 140, 240);
}
.img {
  /* float: center; */
  margin-left: 42%;
  /* margin-right:20%; */
  margin-top: 13%;
  position: absolute;
}
.personal {
  border-style: solid;
  border-color: white;
  margin-top: 5%;
  text-align: center;
  margin-right: 50px;
}
.keyboard {
  border-style: solid;
  border-color: white;
  margin-top: 5%;
  text-align: center;
  margin-right: 50px;
}
.team {
  border-style: solid;
  border-color: white;
}
.div {
  margin-top: 3%;
  width: 100%;
  height: 100%;
  overflow-y: scroll;
}
</style>


