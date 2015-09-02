package com.hermes.components.HMTables;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class HMTableModel<T> extends AbstractTableModel {

    private Class<?> classe;
    private List<String> colunas = new ArrayList();
    private List<T> lista;
    private List<String> titulos = new ArrayList();

    public HMTableModel(List<T> lista, Class c, String[] colunas) {
        if (lista == null) {
            this.lista = new ArrayList();
        } else {
            this.lista = lista;
        }
        if (lista != null) {
            this.classe = this.lista.get(0).getClass();
            for (Field field : classe.getDeclaredFields()) {
                this.colunas.add(field.getName());
            }
        } else {
            this.classe = c;
            for (Field field : classe.getDeclaredFields()) {
                this.colunas.add(field.getName());
            }
        }
        if (colunas != null) {
            for (String s : colunas) {
                this.titulos.add(s);
            }
        }
    }

    public List<T> getLista() {
        return this.lista;
    }

    public int getRowCount() {  //QUANTIDADE DE LINHAS DA JTABLE
        return lista.size();
    }

    public int getColumnCount() {  //QUANTIDADE DE COLUNAS DA TABELAS 
        if (classe != null) {
            return classe.getDeclaredFields().length;
        } else {
            return 0;
        }
    }

    public T getObjectAt(Integer index) {
        return this.lista.get(index);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {  // PEGAR UM VALOR DA TABELA
        Object o = lista.get(rowIndex);
        try {
            Field field = o.getClass().getDeclaredField((String) colunas.get(columnIndex));
            field.setAccessible(true);
            return field.get(o);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { // ATRIBUIR UM VALOR PARA 1 ITEM DA TABELA
        Object o = lista.get(rowIndex);
        try {
            Field field = o.getClass().getDeclaredField((String) colunas.get(columnIndex));
            field.setAccessible(true);
            field.set(o, aValue.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getColumnName(int columnIndex) { // PEGA O NOME DA COLUNA DA TABELA
        if (!titulos.isEmpty()) {
            return titulos.get(columnIndex);
        } else {
            return colunas.get(columnIndex);
        }
    }

    public void addObject(T ob) {
        this.lista.add(ob);
    }

    public void alterObject(T ob, Integer index) {
        this.lista.set(index, ob);
    }

    public void removerObject(int index) {
        this.lista.remove(index);
    }

    public void atualizaTable() {
        fireTableDataChanged();
    }

    public List<Integer> getColumnIndexByNome(String coluna) {
        List<Integer> indexes = new ArrayList();
        for (int i = 0; i < colunas.size(); i++) {
            if (coluna.equals(colunas.get(i).toString())) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    public void esvaziaTable() {
        this.lista = new ArrayList();
    }
}