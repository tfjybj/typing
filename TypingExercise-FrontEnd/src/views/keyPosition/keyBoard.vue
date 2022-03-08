
 <template>
  <div>
    <div id="box" ref="box">
    <div class="marquee-box" ref="marquee" @mouseover="menter" @mouseleave="mleave">
      <p ref="cmdlist" id="pWidth">
        <!-- <img style="width: 12px;height: 12px" src="../assets/logo.png" alt="">累计练习时长1小时，可以获得1次抽奖机会，奖品有。。。。。 -->
      <span style="color: blue; font-size: 23px; text-align: center"
            >键位练习累计练习时长1小时，可以获得1次抽奖机会，奖品有学习通积分不等，快来练习吧！
          </span>
      </p>
    </div>
  </div>

    <!-- <el-button
      type="primary"
      icon="el-icon-s-promotion"
      style="margin-left: 44.8%; width: 10%; margin-top: 0.1%"
      class="clickSave"
      @click="clickSave()"
      >是否保存</el-button
    > -->

    <el-card style="margin-top: 0.5%; width: 95.5%; margin-left: 2%">
      <iframe
        id="iframe_key"
        ref="iframe"
        src="/1.键盘布局.html"
        frameborder="0"
        width="100%"
        height="500"
      ></iframe>

      <!-- <div @click="senMessage"></div> -->
    </el-card>

    <el-dialog title="键位练习结束" :visible.sync="centerDialogVisible" width="45%">
      <span style="margin-left: 10%" >{{ speedAndRightTime }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="(centerDialogVisible = false), again()">再来一次</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
export default {
  data() {
    return {
      updateTimer:"",//更新成绩的计时器
      value: 0,
      timer: '',//计时器
      pwidth:0,//公告文本的宽度
      windowWidth:document.documentElement.clientWidth,// 设备屏幕的宽度

      centerDialogVisible:false,
      speedAndRightTime:"",
      rightNum:"",
      jindu:"",
      sudu:"",      
      shijian:"",
      jumpTimer:"",
      IntegralRule:"",//积分规则数据
      integralSource:"每日打卡",//积分来源
      smallSource:"键位练习",//来源细分
      integralReward:3,//积分奖励几分
      num:0,//获取i的值
      dateNow:"",//当前日期
      setTime:"",//练习时长  秒
      setTime2:"",//练习时长  分钟
      flag:false,
      userCode:localStorage.getItem("usercode"),
      gettime:"",//当前日期时间
      keyId:""//键位练习初始化id
    };
  },
  mounted(){
    localStorage.setItem("keyarticle",'1.键盘布局')
    let element = this.$refs.cmdlist;
    this.pwidth = document.defaultView.getComputedStyle(element,'').width.split('px');
    this.timer = setInterval(this.clickCommend, 20);
    this.updateTimer = setInterval(this.updateKeyScore,10000);

  },
   watch:{
    value(newValue, oldValue) {
      let allWidth= parseInt(this.windowWidth)+parseInt(this.pwidth[0]);
      if(newValue <= -allWidth){
        this.$refs.cmdlist.style.marginLeft = this.windowWidth+"px";
        this.value = 0;
      }
    },
  },
  created(){
    this.getCurrentTime();
    //调用初始化的接口
    this.insertKeyResult();
    

  },
  methods: {
    getCurrentTime() {
      //获取当前时间并打印
      var vm = this;
    　let yy = new Date().getFullYear();
    　let mm = new Date().getMonth()+1;
    　let dd = new Date().getDate();
    　let hh = new Date().getHours();
    　let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
    　let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
    　vm.gettime = yy+'-'+mm+'-'+dd+' '+hh+':'+mf+':'+ss;
    },
    clickCommend(e) {
      let _this = this;
      this.$nextTick(() => {
        this.value -=1;
        this.$refs.cmdlist.style.marginLeft = _this.windowWidth+this.value+"px";
      });
    },
    menter(){
      clearInterval(this.timer);
    },
    mleave(){
      this.timer = setInterval(this.clickCommend, 20);
    },
    //初始化键位练习
    insertKeyResult(){
      var vm = this;
      var body = {
          articleName: localStorage.getItem("keyarticle"),
          createTime: vm.gettime,
          id: "123",
          isDelete: 0,
          operator: "",
          remark: "",
          rightRate: 0,
          setTime: 0,
          speed: 0,
          speedProgress: 0,
          updateTime: vm.gettime,
          userCode: localStorage.getItem("usercode"),
          userName: localStorage.getItem("username")
      };
      // console.log(body);
      vm.$axios
        .post("/ty_key/insertKeyResult", body)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.keyId = response.data.data.data.id
            // console.log(vm.keyId);
          } else {
            // console.log("成绩初始化失败");
          }
        });
    },

    //判断今天是否保存过
    // selectIsSign(){
    //   console.log("调用接口，判断今天是否保存过");
    //   this.getDate();
    //   var vm = this;
    //   vm.$axios
    //     .get("/ty_person_integral/selectByUserCode/" +vm.userCode +"/" +vm.integralSource + "/"+ vm.smallSource )
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         for (let i = 0; i < response.data.data.length; i++) {
    //           if (response.data.data[i].createDay==vm.dateNow){
    //               vm.flag=true;
    //           }
    //         }
    //         if(vm.flag==true){
    //           //今天已经保存过了
    //           // vm.$message.success("今天已经保存过了");
    //           // console.log("今天已经保存过了");
    //         }else{
    //           //今天没有保存过
    //           vm.selectkeyTime();
    //         }
    //       } else if (response.data.code === "1111") {
    //         //第一次保存
    //         vm.selectkeyTime();
    //       }
    //     });
    // },


    //查询当天练习的时间
    // selectkeyTime(){
    //   // console.log("调用接口，查询当天练习的时间");
    //   this.getDate();
    //   var vm = this;
    //   vm.$axios
    //     .get("/ty_key/selectSetTimeByTime/" + vm.userCode +"/" +vm.dateNow )
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         vm.setTime=response.data.data[0].num //秒
    //         vm.setTime2 = vm.setTime / 60   //分钟
    //         // console.log("当前练习时间"+vm.setTime2);
    //         if(vm.setTime2>=30){            
    //           //达到练习标准 
    //           vm.keyBoardSign();
    //         }else{
    //           // console.log("未达到标准");
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
    //更新键位练习的成绩
    updateKeyScore(){
      var shijian11=localStorage.getItem("shijian");
      var minute = parseInt(shijian11.split("分",1));//获取分
      var shijian12=localStorage.getItem("shijian");
      var second = (shijian12.split("秒",1)+"").match(/分(\S*)/)[1];//获取秒
   
      var time= parseInt(minute*60)+parseInt(second) //转化为秒
      var jindu=parseInt(localStorage.getItem("jindu"));//进度
      var rightNum=parseInt(localStorage.getItem("rightNum"));//正确率

      var vm = this;
      var body = {
          articleName: localStorage.getItem("keyarticle"),
          createTime: vm.gettime,
          id: vm.keyId,
          isDelete: 0,
          operator: "",
          remark: "",
          rightRate: rightNum,
          setTime: time,
          speed: localStorage.getItem("sudu"),
          speedProgress: jindu,
          updateTime: vm.gettime,
          userCode: localStorage.getItem("usercode"),
          userName: localStorage.getItem("username")
      };
      // console.log(body);
      vm.$axios
        .post("/ty_key/updateKeyResult", body)
        .then(function (response) {
          if (response.data.code === "0000") {
            // console.log("更新成功");
          } else {
          }
        });


    },
    //查询键位练习添加的积分数
    selectIntegralRule() {
      var vm = this;
      vm.$axios
        .get("/ty_integral_rule/selectIntegralRule/" + vm.page + "/" + vm.size)
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.IntegralRule = response.data.data.list;
            for (let i = 0; i < response.data.data.list.length; i++) {
              if(response.data.data.list[i].smallSource === vm.smallSource){
                vm.num = i;
              }
            }
            vm.integralReward=response.data.data.list[vm.num].integralReward;
          } else if (response.data.code === "1111") {
            vm.$message({
              message:"暂无数据",
              type: "warning",
            });
          }
        });
    },
    //完成键位练习加积分
    // keyBoardSign(){
    //   // console.log("达到标准，加积分");
    //   var vm = this;
    //   vm.selectIntegralRule();
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
    //         vm.$message.success("恭喜您今日已完成 键位练习30分钟 任务，并获得学习通"+vm.integralReward+"积分。");
    //       } else {
    //         // vm.$message({
    //         //   message: "积分添加失败",
    //         //   type: "warning",
    //         // });
    //       }
    //     });
    // },

    //点击保存按钮，调用保存方法
    clickSave() {
      this.save();
      // this.selectIsSign();
    },
    //是否进行保存
    // save() {
    //   this.$confirm("确定保存成绩", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning",
    //   })
    //     .then(() => {
    //       //跳转到主页面
    //       // this.$router.push("/Main");
    //       this.insertPersonResult();
          
    //     })
    //     .catch(() => {
    //       //停在当前页
    //     });
    // },
    //再来一次
    // again() {
    //   location.reload();
    //   // this.$router.push("/typingPersonal");
    // },
    //倒计时自动跳转
    // checkScore(){
    //   clearInterval(this.jumpTimer);
    //   this.$router.push("/Main");
    // },
    // 插入成绩
    // insertPersonResult() {
    //   var shijian11=localStorage.getItem("shijian");
    //   var minute = parseInt(shijian11.split("分",1));//获取分
    //   var shijian12=localStorage.getItem("shijian");
    //   var second = (shijian12.split("秒",1)+"").match(/分(\S*)/)[1];//获取秒
   
    //   var time= parseInt(minute*60)+parseInt(second) //转化为秒
    //   var jindu=parseInt(localStorage.getItem("jindu"));//进度
    //   var rightNum=parseInt(localStorage.getItem("rightNum"));//正确率

    //   var vm = this;
    //   var body = {
    //     articleName:localStorage.getItem("keyarticle"),
    //     userCode: localStorage.getItem("usercode"),
    //     userName: localStorage.getItem("username"),
    //     userId: localStorage.getItem("userid"),
    //     setTime: time,
    //     speed: localStorage.getItem("sudu"),
    //     speedProgress: jindu,
    //     rightRate: rightNum
    //   };
    //   console.log(body);

    //   vm.speedAndRightTime =
    //     "时间：" +
    //     localStorage.getItem("shijian") +
    //     "," +
    //     "速度：" +
    //     localStorage.getItem("sudu") +
    //     "KPM" +
    //     "," +
    //     "正确率：" +
    //     rightNum +
    //     "%"+
    //     "," +
    //     "进度" +
    //     jindu + 
    //     "%"
    //     ;

    //   vm.$axios
    //     .post("/ty_key/insertKeyResult", body)
    //     .then(function (response) {
    //       if (response.data.code === "0000") {
    //         // vm.$message.success("成绩保存成功");
    //         //调用查看积分方法
    //         // vm.selectPersonResult();
    //         // vm.go();
    //         // 调用弹框

            
    //         //调用接口
    //         // vm.selectIsSign();
    //         // vm.centerDialogVisible = true;
    //         // vm.jumpTimer = setInterval(() => {vm.checkScore();}, 10000);
    //       } else {
    //         vm.$message.error("成绩添加失败");
    //         return false;
    //       }
    //     });
    // },
  },
    beforeDestroy() {
    clearInterval(this.timer);
    clearInterval(this.updateTimer);
  }

};
</script>

<style scoped>
#box {
  width: 100%;
  height: 50px;
  margin-top: 5px;
  position: relative;
}
.marquee-box  {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
  background-color: #f8f8f8;
}
#pWidth{
  width: 100%;
  height: 50px;
  padding: 0;
  margin: 0;
  line-height: 50px;
  display: block;
  word-break: keep-all;
  white-space: nowrap;
  overflow:hidden;
  font-family: 微软雅黑; 
  font-Size:10px;
  background-color: #f8f8f8
}
::-webkit-scrollbar {
  width: 0 !important;
}
::-webkit-scrollbar {
  width: 0 !important;height: 0;
}

</style>
