package leocarbon.cu.handlers;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import leocarbon.cu.About;
import leocarbon.cu.ColorUtility;
import static leocarbon.cu.ColorUtility.A;
import static leocarbon.cu.ColorUtility.CU;
import static leocarbon.cu.ColorUtility.cc;
import leocarbon.cu.Easyview;
import leocarbon.cu.Options;
import org.apache.log4j.Logger;

public class ActionHandler implements ActionListener{
    public static ActionHandler ActionListener = new ActionHandler();
    
    public static JFrame about, preferences;
    
    public static boolean isEasyViewTextVisible = true;
    
    public static String[] EditmenuitemNames = {
                    "Copy Hex Value", "Copy AHex Value", "Copy RGB Value", "Copy RGBA Value", "Paste", "/", "Undo", "Redo", "/", 
                    "Invert RGB", "Invert Red", "Invert Green", "Invert Blue", "/", 
                    "Brighten", "Darken"
    };
            
    public static void createController(final JPanel inpanel, final JCheckBox input){
        input.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent AE){
                if(input.isSelected()){
                    inpanel.setVisible(true);
                    
                    CU.ccmtoggle.setEnabled(true);
                    CU.cmtoggle.setEnabled(true);
                    
                    CU.pack();
                }
                else{
                    inpanel.setVisible(false);
                    /* There is no use of this program if you disable both of the Color Pickers.
                     * This code is here for debug reasons.
                    if(CU.cmtoggle.equals(e.getSource())){
                        CU.ccmtoggle.setEnabled(false);
                        System.out.println("ccmtogggle Disabled");
                    } else if(CU.ccmtoggle.equals(e.getSource())){
                        CU.cmtoggle.setEnabled(false);
                        System.out.println("cmtogggle Disabled");
                    }
                     */
                    CU.pack();
                }
            }
        });
    }
    
    public static void createColorMixerChangeListener(){
        final ColorSelectionModel model = cc.getSelectionModel();
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent CE) {
                Easyview.updateEv(cc.getColor());
                if(model.getSelectedColor().getAlpha() != 255){
                    model.setSelectedColor(new Color(model.getSelectedColor().getRed(),
                            model.getSelectedColor().getGreen(),
                            model.getSelectedColor().getBlue(),
                            255));
                }
            }
        };
        model.addChangeListener(changeListener);
    }
    public static void createEasyViewTextController(final JCheckBox input){
        input.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent AE){
                if(input.isSelected()){
                    isEasyViewTextVisible = true;
                    System.out.println("Set Easy View color value visibility to true");
                    Easyview.updateEv(cc.getColor());
                }
                else{
                    isEasyViewTextVisible = false;
                    System.out.println("Set Easy View color value visibility to false");
                    Easyview.updateEv(cc.getColor());
                }
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent AE) {
        String ActionCommand = AE.getActionCommand();
        if(ActionCommand.contains(".menu.")){
            String[] description = ActionCommand.split("\\.");
            if("File".equals(description[0])){
                if(description[2].equals("Options")){
                    ColorUtility.O = new Options();
                }         
            } else if("Edit".equals(description[0])){
                if(description[2].equals("Copy Hex Value")){
                    copytoClipboard(Easyview.hex);
                } else if(description[2].equals("Copy AHex Value")){
                    copytoClipboard(Easyview.ahex);
                } else if(description[2].equals("Copy RGB Value")){
                    copytoClipboard(Easyview.rgb);
                } else if(description[2].equals("Copy RGBA Value")){
                    copytoClipboard(Easyview.rgba);
                } else if(description[2].equals("Paste Hex Value")){
                    String phex = pastefromClipboard();
                    System.out.println(phex);
                    if(!phex.startsWith("#")) phex = "#".concat(phex);
                    cc.setColor(Color.decode(phex));
                } else if(description[2].equals("Paste AHex Value")){
                } else if(description[2].equals("Paste RGB Value")){
                } else if(description[2].equals("Paste RGBA Value")){
                    String prgba = pastefromClipboard();
                    cc.setColor(new Color(Integer.parseInt(prgba)));
                } else if(description[2].equals("Undo")){

                } else if(description[2].equals("Redo")){

                } else if(description[2].equals("Invert RGB")){
                    CU.IC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "invert"));
                } else if(description[2].equals("Invert Red")){
                    CU.IC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "rinvert"));
                } else if(description[2].equals("Invert Green")){
                    CU.IC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "ginvert"));
                } else if(description[2].equals("Invert Blue")){
                    CU.IC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "binvert"));
                } else if(description[2].equals("Brighten")){
                    CU.TC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "lighten"));
                } else if(description[2].equals("Darken")){
                    CU.TC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "darken"));
                }
            } else if("Modules".equals(description[0])){
                if(description[2].equals("Average")){
                    CU.AC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "A"));
                } else if(description[2].equals("Eyedropper")){
                    if(!CU.DEyed.deyedstart.isSelected()){
                        CU.DEyed.deyedstart.setSelected(true);
                        CU.DEyed.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "DEyed"));
                    }
                } else if(description[2].equals("Random")){
                    CU.RC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "random"));
                } else if(description[2].equals("Color Fading")){
                    if(!CU.SC.scroll.isSelected()){
                        CU.SC.scroll.setSelected(true);
                        CU.SC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "hscroll"));
                    }
                }
            } else if("Window".equals(description[0])){
                if(description[2].equals("Copy Hex Value")){
                    copytoClipboard(Easyview.hex);
                } else if(description[2].equals("Copy AHex Value")){
                    copytoClipboard(Easyview.ahex);
                } else if(description[2].equals("Copy RGB Value")){
                    copytoClipboard(Easyview.rgb);
                } else if(description[2].equals("Copy RGBA Value")){
                    copytoClipboard(Easyview.rgba);
                } else if(description[2].equals("Paste Hex Value")){
                    String phex = pastefromClipboard();
                    System.out.println(phex);
                    if(!phex.startsWith("#")) phex = "#".concat(phex);
                    cc.setColor(Color.decode(phex));
                }
            } else if("Help".equals(description[0])){
                if(description[2].equals("About")){
                    A = new About();
                }
            }
        }
    }
    public static void copytoClipboard(String iString) {
        StringSelection SS = new StringSelection(iString);
        Clipboard C = Toolkit.getDefaultToolkit().getSystemClipboard();
        C.setContents(SS, SS);
    } public static String pastefromClipboard() {
        Clipboard C = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable T = C.getContents(null);
        try {
            return (String)T.getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException UFE) {
            Logger.getLogger(ActionHandler.class.getName()).error(UFE);
        } catch (IOException IOE) {
            Logger.getLogger(ActionHandler.class.getName()).error(IOE);
        }
        return null;
    }
}