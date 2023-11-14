function login() {
    event.preventDefault();

    const loginRequest = new XMLHttpRequest();

    loginRequest.onload = function () {
        if (loginRequest.status === 200) {
            document.cookie = "token=" + encodeURIComponent(loginRequest.responseText);
            window.location.href = window.location.origin + '/_myboards';
        } else {
            displayNotification('failure', 'Invalid credentials!');
        }
    };

    loginRequest.open("POST", "/sessions", true);
    loginRequest.setRequestHeader("Content-Type", "application/json");

    const username = document.getElementById('username').value;
    if (username === '') {
        displayNotification('failure', 'Username cannot be empty!');
        return;
    }

    const password = document.getElementById('password').value;
    if (password === '') {
        displayNotification('failure', 'Password cannot be empty!');
        return;
    }

    const data = {
        username: username,
        password: password
    };

    loginRequest.send(JSON.stringify(data));
}


function checkIfLoggedUser() {
    // Request the username to the server
    const usernameRequest = new XMLHttpRequest();

    usernameRequest.onload = function () {
        if (usernameRequest.status === 200) {
            // Redirect to my boards page
            window.location.href = window.location.origin + '/_myboards';
        } else {
            // Clean session cookie
            document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        }
    }

    usernameRequest.open("GET", "/sessions", true);

    const token = getTokenCookie();
    if (token) {
        usernameRequest.setRequestHeader("Authorization", token);
        usernameRequest.send();
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const signinButton = document.getElementById('signin-button');

    usernameInput.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            passwordInput.focus();
        }
    });

    passwordInput.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            signinButton.click();
        }
    });

    signinButton.addEventListener('click', login);

    hidePreloader();
});