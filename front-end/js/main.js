// main.js - Ponto de entrada principal da aplicação

// Importação dos módulos
import { AuthService } from './authService.js';
import { OrderService } from './orderService.js';
import { OrderUI } from './orderUI.js';
import { DashboardController } from './dashboardController.js';
import { MenuController } from './menuController.js';

document.addEventListener('DOMContentLoaded', () => {
  MenuController.init();
});

// Configuração inicial
document.addEventListener('DOMContentLoaded', () => {
  // Verifica autenticação
  if (!AuthService.isAuthenticated()) {
    AuthService.redirectToLogin();
    return;
  }

  // Inicializa o controlador do dashboard
  DashboardController.init();
  DashboardController.setupEventListeners();

  // Configura o botão de logout
  const logoutButton = document.getElementById('logoutButton');
  if (logoutButton) {
    logoutButton.addEventListener('click', (e) => {
      e.preventDefault();
      AuthService.logout();
    });
  }

  // Carrega os pedidos inicialmente
  loadOrders();

  // Configura o auto-refresh (opcional)
  setInterval(loadOrders, 30000); // Atualiza a cada 30 segundos
});

// Função para carregar pedidos
async function loadOrders() {
  try {
    const token = AuthService.getToken();
    const orders = await OrderService.fetchOrders(token);
    
    // Ordena pedidos por data (mais recente primeiro)
    const sortedOrders = orders.sort((a, b) => 
      new Date(b.createdAt) - new Date(a.createdAt)
    );
    
    OrderUI.renderOrders(sortedOrders);
    
    // Atualiza contador de pedidos
    updateOrderCount(sortedOrders.length);
    
  } catch (error) {
    console.error('Erro ao carregar pedidos:', error);
    OrderUI.showErrorMessage('Falha ao carregar pedidos. Tente recarregar a página.');
  }
}

// Atualiza contador de pedidos
function updateOrderCount(count) {
  const counterElement = document.getElementById('orderCount');
  if (counterElement) {
    counterElement.textContent = `Total de Pedidos: ${count}`;
  }
}

// Exportações para uso no HTML (quando necessário)
window.Dashboard = {
  refreshOrders: loadOrders,
  logout: AuthService.logout
};