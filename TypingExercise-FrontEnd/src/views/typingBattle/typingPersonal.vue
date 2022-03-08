<!-- 
 * @Descripttion: 打字结束之后的提示框，再来一次或者是查看个人成绩
 * @version: 
 * @Author: 冯佳兴
 * @Date: 2019-09-25 14:26:49
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2020-11-18 21:30:13
 -->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 68%; margin-left: 16%">
      <div slot="header" class="clearfix">
        <span>
          文章:&nbsp;&nbsp; 
          <span style="color: #3499d0">{{ article }}</span>
          <span style="margin-left: 2%"></span>
          设定时间:
          <span style="color: #3499d0;">{{ deadtime}}分钟</span>
        </span>
      </div>
      <!-- 文章内容 -->
      <div class="mainText" v-for="(item, index) in textList" :key="index">
        <span
          class="testM"
          style="
            font-size: 20px;
            pointer-events: none;
            letter-spacing: 0.5px;
            font-family: 'Courier New', Courier, monospace;
          "
          v-for="(item2, index2) in tempText[index]"
          :key="index2"
          :id="[index + '-' + index2]"
          >{{ item2 }}</span
        >
        <br />
        <br />
        <el-input
          :id="index"
          :maxlength="everyInputMaxLength[index]"
          v-model="tempTextList[index]"
          @input="kissKeyBord(index)"
          class="typingLineStyle"
        ></el-input>
        <br />
        <br />
      </div>
    </el-card>
    <!-- <el-card class="tipTool">
      <div>
        <img
          src="../../assets/clock.jpg"
          style="width: 30%; height: 30%; margin-left: 38%"
        />
      </div>
      <div style="margin-top: 6%">
        <span
          style="
            font-weigh: bold;
            font-size: 25px;
            display: block;
            margin-left: 40%;
            margin-top: 3%;
            color: #ff9800;
          "
          >{{ deadTime }}：{{ seconds }}</span
        >
        <span
          style="
            font-weigh: bold;
            font-size: 10px;
            display: block;
            margin-left: 35%;
            margin-top: 3%;
          "
          >设定时间:{{ time }}分钟</span
        >
        <span
          style="
            font-weigh: bold;
            font-size: 15px;
            display: block;
            margin-left: 10%;
            margin-top: 6%;
          "
          >速度:&nbsp;{{ kissSpeed }} KPM &nbsp;正确率:&nbsp;{{
            rightRate
          }}
          %</span
        >
      </div>
    </el-card> -->
    
    <!-- <el-card class="footPerson" v-if="footPerson">
      <div>
        <span
          style="
            margin-left: 25%;
            font-size: 15px;
            display: block;
            margin-top: 6%;
            color: blue;
          "
          >本班个人top3展示</span
        >
        <span
          style="
            font-weigh: bold;
            text-align: center;
            font-size: 12px;
            display: block;
            margin-left: 6%;
            margin-top: 13%;
          "
        >
          <img
            v-if="ranking1 == 1"
            class="personImg"
            src="../../assets/img/first.png"
          />
          冠军:&nbsp;<span class="font">{{ this.firstPseron }} </span
          >个人积分:&nbsp;{{ this.firstscore }} 分</span
        >
        <span
          style="
            font-weigh: bold;
            text-align: center;
            font-size: 12px;
            display: block;
            margin-left: 6%;
            margin-top: 13%;
          "
        >
          <img
            v-if="ranking2 == 2"
            class="personImg"
            src="../../assets/img/second.png"
          />
          亚军:&nbsp;<span class="font">{{ this.secondPseron }} </span
          >个人积分:&nbsp;{{ this.secondscore }}分</span
        >
        <span
          style="
            font-weigh: bold;
            text-align: center;
            font-size: 12px;
            display: block;
            margin-left: 6%;
            margin-top: 13%;
          "
        >
          <img
            v-if="ranking3 == 3"
            class="personImg"
            src="../../assets/img/third.png"
          />
          季军:&nbsp;<span class="font">{{ this.thirdPseron }} </span
          >个人积分:&nbsp;{{ this.thirdscore }} 分</span
        >
      </div>
    </el-card> -->

    <!-- <el-card class="footTeam" v-if="footTeam">
      <div>
        <span
          style="
            margin-left: 25%;
            font-size: 15px;
            display: block;
            margin-top: 6%;
            color: blue;
          "
          >本班小组top3展示</span
        >

        <span
          style="
            font-weigh: bold;
            text-align: center;
            font-size: 10px;
            display: block;
            margin-left: 6%;
            margin-top: 6%;
          "
        >
          <img
            v-if="ranking1 == 1"
            class="teamImg"
            src="../../assets/img/first.png"
          />
          冠军:&nbsp;{{ this.firstTeamName }} &nbsp;</span
        >
        <br /><span style="text-align: center" class="font2">{{
          this.firstTeamPerson}}</span>
        <span
          style="
            font-weigh: bold;
            text-align: center;
            font-size: 10px;
            display: block;
            margin-left: 6%;
            margin-top: 6%;
          "
        >
          <img
            v-if="ranking2 == 2"
            class="teamImg"
            src="../../assets/img/second.png"
          />
          亚军:&nbsp;{{ this.secondTeamName }} &nbsp;</span
        >
        <br /><span style="text-align: center" class="font2">{{
          this.secondTeamPerson}}</span>
        <span
          style="
            font-weigh: bold;
            text-align: center;
            font-size: 10px;
            display: block;
            margin-left: 6%;
            margin-top: 6%;
          "
        >
          <img
            v-if="ranking3 == 3"
            class="teamImg"
            src="../../assets/img/third.png"
          />
          季军:&nbsp;{{ this.thirdTeamName }} &nbsp;</span
        >
        <br /><span text-align:center class="font2">{{
          this.thirdTeamPerson
        }}</span>
      </div>
    </el-card> -->

    <el-button type="text" @click="centerDialogVisible = true"></el-button>
    <!-- 打字结束的弹窗 -->
    <el-dialog title="打字结束" :visible.sync="centerDialogVisible" width="30%">
      <span style="margin-left: 13%">{{ speedAndRightTime }}</span><br/><br/>
      <!-- <span style="margin-left: 13%">您当前名次为第</span><span style="color: red;">{{numberNum}}</span><span>名,您当前等级为</span><span style="color: red;">{{level}}</span><span>。</span><br/>
      <span style="margin-left: 13%">恭喜您超过了本班内</span><span style="color: red;">{{ percent }}</span><span style="color: red;">%</span><span>的同学。</span><br/>
      <span style="margin-left: 13%">预计再练习</span><span style="color: red;">{{number}}</span><span>次，就可以晋级下一等级哦！</span> -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="(centerDialogVisible = false), again()"
          >再来一次</el-button
        >
        <el-button
          type="primary"
          @click="(centerDialogVisible = false), seeScord()"
          >查看成绩</el-button
        >
      </span>
    </el-dialog>
    <button @click="go"></button>
    <!-- 金手指的弹窗 -->
    <el-button type="text" @click="centerDialogVisible1 = true"></el-button>
    <el-dialog
      title="金手指加成"
      :visible.sync="centerDialogVisible1"
      width="40%"
    >
      <span>恭喜您获得“金手指”一次，使用金手指可以打字速度加成10%</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="(centerDialogVisible1 = false), again1()"
          >使用金手指</el-button
        >
        <!-- <el-button
          @click="(centerDialogVisible1 = false), seeScord1()"
          >放弃使用</el-button
        > -->
      </span>
    </el-dialog>
    <!-- <button @click="go">点击</button> -->
  </div>
