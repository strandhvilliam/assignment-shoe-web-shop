
export class Brand {
    public id: number;
    public name: string;

    constructor(id: number, name: string) {
        this.id = id;
        this.name = name;
    }
}

export class Category {
public id: number;
    public name: string;

    constructor(id: number, name: string) {
        this.id = id;
        this.name = name;
    }
}

export class City {
    public id: number;
    public name: string;

    constructor(id: number, name: string) {
        this.id = id;
        this.name = name;
    }
}

export class Color {
    public id: number;
    public name: string;
    public hex: string;

    constructor(id: number, name: string, hex: string) {
        this.id = id;
        this.name = name;
        this.hex = hex;
    }
}
export class Customer {
    public id: number;
    public firstName: string;
    public lastName: string;
    public email: string;
    public password?: string;
    public address?: string;
    public city?: City;

    constructor(id: number, firstName: string, lastName: string, email: string, password?: string, address?: string, city?: City) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
    }

}

export class Item {
    public id: number;
    public productType: ProductType;
    public size: Size;

    constructor(id: number, productType: ProductType, size: Size) {
        this.id = id;
        this.productType = productType;
        this.size = size;
    }
}

export class Order {
    public id: number;
    public customer: Customer;
    public status: string;
    public orderDetails: OrderDetail[];

    constructor(id: number, customer: Customer, status: string, orderDetails: OrderDetail[]) {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.orderDetails = orderDetails;
    }
}

export class OrderDetail {
    public id: number;
    public orderId: number;
    public item: Item;
    public quantity: number;

    constructor(id: number, orderId: number, item: Item, quantity: number) {
        this.id = id;
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
    }
}

export class ProductType {
public id: number;
    public name: string;
    public brand: Brand;
    public price: number;
    public colors: Color[];
    public categories: Category[];

    constructor(id: number, name: string, brand: Brand, price: number, colors: Color[], categories: Category[]) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.colors = colors;
        this.categories = categories;
    }
}

export class Size {
    public id: number;
    public euSize: number;
    public ukSize: number;

    constructor(id: number, euSize: number, ukSize: number) {
        this.id = id;
        this.euSize = euSize;
        this.ukSize = ukSize;
    }
}

