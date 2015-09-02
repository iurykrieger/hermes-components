package com.hermes.components.documents;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author iury
 */
public class MaxLenghtDocument extends PlainDocument {

    private int iMaxLenght;

    public MaxLenghtDocument(int iMaxLenght) {
        super();
        this.iMaxLenght = iMaxLenght;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {

        if (iMaxLenght <= 0) {
            super.insertString(offset, str, attr);
            return;
        }
        int ilen = (getLength() + str.length());
        if (ilen <= iMaxLenght) {
            super.insertString(offset, str, attr);
        }
    }
}
