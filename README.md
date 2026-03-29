# Unicorn't Store CLI

Aplicación de consola Java para gestión de una tienda de poleras y tazones geek/memes.  
Módulo 4 del proyecto Unicorn't Store.

**Repositorio:** https://github.com/keber/ecommerce-cli-m4

---

## Requisitos

| Herramienta | Versión mínima |
|---|---|
| Java | 21 |
| Maven | 3.8+ |

---

## Ejecución

### Compilar

```bash
mvn clean compile
```

### Ejecutar

```bash
mvn exec:java
```

### Tests

```bash
mvn test
```

---

## Estructura del proyecto

```
src/
├── main/java/com/unicornt/store/
│   ├── Main.java
│   ├── exception/
│   │   └── InvalidQuantityException.java
│   ├── model/
│   │   ├── Product.java
│   │   ├── CartItem.java
│   │   ├── Cart.java
│   │   ├── Order.java
│   │   ├── AppliedDiscount.java
│   │   └── discount/
│   │       ├── DiscountRule.java         # interfaz
│   │       ├── AmountDiscount.java       # descuento por monto
│   │       └── CategoryDiscount.java     # descuento por categoria
│   ├── service/
│   │   ├── Catalog.java
│   │   ├── DataSeeder.java               # 49 productos
│   │   └── StoreService.java
│   └── ui/
│       ├── Console.java                  # orquestador
│       ├── ConsoleIO.java                # helpers de I/O
│       ├── AdminMenu.java
│       └── UserMenu.java
└── test/java/com/unicornt/store/
    ├── CartTest.java            # 7 tests
    ├── CatalogTest.java         # 16 tests
    ├── AmountDiscountTest.java  # 5 tests
    └── CategoryDiscountTest.java# 5 tests
```

---

## Menús

### Menu Principal

```
========================================================================
  UNICORN'T STORE - Menu Principal
========================================================================
  1) Administrador
  2) Usuario
  0) Salir
========================================================================
```

### Menu Administrador

```
------------------------------------------------------------------------
  ADMIN - Gestion de Productos
------------------------------------------------------------------------
  1) Listar productos
  2) Buscar productos
  3) Crear producto
  4) Editar producto
  5) Eliminar producto
  0) Volver al menu principal
------------------------------------------------------------------------
```

### Menu Usuario

```
------------------------------------------------------------------------
  USUARIO - Tienda
------------------------------------------------------------------------
  1) Listar productos
  2) Buscar productos
  3) Agregar al carrito
  4) Quitar del carrito
  5) Ver carrito
  6) Ver descuentos activos
  7) Confirmar compra
  0) Volver al menu principal
------------------------------------------------------------------------
```

---

## Descuentos activos

| Regla | Condicion | Descuento |
|---|---|---|
| Por monto | Total del carrito >= $30.000 | 10% sobre el total |
| Por categoria | Al menos 1 producto de la categoria `it-crowd` | 5% sobre el total |

Los descuentos son acumulables cuando se cumplen ambas condiciones.

---

## Ejemplo de compra

A continuacion se muestra una sesion completa desde el menu de usuario.

**1. Buscar un producto**

```
  Selecciona una opcion: 2
  Buscar (nombre/categoria): moss
  -- 1 resultado(s) --
  [12] Polera Moss | Categoria: it-crowd | Precio: $13.990
```

**2. Agregar dos productos al carrito**

```
  Selecciona una opcion: 3
  ID del producto: 12
  Cantidad: 2
  OK: Agregado al carrito - Polera Moss x2

  Selecciona una opcion: 3
  ID del producto: 7
  Cantidad: 1
  OK: Agregado al carrito - Polera Sysadmin x1
```

**3. Ver carrito**

```
  Selecciona una opcion: 5

  -- Carrito --
  [12] Polera Moss (x2) - Subtotal: $27.980
  [7]  Polera Sysadmin (x1) - Subtotal: $13.990
  ------------------------------------------------------------
  TOTAL BASE: $41.970
```

**4. Ver descuentos activos**

```
  Selecciona una opcion: 6

  -- Descuentos activos --
  1. Descuento por monto (minimo $30.000): 10%
  2. Descuento por categoria 'it-crowd': 5%
```

**5. Confirmar compra**

```
  Selecciona una opcion: 7

  -- Confirmar compra --
  [12] Polera Moss (x2) - Subtotal: $27.980
  [7]  Polera Sysadmin (x1) - Subtotal: $13.990
  ------------------------------------------------------------
  TOTAL BASE: $41.970

  Confirmar la compra? (s/N): s

  ========================================================================
  COMPRA CONFIRMADA
  ========================================================================
  Orden #1
  Total base  : $41.970
  Descuentos aplicados:
    - Descuento por monto (minimo $30.000): 10%  =>  -$4.197
    - Descuento por categoria 'it-crowd': 5%     =>  -$2.099
  ----------------------------------------
  TOTAL FINAL : $35.674
  ========================================================================
```

---

## Catalogo

El catalogo incluye 49 productos en las siguientes categorias:

| Categoria | Descripcion |
|---|---|
| `devops` | Poleras con memes de DevOps y cultura de sistemas |
| `it-crowd` | Poleras inspiradas en la serie IT Crowd |
| `linux` | Poleras con referencias a Linux y software libre |
| `programador` | Poleras con humor de desarrollo de software |
| `personajes` | Poleras con personajes icónicos de la cultura tech |
| `qa` | Poleras con humor de control de calidad |
| `general` | Poleras con memes geek generales |
