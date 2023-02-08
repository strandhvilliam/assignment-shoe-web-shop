import {Order, OrderDetail} from "../models";

class OrderView implements View {
    render(data: Order): string {
        return data.orderDetails.map((orderDetail: OrderDetail) => this.generateMarkup(orderDetail)).join('');

    }

    generateMarkup(detail: OrderDetail): string {
        return `
           <div class="cart__item">
                <div class="cart__item-img">
                    <img src="https://img01.ztat.net/article/spp-media-p1/3cf7e79923b83c1da9f8e190a78df533/b8d578124c5e4ae1a4ce5aff11c17280.jpg?imwidth=300&filter=packshot" alt="product">
                </div>
                <div class="cart__item-info">
                    <h2 class="cart__item-title">${detail.item.productType.name}</h2>
                    <span class="cart__item-color">Brand: ${detail.item.productType.brand.name}</span>
                    <span class="cart__item-size">Size: ${detail.item.size.euSize}</span>
                    <span class="cart__item-price">${detail.item.productType.price} SEK</span>
                </div>
            </div> 
       `
    }

} export default new OrderView();