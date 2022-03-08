<template>
  <div>
    <!-- <div>
    <img src="../../src/assets/img/report.png" alt />
    百度语音,浏览器默认拒绝自动播放音频，需要的话得手动设置
    </div> -->
    <div style="float:left;margin:2%;width: 100%">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>播报内容</span>
        </div>
         <div v-for="(item, index) in reportContent" :key="index" class="text item">
          {{reportContent[index]}}
          <el-button type="primary" style="margin-left:20%;margin-top:10%;width:60%;" @click="refresh(reportContent[index])">点击播放</el-button>
        </div>
        <!-- <div class="text item">
          <span>{{reportContent}}</span>
        </div> -->
      </el-card>
      <!-- <el-table data="reportContent[i]" style="width: 100%;">
        <el-table-column prop="i+1" label="播报序号" style="width:10%;"></el-table-column>
        <el-table-column prop="reportContent[i]" label="播报内容" style="width:90%;"></el-table-column>
      </el-table> -->
    </div>

    <div style="float:right;width: 20%;margin:3%;">
      <!-- el-icon-video-pause -->
      <!-- <el-button type="primary" style="margin-left:20%;margin-top:5%;width:60%;" @click="change">{{playorpause}}</el-button>
      <el-button type="primary" style="margin-left:20%;margin-top:10%;width:60%;" @click="refresh">刷新</el-button> -->
    </div>
   <div style="float:left"> <p v-html="logInfo"></p></div>
  </div>

</template>

