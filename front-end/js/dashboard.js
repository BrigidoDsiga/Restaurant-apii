document.addEventListener('DOMContentLoaded', async () => {
  const token = localStorage.getItem('token');
  if (!token) {
    window.location.href = 'login.html';
    return;
  }

  const response = await fetch('/api/orders', {
    headers: {
      'Authorization': 'Bearer ' + token
    }
  });

  if (response.ok) {
    const orders = await response.json();
    const list = document.getElementById('ordersList');
    orders.forEach(order => {
      const li = document.createElement('li');
      li.textContent = `Pedido #${order.id} - Total: R$${order.total} - Status: ${order.status}`;
      list.appendChild(li);
    });
  }
});

function logout() {
  localStorage.removeItem('token');
  window.location.href = 'login.html';
}
