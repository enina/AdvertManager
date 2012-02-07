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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to set system look and feel {0}", ex.getClass().getSimpleName());
        }
        initComponents();
        basicPanel.setVisible(false);
        btnPreview.setVisible(false);

        advancedPanel.setVisible(false);
        radioGroup.add(radioGet);
        radioGroup.add(radioPost);

        txtSubItemSelector.getDocument().addDocumentListener(new SubItemSelectorDocListener());
        txtUrl.getDocument().addDocumentListener(new URLDocListener());
        txtMainSelector.getDocument().addDocumentListener(new MainSelectorDocListener());
        txtListEntrySelector.getDocument().addDocumentListener(new ListEntrySelectorDocListener());
        treeDoc.addMouseListener(new DocTreeMouseListener());
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
                    treeDoc.setModel(model);

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
        advancedPanel = new javax.swing.JPanel();
        txtSubItemSelector = new javax.swing.JTextField();
        cmbSubItem = new javax.swing.JComboBox();
        lblSubItem = new javax.swing.JLabel();
        txtListEntrySelector = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtMainSelector = new javax.swing.JTextField();
        javax.swing.JLabel lblMainDataSelector = new javax.swing.JLabel();
        basicPanel = new javax.swing.JPanel();
        lblItem = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        lblURL = new javax.swing.JLabel();
        lblMethod = new javax.swing.JLabel();
        radioGet = new javax.swing.JRadioButton();
        radioPost = new javax.swing.JRadioButton();
        txtUrl = new javax.swing.JTextField();
        btnDisplay = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        miExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        miProduct = new javax.swing.JMenuItem();
        miAffiliate = new javax.swing.JMenuItem();
        miAccess = new javax.swing.JMenuItem();
        miAuthor = new javax.swing.JMenuItem();
        Options = new javax.swing.JMenu();
        javax.swing.JMenuItem miAdvanced = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(180);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(336, 500));
        jSplitPane1.setRequestFocusEnabled(false);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("/");
        treeDoc.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(treeDoc);

        jSplitPane1.setLeftComponent(jScrollPane1);

        basePanel.setPreferredSize(new java.awt.Dimension(300, 650));

        advancedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Advanced"));
        advancedPanel.setToolTipText("advanced");
        advancedPanel.setRequestFocusEnabled(false);

        txtSubItemSelector.setText("enter sub item selector");

        cmbSubItem.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                onSubItemChange(evt);
            }
        });

        lblSubItem.setText("SubItem Selector");

        txtListEntrySelector.setText("enter list entry selector");

        jLabel1.setText("List entry selector");

        txtMainSelector.setText("enter data list selector");

        lblMainDataSelector.setText("Data list selector");

        javax.swing.GroupLayout advancedPanelLayout = new javax.swing.GroupLayout(advancedPanel);
        advancedPanel.setLayout(advancedPanelLayout);
        advancedPanelLayout.setHorizontalGroup(
            advancedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(advancedPanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, advancedPanelLayout.createSequentialGroup()
                .addGroup(advancedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, advancedPanelLayout.createSequentialGroup()
                        .addGroup(advancedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubItem)
                            .addComponent(lblMainDataSelector))
                        .addGap(0, 108, Short.MAX_VALUE))
                    .addComponent(cmbSubItem, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(231, 231, 231))
            .addComponent(txtMainSelector)
            .addComponent(txtListEntrySelector)
            .addComponent(txtSubItemSelector)
        );
        advancedPanelLayout.setVerticalGroup(
            advancedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(advancedPanelLayout.createSequentialGroup()
                .addComponent(lblMainDataSelector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMainSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
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

        basicPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic"));

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

        javax.swing.GroupLayout basicPanelLayout = new javax.swing.GroupLayout(basicPanel);
        basicPanel.setLayout(basicPanelLayout);
        basicPanelLayout.setHorizontalGroup(
            basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicPanelLayout.createSequentialGroup()
                .addGroup(basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblURL)
                    .addComponent(lblItem))
                .addGap(28, 28, 28)
                .addGroup(basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(basicPanelLayout.createSequentialGroup()
                        .addComponent(lblItemName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(basicPanelLayout.createSequentialGroup()
                        .addComponent(lblMethod)
                        .addGap(50, 50, 50)
                        .addComponent(radioGet)
                        .addGap(18, 18, 18)
                        .addComponent(radioPost)
                        .addGap(0, 171, Short.MAX_VALUE))))
            .addComponent(txtUrl)
            .addGroup(basicPanelLayout.createSequentialGroup()
                .addComponent(btnDisplay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        basicPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDisplay, btnPreview});

        basicPanelLayout.setVerticalGroup(
            basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicPanelLayout.createSequentialGroup()
                .addGroup(basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblItem)
                    .addComponent(lblItemName))
                .addGroup(basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblURL)
                    .addComponent(lblMethod)
                    .addComponent(radioGet)
                    .addComponent(radioPost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(basicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDisplay)
                    .addComponent(btnPreview))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblStatus.setText("Status:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblStatus)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout basePanelLayout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(basePanelLayout);
        basePanelLayout.setHorizontalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(basicPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(advancedPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        basePanelLayout.setVerticalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addComponent(basicPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(advancedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        miExit.setText("exit");
        menuFile.add(miExit);

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

        Options.setText("Options");

        miAdvanced.setText("Advanced");
        miAdvanced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAdvancedItem(evt);
            }
        });
        Options.add(miAdvanced);

        jMenuBar1.add(Options);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed

        JDialog newProjDialog = new NewProjectDialog(this, true);

        newProjDialog.setVisible(true);

        project = ((NewProjectDialog) newProjDialog).getProject();
        if (project!= null && project.isValid()) 
            displayTree(project.getBaseURL(), project.getMethod());
        else
            lblStatus.setText("Status:Project configuration is invalid");

    }//GEN-LAST:event_openMenuItemActionPerformed

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

            dataSpec.addSubItem(new SelectableItem("affiliateName"));
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
        Set<String> subItems = dataSpec.getSubItems().keySet();

        for (String name : subItems) {
            cmbSubItem.addItem(name);
        }

        basicPanel.setVisible(true);
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

    private void onPreview(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onPreview

        PreviewTableModel previewData = buildPreviewTableModel(project.getDataSpec(curDataSpec));

        if (previewData != null) {
            JDialog previewDialog = new PreviewDialog(this, previewData, false);

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
            advancedPanel.setVisible(!advancedPanel.isVisible());
        }
    }//GEN-LAST:event_onAdvancedItem

    private class SubItemSelectorDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            String subItem = (String) cmbSubItem.getSelectedItem();
            DataSpec dataSpec = project.getDataSpec(curDataSpec);
            if (dataSpec!= null) {
                SelectableItem subItemSpec = dataSpec.getSubItem(subItem);
                subItemSpec.setSelector(txtSubItemSelector.getText());
            }
        }
    }

    private class URLDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            DataSpec dataSpec = project.getDataSpec(curDataSpec);
            if (dataSpec!= null) {
                dataSpec.setDataURL(txtUrl.getText());
            }
        }
    }

    private class MainSelectorDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            project.getDataSpec(curDataSpec).setSelector(txtMainSelector.getText());            
        }
    }

    private class ListEntrySelectorDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            project.getDataSpec(curDataSpec).setListEntrySelector(txtListEntrySelector.getText());
        }
    }

    private abstract class BaseDocumentListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateData(e);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateData(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateData(e);
        }

        private void updateData(DocumentEvent e) {
            if (project != null) {
                
                doUpdate();

                if (project.getDataSpec(curDataSpec).isValid())
                    btnPreview.setVisible(true);
                else
                    btnPreview.setVisible(false);
            }
            
        }

        protected abstract void doUpdate();
    }

    private class DocTreeMouseListener implements MouseListener {

        @Override public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3 && project != null && curDataSpec != null) {
                JPopupMenu ctxMenu = new JPopupMenu();
                ActionListener actionListener = new CtxMenuActionListener(treeDoc.getClosestPathForLocation(e.getX(), e.getY()));
                DataSpec dataSpec = project.getDataSpec(curDataSpec);
                ctxMenu.add(new CtxMenuItem(dataSpec.getName() + " root element", "main", actionListener));
                ctxMenu.add(new CtxMenuItem(dataSpec.getName() + " list entry element", "li", actionListener));

                for (String subItem : dataSpec.getSubItems().keySet()) {
                    ctxMenu.add(new CtxMenuItem(subItem + " element", subItem, actionListener));
                }

                ctxMenu.show(e.getComponent(), e.getX(), e.getY());
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
    private javax.swing.JMenu Options;
    private javax.swing.JPanel advancedPanel;
    private javax.swing.JPanel basePanel;
    private javax.swing.JPanel basicPanel;
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnPreview;
    private javax.swing.JComboBox cmbSubItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblItem;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSubItem;
    private javax.swing.JLabel lblURL;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem miAccess;
    private javax.swing.JMenuItem miAffiliate;
    private javax.swing.JMenuItem miAuthor;
    private javax.swing.JMenuItem miExit;
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
