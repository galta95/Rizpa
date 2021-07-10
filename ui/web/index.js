const userNameSessionStorage = window.sessionStorage.getItem("username");

if (userNameSessionStorage) {
    window.location.replace("pages/feed/feed.html");
} else {
    window.location.replace("pages/signup/signup.html");
}

