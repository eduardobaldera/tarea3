package edu.pucmm.eict.servicios;

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
//    private List<Estudiante> listaEstudiante = new ArrayList<>();
    private List<Producto> listaProducto = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<VentasProducto> listaVentas = new ArrayList<VentasProducto>();
    private boolean usr = false;
    private boolean adm = false;


    /**
     * Constructor privado.
     */
    private FakeServices(){
        //añadiendo los estudiantes.
        //listaEstudiante.add(new Estudiante(20011136, "Carlos Camacho", "ITT"));
        listaProducto.add(new Producto(0001, "Lapicero", new BigDecimal("0.03") ));
        //anadiendo los usuarios.
        listaUsuarios.add(new Usuario("admin", "admin", "admin"));
        listaUsuarios.add(new Usuario("logueado", "logueado", "logueado"));
        listaUsuarios.add(new Usuario("usuario", "usuario", "usuario"));

    }

    public static FakeServices getInstancia(){
        if(instancia==null){
            instancia = new FakeServices();
        }
        return instancia;
    }
//    public Usuario autheticarUsuario(String usuario, String password){
//        //simulando la busqueda en la base de datos.
//        return new Usuario(usuario, "Usuario "+usuario, password);
//    }
    public List<Usuario> getListaUsuarios(){
        return listaUsuarios;
    }

    public  List<Producto> listarProducto(){return  listaProducto;};

    public Producto getProductoPorId (int id){
        return  listaProducto.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
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

}
