#!/bin/bash

# Script para detener todos los servicios de MetaMapa
# Uso: ./stop-metamapa.sh

echo "🛑 Deteniendo MetaMapa - Todos los servicios"
echo "=========================================="

# Directorio base del proyecto
BASE_DIR="/c/Users/nicol/OneDrive/Escritorio/TP_Dise-o__Enjavajoyers"

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para detener un servicio
stop_service() {
    local service_name=$1
    local pid_file="$BASE_DIR/logs/$service_name.pid"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        echo -e "${YELLOW}[INFO]${NC} Deteniendo $service_name (PID: $pid)..."
        
        # Intentar detener el proceso
        kill $pid 2>/dev/null
        
        # Esperar un momento
        sleep 2
        
        # Verificar si el proceso sigue vivo
        if kill -0 $pid 2>/dev/null; then
            echo -e "${RED}[WARN]${NC} $service_name no respondió, forzando detención..."
            kill -9 $pid 2>/dev/null
        fi
        
        # Eliminar el archivo PID
        rm -f "$pid_file"
        
        echo -e "${GREEN}[OK]${NC} $service_name detenido"
    else
        echo -e "${YELLOW}[WARN]${NC} No se encontró PID para $service_name"
    fi
}

echo ""
echo "⏹️  Deteniendo servicios..."
echo ""

# Detener en orden inverso al inicio
stop_service "Interfaz_grafica"
stop_service "gestion-de-usuarios"
stop_service "servicio-estadistica"
stop_service "servicio-agregador"
stop_service "servicio-fuente-estatica"

# También matar cualquier proceso Maven que pueda haber quedado
echo ""
echo -e "${YELLOW}[INFO]${NC} Limpiando procesos Maven residuales..."
# En Windows con Git Bash, usar taskkill
taskkill //F //FI "WINDOWTITLE eq mvn*" 2>/dev/null || true
taskkill //F //FI "IMAGENAME eq java.exe" //FI "WINDOWTITLE eq *spring-boot*" 2>/dev/null || true

echo ""
echo "=========================================="
echo -e "${GREEN}✅ Todos los servicios detenidos${NC}"
echo ""
