document.addEventListener('DOMContentLoaded', function() {
    // Tab Functionality
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

    // REFINEMENT: Animated Placeholder Text
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
        }, 2500); // Change placeholder every 2.5 seconds
    }
});

//login

// Login page functionality
if (document.querySelector('.login-page')) {
    // Toggle password visibility
    const showPasswordBtn = document.querySelector('.show-password');
    const passwordInput = document.getElementById('password');
    
    if (showPasswordBtn && passwordInput) {
        showPasswordBtn.addEventListener('click', function() {
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                this.textContent = 'HIDE';
            } else {
                passwordInput.type = 'password';
                this.textContent = 'SHOW';
            }
        });
    }

    // Form submission
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const errorElement = document.getElementById('login-error');
            if (errorElement) errorElement.style.display = 'none';
            
            // Add your authentication logic here
            // On successful login:
            // window.location.href = 'dashboard.html';
        });
    }
}

//login

document.addEventListener('DOMContentLoaded', function() {
    // Login functionality
    const loginButton = document.querySelector('.login-button');
    const emailInput = document.querySelector('input[type="email"]');
    const passwordInput = document.querySelector('input[type="password"]');
    const showPasswordBtn = document.querySelector('.show-password');
    
    // Store original button text
    const originalButtonText = loginButton.textContent;
    
    loginButton.addEventListener('click', async function(e) {
        e.preventDefault();
        
        // Get values and trim whitespace
        const email = emailInput.value.trim();
        const password = passwordInput.value.trim();
        
        // Validate inputs
        if (!email || !password) {
            showError('Please enter both email and password');
            return;
        }
        
        if (!validateEmail(email)) {
            showError('Please enter a valid email address');
            return;
        }
        
        // Disable button during request
        loginButton.disabled = true;
        loginButton.textContent = 'Logging in...';
        
        try {
            // Simulate API call (replace with actual fetch)
            const response = await mockLoginApiCall(email, password);
            
            if (response.success) {
                // On successful login (redirect or show success)
                alert('Login successful! Redirecting...');
                // window.location.href = '/dashboard'; // Uncomment for real redirect
            } else {
                showError(response.message || 'Login failed');
            }
        } catch (error) {
            console.error('Login error:', error);
            showError('An error occurred during login');
        } finally {
            // Re-enable button
            loginButton.disabled = false;
            loginButton.textContent = originalButtonText;
        }
    });
    
    // Show/hide password toggle
    showPasswordBtn.addEventListener('click', function() {
        const isPassword = passwordInput.type === 'password';
        passwordInput.type = isPassword ? 'text' : 'password';
        this.textContent = isPassword ? 'HIDE' : 'SHOW';
        
        // Focus the password field after toggle
        passwordInput.focus();
    });
    
    // Email validation helper
    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
    
    // Error display helper
    function showError(message) {
        // Remove any existing error messages
        const existingError = document.querySelector('.error-message');
        if (existingError) existingError.remove();
        
        // Create and display error message
        const errorElement = document.createElement('div');
        errorElement.className = 'error-message';
        errorElement.textContent = message;
        errorElement.style.color = '#ff4444';
        errorElement.style.marginTop = '10px';
        errorElement.style.textAlign = 'center';
        
        loginButton.insertAdjacentElement('afterend', errorElement);
    }
    
    // Mock API function (replace with real fetch)
    function mockLoginApiCall(email, password) {
        return new Promise((resolve) => {
            setTimeout(() => {
                // Simulate server validation
                if (email === 'user@example.com' && password === 'password123') {
                    resolve({ success: true });
                } else {
                    resolve({ 
                        success: false, 
                        message: 'Invalid email or password' 
                    });
                }
            }, 1500); // Simulate network delay
        });
    }
});

//sign up

document.addEventListener("DOMContentLoaded", () => {
    const signupForm = document.getElementById('signupForm');

    signupForm.addEventListener('submit', (event) => {
        event.preventDefault();

        const name = document.getElementById('name').value.trim();
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();
        const age = document.getElementById('age').value.trim();
        const pan = document.getElementById('pan').value.trim();
        const state = document.getElementById('state').value.trim();
        const city = document.getElementById('city').value.trim();
        const country = document.getElementById('country').value.trim();

        if (!name || !username || !password || !age || !pan || !state || !city || !country) {
            alert("Please fill in all fields");
            return;
        }

        if (age <= 0) {
            alert("Please enter a valid age");
            return;
        }

        if (pan.length !== 10) {
            alert("PAN number must be 10 characters");
            return;
        }

        alert("Sign up successful!");
        window.location.href = "login.html";
    });
});
