<!--
 * @Descripttion: 
 * @version: 
 * @Author: 刘延强
 * @Date: 2021-09-21 16:55:00
 * @LastEditors: 刘延强
 * @LastEditTime: 2021-09-21 17:01:50
-->

<template>
  <div class="ele-import-finish">
    <div>
      <i class="el-icon-success"></i>
    </div>
    <div>导入成功</div>
    <div class="ele-import-action">
      <el-button
        @click="handleFinish"
        type="primary"
      >查看详情</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EleImportFinish',
  data () {
    return {}
  },
  methods: {
    handleFinish () {
      this.$emit('finish')
    }
  },
  mounted () {}
}
</script>

<style>
.ele-import-finish {
  text-align: center;
}

.ele-import-finish .el-icon-success {
  font-size: 150px;
  line-height: 1.2;
  color: #67c23a;
}
</style>
