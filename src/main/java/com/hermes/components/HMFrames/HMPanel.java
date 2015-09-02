/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hermes.components.HMFrames;

import com.hermes.components.HMFields.HMDecimalField;
import com.hermes.components.HMFields.HMFormattedTextField;
import com.hermes.components.HMFields.HMMonetaryField;
import com.hermes.components.HMFields.HMTextField;
import com.hermes.components.HMSelections.HMComboBox;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author iury
 */
public class HMPanel extends JPanel {

    private Class classe;

    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }
    
    public void salvar(){
        
    }
    
    public void getReceivedObject(Object object){
        
    }

    public void resetaPanel(JPanel jp) {
        Component[] componentes = jp.getComponents();
        String classe;
        for (int contador = 0; contador < componentes.length; contador++) {
            classe = componentes[contador].getClass().getName();
            try {
                if (classe.contains("JPanel")) {
                    resetaPanel((JPanel) componentes[contador]);
                } else {
                    if (classe.contains("HMTextField")) {
                        ((HMTextField) componentes[contador]).setText("");
                    }
                    if (classe.contains("HMFormattedTextField")) {
                        ((HMFormattedTextField) componentes[contador]).setText("");
                    }
                    if (classe.contains("HMComboBox")) {
                        ((HMComboBox) componentes[contador]).setSelectedIndex(0);
                    }
                    if (classe.contains("HMMonetaryField")){
                        ((HMMonetaryField) componentes[contador]).setText("");
                    }
                    if (classe.contains("HMDecimalField")){
                        ((HMDecimalField) componentes[contador]).setText("");
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Não foi possível resetar os campos!");
            }
        }
    }

    public boolean verificaCampos(JPanel jp) {
        Component[] componentes = jp.getComponents();
        String classe;
        boolean retorno = false;
        for (int contador = 0; contador < componentes.length; contador++) {
            classe = componentes[contador].getClass().getName();
            try {
                if (classe.contains("JPanel")) {
                    verificaCampos((JPanel) componentes[contador]);
                } else {
                    if (classe.contains("HMTextField")) {
                        HMTextField tf = ((HMTextField) componentes[contador]);
                        if (!tf.isValid() && tf.isObrigatory()) {
                            retorno = false;
                        } else {
                            retorno = true;
                        }
                    } else if (classe.contains("HMFormattedTextField")) {
                        HMFormattedTextField tf = ((HMFormattedTextField) componentes[contador]);
                        if (!tf.isValid() && tf.isObrigatory()) {
                            retorno = false;
                        } else {
                            retorno = true;
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Não foi possível verificar os campos!");
            }
        }
        return retorno;
    }

    public HMPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
