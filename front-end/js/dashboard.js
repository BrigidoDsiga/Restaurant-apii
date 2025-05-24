// authService.js - Serviço de autenticação
export const AuthService = {
  getToken() {
    return localStorage.getItem('token');
  },

  isAuthenticated() {
    return !!this.getToken();
  },

  redirectToLogin() {
    window.location.href = 'login.html';
  },

  logout() {
    localStorage.removeItem('token');
    this.redirectToLogin();
  }
};

// orderService.js - Serviço de pedidos
export const OrderService = {
  API_BASE_URL: '/api',

  async fetchOrders(token) {
    try {
      const response = await fetch(`${this.API_BASE_URL}/orders`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Failed to fetch orders');
      }

      return await response.json();
    } catch (error) {
      console.error('OrderService.fetchOrders error:', error);
      throw error;
    }
  }
};

// orderUI.js - Manipulação da interface
export const OrderUI = {
  renderOrders(orders) {
    const listElement = document.getElementById('ordersList');
    if (!listElement) return;

    listElement.innerHTML = '';

    if (!orders || orders.length === 0) {
      this.showEmptyMessage();
      return;
    }

    orders.forEach(order => {
      listElement.appendChild(this.createOrderElement(order));
    });
  },

  createOrderElement(order) {
    const element = document.createElement('li');
    element.className = 'order-item';
    element.innerHTML = `
      <div class="order-header">
        <span class="order-id">Pedido #${order.id}</span>
        <span class="order-status ${this.getStatusClass(order.status)}">${order.status}</span>
      </div>
      <div class="order-details">
        <span class="order-total">Total: R$ ${order.total.toFixed(2)}</span>
        <span class="order-date">${new Date(order.createdAt).toLocaleString()}</span>
      </div>
    `;
    return element;
  },

  getStatusClass(status) {
    const statusMap = {
      'PENDING': 'status-pending',
      'PROCESSING': 'status-processing',
      'COMPLETED': 'status-completed',
      'CANCELLED': 'status-cancelled'
    };
    return statusMap[status] || '';
  },

  showEmptyMessage() {
    const listElement = document.getElementById('ordersList');
    if (listElement) {
      listElement.innerHTML = '<li class="empty-message">Nenhum pedido encontrado</li>';
    }
  },

  showErrorMessage(message) {
    const listElement = document.getElementById('ordersList');
    if (listElement) {
      listElement.innerHTML = `<li class="error-message">${message}</li>`;
    }
  }
};

// dashboardController.js - Controlador principal
export const DashboardController = {
  async init() {
    if (!AuthService.isAuthenticated()) {
      AuthService.redirectToLogin();
      return;
    }

    try {
      const token = AuthService.getToken();
      const orders = await OrderService.fetchOrders(token);
      OrderUI.renderOrders(orders);
    } catch (error) {
      console.error('DashboardController.init error:', error);
      OrderUI.showErrorMessage('Erro ao carregar pedidos. Tente novamente mais tarde.');
    }
  },

  setupEventListeners() {
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
      logoutButton.addEventListener('click', AuthService.logout);
    }
  }
};

// Inicialização da aplicação
document.addEventListener('DOMContentLoaded', () => {
  DashboardController.init();
  DashboardController.setupEventListeners();
});