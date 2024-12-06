<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" class="login-form">
      <h2>蟹钳</h2>
      <el-form-item label="Username">
        <el-input v-model="loginForm.username"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input type="password" v-model="loginForm.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">Login</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { MessageBox } from 'element-ui';

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    };
  },
  methods: {
    async login() {
      try {
        // 发送登录请求
        const response = await this.$axios.post('/api/login', this.loginForm);
        if (response.data.code && response.data.msg === "success") {
          // 假设登录成功后，后端返回的数据中有token，将其存储到localStorage
          localStorage.setItem('token', response.data.data);
          // 登录成功后可进行页面跳转等操作，比如跳转到首页
          this.$router.push('/home');
        } else {
          // 登录失败，根据后端返回的错误信息弹出提示框
          MessageBox.alert(response.data.msg, '登录失败', {
            confirmButtonText: '确定'
          });
        }
      } catch (error) {
        console.error('登录请求出错:', error);
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('../../assets/images/login-background.jpg'); /* 确保路径正确 */
  -background-size: cover;
  background-position: center;
}

.login-form {
  width: 300px;
  padding: 20px;
  background: rgba(255, 255, 255, 0.8); /* 使表单背景透明 */
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.8);
}
</style>