let usersCache = [];

window.onload = loadUsers;

async function loadUsers() {
    const token = localStorage.getItem("accessToken");
    try {
        const res = await fetch("http://localhost:5050/api/users/getAll", {
            headers: { Authorization: "Bearer " + token }
        });
        if (!res.ok) throw new Error(res.status);
        usersCache = await res.json();
        renderList();
    } catch(err) {
        document.getElementById("result").innerText = "Error: " + err.message;
    }
}

function renderList() {
    const container = document.getElementById("result");
    container.innerHTML = usersCache.map(u => `
        <div onclick="getUserById(${u.id})">
            <b>${u.name}</b><br>
            Username: ${u.username}<br>
            Email: ${u.email}<br>
            Age: ${u.age || ''}<br>
            ID: ${u.id}
        </div>
    `).join("");
}

async function getUserById(id) {
    const token = localStorage.getItem("accessToken");
    try {
        const res = await fetch(`http://localhost:5050/api/users/${id}`, {
            headers: { Authorization: "Bearer " + token }
        });
        if (!res.ok) throw new Error(res.status);
        const user = await res.json();
        document.getElementById("result").innerHTML = `
            <button onclick="goBack()">⬅ Geri</button>
            <div>
                <p>ID: ${user.id}</p>
                <p>Name: ${user.name}</p>
                <p>Username: ${user.username}</p>
                <p>Email: ${user.email}</p>
              
                <button onclick="showUpdateForm(${user.id})">Update</button>
                <button onclick="deleteUser(${user.id})">Delete</button>
            </div>
        `;
    } catch(err) {
        document.getElementById("result").innerText = "Error: " + err.message;
    }
}

function goBack() {
    renderList();
}

function showUpdateForm(id) {
    const user = usersCache.find(u => u.id === id);
    document.getElementById("result").innerHTML = `
        <button onclick="getUserById(${id})">⬅ Geri</button>
        <div>
            <h3>Update User</h3>
            <label>Name: <input id="updName" value="${user.name}"></label><br>
            <label>Username: <input id="updUsername" value="${user.username}"></label><br>
            <label>Email: <input id="updEmail" value="${user.email}"></label><br>
        
            <button onclick="updateUser(${id})">Save</button>
        </div>
    `;
}

async function updateUser(id) {
    const token = localStorage.getItem("accessToken");
    const updated = {
        name: document.getElementById("updName").value,
        username: document.getElementById("updUsername").value,
        email: document.getElementById("updEmail").value
    };

    const res = await fetch(`http://localhost:5050/api/users/${id}`, {
        method: "PUT",
        headers: {
            Authorization: "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updated)
    });

    if (res.ok) {
        const user = await res.json().catch(() => updated); 
        const index = usersCache.findIndex(u => u.id === id);
        usersCache[index] = user;
        getUserById(id);
    } else {
        alert("Update failed: " + res.status);
    }
}

async function deleteUser(id) {
    if (!confirm("Silinsin mi?")) return;
    const token = localStorage.getItem("accessToken");
    const res = await fetch(`http://localhost:5050/api/users/delete/${id}`, {
        method: "DELETE",
        headers: { Authorization: "Bearer " + token }
    });
    if (res.ok) {
        usersCache = usersCache.filter(u => u.id !== id);
        renderList();
    } else {
        alert("Delete failed: " + res.status);
    }
}