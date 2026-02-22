  const token = localStorage.getItem("accessToken");

        if(!token) {
            window.location.href = "login.html";
        }

        async function loadProfile() {
            try {
                const response = await fetch("http://localhost:5050/api/users/getUser", {
                    method: "GET",
                    headers: {"Authorization": "Bearer "+token}
                });

                const data = await response.json();

                if(!response.ok) {
                    document.getElementById("result").innerText = "Xeta bas verdi";
                }

                if (data.name==null||data.username==null||data.email==null){
                    window.location.href = "login.js"
                }   
                const plan = localStorage.getItem("plan");

                document.getElementById("name").innerText = "name: "+data.name;
                document.getElementById("username").innerText = "username: "+data.username;
                document.getElementById("email").innerText = "email: "+data.email;
                document.getElementById("plan").innerText = "plan: "+plan;
            }catch(e) {
                document.getElementById("result").innerText = "SERVER ERROR";
            }
        }

        function delete1(){
            localStorage.removeItem("accessToken")
        }


        loadProfile();

