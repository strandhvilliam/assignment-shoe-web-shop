import {Category} from "../models";

class CategoryView implements View {
    render(data: Category[]): string {
        return data.map((category: Category) => this.generateMarkup(category)).join('');
    }

    generateMarkup(category: Category): string {
        return `
            <li class="menu__option" data-id="${category.id}">
                <button class="menu__btn" type="button">${category.name}</button>
            </li>
        `;
    }


} export default new CategoryView();