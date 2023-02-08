import {ProductType} from "../models";

class ProductView implements View {


    render(data: ProductType[]) {
        const markup = data.map(item => this.generateMarkup(item)).join('');
        return markup;
    }


    generateMarkup(product: ProductType) {
        return `
        <div class="product" data-id="${product.id}">
            <div class="product__image">
                <img src="https://img01.ztat.net/article/spp-media-p1/3cf7e79923b83c1da9f8e190a78df533/b8d578124c5e4ae1a4ce5aff11c17280.jpg?imwidth=300&filter=packshot" alt="product image">
                <div class="product__image--overlay hidden"></div>
            </div>
            <div class="product__info">
                <div>
                    <h3 class="product__title">${product.name}</h3>
                    <p class="product__price">${product.price} :-</p>
                </div>
                <button class="product__btn btn" type="button">Add to cart</button>
            </div>
        </div>
        `

    }


} export default new ProductView();