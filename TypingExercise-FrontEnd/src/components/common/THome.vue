
<!--
 * @Descripttion: 教师端
 * @version: 
 * @Author: 张艳伟
 * @Date: 2021年11月7日16:29:28
 * @LastEditors: 
 * @LastEditTime: 
 -->
<template>
  <div class=".div">
    <t-head></t-head>
    <div :class="{ 'content-collapse': collapse }">
      <br/>
      <br/>
      <v-tag></v-tag>
      <div class="content">
        <transition name="move" mode="out-in">
          <keep-alive :include="tagsList">
            <router-view></router-view>
          </keep-alive>
        </transition>
      </div>
    </div>
  </div>
</template>

<script>
import tHead from "./THeader.vue";
import vTag from "./Tag.vue";
import bus from "./bus";
export default {
  data() {
    return {
      tagsList: [],
      collapse: false,
    };
  },
  components: {
    tHead,
    vTag,
  },
  created() {
    bus.$on("collapse-content", (msg) => {
      this.collapse = msg;
    });

    // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
    bus.$on("tags", (msg) => {
      let arr = [];
      for (let i = 0, len = msg.length; i < len; i++) {
        msg[i].name && arr.push(msg[i].name);
      }
      this.tagsList = arr;
    });
  },
};
</script>
<style scoped>
.div {
  overflow-y: auto;
}
</style>