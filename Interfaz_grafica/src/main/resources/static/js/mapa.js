// JavaScript para la funcionalidad del mapa con Leaflet
document.addEventListener('DOMContentLoaded', function() {
    // Variables globales
    let map;
    let markers = [];
    let allHechos = [];
    let filteredHechos = [];

    // Inicializar el mapa
    function initMap() {
        // Crear el mapa centrado en Argentina
        map = L.map('map').setView([-38.4161, -63.6167], 5);

        // Agregar capa de OpenStreetMap
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap contributors'
        }).addTo(map);

        console.log('✅ Mapa inicializado correctamente');
    }

    // Obtener color según categoría
    function getColorByCategory(categoria) {
        if (!categoria) return '#34495e';
        
        const colors = {
            'INCENDIO': '#e74c3c',
            'INUNDACION': '#3498db',
            'DEFORESTACION': '#2ecc71',
            'CONTAMINACION': '#f39c12',
            'DESAPARICION': '#9b59b6',
            'incendio': '#e74c3c',
            'inundacion': '#3498db',
            'deforestacion': '#2ecc71',
            'contaminacion': '#f39c12',
            'desaparicion': '#9b59b6'
        };

        const categoriaUpper = categoria.toUpperCase();
        return colors[categoriaUpper] || colors[categoria.toLowerCase()] || '#34495e';
    }

    // Crear marcador personalizado
    function createCustomMarker(hecho) {
        const color = getColorByCategory(hecho.categoria);
        
        const customIcon = L.divIcon({
            className: 'custom-marker',
            html: `<div style="background-color: ${color}; width: 25px; height: 25px; border-radius: 50%; border: 3px solid white; box-shadow: 0 2px 8px rgba(0,0,0,0.3);"></div>`,
            iconSize: [25, 25],
            iconAnchor: [12, 12],
            popupAnchor: [0, -12]
        });

        return customIcon;
    }

    // Crear contenido del popup
    function createPopupContent(hecho, lat, lng) {
        const fechaFormateada = hecho.fechaAcontecimiento ? new Date(hecho.fechaAcontecimiento).toLocaleDateString('es-AR') : 
                               (hecho.fecha_hecho ? new Date(hecho.fecha_hecho).toLocaleDateString('es-AR') : 
                               (hecho.fecha ? new Date(hecho.fecha).toLocaleDateString('es-AR') : 'Sin fecha'));
        
        const descripcionCorta = hecho.descripcion ? 
            (hecho.descripcion.length > 150 ? hecho.descripcion.substring(0, 150) + '...' : hecho.descripcion) : 
            'Sin descripción';

        return `
            <div class="popup-content">
                <h5>${hecho.titulo || 'Sin título'}</h5>
                <span class="popup-category">${hecho.categoria || 'Sin categoría'}</span>
                <p class="popup-description">${descripcionCorta}</p>
                <div class="popup-date">
                    <i class="bi bi-calendar-event"></i>
                    ${fechaFormateada}
                </div>
                <div class="popup-location">
                    <i class="bi bi-geo-alt"></i>
                    ${lat ? lat.toFixed(4) : '0'}, ${lng ? lng.toFixed(4) : '0'}
                </div>
                <div class="popup-actions">
                    <a href="/hechos/detalle/${hecho.id}" class="btn btn-primary btn-sm">
                        <i class="bi bi-eye"></i> Ver Detalle
                    </a>
                    <button onclick="centrarEnMapa(${lat}, ${lng})" class="btn btn-outline-secondary btn-sm">
                        <i class="bi bi-crosshair"></i> Centrar
                    </button>
                </div>
            </div>
        `;
    }

    // Agregar marcadores al mapa
    function addMarkersToMap(hechos) {
        // Limpiar marcadores existentes
        markers.forEach(marker => map.removeLayer(marker));
        markers = [];

        console.log(`🔍 Procesando ${hechos.length} hechos para agregar marcadores...`);

        // Agregar nuevos marcadores
        hechos.forEach((hecho, index) => {
            // Obtener coordenadas (pueden estar en el objeto directamente o en hecho.ubicacion)
            const lat = hecho.latitud || (hecho.ubicacion && hecho.ubicacion.latitud);
            const lng = hecho.longitud || (hecho.ubicacion && hecho.ubicacion.longitud);
            
            if (lat && lng) {
                console.log(`📍 Hecho ${index + 1}: "${hecho.titulo}" en [${lat}, ${lng}] - Categoría: ${hecho.categoria}`);
                
                const color = getColorByCategory(hecho.categoria);
                console.log(`   Color asignado: ${color}`);
                
                const icon = createCustomMarker(hecho);
                const marker = L.marker([lat, lng], { icon: icon })
                    .bindPopup(createPopupContent(hecho, lat, lng))
                    .addTo(map);

                markers.push(marker);
            } else {
                console.warn(`⚠️ Hecho ${index + 1} "${hecho.titulo}" NO tiene coordenadas`);
            }
        });

        console.log(`✅ ${markers.length} marcadores agregados al mapa de ${hechos.length} hechos`);
        
        if (markers.length === 0) {
            console.error('❌ NO se agregó ningún marcador al mapa');
        }
    }

    // Cargar hechos desde el backend
    async function cargarHechos() {
        try {
            showLoading(true);
            console.log('🔄 Cargando hechos desde el backend...');

            const response = await fetch('/hechos/api/list');
            
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }

            const data = await response.json();
            console.log('✅ Hechos cargados:', data.length);
            console.log('📍 Ejemplo de hecho:', data[0]);

            // Verificar cuántos hechos tienen coordenadas (pueden estar en ubicacion o directamente)
            const hechosConCoordenadas = data.filter(h => 
                (h.latitud && h.longitud) || 
                (h.ubicacion && h.ubicacion.latitud && h.ubicacion.longitud)
            );
            console.log(`📍 Hechos con coordenadas: ${hechosConCoordenadas.length} de ${data.length}`);

            if (hechosConCoordenadas.length === 0) {
                console.warn('⚠️ NINGÚN hecho tiene coordenadas (latitud/longitud)');
                alert('⚠️ Los hechos no tienen coordenadas geográficas. No se pueden mostrar en el mapa.');
            } else {
                console.log(`✅ Se mostrarán ${hechosConCoordenadas.length} puntos en el mapa`);
            }

            allHechos = data;
            filteredHechos = [...allHechos];

            // Agregar marcadores
            addMarkersToMap(filteredHechos);

            // Actualizar estadísticas
            updateStats();

            // Cargar categorías en el filtro
            loadCategories();

            showLoading(false);

        } catch (error) {
            console.error('❌ Error al cargar hechos:', error);
            showLoading(false);
            alert('Error al cargar los hechos del mapa. Por favor, intenta nuevamente.');
        }
    }

    // Cargar categorías únicas en el select
    function loadCategories() {
        const categorias = [...new Set(allHechos.map(h => h.categoria).filter(c => c))];
        const select = document.getElementById('categoriaFilter');
        
        select.innerHTML = '<option value="">Todas las categorías</option>';
        categorias.forEach(categoria => {
            const option = document.createElement('option');
            option.value = categoria;
            option.textContent = categoria;
            select.appendChild(option);
        });
    }

    // Aplicar filtros
    function applyFilters() {
        const searchText = document.getElementById('searchInput').value.toLowerCase().trim();
        const categoria = document.getElementById('categoriaFilter').value;
        const fechaDesde = document.getElementById('fechaDesde').value;
        const fechaHasta = document.getElementById('fechaHasta').value;

        filteredHechos = allHechos.filter(hecho => {
            let match = true;

            // Filtro de búsqueda de texto
            if (searchText) {
                const tituloMatch = (hecho.titulo || '').toLowerCase().includes(searchText);
                const descripcionMatch = (hecho.descripcion || '').toLowerCase().includes(searchText);
                match = match && (tituloMatch || descripcionMatch);
            }

            // Filtro de categoría
            if (categoria) {
                match = match && hecho.categoria === categoria;
            }

            // Filtro de fecha desde
            if (fechaDesde && hecho.fecha_hecho) {
                const hechoFecha = new Date(hecho.fecha_hecho);
                const desde = new Date(fechaDesde);
                match = match && hechoFecha >= desde;
            }

            // Filtro de fecha hasta
            if (fechaHasta && hecho.fecha_hecho) {
                const hechoFecha = new Date(hecho.fecha_hecho);
                const hasta = new Date(fechaHasta);
                match = match && hechoFecha <= hasta;
            }

            return match;
        });

        // Actualizar marcadores y estadísticas
        addMarkersToMap(filteredHechos);
        updateStats();

        // Ajustar el mapa para mostrar todos los marcadores
        if (filteredHechos.length > 0 && markers.length > 0) {
            const group = new L.featureGroup(markers);
            map.fitBounds(group.getBounds().pad(0.1));
        }

        console.log(`✅ Filtros aplicados: ${filteredHechos.length} hechos mostrados`);
    }

    // Limpiar filtros
    function clearFilters() {
        document.getElementById('searchInput').value = '';
        document.getElementById('categoriaFilter').value = '';
        document.getElementById('fechaDesde').value = '';
        document.getElementById('fechaHasta').value = '';

        filteredHechos = [...allHechos];
        addMarkersToMap(filteredHechos);
        updateStats();

        // Restaurar vista inicial
        map.setView([-38.4161, -63.6167], 5);

        console.log('✅ Filtros limpiados');
    }

    // Actualizar estadísticas
    function updateStats() {
        document.getElementById('hechosCount').textContent = filteredHechos.length;
        const categoriasUnicas = new Set(filteredHechos.map(h => h.categoria).filter(c => c));
        document.getElementById('categoriasCount').textContent = categoriasUnicas.size;
    }

    // Mostrar/ocultar loading
    function showLoading(show) {
        const spinner = document.getElementById('loadingSpinner');
        spinner.style.display = show ? 'flex' : 'none';
    }

    // Función global para centrar el mapa (usada en los popups)
    window.centrarEnMapa = function(lat, lng) {
        map.setView([lat, lng], 12);
    };

    // Event Listeners
    document.getElementById('aplicarFiltros').addEventListener('click', applyFilters);
    document.getElementById('limpiarFiltros').addEventListener('click', clearFilters);
    
    document.getElementById('searchInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            applyFilters();
        }
    });

    // Inicialización
    console.log('🚀 Inicializando aplicación del mapa...');
    initMap();
    cargarHechos();
});
