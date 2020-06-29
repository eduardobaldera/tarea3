package edu.pucmm.eict.controladores;

import edu.pucmm.eict.encapsulaciones.CarroCompra;
import edu.pucmm.eict.encapsulaciones.Producto;
import edu.pucmm.eict.encapsulaciones.Usuario;
import edu.pucmm.eict.servicios.FakeServices;
import edu.pucmm.eict.util.BaseControlador;
import io.javalin.Javalin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Representa las rutas para manejar las operaciones de petición - respuesta.
 */
public class CrudTradicionalControlador extends BaseControlador {

    FakeServices fakeServices = FakeServices.getInstancia();

    public CrudTradicionalControlador(Javalin app) {
        super(app);
    }

    /**
     * Las clases que implementan el sistema de plantilla están agregadas en PlantillasControlador.
     */
    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/tarea2/", () -> {


                get("/", ctx -> {
                    ctx.redirect("/tarea2/listar");
                });

                // Render de Login de usuarios
                // http://localhost:7000/usuarios/login/
                get("/login/", ctx -> {
                    //CarroCompra carrito = ctx.sessionAttribute("carrito");
                    Map<String, Object> modelo = new HashMap<>();
                    //modelo.put("usr", false);
                    //modelo.put("admin", false);
                    //modelo.put("title", "Login de Usuario");
                    //contexto.put("cantidad", carrito.getListaProductos().size());
                    ctx.render("/templates/crud-tradicional/login.html", modelo);
                });

                // Manejo de Login de usuarios
                // http://localhost:7000/api/usuarios/login/
                post("/usuarios/login/", ctx -> {
                    String usr = ctx.formParam("usuario");
                    String passw = ctx.formParam("password");
                    Usuario tmp = fakeServices.loginUsuario(usr, passw);
                    ctx.sessionAttribute("usuario", tmp);
                    ctx.redirect("/");
                });

                // Logout de usuarios
                // http://localhost:7000/api/usuarios/logout/
                get("/usuarios/logout/", ctx -> {
                    Usuario usr = ctx.sessionAttribute("usuario");
                    fakeServices.logoutUsuario();
                    ctx.redirect("/api/usuarios/login/");
                });


                get("/listar", ctx -> {
                    //tomando el parametro utl y validando el tipo.
                    List<Producto> lista = fakeServices.listarProducto();
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Administrar Productos");
                    modelo.put("lista", lista);
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/listar.html", modelo);
                });

                get("/comprar", ctx -> {
                    //tomando el parametro utl y validando el tipo.
                    List<Producto> lista = fakeServices.listarProducto();
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de Productos");
                    modelo.put("lista", lista);
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/comprar.html", modelo);
                });

                //Agregando un producto al carrito

                post("/agregar/:id", ctx -> {
                    CarroCompra carrito = ctx.sessionAttribute("carrito");
                    Producto preprod = fakeServices.getProductoPorId(ctx.pathParam("id", Integer.class).get());
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    //agregar cantidad
                    Producto producto = new Producto(preprod.getId(), preprod.getNombre(), preprod.getPrecio());
                    carrito.getListaProductos().add(producto);
                    ctx.redirect("/tarea2/listar/");
                });


                // Carrito de compras
                // http://localhost:7000/api/carrito
                get("/carrito/", ctx -> {
                    CarroCompra carrito = ctx.sessionAttribute("carrito");
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("titulo", "Carrito de Compra");
                    contexto.put("carrito", carrito);
                    contexto.put("cantidad", carrito.getListaProductos().size());
                    contexto.put("usr", fakeServices.getUsr());
                    contexto.put("admin", fakeServices.getAdm());
                    contexto.put("usuario", ctx.sessionAttribute("usuario"));
                    ctx.render("/templates/crud-tradicional/carrito.html", contexto);
                });

                get("/crear", ctx -> {
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Registrar Producto");
                    modelo.put("accion", "/tarea2/crear");
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                /**
                 * manejador para la creación del estudiante, una vez creado
                 * pasa nuevamente al listado.
                 */
                post("/crear", ctx -> {
                    //obteniendo la información enviada.
                    int matricula = ctx.formParam("matricula", Integer.class).get();
                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("carrera"));
                    //
                    Producto tmp = new Producto(matricula, nombre, precio);
                    //realizar algún tipo de validación...
                    fakeServices.crearProducto(tmp); //puedo validar, existe un error enviar a otro vista.
                    ctx.redirect("/tarea2/");
                });

                get("/visualizar/:id", ctx -> {
                    Producto producto = fakeServices.getProductoPorId(ctx.pathParam("id", Integer.class).get());
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Visualizar Producto "+ producto.getId());
                    modelo.put("producto", producto);
                    modelo.put("visualizar", true); //para controlar en el formulario si es visualizar
                    modelo.put("accion", "/tarea2/");

                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                get("/editar/:id", ctx -> {
                    Producto producto = fakeServices.getProductoPorId(ctx.pathParam("id", Integer.class).get());
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Editar Producto "+producto.getId());
                    modelo.put("producto", producto);
                    modelo.put("accion", "/tarea2/editar");

                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                /**
                 * Proceso para editar un producto.
                 */
                post("/editar", ctx -> {
                    //obteniendo la información enviada.
                    int id = ctx.formParam("Id", Integer.class).get();
                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("Precio"));
                    //
                    Producto tmp = new Producto(id, nombre, precio);
                    //realizar algún tipo de validación...
                    fakeServices.actualizarProducto(tmp); //puedo validar, existe un error enviar a otro vista.
                    ctx.redirect("/tarea2/");
                });

                /**
                 * Puede ser implementando por el metodo post, por simplicidad utilizo el get. ;-D
                 */
                get("/eliminar/:matricula", ctx -> {
                    fakeServices.eliminandoProducto(ctx.pathParam("matricula", Integer.class).get());
                    ctx.redirect("/tarea2/");
                });

            });
        });
    }
}
