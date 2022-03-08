<!--
 * @Descripttion: 
 * @version: 
 * @Author: 冯佳兴
 * @Date: 2019-09-26 08:33:59
 * @LastEditors: sueRimn
 * @LastEditTime: 2019-09-26 08:33:59
 -->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <div slot="header" class="clearfix">
        <span>助力结束</span>
        <!-- <el-button type="primary" @click="select" class="select">查看助力结果</el-button>
        <span>温馨提示:所有队友都打完字后再点击查看助力结果，否则会助力失败哦。</span> -->
      </div>

      <el-dialog title="是否继续去助力其他人" :visible.sync="centerDialogVisible" width="30%">
      <span slot="footer" class="dialog-footer">
        <el-button @click="(centerDialogVisible = false), again()">去助力</el-button>
        <el-button @click="(centerDialogVisible = false), goMain()">去主页</el-button>
      </span>
     </el-dialog>

      <div style="width: 100%">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column
            prop="rank"
            label="序号"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="userName"
            label="姓名"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="articleName"
            label="文章名称"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="setTime"
            label="设定时间"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="speed"
            label="速度"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="rightRate"
            label="正确率"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="score"
            label="成绩"
            style="width: 10%"
          ></el-table-column>
          <el-table-column
            prop="improvedScore"
            label="助力结果"
            style="width: 10%"
            class="improved"
          ></el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      num: 0,
      centerDialogVisible: false,
      rate: "",
      speedImproved: "",
      sum: 0,
      findScoreTimer: "",
      goTimer:"",
      tableData: [],
      tableDataimproved:[],
      activeName: "personalRankList",
      number:"",//获取队员序号
      captainscore:0,//队长分数
      memberscore:0,//队员分数

      userCode:localStorage.getItem("usercode"),
      numTran: 0,
      IntegralRule:"",//积分规则数据
      integralSource:"每日打卡",//积分来源
      smallSource:"邀请助力",//来源细分
      integralReward:3,//积分奖励几分
      numberTran:0,//练习次数
      dateNow:"",
      flag:false 
    };
  },
  methods: {
    //判断今天是否保存过
    // selectIsSign(){
    //   this.getDate();
    //   var vm = this;
    //   vm.$axios
    //     .get("/ty_person_integral/selectByUserCode/" + vm.userCode +"/" +vm.integralSource + "/"+ vm.smallSource )
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         for (let i = 0; i < response.data.data.length; i++) {
    //           if (response.data.data[i].createDay==vm.dateNow){
    //               vm.flag=true;
    //           }
    //         }
    //         if(vm.flag==true){
    //           //今天已经保存过了
    //           // vm.$message.success("已经保存过了");
    //         }else{
    //           //今天没有保存过
    //           vm.selectNums();
    //         }
    //       } else if (response.data.code === "1111") {
    //         //第一次保存
    //         vm.selectNums();
    //       }
    //     });
    // },
    //查询当天练习的次数
    // selectNums(){
    //   this.getDate();
    //   var vm = this;
    //   vm.$axios
    //     .get("/teamDetailResult/selectByTimeUserCode/" + vm.userCode +"/" +vm.dateNow )
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         vm.numberTran=response.data.data[0].num //次数
    //         if(vm.numberTran>=3){
    //           //达到练习标准 
    //           vm.keyBoardSign();
    //         }else{
    //           //还没到达练习标准
    //         }
    //       } else if (response.data.code === "1111") {
    //         //还没到达练习标准
    //       }
    //     });
    // },
    //获取当前日期
    getDate(){
      const nowDate = new Date();
      const date = {
          year: nowDate.getFullYear(),
          month: nowDate.getMonth()+1,
          date: nowDate.getDate(),
      }
      const newmonth = date.month>=10?date.month:'0'+date.month
      const day = date.date>10?date.date:'0'+date.date
      this.dateNow = date.year + '-' + newmonth + '-' + day
    },
    //查询邀请助力添加的积分数
    selectIntegralRule() {
      // console.log("查询邀请助力添加的积分数");
      var vm = this;
      vm.$axios
        .get("/ty_integral_rule/selectIntegralRule/" + vm.page + "/" + vm.size)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.IntegralRule = response.data.data.list;
            for (let i = 0; i < response.data.data.list.length; i++) {
              if(response.data.data.list[i].smallSource === vm.smallSource){
                vm.number = i;
              }
            }
            vm.integralReward=response.data.data.list[vm.number].integralReward;
          } else if (response.data.code === "1111") {
            vm.$message({
              message:"暂无数据",
              type: "warning",
            });
          }
        });
    },
    //邀请助力练习加积分
    // keyBoardSign(){
    //   // console.log("邀请助力练习加积分")
    //   var vm = this;
    //   var body = {
    //     userCode:localStorage.getItem("usercode"),
    //     integralSource: vm.integralSource,//积分来源
    //     smallSource: vm.smallSource,//来源细分
    //     integralReward: vm.integralReward,//积分奖励几分
    //     userName:localStorage.getItem("username")
    //   };
    //   vm.$axios
    //     .post("ty_person_integral/insertIntegral", body)
    //     .then(function (response) {
    //       if (response.data.code == "0000") {
    //         vm.$message.success("恭喜您今日已完成 邀请助力3次 任务，并获得学习通"+vm.integralReward+"积分。");
    //       } else {
    //         // vm.$message({
    //         //   message: "积分添加失败",
    //         //   type: "warning",
    //         // });
    //       }
    //     });
    // },



    again(){//去助力页面
      this.$router.push("/witeBattleTeam");//跳转到助力页面
    },
    goMain(){
      this.$router.push("/Main");//跳转到首页
    },
    go(){
      this.centerDialogVisible=true;
    },
    select() {},
    saveScore() {
      var vm=this;
      vm.$axios
        .get(
          "/teamDetailResult/TeamDetailResult?teamName=" +
            localStorage.getItem("teamName")
        )
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.tableDataimproved = response.data.data;
            
            if(sessionStorage.getItem("captainName")=="123") {
              //队员
              for (let i = 0; i < vm.tableDataimproved.length; i++) {
                  if(vm.tableDataimproved[i].userName ==localStorage.getItem("username")){
                        vm.index = i;
                        break;
                  }
              }

              var body = {
                teamId:localStorage.getItem("teamName"),
                userName: localStorage.getItem("username"),
                speed: vm.tableDataimproved[vm.index].speed,
                rightRate: vm.tableDataimproved[vm.index].rightRate,
                setTime:localStorage.getItem("setTime"),
                createTime:new Date(),
                realTime:0,//队员
                articleName: vm.tableDataimproved[vm.index].articleName,
                score:vm.tableDataimproved[vm.index].score,
                userCode:localStorage.getItem("usercode"),
                teamName:localStorage.getItem("teamName"),
                improvedScore:localStorage.getItem("memberscore")
              }
              console.log(body);
              vm.$axios
              .post("/teamDetailResult/insertTeamDetailResult", body)
              .then(function (response) {
                if (response.data.code === "0000") {
                  // vm.selectIsSign();
                  vm.$message.success("保存成功");
                } else {
                  // vm.$message.error("保存失败，请重新按照要求开始");
                }
              });
            }else{
            for (let i = 0; i < vm.tableDataimproved.length; i++) {
              if (vm.tableDataimproved[i].userName ==sessionStorage.getItem("captainName")) {
                vm.number = i;
              }
            }
            //队长   
            if (vm.tableDataimproved[vm.number].userName==sessionStorage.getItem("captainName")) {
              var body = {
                teamId:localStorage.getItem("teamName"),
                userName: localStorage.getItem("username"),
                speed: vm.tableDataimproved[vm.number].speed,
                rightRate: vm.tableDataimproved[vm.number].rightRate,
                setTime:localStorage.getItem("setTime"),
                createTime:new Date(),
                realTime:1,//队长
                articleName: vm.tableDataimproved[vm.number].articleName,
                score:vm.tableDataimproved[vm.number].score,
                userCode:localStorage.getItem("usercode"),
                teamName:localStorage.getItem("teamName"),
                improvedScore:vm.captainscore
              }
              vm.$axios
              .post("/teamDetailResult/insertTeamDetailResult", body)
              .then(function (response) {
                if (response.data.code === "0000") {
                  // vm.selectIsSign();
                  vm.$message.success("保存成功");
                } else {
                  // vm.$message.error("保存失败，请重新按照要求开始");
                }
              });

            }
            }
          }
        });
    },
    load() {
      var vm = this;
      vm.$axios
        .get(
          "/teamDetailResult/TeamDetailResult?teamName=" +
            localStorage.getItem("teamName")
        )
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.tableData = response.data.data;

            for (let i = 0; i < vm.tableData.length; i++) {
              if (vm.tableData[i].userName == sessionStorage.getItem("captainName")) {
                vm.num = i;
              }
            }

            //判断当前等级是初级键盘手，确定升级高级键盘手的助力规则
            if (localStorage.getItem("level") == "初级键盘手") {
              //for循环判断正确率大于97，否则不加分
              for (let index = 0; index < vm.tableData.length; index++) {
                vm.tableData[index].rank = index + 1;
                //判断队长的成绩在130 97以上才可以助力成功，否则助力不成功
                if (
                  vm.tableData[vm.num].speed >= 130 &&
                  vm.tableData[vm.num].rightRate >= 97
                ) {
                  //循环判断所有成员的成绩在97以上，才可以助力成功，否则助力不成功
                  if (vm.tableData[index].rightRate >= 97) {
                    vm.tableData[index].improvedScore =
                      "助力分数：" + parseInt(vm.tableData[index].speed * 0.03);
                      if(vm.tableData[index].userName==localStorage.getItem("username")){
                           vm.memberscore=parseInt(vm.tableData[index].speed * 0.03);
                           localStorage.setItem("memberscore",vm.memberscore);
                      }
                    vm.sum += parseInt(vm.tableData[index].speed * 0.03);
                  }
                } else {
                  //否则助力的分数加0
                  vm.tableData[index].improvedScore = "助力分数：" + 0;
                  vm.memberscore=0;//队员助力分数
                  localStorage.setItem("memberscore",vm.memberscore);
                }
              }
              vm.speedImproved = vm.sum;
              //助力的速度=总的速度-队长本人的助力速度
              // vm.speedImproved = vm.sum - parseInt(vm.tableData[vm.num].speed * 0.03);
              // console.log("助力的速度：" + vm.speedImproved);
            } else {
              //确定升级初级键盘手的规则
              //for循环判断正确率大于96，否则不加分
              for (let index = 0; index < vm.tableData.length; index++) {
                vm.tableData[index].rank = index + 1;
                if (vm.tableData[index].rightRate >= 96) {
                  vm.tableData[index].improvedScore =
                    "助力分数：" + parseInt(vm.tableData[index].speed * 0.05);
                    if(vm.tableData[index].userName==localStorage.getItem("username")){
                           vm.memberscore=parseInt(vm.tableData[index].speed * 0.05);
                           localStorage.setItem("memberscore",vm.memberscore);
                      }
                  vm.sum += parseInt(vm.tableData[index].speed * 0.05);
                  vm.memberscore = parseInt(vm.tableData[index].speed * 0.05);
                } else {
                  vm.tableData[index].improvedScore = "助力分数：" + 0;
                  vm.memberscore=0;//队员助力分数
                  localStorage.setItem("memberscore",vm.memberscore);
                }
              }
              //助力的速度=总的速度-队长本人的助力速度
              vm.speedImproved = vm.sum;
              // vm.speedImproved =vm.sum - parseInt(vm.tableData[vm.num].speed * 0.05);
            }

            if (vm.tableData[vm.num].userName == sessionStorage.getItem("captainName")) {
              //往后端传数据
              var body = {
                articleName: vm.tableData[vm.num].articleName,
                setTime: vm.tableData[vm.num].realTime,
                speed: vm.tableData[vm.num].speed + vm.speedImproved,
                words: vm.speedImproved,
                rightRate: vm.tableData[vm.num].rightRate,
                userName: localStorage.getItem("username"),
                userId: localStorage.getItem("userid"),
              };
               //显示到表格中
            vm.tableData[vm.num].improvedScore =
              "助力后总分：" + (vm.tableData[vm.num].speed + vm.speedImproved)
              vm.captainscore = (vm.tableData[vm.num].speed + vm.speedImproved) //队长分数
            }
                
              vm.$axios
                .post("/personResult/insertPersonResult", body)
                .then(function (response) {
                  if (response.data.code === "0000") {
                    // vm.$message.success("助力完成");
                  } else {
                    // vm.$message.error("助力失败，请重新按照要求开始");
                  }
                });
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

         vm.saveScore();
    },
  },
  mounted() {
    // this.findScoreTimer = setInterval(() => {
    this.load();
    // }, 2000);
    this.goTimer =setInterval(()=>{
      this.go();
    },6000);
  },
  beforeDestroy() {
    clearInterval(this.findScoreTimer);
    clearInterval(this.goTimer);
  },
  created(){
    var vm = this;
    //vm.time = localStorage.getItem("deadTime");
    //用于屏蔽浏览器上的 一些特殊的快捷键
    document.onkeydown = function(e) {
      let evn = e || event;
      let key = evn.keyCode || evn.which || evn.charCode;
      //F5
      //通过缓存中有没有用户名对用户状态进行判断
      if (localStorage.getItem("username") == undefined) {
        //用户为登录状态，没有禁止事件
      } else {
        if (key === 116) {
          e.preventDefault(); //禁止默认事件
           if (num == 2) {
            } else {
              num = 2;
              vm.$message.error("骚年，不允许刷新界面！");
              setTimeout(() => {
                num = 0;
              }, 3000 );
            }
        }
        // ctrl +F5
        if (e.ctrlKey && key === 116) {
          e.preventDefault(); //禁止默认事件
           if (num == 2) {
            } else {
              num = 2;
              vm.$message.error("骚年，不允许刷新界面！");
              setTimeout(() => {
                num = 0;
              }, 3000 );
            }
        }
        // ctrl + C
        if (e.ctrlKey && key === 67) {
          e.returnValue = false;
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
  }
};
</script>
<style scoped>
.improved {
  background-color: red;
}
.breadcrumb {
  margin-top: 2%;
  margin-left: 2%;
}
.MainCard {
  margin-top: 3% !important;
}
.select {
  margin-left: 2%;
  margin-right: 2%;
}
</style>
