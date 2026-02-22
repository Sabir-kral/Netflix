let videoCache = [];

window.onload = loadVideos;

async function loadVideos() {
    const token = localStorage.getItem("accessToken");
    const container = document.getElementById("result");
    
    try {
        const res = await fetch("http://localhost:5050/api/videos/myVideos", {
            headers: { Authorization: "Bearer " + token }
        });
        if (!res.ok) throw new Error("Status: " + res.status);
        videoCache = await res.json();
        renderList();
    } catch(err) {
        container.innerHTML = `<div style="color:red; text-align:center;">Failed to load: ${err.message}</div>`;
    }
}

function renderList() {
    const container = document.getElementById("result");

    container.style.display = "grid"; 
    
    container.innerHTML = videoCache.map(u => `
        <div class="video-card" onclick="getUserById(${u.id})">
            <b>${u.name}</b>
            <p>Uploader: ${u.uploadedBy}</p>
            <p>${u.active ? "🟢 Active" : "🔴 Inactive"}</p>
        </div>
    `).join("");
}

async function getUserById(id) {
    const token = localStorage.getItem("accessToken");
    try {
        const res = await fetch(`http://localhost:5050/api/videos/${id}`, {
            headers: { Authorization: "Bearer " + token }
        });
        if (!res.ok) throw new Error(res.status);
        const video = await res.json();
        
        const container = document.getElementById("result");
        container.style.display = "block"; 
        
        container.innerHTML = `
            <div class="detail-container">
                <button class="btn-back" onclick="goBack()">⬅ Back to List</button>
                <div class="detail-info">
                    <h1>${video.name}</h1>
                    <p>${video.id}</p>
                    <p><strong>Description:</strong> ${video.description || "No description available."}</p>
                    <p><strong>Maturity Rating:</strong> <span style="border:1px solid grey; padding: 2px 5px;">${video.ageLimit}+</span></p>
                    <p><strong>Uploader:</strong> ${video.uploadedBy}</p>
                </div>
                <div class="action-buttons" style="margin-top:30px;">
                    <a href="updateVideo.html?id=${video.id}"><button class="btn-update">Update Video</button></a>
                    <button class="btn-delete" onclick="deleteVideo(${video.id})">Delete Video</button>
                </div>
            </div>
        `;
    } catch(err) {
        alert("Error fetching details: " + err.message);
    }
}

function goBack() {
    renderList();
}

async function deleteVideo(id) {
    if (!confirm("Are you sure you want to delete this video?")) return;
    
    const token = localStorage.getItem("accessToken");
    try {
        const res = await fetch(`http://localhost:5050/api/videos/delete/${id}`, {
            method: "DELETE",
            headers: { Authorization: "Bearer " + token }
        });
        if (res.ok) {
            videoCache = videoCache.filter(u => u.id !== id);
            renderList();
        } else {
            alert("Delete failed: " + res.status);
        }
    } catch(err) {
        alert("Network error: " + err.message);
    }
}