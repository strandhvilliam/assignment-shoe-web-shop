const topTenBtn = document.querySelector('#r-top-ten')!;
const cityRankingBtn = document.querySelector('#r-city-ranking')!;
const customerOrderRateBtn = document.querySelector('#r-customer-orders')!;
const customerSpendingBtn = document.querySelector('#r-customer-spending')!;
const resultUrl = 'http://localhost:8000/';
const tableContainer = document.querySelector('.table__content')!;
const getTopTen = async () => {
    console.log('getTopTen')
    const res = await fetch(resultUrl + 'top-ten', {
        method: 'GET',
    });
    return res.text();
}

const getCityRanking = async () => {
    const res = await fetch(resultUrl + 'city-ranking', {
        method: 'GET',
    });
    return res.text();
}

const getCustomerOrderRate = async () => {
    const res = await fetch(resultUrl + 'customer-order-rate', {
        method: 'GET',
    });
    return res.text();
}

const getCustomerSpending = async () => {
    const res = await fetch(resultUrl + 'customer-spending', {
        method: 'GET',
    });
    return res.text();
}

topTenBtn.addEventListener('click', async () => {
    tableContainer.innerHTML = await getTopTen();
});

cityRankingBtn.addEventListener('click', async () => {
    tableContainer.innerHTML = await getCityRanking();
});

customerOrderRateBtn.addEventListener('click', async () => {
    tableContainer.innerHTML = await getCustomerOrderRate();
});

customerSpendingBtn.addEventListener('click', async () => {
    tableContainer.innerHTML = await getCustomerSpending();

});