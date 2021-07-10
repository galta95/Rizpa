const USERS_URL = '../../users';
const USER_URL = '../../users/user';
const STOCKS_URL = '../../stocks'
const STOCK_URL = '../../stocks/stock'
const UPLOAD_URL = '../../upload';

let localUsers = {};
let localStocks = {};
let addMoneyForm;
let addStockForm;
let balance;
let userName
let uploadFileForm;

// Users

const userNameFromSession = window.sessionStorage.getItem("username");

const createUserListItem = (username, permission) => {
    const listItem = document.createElement('li');
    listItem.className = 'list-group-item'
    listItem.textContent = permission + ": " + username;
    return listItem;
}

const getAllUsers = () => {
    const myList = document.querySelector('#users-list');

    fetch(USERS_URL)
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

const setUserHello = () => {
    userName = document.getElementById('userHello');
    const name = document.createElement("strong");
    name.className = 'col';
    name.textContent = userNameFromSession;
    userName.appendChild(name);
}

const addMoney = (e) => {
    e.preventDefault();
    let moneyInput = document.getElementById('addMoneyInput');
    if (isNaN(moneyInput.value)) {
        return;
    }

    const formData = {
        userName: userNameFromSession,
        money: parseInt(moneyInput.value)
    }

    return fetch(USER_URL, {
        method: 'PUT',
        body: JSON.stringify(formData)
    })
        .then(() => addMoneyForm.reset())
        .catch(() => console.log());
}

const getUserBalance = () => {
    balance = document.getElementById('balance');

    fetch(USER_URL + `?username=${userNameFromSession}`)
        .then(res => res.json())
        .then(data => {
            const money = document.createElement("strong");
            money.id = 'money';
            money.className = 'col';
            money.textContent = data.money;
            balance.appendChild(money);
        }).catch(e => console.log(e))
}

const updateUserBalance = () => {
    const money = document.getElementById('money');
    fetch(USER_URL + `?username=${userNameFromSession}`)
        .then(res => res.json())
        .then(data => {
            money.textContent = data.money;
        }).catch(e => console.log(e))
}

// Stock

const addStock = (e) => {
    e.preventDefault();
    let companyNameInput = document.getElementById('companyName');
    let symbolInput = document.getElementById('symbol');
    let numOfSharesInput = document.getElementById('numOfShares');
    let companyValueInput = document.getElementById('companyValue');

    const formData = {
        companyName: companyNameInput.value,
        symbol: symbolInput.value,
        numOfShares: parseInt(numOfSharesInput.value),
        companyValue: parseInt(companyValueInput.value)
    }

    return fetch(STOCK_URL, {
        method: 'POST',
        body: JSON.stringify(formData)
    })
        .then(() => addStockForm.reset())
        .catch(() => console.log());
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

    listItem.className = 'list-group-item list-group-item-action';
    gridItem.className = 'container';

    gridItem.appendChild(createStockRow(stock));
    listItem.appendChild(gridItem);

    return listItem;
}

const getAllStocks = () => {
    const myList = document.querySelector('#stocks-list');

    fetch(STOCKS_URL)
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

// File
const uploadFile = (e) => {
    e.preventDefault();
    const selectedFile = document.getElementById('inputFile').files[0];

    let formData = new FormData();
    formData.append("file", selectedFile);

    fetch(UPLOAD_URL, {method: 'POST', body: formData})
        .then(() => window.alert("File Uploaded Successfully!"))
        .catch(() => window.alert("Error: File did not uploaded"));
}

// Events

function init() {
    addMoneyForm = document.getElementById("addMoneyForm");
    addStockForm = document.getElementById("addStockForm");
    uploadFileForm = document.getElementById('formFile');

    addMoneyForm.addEventListener("submit", addMoney);
    addStockForm.addEventListener("submit", addStock);
    uploadFileForm.addEventListener("submit", uploadFile);

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