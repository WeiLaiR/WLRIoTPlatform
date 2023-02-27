
import { createStore } from 'vuex'


const store = createStore({
  state() {
    return {
      newName: '',

    }
  },
  mutations: {
    setPath(state) {
      state.newName =  localStorage.getItem("newNameValue")
    }
  }
})

export default store
