function setUserName() {

    const usernameRequest = new XMLHttpRequest();

    usernameRequest.onload = function () {
        if (usernameRequest.status === 200) {
            let usernameElement = document.getElementById("username");
            if (usernameElement) {
                usernameElement.innerText = usernameRequest.responseText;
            }
        } else {
            // Clean session cookie
            document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        }
    };

    usernameRequest.open("GET", "/sessions", true);

    const token = getTokenCookie();
    if (token) {
        usernameRequest.setRequestHeader("Authorization", token);
    }

    usernameRequest.send();
}


function isPostItOnwer(postIt) {
    const userIdRequest = new XMLHttpRequest();

    userIdRequest.onload = function () {
        if (userIdRequest.status === 200) {
            if (postIt.owner == userIdRequest.responseText)
                enableEditButtons(true);
            else
                enableEditButtons(false);
        } else {
            // Clean session cookie
            document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        }
    };

    userIdRequest.open("GET", "/sessions/id", true);

    const token = getTokenCookie();
    if (token) {
        userIdRequest.setRequestHeader("Authorization", token);
    }

    userIdRequest.send();
}


function getTokenCookie() {
    if (!document.cookie) return null;
    const cookies = document.cookie.split(";").map(cookie => cookie.trim());
    const tokenCookie = cookies.find(cookie => cookie.startsWith("token="));
    return decodeURIComponent(tokenCookie.split("=")[1]);
}

// JavaScript to hide the preloader once the page is fully loaded
function hidePreloader() {
    document.querySelector('.preloader').style.display = 'none';
}

function displayNotification(type, message) {
    let iconClass = '';
    let colorClass = '';


    switch (type) {
        case 'success':
            var snd = new Audio('/commons/sound/success_sound.mp3');
            snd.play();
            iconClass = 'fa-check-circle';
            colorClass = 'success';
            break;
        case 'info':
            var snd = new Audio('/commons/sound/info_sound.mp3');
            snd.play();
            iconClass = 'fa-info-circle';
            colorClass = 'info';
            break;
        case 'failure':
            var snd = new Audio('/commons/sound/fail_sound.mp3');
            snd.play();
            iconClass = 'fa-times-circle';
            colorClass = 'failure';
            break;
        default:
            return; // Invalid type, exit the function
    }

    const notiDiv = document.createElement('div');
    notiDiv.classList.add('alert', 'showAlert', colorClass);

    const iconSpan = document.createElement('span');
    iconSpan.classList.add('fas', iconClass);

    const messageSpan = document.createElement('span');
    messageSpan.classList.add('msg');
    messageSpan.textContent = message;

    const closeDiv = document.createElement('div');
    closeDiv.classList.add('close-btn');

    const closeIcon = document.createElement('span');
    closeIcon.classList.add('fas', 'fa-times');

    closeDiv.appendChild(closeIcon);

    notiDiv.appendChild(iconSpan);
    notiDiv.appendChild(messageSpan);
    notiDiv.appendChild(closeDiv);

    const notisContainer = document.querySelector('.notification-container');

    notisContainer.insertBefore(notiDiv, notisContainer.firstChild);

    closeDiv.addEventListener('click', function () {
        hideAlert(notiDiv);
    });

    setTimeout(function () {
        notiDiv.classList.add('show');
    }, 10);

    setTimeout(function () {
        hideAlert(notiDiv);
    }, 5000);
}

function hideAlert(notiDiv) {
    notiDiv.classList.remove('show');
    notiDiv.classList.add('hide');
    setTimeout(function () {
        notiDiv.remove();
    }, 1000);
}

