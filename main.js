let videoCache = [];


window.onload = loadUsers;

async function loadUsers() {
    const token = localStorage.getItem("accessToken");
    try {
        const res = await fetch("http://localhost:5050/api/videos/getAllVideos");
        if (!res.ok) throw new Error(res.status);
        videoCache = await res.json();
        renderList(videoCache);
    } catch(err) {
        console.error("Error loading videos:", err);
    }
}


function renderList(list) {
    const container = document.getElementById("videos");
    container.innerHTML = list.map(d => `
    <div class="video-card" onclick="getVideoById(${d.id})">
        <div class="video-card__info">
            <h3 class="video-card__title">${d.name}</h3>
            <div class="video-card__metadata">
                <span class="age-limit">${d.ageLimit}+</span>
                <span class="uploader">${d.uploadedBy}</span>
            </div>
            <p class="video-card__desc">${d.description}</p>
        </div>
    </div>
    `).join("");
}

function displayVideos(videos) {
    const container = document.getElementById('video-list');
    container.innerHTML = '';

    videos.forEach(v => {
        const card = document.createElement('div');
        card.className = 'video-card';
        card.innerHTML = `
            <div style="padding: 15px;">
                <h3 style="color: #e50914;">${v.name}</h3>
                <p style="font-size: 12px; color: #ccc;">${v.description}</p>
                <span style="border: 1px solid gray; padding: 2px 5px; font-size: 10px;">
                    ${v.ageLimit}+
                </span>
            </div>
        `;
        container.appendChild(card);
    });
}


function searchVideos() {
    const query = document.getElementById("searchInput").value.toLowerCase();
    const filtered = videoCache.filter(v => 
        v.name.toLowerCase().includes(query) || 
        v.uploadedBy.toLowerCase().includes(query)
    );
    renderList(filtered);
}


async function getVideoById(id) {
    const token = localStorage.getItem("accessToken");

    document.getElementById("home-page").style.display = "none";
    const detailView = document.getElementById("detail-view");
    detailView.style.display = "block";
    window.scrollTo(0,0);

    try {
        const res = await fetch(`http://localhost:5050/api/videos/${id}`, {
            headers: { Authorization: "Bearer " + token }
        });
        const video = await res.json();

        detailView.innerHTML = `
            <div class="video-detail-container">
                <button class="back-btn" onclick="goBack()">⬅ Back to Browse</button>
                <div class="player-section">
                    <video controls autoplay class="main-video-player">
                        <source src="${video.videoUrl || 'https://www.w3schools.com/html/mov_bbb.mp4'}" type="video/mp4">
                    </video>
                </div>
                <div class="info-section">
                    <h1 class="info-title">${video.name}</h1>
                    <div class="info-meta">
                        <span>98% Match</span>
                        <span style="color: white; border: 1px solid white; padding: 0 5px;">${video.ageLimit}+</span>
                        <span style="color: white;">Uploaded by: ${video.uploadedBy}</span>
                    </div>
                    <p class="info-desc">${video.description}</p>
                </div>
            </div>
        `;
    } catch(err) {
        detailView.innerHTML = `<p>Error: ${err.message} <button onclick="goBack()">Back</button></p>`;
    }
}


function goBack() {

    const vid = document.querySelector('video');
    if(vid) vid.pause();

    document.getElementById("home-page").style.display = "block";
    document.getElementById("detail-view").style.display = "none";
}
function admin() {
    const role = localStorage.getItem("role");
    if (role && role.includes("3")) { 
        window.location.href = "adminPanelMain.html";
    }
}
admin()
function manager() {
    const role = localStorage.getItem("role");
    if (role && role.includes("4")) { 
        window.location.href = "managerPanelMain.html";
    }
}
manager()