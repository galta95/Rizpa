const FEEDURL = '../feed/feed.html';

const login = () => {
    const userAttributes = document.getElementsByClassName("text-input");
    const userName = userAttributes.username.value;
    const password = userAttributes.password.value;

    fetch(`http://localhost:8080/ui_Web_exploded/login?username=${userName}&password=${password}`)
        .then((res) => {
            if (res.ok) {
                window.location.replace(FEEDURL)
            } else {
                window.alert("Username or Password invalid")
            }
        }).catch(e => {
        window.location.replace('/')
    })
}
