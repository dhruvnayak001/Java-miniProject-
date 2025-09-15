// ==== BASE URL ====
const API_BASE = "http://localhost:8080/api";

/* ============================
   TAB FUNCTIONALITY (index.html)
============================ */
document.addEventListener('DOMContentLoaded', function () {
    const tabButtons = document.querySelectorAll('.tab-button');
    const tabContents = document.querySelectorAll('.tab-content');

    tabButtons.forEach(button => {
        button.addEventListener('click', () => {
            tabButtons.forEach(btn => btn.classList.remove('active'));
            tabContents.forEach(content => content.classList.remove('active'));
            button.classList.add('active');
            const tabId = button.getAttribute('data-tab');
            document.getElementById(tabId).classList.add('active');
        });
    });

    // Scroll Animation
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
            }
        });
    }, { threshold: 0.1 });

    const sections = document.querySelectorAll('section');
    sections.forEach(section => observer.observe(section));

    // Placeholder animation in Symptom Checker
    const symptomInput = document.getElementById('symptom-input');
    if (symptomInput) {
        const symptoms = [
            "e.g., persistent headache...",
            "e.g., high fever, chills...",
            "e.g., joint pain and swelling...",
            "e.g., chronic fatigue...",
            "e.g., skin rash or irritation..."
        ];
        let i = 0;
        setInterval(() => {
            symptomInput.placeholder = symptoms[i];
            i = (i + 1) % symptoms.length;
        }, 2500);
    }
});

/* ============================
   LOGIN FUNCTIONALITY
============================ */
const loginForm = document.getElementById("login-form");
if (loginForm) {
    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();
        const errorElement = document.getElementById("login-error");

        try {
            const res = await fetch(`${API_BASE}/users/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });

            const text = await res.text();

            if (text.includes("✅")) {
                alert(text);

                // Save user session
                localStorage.setItem("loggedInUser", username);

                // Redirect based on role
                if (username === "admin") {
                    window.location.href = "admin.html";
                } else {
                    window.location.href = "index.html";
                }
            } else {
                errorElement.textContent = text;
                errorElement.style.display = "block";
            }
        } catch (err) {
            console.error(err);
            alert("⚠ Server error. Please try again.");
        }
    });

    // Toggle password visibility
    const showPasswordBtn = document.querySelector('.show-password');
    const passwordInput = document.getElementById('password');
    if (showPasswordBtn && passwordInput) {
        showPasswordBtn.addEventListener('click', function () {
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                this.textContent = 'HIDE';
            } else {
                passwordInput.type = 'password';
                this.textContent = 'SHOW';
            }
        });
    }
}

/* ============================
   SIGNUP FUNCTIONALITY
============================ */
const signupForm = document.getElementById("signupForm");
if (signupForm) {
    signupForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const user = {
            name: document.getElementById("name").value,
            username: document.getElementById("username").value,
            password: document.getElementById("password").value,
            age: document.getElementById("age").value,
            pan: document.getElementById("pan").value,
            state: document.getElementById("state").value,
            city: document.getElementById("city").value,
            country: document.getElementById("country").value
        };

        try {
            const res = await fetch(`${API_BASE}/users/register`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(user)
            });

            if (!res.ok) throw new Error("Failed to register");
            const savedUser = await res.json();

            alert("✅ Registered successfully: " + savedUser.username);
            window.location.href = "login.html";
        } catch (err) {
            console.error(err);
            alert("⚠ Registration failed.");
        }
    });
}

/* ============================
   SYMPTOM CHECKER (index.html)
============================ */
const checkBtn = document.querySelector("#symptom-input + a.btn-primary");
if (checkBtn) {
    checkBtn.addEventListener("click", async (e) => {
        e.preventDefault();
        const input = document.getElementById("symptom-input").value;
        if (!input) return alert("Please enter symptoms first!");

        try {
            const res = await fetch(`${API_BASE}/match?symptoms=${encodeURIComponent(input)}`);
            const diseases = await res.json();

            let resultHTML = "<h3>Possible Matches:</h3><ul>";
            if (diseases.length > 0) {
                diseases.forEach(d => {
                    resultHTML += `<li><strong>${d.name}</strong> - ${d.description}</li>`;
                });
            } else {
                resultHTML += "<li>No matches found.</li>";
            }
            resultHTML += "</ul>";

            const widget = document.querySelector(".checker-widget");
            widget.innerHTML = ""; // Clear previous results
            widget.insertAdjacentHTML("beforeend", resultHTML);

        } catch (err) {
            console.error(err);
            alert("⚠ Error checking symptoms.");
        }
    });
}

/* ============================
   ADMIN PAGE FUNCTIONS
============================ */
async function loadDiseases() {
    try {
        const res = await fetch(`${API_BASE}/diseases`);
        const diseases = await res.json();
        const container = document.getElementById("disease-list");
        if (container) {
            container.innerHTML = diseases.map(d => `<li>${d.name} - ${d.description}</li>`).join("");
        }
    } catch (err) {
        console.error("Failed to load diseases:", err);
    }
}

async function addDisease(name, description, symptoms) {
    const disease = { name, description, symptoms };
    try {
        await fetch(`${API_BASE}/diseases`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(disease)
        });
        loadDiseases();
    } catch (err) {
        console.error("Failed to add disease:", err);
    }
}

// Expose for admin.html usage
window.loadDiseases = loadDiseases;
window.addDisease = addDisease;


//hamburger menu//
document.addEventListener("DOMContentLoaded", function () {
  const toggleBtn = document.querySelector(".mobile-menu-toggle");
  const navMenu = document.querySelector(".main-nav");
  const dropdownParents = document.querySelectorAll(".dropdown-parent");

  // --- Toggle hamburger menu ---
  toggleBtn.addEventListener("click", (e) => {
    e.stopPropagation(); // prevent clicks bubbling to nav
    navMenu.classList.toggle("mobile-nav-open");
  });

  // --- Dropdown accordion only for mobile & tablet ---
  dropdownParents.forEach(parent => {
    const link = parent.querySelector("a");

    link.addEventListener("click", function (e) {
      if (window.innerWidth <= 1024) { // Mobile & Tablet only
        e.preventDefault();
        e.stopPropagation();

        // Close all other dropdowns
        dropdownParents.forEach(p => {
          if (p !== parent) p.classList.remove("active");
        });

        // Toggle this one
        parent.classList.toggle("active");
      }
    });
  });
});

