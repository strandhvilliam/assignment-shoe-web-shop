import {Size} from "../models";

class SizeView implements View {
    render(data: Size[]): string {
        return data.map((size: Size) => this.generateMarkup(size)).join('');
    }

    generateMarkup(size: Size): string {
        return `
            
                <button class="product__btn--overlay btn" data-id="${size.id}" type="button">${size.euSize}</button>
            
        `;
    }
} export default new SizeView();