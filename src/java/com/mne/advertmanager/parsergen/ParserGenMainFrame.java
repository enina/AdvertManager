/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.parsergen;

import com.mne.advertmanager.parsergen.model.DataSpec;
import com.mne.advertmanager.parsergen.model.Project;
import com.mne.advertmanager.parsergen.model.SelectableItem;
import com.mne.advertmanager.util.JSoupTransport;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jsoup.Connection;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class ParserGenMainFrame extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(ParserGenMainFrame.class.getName());
    private Project project = null;
    private String curDataSpec = "";

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ParserGenMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ParserGenMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ParserGenMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ParserGenMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ParserGenMainFrame().setVisible(true);
            }
        });
    }
    /**
     * Creates new form ParserGenMainFrame
     */
    public ParserGenMainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ParserGenMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ParserGenMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ParserGenMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ParserGenMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        componentPanel.setVisible(false);
        radioGroup.add(radioGet);
        radioGroup.add(radioPost);

        txtSubItemSelector.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel(e);
            }

            private void updateLabel(DocumentEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        String subItem = (String) cmbSubItem.getSelectedItem();
                        DataSpec dataSpec = project.getDataSpec(curDataSpec);
                        SelectableItem subItemSpec = dataSpec.getSubItem(subItem);
                        subItemSpec.setSelector(txtSubItemSelector.getText());
                    }
                });
            }
        });


    }

    private void displayTree(String url, String method) {
        
        Connection con = JSoupTransport.login(project);
        org.jsoup.nodes.Document dataRoot = JSoupTransport.retrieveDocument(con, url, method);
        JSoupTransport.logout(con, project);



        DefaultMutableTreeNode controlRoot = new DefaultMutableTreeNode("/");
        controlRoot.setAllowsChildren(true);

        buildTree(dataRoot.select("html").first(), controlRoot);

        DefaultTreeModel model = new DefaultTreeModel(controlRoot.getFirstChild());
        treeDoc.setModel(model);

        model.reload(controlRoot.getFirstChild());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioGroup = new javax.swing.ButtonGroup();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeDoc = new javax.swing.JTree();
        basePanel = new javax.swing.JPanel();
        componentPanel = new javax.swing.JPanel();
        txtSubItemSelector = new javax.swing.JTextField();
        lblSubItem = new javax.swing.JLabel();
        cmbSubItem = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        txtMainSelector = new javax.swing.JTextField();
        javax.swing.JLabel lblMainDataSelector = new javax.swing.JLabel();
        txtUrl = new javax.swing.JTextField();
        lblURL = new javax.swing.JLabel();
        lblItem = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        radioGet = new javax.swing.JRadioButton();
        radioPost = new javax.swing.JRadioButton();
        lblMethod = new javax.swing.JLabel();
        btnDisplay = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtListEntrySelector = new javax.swing.JTextField();
        btnPreview = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        miProduct = new javax.swing.JMenuItem();
        miAffiliate = new javax.swing.JMenuItem();
        miAccess = new javax.swing.JMenuItem();
        miAuthor = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(180);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("/");
        treeDoc.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(treeDoc);

        jSplitPane1.setLeftComponent(jScrollPane1);

        componentPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtSubItemSelector.setText("enter sub item selector");

        lblSubItem.setText("SubItem Selector");

        cmbSubItem.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                onSubItemChange(evt);
            }
        });

        txtMainSelector.setText("enter data list selector");

        lblMainDataSelector.setText("Data list selector");

        txtUrl.setText("enter url");

        lblURL.setText("url");

        lblItem.setText("Item:");

        lblItemName.setText("itemName");

        radioGet.setText("get");

        radioPost.setText("post");

        lblMethod.setText("method:");

        btnDisplay.setText("Display Data");
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onDisplayData(evt);
            }
        });

        jLabel1.setText("List entry selector");

        txtListEntrySelector.setText("enter list entry selector");

        btnPreview.setText("Preview");
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onPreview(evt);
            }
        });

        javax.swing.GroupLayout componentPanelLayout = new javax.swing.GroupLayout(componentPanel);
        componentPanel.setLayout(componentPanelLayout);
        componentPanelLayout.setHorizontalGroup(
            componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(componentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(componentPanelLayout.createSequentialGroup()
                        .addComponent(lblItem)
                        .addGap(18, 18, 18)
                        .addComponent(lblItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtUrl)
                    .addComponent(txtMainSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addGroup(componentPanelLayout.createSequentialGroup()
                        .addComponent(lblURL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMethod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioGet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioPost))
                    .addGroup(componentPanelLayout.createSequentialGroup()
                        .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubItem)
                            .addComponent(lblMainDataSelector)
                            .addComponent(cmbSubItem, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSubItemSelector)
                    .addGroup(componentPanelLayout.createSequentialGroup()
                        .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(componentPanelLayout.createSequentialGroup()
                                .addComponent(btnDisplay)
                                .addGap(33, 33, 33)
                                .addComponent(btnPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtListEntrySelector)))
        );
        componentPanelLayout.setVerticalGroup(
            componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, componentPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblItem)
                    .addComponent(lblItemName))
                .addGap(18, 18, 18)
                .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblURL)
                    .addComponent(radioGet)
                    .addComponent(radioPost)
                    .addComponent(lblMethod))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMainDataSelector)
                .addGap(13, 13, 13)
                .addComponent(txtMainSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtListEntrySelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSubItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSubItem)
                .addGap(18, 18, 18)
                .addComponent(txtSubItemSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDisplay)
                    .addComponent(btnPreview))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout basePanelLayout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(basePanelLayout);
        basePanelLayout.setHorizontalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(componentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        basePanelLayout.setVerticalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(componentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(basePanel);

        menuFile.setText("File");

        openMenuItem.setText("new");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        menuFile.add(openMenuItem);

        jMenuItem2.setText("open");
        menuFile.add(jMenuItem2);

        jMenuItem3.setText("save");
        menuFile.add(jMenuItem3);

        jMenuBar1.add(menuFile);

        menuEdit.setText("Edit");

        miProduct.setText("Product");
        miProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onEditProduct(evt);
            }
        });
        menuEdit.add(miProduct);

        miAffiliate.setText("Affiliate");
        miAffiliate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onEditAffiliate(evt);
            }
        });
        menuEdit.add(miAffiliate);

        miAccess.setText("Access");
        menuEdit.add(miAccess);

        miAuthor.setText("Author");
        menuEdit.add(miAuthor);

        jMenuBar1.add(menuEdit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed

        JDialog newProjDialog = new NewProjectDialog(this, true);

        newProjDialog.setVisible(true);

        project = ((NewProjectDialog) newProjDialog).getProject();

        displayTree(project.getBaseURL(), project.getMethod());

    }//GEN-LAST:event_openMenuItemActionPerformed

    private void onEditProduct(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onEditProduct

        DataSpec productSpec = new DataSpec("product");
        productSpec.addSubItem(new SelectableItem("description"));
        productSpec.addSubItem(new SelectableItem("price"));
        productSpec.addSubItem(new SelectableItem("commision"));
        productSpec.addSubItem(new SelectableItem("product_link"));
        productSpec.addSubItem(new SelectableItem("author"));

        fillComponentPanel(productSpec);
    }//GEN-LAST:event_onEditProduct

    private void onEditAffiliate(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onEditAffiliate

        DataSpec dataSpec = new DataSpec("Affiliate");

        dataSpec.addSubItem(new SelectableItem("affiliateName"));
        dataSpec.addSubItem(new SelectableItem("email"));

        fillComponentPanel(dataSpec);

    }//GEN-LAST:event_onEditAffiliate

    private void onDisplayData(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onDisplayData

        DataSpec dataSpec = project.getDataSpec(curDataSpec);
        dataSpec.setDataURL(txtUrl.getText());
        dataSpec.setMethod(radioGet.isSelected() ? "get" : "post");
        dataSpec.setSelector(txtMainSelector.getText());
        dataSpec.setListEntrySelector(txtListEntrySelector.getText());

        displayTree(project.getBaseURL() + dataSpec.getDataURL(), dataSpec.getMethod());

    }//GEN-LAST:event_onDisplayData

    private void onSubItemChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_onSubItemChange

        if (project != null) {
            String selectorText = "";

            String subItem = (String) cmbSubItem.getSelectedItem();
            DataSpec dataSpec = project.getDataSpec(curDataSpec);
            SelectableItem subItemSpec = dataSpec.getSubItem(subItem);
            selectorText = subItemSpec.getSelector();

            if (selectorText == null) {
                selectorText = "Enter sub item selector";
            }

            txtSubItemSelector.setText(selectorText);
        }
    }//GEN-LAST:event_onSubItemChange

