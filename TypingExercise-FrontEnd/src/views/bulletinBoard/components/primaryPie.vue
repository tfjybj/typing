<!--
 * @Descripttion: 
 * @version: 
 * @Author: 刘延强
 * @Date: 2021-09-17 16:06:07
 * @LastEditors: 刘延强
 * @LastEditTime: 2021-09-20 10:41:52
-->
<template>
  <!--为echarts准备一个具备大小的容器dom-->
  <div id="primary" style="width: 100%; height: 200px"></div>
</template>

<script>
import echarts from "echarts";
export default {
  name: "",
  props: {
    Table: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      charts: "",
    };
  },
  //调用
  mounted() {
    this.drawLine();
    this.initData();
  },
  methods: {
    drawLine() {
      this.charts = echarts.init(document.getElementById("primary"));
      this.charts.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        series: [
          {
            name: "初级键盘手（人）",
            type: "pie",
            radius: "55%",
            center: ["50%", "50%"],
            data: [],
            label: {
              normal: {
              show: true,
              formatter: function (params) {
                  return params.name + " " + params.value + "人"
               }
              }
            },
          },
        ],
      });
      window.addEventListener("resize", function () {
        charts.resize();
      });
    },
    //获取饼状图数据
    initData() {
      var getData = [];
      for (let i = 0; i < this.Table.length; i++) {
        var obj = new Object();
        obj.name = this.Table[i].className;
        obj.value = this.Table[i].primaryCount;
        //当前用户所在班级进行高亮显示
        if(localStorage.getItem("className")==obj.name){
        obj.label = {normal:{show:true,textStyle:{fontSize:23,color: "#b713ed"}}}
        }
        else{
        obj.label = {normal:{show:true,textStyle:{fontSize:13}}}
        }
        getData[i] = obj;
      }
      this.charts.setOption({
        series: [
          {
            data: getData,
          },
        ],
      });
    },
  },
};
</script>

<style scoped>
</style>