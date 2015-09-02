package com.hermes.components.HMFields;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author iury
 */
public class HMMonetaryField extends JFormattedTextField {
    
    public static final String MASCARA_DECIMAL = "#,##0.000";
    private static final NumberFormat MONETARY_FORMAT = new DecimalFormat("R$ #,##0.00");
    private NumberFormat numberFormat;
    private int limit = 16;

    public HMMonetaryField(int casasDecimais) {
        this(new DecimalFormat((casasDecimais == 0 ? "#,##0" : "#,##0.") + makeZeros(casasDecimais)));
        Dimension dm = new Dimension(150, 26);
        this.setPreferredSize(dm);
    }

    public HMMonetaryField() {
        this(MONETARY_FORMAT);
        Dimension dm = new Dimension(150, 26);
        this.setPreferredSize(dm);
    }
    
    public HMMonetaryField(String mask){
        this(new DecimalFormat(mask));
        Dimension dm = new Dimension(150,26);
        this.setPreferredSize(dm);
    }

    public HMMonetaryField(NumberFormat format) {
        Dimension dm = new Dimension(150, 26);
        this.setPreferredSize(dm);
        // define o formato do número  
        numberFormat = format;
        // alinhamento horizontal para o texto  
        setHorizontalAlignment(RIGHT);
        // documento responsável pela formatação do campo  
        setDocument(new PlainDocument() {
            private static final long serialVersionUID = 1L;

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                String text = new StringBuilder(HMMonetaryField.this.getText().replaceAll("[^0-9]", "")).append(
                        str.replaceAll("[^0-9]", "")).toString();
                super.remove(0, getLength());
                if (text.isEmpty()) {
                    text = "0";
                } else {
                    text = new BigInteger(text).toString();
                }

                super.insertString(0, numberFormat.format(new BigDecimal(getLimit() > 0 && text.length() > getLimit() ? text
                        .substring(0, getLimit()) : text).divide(new BigDecimal(Math.pow(10, numberFormat
                        .getMaximumFractionDigits())))), a);
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                if (len != getLength()) {
                    insertString(0, "", null);
                }
            }
        });
        // limpa o campo se apertar DELETE  
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    setText("");
                }
            }
        });
        // formato inicial  
        setText("0");
        setCaretPosition(getText().length());
    }

    public void setValue(BigDecimal value) {
        super.setText(numberFormat.format(value));
    }

    public final BigDecimal getValue() {
        return new BigDecimal(getText().replaceAll("[^0-9]", "")).divide(new BigDecimal(Math.pow(10, numberFormat
                .getMaximumFractionDigits())));
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    private static final String makeZeros(int zeros) {
        if (zeros >= 0) {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < zeros; i++) {
                builder.append('0');
            }
            return builder.toString();
        } else {
            throw new RuntimeException("Número de casas decimais inválida (" + zeros + ")");
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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
