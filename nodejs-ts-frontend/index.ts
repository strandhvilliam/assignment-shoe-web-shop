import express from 'express';
import path from "path";
import {fileURLToPath} from "url";
import {loadData, reqAddToCart, reqLoginCustomer} from "./tcp.js";
import productView from "./view/productView.js";
import {Brand, Category, Color, Customer, Item, Order, ProductType} from "./models.js";
import categoryView from "./view/categoryView.js";
import colorView from "./view/colorView.js";
import sizeView from "./view/sizeView.js";
import orderView from "./view/orderView.js";
import topTenView from "./view/topTenView.js";
import cityRankingView from "./view/cityRankingView.js";
import customerOrderRateView from "./view/customerOrderRateView.js";
import customerSpendingView from "./view/customerSpendingView.js";

const GROUP_SEPARATOR = String.fromCharCode(29);
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const PORT = 8000;

const app = express();

app.use(express.static(path.join(__dirname, '../')));

app.listen(PORT, () => console.log(`Server listening on port ${PORT}`));

app.get('/', (req, res) => {
    res.sendFile('index.html', {root: './'});
});

app.get('/reports', (req, res) => {
    res.sendFile('reports.html', {root: './'});
});

// app.use('all-products', express.static(path.join(__dirname, '../')));


app.get('/all', (req, res, next) => {
    // @ts-ignore
    loadData('GET_ALL_PRODUCT_TYPES').then((data: ProductType[]) => {
        /*const products: ProductType[] = data.map((item: Item) => item.productType)
        const unique: ProductType[] = products.filter((v, i, a) => a.findIndex(t => (t.id === v.id)) === i);
        res.send(productView.render(unique));*/
        res.send(productView.render(data));
    });
})

app.get('/categories', (req, res, next) => {
    // @ts-ignore
    loadData('GET_ALL_CATEGORIES').then((data: Category[]) => {
        res.send(categoryView.render(data));
    });
});

app.get('/colors', (req, res, next) => {
    // @ts-ignore
    loadData('GET_ALL_COLORS').then((data: Color[]) => {
        res.send(colorView.render(data));
    });
});

app.get('/sizes/:id', (req, res, next) => {
// @ts-ignore
    loadData('GET_SIZES_BY_PRODUCT_TYPE', req.params.id).then((data: Size[]) => {
        res.send(sizeView.render(data));
    });
});

app.get('/customer/:id', (req, res, next) => {
    // @ts-ignore
    loadData('GET_CUSTOMER_BY_ID', req.params.id).then((data: Customer) => {
        res.send({id: data.id, firstName: data.firstName, lastName: data.lastName});
    });
});

app.get('/order/:id', (req, res, next) => {
    // @ts-ignore
    loadData('GET_ORDER_BY_CUSTOMER_ID', req.params.id).then((data: Order) => {
        const total = data.orderDetails
            .map((orderDetail) => orderDetail.item.productType.price)
            .reduce((a, b) => a + b, 0)
        res.send(orderView.render(data) + GROUP_SEPARATOR + total + GROUP_SEPARATOR + data.id);
    });
});

app.get('/addToCart/:customerId/:orderId/:productId/:sizeId', (req, res, next) => {
    reqAddToCart(req.params.customerId, req.params.orderId, req.params.productId, req.params.sizeId).then((data: any) => {
        res.send(data);
    });
});

app.get('/login/:email/:password', (req, res, next) => {
    // @ts-ignore
    reqLoginCustomer(req.params.email, req.params.password).then((data: any) => {
        if (data instanceof Customer) {
            res.send({id: data.id, firstName: data.firstName, lastName: data.lastName});
        } else {
            res.send(data);
        }
    });
});

app.get('/top-ten', (req, res, next) => {
// @ts-ignore
    loadData('REPORT_TOP_TEN_PRODUCTS').then((data: string[]) => {
        res.send(topTenView.render(data));
    });
});

app.get('/city-ranking', (req, res, next) => {
    // @ts-ignore
    loadData('REPORT_CITY_RANKING').then((data: any) => {
        res.send(cityRankingView.render(data));
    });
});

app.get('/customer-order-rate', (req, res, next) => {
// @ts-ignore
    loadData('REPORT_CUSTOMER_ORDER_RATE').then((data: any) => {
        res.send(customerOrderRateView.render(data));
    });
});

app.get('/customer-spending', (req, res, next) => {
// @ts-ignore
    loadData('REPORT_CUSTOMER_SPENDING').then((data: any) => {
        res.send(customerSpendingView.render(data));
    });
});



