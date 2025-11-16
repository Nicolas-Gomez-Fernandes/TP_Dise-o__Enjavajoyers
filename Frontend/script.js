// Funcionalidad para los modales de login y registro
document.addEventListener('DOMContentLoaded', function() {
    const loginBtn = document.getElementById('loginBtn');
    const loginModal = document.getElementById('loginModal');
    const registerModal = document.getElementById('registerModal');
    const closeBtns = document.querySelectorAll('.close');
    const showRegisterLink = document.getElementById('showRegister');
    const showLoginLink = document.getElementById('showLogin');
    const forgotPasswordLink = document.getElementById('forgotPassword');
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');

    // Abrir modal de login
    loginBtn.addEventListener('click', function(e) {
        e.preventDefault();
        loginModal.style.display = 'block';
        document.body.style.overflow = 'hidden'; // Prevenir scroll
    });

    // Abrir modal de login desde el footer
    const footerLoginBtn = document.getElementById('footerLoginBtn');
    if (footerLoginBtn) {
        footerLoginBtn.addEventListener('click', function(e) {
            e.preventDefault();
            loginModal.style.display = 'block';
            document.body.style.overflow = 'hidden'; // Prevenir scroll
        });
    }


    // Cerrar modales con el botón X
    closeBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            loginModal.style.display = 'none';
            registerModal.style.display = 'none';
            document.body.style.overflow = 'auto'; // Restaurar scroll
        });
    });

    // Cerrar modales haciendo clic fuera de ellos
    window.addEventListener('click', function(e) {
        if (e.target === loginModal) {
            loginModal.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
        if (e.target === registerModal) {
            registerModal.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
    });

    // Cambiar entre modales
    showRegisterLink.addEventListener('click', function(e) {
        e.preventDefault();
        loginModal.style.display = 'none';
        registerModal.style.display = 'block';
    });

    showLoginLink.addEventListener('click', function(e) {
        e.preventDefault();
        registerModal.style.display = 'none';
        loginModal.style.display = 'block';
    });

    // Manejar envío del formulario de login
    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Aquí puedes agregar la lógica de autenticación
        console.log('Login:', { email, password });

        // Simular proceso de login
        showMessage('Iniciando sesión...', 'info');

        // Por ahora, solo cerramos el modal
        setTimeout(() => {
            loginModal.style.display = 'none';
            document.body.style.overflow = 'auto';
            showMessage('¡Bienvenido!', 'success');
        }, 1500);
    });

    // Manejar envío del formulario de registro
    registerForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const name = document.getElementById('regName').value;
        const email = document.getElementById('regEmail').value;
        const password = document.getElementById('regPassword').value;
        const confirmPassword = document.getElementById('regConfirmPassword').value;

        // Validar que las contraseñas coincidan
        if (password !== confirmPassword) {
            showMessage('Las contraseñas no coinciden', 'error');
            return;
        }

        // Aquí puedes agregar la lógica de registro
        console.log('Register:', { name, email, password });

        // Simular proceso de registro
        showMessage('Creando cuenta...', 'info');

        // Por ahora, solo cerramos el modal
        setTimeout(() => {
            registerModal.style.display = 'none';
            document.body.style.overflow = 'auto';
            showMessage('¡Cuenta creada exitosamente!', 'success');
        }, 1500);
    });

    // Función para mostrar mensajes
    function showMessage(message, type) {
        // Crear elemento de mensaje
        const messageDiv = document.createElement('div');
        messageDiv.className = `message message-${type}`;
        messageDiv.textContent = message;

        // Agregar estilos
        messageDiv.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 1rem 1.5rem;
            border-radius: 5px;
            color: white;
            font-weight: 500;
            z-index: 1001;
            animation: slideIn 0.3s ease;
        `;

        // Colores según el tipo
        if (type === 'success') {
            messageDiv.style.backgroundColor = 'var(--color-secundario)';
        } else if (type === 'error') {
            messageDiv.style.backgroundColor = '#e74c3c';
        } else {
            messageDiv.style.backgroundColor = 'var(--color-primario)';
        }

        // Agregar al DOM
        document.body.appendChild(messageDiv);

        // Remover después de 3 segundos
        setTimeout(() => {
            messageDiv.style.animation = 'slideOut 0.3s ease';
            setTimeout(() => {
                document.body.removeChild(messageDiv);
            }, 300);
        }, 3000);
    }

    // Agregar estilos CSS para las animaciones
    const style = document.createElement('style');
    style.textContent = `
        @keyframes slideIn {
            from {
                transform: translateX(100%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }

        @keyframes slideOut {
            from {
                transform: translateX(0);
                opacity: 1;
            }
            to {
                transform: translateX(100%);
                opacity: 0;
            }
        }
    `;
    document.head.appendChild(style);

    // Manejar enlace de "olvidé contraseña"
    forgotPasswordLink.addEventListener('click', function(e) {
        e.preventDefault();
        showMessage('Función en desarrollo. Contacta al administrador.', 'info');
    });
});
