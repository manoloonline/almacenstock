/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entidades.Historico;
import entidades.Producto;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.servlet.ServletContext;
import modelo.servicio.GestionServicios;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;


/**
 *
 * @author tarde
 */
@ManagedBean
@SessionScoped
public class InventarioBean implements Serializable{

    @ManagedProperty("#{servicio}")
    private GestionServicios gs;
    
    @ManagedProperty(value = "#{loginBean}" )
    LoginBean loginBean;
    
    private String referencia;
    private Date fecha;
    private int stock;
    private String descripcion;
    private Integer ultimaCantidad;
    private Date ultimaFecha;
    private Historico historico;
    private Producto producto;
    private List<String> todos;
    private String opcion;
    private String codigoPanel;
    private LineChartModel grafico;
    private boolean renderGraph;
    private boolean renderImage;
    private List<Historico> listaHistorico;
    //private List<Historico> listaHistoricos;
    

    public InventarioBean() {
    }
    
        @PostConstruct
    public void init() {
        fecha = new Date();
        todos = gs.obtenerStringProductos(loginBean.getU().getId());
        opcion ="1";
        renderGraph=false;
        renderImage=true;
        this.crearGrafico(null);
        this.cargarOpcion();
    }

    public boolean isRenderGraph() {
        return renderGraph;
    }

    public void setRenderGraph(boolean renderGraph) {
        this.renderGraph = renderGraph;
    }

    public boolean isRenderImage() {
        return renderImage;
    }

    public void setRenderImage(boolean renderImage) {
        this.renderImage = renderImage;
    }
    
    public LineChartModel getGrafico() {
        return grafico;
    }

    public void setGrafico(LineChartModel grafico) {
        this.grafico = grafico;
    }

/*    public List<Historico> getListaHistoricos() {
        return listaHistoricos;
    }

    public void setListaHistoricos(List<Historico> listaHistoricos) {
        this.listaHistoricos = listaHistoricos;
    }
*/
    public String getCodigoPanel() {
        return codigoPanel;
    }

    public void setCodigoPanel(String codigoPanel) {
        this.codigoPanel = codigoPanel;
    }

    public List<String> getTodos() {
        return todos;
    }

