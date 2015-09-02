package com.hermes.components.HMFields;

import com.hermes.components.utils.ImageFactory;
import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.RoundedBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

/**
 *
 * @author iury
 */
public class HMFormattedTextField extends javax.swing.JFormattedTextField {

    private int emptyCount = 0;
    private boolean obrigatory;
    private boolean valid;
    private boolean keyTyped;
    private String mask;
    public static final String TELEFONE = "(##) ####-####";
    public static final String DATA = "##/##/####";
    public static final String RG = "#.###.###";
    public static final String CPF = "###.###.###-##";
    public static final String CEP = "#####-###";
    public static final String CNPJ = "##.###.###/####-##";
    public static final String HORA = "##:##";

    public HMFormattedTextField() {
        initComponents();
        initComponent();
    }

    public void initComponent() {
        Dimension dm = new Dimension(150, 26);
        this.setPreferredSize(dm);
        this.valid = false;
        this.obrigatory = false;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN));
    }

    public boolean isObrigatory() {
        return obrigatory;
    }

    public void setObrigatory(boolean obrigatory) {
        this.obrigatory = obrigatory;
    }

    public void setMask(String mask) {
        this.mask = mask;
        MaskFormatter m;
        try {
            m = new MaskFormatter(mask);
            m.setPlaceholderCharacter('_');
            this.setFormatterFactory(new DefaultFormatterFactory(m));
            if (mask.equals(DATA)) {
                initDataField();
            }
        } catch (ParseException ex) {
            Logger.getLogger(HMFormattedTextField.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initDataField() {
        setStaticIcon("calendar.png", 18, 18);
        //getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void setStaticIcon(String nome, int largura, int altura) {
        this.setLayout(new BorderLayout());
        lb_img.setIcon(new ImageFactory().getIcon(nome, largura, altura));
        this.add(lb_img, new BorderLayout().EAST);
    }

    public String getValor() {
        String s = this.getText();
        s = s.replace(".", "");
        s = s.replace("/", "");
        s = s.replace("-", "");
        s = s.replace(":", "");
        return s;
    }

    public void onKeyReleased() {
        if (obrigatory) {
            this.keyTyped = true;
            if (this.getText().contains("_")) {
                this.valid = false;
            } else {
                this.valid = true;
            }
            if (isValid()) {
                this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN));
            }
        } else {
            valid = true;
        }
    }

    public void onFocusLost() {
        if (obrigatory) {
            if (this.getText().contains("_")) {
                setValue(null);
                RoundedBalloonStyle balao = new RoundedBalloonStyle(2, 2, new Color(224, 237, 255), new Color(224, 237, 255));
                this.valid = false;
                if (emptyCount < 2) {
                    TimingUtils.showTimedBalloon(new BalloonTip(this, "O campo está em branco!", balao, false), 1000);
                }
                emptyCount++;
            } else {
                this.valid = true;
            }
            if (isValid()) {
                this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN));
            } else {
                this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
            }
        } else {
            valid = true;
        }
    }

    public void onFocusGained() {
        if (isValid()) {
            setText(getText());
            setCaretPosition(getText().length());
        }
    }

    public void openCalendar() {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final JCalendar jc = new JCalendar();
        final JPopupMenu popup = new JPopupMenu();
        String tmp[] = getText().split("/");
        String data = tmp[1] + "/" + tmp[0] + "/" + tmp[2];
        if (isValid()) {
            jc.setDate(new Date(data));
        }
        jc.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                setText(sdf.format(jc.getDate()));
                setValid(true);
                popup.setVisible(false);
            }
        });
        jc.setVisible(true);
        popup.setLayout(new BorderLayout());
        popup.add(jc);
        popup.setPopupSize(220, 160);
        popup.show(this, 0, this.getHeight());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_img = new javax.swing.JLabel();

        lb_img.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb_imgMouseEntered(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_imgMouseClicked(evt);
            }
        });

        setVerifyInputWhenFocusTarget(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        onFocusLost();
    }//GEN-LAST:event_formFocusLost

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        onKeyReleased();
    }//GEN-LAST:event_formKeyReleased

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        onFocusGained();
    }//GEN-LAST:event_formFocusGained

    private void lb_imgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_imgMouseClicked
        openCalendar();
    }//GEN-LAST:event_lb_imgMouseClicked

    private void lb_imgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_imgMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lb_imgMouseEntered
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lb_img;
    // End of variables declaration//GEN-END:variables
}
