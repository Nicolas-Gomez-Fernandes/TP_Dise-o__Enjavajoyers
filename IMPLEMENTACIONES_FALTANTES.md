# 🚧 Implementaciones Necesarias para Completar el TP

Este documento detalla **SOLO las funcionalidades esenciales** que faltan para completar el Trabajo Práctico. Sin extras, sin opcionales.

---

## 📋 Las 3 Cosas Críticas que Faltan

1. [Cargar Datos Reales en CSV](#1-cargar-datos-reales-en-csv)
2. [Importador Automático de CSV](#2-importador-automático-de-csv)
3. [Sincronización Agregador ↔ Fuente Estática](#3-sincronización-agregador--fuente-estática)

---

## 1. Cargar Datos Reales en CSV

### 📁 Archivo: `servicio-fuente-estatica/src/main/resources/hechos.csv`

**Estado:** Vacío o con 2-3 hechos de prueba

**Qué hacer:**
Agregar **mínimo 20 hechos históricos** de Argentina en el CSV (pueden ser más, pero con 20 alcanza)

**Formato:**
```csv
titulo,descripcion,fecha_acontecimiento,provincia,categoria,latitud,longitud
Revolución de Mayo,Inicio del proceso independentista argentino,1810-05-25,Ciudad Autónoma de Buenos Aires,POLITICO,-34.603722,-58.381592
Declaración de la Independencia,Congreso de Tucumán declara la independencia,1816-07-09,Tucumán,POLITICO,-26.808285,-65.217590
```

**Reglas simples:**
- Fecha formato: YYYY-MM-DD
- Coordenadas: cualquier lugar de Argentina
- Descripción: mínimo 50 caracteres
- Categorías válidas: POLITICO, BELICO, SOCIAL, ECONOMICO, CULTURAL, DEPORTIVO, CIENTIFICO, CATASTROFE_NATURAL

---

## 2. Importador Automático de CSV

### 📁 Crear: `servicio-fuente-estatica/src/main/java/.../config/DataLoader.java`

**Estado:** No existe

**Qué hace:**
Cuando el servicio arranca, lee el CSV y guarda los hechos en la base de datos automáticamente.

**Código básico:**

```java
@Component
public class DataLoader implements ApplicationRunner {
    
    @Autowired
    private HechoRepository hechoRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Si ya hay datos, no importar de nuevo
        if (hechoRepository.count() > 0) {
            log.info("BD ya tiene datos. No se importa CSV.");
            return;
        }
        
        // Leer CSV desde resources
        ClassPathResource resource = new ClassPathResource("hechos.csv");
        List<String> lineas = Files.readAllLines(Paths.get(resource.getURI()));
        
        // Saltar header (primera línea)
        for (int i = 1; i < lineas.size(); i++) {
            String[] campos = lineas.get(i).split(",");
            
            Hecho hecho = new Hecho();
            hecho.setTitulo(campos[0]);
            hecho.setDescripcion(campos[1]);
            hecho.setFecha(LocalDate.parse(campos[2]));
            hecho.setProvincia(campos[3]);
            hecho.setCategoria(Categoria.valueOf(campos[4]));
            hecho.setLatitud(Double.parseDouble(campos[5]));
            hecho.setLongitud(Double.parseDouble(campos[6]));
            
            hechoRepository.save(hecho);
        }
        
        log.info("✅ Importados {} hechos desde CSV", lineas.size() - 1);
    }
}
```

**Eso es todo.** No necesita ser más complejo.

---

## 3. Sincronización Agregador ↔ Fuente Estática

### 📁 Modificar: `servicio-agregador/src/main/java/.../schedulers/HechoScheduler.java`

**Estado:** Existe pero no funciona bien

**Problema:**
El agregador se ejecuta cada minuto pero no trae los hechos de la fuente estática.

**Solución:**

**En el método que ya existe** (`actualizarHechosPorFuente()` o similar), verificar que:

1. Se consulte correctamente a `http://localhost:8080/estatica/hechos`
2. Se obtengan los hechos como lista de objetos
3. Se guarden en la base de datos `agregador_db`
4. Se vea en los logs: `✅ Recibidos X hechos`

**Revisar:**
- Que el WebClient esté bien configurado con la URL correcta
- Que el endpoint de fuente estática (`/estatica/hechos`) responda correctamente
- Que no haya errores de mapping entre DTOs

**No hace falta:**
- Deduplicación
- Detección de modificados/eliminados
- Algoritmos complejos

**Solo necesitamos:** Traer hechos de la fuente estática y guardarlos en el agregador. Simple.



---

## ✅ Checklist Para Entregar

```
❌ 1. CSV con 20+ hechos históricos
❌ 2. DataLoader.java que importe el CSV automáticamente
❌ 3. Sincronización funcionando (hechos aparecen en http://localhost:8085)
✅ 4. Todo lo demás ya está hecho
```

---

## 🎯 Plan Simple

### Día 1: CSV
- Buscar 20 hechos históricos en Wikipedia
- Escribirlos en el CSV con el formato correcto

### Día 2: DataLoader
- Crear la clase DataLoader.java
- Probar que funcione (ver logs al iniciar el servicio)

### Día 3: Sincronización
- Revisar HechoScheduler.java
- Verificar que traiga los hechos correctamente
- Probar en el navegador que aparezcan

### Día 4: Pruebas finales
- Levantar todo el sistema
- Verificar que funcione de punta a punta
- Listo para entregar ✅

---

## 📞 Contacto

Para dudas sobre implementaciones faltantes, consultar con el equipo Enjavajoyers.

**Repositorio:** [TP_Dise-o__Enjavajoyers](https://github.com/Nicolas-Gomez-Fernandes/TP_Dise-o__Enjavajoyers)

---

## 📄 Última Actualización

**Fecha:** 16 de noviembre de 2025  
**Versión:** 1.0  
**Estado del TP:** 70% completado (estimado)
