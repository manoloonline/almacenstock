/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entidades.ProductoHistoricoBean;
import entidades.Historico;
import entidades.Producto;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import modelo.servicio.GestionServicios;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author -
 */
@ManagedBean
@ViewScoped
public class InformesBean implements Serializable{

    /**
     * Creates a new instance of OpcionesBean
     */
    private String opcion;
    private boolean renderInforme;
    private boolean renderGrafico;
    private boolean renderQr;
    private String referencia;
    private List<Producto> productos;
    private List<Historico> listaHistorico;
    private List<ProductoHistoricoBean> listaProductoUltimoHistorico;
    private LineChartModel grafico;
    private String opcionCodigo;
    private List<String> selectedCodigos;
    private HashMap leyendasCodigos;
    private List<SelectItem> colores;
    private List<SelectItem> subcategorias;
    private List<SelectItem> categorias;
    
    
    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
    
    @ManagedProperty("#{servicio}")
    private GestionServicios gs;
    
    public InformesBean() {

    }
    
    @PostConstruct
    public void init() {
        opcion="1";
        renderInforme = true;
        renderGrafico = false;
        renderQr = false;
        productos = gs.obtenerProductos(loginBean.getU().getId());
        ordenarProductos();
        this.crearGrafico(null);
        opcionCodigo="qr";
        colores = gs.obtenerColores(loginBean.getU().getId());
        subcategorias = gs.obtenerSubcategorias(loginBean.getU().getId());
        categorias = gs.obtenerCategorias(loginBean.getU().getId());
        //Inicializar las leyendas de las etiquetas
        leyendasCodigos = new HashMap();
        for(Producto p : productos){
            leyendasCodigos.put(p.getReferencia(), p.getDescripcion());
        }
        //Inicializar la lista de informes
        listaProductoUltimoHistorico = new ArrayList<ProductoHistoricoBean>();
        for(Producto p : productos){
            listaProductoUltimoHistorico.add(new ProductoHistoricoBean(p,
                    gs.obtenerUltimoHistorico(p.getReferencia(), loginBean.getU().getId())));
        }
    }

    public List<SelectItem> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(List<SelectItem> subcategorias) {
        this.subcategorias = subcategorias;
    }

