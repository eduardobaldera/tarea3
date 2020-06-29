package edu.pucmm.eict;

import edu.pucmm.eict.controladores.*;
import edu.pucmm.eict.encapsulaciones.CarroCompra;
import edu.pucmm.eict.encapsulaciones.Producto;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
            config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
            config.enableCorsForAllOrigins();
        }).start(getHerokuAssignedPort());

        //creando el manejador
        app.get("/", ctx -> {
            List<Producto> productosIniciales = new ArrayList<Producto>();
            ctx.sessionAttribute("carrito", new CarroCompra(1, productosIniciales));
            ctx.redirect("/tarea2/");
        });


        new CrudTradicionalControlador(app).aplicarRutas();


    }
    /**
     * Metodo para indicar el puerto en Heroku
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }
}
