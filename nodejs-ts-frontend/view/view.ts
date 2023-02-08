interface View {
    render(data: any): string;
    generateMarkup(data: any): string;
}