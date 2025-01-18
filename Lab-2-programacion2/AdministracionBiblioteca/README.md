**Sistema de Gestión de Biblioteca**

# Descripción:

El Sistema de Gestión de Biblioteca es una aplicación Java que permite administrar las operaciones básicas de una biblioteca, incluyendo la gestión de libros, miembros, préstamos, reseñas y multas. El sistema proporciona una interfaz interactiva por consola que facilita la interacción con todas las funcionalidades disponibles.
Características Principales

- Gestión de libros y copias múltiples
- Registro y administración de miembros
- Sistema de préstamos y devoluciones
- Gestión de reseñas y calificaciones
- Sistema de multas por retrasos
- Búsqueda avanzada de libros
- Interfaz interactiva por consola

# Estructura del Proyecto 
El proyecto está organizado en las siguientes clases principales:
library/
├── Book.java               # Gestión de libros y sus copias
├── BookCopy.java          # Representa una copia física de un libro
├── Fine.java              # Manejo de multas
├── Library.java           # Clase principal que coordina todas las operaciones
├── LibraryCard.java       # Tarjeta de membresía
├── LibrarySearch.java     # Funcionalidades de búsqueda
├── Loan.java              # Gestión de préstamos
├── Member.java            # Información y gestión de miembros
├── Review.java            # Sistema de reseñas
└── InteractiveLibrarySystem.java  # Interfaz de usuario interactiva

# Requisitos del Sistema

Java 8 o superior
JDK instalado y configurado

**Terminal o consola para ejecutar la aplicación**

**Instalación**

# Clone el repositorio:

bashCopygit clone [https://github.com/samueldavidhhuhuhhuhuh/Lab-2-programacion2.git]



# Ejecute la aplicación:

AdministracionBiblioteca

### Guía de Uso
A continuación se presenta una guía paso a paso para probar las principales funcionalidades del sistema.

1. Configuración Inicial
Al iniciar el sistema, se solicitará la configuración básica de la biblioteca:
Nombre de la biblioteca: Biblioteca Central Municipal

Dirección: Av. Principal 123, Ciudad
2. Registro de Miembros
Para probar el sistema, se recomienda registrar al menos dos miembros:
Miembro 1:
- Nombre: Ana García
- Email: ana.garcia@email.com
- Dirección: Calle 45 #123

Miembro 2:
- Nombre: Carlos López
- Email: carlos.lopez@email.com
- Dirección: Avenida Central 789
Importante: Guarde los IDs generados para cada miembro, los necesitará para iniciar sesión.

3. Agregado de Libros
Ejemplos de libros para agregar al sistema:
Libro 1:
- Título: Cien años de soledad
- Autor: Gabriel García Márquez
- ISBN: 9780307474728
- Género: Ficción literaria
- Copias: 3

Libro 2:
- Título: 1984
- Autor: George Orwell
- ISBN: 9780451524935
- Género: Ciencia ficción
- Copias: 2

Libro 3:
- Título: El Principito
- Autor: Antoine de Saint-Exupéry
- ISBN: 9780156012195
- Género: Literatura infantil
- Copias: 4

4. Operaciones Principales
Búsqueda de Libros
El sistema permite búsquedas por:

Título (ej: "cien", "1984")
Autor (ej: "García", "Orwell")
Género (ej: "ficción")
Préstamos

# Inicie sesión con el ID de miembro
Seleccione "Tomar prestado un libro"
Elija un libro del catálogo usando su ID

# Reseñas
Ejemplos de reseñas para agregar:
CopyPara "Cien años de soledad":
- Calificación: 5
- Comentario: Una obra maestra de la literatura latinoamericana. 
Para "1984":
- Calificación: 4
- Comentario: Un libro impactante y muy relevante.

# Flujo de Prueba Recomendado

- Inicie el sistema y configure la biblioteca
- Registre los miembros sugeridos
- Agregue los libros de ejemplo
- Realice búsquedas de prueba
- Con el primer miembro:
Tome prestado un libro
Agregue una reseña

Con el segundo miembro:
Tome prestado otro libro
Agregue otra reseña


Devuelva libros y verifique multas
Explore las diferentes opciones de búsqueda
Verifique el catálogo actualizado

Tips y Sugerencias
Mejores Prácticas

Guarde todos los IDs generados por el sistema
Use términos parciales en las búsquedas
Verifique el estado de los libros después de cada operación
Revise las reseñas y calificaciones regularmente

# Consejos para Testing

Pruebe los límites del sistema (ej: intentar prestar más libros que copias disponibles)
Verifique el comportamiento con datos inválidos
Compruebe el sistema de multas con diferentes escenarios
Pruebe las búsquedas con diferentes criterios

