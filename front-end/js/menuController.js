import { MenuService } from './menuService.js';
import { MenuUI } from './menuUI.js';

export const MenuController = {
  currentCategory: null,

  async init() {
    MenuUI.init();
    this.setupEventListeners();
    
    try {
      MenuUI.showLoading(true);
      const categories = await MenuService.getMenuCategories();
      MenuUI.renderCategories(categories);
      
      // Load first category by default
      if (categories.length > 0) {
        this.currentCategory = categories[0].id;
        await this.loadCategoryItems(this.currentCategory);
      }
    } catch (error) {
      MenuUI.showError('Failed to load menu categories');
    } finally {
      MenuUI.showLoading(false);
    }
  },

  setupEventListeners() {
    window.addEventListener('menuCategorySelected', (e) => {
      this.handleCategorySelection(e.detail);
    });

    window.addEventListener('menuSearch', (e) => {
      this.handleSearch(e.detail);
    });

    window.addEventListener('menuResetSearch', () => {
      if (this.currentCategory) {
        this.loadCategoryItems(this.currentCategory);
      }
    });

    window.addEventListener('menuRetryLoad', () => {
      this.init();
    });
  },

  async handleCategorySelection(categoryId) {
    this.currentCategory = categoryId;
    try {
      MenuUI.showLoading(true);
      await this.loadCategoryItems(categoryId);
    } catch (error) {
      MenuUI.showError('Failed to load menu items');
    } finally {
      MenuUI.showLoading(false);
    }
  },

  async loadCategoryItems(categoryId) {
    const items = await MenuService.getMenuItems(categoryId);
    MenuUI.renderMenuItems(items);
  },

  async handleSearch(query) {
    try {
      MenuUI.showLoading(true);
      const results = await MenuService.searchMenuItems(query);
      MenuUI.renderMenuItems(results);
    } catch (error) {
      MenuUI.showError('Failed to search menu');
    } finally {
      MenuUI.showLoading(false);
    }
  }
};