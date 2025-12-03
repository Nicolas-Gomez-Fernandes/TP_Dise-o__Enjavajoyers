#!/bin/bash

# Script para ver el estado de los servicios de MetaMapa
# Uso: ./status-metamapa.sh

echo "📊 Estado de MetaMapa"
echo "=========================================="

# Directorio base del proyecto
BASE_DIR="/c/Users/nicol/OneDrive/Escritorio/TP_Dise-o__Enjavajoyers"

# Colores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para verificar estado de un servicio
check_service() {
    local service_name=$1
    local port=$2
    local pid_file="$BASE_DIR/logs/$service_name.pid"
    
    printf "%-25s" "$service_name:"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if kill -0 $pid 2>/dev/null; then
            # Verificar si el puerto está escuchando
            if netstat -an | grep ":$port " | grep LISTEN > /dev/null 2>&1; then
                echo -e "${GREEN}✅ ACTIVO${NC} (PID: $pid, Puerto: $port)"
            else
                echo -e "${YELLOW}⏳ INICIANDO${NC} (PID: $pid, Puerto: $port)"
            fi
        else
            echo -e "${RED}❌ DETENIDO${NC} (PID inválido)"
            rm -f "$pid_file"
        fi
    else
        echo -e "${RED}❌ DETENIDO${NC}"
    fi
}

echo ""

# Verificar cada servicio
check_service "servicio-fuente-estatica" "8080"
check_service "servicio-agregador" "8083"
check_service "servicio-estadistica" "8084"
check_service "gestion-de-usuarios" "8086"
check_service "Interfaz_grafica" "8085"

echo ""
echo "=========================================="
echo ""
echo "🌐 URLs de acceso:"
echo "  • Fuente Estática:    http://localhost:8080/estatica/hechos"
echo "  • Agregador:          http://localhost:8083/agregador/hechos"
echo "  • Estadística:        http://localhost:8084/estadistica"
echo "  • API Usuarios:       http://localhost:8086/usuarios"
echo "  • Interfaz Web:       http://localhost:8085"
echo ""
echo "📝 Ver logs en tiempo real:"
echo "  tail -f logs/[nombre-servicio].log"
echo ""
