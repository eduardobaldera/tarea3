package edu.pucmm.eict.servicios;

import edu.pucmm.eict.encapsulaciones.CarroCompra;
import edu.pucmm.eict.encapsulaciones.Producto;
import edu.pucmm.eict.encapsulaciones.Usuario;
import edu.pucmm.eict.encapsulaciones.VentasProducto;

import java.math.BigDecimal;
import java.util.*;

/**
 * Ejemplo de servicio patron Singleton
 */
public class FakeServices {

    private static FakeServices instancia;
    private List<Producto> listaProducto = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<VentasProducto> listaVentas = new ArrayList<VentasProducto>();
    private boolean usr = false;
    private boolean adm = false;
    private CarroCompra carrito;

    /**
     * Constructor privado.
     */
    private FakeServices(){
        //anadiendo productos
        listaProducto.add(new Producto(0001, "Lapicero", new BigDecimal("0.03")));
        listaProducto.add(new Producto(0002, "Mesa", new BigDecimal("0.90")));
        //anadiendo los usuarios.
        listaUsuarios.add(new Usuario("admin", "admin", "admin"));
        listaUsuarios.add(new Usuario("logueado", "logueado", "logueado"));
        listaUsuarios.add(new Usuario("usuario", "usuario", "usuario"));
        //anadiendo ventas
        List<Producto> listaTemporalVenta = new ArrayList<Producto>();
        listaTemporalVenta.add(new Producto(1, "Motherboard", new BigDecimal("9500"), 2));
        listaTemporalVenta.add(new Producto(2, "CPU AMD Ryzen 5 3500", new BigDecimal("14350"), 1));
        listaVentas.add(new VentasProducto(1, new Date(), "Rafael Felipe", listaTemporalVenta));

    }

    public static FakeServices getInstancia(){
        if(instancia==null){
            instancia = new FakeServices();
        }
        return instancia;
    }
    public List<Usuario> getListaUsuarios(){
        return listaUsuarios;
    }

    public  List<Producto> listarProducto(){return  listaProducto;};

    public Producto getProductoPorId (int id){
        return  listaProducto.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public boolean eliminarProducto(Producto producto){
        return listaProducto.remove(producto);
    }


    public Producto getProductoEnCarrito(int id){
        return carrito.getListaProductos().stream().filter(producto -> producto.getId() == id).findFirst().orElse(null);
    }

    public void procesarVenta(VentasProducto venta){
        listaVentas.add(venta);
    }

    public List<VentasProducto> getListaVentas(){
        return listaVentas;
    }

    public void limpiarCarrito(){
        List<Producto> tmp = new ArrayList<Producto>();
        carrito.setListaProductos(tmp);
    }

    public Producto crearProducto(Producto producto){
        if(getProductoPorId(producto.getId())!=null){
            System.out.println("Producto Registrado...");
            return null; //generar una excepcion...
        }
        listaProducto.add(producto);
        return producto;
    }

    public Producto actualizarProducto(Producto producto){
        Producto tmp = getProductoPorId(producto.getId());
        if(tmp == null){//no existe, no puede se actualizado
            throw new RuntimeException("No puedo actualizar");
        }
        tmp.mezclar(producto);
        return tmp;
    }

    public boolean eliminandoProducto(int id){
        Producto tmp = new Producto();
        tmp.setId(id);
        return listaProducto.remove(tmp);
    }

    public Usuario getUsuarioPorNombreUsuario(String usr){
        return listaUsuarios.stream().filter(usuario -> usuario.getUsuario().equals(usr)).findFirst().orElse(null);
    }

    public Usuario loginUsuario(String usuario, String passw){
        Usuario tmp = getUsuarioPorNombreUsuario(usuario);
        if(tmp == null) {
            throw new RuntimeException("Usuario no existente!");
        } else if(tmp.getUsuario().equals("admin") && tmp.getPassword().equals("admin")) {
            adm = true;
            usr = false;
            return tmp;
        } else if(tmp.getUsuario().equals(usuario) && tmp.getPassword().equals(passw)) {
            usr = true;
            adm = false;
            return tmp;
        } else throw new RuntimeException("Password incorrecto!");
    }

    public void logoutUsuario() {
        usr = false;
        adm = false;
    }

    public boolean getUsr() { return usr; }

    public void setUsr(boolean loggeado) { usr = loggeado; }

    public boolean getAdm() { return adm; }

    public void setAdm(boolean admin) { adm = admin; }

    public CarroCompra getCarrito() { return carrito; }

    public void setCarrito(CarroCompra cart) { this.carrito = cart; }

}