private void buildTree(org.jsoup.nodes.Node dataNode, DefaultMutableTreeNode controlNode) {

        String nodeText = "";
        
        if (dataNode instanceof Element) {
            nodeText = builElementText((Element) dataNode);
        } else {
            nodeText = dataNode.toString();
        }
        
        DefaultMutableTreeNode childNode = null;
        
        if (nodeText.trim().length() > 0) {
            childNode = new DefaultMutableTreeNode(nodeText, true);
            controlNode.add(childNode);
        }

        List<org.jsoup.nodes.Node> childNodes = dataNode.childNodes();

        if (childNodes != null) {
            for (org.jsoup.nodes.Node n : childNodes) {
                buildTree(n, childNode);
            }
        }
    }

    private String builElementText(Element element) {

        String result = "[" + element.tagName();

        Attributes attrList = element.attributes();

        int size = attrList.size();

        if (size != 0) {
            result += " " + attrList.html()+" ";
        }
        result += "]";

        return result;
    }

    private void fillComponentPanel(DataSpec dataSpec) {

        lblItemName.setText(dataSpec.getName());
        Set<String> subItems = dataSpec.getSubItems().keySet();

        for (String name : subItems) {
            cmbSubItem.addItem(name);
        }

        componentPanel.setVisible(true);
        project.addDataSpec(dataSpec);
        curDataSpec = dataSpec.getName();
    }

    private PreviewTableModel buildPreviewTableModel(DataSpec dataSpec) {
        
        Connection con = JSoupTransport.login(project);
        
        org.jsoup.nodes.Document doc = JSoupTransport.retrieveDocument(con, project.getBaseURL() + dataSpec.getDataURL(), dataSpec.getMethod());
        
        JSoupTransport.logout(con, project);
        
        Element dataElem = doc.select(dataSpec.getSelector()).first();
        Elements dataList = dataElem.select(dataSpec.getListEntrySelector());
        
        List<String> names = new ArrayList<String>(dataSpec.getSubItems().keySet());
        ArrayList<List<String>> rows = new ArrayList<List<String>>();
        

        for (int i = 0;i <dataList.size();++i){
            List<String> singleRow = new ArrayList<String>();
            for(SelectableItem item:dataSpec.getSubItems().values()) {
                singleRow.add(dataList.get(i).select(item.getSelector()).first().text()); 
            }
            rows.add(singleRow);
        }
        
       PreviewTableModel result = new PreviewTableModel(names,rows);
        
        
        return result;
    }    
    
    private void onPreview(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onPreview
        DataSpec dataSpec = project.getDataSpec(curDataSpec);
        dataSpec.setDataURL(txtUrl.getText());
        dataSpec.setMethod(radioGet.isSelected() ? "get" : "post");
        dataSpec.setSelector(txtMainSelector.getText());
        dataSpec.setListEntrySelector(txtListEntrySelector.getText());

        PreviewTableModel previewData = buildPreviewTableModel(dataSpec);
        
        JDialog previewDialog = new PreviewDialog(this,previewData, false);

        previewDialog.setVisible(true);
    }//GEN-LAST:event_onPreview
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basePanel;
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cmbSubItem;
    private javax.swing.JPanel componentPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblItem;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblSubItem;
    private javax.swing.JLabel lblURL;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem miAccess;
    private javax.swing.JMenuItem miAffiliate;
    private javax.swing.JMenuItem miAuthor;
    private javax.swing.JMenuItem miProduct;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JRadioButton radioGet;
    private javax.swing.ButtonGroup radioGroup;
    private javax.swing.JRadioButton radioPost;
    private javax.swing.JTree treeDoc;
    private javax.swing.JTextField txtListEntrySelector;
    private javax.swing.JTextField txtMainSelector;
    private javax.swing.JTextField txtSubItemSelector;
    private javax.swing.JTextField txtUrl;
    // End of variables declaration//GEN-END:variables

    
}
