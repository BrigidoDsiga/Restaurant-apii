const API_URL = 'http://localhost:8080/api';
const token = localStorage.getItem('token');

if (!token) {
  window.location.href = 'login.html';
}

async function fetchOrders() {
  try {
    const response = await fetch(`${API_URL}/admin/orders`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });

    if (!response.ok) {
      throw new Error('Erro ao buscar pedidos');
    }

    const orders = await response.json();
    renderOrders(orders);
  } catch (error) {
    console.error(error.message);
    document.getElementById('ordersList').innerHTML = '<li>Erro ao carregar pedidos.</li>';
  }
}

function renderOrders(orders) {
  const list = document.getElementById('ordersList');
  list.innerHTML = '';

  orders.forEach(order => {
    const li = document.createElement('li');
    li.innerHTML = `
      <strong>Pedido #${order.id}</strong><br>
      Cliente: ${order.customerName}<br>
      Itens: ${order.items.map(i => i.name).join(', ')}<br>
      Status: ${order.status}
      <button onclick="updateStatus(${order.id}, 'FINALIZADO')">Finalizar</button>
    `;
    list.appendChild(li);
  });
}

async function updateStatus(orderId, status) {
  try {
    const response = await fetch(`${API_URL}/admin/orders/${orderId}/status`, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ status })
    });

    if (!response.ok) throw new Error('Erro ao atualizar status');

    fetchOrders(); // Recarrega os pedidos
  } catch (error) {
    console.error(error.message);
  }
}

function logout() {
  localStorage.removeItem('token');
  window.location.href = 'login.html';
}

document.addEventListener('DOMContentLoaded', fetchOrders);
