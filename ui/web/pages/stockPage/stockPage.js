const TRADE_URL = '../../trades/trade';
const USER_URL = '../../users/user';
const STOCK_URL = '../../stocks/stock'

let backBtn
let tradeForm
let orderTypeSelect
let limitInput
let limitLabel
let userName
let stockSymbol
let stockPrice
let stockCycle


const userNameFromSession = window.sessionStorage.getItem("username");
const stockNameFromSession = window.sessionStorage.getItem("stockName");

function backPage() {
    window.location.replace("../feed/feed.html");
}

function tradeStock(e) {
    e.preventDefault();
    let tradeDirectionInput = document.getElementsByName('tradeDirection');
    let orderTypeInput = document.getElementById('orderType');
    let quantityInput = document.getElementById('quantity');
    let limitInput = document.getElementById('limit');

    if (quantityInput.value < 1 || limitInput.value < 1) {
        addStockForm.reset();
        window.alert("Error: invalid input. must be positive number!");
        return;
    }

    if (orderTypeInput.value === "MKT") {
        limitInput = 0;
    }

    const formData = {
        symbol: stockNameFromSession,
        userName: userNameFromSession,
        tradeDirection: tradeDirectionInput.value,
        orderType: orderTypeInput.value,
        quantity: parseInt(quantityInput.value),
        limit: parseInt(limitInput.value)
    }

    fetch(TRADE_URL, {
        method: 'POST',
        body: JSON.stringify(formData)
    })
        .then(() => addStockForm.reset())
        .catch(() => {
            window.alert("Error: BAD REQUEST!");
            console.log();
        });
}

function limitVisibility() {
    let option = orderTypeSelect.value;
    if (option === "MKT") {
        limitInput.className = "invisible"
        limitLabel.className = "invisible"
    } else {
        limitInput.className = "form-control"
        limitLabel.className = "form-label"
    }
}

const setUser = () => {
    userName = document.getElementById('name');
    const name = document.createElement("strong");
    name.className = 'col';
    name.textContent = userNameFromSession;
    userName.append(name);
}

const setStockSymbol = () => {
    stockSymbol = document.getElementById('symbol');
    const name = document.createElement("strong");
    name.className = 'col';
    name.textContent = stockNameFromSession;
    stockSymbol.append(name);
}

// function getUserHoldings() {
//     const holdings = document.getElementById('holdings');
//     fetch(USER_URL + `?username=${userNameFromSession}`)
//         .then(res => res.json())
//         .then(data => {
//             holdings.textContent = data.;
//         }).catch(e => console.log(e))
// }

// Events

function getStockPrice() {
    const price = document.getElementById('price');
    fetch(STOCK_URL + `?symbol=${stockNameFromSession}`)
        .then(res => res.json())
        .then(data => {
            price.textContent = data.price;
        }).catch(e => console.log(e))
}

function getStockCycle() {
    const cycle = document.getElementById('cycle');
    fetch(STOCK_URL + `?symbol=${stockNameFromSession}`)
        .then(res => res.json())
        .then(data => {
            cycle.textContent = data.cycle;
        }).catch(e => console.log(e))
}

function init() {
    backBtn = document.getElementById("back");
    tradeForm = document.getElementById("tradeForm");
    orderTypeSelect = document.getElementById("orderType");
    limitInput = document.getElementById("limit");
    limitLabel = document.getElementById("limitLabel");
    stockCycle = document.getElementById("cycle");

    backBtn.addEventListener("click", backPage);
    tradeForm.addEventListener("submit", tradeStock);
    orderTypeSelect.addEventListener("change", limitVisibility);

    setUser();
    setStockSymbol();

    // getUserHoldings();
    getStockPrice();
    getStockCycle();
}

window.addEventListener("DOMContentLoaded", () => {
    init();

    setInterval(getUserHoldings, 2000);
    setInterval(getStockPrice, 2000);
    setInterval(getStockCycle, 2000);
});
