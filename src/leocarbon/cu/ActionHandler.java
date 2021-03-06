package leocarbon.cu;

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
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import static leocarbon.cu.ColorUtility.*;
import org.apache.log4j.Logger;

public class ActionHandler implements ActionListener {
    public static ActionHandler ActionListener = new ActionHandler();
    
    public static void createController(final JPanel inpanel, final JCheckBox input){
        input.addActionListener((ActionEvent AE) -> {
            if(input.isSelected()){
                inpanel.setVisible(true);
                
                ccmtoggle.setEnabled(true);
                cmtoggle.setEnabled(true);
                cc.setPreviewPanel(new JPanel());
                CU.pack();
            }
            else {
                inpanel.setVisible(false);
                if(cmtoggle.equals(AE.getSource())) ccmtoggle.setEnabled(false);
                else if(ccmtoggle.equals(AE.getSource())) cmtoggle.setEnabled(false);
                else if(evtoggle.equals(AE.getSource())){
                    cc.setPreviewPanel(null);
                }
                CU.pack();
            }
        });
    }
    
    public static void createColorMixerChangeListener(){
        final ColorSelectionModel model = cc.getSelectionModel();
        ChangeListener changeListener = (ChangeEvent CE) -> {
            if(Easyview.Opaque && model.getSelectedColor().getAlpha() != 255) Ev.update(new Color(model.getSelectedColor().getRed(),model.getSelectedColor().getGreen(),model.getSelectedColor().getBlue(),255));
            else Ev.update(new Color(model.getSelectedColor().getRed(),model.getSelectedColor().getGreen(),model.getSelectedColor().getBlue(),model.getSelectedColor().getAlpha()));
        };
        model.addChangeListener(changeListener);
    }
    
    boolean cmv = true, ccmv = true, evv = true;
    @Override
    public void actionPerformed(ActionEvent AE) {
        String ActionCommand = AE.getActionCommand();
        if(ActionCommand.contains(".menu.")){
            String[] description = ActionCommand.split("\\.");
            if(RB.getString("file").equals(description[0])){
                if(description[2].equals(RB.getString("file.Options"))){
                    ColorUtility.O = new Options();
                }         
            } else if(RB.getString("edit").equals(description[0])){
                if(description[2].equals(RB.getString("edit.cHex"))){
                    copytoClipboard(Easyview.hex);
                    if(dologging) Logger.getLogger(ActionHandler.class).info("Copied Hex Color "+Easyview.hex);
                } else if(description[2].equals(RB.getString("edit.cAHex"))){
                    copytoClipboard("0x"+Easyview.ahex);
                    if(dologging) Logger.getLogger(ActionHandler.class).info("Copied AHex Color "+"0x"+Easyview.ahex);
                } else if(description[2].equals(RB.getString("edit.cRGB"))){
                    copytoClipboard(Easyview.rgb);
                    if(dologging) Logger.getLogger(ActionHandler.class).info("Copied RGB Color "+Easyview.rgb);
                } else if(description[2].equals(RB.getString("edit.cRGBA"))){
                    copytoClipboard(Easyview.rgba);
                    if(dologging) Logger.getLogger(ActionHandler.class).info("Copied RGBA Color "+Easyview.rgba);
                } else if(description[2].equals(RB.getString("edit.pHex"))){
                    String phex = pastefromClipboard();
                    if(!phex.startsWith("#")) phex = "#".concat(phex);
                    cc.setColor(Color.decode(phex));
                    if(dologging) Logger.getLogger(ActionHandler.class).info("Pasted Hex Color "+phex);
                } else if(description[2].equals(RB.getString("edit.pAHex"))){
                    String pahex = pastefromClipboard();
                    if(pahex.startsWith("0x")) pahex = pahex.substring(2);
                    String apahex = pahex.substring(0,2); //Do something with alpha data
                    pahex = pahex.substring(2);
                    Color C = new Color(Integer.parseInt(pahex,16));
                    cc.setColor(C);
                    if(dologging) Logger.getLogger(ActionHandler.class).info("Pasted AHex Color "+pahex);
                } else if(description[2].equals(RB.getString("edit.pRGB"))){
                    if(dologging) Logger.getLogger(ActionHandler.class).info("Pasted RGB Color ");
                } else if(description[2].equals(RB.getString("edit.pRGBA"))){
                    String prgba = pastefromClipboard();
                    cc.setColor(new Color(Integer.parseInt(prgba)));
                    if(dologging) Logger.getLogger(ActionHandler.class).info("Pasted RGBA Color "+prgba);
                } else if(description[2].equals(RB.getString("edit.undo"))){
                } else if(description[2].equals(RB.getString("edit.redo"))){
                } else if(description[2].equals(RB.getString("IC.invert"))){
                    IC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "invert"));
                } else if(description[2].equals(RB.getString("IC.rinvert"))){
                    IC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "rinvert"));
                } else if(description[2].equals(RB.getString("IC.ginvert"))){
                    IC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "ginvert"));
                } else if(description[2].equals(RB.getString("IC.binvert"))){
                    IC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "binvert"));
                } else if(description[2].equals(RB.getString("edit.b"))){
                    TC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "lighten"));
                } else if(description[2].equals(RB.getString("edit.d"))){
                    TC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "darken"));
                }
            } else if(RB.getString("modules").equals(description[0])){
                if(description[2].equals(AC.getDisplayName())){
                    AC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "A"));
                } else if(description[2].equals(DEyed.getDisplayName())){
                    DEyed.deyedstart.setSelected(!DEyed.deyedstart.isSelected());
                    DEyed.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "DEyed"));
                } else if(description[2].equals(RC.getDisplayName())){
                    RC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "random"));
                } else if(description[2].equals(SC.getDisplayName())){
                    SC.scroll.setSelected(!SC.scroll.isSelected());
                    SC.actionPerformed(new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "hscroll"));
                }
            } else if(RB.getString("help").equals(description[0])){
                if(description[2].equals(RB.getString("help.a"))){
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
        } catch (UnsupportedFlavorException | IOException E) {
            if(dologging) Logger.getLogger(ActionHandler.class.getName()).error(E);
        }
        return null;
    }
}