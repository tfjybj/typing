 /*
 * @Author: 冯佳兴
 * @Date: 2019-09-23 14:51:53 
 * @Last Modified by:冯佳兴
 * @Last Modified time: 2019-09-24 15:58:07
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
          <input oninput="value=value.replace(/[^\d]/g,'')" 
            class="el-dialog-input"
          v-model="time" 
          :min="1" 
          :max="20" 
          :controls="false" 
          maxlength="2"
          autocomplete="off" 
          placeholder="请输入分钟">
          </br>
          (1-20分钟,建议5分钟)
        </el-form-item>

        <el-divider content-position="left"></el-divider>
        <el-form-item>
          <el-button type="primary" @click="submitForm()">开始练习</el-button>
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
      
      if (vm.time == "") {
        vm.$message.error("时间不为空");
        return;
      }
      vm.time = parseInt(vm.time);
      if ((vm.time%1 !== 0)|| vm.time<=0 || vm.time>20){
          vm.$message.error("测试时间范围为1-20分钟,且为整型");
          return;
      }
      vm.$router.push("/typingPersonal");
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
.el-dialog-input{
 width: 50%;
  height:30px;
  border-radius: 5px;
  color: #606266;
  border:0.1px solid #dcdfe6;
  outline:none;
  padding: 0 15px;
}
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
