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
const stockHoldings = document.createElement("strong");
let stockPriceHeader;
let stockCycleHeader;
let stockHoldingsHeader;

const userNameFromSession = window.sessionStorage.getItem("username");
const stockSymbolFromSession = window.sessionStorage.getItem("stockName");

function backPage() {
    window.location.replace("../feed/feed.html");
}

const tradeStock = (e) => {
    e.preventDefault();
    let tradeDirectionInput = "buy";
    let orderTypeInput = document.getElementById('orderType');
    let quantityInput = document.getElementById('quantity');
    let limitInput = document.getElementById('limit');
    const sellCheck = document.getElementById("sell");

    if (parseInt(quantityInput.value) < 1 || (!limitInput.disabled && parseInt(limitInput.value) < 1)) {
        tradeForm.reset();
        window.alert("Error: invalid input. must be positive number!");
        return;
    }

    if (orderTypeInput.value === "MKT") {
        limitInput.value = 0;
    }

    if (sellCheck.checked) {
        tradeDirectionInput = "sell";
    }

    const formData = {
        symbol: stockSymbolFromSession,
        userName: userNameFromSession,
        tradeDirection: tradeDirectionInput,
        orderType: orderTypeInput.value,
        quantity: parseInt(quantityInput.value),
        limit: parseInt(limitInput.value)
    }

    fetch(TRADE_URL, {
        method: 'POST',
        body: JSON.stringify(formData)
    })
        .then((res) => {
            if (!res.ok) {
                window.alert("Bad request!");
            }
            window.alert("Success!");
            tradeForm.reset()
        })
        .catch(() => {
            window.alert("Error in system");
            console.log();
        });
}

function limitVisibility() {
    let option = orderTypeSelect.value;
    if (option === "MKT") {
        limitInput.disabled = true;
        limitLabel.disabled = true;
    } else {
        limitInput.disabled = false;
        limitLabel.disabled = false;
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

const getUserHoldings = () => {
    fetch(USER_URL + `?username=${userNameFromSession}`)
        .then(res => res.json())
        .then(data => {
            stockHoldings.textContent = data.holdings[stockSymbolFromSession];
            stockHoldingsHeader.append(stockHoldings);
        }).catch(e => console.log(e))
}

// Events
const getStockInfo = () => {
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
    stockHoldingsHeader = document.getElementById("holdings");

    backBtn.addEventListener("click", backPage);
    tradeForm.addEventListener("submit", tradeStock);
    orderTypeSelect.addEventListener("change", limitVisibility);

    setUser();
    setStockSymbol();
    getStockInfo();
    getUserHoldings();

    // getUserHoldings();
}

window.addEventListener("DOMContentLoaded", () => {
    init();

    setInterval(getUserHoldings, 2000);
    setInterval(getStockInfo, 2000);
});
