package entidades;

import entidades.Categoria;
import entidades.Color;
import entidades.Historico;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-31T04:10:44")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> id;
    public static volatile CollectionAttribute<Producto, Historico> historicoCollection;
    public static volatile SingularAttribute<Producto, Integer> stockMin;
    public static volatile SingularAttribute<Producto, Categoria> subcategoriaRelacionada;
    public static volatile SingularAttribute<Producto, Double> precio;
    public static volatile SingularAttribute<Producto, Integer> usuario;
    public static volatile SingularAttribute<Producto, String> descripcion;
    public static volatile SingularAttribute<Producto, Integer> piezasCaja;
    public static volatile SingularAttribute<Producto, byte[]> foto;
    public static volatile SingularAttribute<Producto, Color> colorRelacionado;
    public static volatile SingularAttribute<Producto, String> referencia;

}