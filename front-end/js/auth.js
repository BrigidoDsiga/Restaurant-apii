// authService.js - Camada de serviço para abstrair a lógica de autenticação
export const AuthService = {
  async login(username, password) {
    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        throw new Error('Credenciais inválidas');
      }

      const data = await response.json();
      return data.token;
    } catch (error) {
      console.error('Login failed:', error);
      throw error;
    }
  },

  async register(username, email, password) {
    try {
      const response = await fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email, password })
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Erro ao registrar');
      }

      return true;
    } catch (error) {
      console.error('Registration failed:', error);
      throw error;
    }
  },

  storeToken(token) {
    localStorage.setItem('token', token);
  },

  redirectTo(path) {
    window.location.href = path;
  }
};

// authHandlers.js - Manipuladores de eventos para os formulários
export const AuthHandlers = {
  initLoginForm() {
    const form = document.getElementById('loginForm');
    if (!form) return;

    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      
      const username = document.getElementById('username').value.trim();
      const password = document.getElementById('password').value;
      const errorElement = document.getElementById('loginError');

      try {
        errorElement.textContent = '';
        
        if (!username || !password) {
          throw new Error('Por favor, preencha todos os campos');
        }

        const token = await AuthService.login(username, password);
        AuthService.storeToken(token);
        AuthService.redirectTo('dashboard.html');
      } catch (error) {
        errorElement.textContent = error.message;
      }
    });
  },

  initRegisterForm() {
    const form = document.getElementById('registerForm');
    if (!form) return;

    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      
      const username = document.getElementById('username').value.trim();
      const email = document.getElementById('email').value.trim();
      const password = document.getElementById('password').value;
      const errorElement = document.getElementById('registerError');

      try {
        errorElement.textContent = '';
        
        if (!username || !email || !password) {
          throw new Error('Por favor, preencha todos os campos');
        }

        if (!this.validateEmail(email)) {
          throw new Error('Por favor, insira um email válido');
        }

        if (password.length < 6) {
          throw new Error('A senha deve ter pelo menos 6 caracteres');
        }

        await AuthService.register(username, email, password);
        AuthService.redirectTo('login.html');
      } catch (error) {
        errorElement.textContent = error.message;
      }
    });
  },

  validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
  }
};

// authModule.js - Módulo principal que inicializa tudo
export const AuthModule = {
  init() {
    document.addEventListener('DOMContentLoaded', () => {
      AuthHandlers.initLoginForm();
      AuthHandlers.initRegisterForm();
    });
  }
};

// Inicialização do módulo
AuthModule.init();
