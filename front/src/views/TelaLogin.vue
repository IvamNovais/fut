<template>
  <div class="login-container">
    <el-card class="login-card" shadow="hover">
      <h2 class="login-title">Login</h2>
      <el-form :model="loginForm" ref="loginForm" :rules="rules" label-width="0px">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="Usuário" clearable>
            <template slot="prepend"><i class="el-icon-user"></i></template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="Senha" clearable>
            <template slot="prepend"><i class="el-icon-lock"></i></template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">Entrar</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="text" @click="openRegisterModal">Criar Usuário</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- Modal para criar usuário -->
    <el-dialog :visible.sync="showRegisterModal" title="Criar Usuário">
      <el-form :model="registerForm" ref="registerForm" :rules="registerRules" label-width="120px">
        <el-form-item label="Nome" prop="name">
          <el-input v-model="registerForm.name" placeholder="Nome" clearable>
            <template slot="prepend"><i class="el-icon-user-solid"></i></template>
          </el-input>
        </el-form-item>
        <el-form-item label="Usuário" prop="username">
          <el-input v-model="registerForm.username" placeholder="Usuário" clearable>
            <template slot="prepend"><i class="el-icon-user"></i></template>
          </el-input>
        </el-form-item>
        <el-form-item label="Senha" prop="password">
          <el-input type="password" v-model="registerForm.password" placeholder="Senha" clearable>
            <template slot="prepend"><i class="el-icon-lock"></i></template>
          </el-input>
        </el-form-item>
        <el-form-item label="Confirmar senha" prop="confirmPassword">
          <el-input type="password" v-model="registerForm.confirmPassword" placeholder="Confirmar senha" clearable>
            <template slot="prepend"><i class="el-icon-lock"></i></template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="cadastrar">Registrar</el-button>
          <el-button @click="showRegisterModal = false">Cancelar</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { Notification } from 'element-ui';
import authService from '../services/authService.js';

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      registerForm: {
        name: '',
        username: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        username: [
          { required: true, message: 'Por favor, insira o nome de usuário', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Por favor, insira a senha', trigger: 'blur' }
        ]
      },
      registerRules: {
        name: [
          { required: true, message: 'Por favor, insira o seu nome', trigger: 'blur' }
        ],
        username: [
          { required: true, message: 'Por favor, insira o nome de usuário', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Por favor, insira a senha', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: 'Por favor, confirme sua senha', trigger: 'blur' },
          { validator: this.validateConfirmPassword, trigger: 'blur' }
        ]
      },
      showRegisterModal: false
    };
  },
  methods: {
    async login() {
      this.$refs.loginForm.validate(async (valid) => {
        if (!valid) {
          this.showNotification('Erro ao enviar', 'Por favor, verifique os campos e tente novamente.', 'error');
          return false;
        }
        try {
          const response = await authService.login({
            login: this.loginForm.username,
            password: this.loginForm.password
          });
          this.showNotification('Sucesso', 'Login realizado com sucesso!', 'success');
          this.$router.push("/");
          console.log(response);

        } catch (error) {
          this.showNotification('Erro de Login', 'Nome de usuário ou senha incorretos.', 'error');
          console.log(error);
        }
      });
    },
    openRegisterModal() {
      this.showRegisterModal = true;
    },
    async cadastrar() {
      this.$refs.registerForm.validate(async (valid) => {
        if (!valid) {
          this.showNotification('Erro ao registrar', 'Por favor, verifique os campos e tente novamente.', 'error');
          return false;
        }
        try {
          await authService.register({
            nome: this.registerForm.name,
            login: this.registerForm.username,
            password: this.registerForm.password,
          });
          this.showNotification('Sucesso', 'Usuário criado com sucesso!', 'success');
          this.showRegisterModal = false;
        } catch (error) {
          this.showNotification('Erro ao registrar', 'Ocorreu um erro ao tentar criar o usuário.', 'error');
          console.log(error);
        }
      });
    },
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.registerForm.password) {
        callback(new Error('As senhas não correspondem'));
      } else {
        callback();
      }
    },
    showNotification(title, message, type) {
      Notification({
        title: title,
        message: message,
        type: type,
        duration: 3000
      });
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
  background-color: #f0f2f5;
}
.login-card {
  width: 400px;
  padding: 20px;
}
.login-title {
  text-align: center;
  margin-bottom: 20px;
}
.el-input__inner {
  font-size: 16px;
  padding: 12px 10px;
}
.el-input__placeholder {
  color: #a0a0a0;
}
</style>
