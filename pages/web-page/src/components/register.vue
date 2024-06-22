<template>
  <div class="register-container">
    <el-form :model="registerForm" :rules="registerFormRules" ref="registerFormRef" label-width="100px" class="login-form">
      <!-- 用户名输入框 -->
      <el-form-item label="name" prop="username">
        <el-input v-model="registerForm.username"></el-input>
      </el-form-item>

      <!-- 用户密码输入框 -->
      <el-form-item label="password" prop="password">
        <el-input type="password" v-model="registerForm.password"></el-input>
      </el-form-item>

      <!-- 确认用户密码输入框 -->
      <el-form-item label="confirm" prop="confirmPassword">
        <el-input type="password" v-model="registerForm.confirmPassword"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="register">register</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import md5 from "md5";

export default {
  name: 'Register',
  data() {
    return {
      registerForm: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      registerFormRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: this.confirmPasswordValidator, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    register() {
      this.$refs.registerFormRef.validate((valid) => {
        if (valid) {
          let pass = this.registerForm.password;
          //10次hash
          for (let i = 0; i < 10; i++) {
            pass = md5(pass)
          }
          this.$http.post('register', {username: this.registerForm.username, password: pass})
          alert('注册信息提交成功！');
          this.$router.push('/login')
        } else {
          console.error('注册信息验证失败！');
          return false;
        }
      });
    },
    confirmPasswordValidator(rule, value, callback) {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  /* 样式 */
}
.login-form {
  /* 样式 */
}
</style>