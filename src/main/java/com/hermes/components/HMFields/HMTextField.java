package com.hermes.components.HMFields;

import com.hermes.components.documents.MaxLenghtDocument;
import com.hermes.components.utils.ImageFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.RoundedBalloonStyle;
import net.java.balloontip.utils.TimingUtils;

public class HMTextField extends javax.swing.JTextField {

    private int emptyCount = 0;
    private int specialCount = 0;
    private boolean obrigatory;
    private boolean special;
    private boolean validObrigatory;
    private boolean validSpecial;
    private boolean keyTyped;

    public HMTextField() {
        initComponents();
        initComponent();
    }

    public void initComponent() {
        Dimension dm = new Dimension(150, 26);
        this.setPreferredSize(dm);
        this.validObrigatory = false;
        this.validSpecial = false;
    }
    
    public void setValid(boolean valid){
        this.validObrigatory = true;
        this.validSpecial = true;
        this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN));
    }

    public boolean isValid() {
        if (obrigatory) {
            if (validObrigatory && validSpecial) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean isObrigatory() {
        return obrigatory;
    }

    public void setObrigatory(boolean obrigatory) {
        this.obrigatory = obrigatory;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public void setCharacterLimit(int limit) {
        setDocument(new MaxLenghtDocument(limit));
    }

    public void setStaticIcon(String nome, int largura, int altura) {
        this.setLayout(new BorderLayout());
        lb_img.setIcon(new ImageFactory().getIcon(nome, largura, altura));
        this.add(lb_img, new BorderLayout().EAST);
        this.repaint();
        this.doLayout();
    }

    public void onKeyTyped() {
        this.keyTyped = true;
        if (special) {
            if (this.getText().contains(" ")) {
                RoundedBalloonStyle balao = new RoundedBalloonStyle(2, 2, new Color(224, 237, 255), new Color(224, 237, 255));
                if (specialCount < 2) {
                    TimingUtils.showTimedBalloon(new BalloonTip(this, "O campo contém caracteres especiais", balao, false), 1000);
                }
                this.validSpecial = false;
                specialCount++;
            } else {
                this.validSpecial = true;
            }
        } else {
            validSpecial = true;
        }
        if (obrigatory) {
            if (!this.getText().isEmpty()) {
                this.validObrigatory = true;
            } else {
                this.validObrigatory = false;
            }
        } else {
            validObrigatory = true;
        }
        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            RoundedBalloonStyle balao = new RoundedBalloonStyle(2, 2, new Color(224, 237, 255), new Color(224, 237, 255));
            TimingUtils.showTimedBalloon(new BalloonTip(this, "A tecla Caps Lock está ligada", balao, false), 1000);
        }
        if (isValid()) {
            this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN));
        } else {
            this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
        }
    }

    public void onFocusLost() {
        if (obrigatory) {
            if (this.getText().isEmpty()) {
                RoundedBalloonStyle balao = new RoundedBalloonStyle(2, 2, new Color(224, 237, 255), new Color(224, 237, 255));
                this.validObrigatory = false;
                if (emptyCount < 2) {
                    TimingUtils.showTimedBalloon(new BalloonTip(this, "O campo está em branco!", balao, false), 1000);
                }
                emptyCount++;
            } else {
                this.validObrigatory = true;
            }
            if (isValid()) {
                this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GREEN));
            } else {
                this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
            }
        }
    }

    public void onFocusGained() {
        if (isValid()) {
            setText(getText());
            setCaretPosition(getText().length());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_img = new javax.swing.JLabel();

        lb_img.setToolTipText("");

        setAlignmentX(1.0F);
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
        onKeyTyped();
    }//GEN-LAST:event_formKeyReleased

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        onFocusGained();
    }//GEN-LAST:event_formFocusGained
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lb_img;
    // End of variables declaration//GEN-END:variables
}
