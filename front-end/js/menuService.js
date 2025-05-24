// menu.js - Restaurant Menu System
class RestaurantMenu {
  constructor() {
    this.menuData = [];
    this.categories = [];
    this.currentCategory = null;
    this.init();
  }

  async init() {
    // DOM Elements
    this.elements = {
      menuContainer: document.querySelector('.menu-container'),
      categoriesContainer: document.querySelector('#menuCategories'),
      itemsContainer: document.querySelector('#menuItems'),
      searchInput: document.querySelector('#menuSearch'),
      loadingIndicator: document.querySelector('#menuLoading'),
      filterButtons: document.querySelectorAll('.filter-btn')
    };

    // Event Listeners
    this.setupEventListeners();

    // Load Menu Data
    await this.loadMenuData();
    this.renderCategories();
    this.renderAllItems();
  }

  async loadMenuData() {
    try {
      this.showLoading(true);
      // In a real app, this would be a fetch call to your API
      // const response = await fetch('/api/menu');
      // this.menuData = await response.json();
      
      // Mock data for demonstration
      this.menuData = [
        { id: 1, name: 'Margherita Pizza', description: 'Classic tomato and mozzarella', price: 12.99, category: 'pizza', isVegetarian: true },
        { id: 2, name: 'Pepperoni Pizza', description: 'Tomato, mozzarella and pepperoni', price: 14.99, category: 'pizza' },
        { id: 3, name: 'Caesar Salad', description: 'Romaine, croutons, parmesan', price: 8.99, category: 'salad', isVegetarian: true },
        { id: 4, name: 'Garlic Bread', description: 'Fresh baked with garlic butter', price: 4.99, category: 'appetizer', isVegetarian: true },
        { id: 5, name: 'Tiramisu', description: 'Classic Italian dessert', price: 6.99, category: 'dessert', isVegetarian: true }
      ];
      
      this.categories = [...new Set(this.menuData.map(item => item.category))];
      
      // Simulate network delay
      await new Promise(resolve => setTimeout(resolve, 500));
    } catch (error) {
      console.error('Error loading menu data:', error);
      this.showError('Failed to load menu. Please try again.');
    } finally {
      this.showLoading(false);
    }
  }

  setupEventListeners() {
    // Category selection
    if (this.elements.categoriesContainer) {
      this.elements.categoriesContainer.addEventListener('click', (e) => {
        if (e.target.classList.contains('category-btn')) {
          const category = e.target.dataset.category;
          this.currentCategory = category === 'all' ? null : category;
          this.renderItems();
          this.updateActiveCategoryButton(e.target);
        }
      });
    }

    // Search functionality
    if (this.elements.searchInput) {
      this.elements.searchInput.addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase();
        this.filterItems(query);
      });
    }

    // Filter buttons (vegetarian, etc.)
    this.elements.filterButtons.forEach(button => {
      button.addEventListener('click', (e) => {
        this.toggleFilter(e.target.dataset.filter);
      });
    });
  }

  renderCategories() {
    if (!this.elements.categoriesContainer) return;
    
    const categoriesHTML = `
      <button class="category-btn active" data-category="all">All</button>
      ${this.categories.map(category => `
        <button class="category-btn" data-category="${category}">
          ${this.capitalizeFirstLetter(category)}
        </button>
      `).join('')}
    `;
    
    this.elements.categoriesContainer.innerHTML = categoriesHTML;
  }

  renderAllItems() {
    this.renderItems(this.menuData);
  }

  renderItems(items = null) {
    if (!this.elements.itemsContainer) return;
    
    const itemsToRender = items || this.getFilteredItems();
    
    if (itemsToRender.length === 0) {
      this.elements.itemsContainer.innerHTML = `
        <div class="empty-message">
          No items found in this category
        </div>
      `;
      return;
    }
    
    this.elements.itemsContainer.innerHTML = itemsToRender.map(item => `
      <div class="menu-item" data-category="${item.category}">
        <div class="item-header">
          <h3 class="item-name">${item.name}</h3>
          ${item.isVegetarian ? '<span class="vegetarian-badge">Vegetarian</span>' : ''}
          <span class="item-price">$${item.price.toFixed(2)}</span>
        </div>
        <p class="item-description">${item.description}</p>
        <button class="add-to-cart" data-item-id="${item.id}">
          Add to Order
        </button>
      </div>
    `).join('');
  }

  getFilteredItems() {
    let filteredItems = [...this.menuData];
    
    // Filter by category
    if (this.currentCategory) {
      filteredItems = filteredItems.filter(item => item.category === this.currentCategory);
    }
    
    // Filter by search query
    if (this.searchQuery) {
      filteredItems = filteredItems.filter(item => 
        item.name.toLowerCase().includes(this.searchQuery) || 
        item.description.toLowerCase().includes(this.searchQuery)
      );
    }
    
    // Filter by dietary preferences
    if (this.activeFilters.has('vegetarian')) {
      filteredItems = filteredItems.filter(item => item.isVegetarian);
    }
    
    return filteredItems;
  }

  filterItems(query) {
    this.searchQuery = query.toLowerCase();
    this.renderItems();
  }

  toggleFilter(filterName) {
    if (!this.activeFilters) {
      this.activeFilters = new Set();
    }
    
    if (this.activeFilters.has(filterName)) {
      this.activeFilters.delete(filterName);
    } else {
      this.activeFilters.add(filterName);
    }
    
    this.renderItems();
  }

  updateActiveCategoryButton(activeButton) {
    document.querySelectorAll('.category-btn').forEach(btn => {
      btn.classList.remove('active');
    });
    activeButton.classList.add('active');
  }

  showLoading(show) {
    if (this.elements.loadingIndicator) {
      this.elements.loadingIndicator.style.display = show ? 'block' : 'none';
    }
  }

  showError(message) {
    if (this.elements.itemsContainer) {
      this.elements.itemsContainer.innerHTML = `
        <div class="error-message">
          ${message}
          <button class="retry-btn">Try Again</button>
        </div>
      `;
      
      document.querySelector('.retry-btn')?.addEventListener('click', () => {
        this.loadMenuData();
      });
    }
  }

  capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }
}

// Initialize the menu when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
  const restaurantMenu = new RestaurantMenu();
  
  // Make available globally if needed
  window.restaurantMenu = restaurantMenu;
});