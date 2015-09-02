package com.hermes.components.HMTables;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class HMMultipleClassesTableModel<T> extends AbstractTableModel {

    private List<String> colunas = new ArrayList();
    private List<T> lista;
    private List<List<T>> listas;
    private List<Class<?>> classes;
    private List<String> titulos = new ArrayList();

    public HMMultipleClassesTableModel(List<List<T>> listas, List<Class<?>> classes, String[] colunas) {
        this.listas = listas;
        lista = new ArrayList();
        if (!listas.get(0).isEmpty()) {
            classes = new ArrayList();
            for (List<T> l : this.listas) {
                classes.add(l.get(0).getClass());
                for (T t : l) {
                    lista.add(t);
                }
            }
        } else {
            this.classes = classes;
        }
        for (Class c : classes) {
            for (Field field : c.getDeclaredFields()) {
                this.colunas.add(field.getName());
            }
        }
        if (colunas != null) {
            for (String s : colunas) {
                this.titulos.add(s);
            }
        }
    }

    public List<List<T>> getListas() {
        return this.listas;
    }

    public void setListas(List<List<T>> listas) {
        this.listas = listas;
        lista.clear();
        if (!listas.get(0).isEmpty()) {
            for (List<T> l : this.listas) {
                for (T t : l) {
                    lista.add(t);
                }
            }
        }
        atualizaTable();
    }

    public int getRowCount() {  //QUANTIDADE DE LINHAS DA JTABLE
        return lista.size() / this.listas.size();
    }

    public List<String> getColumnNames() {
        return this.colunas;
    }

    public int getColumnCount() {  //QUANTIDADE DE COLUNAS DA TABELAS 
        return this.colunas.size();
    }

    public List<T> getObjectAt(Integer index) {
        List<T> l = new ArrayList();
        for (List<T> ls : listas) {
            l.add(ls.get(index));
        }
        return l;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {  // PEGAR UM VALOR DA TABELA
        Object o = lista.get(rowIndex);
        if (columnIndex > o.getClass().getDeclaredFields().length) {
            o = lista.get(rowIndex + (lista.size() / 2));
        }
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
        if (columnIndex > o.getClass().getDeclaredFields().length) {
            o = lista.get(rowIndex + (lista.size() / 2));
        }
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

    public void addObject(List<T> l) {
        int i = 0;
        for (T t : l) {
            this.lista.add(t);
            this.listas.get(i).add(t);
        }
    }

    public void alterObject(List<T> l, Integer index) {
        int i = 0;
        for (T t : l) {
            this.lista.set(index, t);
            this.listas.get(i).set(index, t);
        }
    }

    public void removerObject(List<T> l) {
        int i = 0;
        for (T t : l) {
            this.lista.remove(t);
            this.listas.get(i).remove(t);
        }
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

    public void getTitulos(Class classe) {
        for (Field field : classe.getDeclaredFields()) {
            String nome = null;
            nome = field.getName();
            titulos.add(nome.replace(nome.charAt(0), nome.toUpperCase().charAt(0)));
        }
    }
}