let videoCache = [];

window.onload = loadVideos;

async function loadVideos() {
    const token = localStorage.getItem("accessToken");
    try {
        const res = await fetch("http://localhost:5050/api/videos/getAllWithActive", {
            headers: { Authorization: "Bearer " + token }
        });
        if (!res.ok) throw new Error(res.status);
        videoCache = await res.json();
        renderList();
    } catch(err) {
        document.getElementById("result").innerText = "Error: " + err.message;
    }
}

function renderList() {
    const container = document.getElementById("result");
    container.innerHTML = videoCache.map(u => `
        <div class="video-card" onclick="getUserById(${u.id})">
            <b>${u.name}</b>
            <p>ID: ${u.id}</p>
            <p>Uploader: ${u.uploadedBy}</p>
            <p>Status: ${u.active ? "✅ Active" : "❌ Inactive"}</p>
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
        
        document.getElementById("result").innerHTML = `
            <div class="detail-container">
                <button class="btn-back" onclick="goBack()" style="margin-bottom:20px;">⬅ Back to List</button>
                <div class="detail-info">
                    <h1>${video.name}</h1>
                    <p><strong>Description:</strong> ${video.description}</p>
                    <p><strong>Age Limit:</strong> ${video.ageLimit}+</p>
                    <p><strong>Status:</strong> ${video.active ? "Active" : "Hidden"}</p>
                    <p><strong>Uploader:</strong> ${video.uploadedBy}</p>
                </div>
                <div class="action-buttons">
                    <a href="updateVideo.html?id=${video.id}"><button class="btn-update">Update Video</button></a>
                    <button class="btn-delete" onclick="deleteVideo(${video.id})">Delete Video</button>
                </div>
            </div>
        `;
    } catch(err) {
        document.getElementById("result").innerText = "Error: " + err.message;
    }
}

function goBack() {
    renderList();
}
async function deleteVideo(id) {
    if (!confirm("Silinsin mi?")) return;
    const token = localStorage.getItem("accessToken");
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
}