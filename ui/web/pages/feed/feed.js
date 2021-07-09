const USERS_URL = '../../users';
const STOCKS_URL = '../../stocks'

let localUsers = {};
let localStocks = {};

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

window.addEventListener("DOMContentLoaded", () => {
    setInterval(getAllUsers, 2000);
    setInterval(getAllStocks, 2000);
});