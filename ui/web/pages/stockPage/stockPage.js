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
const stockPrice = document.createElement("strong");
const stockCycle = document.createElement("strong");
let stockPriceHeader;
let stockCycleHeader;


const userNameFromSession = window.sessionStorage.getItem("username");
const stockSymbolFromSession = window.sessionStorage.getItem("stockName");

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
        symbol: stockSymbolFromSession,
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
    name.textContent = userNameFromSession.toUpperCase();
    userName.append(name);
}

const setStockSymbol = () => {
    stockSymbol = document.getElementById('symbol');
    const name = document.createElement("strong");
    name.textContent = stockSymbolFromSession;
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
getStockInfo = () => {
    fetch(STOCK_URL + `?symbol=${stockSymbolFromSession}`)
        .then(res => res.json())
        .then(data => {
            stockPrice.textContent = data.price;
            stockCycle.textContent = data.cycle;
            stockCycleHeader.append(stockCycle);
            stockPriceHeader.append(stockPrice);
        }).catch(e => console.log(e))
}

function init() {
    backBtn = document.getElementById("back");
    tradeForm = document.getElementById("tradeForm");
    orderTypeSelect = document.getElementById("orderType");
    limitInput = document.getElementById("limit");
    limitLabel = document.getElementById("limitLabel");
    stockPriceHeader = document.getElementById("price");
    stockCycleHeader = document.getElementById("cycle");

    backBtn.addEventListener("click", backPage);
    tradeForm.addEventListener("submit", tradeStock);
    orderTypeSelect.addEventListener("change", limitVisibility);

    setUser();
    setStockSymbol();
    getStockInfo();

    // getUserHoldings();
}

window.addEventListener("DOMContentLoaded", () => {
    init();

    //setInterval(getUserHoldings, 2000);
    setInterval(getStockInfo, 2000);
});
