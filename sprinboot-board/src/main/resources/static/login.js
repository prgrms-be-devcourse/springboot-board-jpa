async function login() {
    console.log("start")
    const email = document.getElementById('email').value
    const password = document.getElementById('password').value
    const url = "/users/login"
    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    });

    if (response.status === 200) {

        window.location.replace("/login-home.html")
    }
}