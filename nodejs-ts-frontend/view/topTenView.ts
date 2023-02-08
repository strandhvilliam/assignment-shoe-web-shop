import {ProductType} from "../models";

class TopTenView {


    render(data: any): string {
        data = data.filter((item: string[]) => item[0] != null);
        const markup = `
        <thead id="table-head">
        <tr>
            <th>Product</th>
            <th>Amount sold</th>
        </tr>
        </thead>
        <tbody id="table-body">
        ${data.map((item: string) => this.generateMarkup(item[0], item[1])).join('')}
        </tbody>
`

        return markup;
    }

    generateMarkup(product: string, count: string): string {

        return `
        <tr>
            <td>${product}</td>
            <td>${count}</td>
        </tr>
        `;
    }



} export default new TopTenView();