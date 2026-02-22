    const resultDiv = document.getElementById("result");
    const goBackBtn = document.getElementById("goBack");

    goBackBtn.addEventListener("click", () => {
        window.location.href = "adminPanel.html"
    });

    window.onload = async function() {
        const token = localStorage.getItem("accessToken");
        resultDiv.innerHTML = "Loading logs...";

        try {
            const res = await fetch("http://localhost:5050/api/logs", {
                headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "application/json"
                }
            });

            if (!res.ok) {
                const errText = await res.text();
                resultDiv.innerHTML = `<p style="color:red;">Error: ${errText}</p>`;
                return;
            }

            const logs = await res.json();

            if (!logs || logs.length === 0) {
                resultDiv.innerHTML = "<p>No logs found.</p>";
                return;
            }

            let html = `<table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Message</th>
                        <th>Log Type</th>
                    </tr>
                </thead>
                <tbody>`;

            logs.forEach(log => {
                html += `<tr>
                    <td>${log.id}</td>
                    <td>${log.message || ''}</td>
                    <td>${log.logType || ''}</td>
                </tr>`;
            });

            html += `</tbody></table>`;
            resultDiv.innerHTML = html;

        } catch(err) {
            resultDiv.innerHTML = `<p style="color:red;">Error: ${err.message}</p>`;
        }
    };