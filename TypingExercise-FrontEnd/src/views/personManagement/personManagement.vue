<!--
 * @Descripttion: 
 * @version: 
 * @Author: 刘延强
 * @Date: 2021-09-20 15:46:35
 * @LastEditors: 刘延强
 * @LastEditTime: 2021-09-22 14:43:13
-->
<template>
  <div>
    <el-card style="margin-top: 2.5%; width: 95.5%; margin-left: 2%">
      <el-tabs class="tabTable" v-model="activeName">
        <el-tab-pane label="看板" name="personList4">
          <board-homeTeacher></board-homeTeacher>
        </el-tab-pane>
        <el-tab-pane label="团队人员管理" name="personList1">
          <personTeam></personTeam>
        </el-tab-pane>
        <el-tab-pane label="等级管理" name="personList2">
          <personLevel></personLevel>
        </el-tab-pane>
        <el-tab-pane label="打字练习排行榜" name="personList3">
          <person-rank-list></person-rank-list>
        </el-tab-pane>
        <el-tab-pane label="键位练习排行榜" name="personList5">
          <person-key-board></person-key-board>
        </el-tab-pane>
        <el-tab-pane label="选择要展示班级" name="personList6">
          <show-class></show-class>
        </el-tab-pane>
        <el-tab-pane label="邀请助力次数" name="personList7">
          <person-battle-team></person-battle-team>
        </el-tab-pane>
        <el-tab-pane label="邀请助力详情" name="personList8">
          <person-battle></person-battle>
        </el-tab-pane>
        <el-tab-pane label="公告消息提醒管理" name="personList9">
          <person-notice></person-notice>
        </el-tab-pane>
        <el-tab-pane label="积分规则管理" name="personList10">
          <person-integral-rule></person-integral-rule>
        </el-tab-pane>
        <el-tab-pane label="每日打卡数据" name="personList11">
          <person-punch-day></person-punch-day>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import personLevel from "./personLevel.vue";
import personTeam from "./personTeam.vue";
import PersonRankList from "./personRankList.vue";
import BoardHomeTeacher from "../bulletinBoard/boardHomeTeacher.vue";
import personKeyBoard from "@/views/personManagement/personKeyBoard";
import ShowClass from "./showClass.vue";
import personBattleTeam from "@/views/personManagement/personBattleTeam";
import personBattle from "@/views/personManagement/personBattle";
import personNotice from "@/views/personManagement/personNotice";
import personIntegralRule from "@/views/personManagement/personIntegralRule";
import personPunchDay from "@/views/personManagement/personPunchDay";

export default {
  name: "personManagement",
  components: {
    personLevel,
    personTeam,
    PersonRankList,
    BoardHomeTeacher,
    personKeyBoard,
    ShowClass,
    personBattleTeam,
    personBattle,
    personNotice,
    personIntegralRule,
    personPunchDay,
  },
  data() {
    return {
      activeName: "personList4",
    };
  },
  methods: {},
};
</script>
<style scoped>
.el-dialog-input {
  width: 50%;
}
.word {
  margin-left: 24px;
}
.editWord {
  margin-left: 24px;
}
/deep/.el-table th > .cell {
  text-align: center;
}
/deep/.el-table .cell {
  text-align: center;
}
</style>
