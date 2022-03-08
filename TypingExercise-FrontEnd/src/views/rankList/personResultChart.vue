<template>
  <div id="chartLine" class="line-wrap" width="100%" ref="chart"></div>
  
</template>

<script>
import * as echarts from "echarts";
require("echarts/theme/shine"); //引入主题
export default {
  data() {
    return {
      chartLine: "",
      //接收后端返回来的文章练习数据
      labList: [],
      //接收后端返回来的键位练习数据
      labKeyList: [],
      //x轴数据—时间
      createTime: [],
      //y轴速度
      speeddata: [],
      //y轴键位练习速度
      speedKeyData: [],
      //y轴正确率
      rightRatedata: [],
      //y轴键位练习正确率
      rightKeyRateData: [],
      //xxx人员成绩
      userName: "",
      //消息
      massage: "成绩折线图",
    };
  },
  created() {},
  mounted() {
    //获取userid
    var vm = this;
    this.$nextTick(function () {
      this.drawLineChart();
    });

    if (
      this.$route.query.userid == null ||
      this.$route.query.username == null
    ) {
      vm.userid = localStorage.getItem("userid");
      vm.userName = localStorage.getItem("username");
      vm.userCode = localStorage.getItem("usercode");
      this.Echarts_list();
      this.Echarts_Keylist();
    } else {
      vm.userid = this.$route.query.userid;
      vm.userName = this.$route.query.username;
      vm.userCode = localStorage.getItem("usercode");
      this.Echarts_list();
      this.Echarts_Keylist();
    }
    // this.Echarts_list();
  },
  watch: {
    createTime(val) {
      this.drawLineChart();
    },
  },
  methods: {
    //初始化图表，具体实现
    drawLineChart() {
      //初始化折线图
      this.chartLine = echarts.init(this.$el, "default");
      this.chartLine = this.$echarts.init(document.getElementById("chartLine"));

      //设置折线图内容
      let option = {
        title: {
          text: this.userName + this.massage,
          x: "center",
          align: "right",
        },
        grid: {
          bottom: 80,
        },
        toolbox: {
          feature: {
            saveAsImage: {}, //保存为图片
          },
        },
        //鼠标放到折线图上展示的数据展示样式
        tooltip: {
          trigger: "axis",
          axisPointer: {
            animation: false,
          },
        },
        //时间轴
        dataZoom: [
          {
            show: true,
            realtime: true,
            start: 30,
            end: 70,
          },
          {
            type: "inside",
            realtime: true,
          },
        ],
        //标志对应的折线图，点击legend标志会隐藏对应的折线图
        legend: {
          top: 30,
          data: [
            "文章练习速度",
            "键位练习速度",
            "文章练习正确率",
            "键位练习正确率",
          ],
        },
        calculable: true,
        //x轴
        xAxis: [
          {
            name: "时间",
            type: "category",
            boundaryGap: false,
            axisTick: {
              show: false,
            },
            data: this.createTime,
          },
        ],
        //y轴
        yAxis: [
          {
            type: "value",
            axisTick: {
              show: false,
            },
            name: "速度|正确率",
          },
        ],
        //y轴数据
        series: [
          {
            name: "文章练习正确率",
            type: "line",
            data: this.rightRatedata,
          },
          {
            name: "键位练习正确率",
            type: "line",
            data: this.rightKeyRateData,
          },
          {
            name: "文章练习速度",
            type: "line",
            data: this.speeddata,
          },
          {
            name: "键位练习速度",
            type: "line",
            data: this.speedKeyData,
          },
        ],
      };
      //自适应
      window.onresize = this.chartLine.resize;
      //使用刚指定的配置项和数据显示图表
      this.chartLine.setOption(option);
    },
    //发送请求，返回文章练习数据
    Echarts_list() {
      this.$axios
        .get("/personResult/selectPersonRateAndSpeed/" + this.userid)
        .then((res) => {
          var i = 0;
          if (res.data.code == "0000") {
            this.labList = [];
            for (i = 0; i < res.data.data.length; i++) {
              var daytime = res.data.data[i].createTime;
              this.createTime.push(daytime);
              var speed = res.data.data[i].speed;
              this.speeddata.push(speed);
              var right = res.data.data[i].rightRate;
              this.rightRatedata.push(right);
            }
          }
        });
    },
    //发送请求，返回键位练习数据
    Echarts_Keylist() {
      this.$axios
        .get("/ty_key/selectPersonKeyRateAndSpeed/" + this.userCode)
        .then((res) => {
          var i = 0;
          if (res.data.code == "0000") {
            this.labKeyList = [];
            for (i = 0; i < res.data.data.length; i++) {
              var daytime = res.data.data[i].createTime;
              this.createTime.push(daytime);
              var speed = res.data.data[i].speed;
              this.speedKeyData.push(speed);
              var right = res.data.data[i].rightRate;
              this.rightKeyRateData.push(right);
            }
          }
        });
    },
  },
};
</script>

<style lang='less' scope>
.line-wrap {
  margin-top: 30px;
  width: 100%;
  height: 450px;
  background-color: white;
}
</style>