<script>
export default {
  data(){
    return{
      list:[],//保存音频
      playorpause:"点击播放",
      reportContent:[],
      logInfo:""
    }
  },
  // mounted(){
  //   this.btts('你好')
  //   setTimeout(()=>{
  //     this.btts('世界上最大的动物是')
  //   },1000)
  //   setTimeout(()=>{
  //     this.btts('不知道')
  //   },1200)
  //   // 以上三次模拟预警弹框，弹框一出开始播放，第二条语音在第一条音频播完紧跟上。
  //   // 计时器保证三条音频可以紧跟上
  //   this.playTime = setInterval(()=>{
  //     this.play()
  //   },1000)
  // },
  created(){
    var vm = this;
    vm.logInfo =vm.logInfo +" 开始加载晋级名单";
    this.getReportContent();
  },
  destroyed(){
    clearInterval(this.playTime)
  },
  methods:{
  //   this.btts('你好')
  //   setTimeout(()=>{
  //     this.btts('世界上最大的动物是')
  //   },1000)
  //   setTimeout(()=>{
  //     this.btts('不知道')
  //   },1200)
  //   // 以上三次模拟预警弹框，弹框一出开始播放，第二条语音在第一条音频播完紧跟上。
  //   // 计时器保证三条音频可以紧跟上
  //   this.playTime = setInterval(()=>{
  //     this.play()
  //   },1000)
   //获取后端传过来的播报内容
   //播放/暂停
     change(){
      var vm = this;
      if(vm.playorpause==="点击播放"){
        
        vm.playorpause="点击暂停";
        for (let index = 0; index < vm.reportContent.length; index++) {
          this.btts(vm.reportContent[index]);
        }
        vm.logInfo = vm.logInfo+"<br>已经完成所有文本的语音转换，转换条数为："+this.list.length;

         this.millisecond = setInterval(() => {
                 if(this.list.length!=0){
        
                    this.play();
                }
             }, 1000);
      }else{
        vm.playorpause="点击播放";        
      }
      
    },
    //刷新
    refresh(msge){
      // this.change();  // 自动播报
      // this.getReportContent();  //重新获取要播报的内容
      console.log(msge)
      this.btts(msge);
      this.play();
    },
    //获取后端传过来的播报内容
    getReportContent() {
      var vm = this;
      vm.reportContent = [];  //每次刷新都要置空晋级列表（播放内容列表）
      vm.$axios
        .get("/personResult/selectPersonImprovement")
        .then(function(response) {
          // var i = 0;
          if (response.data.code === "0000") {
            if(response.data.data==undefined || response.data.data==null){
              vm.reportContent.push("暂无晋级人员") ;
            }else{
              vm.reportContent = response.data.data;
              // console.log(response.data.data);
            }    
            vm.logInfo =vm.logInfo +" <br/>已完成加载晋级人员信息";

          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning"
            });
          }
        });
    },
    play(){
      // console.log(this.list,'1222222222222')
      var vm = this;
      if(this.list.length){
        vm.logInfo = vm.logInfo+"<br> 准备开始逐条播放音频，当前是倒数第"+this.list.length+"条";
        // 播放
        var audio = new Audio();
        audio.setAttribute('src', this.list[0].src);
        audio.play();
        audio=null;
        // 及时清除计时器，避免重叠
        clearInterval(this.playTime)
        // 在第一条音频播放时间后重新开启计时器
        this.playTime = setInterval(()=>{
         
          this.play();
        },7000)
        // 及时删除数组0
        this.list.splice(0,1);

        //vm.reportContent=[];  //播放完，置空
      }
    },
     btts(reportContent) {
      var vm=this;
      vm.logInfo = vm.logInfo+"<br> 准备进行语音转换（逐文本）";
　　  //以下参数参考api文档，token和人的声音音色之类的参数
      var param={
            tex: reportContent,//合成的文本，合成的文本，使用UTF-8编码。小于2048个中文字或者英文数字。（文本在百度服务器内转换为GBK后，长度必须小于4096字节）
            tok: '24.f91a7b34fb48a6ffa060b7e44db7a282.2592000.1606911528.282335-22914405',//这个token要换成自己的
            spd: 5,//默认为5中语速，语速，取值0-15，默认为5中语速
            pit: 5,//5中语调，音调，取值0-15，默认为5中语调
            vol: 15,//音量，取值0-15，默认为5中音量
            per: 1,//基础音库，度小宇=1，度小美=0，度逍遥（基础）=3，度丫丫=4
            ctp:1,//web，客户端类型选择，web端填写固定值1
            lan:'zh'//中文，固定值zh。语言选择,目前只有中英文混合模式，填写固定值zh
        };
      var url = 'http://tsn.baidu.com/text2audio';
      var p = param || {};
      var that = this;
      var xhr = new XMLHttpRequest();
      xhr.open('POST', url);
　　　　// 创建form参数
    　　 var data = {};
　　　　 for (var p in param) {
          data[p] = param[p]
      }
 　　　　// 赋值预定义参数
　　　　 data.cuid = data.cuid || data.tok;
      // data.ctp = 1;
      // data.lan = data.lan || 'zh';
　　　 //以上为封装请求的参数
　　　// 序列化参数列表
　　　 var fd = [];
      for(var k in data) {
          fd.push(k + '=' + encodeURIComponent(data[k]));
      }
      var frd = new FileReader();
      xhr.responseType = 'blob';
      xhr.send(fd.join('&'));
　　　 //请求返回结果处理
      xhr.onreadystatechange = function() {
          if (xhr.readyState == 4) {
              if (xhr.status == 200) {
                  if (xhr.response.type == 'audio/mp3') {
　　　　　　　　　　　　//  保存数据
                      that.list.push({
                        src:URL.createObjectURL(xhr.response),
                        size:xhr.response.size
                      })
                      vm.logInfo = vm.logInfo+"<br> 语音合成结束，存入待播放数据list";
                      // 播报传入的内容
                      // 计时器保证三条音频可以紧跟上
                      // this.playTime = setInterval(()=>{
                      //     that.play();
                      // },1000)                     
                  }else{
　　　　　　　　　//这里如果百度语音合成的接口返回类型不是audio/mp3，是json的话，代表你的参数有问题，返回结果失败，具体可以查看api文档
                  vm.logInfo = vm.logInfo+"<br> 调用语音合成接口token已失效!";
                }
              }else{
             
                vm.logInfo = vm.logInfo+"<br> 语音合成接口调用失败";

              }
          }
      }
    }
  }
}
</script>
<style scoped>
  .text {
    font-size: 20px;
  }
  .item {
    margin: 5%;
  }
  .clearfix {
    font-size: 24px;
    font-weight: bold;
    text-align: center;
  }
</style>
