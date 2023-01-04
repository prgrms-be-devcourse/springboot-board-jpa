async function join() {
    console.log("start")
    const email = document.getElementById('email').value
    const password = document.getElementById('password').value
    const name = document.getElementById('name').value
    const age = document.getElementById('age').value
    const hobby = document.querySelector('input[name="hobby"]:checked').value
    const url = "/users"
    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            userLoginInfo: {
                email: email,
                password: password
            },
            userSideInfo: {
                name: name,
                age: age,
                hobby: hobby
            }
        })
    })

    if (response.status === 201) {
        window.location.replace("/login.html")
    }

}