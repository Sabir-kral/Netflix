

async function manager() {
    const username = document.getElementById("username").value;
    const token = localStorage.getItem("accessToken");

    const response = await fetch(`http://localhost:5050/api/manager/findManager?username=${username}`, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    if (!response.ok) {
        document.getElementById("result").innerText = "Error: " + response.status;
        return;
    }

    const data = await response.json();
    document.getElementById("result").innerText = 
    `
    Name:${data.name}
    Username: ${data.username}
    Email:${data.email}

    `;

    if (username==null){
        document.getElementById("result").innerText==""
    }
}