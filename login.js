document.getElementById("loginForm").addEventListener("submit", e=>{
    e.preventDefault();
    login();
});

async function login(){

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const result = document.getElementById("result");

    result.innerText = "Gözləyin...";

    try{
        const res = await fetch("http://localhost:5050/api/auth/login",{
            method:"POST",
            headers:{ "Content-Type":"application/json" },
            body: JSON.stringify({ username, password })
        });

        const data = await res.json();
        console.log("LOGIN RESPONSE:", data);

        if(!res.ok){
            result.innerText = data.message || "Login failed";
            return;
        }

        localStorage.setItem("accessToken", data.accessToken);
        localStorage.setItem("role", data.role);
        localStorage.setItem("userId", data.id);

        console.log("Saved userId =", data.id);

        result.innerText = "Login successful";

        setTimeout(()=>{
            window.location.href="plan.html";
        },1000);

    }catch(err){
        console.error(err);
        result.innerText = "Server error";
    }
}