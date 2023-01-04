async function getDetails() {
    const url = "/users"

    const response = await fetch(url)
    if (response.status === 200) {
        console.log(response.json())
    }
}