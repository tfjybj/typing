<template>
  <div>
    <el-card style="margin-top:2.5%;width:68%;margin-left:16%">
      <div slot="header" class="clearfix">
        <span>
          文章:&nbsp;&nbsp;
          <span style="color:#3499d0">{{articleName}}</span>
        </span>
      </div>
      <div class="mainText" v-for="(item,index) in textList" :key="index">
        <span
          style="font-size:20px;letter-spacing:.5px;font-family: 'Courier New', Courier, monospace"
          v-for="(item2,index2) in tempText[index]"
          :key="index2"
          :id="[index+'-'+index2]"
        >{{item2}}</span>
        <br />
        <br />
        <el-input
          :id="index"
          :maxlength="everyInputMaxLength[index]"
          v-model="tempTextList[index]"
          @input="kissKeyBord(index)"
          style="font-size:20px;"
          class="typingLineStyle"
        ></el-input>
        <br />
        <br />
      </div>
    </el-card>



<el-dialog
  title="设定时间已到"
  center
  :visible.sync="isShowScore"
  width="20%"
  style="margin-top:10%"
  >
  <span style="margin-left:15%">时间:{{time-deadTime}} 速度:{{kissSpeed}} 正确率:{{rightRate}}</span>
  <span slot="footer" class="dialog-footer">
    <!-- <el-button type="primary" @click="checkScore" style="width:100%">查看助力结果</el-button> -->
    <span style="margin-left:8%">助力结果正在计算中，请稍等一下哦！</span>
  </span>
</el-dialog>







<!-- 去掉助力页面的倒计时 -->
    <!-- <el-card class="tipTool">
      <div>
        <img src="../../assets/clock.jpg" style="width:40%;height:40%;margin-left:29%" />
      </div>
      <div style="margin-top:8%">
        <span style="margin-left:42%">倒计时</span>
        <span
          style="font-weigh:bold;font-size:25px;display:block;margin-left:40%;margin-top:3%;color:#FF9800"
        >{{deadTime}}:{{seconds}}</span>
        <span
          style="font-weigh:bold;font-size:10px;display:block;margin-left:38%;margin-top:3%"
        >设定时间:{{time}}分钟</span>
        <span
          style="font-weigh:bold;font-size:15px;display:block;margin-left:36%;margin-top:6%"
        >速度:&nbsp;{{kissSpeed}} KPM</span>
        <span
          style="font-weigh:bold;font-size:15px;display:block;margin-left:38%;margin-top:3%"
        >正确率:&nbsp;{{rightRate}} %</span>
      </div>
    </el-card> -->
  </div>
