// Funcionalidad para la página de hechos
document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const searchInput = document.getElementById('searchInput');
    const searchBtn = document.getElementById('searchBtn');
    const categoriaFilter = document.getElementById('categoriaFilter');
    const fechaFilter = document.getElementById('fechaFilter');
    const ubicacionFilter = document.getElementById('ubicacionFilter');
    const descripcionFilter = document.getElementById('descripcionFilter');
    const tituloFilter = document.getElementById('tituloFilter');
    const limpiarFiltrosBtn = document.getElementById('limpiarFiltros');
    const aplicarFiltrosBtn = document.getElementById('aplicarFiltros');
    const hechosGrid = document.getElementById('hechosGrid');
    const viewBtns = document.querySelectorAll('.view-btn');
    const prevPageBtn = document.getElementById('prevPage');
    const nextPageBtn = document.getElementById('nextPage');

    // Estado de la aplicación
    let currentPage = 1;
    let itemsPerPage = 12;
    let currentView = 'grid';
    let filteredHechos = [];
    let allHechos = [];

    // Datos de ejemplo (en producción esto vendría de una API)
    const hechosEjemplo = [
        {
            id: 1,
            titulo: "Incendio forestal en Parque Nacional Los Alerces",
            descripcion: "Gran incendio que afecta más de 500 hectáreas de bosque nativo en la provincia de Chubut.",
            categoria: "incendio",
            ubicacion: "Chubut, Argentina",
            fecha: "2025-01-15",
            imagen: "img/Incendio-forestal.avif",
            coordenadas: { lat: -42.8, lng: -71.9 }
        },
        {
            id: 2,
            titulo: "Contaminación del río Riachuelo",
            descripcion: "Niveles alarmantes de contaminación industrial detectados en el río Riachuelo de Buenos Aires.",
            categoria: "contaminacion",
            ubicacion: "Buenos Aires, Argentina",
            fecha: "2025-01-14",
            imagen: "img/rio-contaminado.jpg",
            coordenadas: { lat: -34.6, lng: -58.4 }
        },
        {
            id: 3,
            titulo: "Desaparición de activista ambiental",
            descripcion: "Desaparición de activista que denunciaba tala ilegal en la selva amazónica peruana.",
            categoria: "desaparicion",
            ubicacion: "Loreto, Perú",
            fecha: "2025-01-13",
            imagen: "img/desapariciones.jpg",
            coordenadas: { lat: -3.7, lng: -73.2 }
        },
        {
            id: 4,
            titulo: "Inundaciones en la región del Gran Chaco",
            descripcion: "Inundaciones masivas afectan a más de 10,000 familias en la región del Gran Chaco.",
            categoria: "inundacion",
            ubicacion: "Chaco, Argentina",
            fecha: "2025-01-12",
            imagen: "img/mapa.png",
            coordenadas: { lat: -27.4, lng: -59.0 }
        },
        {
            id: 5,
            titulo: "Deforestación en la Amazonía brasileña",
            descripcion: "Tala ilegal de más de 1,000 hectáreas de selva amazónica en el estado de Pará.",
            categoria: "deforestacion",
            ubicacion: "Pará, Brasil",
            fecha: "2025-01-11",
            imagen: "img/mapa.png",
            coordenadas: { lat: -1.4, lng: -48.5 }
        },
        {
            id: 6,
            titulo: "Incendio en reserva natural de Chile",
            descripcion: "Incendio que afecta la reserva natural de la Araucanía, amenazando especies endémicas.",
            categoria: "incendio",
            ubicacion: "Araucanía, Chile",
            fecha: "2025-01-10",
            imagen: "img/Incendio-forestal.avif",
            coordenadas: { lat: -38.9, lng: -72.6 }
        }
    ];

    // Inicialización
    function init() {
        allHechos = [...hechosEjemplo];
        filteredHechos = [...allHechos];
        renderHechos();
        updateStats();
        setupEventListeners();
        setupFooterLogin();
    }

    // Configurar event listeners
    function setupEventListeners() {
        // Búsqueda
        searchBtn.addEventListener('click', performSearch);
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') performSearch();
        });

        // Filtros
        limpiarFiltrosBtn.addEventListener('click', clearFilters);
        aplicarFiltrosBtn.addEventListener('click', applyFilters);

        // Cambio de vista
        viewBtns.forEach(btn => {
            btn.addEventListener('click', function() {
                changeView(this.dataset.view);
            });
        });

        // Paginación
        prevPageBtn.addEventListener('click', () => changePage(-1));
        nextPageBtn.addEventListener('click', () => changePage(1));
    }

    // Realizar búsqueda
    function performSearch() {
        const searchTerm = searchInput.value.toLowerCase().trim();

        if (searchTerm === '') {
            filteredHechos = [...allHechos];
        } else {
            filteredHechos = allHechos.filter(hecho =>
                hecho.titulo.toLowerCase().includes(searchTerm) ||
                hecho.descripcion.toLowerCase().includes(searchTerm) ||
                hecho.ubicacion.toLowerCase().includes(searchTerm)
            );
        }

        currentPage = 1;
        renderHechos();
        updateStats();
    }

    // Aplicar filtros
    function applyFilters() {
        const categoria = categoriaFilter.value;
        const fecha = fechaFilter.value;
        const ubicacion = ubicacionFilter.value;
        const titulo = tituloFilter.value.toLowerCase().trim();
        const descripcion = descripcionFilter.value.toLowerCase().trim();

        filteredHechos = allHechos.filter(hecho => {
            let match = true;

            if (categoria && hecho.categoria !== categoria) match = false;
            if (ubicacion && !hecho.ubicacion.toLowerCase().includes(ubicacion.toLowerCase())) match = false;
            if (descripcion && !hecho.descripcion.toLowerCase().includes(descripcion)) match = false;
            if(titulo && !hecho.titulo.toLowerCase().includes(titulo)) match = false;

            if (fecha) {
                const hechoFecha = new Date(hecho.fecha);
                const hoy = new Date();

                switch(fecha) {
                    case 'hoy':
                        match = match && hechoFecha.toDateString() === hoy.toDateString();
                        break;
                    case 'semana':
                        const semanaAtras = new Date(hoy.getTime() - 7 * 24 * 60 * 60 * 1000);
                        match = match && hechoFecha >= semanaAtras;
                        break;
                    case 'mes':
                        const mesAtras = new Date(hoy.getFullYear(), hoy.getMonth() - 1, hoy.getDate());
                        match = match && hechoFecha >= mesAtras;
                        break;
                    case 'año':
                        const añoAtras = new Date(hoy.getFullYear(), 0, 1);
                        match = match && hechoFecha >= añoAtras;
                        break;
                }
            }

            return match;
        });

        currentPage = 1;
        renderHechos();
        updateStats();
    }

    // Limpiar filtros
    function clearFilters() {
        searchInput.value = '';
        categoriaFilter.value = '';
        fechaFilter.value = '';
        ubicacionFilter.value = '';
        descripcionFilter.value = '';
        tituloFilter.value = '';

        filteredHechos = [...allHechos];
        currentPage = 1;
        renderHechos();
        updateStats();
    }

    // Cambiar vista
    function changeView(view) {
        currentView = view;

        // Actualizar botones activos
        viewBtns.forEach(btn => {
            btn.classList.toggle('active', btn.dataset.view === view);
        });

        // Cambiar clase del grid
        hechosGrid.className = `hechos-grid hechos-${view}`;

        renderHechos();
    }

    // Cambiar página
    function changePage(direction) {
        const totalPages = Math.ceil(filteredHechos.length / itemsPerPage);

        if (direction === -1 && currentPage > 1) {
            currentPage--;
        } else if (direction === 1 && currentPage < totalPages) {
            currentPage++;
        }

        renderHechos();
        updatePagination();
    }

    // Renderizar hechos
    function renderHechos() {
        if (filteredHechos.length === 0) {
            hechosGrid.innerHTML = `
                <div class="loading" style="grid-column: 1 / -1;">
                    <h3>No se encontraron hechos</h3>
                    <p>Intenta ajustar los filtros de búsqueda</p>
                </div>
            `;
            return;
        }

        const startIndex = (currentPage - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;
        const hechosToShow = filteredHechos.slice(startIndex, endIndex);

        hechosGrid.innerHTML = hechosToShow.map(hecho => `
            <div class="hecho-card" data-id="${hecho.id}" onclick="irADetalle(${hecho.id})">
                <img src="${hecho.imagen}" alt="${hecho.titulo}" class="hecho-imagen">
                <div class="hecho-content">
                    <span class="hecho-categoria">${getCategoriaLabel(hecho.categoria)}</span>
                    <h3 class="hecho-titulo">${hecho.titulo}</h3>
                    <p class="hecho-descripcion">${hecho.descripcion}</p>
                    <div class="hecho-meta">
                        <span class="hecho-ubicacion">📍 ${hecho.ubicacion}</span>
                        <span class="hecho-fecha">📅 ${formatDate(hecho.fecha)}</span>
                    </div>
                </div>
            </div>
        `).join('');

        updatePagination();
    }

    // Actualizar estadísticas
    function updateStats() {
        document.getElementById('totalHechos').textContent = filteredHechos.length;

        const hoy = new Date().toISOString().split('T')[0];
        const hechosHoy = filteredHechos.filter(h => h.fecha === hoy).length;
        document.getElementById('hechosHoy').textContent = hechosHoy;

        const categoriasUnicas = new Set(filteredHechos.map(h => h.categoria)).size;
        document.getElementById('categorias').textContent = categoriasUnicas;

        const paisesUnicos = new Set(filteredHechos.map(h => h.ubicacion.split(',')[1]?.trim() || h.ubicacion)).size;
        document.getElementById('paises').textContent = paisesUnicos;
    }

    // Actualizar paginación
    function updatePagination() {
        const totalPages = Math.ceil(filteredHechos.length / itemsPerPage);

        prevPageBtn.disabled = currentPage === 1;
        nextPageBtn.disabled = currentPage === totalPages;

        // Actualizar números de página
        const paginasContainer = document.querySelector('.paginas');
        if (paginasContainer) {
            let paginasHTML = '';

            for (let i = 1; i <= totalPages; i++) {
                if (i === 1 || i === totalPages || (i >= currentPage - 1 && i <= currentPage + 1)) {
                    paginasHTML += `<span class="pagina ${i === currentPage ? 'active' : ''}" onclick="goToPage(${i})">${i}</span>`;
                } else if (i === currentPage - 2 || i === currentPage + 2) {
                    paginasHTML += '<span class="pagina">...</span>';
                }
            }

            paginasContainer.innerHTML = paginasHTML;
        }
    }

    // Ir a página específica
    window.goToPage = function(page) {
        currentPage = page;
        renderHechos();
    };

    // Utilidades
    function getCategoriaLabel(categoria) {
        const labels = {
            'incendio': '🔥 Incendio',
            'contaminacion': '🌊 Contaminación',
            'desaparicion': '🚨 Desaparición',
            'inundacion': '💧 Inundación',
            'deforestacion': '🌳 Deforestación'
        };
        return labels[categoria] || categoria;
    }

    function formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('es-ES', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
        });
    }
    // Configurar login del footer
    function setupFooterLogin() {
        const footerLoginBtn = document.getElementById('footerLoginBtn');
        if (footerLoginBtn) {
            footerLoginBtn.addEventListener('click', function(e) {
                e.preventDefault();
                const loginModal = document.getElementById('loginModal');
                if (loginModal) {
                    loginModal.style.display = 'block';
                    document.body.style.overflow = 'hidden';
                }
            });
        }
    }

    // Función global para navegar a detalle
    window.irADetalle = function(hechoId) {
        window.location.href = `detalle-hecho.html?id=${hechoId}`;
    };


    // Inicializar la aplicación
    init();
});
