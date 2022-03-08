<template>
  <!--为echarts准备一个具备大小的容器dom-->
  <div id="main" style="width: 100%; height: 200px"></div>
</template>
<script>
import echarts from "echarts";
export default {
  name: "",
  props: {
    tableGrowthBar: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      charts: "",
      teamName: [],
      growthIntegral: [],
    };
  },
  //调用
  mounted() {
    this.drawLine();
    this.initData();
  },
  methods: {
    drawLine(id) {
      this.charts = echarts.init(document.getElementById("main"));
      this.charts.setOption({
        color: ["#2f89cf"],
        //提示框组件
        tooltip: {
          //触发方式
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        //直角坐标系内绘制网格
        grid: {
          left: "3%",
          right: "4%",
          bottom: "8%",
          //是否显示刻度标签，true为显示
          containLabel: true,
        },
        // //工具箱组件，可以另存为图片等功能
        // toolbox: {
        //   feature: {
        //     saveAsImage: {},
        //   },
        // },
        //设置x轴的相关配置
        xAxis: [
          {
            type: "category",
            data: [],
            axisTick: {
              alignWithLabel: true,
            },
            //设置x轴的文字颜色
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.8)",
              },
            },
            //x轴线条样式是否显示
            axisLine: {
              show: false,
            },
          },
        ],
        //设置y轴的相关配置
        yAxis: [
          {
            name: "积分",
            type: "value",
            //y轴文字标签样式
            axisLabel: {
              textStyle: {
                color: "rgba(255,255,255,.8)",
                fontSize: "12",
              },
            },
            //设置y轴上的标识样式
            axisLine: {
              lineStyle: {
                color: "rgba(255,255,255,.5)",
              },
            },
            //设置分割线的样式
            splitLine: {
              lineStyle: {
                color: "rgba(255,255,255,.1)",
              },
            },
          },
        ],
        //系列图表配置 它决定着显示哪种类型的图表
        series: [
          {
            type: "bar",
            data: [],
            itemStyle: {
              barBorderRadius: 5,
            },
          },
        ],
      });
    },
    //获取饼状图数据
    initData() {
      var i = 0;
      for (i = 0; i < this.tableGrowthBar.length; i++) {
        var name = this.tableGrowthBar[i].teamName;
        this.teamName.push(name);
        var integral = this.tableGrowthBar[i].growthIntegral;
        this.growthIntegral.push(integral);
      }
      this.charts.setOption({
        xAxis: [
          {
            data: this.teamName,
          },
        ],
        series: [
          {
            type: "bar",
            data: this.growthIntegral,
          },
        ],
      });
    },
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
  list-style: none;
}
</style>
