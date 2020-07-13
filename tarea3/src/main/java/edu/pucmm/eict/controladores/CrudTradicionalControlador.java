package edu.pucmm.eict.controladores;

import edu.pucmm.eict.encapsulaciones.CarroCompra;
import edu.pucmm.eict.encapsulaciones.Producto;
import edu.pucmm.eict.encapsulaciones.Usuario;
import edu.pucmm.eict.encapsulaciones.VentasProducto;
import edu.pucmm.eict.servicios.FakeServices;
import edu.pucmm.eict.util.BaseControlador;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;

import java.math.BigDecimal;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Representa las rutas para manejar las operaciones de petición - respuesta.
 */
public class CrudTradicionalControlador extends BaseControlador {

    FakeServices fakeServices = FakeServices.getInstancia();
    //Registro de sistemas de plantillas
    private void registroPlantillas() {
        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");
    }
    CarroCompra carrito;

    public CrudTradicionalControlador(Javalin app) {
        super(app);
    }

    /**
     * Las clases que implementan el sistema de plantilla están agregadas en PlantillasControlador.
     */
    @Override
    public void aplicarRutas() {
        app.routes(() -> {

            //carrito en sesion
            before(ctx -> {
                carrito = ctx.sessionAttribute("carrito");
                if(carrito == null) {
                    List<Producto> productosIniciales = new ArrayList<Producto>();
                    ctx.sessionAttribute("carrito", new CarroCompra(1, productosIniciales));
                }
            });

            path("/tarea2/", () -> {


                get("/", ctx -> {
                    ctx.redirect("/tarea2/comprar");
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
                post("/login/", ctx -> {
                    String usr = ctx.formParam("usuario");
                    String passw = ctx.formParam("password");
                    Usuario tmp = fakeServices.loginUsuario(usr, passw);
                    ctx.sessionAttribute("usuario", tmp);
                    ctx.redirect("/");
                });

                // Logout de usuarios
                // http://localhost:7000/api/usuarios/logout/
                get("/logout/", ctx -> {
                    Usuario usr = ctx.sessionAttribute("usuario");
                    fakeServices.logoutUsuario();
                    ctx.redirect("login/");
                });


                get("/listar", ctx -> {
                    //tomando el parametro utl y validando el tipo.
                    List<Producto> lista = fakeServices.listarProducto();
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Administrar Productos");
                    modelo.put("lista", lista);
                    modelo.put("usr", fakeServices.getUsr());
                    modelo.put("admin", fakeServices.getAdm());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/listar.ftl", modelo);
                });

                get("/comprar", ctx -> {
                    //tomando el parametro utl y validando el tipo.
                    List<Producto> lista = fakeServices.listarProducto();
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de Productos");
                    modelo.put("lista", lista);
                    modelo.put("usr", fakeServices.getUsr());
                    modelo.put("admin", fakeServices.getAdm());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/comprar.ftl", modelo);
                });

                //Agregando un producto al carrito

                post("/agregar/:id", ctx -> {
                    Producto preprod = fakeServices.getProductoPorId(ctx.pathParam("id", Integer.class).get());
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    Producto producto = new Producto(preprod.getId(), preprod.getNombre(), preprod.getPrecio(), cantidad);
                    carrito.getListaProductos().add(producto);
                    ctx.redirect("/tarea2/comprar");
                });


                // Carrito de compras
                // http://localhost:7000/api/carrito
                get("/carrito/", ctx -> {
                    CarroCompra carrito = ctx.sessionAttribute("carrito");
                    List<Producto> lista = carrito.getListaProductos();
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Carrito de Compra");
                    modelo.put("carrito", carrito);
                    modelo.put("lista", lista);
                    modelo.put("cantidad", carrito.getListaProductos().size());
                    modelo.put("usr", fakeServices.getUsr());
                    modelo.put("admin", fakeServices.getAdm());
                    modelo.put("usuario", ctx.sessionAttribute("usuario"));
                    ctx.render("/templates/crud-tradicional/carrito.ftl", modelo);
                });

                get("/carrito/eliminar/:id", ctx -> {
                    fakeServices.setCarrito(carrito);
                    Producto tmp = fakeServices.getProductoEnCarrito(ctx.pathParam("id", Integer.class).get());
                    fakeServices.getCarrito().borrarProducto(tmp);
                    ctx.sessionAttribute("carrito", fakeServices.getCarrito());
                    ctx.redirect("/tarea2/carrito");
                });

                get("/crear", ctx -> {
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Registrar Producto");
                    modelo.put("accion", "/tarea2/crear");
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/CrearEditar.ftl", modelo);
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
                    fakeServices.crearProducto(tmp);
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
                    ctx.render("/templates/crud-tradicional/visualizar.ftl", modelo);
                });

                get("/editar/:id", ctx -> {
                    Producto producto = fakeServices.getProductoPorId(ctx.pathParam("id", Integer.class).get());
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Editar Producto ");
                    modelo.put("producto", producto);
                    modelo.put("accion", "/tarea2/editar");
                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/CrearEditar.ftl", modelo);
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

                post("carrito/checkout/", ctx -> {
                    fakeServices.setCarrito(carrito);
                    long id = fakeServices.getListaVentas().get(fakeServices.getListaVentas().size() - 1).getId() + 1;
                    String nombreCliente = ctx.formParam("nombre");
                    VentasProducto venta = new VentasProducto(id, new Date(), nombreCliente, fakeServices.getCarrito().getListaProductos());
                    fakeServices.procesarVenta(venta);
                    fakeServices.limpiarCarrito();
                    ctx.sessionAttribute("carrito", fakeServices.getCarrito());
                    ctx.redirect("/tarea2/carrito/");
                });

                // Listado de ventas realizadas
                // http://localhost:7000/api/ventas
                get("/ventas", ctx -> {
                    Map<String, Object> contexto = new HashMap<>();
                    contexto.put("titulo", "Listado de Ventas Realizadas");
                    contexto.put("ventas", fakeServices.getListaVentas());
                    contexto.put("usr", fakeServices.getUsr());
                    contexto.put("admin", fakeServices.getAdm());
                    contexto.put("usuario", ctx.sessionAttribute("usuario"));
                    ctx.render("/templates/crud-tradicional/listarventas.ftl", contexto);
                });

            });

        });
    }
}
