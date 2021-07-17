const USERS_URL = '../../users';
const USER_URL = '../../users/user';
const STOCKS_URL = '../../stocks'
const STOCK_URL = '../../stocks/stock'
const UPLOAD_URL = '../../upload';
const brokerStockPage = "../stockPage/stockPage.html";
const adminStockPage = "../stockPage/adminStockPage.html";
const CHAT_URL = '../../chat';

const ADMIN = 'admin';
let localUsers = {};
let localStocks = {};
let addMoneyForm;
let addStockForm;
let balance;
let uploadFileForm;
let inputFile;
let uploadFileBtn;
let chatForm;
let chatArea;
let chatInputString = "";
let chatInput;
let currentChatSize = 0;
let newChatSize = 0;
// Users

const userNameFromSession = window.sessionStorage.getItem("username");
const permissionsFromSession = window.sessionStorage.getItem("permissions");

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
    const userHello = document.getElementById('userHello');
    userHello.textContent = `Hello ${userNameFromSession},`;
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

const addMovementsToTable = (movements) => {
    document.getElementById("tb").remove();
    const newTb = document.createElement("tbody");
    newTb.id = "tb";
    dealsTable.append(newTb);
    movements.forEach((movement, i) => {
        const dealRow = newTb.insertRow(i);
        const cell0 = dealRow.insertCell(0);
        const cell1 = dealRow.insertCell(1);
        const cell2 = dealRow.insertCell(2);
        const cell3 = dealRow.insertCell(3);
        const cell4 = dealRow.insertCell(4);
        const cell5 = dealRow.insertCell(5);

        cell0.textContent = movement.movementType;
        cell1.textContent = movement.date;
        cell2.textContent = movement.symbol;
        cell3.textContent = movement.amount;
        cell4.textContent = movement.prevBalance;
        cell5.textContent = movement.afterBalance;
    })
}

const getUserMovements = () => {
    fetch(USER_URL + `?username=${userNameFromSession}`)
        .then(res => res.json())
        .then(data => {
            if (data.movments.length > 0) {
                addMovementsToTable(data.movments);
            }
        }).catch(e => console.log(e))
}

const updateUserBalanceAndAlerts = () => {
    const money = document.getElementById('money');
    fetch(USER_URL + `?username=${userNameFromSession}`)
        .then(res => res.json())
        .then(data => {
            money.textContent = data.money;

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

const moveToStockScreen = (stockName) => {
    window.sessionStorage.setItem("stockName", stockName);
    if (permissionsFromSession === ADMIN) {
        window.location.replace(adminStockPage);
    } else {
        window.location.replace(brokerStockPage);
    }
}

const createStockCol = (textContent) => {
    const listItemCol = document.createElement('div');
    listItemCol.className = 'col text-center';
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
    let listItem = document.createElement('a');

    listItem.className = 'list-group-item list-group-item-action';
    listItem.addEventListener("click", () => {
        const stockName = listItem.children[0].children[1].textContent;
        moveToStockScreen(stockName);
    });

    listItem.appendChild(createStockRow(stock));
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
const hasFile = () => {
    if (inputFile.value) {
        uploadFileBtn.disabled = false;
    } else {
        uploadFileBtn.disabled = true;
    }
}

const uploadFile = (e) => {
    e.preventDefault();
    const selectedFile = document.getElementById('inputFile').files[0];

    let formData = new FormData();
    formData.append("file", selectedFile);

    fetch(UPLOAD_URL, {method: 'POST', body: formData})
        .then((res) => {
            if (res.ok) {
                uploadFileForm.reset();
                uploadFileBtn.disabled = true;
                window.alert("File Uploaded Successfully!");
            } else {
                window.alert("Error: File did not uploaded");
            }
        })
        .catch(() => window.alert("Error: File did not uploaded"));
}

const updateChatInput = () => {
    chatInputString += chatInput.value;
}

const sendMessage = (e) => {
    e.preventDefault();
    fetch(CHAT_URL + `?username=${userNameFromSession}&userstring=${chatInputString}`, {method: 'POST'})
        .then(() => {
            chatInputString = "";
            chatForm.reset();
            getChatData();
        });
}

const getChatData = () => {
    fetch(CHAT_URL)
        .then(res => res.json())
        .then(data => {
            newChatSize = data.chatDataList.length;
            if (newChatSize > currentChatSize) {
                data.chatDataList.forEach((chatBlock, i) => {
                    if (i > currentChatSize - 1) {
                        const chatString = chatBlock.username + ": " + chatBlock.chatString;
                        const line = document.createElement("div");
                        line.textContent = chatString;
                        chatArea.append(line);
                    }
                })
                currentChatSize = newChatSize;
            }
        })
}

// Events

const init = () => {
    addMoneyForm = document.getElementById("addMoneyForm");
    addStockForm = document.getElementById("addStockForm");
    uploadFileForm = document.getElementById('formFile');
    inputFile = document.getElementById('inputFile');
    uploadFileBtn = document.getElementById('uploadFileBtn');
    chatForm = document.getElementById('chatform');
    chatArea = document.getElementById('chatarea');
    chatInput = document.getElementById('userString');

    addMoneyForm.addEventListener("submit", addMoney);
    addStockForm.addEventListener("submit", addStock);
    uploadFileForm.addEventListener("submit", uploadFile);
    inputFile.addEventListener("change", hasFile);
    chatForm.addEventListener("submit", sendMessage);
    chatInput.addEventListener("change", updateChatInput);

    setUserHello();
    getAllUsers();
    getAllStocks();
    getUserBalance();
    getUserMovements();
}

window.addEventListener("DOMContentLoaded", () => {
    init();

    setInterval(getAllUsers, 2000);
    setInterval(getAllStocks, 2000);
    setInterval(getChatData, 2000);

    if (permissionsFromSession === "admin") {
        document.getElementById("brokerWindow").remove();
    } else {
        setInterval(updateUserBalanceAndAlerts, 2000);
        setInterval(getUserMovements, 2000);
    }
});

