const TRADE_URL = '../../trades/trade';

let backBtn
let tradeForm

const userNameFromSession = window.sessionStorage.getItem("username");

function backPage() {
    window.location.replace("pages/feed/feed.html");
}

function tradeStock(e) {
    e.preventDefault();
    let tradeDirectionInput = document.getElementsByName('tradeDirection');
    let orderTypeInput = document.getElementById('orderType');
    let quantityInput = document.getElementById('quantity');
    let limitInput = document.getElementById('limit');

    const formData = {
        symbol: "NEED TO ADD", // TODO
        date: "NEED TO ADD", // TODO
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
        .catch(() => console.log());
}

// Events

function init() {
    backBtn = document.getElementById("back");
    tradeForm = document.getElementById("tradeForm");

    backBtn.addEventListener("click", backPage);
    tradeForm.addEventListener("submit", tradeStock);

    setUserHello();
    getAllUsers();
    getAllStocks();
    getUserBalance();
}

window.addEventListener("DOMContentLoaded", () => {
    init();

    setInterval(getAllUsers, 2000);
    setInterval(getAllStocks, 2000);
    setInterval(updateUserBalance, 2000);
});
