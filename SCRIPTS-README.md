# 🚀 Scripts de Gestión de MetaMapa

Scripts para iniciar, detener y verificar el estado de todos los servicios de MetaMapa de forma rápida y segura.

## 📋 Scripts Disponibles

### 1. `start-metamapa.sh` - Iniciar todos los servicios

Inicia los 5 servicios de MetaMapa en el orden correcto:

```bash
./start-metamapa.sh
```

**¿Qué hace?**
- ✅ Inicia cada servicio en segundo plano
- ✅ Espera 3 segundos entre servicios para arranque seguro
- ✅ Guarda los PIDs en archivos para control posterior
- ✅ Redirige los logs a archivos individuales en `logs/`

**Orden de inicio:**
1. servicio-fuente-estatica (puerto 8080)
2. servicio-agregador (puerto 8083)
3. servicio-estadistica (puerto 8084)
4. gestion-de-usuarios (puerto 8086)
5. Interfaz_grafica (puerto 8085)

---

### 2. `stop-metamapa.sh` - Detener todos los servicios

Detiene todos los servicios de forma segura:

```bash
./stop-metamapa.sh
```

**¿Qué hace?**
- ✅ Lee los PIDs guardados
- ✅ Envía señal SIGTERM (detención elegante)
- ✅ Si no responde, fuerza con SIGKILL
- ✅ Limpia archivos PID
- ✅ Elimina procesos Maven residuales

---

### 3. `status-metamapa.sh` - Ver estado de los servicios

Muestra el estado actual de todos los servicios:

```bash
./status-metamapa.sh
```

**¿Qué muestra?**
- ✅ Estado de cada servicio (ACTIVO/INICIANDO/DETENIDO)
- ✅ PID del proceso
- ✅ Puerto asignado
- ✅ URLs de acceso
- ✅ Comandos útiles para ver logs

---

## 📝 Logs

Los logs de cada servicio se guardan en:

```
logs/
├── servicio-fuente-estatica.log
├── servicio-agregador.log
├── servicio-estadistica.log
├── gestion-de-usuarios.log
└── Interfaz_grafica.log
```

### Ver logs en tiempo real:

```bash
# Ver logs de un servicio específico
tail -f logs/servicio-fuente-estatica.log

# Ver logs de todos los servicios (en terminales separadas)
tail -f logs/*.log
```

### Ver últimas líneas de todos los logs:

```bash
tail -n 20 logs/*.log
```

---

## 🔧 Uso Típico

### Inicio del día:

```bash
./start-metamapa.sh
./status-metamapa.sh  # Verificar que todo arrancó bien
```

### Durante el desarrollo:

```bash
# Ver logs de un servicio específico
tail -f logs/Interfaz_grafica.log

# Verificar estado
./status-metamapa.sh
```

### Al finalizar:

```bash
./stop-metamapa.sh
```

---

## ⚠️ Troubleshooting

### Los servicios no inician:

1. Verifica que MySQL esté corriendo:
   ```bash
   docker ps | grep mysql
   ```

2. Verifica puertos ocupados:
   ```bash
   netstat -ano | findstr "8080 8083 8084 8085 8086"
   ```

3. Revisa los logs:
   ```bash
   tail -f logs/*.log
   ```

### Un servicio falla al iniciar:

1. Detén todos los servicios:
   ```bash
   ./stop-metamapa.sh
   ```

2. Revisa el log del servicio problemático:
   ```bash
   cat logs/[nombre-servicio].log
   ```

3. Inicia solo ese servicio manualmente:
   ```bash
   cd [carpeta-servicio]
   mvn spring-boot:run
   ```

### Limpiar todo y empezar de cero:

```bash
./stop-metamapa.sh
rm -rf logs/*.log logs/*.pid
./start-metamapa.sh
```

---

## 🌐 URLs de Acceso

Una vez iniciados todos los servicios:

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **Interfaz Web** | http://localhost:8085 | Aplicación web principal |
| **Fuente Estática** | http://localhost:8080/estatica/hechos | API de hechos estáticos |
| **Agregador** | http://localhost:8083/agregador/hechos | API agregadora |
| **Estadística** | http://localhost:8084/estadistica | API de estadísticas |
| **Gestión Usuarios** | http://localhost:8086/usuarios | API de autenticación |

---

## 💡 Tips

- **Tiempo de arranque**: El arranque completo toma ~30-45 segundos
- **Orden importa**: No cambies el orden de inicio de los servicios
- **Logs**: Revisa los logs si algo falla, tienen toda la información
- **Base de datos**: Asegúrate de que MySQL Docker esté corriendo antes de iniciar

---

## 🐳 Iniciar MySQL (si no está corriendo)

```bash
docker start mysql-tp
# o si no existe:
docker run --name mysql-tp -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql:8
```
