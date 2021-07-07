let localUsers = {};
let localStocks = {};

const createUserListItem = (username) => {
    let listItem = document.createElement('li');
    listItem.className = 'list-group-item'
    listItem.textContent = username;
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

    await fetch(`http://localhost:8080/ui_war_exploded/users`)
        .then(res => res.json())
        .then(data => {
            data.users.forEach(user => {
                if (localUsers[user.name] === undefined) {
                    localUsers[user.name] = user.name;
                    const listItem = createUserListItem(user.name);
                    myList.appendChild(listItem);
                }
            })
        }).catch(e => console.log(e))
}

const getAllStocks = async () => {
    const myList = document.querySelector('#stocks-list');

    await fetch(`http://localhost:8080/ui_war_exploded/stocks`)
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
});

window.addEventListener("DOMContentLoaded", () => {
    setInterval(getAllStocks, 2000);
});
