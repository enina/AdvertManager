/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.parsergen;

import com.mne.advertmanager.parsergen.model.DataSpec;
import com.mne.advertmanager.parsergen.model.Project;
import com.mne.advertmanager.parsergen.model.SelectableItem;
import com.mne.advertmanager.util.JSoupTransport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
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

        ParserGenMainFrame app = new ParserGenMainFrame();
        app.setLocation(450, 150);
        app.setVisible(true);

    }

    /**
     * Creates new form ParserGenMainFrame
     */
    public ParserGenMainFrame() {

        boolean isContinue = false;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            isContinue = true;
        } catch (Exception ex) {
            isContinue = false;
            logger.log(Level.SEVERE, "Failed to set system look and feel {0}", ex.getClass().getSimpleName());
        }
        
        if (isContinue) {
            initComponents();
            panelBasic.setVisible(false);
            btnPreview.setVisible(false);

            panelAdvanced.setVisible(false);
            radioGroup.add(radioGet);
            radioGroup.add(radioPost);

            txtSubItemSelector.getDocument().addDocumentListener(new SubItemSelectorDocListener());
            txtUrl.getDocument().addDocumentListener(new URLDocListener());
            txtMainSelector.getDocument().addDocumentListener(new MainSelectorDocListener());
            txtListEntrySelector.getDocument().addDocumentListener(new ListEntrySelectorDocListener());
            treeHtmlDoc.addMouseListener(new DocTreeMouseListener());
        }
    }


    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioGroup = new javax.swing.ButtonGroup();
        splitPane = new javax.swing.JSplitPane();
        scrollPanel = new javax.swing.JScrollPane();
        treeHtmlDoc = new javax.swing.JTree();
        panelBase = new javax.swing.JPanel();
        panelAdvanced = new javax.swing.JPanel();
        txtSubItemSelector = new javax.swing.JTextField();
        cmbSubItem = new javax.swing.JComboBox();
        lblSubItem = new javax.swing.JLabel();
        txtListEntrySelector = new javax.swing.JTextField();
        lblListEntry = new javax.swing.JLabel();
        txtMainSelector = new javax.swing.JTextField();
        javax.swing.JLabel lblMainDataSelector = new javax.swing.JLabel();
        panelBasic = new javax.swing.JPanel();
        lblItem = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        lblURL = new javax.swing.JLabel();
        lblMethod = new javax.swing.JLabel();
        radioGet = new javax.swing.JRadioButton();
        radioPost = new javax.swing.JRadioButton();
        txtUrl = new javax.swing.JTextField();
        btnDisplay = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        panelStatus = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        miNew = new javax.swing.JMenuItem();
        miOpen = new javax.swing.JMenuItem();
        miSave = new javax.swing.JMenuItem();
        miExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        miProduct = new javax.swing.JMenuItem();
        miAffiliate = new javax.swing.JMenuItem();
        miAccess = new javax.swing.JMenuItem();
        miAuthor = new javax.swing.JMenuItem();
        menuOptions = new javax.swing.JMenu();
        javax.swing.JMenuItem miAdvanced = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Parser Configuration");
        setAlwaysOnTop(true);

        splitPane.setDividerLocation(300);
        splitPane.setPreferredSize(new java.awt.Dimension(336, 500));
        splitPane.setRequestFocusEnabled(false);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("/");
        treeHtmlDoc.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        scrollPanel.setViewportView(treeHtmlDoc);

        splitPane.setLeftComponent(scrollPanel);

        panelBase.setPreferredSize(new java.awt.Dimension(300, 650));

        panelAdvanced.setBorder(javax.swing.BorderFactory.createTitledBorder("Advanced"));
        panelAdvanced.setToolTipText("advanced");
        panelAdvanced.setRequestFocusEnabled(false);

        txtSubItemSelector.setText("enter sub item selector");

        cmbSubItem.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                onSubItemChange(evt);
            }
        });

        lblSubItem.setText("SubItem Selector");

        txtListEntrySelector.setText("enter list entry selector");

        lblListEntry.setText("List entry selector");

        txtMainSelector.setText("enter data list selector");

        lblMainDataSelector.setText("Data list selector");

        javax.swing.GroupLayout panelAdvancedLayout = new javax.swing.GroupLayout(panelAdvanced);
        panelAdvanced.setLayout(panelAdvancedLayout);
        panelAdvancedLayout.setHorizontalGroup(
            panelAdvancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdvancedLayout.createSequentialGroup()
                .addComponent(lblListEntry)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAdvancedLayout.createSequentialGroup()
                .addGroup(panelAdvancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAdvancedLayout.createSequentialGroup()
                        .addGroup(panelAdvancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubItem)
                            .addComponent(lblMainDataSelector))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cmbSubItem, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(231, 231, 231))
            .addComponent(txtMainSelector)
            .addComponent(txtListEntrySelector)
            .addComponent(txtSubItemSelector)
        );
        panelAdvancedLayout.setVerticalGroup(
            panelAdvancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdvancedLayout.createSequentialGroup()
                .addComponent(lblMainDataSelector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMainSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblListEntry)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtListEntrySelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSubItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSubItemSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        panelBasic.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic"));

        lblItem.setText("Item:");

        lblItemName.setText("itemName");

        lblURL.setText("url");

        lblMethod.setText("method:");

        radioGet.setSelected(true);
        radioGet.setText("get");
        radioGet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onMethodChange(evt);
            }
        });

        radioPost.setText("post");

        txtUrl.setText("enter url");

        btnDisplay.setText("Display Data");
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onDisplayData(evt);
            }
        });

        btnPreview.setText("Preview");
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onPreview(evt);
            }
        });

        javax.swing.GroupLayout panelBasicLayout = new javax.swing.GroupLayout(panelBasic);
        panelBasic.setLayout(panelBasicLayout);
        panelBasicLayout.setHorizontalGroup(
            panelBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBasicLayout.createSequentialGroup()
                .addGroup(panelBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblURL)
                    .addComponent(lblItem))
                .addGap(28, 28, 28)
                .addGroup(panelBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBasicLayout.createSequentialGroup()
                        .addComponent(lblItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelBasicLayout.createSequentialGroup()
                        .addComponent(lblMethod)
                        .addGap(50, 50, 50)
                        .addComponent(radioGet)
                        .addGap(18, 18, 18)
                        .addComponent(radioPost)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(txtUrl)
            .addGroup(panelBasicLayout.createSequentialGroup()
                .addComponent(btnDisplay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelBasicLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDisplay, btnPreview});

        panelBasicLayout.setVerticalGroup(
            panelBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBasicLayout.createSequentialGroup()
                .addGroup(panelBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblItem)
                    .addComponent(lblItemName))
                .addGroup(panelBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblURL)
                    .addComponent(lblMethod)
                    .addComponent(radioGet)
                    .addComponent(radioPost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelBasicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDisplay)
                    .addComponent(btnPreview))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblStatus.setText("Status:");

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStatusLayout.createSequentialGroup()
                .addComponent(lblStatus)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBaseLayout = new javax.swing.GroupLayout(panelBase);
        panelBase.setLayout(panelBaseLayout);
        panelBaseLayout.setHorizontalGroup(
            panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBasic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelAdvanced, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBaseLayout.setVerticalGroup(
            panelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBaseLayout.createSequentialGroup()
                .addComponent(panelBasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAdvanced, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        splitPane.setRightComponent(panelBase);

        menuFile.setText("File");

        miNew.setText("new");
        miNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onNew(evt);
            }
        });
        menuFile.add(miNew);

        miOpen.setText("open");
        menuFile.add(miOpen);

        miSave.setText("save");
        menuFile.add(miSave);

        miExit.setText("exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onExit(evt);
            }
        });
        menuFile.add(miExit);

        menuBar.add(menuFile);

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

        menuBar.add(menuEdit);

        menuOptions.setText("Options");

        miAdvanced.setText("Advanced");
        miAdvanced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAdvancedItem(evt);
            }
        });
        menuOptions.add(miAdvanced);

        menuBar.add(menuOptions);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void onNew(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onNew

        JDialog newProjDialog = new NewProjectDialog(this, true);

        newProjDialog.setLocation(this.getLocation().x+50, this.getLocation().y+70);
        
        newProjDialog.setVisible(true);

        project = ((NewProjectDialog) newProjDialog).getProject();
        if (project!= null && project.isValid()) 
            displayTree(project.getBaseURL(), project.getMethod());
        else
            lblStatus.setText("Status:Project configuration is invalid");

    }//GEN-LAST:event_onNew

    private void onEditProduct(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onEditProduct

        if (project!= null && project.isValid()) {
            DataSpec productSpec = new DataSpec("product");
            productSpec.addSubItem(new SelectableItem("description"));
            productSpec.addSubItem(new SelectableItem("price"));
            productSpec.addSubItem(new SelectableItem("commision"));
            productSpec.addSubItem(new SelectableItem("product_link"));
            productSpec.addSubItem(new SelectableItem("author"));

            fillComponentPanel(productSpec);
        }
    }//GEN-LAST:event_onEditProduct

    private void onEditAffiliate(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onEditAffiliate

        if (project != null && project.isValid()) {
            DataSpec dataSpec = new DataSpec("Affiliate");

            dataSpec.addSubItem(new SelectableItem("name"));
            dataSpec.addSubItem(new SelectableItem("email"));

            fillComponentPanel(dataSpec);
        }

    }//GEN-LAST:event_onEditAffiliate

    private void onDisplayData(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onDisplayData

        if (project != null && project.isValid()) {
            DataSpec dataSpec = project.getDataSpec(curDataSpec);

            displayTree(project.getBaseURL() + dataSpec.getDataURL(), dataSpec.getMethod());
        }

    }//GEN-LAST:event_onDisplayData

    private void onSubItemChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_onSubItemChange

        if (project != null) {
            String selectorText = "";

            String subItem = (String) cmbSubItem.getSelectedItem();
            DataSpec dataSpec = project.getDataSpec(curDataSpec);
            if (dataSpec != null) {
                SelectableItem subItemSpec = dataSpec.getSubItem(subItem);
                selectorText = subItemSpec.getSelector();

                if (selectorText == null) {
                    selectorText = "Enter sub item selector";
                }
                txtSubItemSelector.setText(selectorText);
            }
        }
    }//GEN-LAST:event_onSubItemChange

    private void onPreview(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onPreview

        PreviewTableModel previewData = buildPreviewTableModel(project.getDataSpec(curDataSpec));

        if (previewData != null) {
            JDialog previewDialog = new PreviewDialog(this, previewData, false);

            previewDialog.setLocation(this.getLocation().x+50, this.getLocation().y+70);
            
            previewDialog.setVisible(true);
        }
    }//GEN-LAST:event_onPreview

    private void onMethodChange(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onMethodChange
        if (project != null) {
            project.getDataSpec(curDataSpec).setMethod(radioGet.isSelected() ? "get" : "post");
        }
    }//GEN-LAST:event_onMethodChange

    private void onAdvancedItem(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAdvancedItem
        
        if (project != null && project.isValid()) {
            panelAdvanced.setVisible(!panelAdvanced.isVisible());
        }
    }//GEN-LAST:event_onAdvancedItem

    private void onExit(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onExit
        setVisible(false);
        dispose();
    }//GEN-LAST:event_onExit

    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void buildTree(org.jsoup.nodes.Node dataNode, DefaultMutableTreeNode controlNode) {

        String nodeText = "";
        String tagName = "";

        if (dataNode instanceof Element) {
            nodeText = builElementText((Element) dataNode);
            tagName = ((Element) dataNode).tagName();
        } else {
            nodeText = dataNode.toString();
            tagName = dataNode.nodeName();
        }

        DefaultMutableTreeNode childNode = null;

        if (nodeText.trim().length() > 0) {
            childNode = new DefaultMutableTreeNode(new NodeData(tagName, nodeText), true);
            controlNode.add(childNode);
        }

        List<org.jsoup.nodes.Node> childNodes = dataNode.childNodes();

        if (childNodes != null) {
            for (org.jsoup.nodes.Node n : childNodes) {
                buildTree(n, childNode);
            }
        }
    }
    private boolean displayTree(String url, String method) {

        Connection con = null;
        boolean result = false;
        String status="";
        try {
            con = JSoupTransport.login(project);
            if (con != null) {
                org.jsoup.nodes.Document dataRoot = JSoupTransport.retrieveDocument(con, url, method);
                if (dataRoot != null) {
                    JSoupTransport.logout(con, project);

                    DefaultMutableTreeNode controlRoot = new DefaultMutableTreeNode("/");
                    controlRoot.setAllowsChildren(true);

                    buildTree(dataRoot.select("html").first(), controlRoot);

                    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) controlRoot.getFirstChild();

                    DefaultTreeModel model = new DefaultTreeModel(rootNode);
                    treeHtmlDoc.setModel(model);

                    ((DefaultMutableTreeNode) rootNode).setParent(null);
                    model.reload(rootNode);

                    status = "Status:Success";
                    result = true;
                }else
                    status = "Status:Failed to retrieved data from URL=" + url;
            }else
                status = "Status:Failed to login";
        } finally {
            lblStatus.setText(status);
            JSoupTransport.logout(con, project);
            return result;
        }
    }

    private String builElementText(Element element) {

        String result = "[" + element.tagName();

        Attributes attrList = element.attributes();

        int size = attrList.size();

        if (size != 0) {
            result += " " + attrList.html() + " ";
        }
        result += "]";

        return result;
    }

    @SuppressWarnings("unchecked")
    private void fillComponentPanel(DataSpec dataSpec) {

        lblItemName.setText(dataSpec.getName());
        txtUrl.setText("");
        txtMainSelector.setText("");
        txtListEntrySelector.setText("");
        
        
        Set<String> subItems = dataSpec.getSubItems().keySet();
        for (String name : subItems) {
            cmbSubItem.addItem(name);
            txtSubItemSelector.setText("");
        }

        panelBasic.setVisible(true);
        project.addDataSpec(dataSpec);
        curDataSpec = dataSpec.getName();
        lblStatus.setText("Status:");
        
    }

    private PreviewTableModel buildPreviewTableModel(DataSpec dataSpec) {

        Connection con = JSoupTransport.login(project);

        org.jsoup.nodes.Document doc = JSoupTransport.retrieveDocument(con, project.getBaseURL() + dataSpec.getDataURL(), dataSpec.getMethod());

        JSoupTransport.logout(con, project);

        PreviewTableModel result = null;
        List<String> names = null;
        ArrayList<List<String>> rows = null;
        try {
            Element dataElem = doc.select(dataSpec.getSelector()).first();
            Elements dataList = dataElem.select(dataSpec.getListEntrySelector());

            names = new ArrayList<String>(dataSpec.getSubItems().keySet());
            rows = new ArrayList<List<String>>();

            for (int i = 0; i < dataList.size(); ++i) {
                List<String> singleRow = new ArrayList<String>();
                for (SelectableItem item : dataSpec.getSubItems().values()) {
                    singleRow.add(dataList.get(i).select(item.getSelector()).first().text());
                }
                rows.add(singleRow);
            }
            result = new PreviewTableModel(names, rows);
            lblStatus.setText("Status:Success");
        }catch(Exception e) {
            lblStatus.setText("Status:Failed to retrieve data.Review selector settings.");
        }

        return result;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private class SubItemSelectorDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            String subItem = (String) cmbSubItem.getSelectedItem();
            SelectableItem subItemSpec = ds.getSubItem(subItem);
            subItemSpec.setSelector(txtSubItemSelector.getText());
        }
    }

    private class URLDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            ds.setDataURL(txtUrl.getText());
        }
    }

    private class MainSelectorDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            ds.setSelector(txtMainSelector.getText());            
        }
    }

    private class ListEntrySelectorDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            ds.setListEntrySelector(txtListEntrySelector.getText());
        }
    }

    private abstract class BaseDocumentListener implements DocumentListener {

        protected DataSpec ds = null;
        @Override public void changedUpdate(DocumentEvent e) {  updateData(e); }

        @Override public void insertUpdate(DocumentEvent e) {   updateData(e); }

        @Override public void removeUpdate(DocumentEvent e) {    updateData(e); }

        private void updateData(DocumentEvent e) {

            if (project != null) {

                if (curDataSpec!= null && curDataSpec.length()>0)
                    ds=project.getDataSpec(curDataSpec);

                if (ds != null) {
                    doUpdate();
                    if (ds.isValid()) {
                        btnPreview.setVisible(true);
                    } else {
                        btnPreview.setVisible(false);
                    }
                }
                
            }
        }

        protected abstract void doUpdate();
    }

    private class DocTreeMouseListener implements MouseListener {

        @Override public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3 && project != null && curDataSpec != null) {
                JPopupMenu ctxMenu = new JPopupMenu();
                ActionListener actionListener = new CtxMenuActionListener(treeHtmlDoc.getClosestPathForLocation(e.getX(), e.getY()));
                DataSpec dataSpec = project.getDataSpec(curDataSpec);
                if (dataSpec!= null) {
                    ctxMenu.add(new CtxMenuItem(dataSpec.getName() + " root element", "main", actionListener));
                    ctxMenu.add(new CtxMenuItem(dataSpec.getName() + " list entry element", "li", actionListener));

                    for (String subItem : dataSpec.getSubItems().keySet()) {
                        ctxMenu.add(new CtxMenuItem(subItem + " element", subItem, actionListener));
                    }

                    ctxMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

        }

        @Override public void mousePressed(MouseEvent e)  {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e)  {}
        @Override public void mouseExited(MouseEvent e)   {}
    }

    private class CtxMenuItem extends JMenuItem {

        CtxMenuItem(String label, String command, ActionListener actionListener) {
            super(label);
            setText(label);
            setActionCommand(command);
            addActionListener(actionListener);
        }
    }

    private class NodeData {

        private String tagName;
        private String label;

        NodeData(String tagName, String label) {
            this.tagName = tagName;
            this.label = label;
        }

        public String getLabel() { return label; }
        public void setLabel(String label) {this.label = label;}
        public String getTagName() {return tagName;}
        public void setTagName(String tagName) {this.tagName = tagName;}
        @Override public String toString() {return label; }
    }

    private class CtxMenuActionListener implements ActionListener {

        private TreePath path;

        private CtxMenuActionListener(TreePath path) {
            this.path = path;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String cmd = e.getActionCommand();

            String selector = "";
            JTextField curField = null;

            if ("main".equals(cmd)) {
                for (int i = 0; i < path.getPathCount(); ++i) {
                    DefaultMutableTreeNode curPathElem = (DefaultMutableTreeNode) path.getPathComponent(i);
                    selector += buildSinglePathElementSelector(curPathElem, true,true);
                }
                curField = txtMainSelector;
            } else if ("li".equals(cmd)) {

                selector = buildSinglePathElementSelector((DefaultMutableTreeNode) path.getLastPathComponent(), false,false);
                curField = txtListEntrySelector;

            } else {
                selector = buildSinglePathElementSelector((DefaultMutableTreeNode) path.getLastPathComponent(), false,true);
                cmbSubItem.setSelectedItem(cmd);
                curField = txtSubItemSelector;
            }
            curField.setText(selector);
        }

        private String buildSinglePathElementSelector(DefaultMutableTreeNode curPathElem, boolean isPrefixRequired,boolean isSuffixRequired) {

            String prefix = "";
            String suffix = "";
            String result = "";

            TreeNode parent = curPathElem.getParent();

            if (parent != null) {
                int childIndex = parent.getIndex(curPathElem);
                if (childIndex == 0) {
                    if (isPrefixRequired) {
                        prefix = ">";
                    }
                    suffix = " ";
                } else {
                    prefix = " ";
                    if (isSuffixRequired)
                        suffix = ":eq(" + childIndex + ")";
                }
            }

            result += prefix + ((NodeData) curPathElem.getUserObject()).getTagName() + suffix;

            return result;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cmbSubItem;
    private javax.swing.JLabel lblItem;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblListEntry;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSubItem;
    private javax.swing.JLabel lblURL;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuOptions;
    private javax.swing.JMenuItem miAccess;
    private javax.swing.JMenuItem miAffiliate;
    private javax.swing.JMenuItem miAuthor;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miNew;
    private javax.swing.JMenuItem miOpen;
    private javax.swing.JMenuItem miProduct;
    private javax.swing.JMenuItem miSave;
    private javax.swing.JPanel panelAdvanced;
    private javax.swing.JPanel panelBase;
    private javax.swing.JPanel panelBasic;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JRadioButton radioGet;
    private javax.swing.ButtonGroup radioGroup;
    private javax.swing.JRadioButton radioPost;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JTree treeHtmlDoc;
    private javax.swing.JTextField txtListEntrySelector;
    private javax.swing.JTextField txtMainSelector;
    private javax.swing.JTextField txtSubItemSelector;
    private javax.swing.JTextField txtUrl;
    // End of variables declaration//GEN-END:variables
}
