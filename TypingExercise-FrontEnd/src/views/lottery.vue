<template>
  <div id="bg" v-show="lottery">
    <img @click="go" class="pointer" src="../../src/assets/img/pointer.png" />
    <img
      ref="turntable"
      class="turntable"
      src="../../src/assets/img/turntable.png"
    />
    <!-- <h2>累计练习1小时可以抽奖哦！</h2> -->
    <h2>当前可抽奖次数为:{{ goTimes }}</h2>
  </div>
</template>
 
<script>
export default {
  name: "App",
  data() {
    return {
      lottery: true,
      isGo: false, //是否正在执行动画
      oTurntable: "", //执行动画的对象
      randomDeg: 0, //即将旋转的度数
      lastDeg: 0, //上一次旋转的度数
      goTimes: 0, //可抽奖次数
      goTime: "", //
      levelnum: "", //抽奖的积分
      setTime: "", //学生练习时长
      times: 3600, //老师设定的练习时长
      userId: "",
      lotteryNums: "", //总可抽奖的次数
      lotteryNum: "", //已经抽过的次数
      userCode: "", //用户学号
      remark: "", //默认为空
      num: 0, //晋级奖励抽过的次数
      teamName: "", //小组名称
      promotion: "", //小组晋级率
      captainUserCode: "", //队长学号
      flag: "", //记录晋级奖励抽奖次数
    };
  },

  created() {
    this.selectLotteryTime(); //获取已经抽过将的次数
  },
  methods: {
    //晋级奖励抽奖
    promotionLottery() {
      //晋级奖励抽过的次数
      var vm = this;
      vm.userCode = localStorage.getItem("usercode");
      vm.$axios
        .get("lottery/selectLotteryByUserCode/" + vm.userCode)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.num = response.data.data[0].num; //晋级奖励抽过的次数
            // console.log(vm.num);

            vm.goTime = localStorage.getItem("goTimes");
            //1.查询个人称号 查询抽过的次数
            if (localStorage.getItem("level") != "小白键盘手") {
              //不是小白
              if (vm.num == 0) {
                //抽过奖次数为0 就有1次
                vm.goTimes = parseInt(vm.goTime) + 1;
                vm.flag = 1;
                // console.log("可抽奖次数" + vm.goTimes);
              } else if (vm.num == 1) {
                //为1就没有次数
                vm.goTimes = vm.goTime;
                // console.log("可抽奖次数" + vm.goTimes);
              }
              //2.查询是不是组长 是组长  并且晋级率==100
              vm.selectPersonTeam();
              
              if (
                localStorage.getItem("usercode") ==
                  localStorage.getItem("captainUserCode") &&
                localStorage.getItem("promotion") == 100
              ) {
                //是队长
                //查询抽过的次数 1=2  2=0
                // console.log(vm.num);
                if (vm.num == 0 || vm.num == 1) {
                  vm.goTimes = parseInt(vm.goTimes) + 1;
                  vm.flag = 2;
                  // console.log("队长再加1次" + vm.goTimes);
                } else if (vm.num == 2) {
                }
              } else {
                //console.log("不是队长");
              }
            }
            // console.log("晋级奖励抽过的次数"+vm.num);
          } else if (response.data.code === "1111") {
            // vm.$message({
            //   message: "",
            //   type: "info",
            // });
          }
        });
    },
    //查询小组的组长
    selectPersonTeam() {
      var vm = this;
      vm.teamName = localStorage.getItem("teamName");
      vm.$axios
        .get("personTeam/selectPersonTeam/" + vm.teamName)
        .then(function (response) {
          if (response.data.code === "0000") {
            var arr = [];
            for (let i = 0; i < response.data.data.list.length; i++) {
              arr.push(response.data.data.list[i].userCode); //拿到三个人的学号
            }
            var max = arr[0]; //假定第一个就是最大值
            var min = arr[0]; //假定第一个也是最小值
            for (var j = 1; j < arr.length; j++) {
              if (arr[j] > max) {
                //如果比最大还大,他就暂时是最大
                max = arr[j];
              } else if (arr[j] < min) {
                //如果比最小还小,他就暂时是最小
                min = arr[j];
              }
            }
            vm.captainUserCode = min;
            localStorage.setItem("captainUserCode", vm.captainUserCode);
          } else if (response.data.code === "1111") {
            // vm.$message({
            //   message: "",
            //   type: "info",
            // });
          }
        });
    },
    go() {
      if (!this.isGo && this.goTimes > 0) {
        if (this.flag == 1 || this.flag == 2) {
          this.getNumber("0");
        } else {
          this.getNumber("");
        }
        this.flag--;
      } else if (!this.isGo && this.goTimes <= 0) {
        alert("抱歉，您当前没有可抽奖的次数，请再努力练习哦。");
      } else return; //表明正在抽奖，未结束扔点击无效
    },
    getRandom(n, m) {
      //该方法能产生[n,m]之间随机数
      let result = Math.floor(Math.floor(Math.random() * (m - n + 1) + n));
      return result;
    },
    //调后端接口 查询已经抽过奖的次数 lotteryNum .
    selectLotteryTime() {
      var vm = this;
      vm.userCode = localStorage.getItem("usercode");
      vm.$axios
        .get("ty_key/selectTimeByUserCode/" + vm.userCode)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.setTime = response.data.data[0].num; //练习时长
            vm.lotteryNums = parseInt(vm.setTime / vm.times); //总共可以抽奖的次数
            
            //求剩余可抽奖的次数
            vm.$axios
              .get("lottery/selectByUserCode/" + vm.userCode)
              .then(function (response) {
                if (response.data.code === "0000") {
                  vm.lotteryNum = response.data.data[0].num; //已经抽过的次数
                  if (vm.lotteryNums - vm.lotteryNum >= 0) {
                    vm.goTimes = vm.lotteryNums - vm.lotteryNum; //还剩余可抽奖的次数
                    localStorage.setItem("goTimes", vm.goTimes);
                    vm.promotionLottery();
                  } else {
                    vm.goTimes = 0;
                  }
                } else if (response.data.code === "1111") {
                }
              });
          } else if (response.data.code === "1111") {
            // vm.$message({
            //   message: "",
            //   type: "info",
            // });
          }
        });
    },

    getNumber(remark) {
      /*  
        调整中奖概率(让每次旋转前指针都在初始位置，这样才准确)
        想转到第一项，需要转：360/7*0 + 360/7/2;   --->该项为超级大奖奖项
        想转到第二项，需要转：360/7*1 + 360/7/2;
        想转到第三项，需要转：360/7*2 + 360/7/2;
        想转到第四项，需要转：360/7*3 + 360/7/2;
        想转到第五项，需要转：360/7*4 + 360/7/2;
        想转到第六项，需要转：360/7*5 + 360/7/2;
        想转到第七项，需要转：360/7*6 + 360/7/2;   --->该项为未中奖项
      */
      let number = this.getRandom(0, 100);
      let caseNum = "";
      // let remark = this.remark;
      if (number === 0) {
        caseNum = 0; //粗略概率为1%
      } else if (number > 0 && number < 5) {
        caseNum = 1; //粗略概率为5%
      } else if (number >= 5 && number < 10) {
        caseNum = 2; //粗略概率为5%
      } else if (number >= 10 && number < 15) {
        caseNum = 3; //粗略概率为5%
      } else if (number >= 15 && number < 20) {
        caseNum = 4; //粗略概率为5%
      } else if (number >= 20 && number < 25) {
        caseNum = 5; //粗略概率为5%
      } else {
        caseNum = 6; //粗略概率为75%
      }
      switch (caseNum) {
        case 0:
          this.ratating((360 / 7) * 0 + 360 / 7 / 2, "5", remark);
          break;
        case 1:
          this.ratating((360 / 7) * 1 + 360 / 7 / 2, "1", remark);
          break;
        case 2:
          this.ratating((360 / 7) * 2 + 360 / 7 / 2, "4", remark);
          break;
        case 3:
          this.ratating((360 / 7) * 3 + 360 / 7 / 2, "1", remark);
          break;
        case 4:
          this.ratating((360 / 7) * 4 + 360 / 7 / 2, "2", remark);
          break;
        case 5:
          this.ratating((360 / 7) * 5 + 360 / 7 / 2, "3", remark);
          break;
        default:
          this.ratating((360 / 7) * 6 + 360 / 7 / 2, "1", remark);
          break;
      }
    },

    ratating(deg, text, remark) {
      this.goTimes--;
      this.isGo = true;
      let times = this.getRandom(3, 6); //圈数(从用户体验角度考虑，设计随机转3-6圈。最少3圈，最多6圈)

      this.randomDeg = deg + 360 * times; //记录这次要旋转的度数(传来的度数+圈数)
      let realDeg = 360 - (this.lastDeg % 360) + this.lastDeg + this.randomDeg;
      /*上次指针离初始状态的度数 + 上次的度数 + 这次要旋转的度数
      (这样的目的是为了每次旋转都从原点开始，保证数据准确)*/
      this.oTurntable.style.transform = `rotate(${realDeg}deg)`;
      setTimeout(() => {
        this.isGo = false;
        this.levelnum = `${text}`;

        this.$message({
          message: `恭喜抽到学习通${text}积分`,
          type: "success",
        });
        // console.log(this.levelnum);
        // console.log(`抽奖结果为学习通${text}积分`);
        // console.log(`以原点为基准共旋转了${this.randomDeg}度,
        // 			以一圈为基准相对旋转了${this.randomDeg % 360}度,最终结果为学习通${text}积分`);
        this.lastDeg = realDeg; //把这次度数存储起来，方便下一次获取
      }, 3000); //4000ms为css里面写的执行动画的时间
      // console.log(remark);
      //TODO 掉后端接口 存库
      var vm = this;
      var body = {
        level: `${text}`,
        userName: localStorage.getItem("username"),
        userCode: localStorage.getItem("usercode"),
        remark: remark,
      };
      vm.$axios.post("/lottery/insertLottery", body).then(function (response) {
        if (response.data.code === "0000") {
          // console.log(body);
          // vm.$message.success("抽奖结果保存成功");
        } else {
          // vm.$message.error("抽奖结果失败");
          return false;
        }
      });
    },
  },
  mounted() {
    this.oTurntable = this.$refs.turntable;
  },
};
</script>
 
 
<style>
#bg {
  width: 640px;
  height: 600px;
  margin: 0 auto;
  background: url("../../src/assets/img/turntable-bg.jpg") no-repeat;
  position: relative;
}
.pointer {
  /* 背景图片 */
  position: absolute;
  z-index: 10;
  top: 155px;
  left: 247px;
}
.turntable {
  /* 转动的 */
  position: absolute;
  z-index: 5;
  top: 60px;
  left: 122px;
  transition: all 4s; /*动画执行时间为4s */
}
</style>