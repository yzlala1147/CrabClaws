<template>
  <div class="change-password-container">
    <el class="box-card">
      <h2 style="color: black;">修改密码</h2>
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordForm" class="form-no-border" style="width: 30%">
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmNewPassword">
          <el-input v-model="passwordForm.confirmNewPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el>
  </div>
</template>

<script>
import qs from 'qs'; // 引入qs库，用于处理请求数据格式转换

export default {
  data() {
    return {
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmNewPassword: ''
      },
      passwordRules: {
        oldPassword: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '新密码长度至少为6位', trigger: 'blur' }
        ],
        confirmNewPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          {
            validator: this.validateConfirmPassword,
            trigger: 'blur'
          }
        ]
      }
    };
  },
  methods: {
    validateConfirmPassword(rule, value, callback) {
      if (value === this.passwordForm.newPassword) {
        callback();
      } else {
        callback(new Error('两次输入的新密码不一致'));
      }
    },
    submitForm() {
      this.$refs.passwordForm.validate(async (valid) => {
        if (valid) {
          try {
            const formData = {
              oldpassword: this.passwordForm.oldPassword,
              newpassword: this.passwordForm.newPassword
            };
            const config = {
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded' // 设置请求头，指定请求数据格式
              }
            };
            const response = await this.$axios.post('/api/repwd', qs.stringify(formData), config);
            if (response.data.code) {
              this.$message({
                message: '修改成功',
                type: 'success'
              });
            } else {
              this.$message.error("修改失败");
            }
          } catch (error) {
            console.log(error);
            this.$message.error('请求出错，请稍后再试');
          }
        }
      });
    },
    resetForm() {
      this.$refs.passwordForm.resetFields();
    }
  }
};
</script>

<style scoped>
.form-no-border.el-form-item {
  border: none; /* 去除表单每项的边框 */
}

.form-no-border.el-form {
  border: none; /* 去除表单整体边框 */
}
</style>