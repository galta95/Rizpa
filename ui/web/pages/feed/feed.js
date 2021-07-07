let localUsers = {};

const createListItem = (username) => {
    let listItem = document.createElement('li');
    listItem.className = 'list-group-item'
    listItem.textContent = username;
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
                    const listItem = createListItem(user.name);
                    myList.appendChild(listItem);
                }
            })
        }).catch(e => console.log(e))
}

window.addEventListener("DOMContentLoaded", () => {
    setInterval(getAllUsers, 2000);
});
