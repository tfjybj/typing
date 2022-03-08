 /*
 * @Author: 张艳伟
 * @Date: 2021年10月22日21:38:21
 * @Last Modified by:复活赛的打字接口
 * @Last Modified time: 
 */
 
 <template>
  <div>
    <el-card class="card">
      <div slot="header" class="clearfix">
        <i class="el-icon-setting">练习设定</i>
      </div>
      <el-form label-width="100px" class="demo-ruleForm">
        <el-form-item label="选择文章" style="width:35%">
          <el-select v-model="value" placeholder="请选择">
            <el-option v-for="(item,index) in options" :key="index" :label="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="left"></el-divider>
        <!-- <el-form-item label="测试时间" prop="time">
          <el-input type="number" v-model="time" style="width:24%"></el-input>(1-50分钟)
        </el-form-item>-->

        <el-form-item label="测试时间" prop="time" style="width:26%">
          <el-input type="number" v-model="time" autocomplete="off" placeholder="请输入分钟"></el-input>(1-20分钟，建议5分钟)
        </el-form-item>

        <el-divider content-position="left"></el-divider>
        <el-form-item>
          <el-button type="primary" @click="submitForm()">进入复活赛</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
 <script>
export default {
  data() {
    return {
      options: [],
      value: "", // 选取默认值
      time: '5'
    };
  },
  methods: {
    submitForm() {
      var vm = this;
      //把小数转换为整数
      vm.time = parseInt(vm.time);
      if (vm.time == "") {
        vm.$message.error("时间不为空");
        return;
      }

       if ((vm.time%1 !== 0)|| vm.time<0 || vm.time>20){
          vm.$message.error("测试时间范围为1-20分钟,且为整型");
           return;
       }

      vm.$router.push("/typingPersonalRevive");
      localStorage.setItem("articles", vm.value);
      localStorage.setItem("deadTime", vm.time);
    },

    // 获取文章
    selectArticles() {
      var vm = this;
      vm.$axios
        .get("/typArticle/SelectArticles")
        .then(function(response) {
          if (response.data.code === "0000") {
            vm.options = response.data.data;
            vm.value = vm.options[0];
          } else if (response.data.code === "500") {
            vm.$message({
              message: response.data.message,
              type: "warning"
            });
          } else if (response.data.code === "1111") {
            vm.$message({
              message: response.data.message,
              type: "warning"
            });
          }
        })
        .catch(function(error) {
          // console.log("删除失败");
        });
    }
  },
  mounted() {},
  // 钩子函数，初始化界面，加载文章
  created() {
    this.selectArticles();
  }
};
</script>
<style scoped>
.card {
  margin-top: 5%;
  width: 70%;
  height: 85%;
  margin-left: 15%;
}

.breadcrumb {
  margin-top: 2%;
  margin-left: 2%;
}
</style>
