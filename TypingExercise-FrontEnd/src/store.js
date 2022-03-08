import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 存放的键值对就是所要管理的状态
    donenum:0,
  },

  mutations: {
    setclicked(state,clickednum){
      state.donenum=clickednum;
    }
  },
  actions: {

  }
})
