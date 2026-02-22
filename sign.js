async function sign() {

    const name = document.getElementById('name').value;
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const resultDiv = document.getElementById('result');


    if (!name || !username || !email || !password) {
        resultDiv.style.color = "#e50914"; 
        resultDiv.innerText = "Error: All fields are required!";
        return;
    }


    const userData = {
        'name': name,
        'username': username,
        'email': email,
        'password': password
    };

    try {
   
        const response = await fetch('http://localhost:5050/api/users/addUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        const data = await response.json();

        if (response.ok) {
            resultDiv.style.color = "#2ecc71"; 
            resultDiv.innerText = "Registration Successful! Redirecting...";
            setTimeout(() => {
                window.location.href = "login.html";
            }, 1000);
  
        } else {
            resultDiv.style.color = "#e50914";
            resultDiv.innerText = data.message || "Registration failed.";
        }
    } catch (error) {
        console.error("Error:", error);
        resultDiv.style.color = "#e50914";
        resultDiv.innerText = "Server is offline. Try again later.";
    }
}