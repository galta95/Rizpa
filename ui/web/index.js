const userNameSession = window.localStorage.getItem("username");

if (userNameSession) {
    window.location.replace("pages/feed/feed.html");
} else {
    window.location.replace("pages/signup/signup.html");
}
