<template>
  <div>
    <el-card class="TeamCard">
      <div slot="header" class="clearfix">
        <span style="font-weight:bold">创建战队</span>
      </div>
      <el-dialog title="温馨提示：" :visible.sync="centerDialogVisible" width="30%">
      <span style="margin-left: 8%">您今天已经邀请别人助力<span class="num">{{ this.improvedNum }}</span>次了，本次及之后每次邀请别人助力将会消费学习通<span class="num">2</span>积分。</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="(centerDialogVisible = false), again()">我知道了</el-button>
      </span>
    </el-dialog>
      <div>
        <p class="TeamNo">邀请码:</p>
        <p style="margin-left:10%;display: inline-block;font-size: 18px;">{{teamNo}}</p>
        <el-divider content-position="left"></el-divider>
        <!-- 战队名默认为房间号 -->
        <p class="TeamNo">战队名:</p>
        <p style="margin-left:10%;display: inline-block;font-size: 18px;">{{inputName}}</p>
        <!-- <div style="display: inline-block;width:100%;margin-top:3%;margin-bottom:3%">
          <p class="TeamName">
            <p style="display: inline-block;color:red;font-size: 18px;"></p>
            战队名称:         
          <el-input v-model="inputName" placeholder="请输入战队名" class="inputTeamName"></el-input>
        </div> -->
        <el-divider content-position="left"></el-divider>
        <div style="display: inline-block;width:100%;margin-top:3%;margin-bottom:3%">
          <p class="TeamName">
            <p style="display: inline-block;color:red;font-size: 18px;">*</p>
            选择文章:
          <el-select v-model="value" placeholder="请选择" class="inputTeamName">
            <el-option
              v-for="(item,index) in options"
              :key="index"
              :label="item"
              :value="item"
            ></el-option>
          </el-select>
        </div>
        <el-divider content-position="left"></el-divider>
         <div style="display: inline-block;width:100%;margin-top:3%;margin-bottom:3%">
          <p class="TeamName">
            <p style="display: inline-block;color:red;font-size: px;">*</p>
            测试时间:
          <input  oninput="value=value.replace(/[^\d]/g,'')"  v-model="inputTime" class="el-dialog-input" placeholder="请输入分钟">(1-10分钟)
          <!-- <p style="display: inline-block;position: absolute;margin-left: 27%;
    ">(1-10分钟)</p> -->
        </div>
        <el-divider content-position="left"></el-divider>
        <el-button type="primary" class="creatBattleTeam" @click="createBattleTeam">创建战队</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return { 
      centerDialogVisible:false,
      //弹窗
      improvedNum: "",
      //战队名称
      inputName: "",
      //选择的文章名称
      value: "",
      //测试时间
      inputTime: "5",
      //战队号
      teamNo: "",
      //存放查询到的文章标题
      options: [],
      /////路由带参数跳转用到的变量
      //用于路由的数组名字，没定义不能传参！
      params: [],
      //文章名称
      articleName: "",
      //战队名称
      teamName: "",
      //房间编号 用的 teamNo
      //测试时间 用的 inputTime
    };
  },
  methods: {
    load() {
      var vm = this;
      var userid = localStorage.getItem("userid");
      var data = new Date(new Date().setHours(0, 0, 0, 0));
      var start =
        data.getFullYear() + "-" + (data.getMonth() + 1) + "-" + data.getDate();
      var starttime = start + " " + "00:00:00";
      var end =
        data.getFullYear() +
        "-" +
        (data.getMonth() + 1) +
        "-" +
        (data.getDate() + 1);
      var endtime = end + " " + "00:00:00";

      vm.$axios
        .get(
          "personResult/selectImprovedByTime/" +
            starttime +
            "/" +
            endtime +
            "/" +
            userid
        )
        .then(function (response) {
          if (response.data.code === "0000") {
            vm.improvedNum = response.data.data;
            // console.log(vm.improvedNum);
            if (vm.improvedNum >= 1){
              vm.centerDialogVisible = true;
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
        });
    },
    inputName1() {
      this.inputName = this.inputName.replace(
        /[ `~!@#$%^&*()_\-+=<>?:"{}|,.\/;'\\[\]·~！@#￥%……&*（）——\-+={}|《》？：“”【】、；‘’，。、]/g,
        ""
      );
    },
    //检查输入的是否是符合要求的数字
    // checkIsNum(e){
    //   let boolean = new RegExp("^[1-9][0-9]*$").test(e.target.value)
    //   if(!boolean){
    //     this.$message.warning('输入正整数')
    //     e.target.value=''
    //   }
    // },
    login() {
      this.$router.push({ path: "/loginPage" });
    },
    again(){

    },
    //队伍创建方法
    createBattleTeam() {
      this.inputName1();
      if (
        this.inputName.length < 1 ||
        this.inputName.length > 5 ||
        this.inputName.length == 0
      ) {
        this.$message.error(
          "战队名称最多5个字符，最少1个字符，不能输入特殊字符"
        );
        return;
      }

      if (
        this.inputTime % 1 === 0 &&
        this.inputTime >= 1 &&
        this.inputTime <= 10
      ) {
        var vm = this;
        this.$axios
          .post("/teamResult/CreateBattleTeam", {
         
            articleName: vm.value, //vm.value,
            captainName: sessionStorage.getItem("captainName"),
            teamName: vm.inputName,
            teamNum: vm.teamNo,
            time: vm.inputTime,
            "userId":localStorage.getItem("userid")
          })
          .then(function (response) {
            if (response.data.code === "0000") {
              // vm.$router.push('/battleTeamRoomByLeader');
              vm.$router.push({
                name: "battleTeamRoomByLeader",
                params: {
                  articleName: vm.value,
                  teamName: vm.inputName,
                  teamNo: vm.teamNo,
                  inputTime: vm.inputTime,
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
          .catch(function (error) {
            // console.log("删除失败");
          });
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
            vm.value = vm.options[0];
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
    //生成五位数
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
  },
  mounted() {
    this.load();
    this.inputTime = 5;
    this.teamNo = this.done(5, 9);
    this.inputName = this.teamNo;//战队名默认为房间号
    this.selectArticles();
  },
  created() {},
};
</script>
<style scoped>
.num {
  margin-left: 2%;
  margin-right: 2%;
  font-size: 16px;
  color:dodgerblue
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
.createTeamBtn {
  margin-left: 72%;
}
.searchInput {
  width: 11%;
  margin-left: 3%;
  border-radius: 50px !important;
}
.TeamNo {
  display: inline-block;
  font-size: 18px;
  margin-left: 6.4%;
}
.TeamName {
  display: inline-block;
  margin-left: 5.4%;
  font-size: 18px;
  /* font-weight: bold; */
}
.joinBtn {
  width: 93% !important;
  margin-top: 1%;
  margin-left: 3.7%;
}
.line {
  width: 95%;
  margin-bottom: 4%;
}
.inputTeamName {
  width: 16%;
  display: inline-block;
  margin-left: 10%;
  /* margin-top: -1.4%; */
  position: absolute;
}
.el-dialog-input {
  margin-left: 10%;
  width: 16%;
  height:30px;
  border-radius: 5px;
  color: #606266;
  border:0.1px solid #dcdfe6;
  outline:none;
  padding: 0 15px;

}
.creatBattleTeam {
  margin-left: 18%;
  width: 10%;
  font-size: 18px;
}
/* >>>.el-input__inner{
  margin-top: 11%;
  margin-left: -24%;
} */
</style>
