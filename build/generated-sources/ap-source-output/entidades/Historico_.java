package entidades;

import entidades.HistoricoPK;
import entidades.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-16T00:19:25")
@StaticMetamodel(Historico.class)
public class Historico_ { 

    public static volatile SingularAttribute<Historico, HistoricoPK> historicoPK;
    public static volatile SingularAttribute<Historico, Integer> cantidad;
    public static volatile SingularAttribute<Historico, Producto> producto;

}