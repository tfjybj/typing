
/*
 * @Author: 冯佳兴
 * @Date: 2019-09-22 12:59:53 
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time:  2019年9月23日11:31:49
 */
<template>
    <div class=".div" >
        <v-head></v-head> 
        <div  :class="{'content-collapse':collapse}">
            <br/>
            <br/>
            <v-tags></v-tags>
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
import vHead from './Header.vue';
import vTags from './Tags.vue';
import bus from './bus';
export default {
    data() {
        return {
            tagsList: [],
            collapse: false
        };
    },
    components: {
        vHead,
        vTags
    },
    created() {
        bus.$on('collapse-content', msg => {
            this.collapse = msg;
        });

        // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
        bus.$on('tags', msg => {
            let arr = [];
            for (let i = 0, len = msg.length; i < len; i++) {
                msg[i].name && arr.push(msg[i].name);
            }
            this.tagsList = arr;
        });
    }
};
</script>
<style scoped>
.div{
 overflow-y: auto;
}
 
</style>