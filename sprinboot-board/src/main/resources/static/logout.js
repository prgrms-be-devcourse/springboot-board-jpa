async function logout() {
    const url = "/users/logout"
    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        }
    });

    if (response.status === 200) {
        window.location.replace("/login.html")
    }
}