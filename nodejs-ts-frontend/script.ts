const GROUP_SEPARATOR = String.fromCharCode(29);
const url = 'http://localhost:8000/';
const reportBtn: HTMLButtonElement = document.querySelector('#report-btn')!;
const showCartBtn: HTMLButtonElement = document.querySelector('#cart-btn')!;
const showAllBtn: HTMLButtonElement = document.querySelector('#all-btn')!;
const showCategoriesBtn: HTMLButtonElement = document.querySelector('#category-btn')!;
const showColorsBtn: HTMLButtonElement = document.querySelector('#color-btn')!;
const loginBtn: HTMLButtonElement = document.querySelector('#login-btn')!;
const cart: HTMLElement = document.querySelector('.cart')!;
const customerName: HTMLElement = document.querySelector('#customer-name')!;
const overlay: HTMLElement = document.querySelector('.overlay')!;
const emailInput: HTMLInputElement = document.querySelector('#email-input')!;
const passwordInput: HTMLInputElement = document.querySelector('#password-input')!;

const state = {
    id: "-1",
    firstname: 'Firstname',
    lastname: 'Lastname',
    orderId: "-1",
}

interface GetterFunction {
    (): Promise<string>;
}


const showReportsPage = () => {
    window.location.href = 'http://localhost:8000/reports';
}

const getCustomerById = async (id: string) => {
    const res = await fetch(url + 'customer/' + id, {
        method: 'GET',
    });
    return res.text();
}

const getCategories = async () => {
    const res = await fetch(url + 'categories', {
        method: 'GET',
    });
    return res.text();
}

const getColors = async () => {
    const res = await fetch(url + 'colors', {
        method: 'GET',
    });
    return res.text();
}

const getAllProducts = async () => {
    const res = await fetch(url + 'all', {
        method: 'GET',
    });
    return res.text();
}

const getProductsByCategory = async (category: string) => {
    const res = await fetch(url + 'category/' + category, {
        method: 'GET',
    });
    return res.text();
}

const getProductsByColor = async (color: string) => {
    const res = await fetch(url + 'color/' + color, {
        method: 'GET',
    });
    return res.text();
}

const getSizesByProductType = async (id: string) => {
    const res = await fetch(url + 'sizes/' + id, {
        method: 'GET',
    });
    return res.text();
}

const getOrderByCustomerId = async (id: string) => {
    const res = await fetch(url + 'order/' + id, {
        method: 'GET',
    });
    return res.text();
}

const addProductToOrder = async (id: string, orderId: string, productId: string, sizeId: string) => {
    const res = await fetch(url + 'addToCart/' + id + '/' + orderId + '/' + productId + '/' + sizeId, {
        method: 'GET',
});
    return res.text();
}

const loginCustomer = async (email: string, password: string) => {
    const res = await fetch(url + 'login/' + email + '/' + password, {
        method: 'GET',
    });
    return res.text();
}


const renderCustomer = (func: Promise<string>) => {
    func.then(res => {
        if (res === "error") {
            alert("Wrong email or password");
            return;
        }
        state.id = JSON.parse(res).id;
        state.firstname = JSON.parse(res).firstName;
        state.lastname = JSON.parse(res).lastName;
        customerName.innerHTML = state.firstname + ' ' + state.lastname;
        renderOrder(getOrderByCustomerId(state.id));
    })

}

const renderOrder = (func: Promise<string>) => {
    func.then(res => {
        const array: string[] = res.split(GROUP_SEPARATOR);
        cart.querySelector(".cart__products")!.innerHTML = array[0];
        cart.querySelector(".cart__total-price")!.innerHTML = array[1] + " SEK";
        state.orderId = array[2];
    })
}

const renderProducts = (func: GetterFunction) => {
    func().then(res => {
        document.querySelector('#content')!.innerHTML = res;
        // document.querySelectorAll('.product').forEach((product: Element) => {
        //     renderSizes(getSizesByProductType, product as HTMLElement);
        // });
        //
        document.querySelectorAll('.product__btn').forEach((btn: Element) => {

            btn.addEventListener('click', () => {
                const productEl = btn.parentElement!.parentElement!;
                renderSizes(getSizesByProductType, productEl as HTMLElement);
                const productOverlay = btn.parentElement!.parentElement!.querySelector('.product__image--overlay')!;
                productOverlay.classList.toggle('hidden');
                setTimeout(() => {
                    productOverlay.querySelectorAll(".product__btn--overlay").forEach((b: Element) => {
                        b.addEventListener('click', () => {
                            b.parentElement!.classList.toggle('hidden');
                            const productId = b.parentElement!.parentElement!.parentElement!.dataset.id!;
                            const sizeId = (b as HTMLElement).dataset.id!;
                            console.log("Adding product " + productId + " with size " + sizeId + " to order")
                            addProductToOrder(state.id, state.orderId, productId, sizeId).then(r => {
                                console.log(r);
                                overlay.classList.toggle('hidden');
                                cart.classList.toggle('hidden');
                                getOrderByCustomerId(state.id).then(r => {
                                    const array: string[] = r.split(GROUP_SEPARATOR);
                                    cart.querySelector(".cart__products")!.innerHTML = array[0];
                                    cart.querySelector(".cart__total-price")!.innerHTML = array[1] + " SEK";
                                    state.orderId = array[2];
                                });
                                if (r.startsWith("ERROR")) {
                                    alert(r); //TODO: replace with something better
                                }
                            });

                        });
                    });
                }, 1000);


            });
        });

    })

};

const renderSizes = (func: (id: string) => Promise<string>, element: HTMLElement) => {
    const id = element.dataset.id!;
    func(id).then(res => {
        element!.querySelector('.product__image--overlay')!.innerHTML = res;
        //TODO: add event listeners to the buttons to open cart and reload the cart content
    })
}

const renderMenu = (func: GetterFunction, element: HTMLElement) => {
    func().then(res => {
        element!.innerHTML = res;
    })
}


showCartBtn!.addEventListener('click', () => {
    if (document.querySelector(".login")!.classList.contains("hidden")) {
        cart.classList.toggle('hidden');
        overlay.classList.toggle('hidden');
    }
});

overlay!.addEventListener('click', () => {
    if (document.querySelector(".login")!.classList.contains("hidden")) {
        cart.classList.toggle('hidden');
        overlay.classList.toggle('hidden');
    }
});

showCategoriesBtn!.addEventListener('click', () => {
    const categories: HTMLElement = document.querySelector('#categories__menu')!;
    categories.classList.toggle('hidden');
    if (categories.innerHTML === "") {
        renderMenu(getCategories, categories);
    }
});

showColorsBtn!.addEventListener('click', () => {
    const colors: HTMLElement = document.querySelector('#colors__menu')!;
    colors.classList.toggle('hidden');
    if (colors.innerHTML === "") {
        renderMenu(getColors, colors);
    }
});

showAllBtn!.addEventListener('click', () => {
    renderProducts(getAllProducts);
});

loginBtn!.addEventListener('click', () => {

    const email = emailInput.value;
    const password = passwordInput.value;

    if (email === "" || password === "") {
        alert("Email or password can not be empty");
        return;
    }
    renderCustomer(loginCustomer(email, password));
    document.querySelector(".login")!.classList.toggle('hidden');
    overlay.classList.toggle('hidden');
});

reportBtn!.addEventListener('click', () => {
    showReportsPage();
});

// renderCustomer(loginCustomer("wille@mailme.com", "1111"));


// getCustomerById("2").then(res => {
//     console.log(res);
// });

// console.log(getProducts().);


// renderProducts();