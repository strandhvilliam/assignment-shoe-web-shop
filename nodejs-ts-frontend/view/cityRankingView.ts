class CityRankingView {


    render(data: any): string {
        data = data.filter((item: string[]) => item[0] != null);
        const markup = `
        <thead id="table-head">
        <tr>
            <th>City</th>
            <th>Revenue</th>
        </tr>
        </thead>
        <tbody id="table-body">
        ${data.map((item: string) => this.generateMarkup(item[0], item[1])).join('')}
        </tbody>
`

        return markup;
    }

    generateMarkup(city: string, count: string): string {

        return `
        <tr>
            <td>${city}</td>
            <td>${count}</td>
        </tr>
        `;
    }



} export default new CityRankingView();