    public void setTodos(List<String> todos) {
        this.todos = todos;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getUltimaCantidad() {
        return ultimaCantidad;
    }

    public void setUltimaCantidad(Integer ultimacantidad) {
        this.ultimaCantidad = ultimacantidad;
    }

    public Date getUltimaFecha() {
        return ultimaFecha;
    }

    public void setUltimaFecha(Date ultimafecha) {
        this.ultimaFecha = ultimafecha;
    }
    
    public GestionServicios getGs() {
        return gs;
    }

    public void setGs(GestionServicios gs) {
        this.gs = gs;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public StreamedContent getImage(){
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            Producto p = gs.obtenerProducto(referencia, loginBean.getU().getId());
            if(p.getFoto()!=null){
                return new DefaultStreamedContent(new ByteArrayInputStream(p.getFoto()));
            }else{
                String relativeWebPath = "/images/sin_imagen.jpg";
                String absoluteDiskPath = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath(relativeWebPath);
                File file = new File(absoluteDiskPath);
                try{
                    return new DefaultStreamedContent(new FileInputStream(file));
                }catch(FileNotFoundException e){
                    return null;
                }
            }
        }
    }

    public List<String> completarTexto(String query) {
        
        List<String> resultado= new ArrayList();
        for(String p:todos){
            if(p.contains(query)){
                resultado.add(p);
            }
        }
        
        return resultado;
    }
    
    public String recuperar(){
        producto = gs.obtenerProducto(referencia,loginBean.getU().getId());
        descripcion = producto.getDescripcion();
        historico = gs.obtenerUltimoHistorico(referencia,loginBean.getU().getId());
        if(historico.getHistoricoPK()!=null){
            ultimaCantidad = historico.getCantidad();
            stock = ultimaCantidad;
            ultimaFecha = historico.getHistoricoPK().getFecha();
        }else{
            ultimaCantidad = null;
            ultimaFecha = null;
        }
        //Creación Grafico
        //if(producto != null){
            //
            //grafico = new GraficoBean(producto,listaHistoricos);
        //}
        this.crearGrafico(producto);
        
        return "";
    }
    
    public String guardar(ActionEvent actionEvent){
        Historico h = new Historico(referencia, fecha, loginBean.getU().getId(),stock);
        int stockaux=stock;
        Date fechaaux=new Date(fecha.getTime());
        if(gs.guardarHistorico(h))
            mensajeRegistroAnadido(actionEvent);
        else
            mensajeRegistroActualizado(actionEvent);
        this.recuperar();
        stock=stockaux;
        fecha=fechaaux;
        return "";
    }
    
    public String eliminar(ActionEvent actionEvent){
        Historico h = new Historico(referencia, fecha, loginBean.getU().getId(),stock);
        if(gs.eliminarHistorico(h))
            mensajeRegistroEliminado(actionEvent);
        else
            mensajeRegistroNoEliminado(actionEvent);
        this.recuperar();
        return "";
    }
    
    public void mensajeRegistroAnadido(ActionEvent actionEvent) {
        addMessage("Registro añadido!");
    }
    
    public void mensajeRegistroActualizado(ActionEvent actionEvent) {
        addWarning("Registro actualizado!");
    }
    
    public void mensajeRegistroEliminado(ActionEvent actionEvent) {
        addError("Registro eliminado!");
    }
    
    public void mensajeRegistroNoEliminado(ActionEvent actionEvent) {
        addFatal("No se ha podido Eliminar registro!");
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void addWarning(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void addError(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void addFatal(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void itemSelect(ItemSelectEvent event) {
        if(event.getSeriesIndex()==1){
            Collections.sort(listaHistorico, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Historico h1 = (Historico) o1;
                    Historico h2 = (Historico) o2;
                    if(h1.getHistoricoPK().getFecha().after(h2.getHistoricoPK().getFecha())){
                        return 1;
                    }else{
                        if(h1.getHistoricoPK().getFecha().before(h2.getHistoricoPK().getFecha())){
                            return -1;
                        }else
                            return 0;
                    }
                }
            });
            Historico h = listaHistorico.get(event.getItemIndex());
            fecha=h.getHistoricoPK().getFecha();
            stock=h.getCantidad();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Seleccionado! "+
                            "Fecha: " + (new SimpleDateFormat("dd-MM-yyyy").format(fecha)) + ", Stock:" + stock,null);

            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public String cargarOpcion(){
        if(opcion.equals("1")){
            renderImage=true;
            renderGraph=false;
            codigoPanel="";
        }
        if(opcion.equals("2")){
            renderImage=false;
            renderGraph=false;
            codigoPanel=
"            <DIV id=\"main\" style=\"display: inline\">\n" +
"            <DIV id=\"mainbody\">\n" +
"            <TABLE width=\"100%\" class=\"tsel\" border=\"0\">\n" +
"              <TBODY>\n" +
"              <TR>\n" +
"                <TD width=\"50%\" align=\"center\" valign=\"top\">\n" +
"                  <TABLE class=\"tsel\" border=\"0\">\n" +
"                    <TBODY>\n" +
"                    <TR>\n" +
"                      <TD><IMG style =\"display: none\" align=\"left\" class=\"selector\" id=\"webcamimg\" onclick=\"setwebcam()\" \n" +
"                        src=\"images/qr/vid.png\"/></TD>\n" +
"                      <TD><IMG style =\"display: none\" align=\"right\" class=\"selector\" id=\"qrimg\" onclick=\"setimg()\" \n" +
"                        src=\"images/qr/cam.png\"/></TD></TR>\n" +
"                    <TR>\n" +
"                      <TD align=\"center\" colspan=\"2\">\n" +
"                        <DIV id=\"outdiv\"></DIV></TD></TR></TBODY></TABLE></TD></TR>\n" +
"              <TR>\n" +
"                </TR>\n" +
"              <TR>\n" +
"                <TD align=\"center\" colspan=\"3\">\n" +
"             </TD></TR></TBODY></TABLE></DIV>&nbsp;</DIV>\n" +
"              <CANVAS width=\"800\" height=\"600\" \n" +
"                id=\"qr-canvas\"></CANVAS> \n" +
"                <SCRIPT type=\"text/javascript\">load();</SCRIPT>";
        }
        if(opcion.equals("3")){
            renderImage=false;
            renderGraph=true;
            codigoPanel="";
        }
        /*try{
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() +"/faces/inventario.xhtml?i=1");
        }catch(IOException e){
            return "inventario";
        }*/
        return "inventario";
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
}
