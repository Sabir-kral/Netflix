async function updateVideo() {
    const id = document.getElementById('id').value;
    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const ageLimit = document.getElementById('ageLimit').value;
    const active = document.getElementById('active').checked; 
    const uploadedBy = document.getElementById('uploadedBy').value;
    const resultDiv = document.getElementById('result');
    const token = localStorage.getItem("accessToken");
        const videoData = {
        name: name,
        description: description,
        ageLimit: ageLimit,
        active: active,
        uploadedBy: uploadedBy
    };
    const response = await fetch(`http://localhost:5050/api/videos/update/${id}`,{
        method:"PUT",
        headers:{ 'Authorization': "Bearer " + token,'Content-Type': 'application/json'},
        body:JSON.stringify(videoData)
    });

    const data = await response.json();

    if (response.ok){
        resultDiv.style.color = "green";
        resultDiv.innerText = "updated successfully redirecting..."

        setTimeout(()=>{
                window.location.href = "main.html"
        },1000)
    } else{
        resultDiv.style.color = "red";
        resultDiv.innerText = "error"
    }
}