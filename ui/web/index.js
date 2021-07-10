const userNameSession = window.sessionStorage.getItem("username");
if (userNameSession) {
    window.location.replace("pages/feed/feed.html");
} else {
    window.location.replace("pages/signup/signup.html");
}
