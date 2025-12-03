#!/bin/bash

# Script para iniciar todos los servicios de MetaMapa
# Uso: ./start-metamapa.sh

echo "🚀 Iniciando MetaMapa - Todos los servicios"
echo "=========================================="

# Directorio base del proyecto
BASE_DIR="/c/Users/nicol/OneDrive/Escritorio/TP_Dise-o__Enjavajoyers"

# Colores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para iniciar un servicio
start_service() {
    local service_name=$1
    local service_dir=$2
    local port=$3
    
    echo -e "${YELLOW}[INFO]${NC} Iniciando $service_name en puerto $port..."
    
    cd "$BASE_DIR/$service_dir"
    
    # Iniciar el servicio en background
    mvn spring-boot:run > "$BASE_DIR/logs/$service_name.log" 2>&1 &
    
    # Guardar el PID
    local pid=$!
    echo $pid > "$BASE_DIR/logs/$service_name.pid"
    
    echo -e "${GREEN}[OK]${NC} $service_name iniciado (PID: $pid)"
}

# Crear directorio de logs si no existe
mkdir -p "$BASE_DIR/logs"

# Limpiar logs antiguos
rm -f "$BASE_DIR/logs"/*.log
rm -f "$BASE_DIR/logs"/*.pid

echo ""
echo "📦 Iniciando servicios de backend..."
echo ""

# 1. Servicio Fuente Estática (8080)
start_service "servicio-fuente-estatica" "servicio-fuente-estatica" "8080"
sleep 3

# 2. Servicio Agregador (8083)
start_service "servicio-agregador" "servicio-agregador" "8083"
sleep 3

# 3. Servicio Estadística (8084)
start_service "servicio-estadistica" "servicio-estadistica" "8084"
sleep 3

# 4. Gestión de Usuarios (8086)
start_service "gestion-de-usuarios" "gestion-de-usuarios" "8086"
sleep 3

# 5. Interfaz Gráfica (8085)
start_service "Interfaz_grafica" "Interfaz_grafica" "8085"

echo ""
echo "=========================================="
echo -e "${GREEN}✅ Todos los servicios iniciados${NC}"
echo ""
echo "📊 Estado de los servicios:"
echo "  • Fuente Estática:    http://localhost:8080"
echo "  • Agregador:          http://localhost:8083"
echo "  • Estadística:        http://localhost:8084"
echo "  • Gestión Usuarios:   http://localhost:8086"
echo "  • Interfaz Gráfica:   http://localhost:8085"
echo ""
echo "📝 Los logs se guardan en: $BASE_DIR/logs/"
echo ""
echo "⏹️  Para detener todos los servicios, ejecuta: ./stop-metamapa.sh"
echo ""
