import {ProductType} from "../models";

class CustomerSpendingView {


    render(data: any): string {
        data = data.filter((item: string[]) => item[0] != null);
        const markup = `
        <thead id="table-head">
        <tr>
            <th>Customer</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody id="table-body">
        ${data.map((item: string) => this.generateMarkup(item[0], item[1])).join('')}
        </tbody>
`

        return markup;
    }

    generateMarkup(customer: string, count: string): string {

        return `
        <tr>
            <td>${customer}</td>
            <td>${count}</td>
        </tr>
        `;
    }



} export default new CustomerSpendingView();