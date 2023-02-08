import {Color} from "../models";

class ColorView implements View {
    render(data: Color[]): string {
        const markup = data.map((color: Color) => this.generateMarkup(color)).join('');
        return markup;
    }

    generateMarkup(color: Color): string {
        return `
            <li class="menu__option" data-id="${color.id}">
                <button class="menu__btn" type="button">${color.name}<span class="menu_color-block" style="background-color: ${color.hex};"></span></button>
            </li>
        `;
    }
} export default new ColorView();