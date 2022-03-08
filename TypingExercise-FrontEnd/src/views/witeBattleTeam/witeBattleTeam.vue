<template>
  <div>
    <!-- 这里是横屏滚动的样式 -->
    <!-- <div id="box" ref="box">
      <div
        class="marquee-box"
        ref="marquee"
        @mouseover="menter"
        @mouseleave="mleave"
      >
        <p ref="cmdlist" id="pWidth">
          <span style="color: blue; font-size: 36px; text-align: center"
            >累计练习时长1小时，可以获得1次抽奖机会，奖品有学习通积分不等，快来练习吧！
          </span>
        </p>
      </div>
    </div> -->

    <el-card class="TeamCard">
      <div slot="header" class="clearfix">
        <!-- <span style="font-weight: bold">邀请助力</span> -->
        <span style="font-weight: bold" class="tips">
          tips：每天有<span class="num">1</span
          >次免费邀请别人助力的机会，之后邀请别人助力将消费学习通<span
            class="num"
            >2</span
          >积分哦。
        </span>
        <el-button
          type="primary"
          round
          class="createTeamBtn2"
          @click="battleTeamRoom"
          >随机组队</el-button
        >
        <el-button
          type="primary"
          round
          class="createTeamBtn"
          @click="createBattleTeam"
          >创建战队</el-button
        >
        <el-input
          size="small"
          placeholder="输入邀请码快速进入助力"
          suffix-icon="el-icon-search"
          v-model="input"
          class="searchInput"
          @input="fastJoinBattleTeam"
        ></el-input>
      </div>
      <div>
        <div
          class="TeamInfoCard"
          v-for="(item, index) in battleTeamList"
          :key="index"
        >
          <p class="TeamNo">&nbsp;{{ item.teamNum }}</p>
          <p class="TeamName">{{ item.teamName }}</p>
          <p class="TeamLeader">队长:&nbsp;{{ item.captainName }}</p>
          <p class="TeamMemberNum">人数:&nbsp;{{ item.personNum }}人</p>
          <el-button
            type="primary"
            class="joinBtn"
            @click="joinBattleTeam(index)"
            >进入</el-button
          >
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: 0,
      timer: "", //计时器
      pwidth: 0, //公告文本的宽度
      windowWidth: document.documentElement.scrollWidth, // 设备屏幕的宽度

      userCode: localStorage.getItem("usercode"),
      //战队名称
      inputName: "",
      //选择的文章名称
      articleValue: "",
      //测试时间
      // inputTime: "5",
      //战队号
      teamNo: "",
      //文章名称
      articleName: "",
      //战队名称
      teamName: "",
      //定时获取队伍信息timer
      findBattleTeamCardTimer: "",
      //获取到的准备中的战队信息
      battleTeamList: [],
      //用于存储快速输入的邀请码
      input: "",
      //战队名称
      teamName: "",
      //战队编号
      teamNum: "",
      //队长名称
      captainName: "",
      //战队人数
      personNum: "",
      ///////////用于路由穿餐
      params: [],
      articleName: "",
      inputTime: "",
      datalist: [],
      num: 0,
      IntegralRule: "", //积分规则数据
      integralSource: "每日打卡", //积分来源
      smallSource: "邀请助力", //来源细分
      integralReward: 3, //积分奖励几分
      number: 0, //练习次数
      dateNow: "",
      flag: false,
      showTimer: "", //计时器
      romNum: "", //房间数
    };
  },
  created() {
    // this.ScrollUp();
    this.selectArticles();
  },
  methods: {
    //判断今天是否保存过
    selectIsSign() {
      this.getDate();
      var vm = this;
      vm.$axios
        .get(
          "/ty_person_integral/selectByUserCode/" +
            vm.userCode +
            "/" +
            vm.integralSource +
            "/" +
            vm.smallSource
        )
        .then(function (response) {
          if (response.data.code === "0000") {
            for (let i = 0; i < response.data.data.length; i++) {
              if (response.data.data[i].createDay == vm.dateNow) {
                vm.flag = true;
              }
            }
            if (vm.flag == true) {
              //今天已经保存过了
              // vm.$message.success("已经保存过了");
            } else {
              //今天没有保存过
              vm.selectNums();
            }
          } else if (response.data.code === "1111") {
            //第一次保存
            vm.selectNums();
          }
        });
    },
    //查询当天练习的次数
    selectNums() {
      this.getDate();
      var vm = this;
      vm.$axios
        .get(
          "/teamDetailResult/selectByTimeUserCode/" +
            vm.userCode +
            "/" +
            vm.dateNow
        )
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.number = response.data.data[0].num; //次数
            if (vm.number >= 3) {
              //达到练习标准
              vm.keyBoardSign();
            } else {
              //还没到达练习标准
            }
          } else if (response.data.code === "1111") {
            //还没到达练习标准
          }
        });
    },
    //获取当前日期
    getDate() {
      const nowDate = new Date();
      const date = {
        year: nowDate.getFullYear(),
        month: nowDate.getMonth() + 1,
        date: nowDate.getDate(),
      };
      const newmonth = date.month >= 10 ? date.month : "0" + date.month;
      const day = date.date > 10 ? date.date : "0" + date.date;
      this.dateNow = date.year + "-" + newmonth + "-" + day;
    },

    //查询邀请助力添加的积分数
    selectIntegralRule() {
      var vm = this;
      vm.$axios
        .get("/ty_integral_rule/selectIntegralRule/" + vm.page + "/" + vm.size)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.IntegralRule = response.data.data.list;
            for (let i = 0; i < response.data.data.list.length; i++) {
              if (response.data.data.list[i].smallSource === vm.smallSource) {
                vm.number = i;
              }
            }
            vm.integralReward =
              response.data.data.list[vm.number].integralReward;
          } else if (response.data.code === "1111") {
            vm.$message({
              message: "暂无数据",
              type: "warning",
            });
          }
        });
    },
    //邀请助力练习加积分
    keyBoardSign() {
      var vm = this;
      var body = {
        userCode: localStorage.getItem("usercode"),
        integralSource: vm.integralSource, //积分来源
        smallSource: vm.smallSource, //来源细分
        integralReward: vm.integralReward, //积分奖励几分
        userName: localStorage.getItem("username"),
      };
      vm.$axios
        .post("ty_person_integral/insertIntegral", body)
        .then(function (response) {
          if (response.data.code == "0000") {
            vm.$message.success(
              "恭喜您今日已完成 邀请助力3次 任务，并获得学习通" +
                vm.integralReward +
                "积分。"
            );
          } else {
            // vm.$message({
            //   message: "积分添加失败",
            //   type: "warning",
            // });
          }
        });
    },
    createBattleTeam() {
      this.level = localStorage.getItem("level");
      //判断账号等级为高级或者是键盘侠，那么则不能创建战队，否则可以创建战队
      if (this.level == "高级键盘手" || this.level == "键盘侠") {
        this.$message.success("您当前等级不能邀请助力，请去助力其他同学!");
      } else {
        this.$router.push({ path: "/CreateBattleTeam" });
        //队长创建战队的时候将姓名存储到缓存中
        localStorage.setItem("captionName", localStorage.getItem("username"));
        sessionStorage.setItem("captainName", localStorage.getItem("username"));
      }
    },
    isShowTimer() {
      // console.log("倒计时倒计时");
    },
    battleTeamRoom() {
      //1.判断当前 有没有可以进入的房间 空闲的房间
      if (this.battleTeamList.length < 1) {
        this.level = localStorage.getItem("level");
        //判断账号等级为高级或者是键盘侠，那么则不能创建战队，否则可以创建战队
        if (this.level == "高级键盘手" || this.level == "键盘侠") {
          this.$message.success("当前没有开放的房间，请您等待好友开房间！");
        } else {
          //如果没有 这个人就需要先创建房间
          this.createTeam();
        }
      } else {
        //2.如果有 直接随机进入空闲的房间
        //根据已经有的房间数 随机生成1位数 比如5个房间 最后再减1 就是0到4
        this.romNum = this.done(1, this.battleTeamList.length) - 1;
        // console.log(this.romNum);
        this.joinBattleTeam(this.romNum); //进入房间
      }
    },
    //队伍创建方法
    createTeam() {
      //队长创建战队的时候将姓名存储到缓存中
      localStorage.setItem("captionName", localStorage.getItem("username"));
      sessionStorage.setItem("captainName", localStorage.getItem("username"));
      if (
        this.inputTime % 1 === 0 &&
        this.inputTime >= 1 &&
        this.inputTime <= 10
      ) {
        var vm = this;
        this.$axios
          .post("/teamResult/CreateBattleTeam", {
            articleName: vm.articleValue, //vm.value,文章名称默认一个
            captainName: sessionStorage.getItem("captainName"),
            teamName: vm.teamNo, //战队名称获取邀请码
            teamNum: vm.teamNo,
            time: 1, //设置默认时间3分钟
            "userId":localStorage.getItem("userid")
          })
          .then(function (response) {
            if (response.data.code === "0000") {
              // vm.$router.push('/battleTeamRoomByLeader');
              vm.$router.push({
                name: "battleTeamRoomByLeader",
                params: {
                  articleName: vm.articleValue,
                  teamName: vm.teamNo,
                  teamNo: vm.teamNo,
                  inputTime: 1, //设置默认时间3分钟
                },
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
          .catch(function (error) {});
      } else {
        this.$message.error("时间必须在规定范围内，且为整数");
      }
    },
    //查询文章标题方法
    selectArticles() {
      var vm = this;
      vm.$axios
        .get("/typArticle/SelectArticles")
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.options = response.data.data;
            vm.articleValue = vm.options[0];
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
        .catch(function (error) {});
    },
    //随机生成五位邀请码
    done(len, max) {
      var arr = [];
      var result = "";
      var count = 0;
      while (count < len) {
        var n = Math.floor(Math.random() * max + 1);
        if (arr.join().indexOf(n) == -1) {
          arr.push(n);
          count++;
        }
      }
      for (let index = 0; index < arr.length; index++) {
        result = result + arr[index];
      }
      return result;
    },

    loadBattleTeamCard() {
      var vm = this;
      vm.userId=localStorage.getItem("userid");
    
      vm.$axios
        .get("/teamResult/loadBattleTeamCard?userId="+vm.userId)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.battleTeamList = response.data.data;
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
        .catch(function (error) {});
    },
    //快速进入房间
    fastJoinBattleTeam() {
      if (this.input.length === 5) {
        var vm = this;
        vm.userId=localStorage.getItem("userid");
        
        vm.$axios
          .get("/teamResult/IsBeforeInBattleTeam?userId="+vm.userId)
          .then(function (response) {
            if (response.data.code === "0000") {
              //如果是队员
              if (response.data.data.state === 0) {
                vm.$router.push({
                  name: "battleTeamRoomByMember",
                  params: {
                    articleName: response.data.data.articleName,
                    inputTime: response.data.data.setTime,
                    teamName: response.data.data.teamName,
                  },
                });
                return;
                
              } else if (response.data.data.state === 1) {
                //如果是队长
                vm.$router.push({
                  name: "battleTeamRoomByLeader",
                  params: {
                    articleName: response.data.data.articleName,
                    inputTime: response.data.data.setTime,
                    teamName: response.data.data.teamName,
                  },
                });
                return;
              } else if (response.data.data.state === 2) {
                vm.$axios
                  .get("/teamResult/loadBattleTeamCard?userId="+vm.userId)
                  .then(function (response) {
                    if (response.data.code === "0000") {
                      vm.battleTeamList = response.data.data;
                    }
                    for (let i = 0; i < vm.battleTeamList.length; i++) {
                      if (vm.battleTeamList[i].teamNum == vm.input) {
                        vm.num = i;
                      }
                    }
                    if (vm.battleTeamList[vm.num].personNum >= 10) {
                      vm.$message({
                        message: "此房间人数已满，请去助力其他人",
                        type: "warning",
                      });
                      return;
                    } else {
                      vm.$axios
                        .get(
                          "/teamResult/joinInBattleTeamByRoomId"+"?"+"teamNums="+vm.input +
                            "&userId="+localStorage.getItem("userid")+"&userName="+localStorage.getItem("username")
                        )
                        .then(function (response) {
                          if (response.data.code === "0000") {
                            vm.input = "";
                            //查询成功，进入房间
                            vm.$router.push("/battleTeamRoomByMember");
                            // vm.selectIsSign();
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
                        .catch(function (error) {});
                    }
                  });
              }
            } else if (response.data.code === "500") {
              
            } else if (response.data.code === "1111") {
              vm.$message({
                message: response.data.message,
                type: "warning",
              });
            }
          })
          .catch(function (error) {});
      }
    },
    //点击加入战队按钮进入房间
    joinBattleTeam(index) {
      var vm = this;
      vm.userId=localStorage.getItem("userid");
 
      vm.$axios.get("/teamResult/loadBattleTeamCard?userId="+vm.userId).then(function (response) {
        if (response.data.code === "0000") {
          vm.battleTeamList = response.data.data;
          if (vm.battleTeamList[index].personNum >= 10) {
            vm.$message({
              message: "此房间人数已满，请去其他房间",
              type: "warning",
            });
            return;
          } else if (vm.battleTeamList[index].personNum < 10) {
          
            vm.$axios
              .get("/teamResult/IsBeforeInBattleTeam?userId="+vm.userId)
              .then(function (response) {
                if (response.data.code === "0000") {
                  //如果是队员
                  if (response.data.data.state === 0) {
                    vm.$router.push({
                      name: "battleTeamRoomByMember",
                      params: {
                        articleName: response.data.data.articleName,
                        inputTime: response.data.data.setTime,
                        teamName: response.data.data.teamName,
                      },
                    });
                    return;
                    // localStorage.setItem("teamName",response.data)
                  } else if (response.data.data.state === 1) {
                    //如果是队长
                    vm.$router.push({
                      name: "battleTeamRoomByLeader",
                      params: {
                        articleName: response.data.data.articleName,
                        inputTime: response.data.data.setTime,
                        teamName: response.data.data.teamName,
                      },
                    });
                    return;
                  } else if (response.data.data.state === 2) {
                    vm.$axios
                      .get(
                        "/teamResult/joinInBattleTeamByRoomId"+"?"+"teamNums="+vm.battleTeamList[index].teamNum  +
                            "&userId="+localStorage.getItem("userid")+"&userName="+localStorage.getItem("username")
                       
                      
                      )
                      .then(function (response) {
                        if (response.data.code === "0000") {
                          // vm.input="";
                          //查询成功，进入房间
                          vm.$router.push("/battleTeamRoomByMember");
                          //判断帮助别人助力的次数够不够3次 够的话就调加分接口
                          // vm.selectIsSign();
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
                      .catch(function (error) {});
                  }
                } else if (response.data.code === "500") {
                
                } else if (response.data.code === "1111") {
                  vm.$message({
                    message: response.data.message,
                    type: "warning",
                  });
                }
              })
              .catch(function (error) {});
          }
        }
      });
    },
  },
  mounted() {
    //第一次加载直接刷新队伍信息
    this.loadBattleTeamCard();
    //然后开始开始查询所有准备中的队伍信息的定时器
    this.findBattleTeamCardTimer = setInterval(() => {
      this.loadBattleTeamCard();
    }, 3000);
    this.inputTime = 5;
    this.teamNo = this.done(5, 9);
  },
  beforeDestroy() {
    clearInterval(this.findBattleTeamCardTimer);

  },
};
</script>
<style scoped>
#box {
  width: 100%;
  height: 42px;
  position: relative;
}
.marquee-box {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: auto;
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
.breadcrumb {
  margin-top: 2%;
  margin-left: 2%;
}
.TeamCard {
  margin-top: 2%;
  margin-bottom: 1%;
  margin-left: 2%;
  width: 95.7%;
}

.tips {
  margin-left: 1%;
  font-size: 14px;
}
.createTeamBtn {
  margin-left: 5%;
}
.createTeamBtn2 {
  margin-left: 20%;
}
.searchInput {
  float: right;
  width: 11%;
  margin-left: 3%;
  border-radius: 50px !important;
}
.TeamInfoCard {
  /* background-color: pink; */
  margin-left: 7.2%;
  margin-top: 3%;
  padding-top: 1%;
  width: 11%;
  padding-bottom: 0.5%;
  display: inline-block;
  border-radius: 5px;
  border: 1px solid;
}
.TeamNo {
  display: inline-block;
  color: #ff9800;
}
.TeamName {
  display: inline-block;
  margin-left: 5%;
  padding-bottom: 11%;
  font-weight: bold;
}
.TeamLeader {
  margin-left: 31%;
  padding-bottom: 4%;
  font-size: 14px;
}
.num {
  margin-left: 0.3%;
  margin-right: 0.3%;
  font-size: 15px;
  color: dodgerblue;
}
.TeamMemberNum {
  margin-left: 31%;
  padding-bottom: 4%;
  font-size: 14px;
}
.joinBtn {
  width: 93% !important;
  margin-top: 1%;
  margin-left: 3.7%;
}
</style>
