// Simulate login state: 'true' = logged in, 'false' = logged out
const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';

// Mock user data (your existing data)
const userData = {
    firstName: "John",
    lastName: "Doe",
    joinDate: "2025-01-15",
    totalLogins: 42,
    lastLogin: new Date(),
    loginHistory: [
        { date: "2025-08-11", time: "06:12 AM", device: "iPhone 13", location: "New York, NY" },
        { date: "2025-08-10", time: "09:45 PM", device: "MacBook Pro", location: "New York, NY" },
        { date: "2025-08-10", time: "02:30 PM", device: "iPhone 13", location: "Brooklyn, NY" },
        { date: "2025-08-09", time: "11:20 AM", device: "iPad Air", location: "New York, NY" },
        { date: "2025-08-08", time: "07:15 PM", device: "iPhone 13", location: "Queens, NY" }
    ],
    symptoms: {
        uniqueCount: 15,
        tags: ["headache", "fever", "cough", "fatigue", "nausea", "dizziness", "back pain", "sore throat", "shortness of breath", "chest pain", "insomnia", "anxiety", "joint pain", "stomach ache", "rash"],
        history: [
            { date: "2025-08-10", symptoms: ["headache", "fatigue"], severity: "Moderate", followUp: "Scheduled" },
            { date: "2025-08-08", symptoms: ["cough", "sore throat"], severity: "Mild", followUp: "Completed" },
            { date: "2025-08-05", symptoms: ["fever", "body aches"], severity: "High", followUp: "Completed" },
            { date: "2025-08-01", symptoms: ["anxiety", "insomnia"], severity: "Moderate", followUp: "In progress" },
            { date: "2025-07-28", symptoms: ["stomach ache", "nausea"], severity: "Mild", followUp: "Completed" }
        ]
    }
};

// Show or hide dashboard section based on login state
function updateDashboardVisibility() {
    const dashboardSection = document.getElementById('dashboardSection');
    const loginBtn = document.querySelector('a.btn-secondary[href="login.html"]');

    if (isLoggedIn) {
        dashboardSection.style.display = 'block';      // Show dashboard
        if (loginBtn) loginBtn.style.display = 'none'; // Hide login button
    } else {
        dashboardSection.style.display = 'none';       // Hide dashboard
        if (loginBtn) loginBtn.style.display = 'inline-block'; // Show login button
    }
}

// Populate dashboard content if logged in
function populateDashboard() {
    if (!isLoggedIn) return; // Don't populate if not logged in

    document.getElementById('user-name').textContent = userData.firstName;
    document.getElementById('full-name').textContent = `${userData.firstName} ${userData.lastName}`;
    document.getElementById('user-avatar').textContent = userData.firstName.charAt(0) + userData.lastName.charAt(0);
    document.getElementById('total-logins').textContent = userData.totalLogins;
    document.getElementById('last-login').textContent = "Today";
    document.getElementById('last-login-time').textContent = userData.lastLogin.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });
    document.getElementById('total-symptoms').textContent = userData.symptoms.uniqueCount;

    const joinDate = new Date(userData.joinDate);
    document.getElementById('member-since').textContent = joinDate.toLocaleString('default', { month: 'short', year: 'numeric' });

    // Populate login history table
    const loginHistoryTable = document.querySelector('#login-history tbody');
    loginHistoryTable.innerHTML = ''; // Clear existing rows
    userData.loginHistory.forEach(login => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${login.date}</td>
            <td>${login.time}</td>
            <td>${login.device}</td>
            <td>${login.location}</td>
        `;
        loginHistoryTable.appendChild(row);
    });

    // Populate symptom tags
    const symptomTagsContainer = document.getElementById('symptom-tags');
    symptomTagsContainer.innerHTML = ''; // Clear existing tags
    userData.symptoms.tags.forEach(symptom => {
        const tag = document.createElement('span');
        tag.className = 'symptom-tag';
        tag.textContent = symptom;
        symptomTagsContainer.appendChild(tag);
    });

    // Populate symptom history table
    const symptomHistoryTable = document.querySelector('#symptom-history tbody');
    symptomHistoryTable.innerHTML = ''; // Clear existing rows
    userData.symptoms.history.forEach(entry => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${entry.date}</td>
            <td>${entry.symptoms.join(', ')}</td>
            <td>${entry.severity}</td>
            <td>${entry.followUp}</td>
        `;
        symptomHistoryTable.appendChild(row);
    });
}

// Run when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    updateDashboardVisibility();
    populateDashboard();
});

// Test login/logout functions
function loginUser() {
    localStorage.setItem('isLoggedIn', 'true');
    location.reload();
}

function logoutUser() {
    localStorage.setItem('isLoggedIn', 'false');
    location.reload();
}

document.addEventListener("DOMContentLoaded", function () {
    const loggedIn = localStorage.getItem("isLoggedIn");

    if (loggedIn !== "true") {
        // Not logged in → redirect to login page
        window.location.href = "login.html";
    }
});
