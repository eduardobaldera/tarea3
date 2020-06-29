package edu.pucmm.eict.encapsulaciones;

import java.util.List;

public class CarroCompra {
    long id;
    List<Producto> listaProductos;

    public CarroCompra() {
    }

    public CarroCompra(long id, List<Producto> listaProductos) {
        this.id = id;
        this.listaProductos = listaProductos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }


}
