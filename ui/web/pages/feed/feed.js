const USERS_URL = '../../users';
const USER_URL = '../../users/user';
const STOCKS_URL = '../../stocks'
const STOCK_URL = '../../stocks/stock'

let localUsers = {};
let localStocks = {};
let addMoneyBtn;
let addStockBtn;

// Users

const userNameFromSession = window.sessionStorage.getItem("username");

const createUserListItem = (username, permission) => {
    const listItem = document.createElement('li');
    listItem.className = 'list-group-item'
    listItem.textContent = permission + ": " + username;
    return listItem;
}

const getAllUsers = async () => {
    const myList = document.querySelector('#users-list');

    await fetch(USERS_URL)
        .then(res => res.json())
        .then(data => {
            data.users.forEach(user => {
                if (localUsers[user.name] === undefined) {
                    localUsers[user.name] = user.name;
                    localUsers[user.permission] = user.permission;
                    const listItem = createUserListItem(user.name, user.permission);
                    myList.appendChild(listItem);
                }
            })
        }).catch(e => console.log(e))
}

// User

const addMoney = async () => {
    let moneyInput = document.getElementById('addMoneyInput');
    if (!moneyInput.value || isNaN(moneyInput.value)) {
        return;
    }

    const formData = {
        userName: userNameFromSession,
        money: parseInt(moneyInput.value)
    }

    return fetch(USER_URL, {
        method: 'PUT',
        body: JSON.stringify(formData)
    }).then((response => response.json()))
}

const getUserBalance = async () => {
    const money = document.querySelector('#balance');

    await fetch(USER_URL + `?username=${userNameFromSession}`)
        .then(res => res.json())
        .then(data => {
            money.textContent = data.money;
        }).catch(e => console.log(e))
}


// Stock

const addStock = async () => {
    let companyNameInput = document.getElementById('companyName');
    let symbolInput = document.getElementById('symbol');
    let numOfSharesInput = document.getElementById('numOfShares');
    let companyValueInput = document.getElementById('companyValue');

    if (!companyNameInput.value || !symbolInput.value ||
        !numOfSharesInput.value || isNaN(numOfSharesInput.value) ||
        !companyValueInput.value || isNaN(companyValueInput.value)) {
        return;
    }

    const formData = {
        companyName: companyNameInput.value,
        symbol: symbolInput.value,
        numOfShares: parseInt(numOfSharesInput.value),
        companyValue: parseInt(companyValueInput.value)
    }

    return fetch(STOCK_URL, {
        method: 'POST',
        body: JSON.stringify(formData)
    }).then((response => response.json()))
}

// Stocks

const createStockCol = (textContent) => {
    const listItemCol = document.createElement('div');
    listItemCol.className = 'col';
    listItemCol.textContent = textContent;
    return listItemCol;
}

const createStockRow = (stock) => {
    const listItemRow = document.createElement('div');
    listItemRow.className = 'row';

    listItemRow.appendChild(createStockCol(stock.companyName));
    listItemRow.appendChild(createStockCol(stock.symbol));
    listItemRow.appendChild(createStockCol(stock.price));
    listItemRow.appendChild(createStockCol(stock.cycle));
    return listItemRow;
}

const createStockListItem = (stock) => {
    let listItem = document.createElement('li');
    let gridItem = document.createElement('div');

    listItem.className = 'list-group-item';
    gridItem.className = 'container';

    gridItem.appendChild(createStockRow(stock));
    listItem.appendChild(gridItem);
    return listItem;
}

const getAllStocks = async () => {
    const myList = document.querySelector('#stocks-list');

    await fetch(STOCKS_URL)
        .then(res => res.json())
        .then(data => {
            data.stocks.forEach(stock => {
                if (localStocks[stock.companyName] === undefined) {
                    localStocks[stock.companyName] = stock.companyName;
                    const listItem = createStockListItem(stock);
                    myList.appendChild(listItem);
                }
            })
        }).catch(e => console.log(e))
}

// Events

window.addEventListener("DOMContentLoaded", () => {
    addMoneyBtn = document.getElementById("addMoneyBtn");
    addMoneyBtn.addEventListener("click", addMoney);
    addStockBtn = document.getElementById("addStockBtn");
    addStockBtn.addEventListener("click", addStock);

    setInterval(getAllUsers, 2000);
    setInterval(getAllStocks, 2000);
    setInterval(getUserBalance, 2000);
});