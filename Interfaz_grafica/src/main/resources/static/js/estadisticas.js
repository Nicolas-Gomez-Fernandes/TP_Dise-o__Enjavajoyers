// JavaScript para la página de estadísticas
document.addEventListener('DOMContentLoaded', function() {
    console.log('🚀 Inicializando página de estadísticas...');

    // Variables globales para los gráficos
    let categoriaChart, timelineChart, provinciaChart, coleccionChart;

    // Colores para los gráficos
    const colors = {
        primary: ['#667eea', '#764ba2', '#56ab2f', '#a8e063', '#f093fb', '#f5576c', '#fa709a', '#fee140'],
        gradient: {
            primary: 'rgba(102, 126, 234, 0.8)',
            success: 'rgba(86, 171, 47, 0.8)',
            warning: 'rgba(240, 147, 251, 0.8)',
            danger: 'rgba(250, 112, 154, 0.8)'
        }
    };

    // Cargar todas las estadísticas
    async function cargarEstadisticas() {
        try {
            console.log('📊 Cargando estadísticas...');

            // Cargar en paralelo con mejor manejo de errores
            const responses = await Promise.all([
                fetch('/estadisticas/api/categorias').then(res => {
                    console.log('Respuesta categorias:', res.status);
                    if (!res.ok) throw new Error(`Error categorías: ${res.status}`);
                    return res.json();
                }),
                fetch('/estadisticas/api/colecciones').then(res => {
                    console.log('Respuesta colecciones:', res.status);
                    if (!res.ok) throw new Error(`Error colecciones: ${res.status}`);
                    return res.json();
                }),
                fetch('/hechos/api/list').then(res => {
                    console.log('Respuesta hechos:', res.status);
                    if (!res.ok) throw new Error(`Error hechos: ${res.status}`);
                    return res.json();
                })
            ]);

            const [categorias, colecciones, hechos] = responses;
            console.log('✅ Datos cargados:', { 
                categorias: categorias.length, 
                colecciones: colecciones.length, 
                hechos: hechos.length 
            });

            // Actualizar tarjetas de resumen
            actualizarResumen(hechos, categorias, colecciones);

            // Crear gráficos
            crearGraficoCategorias(categorias);
            crearGraficoTimeline(hechos);
            crearGraficoProvincias(hechos);
            crearGraficoColecciones(colecciones);

            // Llenar tabla de categorías
            llenarTablaCategorias(categorias);

        } catch (error) {
            console.error('❌ Error al cargar estadísticas:', error);
            alert('Error al cargar las estadísticas. \n\nAsegúrate de que el servicio-estadistica esté corriendo en el puerto 8084.\n\nError: ' + error.message);
        }
    }

    // Actualizar tarjetas de resumen
    function actualizarResumen(hechos, categorias, colecciones) {
        console.log('📊 Ejemplo de hecho:', hechos[0]);
        
        document.getElementById('totalHechos').textContent = hechos.length;
        document.getElementById('totalCategorias').textContent = new Set(hechos.map(h => h.categoria).filter(c => c)).size;
        document.getElementById('totalColecciones').textContent = colecciones.length;
        
        // Los hechos pueden tener provincia en ubicacion.provincia o directamente
        const provincias = new Set(
            hechos.map(h => (h.ubicacion && h.ubicacion.provincia) || h.provincia)
                  .filter(p => p)
        );
        document.getElementById('totalProvincias').textContent = provincias.size || categorias.length;
    }

    // Crear gráfico de categorías (Pie Chart)
    function crearGraficoCategorias(categorias) {
        console.log('🎂 Creando gráfico de categorías con', categorias.length, 'registros');
        console.log('🎂 Datos recibidos:', categorias);
        
        const ctx = document.getElementById('categoriaChart');
        
        // Agrupar por categoría y contar
        const categoriasMap = {};
        categorias.forEach(cat => {
            const nombre = cat.categoria || 'Sin categoría';
            categoriasMap[nombre] = (categoriasMap[nombre] || 0) + (cat.cantidad_hechos_provincia || 0);
        });

        const labels = Object.keys(categoriasMap);
        const data = Object.values(categoriasMap);
        
        console.log('🎂 Labels:', labels);
        console.log('🎂 Data:', data);

        if (categoriaChart) categoriaChart.destroy();

        categoriaChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Cantidad de Hechos',
                    data: data,
                    backgroundColor: colors.primary,
                    borderWidth: 2,
                    borderColor: '#fff'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: {
                        position: 'right',
                        labels: {
                            font: {
                                size: 12
                            }
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                const label = context.label || '';
                                const value = context.parsed || 0;
                                const total = context.dataset.data.reduce((a, b) => a + b, 0);
                                const percentage = ((value / total) * 100).toFixed(1);
                                return `${label}: ${value} (${percentage}%)`;
                            }
                        }
                    }
                }
            }
        });
    }

    // Crear gráfico de línea de tiempo
    function crearGraficoTimeline(hechos) {
        const ctx = document.getElementById('timelineChart');
        
        console.log('📅 Creando gráfico de timeline con', hechos.length, 'hechos');
        
        // Agrupar por mes - probar diferentes campos de fecha
        const mesMap = {};
        hechos.forEach(hecho => {
            const fecha = hecho.fechaAcontecimiento || hecho.fecha_hecho || hecho.fecha || hecho.fechaCreacion;
            if (fecha) {
                const fechaObj = new Date(fecha);
                const mes = fechaObj.toLocaleDateString('es-AR', { year: 'numeric', month: 'short' });
                mesMap[mes] = (mesMap[mes] || 0) + 1;
            }
        });
        
        console.log('📅 Datos por mes:', mesMap);

        // Ordenar por fecha
        const labels = Object.keys(mesMap).sort((a, b) => {
            return new Date(a) - new Date(b);
        });
        const data = labels.map(label => mesMap[label]);
        
        console.log('📅 Timeline labels:', labels);

        if (timelineChart) timelineChart.destroy();
        
        if (labels.length === 0) {
            console.warn('⚠️ No hay datos de fechas para el timeline');
            return;
        }

        timelineChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Hechos por Mes',
                    data: data,
                    borderColor: colors.gradient.primary,
                    backgroundColor: 'rgba(102, 126, 234, 0.1)',
                    borderWidth: 3,
                    tension: 0.4,
                    fill: true,
                    pointRadius: 5,
                    pointHoverRadius: 7,
                    pointBackgroundColor: colors.gradient.primary,
                    pointBorderColor: '#fff',
                    pointBorderWidth: 2
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }
        });
    }

    // Crear gráfico de provincias (Bar Chart)
    function crearGraficoProvincias(hechos) {
        const ctx = document.getElementById('provinciaChart');
        
        console.log('🗺️ Creando gráfico de provincias');
        
        // Agrupar por provincia - puede estar en ubicacion.provincia o directamente
        const provinciaMap = {};
        hechos.forEach(hecho => {
            const provincia = (hecho.ubicacion && hecho.ubicacion.provincia) || hecho.provincia;
            if (provincia) {
                provinciaMap[provincia] = (provinciaMap[provincia] || 0) + 1;
            }
        });
        
        console.log('🗺️ Datos por provincia:', provinciaMap);

        // Ordenar y tomar top 10
        const sortedProvincias = Object.entries(provinciaMap)
            .sort((a, b) => b[1] - a[1])
            .slice(0, 10);

        const labels = sortedProvincias.map(p => p[0]);
        const data = sortedProvincias.map(p => p[1]);
        
        console.log('🗺️ Top 10 provincias:', labels);

        if (provinciaChart) provinciaChart.destroy();
        
        if (labels.length === 0) {
            console.warn('⚠️ No hay datos de provincias para mostrar');
            ctx.getContext('2d').fillText('No hay datos de provincias disponibles', 10, 50);
            return;
        }

        provinciaChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Cantidad de Hechos',
                    data: data,
                    backgroundColor: colors.gradient.success,
                    borderColor: 'rgba(86, 171, 47, 1)',
                    borderWidth: 2,
                    borderRadius: 8,
                    borderSkipped: false
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    },
                    x: {
                        ticks: {
                            maxRotation: 45,
                            minRotation: 45
                        }
                    }
                }
            }
        });
    }

    // Crear gráfico de colecciones
    function crearGraficoColecciones(colecciones) {
        const ctx = document.getElementById('coleccionChart');
        
        console.log('📚 Creando gráfico de colecciones:', colecciones);
        
        // Tomar top 10 colecciones
        const topColecciones = colecciones
            .sort((a, b) => (b.cantidad_hechos || 0) - (a.cantidad_hechos || 0))
            .slice(0, 10);

        const labels = topColecciones.map(c => c.titulo_coleccion || 'Sin título');
        const data = topColecciones.map(c => c.cantidad_hechos || 0);
        
        console.log('📚 Top 10 colecciones:', labels, data);

        if (coleccionChart) coleccionChart.destroy();
        
        if (labels.length === 0 || data.every(d => d === 0)) {
            console.warn('⚠️ No hay datos de colecciones para mostrar');
            return;
        }

        coleccionChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Cantidad de Hechos',
                    data: data,
                    backgroundColor: colors.gradient.warning,
                    borderColor: 'rgba(240, 147, 251, 1)',
                    borderWidth: 2,
                    borderRadius: 8,
                    borderSkipped: false
                }]
            },
            options: {
                indexAxis: 'y',
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    x: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }
        });
    }

    // Llenar tabla de categorías
    function llenarTablaCategorias(categorias) {
        const tbody = document.getElementById('categoriaTableBody');
        tbody.innerHTML = '';

        // Agrupar por categoría
        const categoriasAgrupadas = {};
        categorias.forEach(cat => {
            const nombre = cat.categoria || 'Sin categoría';
            if (!categoriasAgrupadas[nombre]) {
                categoriasAgrupadas[nombre] = {
                    categoria: nombre,
                    total: 0,
                    provincia: cat.provincia || '-',
                    cantidadProvincia: cat.cantidad_hechos_provincia || 0,
                    hora: cat.hora !== null && cat.hora !== undefined ? cat.hora : '-'
                };
            }
            categoriasAgrupadas[nombre].total += cat.cantidad_hechos_provincia || 0;
        });

        // Convertir a array y ordenar por total
        const categoriasArray = Object.values(categoriasAgrupadas)
            .sort((a, b) => b.total - a.total);

        // Crear filas
        categoriasArray.forEach(cat => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td><strong>${cat.categoria}</strong></td>
                <td>${cat.total}</td>
                <td>${cat.provincia}</td>
                <td>${cat.cantidadProvincia}</td>
                <td>${cat.hora === '-' ? '-' : `${cat.hora}:00 hs`}</td>
            `;
            tbody.appendChild(row);
        });
    }

    // Exportar categorías
    document.getElementById('exportCategorias').addEventListener('click', async function() {
        try {
            console.log('📥 Exportando categorías...');
            window.location.href = 'http://localhost:8084/estadisticas/exportar/categorias';
        } catch (error) {
            console.error('❌ Error al exportar categorías:', error);
            alert('Error al exportar categorías');
        }
    });

    // Exportar colecciones
    document.getElementById('exportColecciones').addEventListener('click', async function() {
        try {
            console.log('📥 Exportando colecciones...');
            window.location.href = 'http://localhost:8084/estadisticas/exportar/colecciones';
        } catch (error) {
            console.error('❌ Error al exportar colecciones:', error);
            alert('Error al exportar colecciones');
        }
    });

    // Inicializar
    cargarEstadisticas();
});
