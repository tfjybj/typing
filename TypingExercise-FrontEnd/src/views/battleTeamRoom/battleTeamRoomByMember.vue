<template>
  <div>
    <el-card class="TeamCard">
      <div slot="header" class="clearfix">
        <span style="font-weight: bold">{{ teamName }}</span>
        <span style=" font-weigh: bold; font-size: 25px; 
        display: block; margin-left: 45%;color: #ff9800;"
        >{{ deadTime }}：{{ seconds }}</span>
        <el-button type="primary" round class="createTeamBtn" @click="quitTeam"
          >退出战队</el-button
        >
      </div>
      <div>
        <span style="font-weight: bold; margin-left: 1.5%"
          >邀请码：{{ teamNum }}</span
        >
        <div>
          <div style="margin-top: 2.5%">
            <div
              class="TeamMember"
              v-for="(item, index) in teamMemberList"
              :key="index"
            >
              <el-avatar
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                :size="100"
              ></el-avatar>
              
              <p style="margin-left: 21.4%">{{ item.userName }}</p>
              <!-- <img v-if="num==1" class="teamImg" src="../../assets/img/img.jpg" title="小白键盘手"/> 
              <img v-if="num==2" class="teamImg" src="../../assets/img/third.png" title="初级键盘手"/> 
              <img v-if="num==3" class="teamImg" src="../../assets/img/second.png" title="高级键盘手"/> 
              <img v-if="num==4" class="teamImg" src="../../assets/img/first.png" title="键盘侠"/>  -->
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      flag:"",//1 不在战队
      teamMemberList: [],
      teamMemberList2:[],
      //房间信息
      //文章标题
      articleName: "",
      //队长名称
      captainName: "",
      //队伍人数
      personNum: "",
      //队伍状态
      status: "",
      //队伍名称
      teamName: "",
      //队伍编号
      teamNum: "",
      //比赛市场
      time: "",
      //定时查看队员timer
      findTeamMemberTimer: "",
      num:"",
      level:"",
      deadTimer: "",//倒计时的计时器
      deadTime: "0",//分钟
      seconds: "15",//秒
    };
  },
  methods: {
    //倒计时方法
    showDeadTime() {
      if (this.seconds === "00") {
        this.deadTime = this.deadTime - 1;
        this.seconds = 15;
      }
      this.seconds = this.seconds - 1;
      if (this.seconds > 0 && this.seconds < 10) {
        this.seconds = "0" + this.seconds;
      }
      if (this.seconds == 0 && this.deadTime - 1 >= 0) {
        this.deadTime = this.deadTime - 1;
        this.seconds = 15;
      }
      if (this.seconds == 0 && this.deadTime == 0) {
        clearInterval(this.deadTimer);
        //判断房间内的人员 如果在3-6之间就可以开始
        if(this.teamNums>2 && this.teamNums<11){
          // this.beginBattle();
        }
        else{
          clearInterval(this.deadTimer);
          this.seconds = 15;
          this.deadTimer = setInterval(() => {
            this.showDeadTime();
          }, 1000);
          this.$message({
              message: "房间内人数较少，请继续等待哦！",
              type: "info"
            });
        }
      }
    },
    //加载房间信息
    load() {
      this.level= localStorage.getItem("level");
      if (this.level==="小白键盘手"){
        this.num=1
      }else if (this.level==="初级键盘手"){
        this.num=2
      }else if (this.level==="高级键盘手"){
        this.num=3
      }else if (this.level==="键盘侠"){
        this.num=4
      }
      var vm = this;
      sessionStorage.setItem("captainName", "123");
      vm.userId=localStorage.getItem("userid");
      vm.$axios
        .get("/teamResult/TeamByUserId"+"?userId="+vm.userId)
        .then(function(response) {
          if (response.data.code === "0000") {
            //存储房间信息
            vm.articleName = response.data.data.articleName;
            //队长名称
            vm.captainName = response.data.data.captainName;
            //队伍人数
            vm.personNum = response.data.data.personNum;
            //队伍状态
            vm.status = response.data.data.status;
            //队伍名称
            vm.teamName = response.data.data.teamName;
            //队伍编号
            vm.teamNum = response.data.data.teamNum;
            //比赛市场
            vm.time = response.data.data.time;
            vm.findTeamMember();
          } else if (response.data.code === "500") {
            vm.$message({
              message: response.data.message,
              type: "warning"
            });
          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning"
            });
          }
        })
        .catch(function(error) {
          // console.log("失败");
        });
    },
    findTeamMember() {
      var vm = this;
      vm.$axios
        .get("/teamResult/findBattleMember?teamName=" + vm.teamName)
        .then(function(response) {
          if (response.data.code === "0000") {
            vm.teamMemberList = response.data.data.isBeforeInBattleTeamModels;
            if (response.data.data.state === 2) {
                //队长解散队伍，回到邀请助力页面
                vm.$router.push("/witeBattleTeam");
              } else if (response.data.data.state === 1) {
                //队长开始，跳转到打字界面
                // vm.$router.push('/typingBattle');
                vm.$router.push({
                  name: "TypingBattle",
                  params: {
                    articleName: vm.articleName,
                    teamName: vm.teamName,
                    teamNo: vm.teamNum,
                    inputTime: vm.time,
                  },
                });
              }
            for(let i=0;i<vm.teamMemberList.length; i++) {
              if (
                localStorage.getItem("username") ==
                vm.teamMemberList[i].userName
              ) {
                vm.flag = 0; //在战队里
                break;
              } else {
                vm.flag = 1; //不在
              }
            }
            if (vm.flag == 1) {
              vm.$router.push("/witeBattleTeam");
              vm.$message({
              message: "队长已将您移出房间，请重新进入其他房间",
              type: "info",
            });
            }
            if (vm.flag == 0) {
              
            }
          } else if (response.data.code === "500") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
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
    },
    //退出战队
    quitTeam() {
      var vm = this;
      vm.userId=localStorage.getItem("userid");
      // console.log("vm.userId");
      vm.$axios
        .delete("/teamResult/QuitBattleTeam"+"?teamName=" + vm.teamName+"&userId="+vm.userId)
        .then(function (response) {
          if (response.data.code === "0000") {
            //退出成功，回到邀请助力页面
            vm.$router.push("/witeBattleTeam");
          } else if (response.data.code === "500") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
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
    },
  },
  mounted() {
    this.load();

    this.findTeamMemberTimer = setInterval(() => {
      this.findTeamMember();
    }, 2000);

    this.deadTimer = setInterval(() => {
          this.showDeadTime();
    }, 1000);
  },
  beforeDestroy() {
    clearInterval(this.findTeamMemberTimer);
    clearInterval(this.deadTimer);
  },
};
</script>
<style scoped>
.breadcrumb {
  margin-top: 2%;
  margin-left: 2%;
}
.TeamCard {
  margin-top: 2%;
  margin-left: 2%;
  width: 95.7%;
}
.createTeamBtn {
  /* margin-left: 88%; */
  float:right;
  margin-top: -2.8%;
  position: relative;
}
.TeamMember {
  margin-top: 3%;
  margin-left: 6.2%;
  display: inline-block;
}
.beginBtn {
  width: 10%;
  margin-left: 46%;
  margin-top: 3%;
}
.teamImg{
  margin-left:16%;
  width: 50px;
}
</style>
