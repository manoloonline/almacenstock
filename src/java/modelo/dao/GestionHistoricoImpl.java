/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import entidades.Historico;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author -
 */
@Component
public class GestionHistoricoImpl implements GestionHistorico,Serializable {
    @PersistenceContext(unitName="almacenstockPU")
    EntityManager em;

    @Override
    public Historico obtenerUltimoHistorico(String referencia, int usuario) {
        Query q = em.createNamedQuery("Historico.findLastDateByRef");
        q.setParameter(1, referencia);
        q.setParameter(2, usuario);
        q.setMaxResults(1);
        try{
            return (Historico) q.getSingleResult();
        }catch(Exception e){
            return new Historico(null);
        }
    }

    @Override
    @Transactional
    public boolean guardarHistorico(Historico h) {
        boolean resultado;
        if(em.find(Historico.class, h.getHistoricoPK())==null)
            resultado=true;
        else
            resultado=false;
        em.merge(h);
        return resultado;
    }
    
    @Override
    public List<Historico> obtenerHistorico(String referencia, int usuario) {
        Query q = em.createNamedQuery("Historico.findLastDateByRef");
        q.setParameter(1, referencia);
        q.setParameter(2, usuario);
        return q.getResultList();
    }

    @Override
    @Transactional
    public boolean eliminarHistorico(Historico h) {
        boolean resultado;
        if(em.find(Historico.class, h.getHistoricoPK())==null)
            resultado=false;
        else
            resultado=true;
        h=em.merge(h);
        em.remove(h);
        return resultado;
    }
}
