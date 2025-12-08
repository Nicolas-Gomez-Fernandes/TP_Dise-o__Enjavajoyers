# MetaMapa: Implementación del Módulo de Fuente de Datos Estática

> **Asignatura:** Diseño de Sistemas de Información (2025)  
> **Equipo:** EnJavaJoyers  
> **Versión del Entregable:** MVP - Vertical de Fuente Estática

---

## 1. Introducción y Contexto del Proyecto

Este repositorio aloja la implementación del microservicio **"Fuente de Datos Estática"**, un componente fundamental dentro de la arquitectura distribuida del sistema **MetaMapa**.

Originalmente, MetaMapa fue concebido como una plataforma ambiciosa para la gestión de mapeos colaborativos, cuya arquitectura completa incluía:
* Múltiples fuentes de datos (Estáticas, Dinámicas y Proxy).
* Servicios de agregación y algoritmos de consenso distribuido.
* Gestión de usuarios, autenticación (OAuth) y moderación.
* Visualización geoespacial y estadísticas.

## 2. Decisión Estratégica de Alcance

Dada la magnitud del diseño original y las restricciones temporales inelásticas del ciclo académico, el equipo técnico tomó una **decisión estratégica de acotar el alcance**.

Se decidió **no implementar la totalidad de los módulos** de forma superficial, sino enfocar el 100% de los recursos de desarrollo en la construcción **robusta, completa y funcional** del módulo de Fuente Estática.

### ¿Por qué elegimos SOLO la Fuente Estática?

Esta decisión se basa en el principio de **"Calidad sobre Cantidad"**. En lugar de entregar cuatro servicios inestables ("código espagueti"), entregamos un servicio sólido que cumple con los más altos estándares de diseño.

#### A. Valor Funcional (Data Seeding)
Un sistema de información geográfica es inútil sin datos. La Fuente Estática es el componente que inyecta la "verdad base" al sistema (datasets oficiales, históricos, etc.). Sin este módulo, los algoritmos de consenso y las visualizaciones no tienen materia prima sobre la cual trabajar. Es el **MVP (Producto Mínimo Viable)** más lógico.

#### B. Validación Arquitectónica End-to-End
Aunque funcionalmente parece "solo leer un archivo", técnicamente este módulo nos permitió validar toda la arquitectura propuesta en el TPA sin la complejidad accidental de los otros servicios:
* **Diseño de API REST:** Definición de contratos, verbos HTTP y manejo de errores.
* **Persistencia y ORM:** Mapeo de entidades complejas y transaccionalidad.
* **Patrones de Diseño:** Implementación de *Adapters*, *Strategies* (para el parsing) y *DTOs*.
* **Inyección de Dependencias:** Uso correcto del contenedor de Spring.

#### C. Complejidad de Negocio Específica ("Upsert")
Este módulo resuelve un desafío técnico no trivial exigido por el dominio: la **idempotencia en la carga de datos masivos**.
Implementamos una lógica de actualización inteligente (*Upsert*) que:
1.  Procesa archivos CSV de gran volumen (streaming).
2.  Detecta duplicados por criterios de negocio (título).
3.  Decide si crear un nuevo registro o actualizar los atributos del existente, garantizando la integridad de los datos sin intervención humana.

---

## 3. Módulos Descartados y Justificación Técnica
Para mayor transparencia, detallamos por qué se pospusieron los otros componentes:

**Fuentes Dinámicas**  --> Introduce una alta complejidad de **Seguridad** (Manejo de sesiones, JWT/OAuth, Roles y Permisos) que requiere un tiempo de desarrollo y testeo de seguridad que excedía el cronograma. Una implementación apresurada hubiera sido insegura.

**Fuentes Proxy** --> La integración con sistemas externos requiere manejo avanzado de **Resiliencia** (Circuit Breakers, Timeouts) y la creación de *Mocks* complejos para pruebas, lo cual consume mucho tiempo de configuración de entorno.

**Agregador / Consenso** --> Los algoritmos de unificación de hechos (consenso) implican una lógica difusa y de alta carga computacional que requiere múltiples iteraciones de refinamiento para funcionar correctamente.

## 4. Conclusión
La entrega actual no debe verse como un trabajo incompleto, sino como una **base sólida y extensible**.

Hemos entregado un microservicio que es capaz de:
1.  Ingestar datos masivos de forma eficiente.
2.  Persistirlos asegurando su integridad.
3.  Exponerlos mediante una interfaz clara y documentada.

Esta arquitectura desacoplada permite que, en futuras fases del proyecto, se puedan integrar los módulos de Fuentes Dinámicas y Agregación sin necesidad de refactorizar el núcleo que hemos construido hoy.

---
*Documento de justificación técnica - Grupo EnJavaJoyers 2025*