</template>
<script>
var num = 1;
var indexDelete = 0;
var length;
export default {
  data() {
    return {
      isShowScore:false,
      // testList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      textList: [],
      //用于每行input框存放数据
      tempTextList: [],
      //用于分割字符串
      splitTempTextList: [],
      //
      tempText: [],
      activeName: "personalRankList",
      input: "",
      input2: "",
      //记录正确字母的个数
      rightWords: 0,
      //用于记录每行input框的最大长度
      everyInputMaxLength: [],
      //正确率
      rightRate: 0,
      //用于计算速度的timer
      timerWpm: "",
      //用于倒计时的timer
      deadTimer: "",
      //开始打字的时间
      beginTypeTime: "",
      //总timer
      allTimer:"",
      //跳转timer
      jumpTimer:'',
      //当前时间
      nowTime: "",
      //打字速度
      kissSpeed: 0,
      //打字限时时间
      deadTime: "2",
      seconds: "00",

      /////房间信息
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
      //比赛时长
      time: ""
    };
  },
  methods: {
    //跳转到战队内成绩榜
    checkScore(){
      clearInterval(this.jumpTimer);
      this.isShowScro=false;
      localStorage.setItem("teamName",this.teamName);
      this.$router.push('/inBattleTeamRankList');
    },
    //记录成绩
    recordFinalScore() {
      var vm = this;
      localStorage.setItem("setTime",vm.time);
      vm.$axios
        .post("/teamResult/FinalBattle", {
          articleName: vm.articleName,
          realTime: vm.time-vm.deadTime,//+''+':'+vm.seconds+'',
          rightRate: vm.rightRate,
          score: vm.rightRate*0.9+vm.kissSpeed*0.1,
          setTime: vm.time,
          speed: vm.kissSpeed,
          teamName: vm.teamName,
          userName: localStorage.getItem("username"),
          userId:localStorage.getItem("userid")
        })
        .then(function(response) {
          if (response.data.code === "0000") {
            vm.isShowScore=true;
        vm.jumpTimer = setInterval(() => {
          
          vm.checkScore();


         }, 6000);

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
    loadBattleInfo() {
      var vm = this;
      vm.articleName = vm.$route.params.articleName;
      vm.teamName = vm.$route.params.teamName;
      vm.teamNum = vm.$route.params.teamNo;
      vm.time = vm.$route.params.inputTime;
      vm.deadTime = vm.time;

      //根据文章标题查询文章内容
      vm.$axios
        .get("/typArticle/SelectArticleContent/" + vm.articleName)
        .then(function(response) {
          if (response.data.code === "0000") {
            //加载文章信息
            vm.textList = response.data.data;

            //分割并显示所获得文本信息

            for (var i = 0; i < vm.textList.length; i++) {
              vm.tempTextList.push("");
              vm.tempText.push("");
              //用于记录每行文本的最大长度
              vm.everyInputMaxLength.push(vm.textList[i].length);
            }
            for (var i = 0; i < vm.textList.length; i++) {
              vm.tempText[i] = vm.splitText(vm.textList[i]);
            }
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
    splitText(text) {
      this.splitTempTextList = [];
      for (var i = 0; i < text.length; i++) {
        this.splitTempTextList.push(text.substr(i, 1));
      }
      return this.splitTempTextList;
    },
    //实时计算正确率
    showRightRate() {
      //计算正确字符数量
      var vm = this;
      this.rightWords = 0;
      var result = "";
      var realTotal = 0; //文章字符长度

      for (let index = 0; index < vm.textList.length; index++) {
        //consoleconsole.log(vm.tempTextList[index].length);
        
         realTotal = realTotal + vm.tempTextList[index].length;      
      }

      for (let i = 0; i < this.textList.length; i++) {
        for (let j = 0; j < this.everyInputMaxLength[i]; j++) {
          result = i + "" + "-" + j + "";
          if (document.getElementById(result).style.backgroundColor === "red") {
            this.rightWords++;
          }
        }
      }
      //vm.rightRate = parseInt(100-vm.rightWords / (ealTotal + 1) * 100);
      vm.rightRate = parseInt(100 - ((vm.rightWords) / (realTotal+1)) * 100);
      vm.rightWords = 0;
      vm.realTotal = 0;
    },
    //计算当前打字速度
    showKissWordWPM() {
      var totalWord = 0;
      //用于计算每行输入的字符个数
      var everLineLength = 0;
      var vm = this;
      for (let i = 0; i < this.textList.length; i++) {
        //计算总字数
        vm.everLineLength = this.tempTextList[i];
        if (vm.everLineLength.length > 0) {
          totalWord = totalWord + vm.everLineLength.length;
        }
      }
      vm.kissSpeed = parseInt(
        (totalWord / (vm.nowTime - vm.beginTypeTime)) * 60000
      );
    },
    //倒计时方法，更改时间显示
    showDeadTime() {
      if (this.seconds === "00") {
        this.deadTime = this.deadTime - 1;
        this.seconds = 60;
      }
      this.seconds = this.seconds - 1;
      if (this.seconds > 0 && this.seconds < 10) {
        this.seconds = "0" + this.seconds;
      }
      if (this.seconds == 0 && this.deadTime - 1 >= 0) {
        this.deadTime = this.deadTime - 1;
        this.seconds = 60;
      }
      if (this.seconds == 0 && this.deadTime == 0) {
        // alert("嘿嘿，倒计时结束了！");
        // clearInterval(this.deadTimer);
        // clearInterval(this.timerWpm);
        clearInterval(this.allTimer);


      this.recordFinalScore();
                                                                
      }
    },
    // 用户往输入框开始输入数据
    kissKeyBord(index) {
      var vm = this;
      var tempIndex = index;
      this.input = this.tempTextList[index];
      this.input2 = this.textList[index];
      var result = "";
      var resultRight = "";
      var i = 0;
      var a = "";
      var b = "";
      indexDelete = index;
      length = this.input.length;

      //获取开始打字的时间
      if (this.beginTypeTime.length === 0) {
        this.beginTypeTime = new Date().getTime();

        // //开始实时计算打字速度
        // this.timerWpm = setInterval(() => {
        //   this.nowTime = new Date().getTime();
        //   this.showKissWordWPM();
        // }, 1000);

        // //开始倒计时剩余时间
        // this.deadTimer = setInterval(() => {
        //   this.showDeadTime();
        // }, 1000);

        this.allTimer = setInterval(() => {
          this.nowTime = new Date().getTime();
          this.showKissWordWPM();
           this.showDeadTime();
         }, 1000);
        
      }

      while (i < this.input.length) {
        a = this.input.substr(i, 1);
        b = this.input2.substr(i, 1);
        if (a === b) {
          resultRight = i;
          result = tempIndex + "" + "-" + resultRight + "";
          document.getElementById(result).style.backgroundColor = "white";
          // document.getElementById(result).style.color = "blue";

        } else {
          resultRight = i;
          result = tempIndex + "" + "-" + resultRight + "";
          document.getElementById(result).style.backgroundColor = "red";
          // document.getElementById(result).style.backgroundColor = "white";
        }
        i++;
      }

      //用于退格键删除文字后消除红色
      var j = this.input.length;
      while (j < this.input2.length) {
        result = tempIndex + "" + "-" + j + "";
        if (document.getElementById(result).style.backgroundColor === "red") {
          document.getElementById(result).style.backgroundColor = "white";
        }
        // if (document.getElementById(result).style.color === "blue") {
        //   document.getElementById(result).style.color = "black";
        // }
        
        j++;
      }

      //用于自动换行，通过计算输入的文本长度是否等于文本框整个的长度判断，如果相等则获取下一个文本框的id同时获取焦点达到自动换行的目的
      if (
        this.input.length === vm.everyInputMaxLength[index] &&
        index + 1 < this.textList.length
      ) {
        document.getElementById(index + 1).focus();
      }


      //用于判断是否全文输入完毕
      var inputTotal = 0; //输入字符长度
      var realTotal = 0; //文章字符长度
      var everLineLength = 0; //每行输入的长度
      for (let index = 0; index < vm.textList.length; index++) {
        this.everLineLength = this.tempTextList[index];
        inputTotal = inputTotal + this.everLineLength.length;
        realTotal = realTotal + vm.everyInputMaxLength[index];
      }
      //判断是否整篇文章打完
      if (inputTotal === realTotal) {
        clearInterval(this.allTimer);
        // clearInterval(this.deadTimer);
        // clearInterval(this.timerWpm);
        this.recordFinalScore(); 
        
      }
      //注意，这个方法是用来显示正确率的！！！ 
      this.showRightRate();
    }
    //加载比赛信息 文章 时间
  },
  mounted() {
    this.loadBattleInfo();





      /////打字界面初始化成绩
      var vm = this;
      // console.log(localStorage.getItem("userid"));
      vm.$axios
        .post("/teamResult/BattleInfo", {
          articleName: vm.articleName,
          setTime: vm.time,
          teamName: vm.teamName,
          userName: localStorage.getItem("username"),
          userId:localStorage.getItem("userid")
        })
        .then(function(response) {
          if (response.data.code === "0000") {
            
            // consoleconsole.log("加载成功");
            
            
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




    //禁止右键弹出菜单
    document.oncontextmenu = function() {
      return false;
    };
  },
  beforeDestroy() {

    clearInterval(this.allTimer);
    // clearInterval(this.deadTimer);
    // clearInterval(this.timerWpm);
  },

  created() {
    var vm = this;
    //vm.time = localStorage.getItem("deadTime");
    //用于屏蔽浏览器上的 一些特殊的快捷键
    let self = this;
    let code = 0;
    let code2 = 0;
    document.onkeydown = function(e) {
      let evn = e || event;
      let key = evn.keyCode || evn.which || evn.charCode;

      //用于推个键可以返回上一行--王闯
      // if (key == "8" && length == 0 && indexDelete != 0) {
      //   document.getElementById(indexDelete - 1).focus();
      //   indexDelete--;
      // }

      //F5
      //通过缓存中有没有用户名对用户状态进行判断
      if (localStorage.getItem("username") == undefined) {
        //用户为登录状态，没有禁止事件
      } else {
        if (key === 116) {
          e.preventDefault(); //禁止默认事件
          //vm.$message.error("骚年，打字中不允许刷新界面！");
           if (num == 2) {
            } else {
              num = 2;
              vm.$message.error("骚年，打字中不允许刷新界面！");
              setTimeout(() => {
                num = 0;
              }, 3000 );
            }
        }
        // ctrl +F5
        if (e.ctrlKey && key === 116) {
          e.preventDefault(); //禁止默认事件
          //alert("骚年，打字中不允许刷新界面！");
          //vm.$message.error("骚年，打字中不允许刷新界面！");
           if (num == 2) {
            } else {
              num = 2;
              vm.$message.error("骚年，打字中不允许刷新界面！");
              setTimeout(() => {
                num = 0;
              }, 3000 );
            }
        }
        // ctrl + C
        if (e.ctrlKey && key === 67) {
          e.returnValue = false;
          //alert("骚年，不允许复制文本！");
          //vm.$message.error("骚年，不允许复制文本！");
           if (num == 2) {
            } else {
              num = 2;
              vm.$message.error("骚年，不允许复制文本！");
              setTimeout(() => {
                num = 0;
              }, 3000 );
            }
        }
        // ctrl + v
        if (e.ctrlKey && key === 86) {
          e.returnValue = false;
          //vm.$message.error("骚年，不允许粘贴文本！");
          //alert("骚年，不允许粘贴文本！");
           if (num == 2) {
            } else {
              num = 2;
              vm.$message.error("骚年，不允许粘贴文本！");
              setTimeout(() => {
                num = 0;
              }, 3000 );
            }
          return;
      }
      }
    };
    // keyup时及时的 归零
    document.onkeyup = function(e) {
      code = 0;
      code2 = 0;
    };
  }
};
</script>
<style scoped>
.breadcrumb {
  margin-top: 2%;
  margin-left: 2%;
}
.MainCard {
  margin-top: 3% !important;
}
.tipTool {
  margin-top: 1.1%;
  width: 20.5%;
  margin-left: 2%;
  float: right;
  position: fixed;
  top: 12.5%;
  right: 5%;
}
/* .el-input__inner {
  padding-left: 0px !important;
  padding-right: 0 !important;
  letter-spacing: 0.5px;
  font-family: "Courier New", Courier, monospace;
} */
</style>

<style lang="less" >
// 解决输入框无法与上方显示文字对齐且字体一致问题
 .typingLineStyle{
    .el-input__inner {
      font-size:20px;
      letter-spacing: 0.5px;
      font-family: "Courier New";
      padding-left: 0px !important;
      padding-right: 0 !important;
    }
}
</style>

