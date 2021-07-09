const USERS_URL = '../../users';
const STOCKS_URL = '../../stocks'

let localUsers = {};
let localStocks = {};

const createUserListItem = (username, permission) => {
    let listItem = document.createElement('li');
    listItem.className = 'list-group-item'
    listItem.textContent = permission + ": " + username;
    return listItem;
}

const createStockListItem = (stock) => {
    let listItem = document.createElement('li');
    listItem.className = 'list-group-item'
    listItem.textContent = stock;
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

const getAllStocks = async () => {
    const myList = document.querySelector('#stocks-list');

    await fetch(STOCKS_URL)
        .then(res => res.json())
        .then(data => {
            data.stocks.forEach(stock => {
                if (localStocks[stock.name] === undefined) {
                    localStocks[stock.name] = stock.name;
                    const listItem = createStockListItem(stock.name);
                    myList.appendChild(listItem);
                }
            })
        }).catch(e => console.log(e))
}

window.addEventListener("DOMContentLoaded", () => {
    setInterval(getAllUsers, 2000);
    setInterval(getAllStocks, 2000);
});