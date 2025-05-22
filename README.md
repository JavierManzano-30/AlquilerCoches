# 🚗 RentJDMCar - Sistema de Alquiler de Coches Deportivos Japoneses

Bienvenido a **RentJDMCar**, una aplicación de escritorio en Java para la gestión de alquiler de coches deportivos japoneses, con interfaz gráfica moderna, validaciones de usuario, control de fechas y generación de facturas automáticas.

---

## 📦 Características

- Registro y edición de clientes con validaciones personalizadas
- Alquiler de coches con selección de fechas y bloqueo de días no disponibles
- Gestión automática de días de limpieza y fines de semana
- Visualización de alquileres activos con fechas de inicio y fin
- Cancelación individual de reservas por cliente
- Generación de facturas en formato `.txt`
- Visualización de coches por detalle
- Interfaz con `Swing` + `JCalendar`

---

## 🧠 Tecnologías y librerías

- Java 11+
- Swing
- JDBC (conexión a base de datos)
- [JCalendar 1.4](https://toedter.com/jcalendar/)
- SQLite (u otra BD compatible)
- Patrón DAO para acceso a datos
- Validaciones con expresiones regulares

---

## 🖥️ Estructura del proyecto

```
RentJDMCar/
│
├── controller/         → Conexión a la base de datos (ConexionBD.java)
├── dao/                → Acceso a datos (CocheDAO, ClienteDAO, AlquilerDAO...)
├── model/              → Modelos de datos (Coche, Cliente, Alquiler, etc.)
├── utils/              → Herramientas auxiliares (Validador.java, FileManager.java)
├── view/               → Interfaces gráficas (Swing - JFrames)
├── lib/                → Librerías externas (.jar como JCalendar, MySQL, etc.)
├── facturas/           → Archivos generados de facturas (.txt)
└── Main.java           → Punto de entrada principal
```

---

## ✅ Validaciones del sistema

| Campo         | Validación                                                                 |
|---------------|------------------------------------------------------------------------------|
| Nombre        | Solo letras y espacios, sin números                                         |
| Apellido      | Igual que nombre                                                            |
| Email         | Solo proveedores comunes como `@gmail.com`, `@hotmail.com`, `@outlook.es`  |
| Teléfono      | Mínimo 9 dígitos, solo números                                              |
| Contraseña    | Mínimo 6 caracteres                                                         |
| Fechas        | No permite alquiler en el pasado ni en días ocupados                       |

---

## 📝 Cómo ejecutar

1. Asegúrate de tener Java instalado (`java -version`)
2. Clona este repositorio o descarga el `.zip`
3. Abre el proyecto en Eclipse o tu IDE favorito
4. Añade el `.jar` de **JCalendar** a tu Build Path
5. Configura tu base de datos (archivo `ConexionBD.java`)
6. Ejecuta `LoginView.java`

---

## 🧪 Pruebas sugeridas

- Registrar un cliente con datos inválidos
- Intentar alquilar un coche en días ya reservados
- Cancelar una sola reserva sin afectar a otras
- Verificar generación de factura y lectura desde archivo

---

## 📃 Licencia

Este proyecto se ha desarrollado con fines educativos. Puedes modificar y reutilizar el código bajo tu responsabilidad.

---

## ✨ Autor

**Javier Manzano Oliveros** 
Contacto: `jmanzano3010@ejemplo.com`  
GitHub: (https://github.com/JavierManzano-30)
