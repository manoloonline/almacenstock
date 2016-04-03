package entidades;

import entidades.HistoricoPK;
import entidades.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-31T04:10:44")
@StaticMetamodel(Historico.class)
public class Historico_ { 

    public static volatile SingularAttribute<Historico, HistoricoPK> historicoPK;
    public static volatile SingularAttribute<Historico, Integer> cantidad;
    public static volatile SingularAttribute<Historico, Producto> producto;

}