export const MenuUI = {
  elements: {
    categoriesContainer: document.getElementById('menuCategories'),
    itemsContainer: document.getElementById('menuItems'),
    searchInput: document.getElementById('menuSearch'),
    loadingIndicator: document.getElementById('menuLoading')
  },

  init() {
    this.bindEvents();
    this.showLoading(false);
  },

  bindEvents() {
    if (this.elements.searchInput) {
      this.elements.searchInput.addEventListener('input', (e) => {
        const query = e.target.value.trim();
        if (query.length > 2) {
          window.dispatchEvent(new CustomEvent('menuSearch', { detail: query }));
        } else if (query.length === 0) {
          window.dispatchEvent(new CustomEvent('menuResetSearch'));
        }
      });
    }
  },

  renderCategories(categories) {
    if (!this.elements.categoriesContainer) return;

    this.elements.categoriesContainer.innerHTML = categories
      .map(category => `
        <button class="category-btn" 
                data-category-id="${category.id}"
                aria-label="${category.name}">
          ${category.name}
        </button>
      `).join('');

    document.querySelectorAll('.category-btn').forEach(btn => {
      btn.addEventListener('click', (e) => {
        const categoryId = e.currentTarget.dataset.categoryId;
        window.dispatchEvent(new CustomEvent('menuCategorySelected', { detail: categoryId }));
      });
    });
  },

  renderMenuItems(items) {
    if (!this.elements.itemsContainer) return;

    this.elements.itemsContainer.innerHTML = items
      .map(item => `
        <div class="menu-item" data-item-id="${item.id}">
          <h3>${item.name}</h3>
          <p class="description">${item.description}</p>
          <p class="price">$${item.price.toFixed(2)}</p>
          <button class="add-to-cart" data-item-id="${item.id}">
            Add to Order
          </button>
        </div>
      `).join('');
  },

  showLoading(show = true) {
    if (this.elements.loadingIndicator) {
      this.elements.loadingIndicator.style.display = show ? 'block' : 'none';
    }
  },

  showError(message) {
    if (this.elements.itemsContainer) {
      this.elements.itemsContainer.innerHTML = `
        <div class="menu-error">
          <p>${message}</p>
          <button class="retry-btn">Try Again</button>
        </div>
      `;

      document.querySelector('.retry-btn')?.addEventListener('click', () => {
        window.dispatchEvent(new CustomEvent('menuRetryLoad'));
      });
    }
  }
};