    public List<SelectItem> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<SelectItem> categorias) {
        this.categorias = categorias;
    }

    public List<SelectItem> getColores() {
        return colores;
    }

    public void setColores(List<SelectItem> colores) {
        this.colores = colores;
    }

    public List<String> getSelectedCodigos() {
        return selectedCodigos;
    }

    public void setSelectedCodigos(List<String> selectedCodigos) {
        this.selectedCodigos = selectedCodigos;
    }

    public String getOpcionCodigo() {
        return opcionCodigo;
    }

    public void setOpcionCodigo(String opcionCodigo) {
        this.opcionCodigo = opcionCodigo;
    }

    public List<Historico> getListaHistorico() {
        return listaHistorico;
    }

    public void setListaHistorico(List<Historico> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }

    public List<ProductoHistoricoBean> getListaProductoUltimoHistorico() {
        return listaProductoUltimoHistorico;
    }

    public void setListaProductoUltimoHistorico(List<ProductoHistoricoBean> listaProductoUltimoHistorico) {
        this.listaProductoUltimoHistorico = listaProductoUltimoHistorico;
    }

    public LineChartModel getGrafico() {
        return grafico;
    }

    public void setGrafico(LineChartModel grafico) {
        this.grafico = grafico;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public boolean isRenderInforme() {
        return renderInforme;
    }

    public void setRenderInforme(boolean renderInforme) {
        this.renderInforme = renderInforme;
    }

    public boolean isRenderGrafico() {
        return renderGrafico;
    }

    public void setRenderGrafico(boolean renderGrafico) {
        this.renderGrafico = renderGrafico;
    }

    public boolean isRenderQr() {
        return renderQr;
    }

    public void setRenderQr(boolean renderQr) {
        this.renderQr = renderQr;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public GestionServicios getGs() {
        return gs;
    }

    public void setGs(GestionServicios gs) {
        this.gs = gs;
    }

    public HashMap getLeyendasCodigos() {
        return leyendasCodigos;
    }

    public void setLeyendasCodigos(HashMap leyendasCodigos) {
        this.leyendasCodigos = leyendasCodigos;
    }
    
    
    public void ordenarProductos(){
        Collections.sort(productos, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Producto h1 = (Producto) o1;
                    Producto h2 = (Producto) o2;
                    return h1.getReferencia().compareTo(h2.getReferencia());
                }
            });
    }

    public String cargarOpcion(){
        if(opcion.equals("1")){
            renderInforme=true;
            renderGrafico=false;
            renderQr=false;
        }
        if(opcion.equals("2")){
            renderInforme=false;
            renderGrafico=true;
            renderQr=false;
        }
        if(opcion.equals("3")){
            renderInforme=false;
            renderGrafico=false;
            renderQr=true;
        }
        return "";
    }
        private void crearGrafico(Producto p) {
        grafico = new LineChartModel();
        boolean graficoVacio=true;
        listaHistorico = null;
        if(p!=null && p.getReferencia()!=null){
            listaHistorico = gs.obtenerHistorico(referencia,loginBean.getU().getId());
            if(!listaHistorico.isEmpty()){
                graficoVacio=false;
            }
        }
        if(!graficoVacio){    
            LineChartSeries series1 = new LineChartSeries();
            series1.setLabel("Series 1");
            
            Date fechaMin=null;
            Date fechaMax=null;
            for(Historico h:listaHistorico){
                series1.set((new SimpleDateFormat("yyyy-MM-dd").format(h.getHistoricoPK().getFecha())), h.getCantidad());
                if(fechaMin==null){
                    fechaMin=h.getHistoricoPK().getFecha();
                    fechaMax=h.getHistoricoPK().getFecha();
                }else{
                    if(fechaMin.before(h.getHistoricoPK().getFecha()))
                        fechaMin = h.getHistoricoPK().getFecha();
                    if(fechaMax.after(h.getHistoricoPK().getFecha()))
                        fechaMax = h.getHistoricoPK().getFecha();
                }
            }

            LineChartSeries series2 = new LineChartSeries();
            series2.setLabel("Stock Mínimo");
            series2.setShowMarker(false);
            series2.set((new SimpleDateFormat("yyyy-MM-dd").format(fechaMin)), p.getStockMin());
            series2.set((new SimpleDateFormat("yyyy-MM-dd").format(fechaMax)), p.getStockMin());

            grafico.setSeriesColors("FF0000, 58BA27");
            grafico.addSeries(series2);
            grafico.addSeries(series1);

            grafico.setTitle("Zoom para detalle");
            grafico.setZoom(true);
            DateAxis axis = new DateAxis("Fechas");
            axis.setTickAngle(-50);
            //axis.setMax("2014-02-01");
            //axis.setMin("2013-12-31");
            axis.setTickFormat("%b %#d, %y");
            Axis yAxis = grafico.getAxis(AxisType.Y);
            yAxis.setLabel("Stock");
            yAxis.setMin(0);

            grafico.getAxes().put(AxisType.X, axis);
        }else{

            LineChartSeries series2 = new LineChartSeries();
            series2.setLabel("Stock Mínimo");
            series2.setShowMarker(false);
            //System.err.println((new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
            series2.set((new SimpleDateFormat("yyyy-MM-dd").format(new Date())), 1);
            

            grafico.setSeriesColors("FF0000");
            grafico.addSeries(series2);
            
            grafico.setTitle("No hay datos");
            grafico.setZoom(true);
            grafico.getAxis(AxisType.Y).setLabel("Stock");
            DateAxis axis = new DateAxis("Fechas");
            axis.setTickAngle(-50);
            axis.setTickFormat("%b %#d, %y");

            grafico.getAxes().put(AxisType.X, axis);
        }
    }
    public String cargarGrafico(){
        System.out.println("Referencia "+referencia);
        Producto p =gs.obtenerProducto(referencia, loginBean.getU().getId());
        System.out.println("Objeto "+p);
        System.out.println("Referencia "+p.getReferencia());
        System.out.println("Descripcion "+p.getDescripcion());
        crearGrafico(p);
        cargarOpcion();
        
        return "";
    }
    public void seleccionarTodos(){
        boolean lleno=true;
        for(Producto p : productos){
            if(!selectedCodigos.contains(p.getReferencia())){
                selectedCodigos.add(p.getReferencia());
                lleno=false;
            }
        }
        if(lleno)
            selectedCodigos.clear();
    }
    public String recargarInforme(){
        return "";
    }
    
    public int ultimaCantidadStock(String referencia){
        int res = 0;
        for(ProductoHistoricoBean p : listaProductoUltimoHistorico){
            if(p.getProducto().getReferencia().equals(referencia)){
                res = p.getHistorico().getCantidad();
                break;
            }
        }
        return res;
    }
}
