const plans = [
    { id: 1, name: "Basic", description: "Basic Netflix plan with ads", features: ["HD available", "1 screen", "Ads included"] },
    { id: 2, name: "Standard", description: "Standard Netflix plan", features: ["HD available", "2 screens", "No ads"] },
    { id: 3, name: "Premium", description: "Premium Netflix plan", features: ["4K available", "4 screens", "No ads"] }
];

let selectedPlanId = null;

document.addEventListener("DOMContentLoaded", () => {
    renderPlans();

    document.getElementById("submit-plan").addEventListener("click", async () => {
        if (!selectedPlanId) return alert("Plan seçilməyib!");

        const userId = localStorage.getItem("userId");
        if (!userId) return alert("User ID tapılmadı!");

        const plan = plans.find(p => p.id === selectedPlanId);

        try {
            const token = localStorage.getItem("accessToken");
            const res = await fetch(`http://localhost:5050/api/abone/redaktePlan/${userId}?name=${encodeURIComponent(plan.name)}`, {
                method: "PUT",
                headers: { "Authorization": "Bearer " + token }
            });

            if (!res.ok) {
                const text = await res.text();
                return alert("Update failed: " + text);
            }

            alert("Plan uğurla dəyişdirildi: " + plan.name);
            localStorage.setItem("plan",plan.name);
            setTimeout(window.location.href = "main.html",1000)
        } catch (err) {
            alert("Server error: " + err.message);
        }
    });
});


async function loadPlans() {
    const token = localStorage.getItem("accessToken");
    if (!token) return alert("Please login first!");

    try {
        const res = await fetch("http://localhost:5050/api/abone", {
            headers: { "Authorization": "Bearer " + token }
        });
        if (!res.ok) throw new Error("Status " + res.status);

        planCache = await res.json();
        renderPlans(planCache);
    } catch (err) {
        console.error(err);
        alert("Error loading plans: " + err.message);
    }
}



function renderPlans() {
    const container = document.getElementById("plans-wrapper");
    container.innerHTML = "";

    plans.forEach(plan => {
        const div = document.createElement("div");
        div.className = "plan-card";
        div.innerHTML = `
            <input type="radio" name="plan" id="plan-${plan.id}" value="${plan.id}">
            <label for="plan-${plan.id}" class="plan-label">${plan.name}</label>
        `;
        container.appendChild(div);

        div.querySelector("input").addEventListener("change", e => {
            selectedPlanId = parseInt(e.target.value);
            document.getElementById("selected-plan-display").innerText = "Current Selection: " + plan.name;


            const infoDiv = document.getElementById("plan-info");
            document.getElementById("plan-name").innerText = plan.name;
            document.getElementById("plan-description").innerText = plan.description;
            const featuresUl = document.getElementById("plan-features");
            featuresUl.innerHTML = "";
            plan.features.forEach(f => {
                const li = document.createElement("li");
                li.innerText = f;
                featuresUl.appendChild(li);
            });
            infoDiv.style.display = "block";
        });
    });
}