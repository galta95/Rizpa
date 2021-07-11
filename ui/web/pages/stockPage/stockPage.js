const TRADE_URL = '../../trades/trade';

let backBtn
let tradeForm
let orderTypeSelect
let limitInput
let limitLabel

const userNameFromSession = window.sessionStorage.getItem("username");

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
        symbol: "NEED TO ADD", // TODO
        userName: userNameFromSession,
        tradeDirection: tradeDirectionInput.value,
        orderType: orderTypeInput.value,
        quantity: parseInt(quantityInput.value),
        limit: parseInt(limitInput.value)
    }

    return fetch(TRADE_URL, {
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

// Events

function init() {
    backBtn = document.getElementById("back");
    tradeForm = document.getElementById("tradeForm");
    orderTypeSelect = document.getElementById("orderType");
    limitInput = document.getElementById("limit");
    limitLabel = document.getElementById("limitLabel");

    backBtn.addEventListener("click", backPage);
    tradeForm.addEventListener("submit", tradeStock);
    orderTypeSelect.addEventListener("change", limitVisibility);

    setUserHello();
    getAllUsers();
    getAllStocks();
    getUserBalance();
}

window.addEventListener("DOMContentLoaded", () => {
    init();
});

// placeholder="Disabled input" aria-label="Disabled input example" disabled