</template>


<script>
export default {
  data() {
    return {
      deadtime:localStorage.getItem("deadTime"),
      classRank:"",//班级排名情况
      number:"",//还需要练习次数
      numberNum:"",//当前班内排名
      percent:"" ,//超过占比百分之纪
      ranking1: "",
      ranking2: "",
      ranking3: "",
      firstTeamName: "",
      secondTeamName: "",
      thirdTeamName: "",
      firstTeamPerson: "",
      secondTeamPerson: "",
      thirdTeamPerson: "",
      footPerson: false, //个人card
      footTeam: false, //班级card
      showCardTimer: "", //显示隐藏的计数器
      firstPseron: "",
      secondPseron: "",
      thirdPseron: "",
      firstscore: "",
      secondscore: "",
      thirdscore: "",
      // firstTeamScore:"",
      // secondTeamScore:"",
      // thirdTeamScore:"",
      centerDialogVisible: false,
      centerDialogVisible1: false,

      textList: [],
      // 文章名
      articlename: "",
      //用于每行input框存放数据
      tempTextList: [],
      //用于分割字符串
      splitTempTextList: [],
      tempText: [],
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
      //当前时间
      nowTime: "",
      //打字速度
      kissSpeed: 0,
      //打字限时时间
      deadTime: "0",
      seconds: "00",
      speedAndRightTime: "",
      time: "",
      userid: "",
      usercode: "",
      username: "",
      //设置退出提示信息框是否显示
      quitMessage: false,
      level: "小白键盘手",
      progressGrade: 0,
      teamGrade: 0,
      totalGrade: 0,
      //第一次个人积分
      firstTotal: "",
      //第一次小组积分
      firstTeam: "",
      //个人积分差值
      totalValue: "",
      //小组积分差值
      teamValue: "",
      num: 0,
    };
  },

  methods: {
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
          }
        });
    },
    selectClassRank() {
      var vm = this;
      let className = localStorage.getItem("className");
      vm.$axios
        .get(
          "/personResult/selectPersonClassProgress/" +
            1 +
            "/" +
            100 +
            "/" +
            className
        )
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.classRank = response.data.data[0];
            // console.log(vm.classRank);
            // console.log(response.data.data[0][35].userName);
            for(let i=0;i<vm.classRank.length;i++){
              if (vm.classRank[i].userName==localStorage.getItem("username") ){
                vm.numberNum = i+1;
              }
            }  
            // console.log(vm.numberNum);//36班内排名
            // console.log(response.data.data[0].length);//36总人数
            vm.percent = parseInt((1- vm.numberNum/response.data.data[0].length)*100)
            // console.log(vm.percent);//占比 百分之0 
          } else if (response.data.code === "1111") {
            vm.$$message({
              // message: "暂无数据",
              type: "warning",
            });
            vm.classRank = "";
          }
        });
    },
    isShowCard() {
      if (this.footPerson == true) {
        this.footPerson = false; //个人card
        this.footTeam = true; //班级card
      } else {
        this.footPerson = true; //个人card
        this.footTeam = false; //班级card
      }
    },
    // showPersonTable() {
    //   var vm = this;
    //   vm.userCode = localStorage.getItem("usercode");
    //   vm.$axios
    //     .get("/personResult/selectClassTop/" + vm.userCode)
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         // console.log(response.data.data);
    //         vm.firstPseron = response.data.data[0].userName;
    //         vm.secondPseron = response.data.data[1].userName;
    //         vm.thirdPseron = response.data.data[2].userName;
    //         vm.firstscore = response.data.data[0].integral;
    //         vm.secondscore = response.data.data[1].integral;
    //         vm.thirdscore = response.data.data[2].integral;
    //         vm.ranking1 = 1;
    //         vm.ranking2 = 2;
    //         vm.ranking3 = 3;
    //       } else if (response.data.code === "1111") {  
    //         vm.footPerson=false; //个人card
    //         vm.footTeam=false;//班级card
    //         clearInterval(vm.showCardTimer);
    //         // vm.$message({
    //         //   message: "暂无top3数据",
    //         //   type: "info",
    //         // });
    //       }
    //     });
    // },
    // showTeamTable() {
    //   var vm = this;
    //   vm.userCode = localStorage.getItem("usercode");

    //   vm.$axios
    //     .get("/personResult/selectTeamTop/" + vm.userCode)
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         vm.firstTeamName = response.data.data[0].teamName;
    //         vm.secondTeamName = response.data.data[1].teamName;
    //         vm.thirdTeamName = response.data.data[2].teamName;
    //         vm.firstTeamPerson = response.data.data[0].userNames;
    //         vm.secondTeamPerson = response.data.data[1].userNames;
    //         vm.thirdTeamPerson = response.data.data[2].userNames;
    //         // console.log(response.data.data);
    //       } else if (response.data.code === "1111") {
    //         vm.footPerson=false; //个人card
    //         vm.footTeam=false; //班级card
    //         clearInterval(vm.showCardTimer);
    //         // vm.$message({
    //         //   message: "暂无top3数据",
    //         //   type: "info",
    //         // });
    //       }
    //     });
    // },
    go() {
      // console.log("进入go方法");
      this.$store.commit("setclicked", {
        clickednum: (this.num += 1),
      });
    },
    seeScord() {
      let levelNum = localStorage.getItem("levelNum");
      // console.log(levelNum);
      if (levelNum % 10 == 0) {
        // console.log("取到余数啦");
        this.centerDialogVisible1 = true;
      } else {
        this.centerDialogVisible1 = false;
        this.$router.push("/personResultChart");
      }
    },
    seeScord1() {
      //跳转到首页
      this.centerDialogVisible1 = false;
      // console.log("跳转到首页");
      this.$router.push("/Main");
    },
    again1() {
      //跳转到金手指的打字页面
      this.centerDialogVisible1 = false;
      this.$router.push("/typingPerson");
    },
    again() {
      let levelNum = localStorage.getItem("levelNum");
      // console.log(levelNum);
      if (levelNum % 10 == 0) {
        // console.log("取到余数啦");
        this.centerDialogVisible1 = true;
      } else {
        location.reload();
      }
    },
    //退出提示信息
    quit() {
      this.$confirm("现在退出将没有记录和成绩，您确定要退出吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          //跳转到主页面
          this.$router.push("/Main");
        })
        .catch(() => {
          //停在当前页
        });
    },
    //退出按钮
    logout() {
      this.quit();
    },
    // 查询个人成绩
    selectPersonResult(page, size) {
      var vm = this;
      var userId = localStorage.getItem("userid");
      vm.$axios
        .get("/personResult/selectPersonIntegral/" + userId)
        .then(function (response) {
          if (response.data.code === "0000") {
            // vm.level = response.data.data.level;
            vm.progressGrade = response.data.data.progressGrade;
            vm.teamGrade = response.data.data.teamGrade;
            vm.totalGrade = response.data.data.totalGrade;
            // console.log(vm.totalGrade+"打完字后返回的的成绩");
            //获取差值
            vm.totalValue = vm.progressGrade; //vm.totalGrade - vm.firstTotal;
            // console.log(vm.totalValue+"进步的成绩");
            vm.teamValue = vm.teamGrade - vm.firstTeam;
            vm.sendMessage();
          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning",
            });
          }
        });
    },
    //调用消息接口
    sendMessage() {
      var vm = this;
      var body = {
        academy: "",
        className: "",
        confirmType: 0,
        eventName: "打字成绩通知",
        eventContent: {
          totalGrade: this.totalGrade,
          totalValue: this.totalValue,
          teamGrade: this.teamGrade,
          teamValue: this.teamValue,
        },
        eventTime: "",
        profession: "",
        pushEndTime: "",
        pushStartTime: "",
        pushType: 0,
        templateId: "EPw-PQknOmrvPDOccr1sJTzNi0FcNlSlPGqtUKB3mmQ",
        userCode: localStorage.getItem("usercode"),
        userId: localStorage.getItem("userid"),
        userName: localStorage.getItem("username"),
      };
      vm.$axios
        .post("http://tfjybj.com/messagepush/message/wechatmessage", body)
        .then(function (response) {
          if (response.data.code === "0000") {
            // vm.$message.success("调用成功");
          } else {
            // vm.$message.error("调用失败");
            return false;
          }
        });
    },
    // 插入成绩
    insertPersonResult() {
      var vm = this;
      var body = {
        articleName: localStorage.getItem("articles"),
        setTime: localStorage.getItem("deadTime"),
        speed: vm.kissSpeed,
        rightRate: vm.rightRate,
        userName: localStorage.getItem("username"),
        userId: localStorage.getItem("userid"),
      };

      // console.log(body);
      vm.number= (10 - localStorage.getItem("levelNum") % 10 -1)
      
      vm.speedAndRightTime =
        "时间：" +
        localStorage.getItem("deadTime") +
        "分钟," +
        "速度：" +
        vm.kissSpeed +
        "KPM" +
        "," +
        "正确率：" +
        vm.rightRate +
        "%";

      vm.$axios

        .post("/personResult/insertPersonResult", body)

        .then(function (response) {
          // console.log(body);

          if (response.data.code === "0000") {
            vm.$message.success("成绩保存成功");
            //调用查看积分方法
            vm.selectPersonResult();
            vm.go();
            // 调用弹框
            vm.centerDialogVisible = true;
          } else {
            vm.$message.error("成绩添加失败");
            return false;
          }
        });
    },

    // 根据文章名字获取文章内容

    selectArticleContent() {
      if (typeof localStorage.getItem("articles") === "undfined") {
        localStorage.getItem("articles", "a girl selling matches");
      } else {
      }

      var vm = this;

      vm.$axios

        .get(
          //"/typArticle/SelectArticleContent/" + localStorage.getItem("articles")

          "/typArticle/SelectArticleContent/a%20girl%20selling%20matches"
        )

        .then(function (response) {
          if (response.data.code === "0000") {
            vm.textList = response.data.data;

            // console.log(vm.textList);
          } else if (response.data.code === "1111") {
            // vm.$message({

            //   message: response.data.message,

            //   type: "warning"

            // });

            selectArticleContentNull();
          }
        });
    },

    //没有文章的时候查询默认的文章

    selectArticleContentNull() {
      var vm = this;

      vm.$axios

        .get("/typArticle/SelectArticleContent/a girl selling matches")

        .then(function (response) {
          if (response.data.code === "0000") {
            vm.textList = response.data.data;

            // console.log(vm.textList);
          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,

              type: "warning",
            });
          }
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

      // for (let index = 0; index < vm.textList.length; index++) {

      //   realTotal = realTotal + vm.everyInputMaxLength[index];

      // }

      for (let index = 0; index < vm.textList.length; index++) {
        // console.log(vm.tempTextList[index].length);

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

      vm.rightRate = parseInt(100 - (vm.rightWords / realTotal) * 100);
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

        clearInterval(this.deadTimer);

        clearInterval(this.timerWpm);

        // 插入成绩

        this.insertPersonResult();
        //调用查看积分方法
        //  this.selectPersonResult();
        //调用发送消息接口
        //  this.sendMessage();
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

      //获取开始打字的时间

      if (this.beginTypeTime.length === 0) {
        this.beginTypeTime = new Date().getTime();

        //开始实时计算打字速度

        this.timerWpm = setInterval(() => {
          this.nowTime = new Date().getTime();

          this.showKissWordWPM();
        }, 1000);

        //开始倒计时剩余时间

        this.deadTimer = setInterval(() => {
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
        } else {
          resultRight = i;

          result = tempIndex + "" + "-" + resultRight + "";

          document.getElementById(result).style.backgroundColor = "red";
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
        // 插入成绩

        clearInterval(this.deadTimer);

        clearInterval(this.timerWpm);

        vm.insertPersonResult();
      }

      this.showRightRate();
    },
  },

  mounted() {
    this.showCardTimer = setInterval(() => {
      this.isShowCard();
    }, 4000);
    //登陆以后自动跳转全屏

    // let element = document.documentElement;

    // this.$refs.fullscreenselect.className='btn-emptyscreen';

    // element.requestFullscreen();

    //handleFullScreen();

    //获取第一次localStorage中的个人积分和小组积分

    this.firstTotal = localStorage.getItem("totalGrade");
    // console.log(firstTotal+"登陆时成绩第一次成绩");

    this.firstTeam = localStorage.getItem("teamGrade");

    //给第一次进入时设置一个默认的文章

    if (localStorage.getItem("articles") != null) {
    } else {
      localStorage.setItem("articles", "a girl selling matches");

      localStorage.setItem("deadTime", 5);
    }

    this.deadTime = localStorage.getItem("deadTime");

    var vm = this;

    // 加载文章内容

    vm.$axios

      .get(
        "/typArticle/SelectArticleContent/" + localStorage.getItem("articles")
      )

      .then(function (response) {
        if (response.data.code === "0000") {
          vm.textList = response.data.data;

          for (var i = 0; i < vm.textList.length; i++) {
            vm.tempTextList.push("");

            vm.tempText.push("");

            //用于记录每行文本的最大长度

            vm.everyInputMaxLength.push(vm.textList[i].length);
          }

          for (var i = 0; i < vm.textList.length; i++) {
            vm.tempText[i] = vm.splitText(vm.textList[i]);
          }
        } else if (response.data.code === "1111") {
          vm.$message({
            message: response.data.message,

            type: "warning",
          });
        }
      });
    //禁止右键弹出菜单
    document.oncontextmenu = function () {
      return false;
    };
  },

  beforeDestroy() {
    clearInterval(this.deadTimer);

    clearInterval(this.timerWpm);

    clearInterval(this.showCardTimer);
  },

  created() {
    var vm = this;

    vm.time = localStorage.getItem("deadTime");

    //用于屏蔽浏览器上的 一些特殊的快捷键

    let self = this;

    let code = 0;

    let code2 = 0;

    document.onkeydown = function (e) {
      let evn = e || event;

      let key = evn.keyCode || evn.which || evn.charCode;

      //用于推个键可以返回上一行--王闯

      if (key == "8" && length == 0 && indexDelete != 0) {
        document.getElementById(indexDelete - 1).focus();

        indexDelete--;
      }

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
            }, 3000);
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
            }, 3000);
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
            }, 3000);
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
            }, 3000);
          }

          return;
        }
      }
    };

    // keyup时及时的 归零

    document.onkeyup = function (e) {
      code = 0;

      code2 = 0;
    };
    // vm.showPersonTable();
    // vm.showTeamTable();
    vm.selectClassRank();
    vm.selectClassName();
  },

  computed: {
    // 获取文章标题

    article() {
      let article = localStorage.getItem("articles");

      return article ? article : this.articlename;
    },

    // 获取设定时间

    Time() {
      let Time = localStorage.getItem("deadTime");

      return Time ? Time : this.deadTime;
    },
  },
};
</script>


