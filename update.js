
const form = document.getElementById('updateForm');


form.addEventListener('submit', async (e) => {
    

    e.preventDefault(); 
    


    const payload = {
        name: document.getElementById('name').value,
        username: document.getElementById('username').value,
        email: document.getElementById('email').value
    };

    const id = localStorage.getItem("userId")

    try {
        const response = await fetch(`http://localhost:5050/api/users/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert("Profile Updated!");
        }
    } catch (err) {
        console.error("The backend is probably down:", err);
    }
});