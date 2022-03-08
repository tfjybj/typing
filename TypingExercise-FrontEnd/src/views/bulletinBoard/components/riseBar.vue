<template>
  <!--为echarts准备一个具备大小的容器dom-->
  <div id="rise" style="width: 100%; height: 200px"></div>
</template>
<script>
import echarts from "echarts";
export default {
  name: "",
  props: {
    tableRiseBar: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      rise: "",
      //接收后端返回来的数据
      labList: [],
      //接收后端返回来的班级名
      classData: [],
      //接收后端返回来的晋级人次
      riseData: [],
    };
  },
  //调用
  mounted() {
    this.drawBar();
    this.initData();
  },
  methods: {
    drawBar() {
      this.rise = this.$echarts.init(document.getElementById("rise"));
      this.rise.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "value",
            boundaryGap: [0, 0.01],
            axisTick: {
              alignWithLabel: true,
            },
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.8)",
              },
            },
            axisLine: {
              show: false,
            },
            //设置分割线的样式
            splitLine: {
              lineStyle: {
                color: "rgba(255,255,255,.1)",
              },
            },
          },
        ],
        yAxis: [
          {
            type: "category",
            data: [],
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.8)",
                fontSize: "12",
              },
            },
          },
        ],
        series: [
          {
            name: "晋级人次",
            type: "bar",
            data: [],
          },
        ],
      });
      window.addEventListener("resize", function () {
        rise.resize();
      });
    },
    //获取饼状图数据
    initData() {
      var i = 0;
      for (i = 0; i < this.tableRiseBar.length; i++) {
        var name = this.tableRiseBar[i].className;
        this.classData.push(name);
        var rise = this.tableRiseBar[i].riseCount;
        this.riseData.push(rise);
      }
      this.rise.setOption({
        series: [
          {
            type: "bar",
            data: this.riseData,
          },
        ],
        yAxis: [
          {
            data: this.classData,
          }
        ]
      });
    },
  },
};
</script>
<style scoped>
</style>