<style scoped>
.personImg{
  float: left;
  width: 40px;
  margin-left: -8%;
  margin-top: -4%;
}
.teamImg {
  float: left;
  width: 40px;
  margin-left: -7%;
  margin-top: -4%;
}
.breadcrumb {
  margin-top: 2%;

  margin-left: 2%;
}

.MainCard {
  margin-top: 3% !important;
}

.tipTool {
  margin-top: 2%;

  width: 20.5%;

  margin-left: 1%;

  float: right;

  position: fixed;

  top: 10%;

  right: 5%;
}
.font {
  font-size: 12px;
  text-align: center;
  /* border-bottom: 2px solid #000; */
  color: red;
  font-weight: 700;
}
.font2 {
  display: block;
  text-align: center;
  font-size: 12px;
  /* border-bottom: 1px solid #000; */
  color: red;
  font-weight: 700;
}
.footPerson {
  margin-top: 20%;

  width: 20.5%;
  height: 42%;
  margin-left: 2%;

  float: right;

  position: fixed;

  top: 10%;

  right: 5%;
}
.footTeam {
  margin-top: 20%;

  width: 20.5%;
  height: 42%;
  margin-left: 2%;

  float: right;

  position: fixed;

  top: 10%;

  right: 5%;
}
.testM {
  -moz-user-select: none;

  -webkit-user-select: none;

  -ms-user-select: none;

  -khtml-user-select: none;

  user-select: none;
}
</style>




<style lang="less" >
// 解决输入框无法与上方显示文字对齐且字体一致问题

.typingLineStyle {
  .el-input__inner {
    font-size: 20px;

    letter-spacing: 0.5px;

    font-family: "Courier New";

    padding-left: 0px !important;

    padding-right: 0 !important;
  }
}
</style>


