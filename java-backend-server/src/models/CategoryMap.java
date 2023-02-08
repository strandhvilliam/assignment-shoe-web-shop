package models;

public class CategoryMap {

        private final int productId;
        private final int categoryId;

        public CategoryMap(int productId, int categoryId) {
            this.productId = productId;
            this.categoryId = categoryId;
        }

        public int getProductId() {
            return productId;
        }

        public int getCategoryId() {
            return categoryId;
        }



}
