async function video() {
    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const ageLimit = document.getElementById('ageLimit').value;
    const active = document.getElementById('active').checked; 
    const uploadedBy = document.getElementById('uploadedBy').value;
    const resultDiv = document.getElementById('result');

    if (!name || !description || !ageLimit || !uploadedBy) {
        resultDiv.style.color = "#e50914"; 
        resultDiv.innerText = "Error: All fields are required!";
        return;
    }

    const videoData = {
        name: name,
        description: description,
        ageLimit: ageLimit,
        active: active,
        uploadedBy: uploadedBy
    };

    try {
        const token = localStorage.getItem("accessToken");
   
        const response = await fetch('http://localhost:5050/api/videos/add', {
            method: 'POST',
            headers: {
                'Authorization': "Bearer " + token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(videoData)
        });


        let data = {};
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            data = await response.json();
        }

        if (response.ok) {
            resultDiv.style.color = "#2ecc71"; 
            resultDiv.innerText = "Added Successful! Redirecting...";
            setTimeout(() => {
                window.location.href = "main.html";
            }, 1000);
        } else {
            resultDiv.style.color = "#e50914";
            resultDiv.innerText = data.message || "Add failed.";
        }
    } catch (error) {
        console.error("Error:", error);
        resultDiv.style.color = "#e50914";
        resultDiv.innerText = "Server is offline. Try again later.";
    }
}