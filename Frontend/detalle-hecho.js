// Funcionalidad para la página de detalle de hecho
document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const hechoDetalle = document.getElementById('hechoDetalle');
    const loading = document.getElementById('loading');
    const hechosRelacionadosGrid = document.getElementById('hechosRelacionadosGrid');

    // Datos de ejemplo (en producción esto vendría de una API)
    const hechosEjemplo = [
        {
            id: 1,
            titulo: "Incendio forestal en Parque Nacional Los Alerces",
            descripcion: "Gran incendio que afecta más de 500 hectáreas de bosque nativo en la provincia de Chubut. El fuego se inició el pasado martes debido a condiciones climáticas extremas y vientos fuertes que han dificultado las tareas de extinción. Las autoridades han evacuado a más de 200 familias de las zonas aledañas y han desplegado equipos especializados de bomberos forestales. El parque nacional, conocido por sus alerces milenarios, enfrenta una de las peores crisis ambientales de su historia. Los expertos estiman que la recuperación del ecosistema podría tomar décadas.",
            categoria: "incendio",
            ubicacion: "Chubut, Argentina",
            fecha: "2025-01-15",
            imagen: "img/Incendio-forestal.avif",
            coordenadas: { lat: -42.8, lng: -71.9 },
            fuente: "Servicio Nacional de Manejo del Fuego",
        },
        {
            id: 2,
            titulo: "Contaminación del río Riachuelo",
            descripcion: "Niveles alarmantes de contaminación industrial detectados en el río Riachuelo de Buenos Aires. Los análisis de agua muestran concentraciones de metales pesados 300% por encima de los límites permitidos. La situación afecta directamente a más de 5 millones de habitantes del área metropolitana que dependen de este recurso hídrico.",
            categoria: "contaminacion",
            ubicacion: "Buenos Aires, Argentina",
            fecha: "2025-01-14",
            imagen: "img/rio-contaminado.jpg",
            coordenadas: { lat: -34.6, lng: -58.4 },
            fuente: "Autoridad de Cuenca Matanza Riachuelo",
        },
        {
            id: 3,
            titulo: "Desaparición de activista ambiental",
            descripcion: "Desaparición de activista que denunciaba tala ilegal en la selva amazónica peruana. María Elena Vásquez, de 34 años, fue vista por última vez el pasado domingo cuando se dirigía a una reunión con otros defensores ambientales. Las autoridades han iniciado una búsqueda intensiva en la región de Loreto.",
            categoria: "desaparicion",
            ubicacion: "Loreto, Perú",
            fecha: "2025-01-13",
            imagen: "img/desapariciones.jpg",
            coordenadas: { lat: -3.7, lng: -73.2 },
            fuente: "Policía Nacional del Perú",
        },
        {
            id: 4,
            titulo: "Inundaciones en la región del Gran Chaco",
            descripcion: "Inundaciones masivas afectan a más de 10,000 familias en la región del Gran Chaco. Las lluvias torrenciales de los últimos días han causado el desborde de varios ríos, dejando a miles de personas sin hogar. Las autoridades han declarado estado de emergencia en toda la región.",
            categoria: "inundacion",
            ubicacion: "Chaco, Argentina",
            fecha: "2025-01-12",
            imagen: "img/mapa.png",
            coordenadas: { lat: -27.4, lng: -59.0 },
            fuente: "Defensa Civil Argentina",
        },
        {
            id: 5,
            titulo: "Deforestación en la Amazonía brasileña",
            descripcion: "Tala ilegal de más de 1,000 hectáreas de selva amazónica en el estado de Pará. Las imágenes satelitales muestran una deforestación masiva en una zona protegida, lo que ha generado alarma internacional. Las autoridades brasileñas han iniciado una investigación para identificar a los responsables.",
            categoria: "deforestacion",
            ubicacion: "Pará, Brasil",
            fecha: "2025-01-11",
            imagen: "img/mapa.png",
            coordenadas: { lat: -1.4, lng: -48.5 },
            fuente: "Instituto Nacional de Pesquisas Espaciais",
        },
        {
            id: 6,
            titulo: "Incendio en reserva natural de Chile",
            descripcion: "Incendio que afecta la reserva natural de la Araucanía, amenazando especies endémicas. El fuego se ha extendido por más de 200 hectáreas de bosque nativo, poniendo en riesgo a especies únicas de la región. Los equipos de emergencia trabajan contrarreloj para controlar las llamas.",
            categoria: "incendio",
            ubicacion: "Araucanía, Chile",
            fecha: "2025-01-10",
            imagen: "img/Incendio-forestal.avif",
            coordenadas: { lat: -38.9, lng: -72.6 },
            fuente: "CONAF Chile",
        }
    ];

    // Inicialización
    function init() {
        const hechoId = getHechoIdFromURL();
        if (hechoId) {
            loadHechoDetalle(hechoId);
            loadHechosRelacionados(hechoId);
        } else {
            showError('ID de hecho no válido');
        }
        setupEventListeners();
    }

    // Obtener ID del hecho desde la URL
    function getHechoIdFromURL() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('id');
    }

    // Cargar detalle del hecho
    function loadHechoDetalle(hechoId) {
        const hecho = hechosEjemplo.find(h => h.id == hechoId);
        
        if (!hecho) {
            showError('Hecho no encontrado');
            return;
        }

        // Simular carga
        setTimeout(() => {
            renderHechoDetalle(hecho);
        }, 1000);
    }

    // Renderizar detalle del hecho
    function renderHechoDetalle(hecho) {
        loading.style.display = 'none';
        
        hechoDetalle.innerHTML = `
            <img src="${hecho.imagen}" alt="${hecho.titulo}" class="hecho-imagen-principal">
            <div class="hecho-contenido">
                <div class="hecho-header">
                    <div>
                        <span class="hecho-categoria">${getCategoriaLabel(hecho.categoria)}</span>
                        <h1 class="hecho-titulo">${hecho.titulo}</h1>
                    </div>
                </div>
                
                <div class="hecho-meta">
                    <div class="meta-item">
                        <span class="icon">📍</span>
                        <span>${hecho.ubicacion}</span>
                    </div>
                    <div class="meta-item">
                        <span class="icon">📅</span>
                        <span>${formatDate(hecho.fecha)}</span>
                    </div>
                    <div class="meta-item">
                        <span class="icon">📰</span>
                        <span>Fuente: ${hecho.fuente}</span>
                    </div>
                </div>
                
                <div class="hecho-descripcion">
                    ${hecho.descripcion}
                </div>
                
                <div class="hecho-acciones">
                    <button class="btn-accion btn-compartir" onclick="compartirHecho(${hecho.id})">
                        <span>📤</span>
                        Compartir
                    </button>
                    <button class="btn-accion btn-reportar" onclick="reportarHecho(${hecho.id})">
                        <span>🚨</span>
                        Reportar
                    </button>
                    <button class="btn-accion btn-ver-mapa" onclick="verEnMapa(${hecho.coordenadas.lat}, ${hecho.coordenadas.lng})">
                        <span>🗺️</span>
                        Ver en Mapa
                    </button>
                </div>
            </div>
        `;
    }

    // Cargar hechos relacionados
    function loadHechosRelacionados(hechoId) {
        const hechoActual = hechosEjemplo.find(h => h.id == hechoId);
        if (!hechoActual) return;

        // Filtrar hechos relacionados (misma categoría, excluyendo el actual)
        const relacionados = hechosEjemplo.filter(h => 
            h.id != hechoId && h.categoria === hechoActual.categoria
        ).slice(0, 3);

        if (relacionados.length === 0) {
            document.getElementById('hechosRelacionados').style.display = 'none';
            return;
        }

        renderHechosRelacionados(relacionados);
    }

    // Renderizar hechos relacionados
    function renderHechosRelacionados(hechos) {
        hechosRelacionadosGrid.innerHTML = hechos.map(hecho => `
            <div class="hecho-card-relacionado" onclick="irADetalle(${hecho.id})">
                <img src="${hecho.imagen}" alt="${hecho.titulo}">
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
    }

    // Mostrar error
    function showError(message) {
        loading.style.display = 'none';
        hechoDetalle.innerHTML = `
            <div class="loading">
                <h3>❌ ${message}</h3>
                <p>Por favor, verifica que el enlace sea correcto.</p>
                <a href="hechos.html" class="back-btn">Volver a Hechos</a>
            </div>
        `;
    }

    // Configurar event listeners
    function setupEventListeners() {
        // Aquí puedes agregar más event listeners si es necesario
    }

    // Funciones globales para los botones
    window.compartirHecho = function(hechoId) {
        if (navigator.share) {
            navigator.share({
                title: 'Hecho en MetaMapa',
                text: 'Mira este hecho reportado en MetaMapa',
                url: window.location.href
            });
        } else {
            // Fallback: copiar al portapapeles
            navigator.clipboard.writeText(window.location.href).then(() => {
                alert('Enlace copiado al portapapeles');
            });
        }
    };

    window.reportarHecho = function(hechoId) {
        alert('Función de reporte en desarrollo. Gracias por tu interés en mantener la calidad de la información.');
    };

    window.verEnMapa = function(lat, lng) {
        const url = `https://www.google.com/maps?q=${lat},${lng}`;
        window.open(url, '_blank');
    };

    window.irADetalle = function(hechoId) {
        window.location.href = `detalle-hecho.html?id=${hechoId}`;
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

    // Inicializar la aplicación
    init();
});
