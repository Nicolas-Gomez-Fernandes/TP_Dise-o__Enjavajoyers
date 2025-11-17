# 🗺️ MetaMapa - Sistema de Gestión de Hechos Históricos

Sistema distribuido de microservicios para gestionar hechos históricos de Argentina con múltiples fuentes de datos.

---

## 📋 Tabla de Contenidos
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Levantar el Sistema](#-levantar-el-sistema)
- [Funcionalidades Implementadas](#-funcionalidades-implementadas)
- [Estado del Proyecto](#-estado-del-proyecto)
- [Arquitectura](#-arquitectura)

---

## 🔧 Requisitos Previos

Antes de comenzar, asegurate de tener instalado:

### Software Requerido
- **Java 17** (JDK 17.0.12 o superior)
- **Maven 3.9.11** (o superior)
- **XAMPP** (para MySQL)
- **Git Bash** (para Windows)
- **VS Code** (recomendado) con extensiones:
  - Extension Pack for Java
  - Spring Boot Extension Pack

### Verificar Instalaciones

```bash
# Java
java -version
# Debe mostrar: java version "17.0.12" o superior

# Maven
mvn -version
# Debe mostrar: Apache Maven 3.9.11 o superior

# MySQL (desde XAMPP)
# Abrir XAMPP Control Panel y verificar que MySQL esté disponible
```

---

## 🚀 Instalación y Configuración

### 1️⃣ Clonar el Repositorio

```bash
git clone https://github.com/Nicolas-Gomez-Fernandes/TP_Dise-o__Enjavajoyers.git
cd TP_Dise-o__Enjavajoyers
```

### 2️⃣ Configurar MySQL (XAMPP)

1. **Abrir XAMPP Control Panel**
2. **Iniciar MySQL** (clic en "Start" junto a MySQL)
3. Esperar a que el botón se ponga verde con texto "Running"

**Configuración de Base de Datos:**
- **Host:** `localhost:3306`
- **Usuario:** `root`
- **Contraseña:** *(vacía - sin contraseña)*
- **Bases de datos:** Se crean automáticamente al iniciar los servicios:
  - `agregador_db`
  - `estatica_db`
  - `estadistica_db`
  - `gestionUsuario_db`

### 3️⃣ Configurar Terminal en VS Code (Opcional)

Si usás Windows, configurá Git Bash con Java 17:

**Archivo:** `.vscode/settings.json`

```json
{
  "terminal.integrated.defaultProfile.windows": "Git Bash",
  "terminal.integrated.profiles.windows": {
    "Git Bash": {
      "path": "C:\\Program Files\\Git\\bin\\bash.exe",
      "env": {
        "PATH": "C:\\Program Files\\Maven\\apache-maven-3.9.11\\bin;C:\\Program Files\\Java\\jdk-17\\bin;${env:PATH}"
      }
    }
  }
}
```

---

## ▶️ Levantar el Sistema

### Orden de Arranque (IMPORTANTE)

Los servicios deben levantarse en este orden específico:

```
1. MySQL (XAMPP)
2. servicio-fuente-estatica (puerto 8080)
3. servicio-agregador (puerto 8083)
4. servicio-estadistica (puerto 8084)
5. gestion-de-usuarios (puerto 8086)
6. Interfaz_grafica (puerto 8085)
```

### Comandos para Levantar Servicios

**Abrir 5 terminales diferentes en VS Code** (una para cada servicio backend):

#### Terminal 1: servicio-fuente-estatica
```bash
cd servicio-fuente-estatica
mvn spring-boot:run
```
✅ Esperar mensaje: `Started ServicioFuenteEstaticaApplication in X seconds`

#### Terminal 2: servicio-agregador
```bash
cd servicio-agregador
mvn spring-boot:run
```
✅ Esperar mensaje: `Started ServicioAgregadorApplication in X seconds`

#### Terminal 3: servicio-estadistica
```bash
cd servicio-estadistica
mvn spring-boot:run
```
✅ Esperar mensaje: `Started EstadisticaApplication in X seconds`

#### Terminal 4: gestion-de-usuarios
```bash
cd gestion-de-usuarios
mvn spring-boot:run
```
✅ Esperar mensaje: `Started GestionDeUsuariosApplication in X seconds`

#### Terminal 5: Interfaz_grafica
```bash
cd Interfaz_grafica
mvn spring-boot:run
```
✅ Esperar mensaje: `Started InterfazGraficaApplication in X seconds`

### ✅ Verificar que Todo Esté Corriendo

Una vez que todos los servicios estén levantados, accedé a:

🌐 **http://localhost:8085**

Deberías ver la página de inicio de MetaMapa.

---

## 🎯 Funcionalidades Implementadas

### ✅ Paso 2: Sistema de Solicitudes de Eliminación

#### 🔐 Registro de Usuarios

**URL:** `http://localhost:8085/usuarios/register`

**Campos del formulario:**
- **Nombre*** (obligatorio)
- Apellido (opcional)
- Fecha de nacimiento (opcional)
- **Email*** (obligatorio - debe ser único)
- **Contraseña*** (obligatorio)
- **Confirmar contraseña*** (obligatorio)

**Flujo:**
1. Completar formulario de registro
2. Sistema valida que las contraseñas coincidan
3. Sistema verifica que el email no esté registrado
4. Usuario creado con éxito → Redirige al login
5. Iniciar sesión con email y contraseña

#### 🚨 Reportar Hechos (Solicitud de Eliminación)

**Acceso:** 
1. Iniciar sesión con tu cuenta
2. Ver detalle de un hecho histórico
3. Clic en botón **"Reportar Hecho"** (🔺 triángulo de advertencia)

**Formulario de Solicitud:**
- **ID del Hecho:** Se completa automáticamente
- **Fundamento:** Texto explicativo (mínimo 500 caracteres)
  - Sistema cuenta caracteres en tiempo real
  - Validación antes de enviar

**Flujo completo:**
```
Usuario logueado 
→ Detalle del hecho 
→ Clic "Reportar Hecho"
→ Completar fundamento (500+ caracteres)
→ Enviar solicitud
→ Estado: PENDIENTE
→ Administrador revisa
→ Estado: ACEPTADA / RECHAZADA
```

**Estados de Solicitudes:**
- **PENDIENTE:** Esperando revisión del administrador
- **ACEPTADA:** Admin aprobó la eliminación
- **RECHAZADA:** Admin rechazó la solicitud

#### 👨‍💼 Panel de Administración (Solo Admins)

**URL:** `http://localhost:8085/admin/solicitudes-eliminacion`

**Funcionalidades:**
- Ver lista de todas las solicitudes de eliminación
- Filtrar por estado (PENDIENTE, ACEPTADA, RECHAZADA)
- Ver detalles de cada solicitud:
  - ID del hecho reportado
  - Título del hecho
  - Usuario que reportó
  - Fundamento completo
  - Fecha de creación
- **Acciones:**
  - ✅ Aceptar solicitud → Marca hecho como eliminado
  - ❌ Rechazar solicitud → Rechaza la eliminación

---

## 📊 Estado del Proyecto

### ✅ Completado

- **Microservicios Base:**
  - ✅ servicio-fuente-estatica (puerto 8080)
  - ✅ servicio-agregador (puerto 8083)
  - ✅ servicio-estadistica (puerto 8084)
  - ✅ gestion-de-usuarios (puerto 8086)
  - ✅ Interfaz_grafica (puerto 8085)

- **Funcionalidades:**
  - ✅ Registro de usuarios
  - ✅ Login/logout con Spring Security
  - ✅ Gestión de colecciones (CRUD)
  - ✅ Visualización de hechos históricos
  - ✅ **Paso 2: Solicitudes de eliminación de hechos**
  - ✅ Panel de administración para solicitudes

### 🚧 En Desarrollo (Fuente Estática)

Para completar la **Fuente de Datos Estática** falta:

#### 1️⃣ Carga de Datos CSV
**Archivo:** `servicio-fuente-estatica/src/main/resources/hechos.csv`

Actualmente el CSV está vacío o con pocos datos. Se necesita:
- Cargar hechos históricos reales de Argentina
- Formato CSV correcto con columnas:
  - `titulo`
  - `descripcion`
  - `fecha_acontecimiento` (formato: YYYY-MM-DD)
  - `provincia`
  - `categoria`
  - `latitud`
  - `longitud`

**Ejemplo de contenido necesario:**
```csv
titulo,descripcion,fecha_acontecimiento,provincia,categoria,latitud,longitud
Revolución de Mayo,Inicio del proceso independentista argentino,1810-05-25,Ciudad Autónoma de Buenos Aires,POLITICO,-34.603722,-58.381592
Declaración de Independencia,Declaración de la independencia de las Provincias Unidas,1816-07-09,Tucumán,POLITICO,-26.808285,-65.217590
```

#### 2️⃣ Importación Automática al Iniciar
**Clase:** `servicio-fuente-estatica/.../DataLoader.java`

El servicio debe:
- Leer el archivo CSV al iniciar
- Parsear datos correctamente
- Insertar hechos en la base de datos `estatica_db`
- Manejar errores de formato
- Log de hechos importados

#### 3️⃣ API REST Funcional
**Endpoints necesarios:**

```
GET /estatica/hechos              → Listar todos los hechos
GET /estatica/hechos/{id}         → Obtener un hecho por ID
GET /estatica/hechos/filtrar?categoria=POLITICO&provincia=Buenos+Aires
POST /estatica/hechos             → Crear nuevo hecho (Admin)
PUT /estatica/hechos/{id}         → Actualizar hecho (Admin)
DELETE /estatica/hechos/{id}      → Eliminar hecho (Admin)
```

#### 4️⃣ Integración con Agregador
**servicio-agregador** debe:
- Consultar periódicamente a servicio-fuente-estatica
- Sincronizar hechos con su propia base de datos
- Aplicar algoritmos de consenso/deduplicación
- Mostrar hechos estáticos en la interfaz gráfica

#### 5️⃣ Testing
- Unit tests para CriterioFecha (✅ YA CORREGIDO)
- Integration tests para endpoints REST
- Tests de carga de CSV

---

### ❌ No Implementado (Otras Fuentes)

Estas funcionalidades están fuera del alcance del TP actual:

- ❌ Fuente Dinámica (puerto 8081) - API externa
- ❌ Fuente Proxy (puerto 8082) - Proxy para otras APIs
- ❌ Integración con servicios externos

**NOTA:** Para este TP solo nos enfocamos en la **Fuente Estática**.

---

## 🏗️ Arquitectura

### Microservicios

```
┌─────────────────────────────────────────────────────┐
│         Interfaz_grafica (Puerto 8085)              │
│              Spring Boot + Thymeleaf                │
└────────────────────┬────────────────────────────────┘
                     │
        ┌────────────┼────────────┬──────────────┐
        │            │            │              │
        ▼            ▼            ▼              ▼
┌──────────────┐ ┌───────────┐ ┌─────────┐ ┌──────────┐
│  Agregador   │ │Estadística│ │Usuarios │ │F.Estática│
│  (8083)      │ │  (8084)   │ │ (8086)  │ │ (8080)   │
└──────┬───────┘ └─────┬─────┘ └────┬────┘ └────┬─────┘
       │               │            │           │
       └───────────────┴────────────┴───────────┘
                       │
                       ▼
              ┌─────────────────┐
              │  MySQL (XAMPP)  │
              │  localhost:3306 │
              └─────────────────┘
```

### Base de Datos (MySQL)

```sql
-- Bases de datos creadas automáticamente
agregador_db         (Hechos consolidados, colecciones, solicitudes)
estatica_db          (Hechos de fuente estática - CSV)
estadistica_db       (Estadísticas y métricas)
gestionUsuario_db    (Usuarios, roles, permisos)
```

---

## 🛠️ Solución de Problemas Comunes

### ❌ Error: "Connection refused: no further information"

**Causa:** MySQL no está corriendo

**Solución:**
1. Abrir XAMPP Control Panel
2. Hacer clic en "Start" junto a MySQL
3. Esperar a que aparezca en verde "Running"
4. Reiniciar el servicio que falló

---

### ❌ Error: "version can neither be null, empty nor blank"

**Causa:** Problema con `annotationProcessorPath` en POM.xml

**Solución:** Ya está corregido en:
- `servicio-fuente-estatica/pom.xml`
- `gestion-de-usuarios/pom.xml`

Si aparece de nuevo, agregar versión a Lombok:
```xml
<path>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>${lombok.version}</version>
</path>
```

---

### ❌ Puerto ya en uso

**Causa:** Otro proceso usa el puerto

**Solución (Windows):**
```bash
# Ver qué proceso usa el puerto 8080 (ejemplo)
netstat -ano | findstr :8080

# Matar proceso por PID
taskkill /PID <numero_pid> /F
```

---

### ❌ Java 25 no compatible

**Causa:** Maven Compiler Plugin incompatible con Java 25

**Solución:** Usar Java 17 (ya configurado en todos los POMs)

---

## 📞 Contacto

**Equipo:** Enjavajoyers  
**Repositorio:** [TP_Dise-o__Enjavajoyers](https://github.com/Nicolas-Gomez-Fernandes/TP_Dise-o__Enjavajoyers)

---

## 📄 Licencia

Este proyecto es parte del Trabajo Práctico de la materia Diseño de Sistemas de Información - UTN FRBA 2025.