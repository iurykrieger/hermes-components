package com.hermes.components.HMTables;

import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class HMTable<T> extends javax.swing.JTable {

    private HMMultipleClassesTableModel hmctm;
    private HMTableModel htm;

    public HMTable() {
        initComponents();
        centralizarColunas();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
    }

    public HMTable(HMMultipleClassesTableModel hmctm) {
        this.hmctm = hmctm;
        setModel(this.hmctm);
        centralizarColunas();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    }

    public HMTable(HMTableModel htm) {
        this.htm = htm;
        setModel(this.htm);
        centralizarColunas();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    }

    public HMTable(List<List<T>> listas, List<Class> classes, String[] colunas) {
        this.hmctm = new HMMultipleClassesTableModel(listas, classes, colunas);
        centralizarColunas();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    }

    public HMTable(List<T> lista, Class classe, String[] colunas) {
        this.htm = new HMTableModel(lista, classe, colunas);
        centralizarColunas();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
    }

    public void ocultarColunas(String[] colunas) {
        List<Integer> columnIndex;
        for (String coluna : colunas) {
            if (this.hmctm != null) {
                columnIndex = hmctm.getColumnIndexByNome(coluna);
            } else {
                columnIndex = htm.getColumnIndexByNome(coluna);
            }
            for (int i : columnIndex) {
                this.getColumnModel().getColumn(i).setMaxWidth(-1);
                this.getColumnModel().getColumn(i).setMinWidth(-1);
                this.getColumnModel().getColumn(i).setPreferredWidth(-1);
            }
        }
    }
    
    public void alterObject(T object, int index){
        this.htm.alterObject(object,index);
        htm.atualizaTable();
    }
    
    public void alterObjectMC(List<T> lista, int index){
        this.hmctm.alterObject(lista, index);
        hmctm.atualizaTable();
    }

    public void setModelo(HMTableModel htm) {
        this.htm = htm;
        this.setModel(this.htm);
        htm.atualizaTable();
    }

    public void setModelo(HMMultipleClassesTableModel hmctm) {
        this.hmctm = hmctm;
        this.setModel(this.hmctm);
        hmctm.atualizaTable();
    }

    public HMMultipleClassesTableModel getModeloMC() {
        return this.hmctm;
    }

    public HMTableModel getModelo() {
        return this.htm;
    }

    public void centralizarColunas() {
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < this.getColumnCount(); i++) {
            this.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }
    }

    public List<T> getSelectedItemMC() {
        List<T> ob;
        ob = (List<T>) hmctm.getObjectAt(this.getSelectedRow());
        return ob;
    }

    public List<T> getLista() {
        return this.htm.getLista();
    }

    public void removeSelectedItem() {
        htm.removerObject(getSelectedRow());
        this.htm.atualizaTable();
    }

    public Object getSelectedItem() {
        Object ob;
        ob = htm.getObjectAt(this.getSelectedRow());
        return ob;
    }

    public void addObject(T ob) {
        this.htm.addObject(ob);
        this.htm.atualizaTable();
        updateUI();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setForeground(new java.awt.Color(255, 255, 255));
        setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
