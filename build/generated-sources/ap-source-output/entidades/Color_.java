package entidades;

import entidades.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-16T00:19:25")
@StaticMetamodel(Color.class)
public class Color_ { 

    public static volatile SingularAttribute<Color, Integer> id;
    public static volatile SingularAttribute<Color, String> nombre;
    public static volatile SingularAttribute<Color, Integer> usuario;
    public static volatile SingularAttribute<Color, String> tipo;
    public static volatile ListAttribute<Color, Producto> productos;

}