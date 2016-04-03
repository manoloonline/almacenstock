/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;


/**
 *
 * @author tarde
 */

public class ProductoHistoricoBean implements Serializable{
    
    private Producto producto;
    private Historico historico;
    private int cantidadComprar;
    private double precioTotalCompra;
    
    public ProductoHistoricoBean(Producto producto, Historico historico) {
        this.producto = producto;
        this.historico = historico;
        int cantidadComprarAux = -(historico.getCantidad() - producto.getStockMin());
        if(cantidadComprarAux < 0)
            cantidadComprarAux = 0;
        if(producto.getPiezasCaja()>0){
            cantidadComprar = cantidadComprarAux;
            if(cantidadComprarAux%producto.getPiezasCaja()!=0){
                cantidadComprar -= cantidadComprarAux%producto.getPiezasCaja();
                cantidadComprar += producto.getPiezasCaja();
            }
            
        }
        precioTotalCompra = cantidadComprar*producto.getPrecio();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidadComprar() {
        return cantidadComprar;
    }

    public void setCantidadComprar(int cantidadComprar) {
        this.cantidadComprar = cantidadComprar;
    }

    public double getPrecioTotalCompra() {
        return precioTotalCompra;
    }

    public void setPrecioTotalCompra(double precioTotalCompra) {
        this.precioTotalCompra = precioTotalCompra;
    }
    
    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    
}
