package entidades;

import entidades.Categoria;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-31T04:10:44")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile SingularAttribute<Categoria, Integer> id;
    public static volatile SingularAttribute<Categoria, String> nombre;
    public static volatile SingularAttribute<Categoria, Categoria> categoriaPadreRelacionada;
    public static volatile SingularAttribute<Categoria, Integer> usuario;

}