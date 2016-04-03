/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import entidades.Historico;
import java.util.List;

/**
 *
 * @author tarde
 */
public interface GestionHistorico {

    Historico obtenerUltimoHistorico(String referencia, int usuario);
    
    boolean guardarHistorico(Historico h);
    
    public List<Historico> obtenerHistorico(String referencia, int usuario);
    
    public boolean eliminarHistorico(Historico h);
    
}
