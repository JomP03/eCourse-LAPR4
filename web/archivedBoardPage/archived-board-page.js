function loadArchivePage(){
    if (checkIfLoggedUser()) {
        showCountdown(5, redirectBoardsPage);
    }
}

function checkIfLoggedUser() {
    const token = getTokenCookie();
    if (!token) {
        // Redirect to login page
        window.location.href = window.location.origin;
        return false;
    } else {
        setUserName()
        return true;
    }
}

function redirectBoardsPage(){
    window.location.href = window.location.origin + '/_myboards';
}

function showCountdown(seconds, callback) {
    const countdownElement = document.createElement('div');
    countdownElement.id = 'countdown';
    countdownElement.classList.add('countdown-container');
    document.body.appendChild(countdownElement);

    const countdownTextTopElement = document.createElement('div');
    countdownTextTopElement.classList.add('countdown-text-top');
    countdownTextTopElement.innerText = 'You will be redirected to your boards page in';
    countdownElement.appendChild(countdownTextTopElement);

    const countdownLoadingElement = document.createElement('div');
    countdownLoadingElement.classList.add('countdown-loading');
    countdownElement.appendChild(countdownLoadingElement);

    const countdownTextElement = document.createElement('div');
    countdownTextElement.classList.add('countdown-text');
    countdownTextElement.innerText = `${seconds}`;
    countdownElement.appendChild(countdownTextElement);

    let count = seconds;

    const countdownInterval = setInterval(() => {
        count--;
        countdownTextElement.innerText = `${count}`;

        if (count === 0) {
            clearInterval(countdownInterval);
            document.body.removeChild(countdownElement);
            callback();
        }
    }, 1000);
}