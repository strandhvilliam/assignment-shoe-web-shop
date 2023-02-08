import * as net from 'net';
import {Brand, Category, Color, Customer, Item, Order, OrderDetail, ProductType, Size} from './models.js';

const PORT = 9000;
const IP = '127.0.0.1';
const client = new net.Socket();
const GROUP_SEPARATOR = String.fromCharCode(29);
const NEWLINE = String.fromCharCode(10);

client.connect({
    port: PORT,
    host: IP
});

client.on('connect', () => {
    console.log('Connected to server');
    // client.write('Hello server!\n');
});

const sendRequest = (request: string, obj: any) => {
    const json = JSON.stringify(obj);
    client.write(request + GROUP_SEPARATOR + json + "\n");
}


function BrandFactory(brand: any): Brand {
    return new Brand(brand.id, brand.name);
}

function ProductTypeFactory(productType: any): ProductType {
    const brand: Brand = BrandFactory(productType.brand);
    const colors: Color[] = productType.colors.map((color: any) => colorFactory(color));
    const categories: Category[] = productType.categories.map((category: any) => categoryFactory(category));
    return new ProductType(productType.id, productType.name, brand, productType.price, colors, categories);
}

function sizeFactory(size: any) {
    return new Size(size.id, size.euSize, size.ukSize);
}

function colorFactory(color: any) {
    return new Color(color.id, color.name, color.hex);
}

function categoryFactory(category: any) {
    return new Category(category.id, category.name);
}

function customerFactory(customer: any) {
    return new Customer(customer.id, customer.firstName, customer.lastName, customer.email, customer.address);
}

function itemFactory(item: any) {
    const productType = ProductTypeFactory(item.productType);
    const size = sizeFactory(item.size);
    return new Item(item.id, productType, size);
}

function orderDetailFactory(orderDetail: any) {
    const item = itemFactory(orderDetail.item);
    return new OrderDetail(orderDetail.id, orderDetail.orderId, item, orderDetail.quantity);
}

function orderFactory(order: any) {

    const customer = customerFactory(order.customer);
    const orderDetails: OrderDetail[] = order.orderDetails.map((orderDetail: any) => orderDetailFactory(orderDetail));
    return new Order(order.id, customer, order.status, orderDetails);
}


const handleResponse = (response: string, data: string) => {
    switch (response) {
        case 'GET_ALL_ITEMS': {
            try {
                const obj: object[] = JSON.parse(data);
                const items: Item[] = obj.map((item: any) => {
                    const productType = ProductTypeFactory(item.productType);
                    const size = sizeFactory(item.size);
                    return new Item(item.id, productType, size);
                });
                return items;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'GET_ALL_PRODUCT_TYPES': {
            try {
                const obj: object[] = JSON.parse(data);
                return obj.map((productType: any) => {
                    return ProductTypeFactory(productType);
                });
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'GET_ALL_CATEGORIES': {
            try {
                const obj: object[] = JSON.parse(data);
                const categories: Category[] = obj.map((category: any) => {
                    return categoryFactory(category);
                });
                return categories;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'GET_ALL_COLORS': {
            try {
                const obj: object[] = JSON.parse(data);
                const colors: Color[] = obj.map((color: any) => {
                    return colorFactory(color);
                });
                return colors;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'GET_SIZES_BY_PRODUCT_TYPE': {
            try {
                const obj: object[] = JSON.parse(data);
                const sizes: Size[] = obj.map((size: any) => {
                    return sizeFactory(size);
                });
                return sizes;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'GET_CUSTOMER_BY_ID': {
            try {
                const obj: object = JSON.parse(data);
                return obj;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'GET_ORDER_BY_CUSTOMER_ID': {
            try {
                const obj: object = JSON.parse(data);
                return orderFactory(obj);
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'ADD_TO_CART': {
            try {
                const obj: object = JSON.parse(data);
                return obj;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'LOGIN_CUSTOMER': {
            try {
                const obj: object = JSON.parse(data);
                if (obj == null) return "error";
                return obj;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'REPORT_TOP_TEN_PRODUCTS': {
            try {
                const obj: object = JSON.parse(data);
                // console.log(obj);
                return obj;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'REPORT_CITY_RANKING': {
            try {
                const obj: object = JSON.parse(data);
                return obj;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'REPORT_CUSTOMER_ORDER_RATE': {
            try {
                const obj: object = JSON.parse(data);
                return obj;
            } catch (e) {
                console.log(e);
            }
            break;
        }
        case 'REPORT_CUSTOMER_SPENDING': {
            try {
                const obj: object = JSON.parse(data);
                return obj;
            }
            catch (e) {
                console.log(e);
            }
            break;
        }
    }
}

export const loadData = (request: string, id: string = "") => {
    return new Promise((resolve, reject) => {
        let buffer = '';

        function dataHandler(data: any) {
            if (data.indexOf(NEWLINE) != -1) {
                buffer += data;
                const [response, rest]: string[] = buffer.split(GROUP_SEPARATOR);
                buffer = '';
                const dataToSend = handleResponse(response, rest);
                if (dataToSend != undefined) resolve(dataToSend);

                client.off('data', dataHandler);
            } else {
                buffer += data;
            }
        }

        client.on('data', dataHandler);
        sendRequest(request, id);
    });
}

export const reqAddToCart = async (customerId: string, orderId: string, productTypeId: string, sizeId: string) => {
    return new Promise((resolve, reject) => {
        let buffer = '';

        function dataHandler(data: any) {
            if (data.indexOf(NEWLINE) != -1) {
                buffer += data;
                const [response, rest]: string[] = buffer.split(GROUP_SEPARATOR);
                buffer = '';
                const dataToSend = handleResponse(response, rest);
                if (dataToSend != undefined) resolve(dataToSend);
                client.off('data', dataHandler);
            } else {
                buffer += data;
            }
        }

        client.on('data', dataHandler);
        sendRequest('ADD_TO_CART', [customerId, orderId, productTypeId, sizeId]);
    });
}

export const reqLoginCustomer = async (email: string, password: string) => {
    return new Promise((resolve, reject) => {
        let buffer = '';

        function dataHandler(data: any) {
            if (data.indexOf(NEWLINE) != -1) {
                buffer += data;
                const [response, rest]: string[] = buffer.split(GROUP_SEPARATOR);
                buffer = '';
                const dataToSend = handleResponse(response, rest);
                if (dataToSend != undefined) resolve(dataToSend);
                client.off('data', dataHandler);
            } else {
                buffer += data;
            }
        }

        client.on('data', dataHandler);
        sendRequest('LOGIN_CUSTOMER', [email, password]);
    });
}