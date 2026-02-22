const signButton = document.getElementById("sign");
const resultDiv = document.getElementById("result");

signButton.onclick = async function() {
    const name = document.getElementById("name").value.trim();
    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    if (!name || !username || !email || !password) {
        resultDiv.innerText = "Please fill in all fields!";
        return;
    }

    const token = localStorage.getItem("accessToken"); 

    const newManager = { 
        name: name, 
        username: username, 
        email: email, 
        password: password 
    };

    try {
        const res = await fetch("http://localhost:5050/api/manager/addManager", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(newManager)
        });

        if (res.ok) {
            const data = await res.json().catch(() => "Manager added successfully");
            resultDiv.style.color = "green";
            resultDiv.innerText = typeof data === "string" ? data : "Manager added successfully";
            
            document.getElementById("name").value = "";
            document.getElementById("username").value = "";
            document.getElementById("email").value = "";
            document.getElementById("password").value = "";
        } else {
            const errText = await res.text();
            resultDiv.style.color = "red";
            resultDiv.innerText = "Error: " + errText;
        }
    } catch(err) {
        resultDiv.style.color = "red";
        resultDiv.innerText = "Error: " + err.message;
    }
};