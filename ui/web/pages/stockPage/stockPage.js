const TRADE_URL = '../../trades/trade';
const USER_URL = '../../users/user';
const STOCK_URL = '../../stocks/stock'
const STOCK_DEALS_URL = '../../stocks/stock/deals'

const ADMIN = 'admin';
let backBtn
let tradeForm
let orderTypeSelect
let limitInput
let limitLabel
let stockSymbol
const stockPrice = document.createElement("strong");
const stockCycle = document.createElement("strong");
const stockHoldings = document.createElement("strong");
const stockName = document.createElement("strong");
let stockPriceHeader;
let stockCycleHeader;
let stockHoldingsHeader;
let stockNameHeader;
let dealsTable;
let buysTable;
let sellsTable;

const userNameFromSession = window.sessionStorage.getItem("username");
const stockSymbolFromSession = window.sessionStorage.getItem("stockName");
const permissionsFromSession = window.sessionStorage.getItem("permissions");

const backPage = () => {
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
        price: parseInt(limitInput.value)
    }

    fetch(TRADE_URL, {
        method: 'POST',
        body: JSON.stringify(formData)
    })
        .then((res) => {
            if (!res.ok) {
                window.alert("Bad request!");
            } else {
                window.alert("Success!");
            }
            tradeForm.reset()
        })
        .catch(() => {
            window.alert("Error in system");
            console.log();
        });
    limitInput.disabled = false;
    limitLabel.disabled = false;
}

const limitVisibility = () => {
    let option = orderTypeSelect.value;
    if (option === "MKT") {
        limitInput.disabled = true;
        limitLabel.disabled = true;
    } else {
        limitInput.disabled = false;
        limitLabel.disabled = false;
    }
}

const setStockSymbol = () => {
    stockSymbol = document.getElementById('symbol');
    const name = document.createElement("strong");
    name.textContent = stockSymbolFromSession;
    stockSymbol.append(name);
}

const getUserHoldingsAndAlert = () => {
    fetch(USER_URL + `?username=${userNameFromSession}`)
        .then(res => res.json())
        .then(data => {
            let holdings = data.holdings[stockSymbolFromSession];
            if (!holdings) {
                holdings = 0
            }
            stockHoldings.textContent = holdings;
            stockHoldingsHeader.append(stockHoldings);

            if (data.isNewDealAlert) {
                let deal = data.newDeal
                let msg = "New " + deal.symbol + " deal!\n" +
                    "The deal was between: " + deal.consumer + " and " + deal.producer + "\n" +
                    "Deal value : " + deal.dealValue + "\n" +
                    "Num of shares: " + deal.numOfShares + ", Price: " + deal.price + "\n";

                window.alert(msg);

                const formData = {
                    userName: userNameFromSession,
                    money: 0
                }

                return fetch(USER_URL, {
                    method: 'PUT',
                    body: JSON.stringify(formData)
                }).catch(() => console.log());
            }
        }).catch(e => console.log(e))
}

const updateQueue = (table, tableBodyId, queue) => {
    document.getElementById(tableBodyId).remove();
    const newTb = document.createElement("tbody");
    newTb.id = tableBodyId;
    table.append(newTb);
    if (queue.length > 0) {
        queue.forEach((item, i) => {
            const dealRow = newTb.insertRow(i);
            const cell0 = dealRow.insertCell(0);
            const cell1 = dealRow.insertCell(1);
            const cell2 = dealRow.insertCell(2);
            const cell3 = dealRow.insertCell(3);
            const cell4 = dealRow.insertCell(4);

            cell0.textContent = item.date;
            cell1.textContent = item.numOfShares;
            cell2.textContent = item.price;
            cell3.textContent = item.orderType;
            cell4.textContent = item.userName;
        })
    }
}

const getStockInfo = () => {
    fetch(STOCK_URL + `?symbol=${stockSymbolFromSession}`)
        .then(res => res.json())
        .then(data => {
            stockPrice.textContent = data.price;
            stockCycle.textContent = data.cycle;
            stockName.textContent = data.companyName;

            stockCycleHeader.append(stockCycle);
            stockPriceHeader.append(stockPrice);
            stockNameHeader.append(stockName);

            if (permissionsFromSession === ADMIN) {
                    updateQueue(sellsTable, "sellsTableBody", data.sells);
                    updateQueue(buysTable, "buysTableBody", data.buys);
            }
        }).catch(e => console.log(e))
}

const getStockDeals = () => {
    fetch(STOCK_DEALS_URL + `?symbol=${stockSymbolFromSession}`)
        .then(res => res.json())
        .then(data => {
            if (data.deals.length > 0) {
                addDealsToTable(data.deals);
            }
        }).catch(e => console.log(e))
}

const addDealsToTable = (deals) => {
    document.getElementById("tb").remove();
    const newTb = document.createElement("tbody");
    newTb.id = "tb";
    dealsTable.append(newTb);
    deals.forEach((deal, i) => {
        const dealRow = newTb.insertRow(i);
        const cell0 = dealRow.insertCell(0);
        const cell1 = dealRow.insertCell(1);
        const cell2 = dealRow.insertCell(2);

        cell0.textContent = deal.date;
        cell1.textContent = deal.numOfShares;
        cell2.textContent = deal.price;
    })
}

function init() {
    backBtn = document.getElementById("back");
    orderTypeSelect = document.getElementById("orderType");
    limitInput = document.getElementById("limit");
    limitLabel = document.getElementById("limitLabel");
    stockPriceHeader = document.getElementById("price");
    stockCycleHeader = document.getElementById("cycle");
    stockNameHeader = document.getElementById("name");
    stockHoldingsHeader = document.getElementById("holdings");
    dealsTable = document.getElementById("dealsTable");

    if (permissionsFromSession === ADMIN) {
        buysTable = document.getElementById("buysTable");
        sellsTable = document.getElementById("sellsTable");
    } else {
        tradeForm = document.getElementById("tradeForm");
        tradeForm.addEventListener("submit", tradeStock);
        orderTypeSelect.addEventListener("change", limitVisibility);
    }

    backBtn.addEventListener("click", backPage);

    setStockSymbol();
    getStockInfo();
    getStockDeals();
    getUserHoldingsAndAlert();
}

window.addEventListener("DOMContentLoaded", () => {
    init();

    setInterval(getStockInfo, 2000);
    setInterval(getStockDeals, 2000);
    setInterval(getUserHoldingsAndAlert, 2000);
});
