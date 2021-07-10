const LOGIN_URL = `../../login`;
const USERNAME = 'username';
const PASSWORD = 'password';
const PERMISSIONS = 'permissions';
const FEEDURL = '../feed/feed.html';

let userName;
let password;
let permissions;

const login = (e) => {
    e.preventDefault();
    fetch(`${LOGIN_URL}?${USERNAME}=${userName.value}&${PASSWORD}=${password.value}&${PERMISSIONS}=${permissions.value}`)
        .then((res) => {
            if (res.ok) {
                window.sessionStorage.setItem("username", userName.value);
                window.location.replace(FEEDURL)
            } else {
                window.alert("Username or Password invalid")
            }
        }).catch(e => {
        console.log(e);
    })
}

window.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector('form');
    userName = document.getElementById("username");
    password = document.getElementById('password');
    permissions = document.getElementById("permissions");
    form.addEventListener("submit", login);
});