<template>
  <div>
    <el-card class="TeamCard">
      <div slot="header" class="clearfix">
        <span style="font-weight:bold">{{teamName}}</span>
        <!-- <span style="font-weight:bold" class="tips">温馨提示：点击队友头像可以删除误操作进入此房间的队友</span> -->
        <span style=" font-weigh: bold; font-size: 25px; 
        display: block; margin-left: 45%;color: #ff9800;"
        >{{ deadTime }}：{{ seconds }}</span>
        <el-button type="primary" round class="createTeamBtn" @click="breakBattleTeam">解散战队</el-button>
      </div>
      <div>
        <span style="font-weight:bold;margin-left:1.5%;">邀请码:&nbsp;{{teamNo}}</span>
        <div>
          <el-container style="height:400px;">
            <el-main>
              <div style="margin-top:2.5%;">
                <div class="TeamMember" v-for="(item,index) in teamMemberList" :key="index">
                  <img :src=" 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" @click="isDelete(index)" class="photo"/>
                  <!-- <el-avatar
                    src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                    :size="100" @click="isDelete"
                  ></el-avatar> -->
                  <p style="margin-left:21.4%">{{item.userName}}</p>
                  <!-- <img v-if="num==1" class="teamImg" src="../../assets/img/img.jpg" title="小白键盘手"/> 
                  <img v-if="num==2" class="teamImg" src="../../assets/img/third.png" title="初级键盘手"/> 
                  <img v-if="num==3" class="teamImg" src="../../assets/img/second.png" title="高级键盘手"/> 
                  <img v-if="num==4" class="teamImg" src="../../assets/img/first.png" title="键盘侠"/>  -->
                </div>
              </div>
            </el-main>
          </el-container>
          <el-button type="warning" round class="beginBtn" @click="beginBattle">开始助力</el-button>
        </div>
      </div>

      <el-dialog title="温馨提示：" :visible.sync="deleteDialogVisible" :show-close="false" :close-on-click-modal="false"  :close-on-press-escape="false" width="30%">
        <span style="margin-left: 10%" >真的要删除此队友吗？</span>
        <span slot="footer" class="dialog-footer">
        <el-button @click="(deleteDialogVisible = false), deletePerson()">删除</el-button>
        <el-button @click="(deleteDialogVisible = false), cancle()">取消</el-button>
      </span>
     </el-dialog>

      <el-dialog title="温馨提示：" :visible.sync="centerDialogVisible" :show-close="false" :close-on-click-modal="false"  :close-on-press-escape="false" width="30%">
        <span style="margin-left: 10%" >亲：已经等待好久了，还不准备开始吗？</span>
        <span slot="footer" class="dialog-footer">
        <el-button @click="(centerDialogVisible = false), beginBattle()">开始助力</el-button>
        <el-button @click="(centerDialogVisible = false), again()">我知道了</el-button>
      </span>
     </el-dialog>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      num:"",
      level:"",
      teamNums:"",//房间内人数
      userId:"",
      userName:"",
      // waitTime:0,
      //跳转timer
      jumpTimer:'',
      centerDialogVisible:false,
      deleteDialogVisible:false,
      teamMemberList: [],
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
      //设置定时查询成员的timer
      findTeamMemberTimer: "",
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
          this.beginBattle();
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

    isDelete(index){
      this.deleteDialogVisible=true;
      this.userId=this.teamMemberList[index].userId;
      this.userName=this.teamMemberList[index].userName;
      // console.log(this.userId);
      // console.log(this.userName);
    },
    deletePerson(){
      var vm = this;
      if(this.userName == sessionStorage.getItem("captainName")){
        vm.$message({
              message:"队长不能删除自己哦",
              type: "warning"
            });
      }else{
        vm.$axios
        .delete("/teamResult/QuitBattleTeamPerson?teamName="+ vm.teamName+"&userId=" + vm.userId)
        .then(function(response) {
          if (response.data.code === "0000") {
            vm.$message({
              message:"操作成功",
              type: "info"
            });
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
      }
    },
    //等待计时1分钟
    wait(){
      this.jumpTimer = setInterval(() => {  
         this.centerDialogVisible=true;
      }, 60000)
      // this.waitTime++;
    },
    //我知道了 继续等待
    again(){
      // this.wait();
    },
    //页面加载，接收传过来的参数
    load() {
      // this.wait();
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
      vm.articleName = vm.$route.params.articleName;
      vm.teamName = vm.$route.params.teamName;
      vm.teamNo = vm.$route.params.teamNo;
      vm.inputTime = vm.$route.params.inputTime;
      this.findTeamMemberTimer = setInterval(() => {
      this.findTeamMember();
    }, 2000);
    },
    //查询当前组员信息
    findTeamMember() {
      var vm = this;
      vm.$axios
        .get("/teamResult/findBattleMember?teamName=" + vm.teamName)
        .then(function(response) {
          if (response.data.code === "0000") {
            vm.teamMemberList = response.data.data.isBeforeInBattleTeamModels;
            // console.log(response.data.data.isBeforeInBattleTeamModels);
            vm.teamNums=response.data.data.isBeforeInBattleTeamModels.length;
            // console.log(vm.teamNums);
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
    //解散战队
    breakBattleTeam() {
      var vm = this;
      vm.$axios
        .delete("/teamResult/BreakBattleTeam?teamName=" + vm.teamName)
        .then(function(response) {
          if (response.data.code === "0000") {
            //退出成功，回到邀请助力页面
            vm.$router.push("/witeBattleTeam");
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
    //开始比赛
    beginBattle() {
      if(this.teamNums<=1){//房间内只有一个人 不能开启
          this.$message({
              message: "房间人数不够哦！",
              type: "warning"
            });
      }else{
        clearInterval(this.jumpTimer);
      var vm = this;
      vm.$axios
        .get("/teamResult/BeginBattle?teamName=" + vm.teamName)
        .then(function(response) {
          if (response.data.code === "0000") {
            //开始，进入打字界面
            // vm.$router.push('/typingBattle');
            vm.$router.push({
              name: "TypingBattle",
              params: {
                articleName: vm.articleName,
                teamName: vm.teamName,
                teamNo: vm.teamNo,
                inputTime: vm.inputTime
              }
            });
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
      }
      
    }
  },
  mounted() {
    this.load();
    this.findTeamMember();
    this.deadTimer = setInterval(() => {
          this.showDeadTime();
    }, 1000);
  },
  beforeDestroy() {
    clearInterval(this.findTeamMemberTimer);
    clearInterval(this.deadTimer);
  }
};
</script>
<style scoped>
.photo{
  width: 60%;
  height: 60%;
  /* margin-left: -20%; */
  /* margin-right: -20%; */
}
.breadcrumb {
  margin-top: 2%;
  margin-left: 2%;
}
.TeamCard {
  margin-top: 2%;
  margin-left: 2%;
  width: 95.7%;
}
.tips{
  position: relative;
  margin-left: 38%;
}
.createTeamBtn {
  float:right;
  margin-top: -2.8%;
  position: relative;
  /* margin-left: 26%; */
}
.TeamMember {
  margin-top: 3%;
  margin-left: 6.2%;
  display: inline-block;
}
.beginBtn {
  width: 10%;
  margin-left: 43%;
  margin-top: 0%;
}
.teamImg{
  margin-left:16%;
  width: 50px;
}
</style>
