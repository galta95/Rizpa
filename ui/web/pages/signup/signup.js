const PORT = 8080;
const APPNAME = `ui_Web_exploded`;
const BASEURL = `http://localhost:${PORT}/${APPNAME}`;
const LOGIN_URL = `${BASEURL}/login`;
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