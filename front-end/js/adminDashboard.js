
// ordersService.js - Camada de serviço para operações com pedidos
export const OrdersService = {
  API_URL: 'http://localhost:8080/api',

  async getOrders(token) {
    try {
      const response = await fetch(`${this.API_URL}/admin/orders`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Erro ao buscar pedidos');
      }

      return await response.json();
    } catch (error) {
      console.error('Failed to fetch orders:', error);
      throw error;
    }
  },

  async updateOrderStatus(token, orderId, status) {
    try {
      const response = await fetch(`${this.API_URL}/admin/orders/${orderId}/status`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ status })
      });

      if (!response.ok) {
        throw new Error('Erro ao atualizar status');
      }

      return await response.json();
    } catch (error) {
      console.error('Failed to update order status:', error);
      throw error;
    }
  }
};

// authService.js - Camada de serviço para autenticação
export const AuthService = {
  getToken() {
    return localStorage.getItem('token');
  },

  logout() {
    localStorage.removeItem('token');
    window.location.href = 'login.html';
  },

  redirectIfUnauthenticated() {
    if (!this.getToken()) {
      window.location.href = 'login.html';
    }
  }
};

// ordersUI.js - Manipulação da interface do usuário
export const OrdersUI = {
  renderOrders(orders) {
    const list = document.getElementById('ordersList');
    if (!list) return;

    list.innerHTML = '';

    if (!orders || orders.length === 0) {
      list.innerHTML = '<li class="no-orders">Nenhum pedido encontrado</li>';
      return;
    }

    orders.forEach(order => {
      const li = document.createElement('li');
      li.className = 'order-item';
      li.innerHTML = this.createOrderHTML(order);
      list.appendChild(li);
    });
  },

  createOrderHTML(order) {
    return `
      <div class="order-header">
        <strong class="order-id">Pedido #${order.id}</strong>
        <span class="order-status ${this.getStatusClass(order.status)}">${order.status}</span>
      </div>
      <div class="order-body">
        <p><strong>Cliente:</strong> ${order.customerName}</p>
        <p><strong>Itens:</strong> ${order.items.map(i => i.name).join(', ')}</p>
        <p><strong>Total:</strong> R$ ${order.total.toFixed(2)}</p>
        <p><strong>Data:</strong> ${new Date(order.createdAt).toLocaleString()}</p>
      </div>
      <div class="order-actions">
        ${this.createActionButtons(order)}
      </div>
    `;
  },

  createActionButtons(order) {
    if (order.status === 'FINALIZADO' || order.status === 'CANCELADO') {
      return '';
    }

    return `
      <button class="btn btn-complete" onclick="OrdersHandlers.updateStatus(${order.id}, 'FINALIZADO')">
        Finalizar
      </button>
      <button class="btn btn-cancel" onclick="OrdersHandlers.updateStatus(${order.id}, 'CANCELADO')">
        Cancelar
      </button>
    `;
  },

  getStatusClass(status) {
    const statusClasses = {
      'PENDENTE': 'status-pending',
      'PREPARANDO': 'status-preparing',
      'ENVIADO': 'status-shipped',
      'FINALIZADO': 'status-completed',
      'CANCELADO': 'status-cancelled'
    };
    return statusClasses[status] || '';
  },

  showError(message) {
    const list = document.getElementById('ordersList');
    if (list) {
      list.innerHTML = `<li class="error">${message}</li>`;
    }
  }
};

// ordersHandlers.js - Manipuladores de eventos
export const OrdersHandlers = {
  async loadOrders() {
    try {
      const token = AuthService.getToken();
      const orders = await OrdersService.getOrders(token);
      OrdersUI.renderOrders(orders);
    } catch (error) {
      OrdersUI.showError(error.message);
    }
  },

  async updateStatus(orderId, status) {
    try {
      const token = AuthService.getToken();
      await OrdersService.updateOrderStatus(token, orderId, status);
      this.loadOrders(); // Recarrega a lista de pedidos
    } catch (error) {
      console.error('Failed to update order status:', error);
      OrdersUI.showError('Falha ao atualizar status do pedido');
    }
  },

  initLogoutButton() {
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
      logoutBtn.addEventListener('click', AuthService.logout);
    }
  }
};

// dashboardModule.js - Módulo principal
export const DashboardModule = {
  init() {
    document.addEventListener('DOMContentLoaded', () => {
      AuthService.redirectIfUnauthenticated();
      OrdersHandlers.loadOrders();
      OrdersHandlers.initLogoutButton();
    });
  }
};

// Inicialização do módulo
DashboardModule.init();