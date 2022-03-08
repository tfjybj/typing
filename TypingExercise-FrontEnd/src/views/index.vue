/*
 * @Description: 奖状页面
 * @Version: 3.0
 * @Author: 张艳伟
 * @Date: 2021年10月5日19:26:59
 * @LastEditors: 
 * @LastEditTime: 
 */

<template>
  <div>
    <h3></h3>
    <el-button type="primary" class="button" @click="preview">预览证书</el-button>
    <el-dialog
      title="证书预览和下载"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose"
    >
      <div id="pdfDom">
        <div class="proBox">
          <p class="tit"><center>荣誉证书</center></p>
          <p class="con">
            <span>恭喜</span>
            <span class="con-name">{{ userName }}</span
            >同学：<br />
          </p>
          <p class="con-text"><span>在《“键盘侠”成长记》活动中荣获</span></p>
          <p class="con-height">
            <span
              ><center>{{ level }}</center></span>
          </p>
          <p class="con-text">荣誉称号，特发此状，以资鼓励。</p>

          <div class="con-unit">
            <p class="time">{{ this.start }}</p>
          </div>
          <div class="chapter" v-show="isShow">
            <canvas id="chapterCanvas" width="150" height="150"></canvas>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-switch
          inactive-color="#67c23a"
          v-model="downType"
          active-text="图片下载"
          inactive-text="pdf下载"
          style="margin-right: 20px"
        >
        </el-switch>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="getPdf('#pdfDom')">下载</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userName: localStorage.getItem("username"),
      start: "",
      level:localStorage.getItem("level"),
      dialogVisible: false,
      pageData: null, //接收html格式代码
      htmlTitle: "荣誉证书",
      isShow: true,
      isCanvas: false,
      downType: true, // false为 pdf , true为图片
    };
  },
  methods: {
    handleClose() {
      this.dialogVisible = false;
    },
    preview() {
      var vm = this;
      var data = new Date(new Date().setHours(0, 0, 0, 0));
      vm.start =
        data.getFullYear() + "-" + (data.getMonth() + 1) + "-" + data.getDate();
      // console.log(vm.start);
      this.dialogVisible = true;
      this.$nextTick(() => {
        if (!this.isCanvas) {
          // 只绘画一次
          this.isCanvas = true;
          // this.getChapter();
        }
      });
    },
  },
};
</script>

<style scoped >
::v-deep .el-dialog__body {
  padding: 0px;
  display: flex;
  justify-content: center;
}
#pdfDom {
  /* 要想pdf周边留白，要在这里设置 */
  padding: 20px;
  width: 750px;
}
.proBox {
  /* 奖状的模板 */
  background: url("../assets/img/zs.jpg") no-repeat;

  background-size: cover;
  width: 750px;
  height: 525px;
  padding: 90px 94px;
  box-sizing: border-box;
  margin: 0 auto;
  position: relative;
  color: #000;
  font-family: SimSun;
}
.tit {
  color: #cf0c0c;
  font-size: 36px;
  font-weight: 700;
  position: relative;
  top: -6px;
  left: 8px;
  letter-spacing: 20px;
  font-family: STHeiti;
  margin: 20px 0;
}
.proid {
  text-align: right;
  margin: 0;
  font-weight: 500;
}

.con {
  font-size: 20px;
  font-weight: 700;
  text-align: left;
  margin: 10px 0;
  line-height: 32px;
  text-indent: 2em;
}
.con-text {
  font-size: 20px;
  font-weight: 700;
  text-align: left;
  margin: 10px 0;
  line-height: 32px;
  text-indent: 5em;
}
.con-name {
  font-family: 华文行楷, STXingkai;
  border-bottom: 2px solid #000;
}
.con-height {
  font-family: 华文行楷, STXingkai;
  font-weight: 700;
  font-size: 2em
}
.con-unit {
  font-size: 18px;
  font-weight: 700;
  position: absolute;
  right: 130px;
  bottom: 100px;
  text-align: center;
  letter-spacing: 3px;
}
.con-unit p {
  margin: 5px 0;
}
.con-footer {
  font-size: 18px;
  font-weight: 700;
  position: absolute;
  bottom: 45px;
  left: 0;
  right: 0;
  text-align: center;
}
.chapter {
  border-radius: 50%;
  position: absolute;
  bottom: 75px;
  right: 134px;
}
.button{
   margin: 50px 50px;
   position: absolute;
 font-family: 华文行楷, STXingkai;
  font-weight: 700;
  font-size: 2em
}
</style>

