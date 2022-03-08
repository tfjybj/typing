import Vue from 'vue';
import Router from 'vue-router';

/**
 * 重写路由的push方法
 */
 const routerPush = Router.prototype.push
 Router.prototype.push = function push(location) {
   return routerPush.call(this, location).catch(error=> error)
 }
 
Vue.use(Router);
export default new Router({
  routes: [{
      path: '/',
      redirect: '/login',
    },
    // 教师端的路由跳转
    {
      path: '/',
      component: () => import('../src/components/common/THome'),
      meta: {
        title: '自述文件'
      },
      children: [
        {
          path: '/boardHomeTeacher',
          component: () => import('../src/views/bulletinBoard/boardHomeTeacher.vue'),
          meta: {
            title: '教师端看板'
          }
        },
        {
          path: '/personTeam',
          component: () => import('../src/views/personManagement/personTeam.vue'),
          meta: {
            title: '团队人员管理'
          },
        },
        {
          path: '/personLevel',
          component: () => import('../src/views/personManagement/personLevel.vue'),
          meta: {
            title: '等级管理'
          },
        },
        {
          path: '/personRankList',
          component: () => import('../src/views/personManagement/personRankList.vue'),
          meta: {
            title: '打字练习排行榜'
          },
        },
        {
          path: '/personKeyBoard',
          component: () => import('../src/views/personManagement/personKeyBoard.vue'),
          meta: {
            title: '键位练习排行榜'
          },
        },
        {
          path: '/showClass',
          component: () => import('../src/views/personManagement/showClass.vue'),
          meta: {
            title: '选择要展示的班级'
          },
        },
        {
          path: '/personBattleTeam',
          component: () => import('../src/views/personManagement/personBattleTeam.vue'),
          meta: {
            title: '邀请助力次数'
          },
        },
        {
          path: '/personBattle',
          component: () => import('../src/views/personManagement/personBattle.vue'),
          meta: {
            title: '邀请助力详情'
          },
        },
        {
          path: '/personNotice',
          component: () => import('../src/views/personManagement/personNotice.vue'),
          meta: {
            title: '公告消息提醒管理'
          },
        },
        {
          path: '/personIntegralRule',
          component: () => import('../src/views/personManagement/personIntegralRule.vue'),
          meta: {
            title: '积分规则管理'
          },
        },
        {
          path: '/personPunchDay',
          component: () => import('../src/views/personManagement/personPunchDay.vue'),
          meta: {
            title: '每日打卡数据'
          },
        },

      ]
    },
    {
      path: '/',
      component: () => import('../src/components/common/Home'),
      meta: {
        title: '自述文件'
      },
      children: [
        {
          path: '/Main',
          component: () => import('../src/views/Main.vue'),
          meta: {
            title: '系统首页'
          },
        },      
        {
          path: '/403',
          component: () => import('../src/components/common/403.vue'),
          meta: {
            title: '403'
          }
        },
        {
          path: '/404',
          component: () => import('../src/components/common/404.vue'),
          meta: {
            title: '404'
          }
        },
        {
          path: '/ChangePassword',
          component: () => import('../src/views/changepassword/ChangePassword.vue'),
          meta: {
            title: '修改密码'
          }
        },
        {
          path: '/GroupResult',
          component: () => import('../src/views/groupResult/GroupResult.vue'),
          meta: {
            title: '组员成绩'
          }
        },
        {
          path: '/personResultChart',
          component: () => import('../src/views/rankList/personResultChart.vue'),
          meta: {
            title: '个人成绩'
          }
        },

        {
          path: '/personBattleResult',
          component: () => import('../src/views/rankList/personBattleResult.vue'),
          meta: {
            title: '助力详情'
          }
        },
        {
          path: '/commonRankList',
          component: () => import('../src/views/rankList/commonRankList.vue'),
          meta: {
            title: '英雄榜'
          }
        },
        {
          path: '/boardHome',
          component: () => import('../src/views/bulletinBoard/boardHome.vue'),
          meta: {
            title: '看板'
          }
        },
        // {
        //   path: '/sign',
        //   component: () => import('../src/views/sign.vue'),
        //     meta: {
        //       title: '打卡'
        //     }
        // },
        {
          path: '/index',
          component: () => import('../src/views/index.vue'),
          meta: {
            title: '奖状'
          }
        },
        {
          path: '/lottery',
          component: () => import('../src/views/lottery.vue'),
            meta: {
              title: '抽奖'
            }
        },       

        {
          path: '/PracticeSetting',
          component: () => import('../src/views/practicesetting/PracticeSetting.vue'),
          meta: {
            title: '练习设定'
          }
        },
        {
          path: '/PracticeSettingRevive',
          component: () => import('../src/views/practicesetting/PracticeSettingRevive.vue'),
          meta: {
            title: '复活赛'
          }
        },
        {
          path: '/keyBoard',
          component: () => import('../src/views/keyPosition/keyBoard.vue'),
          meta: {
            title: '键位练习'
          }
        },
        {
          path: '/TypingPersonal',
          component: () => import('../src/views/typingBattle/typingPersonal.vue'),
          meta: {
            title: '个人练习'
          }
        },
        {
          path: '/TypingPersonalRevive',
          component: () => import('../src/views/typingBattle/typingPersonalRevive.vue'),
          meta: {
            title: '复活赛'
          }
        },
        {
          path: '/TypingPerson',
          component: () => import('../src/views/typingBattle/typingPerson.vue'),
          meta: {
            title: '金手指加成'
          }
        },
        {
          path: '/TypingBattle',
          name: 'TypingBattle',
          component: () => import('../src/views/typingBattle/typingBattle.vue'),
          meta: {
            title: '组队打字'
          }
        },
        {
          path: '/witeBattleTeam',
          component: () => import('../src/views/witeBattleTeam/witeBattleTeam.vue'),
          meta: {
             title: '邀请助力'
          }
        },
        {
          path: '/commonRankList',
          component: () => import('../src/views/rankList/commonRankList.vue'),
          meta: {
            title: '英雄榜'
          }
        },
        {
          path: '/inBattleTeamRankList',
          component: () => import('../src/views/rankList/inBattleTeamRankList.vue'),
          meta: {
            title: '队内英雄榜'
          }
        },
        {
          path: '/CreateBattleTeam',
          component: () => import('../src/views/CreateBattleTeam/CreateBattleTeam.vue'),
          meta: {
            title: '创建战队'
          }
        },
        {
          path: '/battleTeamRoomByLeader',
          name:'battleTeamRoomByLeader',
          component: () => import('../src/views/battleTeamRoom/battleTeamRoomByLeader.vue'),
          meta: {
            title: '队长等待界面'
          }
        },
        {
          path: '/battleTeamRoomByMember',
          name: 'battleTeamRoomByMember',
          component: () => import('../src/views/battleTeamRoom/battleTeamRoomByMember.vue'),
          meta: {
            title: '队员等待界面'
          }
        },
        {
          path: '/personManagement',
          component: () => import('../src/views/personManagement/personManagement.vue'),
          meta: {
            title: '配置管理'
          },
        },
        
        
      ]
    },

    {
      path: '/RegistUser',
      name:'RegistUser',
      component: () => import('../src/views/registuser/RegistUser.vue'),
      meta: {
        title: '注册用户'
      }
    },
    {
      path: '/login',
      component: () => import('../src/views/login/login.vue'),
      meta: {
        title: '登录'
      }
    },
    {
      path: '*',
      redirect: '/404'
    },
    {
      path: '/report',
      component: () => import('../src/views/report.vue'),
      meta: {
        title: '语音播报'
      },
    },
    {
      path: '/ResetPassword',
      component: () => import('../src/views/changepassword/ResetPassword.vue'),
      meta: {
        title: '重置密码'
      },
    },
  ]
});
