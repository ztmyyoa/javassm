// store.js
import { createStore } from 'vuex';

const store = createStore({
    state: {
        username: localStorage.getItem('username') || '',  // 从 localStorage 获取数据
        password: localStorage.getItem('password') || '',
    },
    getters: {
            getUsername(state) {
                return state.username;
            },
            getPassword(state) {
                return state.password;
            },
            isLoggedIn(state) {
                return !!state.username;  // 非空即已登录
            }

    },
    mutations: {
        setUsername(state, username) {
            state.username = username;
            localStorage.setItem('username', username);  // 将用户名保存到 localStorage
        },
        setPassword(state, password) {
            state.password = password;
            localStorage.setItem('password', password);  // 将密码保存到 localStorage
        },
        clearUserData(state) {
            state.username = '';
            state.password = '';
            localStorage.removeItem('username');
            localStorage.removeItem('password');
        }
    },
    actions: {
        // 可以添加一些需要的 actions，例如提交用户名和密码到后台
    }
});

export default store;
