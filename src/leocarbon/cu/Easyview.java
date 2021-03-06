package leocarbon.cu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import static leocarbon.cu.ColorUtility.A;
import static leocarbon.cu.ColorUtility.Monaco18;
import static leocarbon.cu.ColorUtility.RB;
import static leocarbon.cu.Options.Hexv;
import static leocarbon.cu.Options.RGBv;
import static leocarbon.cu.Options.aHexv;
import static leocarbon.cu.Options.isEasyViewTextVisible;

public class Easyview extends JPanel {
    public static JLabel evr = new JLabel("Loading...",JLabel.CENTER);
    public static JLabel evg = new JLabel("Loading...",JLabel.CENTER);
    public static JLabel evb = new JLabel("Loading...",JLabel.CENTER);
    public static JLabel evh = new JLabel("Loading...",JLabel.CENTER);
    public static JLabel evha = new JLabel("Loading...",JLabel.CENTER);
    
    public static int r, g, b, a;
    public static String hex, ahex;
    public static String rgb, rgba;
    
    public static final boolean Opaque = false;
    
    public void update(Color input) {
        r = input.getRed();
        g = input.getGreen();
        b = input.getBlue();
        a = input.getAlpha();
        
        ahex = Integer.toHexString(input.hashCode()).toUpperCase();
        if(ahex.length() < 8){
            String ahexbuffer = ahex;
            for(a = ahex.length(), ahex = ""; a < 8; ++a){
                ahex = ahex.concat("0");
            }
            ahex = ahex.concat(ahexbuffer);
        }
        hex = ahex.substring(2);
        
        rgba = Integer.toString(input.getRGB());
        
        if(isEasyViewTextVisible){
            if(RGBv){
                evr.setText("<html>"+RB.getString("r")+ r + "</html>");
                evb.setText("<html>"+RB.getString("g") + g + "</html>");
                evg.setText("<html>"+RB.getString("b") + b + "</html>");
            } else {
                evr.setText(null);
                evb.setText(null);
                evg.setText(null);

            } if(Hexv){
                evh.setText("<html>"+RB.getString("h") + hex + "</html>");
            } else {
                evh.setText(null);
            } if(aHexv){
                evha.setText("<html>"+RB.getString("ah") + ahex + "</html>");
            } else {
                evha.setText(null);
            }
        } else {
            evr.setText(null);
            evb.setText(null);
            evg.setText(null);
            evh.setText(null);
            evha.setText(null);
        }
        
        //set Background Color
        setBackground(input);
        
        //set Foreground(text) Color
        //<editor-fold defaultstate="collapsed" desc="old setForeground color algorithm">
        /*
        if(r >= 192 || g >= 192 || b >= 192 ){
            evr.setForeground(input.darker().darker().darker());
            evg.setForeground(input.darker().darker().darker());
            evb.setForeground(input.darker().darker().darker());
            evh.setForeground(input.darker().darker().darker());
            evha.setForeground(input.darker().darker().darker());
        } else if((r == 0 && g == 0 && b == 0)
                ||(r <= 64 && g == 0 && b == 0)
                ||(g <= 64 && b == 0 && r == 0)
                ||(b <= 64 && r == 0 && g == 0)
                ||(r <= 64 && g <= 64 && b == 0)
                ||(g <= 64 && b <= 64 && r == 0)
                ||(b <= 64 && r <= 64 && g == 0)
                ||(r <= 32 && b <=32 && g <= 32)){
            evr.setForeground(Color.WHITE);
            evg.setForeground(Color.WHITE);
            evb.setForeground(Color.WHITE);
            evh.setForeground(Color.WHITE);
            evha.setForeground(Color.WHITE);
        } else {
            evr.setForeground(input.brighter().brighter().brighter());
            evg.setForeground(input.brighter().brighter().brighter());
            evb.setForeground(input.brighter().brighter().brighter());
            evh.setForeground(input.brighter().brighter().brighter());
            evha.setForeground(input.brighter().brighter().brighter());
        }
        */
        //</editor-fold>
        if((r <= 136 && r >= 120)&&(g <= 136 && g >= 120)&&(b <= 136 && b >= 120)
        ||((r <= 136 && r >= 120)&&(g <= 136 && g >= 120))&&(b <= 255 && b >= 64)
        ||((r <= 136 && r >= 120)&&(b <= 136 && b >= 120))&&(g <= 192 && g >= 64)
        ||((g <= 136 && g >= 120)&&(b <= 136 && b >= 120))&&(r <= 192 && r >= 64)) setForeground(new Color(255));
        else if(!(r <= 136 && r >= 120)||!(g <= 136 && g >= 120)||!(b <= 136 && b >= 120)) setForeground(new Color(255-r,255-g,255-b));
        
        if(A != null && A.isVisible()) SwingUtilities.invokeLater(() -> { A.setColors(); });
    }
    
    public Easyview() {
        setOpaque(true);
        setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        
        evr.setFont(Monaco18);
        evr.setOpaque(Opaque);
        evr.setMinimumSize(new Dimension(239, 98));
        evr.setPreferredSize(new Dimension(239, 98));
        c.gridx = 0;
        c.gridy = 0;
        add(evr,c);
        
        evg.setFont(Monaco18);
        evg.setOpaque(Opaque);
        evg.setMinimumSize(new Dimension(239, 98));
        evg.setPreferredSize(new Dimension(239, 98));
        c.gridx = 2;
        c.gridy = 0;
        add(evg,c);
        
        evb.setFont(Monaco18);
        evb.setOpaque(Opaque);
        evb.setMinimumSize(new Dimension(239, 98));
        evb.setPreferredSize(new Dimension(239, 98));
        c.gridx = 1;
        c.gridy = 0;
        add(evb,c);
        
        evh.setFont(Monaco18);
        evh.setOpaque(Opaque);
        evh.setMinimumSize(new Dimension(239, 98));
        evh.setPreferredSize(new Dimension(239, 98));
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        add(evh,c);
        
        evha.setFont(Monaco18);
        evha.setOpaque(Opaque);
        evha.setMinimumSize(new Dimension(239, 98));
        evha.setPreferredSize(new Dimension(239, 98));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        add(evha,c);
    }
    @Override
    public void setForeground(Color Foreground) {
        evr.setForeground(Foreground);
        evg.setForeground(Foreground);
        evb.setForeground(Foreground);
        evh.setForeground(Foreground);
        evha.setForeground(Foreground);
    }
}
