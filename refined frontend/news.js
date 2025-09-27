// news.js
document.addEventListener("DOMContentLoaded", () => {
    const container = document.querySelector("#symptom-checker .container");
    const loading = document.getElementById("loading");

    fetch("http://localhost:8080/api/news/daily")
        .then(res => res.json())
        .then(articles => {
            loading.remove(); // remove "Loading..." text

            if (!articles || articles.length === 0) {
                container.insertAdjacentHTML("beforeend",
                    '<p>No news available right now.</p>');
                return;
            }

            articles.forEach(article => {
                const block = document.createElement("div");
                block.className = "alternating-grid";
                block.style.marginTop = "80px";

                block.innerHTML = `
                    <div class="image-placeholder">
                        <img src="${article.urlToImage || 'placeholder.jpg'}" alt="News Image" style="width:100%; height:auto;">
                    </div>
                    <div class="text-block-background">
                        <h3><a href="${article.url}" target="_blank">${article.title}</a></h3>
                        <p>${article.description || ''}</p>
                    </div>
                `;

                container.appendChild(block);
            });
        })
        .catch(err => {
            console.error("Error loading news:", err);
            loading.textContent = "⚠ Unable to load news. Please try again later.";
        });
});