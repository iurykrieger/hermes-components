package com.hermes.components.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import javax.swing.ImageIcon;

public class ImageFactory {
    
    public ImageIcon getIcon(String nome,int largura,int altura){
        ImageIcon i = new ImageIcon(getClass().getResource("/images/"+nome));
        i.setImage(i.getImage().getScaledInstance(largura, altura, 0));
        return i;
    }
    
    public void moveFile( String from, String to ) {

        File f = new File( from );
        File t = new File( to );

        try {
            FileInputStream fisFrom = new FileInputStream(f);
            FileOutputStream fisTo = new FileOutputStream(t);

            FileChannel fcFrom = fisFrom.getChannel();
            FileChannel fcTo = fisTo.getChannel();

            if( fcFrom.transferTo(0, fcFrom.size(), fcTo ) == 0L ) {
                fcFrom.close();
                fcTo.close();
            }
            fcFrom.close();
            fcTo.close();

        } catch (Exception e) {
            e.printStackTrace();
        }        

    }
    
}
