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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.jsoup.Connection;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class ParserGenMainFrame extends javax.swing.JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -2829437157987592509L;
    private static final Logger logger = Logger.getLogger(ParserGenMainFrame.class.getName());
    private Project project = null;
    private String curDataSpec = "";
    private Marshaller marshaller = null;
    private Unmarshaller unmarshaller = null;

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                ParserGenMainFrame app = new ParserGenMainFrame();
                app.setLocation(450, 150);
                app.setVisible(true);
            }
        });
    }

    /**
     * Creates new form ParserGenMainFrame
     */
    public ParserGenMainFrame() {

        boolean isContinue = false;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            isContinue = true;
            JAXBContext jaxbCtx = JAXBContext.newInstance(com.mne.advertmanager.parsergen.model.Project.class);
            marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshaller = jaxbCtx.createUnmarshaller();
        } catch (Exception ex) {
            isContinue = false;
            logger.log(Level.SEVERE, "Failed to set system look and feel {0}:Message {1}", new Object[]{ex.getClass().getSimpleName(), ex.toString()});
        }

        if (isContinue) {
            initComponents();

            reset();

            radioGroup.add(radioGet);
            radioGroup.add(radioPost);

            txtSubItemSelector.getDocument().addDocumentListener(new SubItemSelectorDocListener());
            txtUrl.getDocument().addDocumentListener(new URLDocListener());
            treeHtmlDoc.addMouseListener(new DocTreeMouseListener());
            txtPaging.getDocument().addDocumentListener(new PageParamDocListener());
            txtNumPages.addChangeListener(new NumPagesDocListener());
        }
    }

    private void reset() {
        panelBasic.setVisible(false);
        btnPreview.setVisible(false);

        panelAdvanced.setVisible(false);
        panelPaging.setVisible(false);

        project = null;
        curDataSpec = "";
        fillComponentPanel(null);
    }

    private ArrayList<List<String>> getPageData(org.jsoup.nodes.Document doc, DataSpec dataSpec) {

        Element dataElem = doc.select(dataSpec.getSelector()).first();
        Elements dataList = dataElem.select(dataSpec.getListEntrySelector());

        ArrayList<List<String>> rows;
        rows = new ArrayList<List<String>>();
        for (int i = 0; i < dataList.size(); ++i) {
            List<String> singleRow = new ArrayList<String>();
            for (SelectableItem item : dataSpec.getDataItems()) {
                Element listEntryElement = dataList.get(i);
                String selector = item.getSelector();
                Elements selectedList = listEntryElement.select(selector);
                if (selectedList != null) {
                    Element targetElement = selectedList.first();
                    String columnValue = targetElement.text();
                    singleRow.add(columnValue);
                } else {
                    System.err.println("Error retrieving value of " + selector);
                }
            }
            rows.add(singleRow);
        }
        return rows;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioGroup = new javax.swing.ButtonGroup();
        jMenuItem2 = new javax.swing.JMenuItem();
        splitPane = new javax.swing.JSplitPane();
        panelProperties = new javax.swing.JPanel();
        panelAdvanced = new javax.swing.JPanel();
        txtSubItemSelector = new javax.swing.JTextField();
        cmbSubItem = new javax.swing.JComboBox();
        lblSubItemSelector = new javax.swing.JLabel();
        lblSubItemName = new javax.swing.JLabel();
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
        panelPaging = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNumPages = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        txtPaging = new javax.swing.JTextField();
        panelDocument = new javax.swing.JPanel();
        txtDocQuery = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        scrollPanel = new javax.swing.JScrollPane();
        treeHtmlDoc = new javax.swing.JTree();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        miNew = new javax.swing.JMenuItem();
        miOpen = new javax.swing.JMenuItem();
        miSave = new javax.swing.JMenuItem();
        miExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        miAccess = new javax.swing.JMenuItem();
        miPurchaseOrder = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuOptions = new javax.swing.JMenu();
        javax.swing.JMenuItem miAdvanced = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Parser Configuration");
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("frameMain");

        splitPane.setDividerLocation(300);
        splitPane.setPreferredSize(new java.awt.Dimension(336, 500));
        splitPane.setRequestFocusEnabled(false);

        panelProperties.setPreferredSize(new java.awt.Dimension(300, 650));

        panelAdvanced.setBorder(javax.swing.BorderFactory.createTitledBorder("Advanced"));
        panelAdvanced.setToolTipText("advanced");
        panelAdvanced.setRequestFocusEnabled(false);

        txtSubItemSelector.setText("enter sub item selector");

        cmbSubItem.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                onSubItemChange(evt);
            }
        });

        lblSubItemSelector.setLabelFor(txtSubItemSelector);
        lblSubItemSelector.setText("SubItem Selector");

        lblSubItemName.setLabelFor(cmbSubItem);
        lblSubItemName.setText("SubItem Name");

        javax.swing.GroupLayout panelAdvancedLayout = new javax.swing.GroupLayout(panelAdvanced);
        panelAdvanced.setLayout(panelAdvancedLayout);
        panelAdvancedLayout.setHorizontalGroup(
            panelAdvancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSubItemSelector, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
            .addComponent(cmbSubItem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelAdvancedLayout.createSequentialGroup()
                .addGroup(panelAdvancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubItemSelector)
                    .addComponent(lblSubItemName))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelAdvancedLayout.setVerticalGroup(
            panelAdvancedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdvancedLayout.createSequentialGroup()
                .addComponent(lblSubItemName)
                .addGap(11, 11, 11)
                .addComponent(cmbSubItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubItemSelector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSubItemSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        panelPaging.setBorder(javax.swing.BorderFactory.createTitledBorder("Paging"));

        jLabel1.setText("number of pages");

        jLabel2.setText("paging parameter");

        javax.swing.GroupLayout panelPagingLayout = new javax.swing.GroupLayout(panelPaging);
        panelPaging.setLayout(panelPagingLayout);
        panelPagingLayout.setHorizontalGroup(
            panelPagingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagingLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumPages, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPaging))
        );
        panelPagingLayout.setVerticalGroup(
            panelPagingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagingLayout.createSequentialGroup()
                .addGroup(panelPagingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNumPages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtPaging, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelPropertiesLayout = new javax.swing.GroupLayout(panelProperties);
        panelProperties.setLayout(panelPropertiesLayout);
        panelPropertiesLayout.setHorizontalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBasic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelAdvanced, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelPaging, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPropertiesLayout.setVerticalGroup(
            panelPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropertiesLayout.createSequentialGroup()
                .addComponent(panelBasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAdvanced, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPaging, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
                .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        splitPane.setRightComponent(panelProperties);

        btnSearch.setText("Search in doc");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSearch(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("/");
        treeHtmlDoc.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        scrollPanel.setViewportView(treeHtmlDoc);

        javax.swing.GroupLayout panelDocumentLayout = new javax.swing.GroupLayout(panelDocument);
        panelDocument.setLayout(panelDocumentLayout);
        panelDocumentLayout.setHorizontalGroup(
            panelDocumentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDocumentLayout.createSequentialGroup()
                .addComponent(txtDocQuery, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch))
            .addComponent(scrollPanel)
        );
        panelDocumentLayout.setVerticalGroup(
            panelDocumentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDocumentLayout.createSequentialGroup()
                .addGroup(panelDocumentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDocumentLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtDocQuery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
        );

        splitPane.setLeftComponent(panelDocument);

        menuFile.setText("File");

        miNew.setText("new");
        miNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onNew(evt);
            }
        });
        menuFile.add(miNew);

        miOpen.setText("open");
        miOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onOpen(evt);
            }
        });
        menuFile.add(miOpen);

        miSave.setText("save");
        miSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSave(evt);
            }
        });
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

        miAccess.setText("Access");
        miAccess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onEditAccess(evt);
            }
        });
        menuEdit.add(miAccess);

        miPurchaseOrder.setText("PurchaseOrder");
        miPurchaseOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onEditPurchaseOrder(evt);
            }
        });
        menuEdit.add(miPurchaseOrder);

        jMenuItem1.setText("Partner");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onEditPartner(evt);
            }
        });
        menuEdit.add(jMenuItem1);
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
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void onNew(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onNew

        reset();
        //create new project windo 
        JDialog newProjDialog = new NewProjectDialog(this, true);
        //set new project windo position
        newProjDialog.setLocation(this.getLocation().x + 50, this.getLocation().y + 70);

        //make visible "new project" windo
        newProjDialog.setVisible(true);
        
       
        project = ((NewProjectDialog) newProjDialog).getProject();
        if (project != null && project.isValid()) {
            displayTree(project.getBaseURL() + project.getHomePage(), project.getMethod());
        } else {
            lblStatus.setText("Status:Project configuration is invalid");
        }

    }//GEN-LAST:event_onNew

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

        DataSpec ds = project.getDataSpec(curDataSpec);
        PreviewTableModel previewData = buildPreviewTableModel(ds);

        if (previewData != null) {
            JDialog previewDialog = new PreviewDialog(this, previewData, false);

            previewDialog.setLocation(this.getLocation().x + 50, this.getLocation().y + 70);

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
            panelPaging.setVisible(!panelPaging.isVisible());
        }
    }//GEN-LAST:event_onAdvancedItem

    private void onExit(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onExit
        setVisible(false);
        dispose();
    }//GEN-LAST:event_onExit

    private void onSearch(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSearch

        String query = txtDocQuery.getText();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeHtmlDoc.getModel().getRoot();
        if (query.length() > 0) {
            TreeNode result = find(root, query);
            if (result != null) {
                ArrayList<TreeNode> path = new ArrayList<TreeNode>();
                while (result != null) {
                    path.add(0, result);
                    result = result.getParent();
                }
                TreePath treePath = new TreePath(path.toArray());
                treeHtmlDoc.setSelectionPath(treePath);
                treeHtmlDoc.makeVisible(treePath);
            }
        }
    }//GEN-LAST:event_onSearch

    private void onEditAccess(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onEditAccess
        
        if (project != null && project.isValid()) {
        
            DataSpec dataSpec = project.getDataSpec("Access");

            fillComponentPanel(dataSpec);
            
        }

    }//GEN-LAST:event_onEditAccess

    private void onSave(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSave
        if (project != null && project.isValid()) {
            try {
                String filePath = project.getHomeDirectory() + File.separator + project.getName() + ".xml";
                File result = new File(filePath);   //create new file in system
                marshaller.marshal(project, result);//save Project obj to file in xml format
                lblStatus.setText("Status:Project definition saved to file:"+filePath);
            } catch (JAXBException ex) {
                lblStatus.setText("Status:Failed to save project definition :"+ex.toString());
                System.out.println("marshaling error:" + ex.getMessage());
            }
        }
    }//GEN-LAST:event_onSave

    private void onEditPurchaseOrder(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onEditPurchaseOrder
        // TODO add your handling code here:
        if (project != null && project.isValid()) {
            
            DataSpec dataSpec = project.getDataSpec("PurchaseOrder");
            fillComponentPanel(dataSpec);
        }
    }//GEN-LAST:event_onEditPurchaseOrder

    private void onOpen(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onOpen
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File projectFile = fc.getSelectedFile();
            try {
                project =(Project) unmarshaller.unmarshal(projectFile);
                lblStatus.setText("Status:Project definition successfuly loaded from file:"+projectFile.getAbsolutePath());
                if (project != null && project.isValid()) 
                    displayTree(project.getBaseURL() + project.getHomePage(), project.getMethod());
            } catch (JAXBException ex) {
                logger.log(Level.SEVERE, "Failed to unmarshal project specification", ex);
                System.out.println("Failed to unmarshal project specification:"+ ex);
                lblStatus.setText("Status:Failed to parse project file:"+projectFile.getAbsolutePath()+" with error:"+ex.toString());
            }
        }
    }//GEN-LAST:event_onOpen

//================================== onEditPartner =============================
/**This function help to collect data about affiliates Partners:
 * upon selection from edit tab "Partners" option, this function create
 * dataSpec object with two params : Name and Email. those params then appear
 * in advanced option of right screen and in context menu of left mous button
 * when clicked on DOM tree to select proper DOM object for Name, Email.
 */
    private void onEditPartner(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onEditPartner
        
        if (project != null && project.isValid()) {
        
            DataSpec dataSpec = project.getDataSpec("Partner");

            fillComponentPanel(dataSpec);
            
        }
    }//GEN-LAST:event_onEditPartner

    private TreeNode find(DefaultMutableTreeNode node, String query) {

        NodeData data = null;
        TreeNode result = null;
        DefaultMutableTreeNode childNode = null;

        if (node != null) {

            data = (NodeData) node.getUserObject();

            if (data.match(query)) {
                return node;
            }

            for (int i = 0; i < node.getChildCount(); ++i) {
                childNode = (DefaultMutableTreeNode) node.getChildAt(i);
                result = find(childNode, query);
                if (result != null) {
                    break;
                }
            }
        }

        return result;
    }

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
        String status = "";
        try {
            con = JSoupTransport.login(project);
            if (con != null) {
                org.jsoup.nodes.Document dataRoot = JSoupTransport.retrieveDocument(con, url, method);
                if (dataRoot != null) {
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
                } else {
                    status = "Status:Failed to retrieved data from URL=" + url;
                }
            } else {
                status = "Status:Failed to login";
            }
        } finally {
            lblStatus.setText(status);
            JSoupTransport.logout(con, project);
        }
        return result;
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

    private void fillComponentPanel(DataSpec dataSpec) {

        curDataSpec = null;
        txtUrl.setText("");
        cmbSubItem.removeAllItems();
        lblStatus.setText("Status:");

        if (dataSpec != null) {
            String dataURL = dataSpec.getDataURL();
            lblItemName.setText(dataSpec.getName());
            txtUrl.setText(dataURL);
            for (SelectableItem si : dataSpec.getAllSubItems()) {
                cmbSubItem.addItem(si.getName());
                txtSubItemSelector.setText(si.getSelector());
            }

            panelBasic.setVisible(true);
            curDataSpec = dataSpec.getName();
            
            if (dataSpec.getNumPages() > 0)
                txtNumPages.setValue(dataSpec.getNumPages());
            else
                txtNumPages.setValue(0);
            
            if (dataSpec.getPageParam()!=null && dataSpec.getPageParam().length()> 0)
                txtPaging.setText(dataSpec.getPageParam());
            else
                txtPaging.setText("");
            
            
            if (dataURL != null && dataURL.length() > 0)
                displayTree(project.getBaseURL() + dataURL, dataSpec.getMethod());
        }


    }

    private PreviewTableModel buildPreviewTableModel(DataSpec dataSpec) {

        Connection con = JSoupTransport.login(project);

        org.jsoup.nodes.Document doc = JSoupTransport.retrieveDocument(con, project.getBaseURL() + dataSpec.getDataURL(), dataSpec.getMethod());

        JSoupTransport.logout(con, project);

        PreviewTableModel result = null;
        List<String> names = null;
        ArrayList<List<String>> page = null;
        try {

            names = new ArrayList<String>();
            for (SelectableItem si : dataSpec.getDataItems()) {
                names.add(si.getName());
            }
            page = getPageData(doc, dataSpec);
            result = new PreviewTableModel(names, page);
            lblStatus.setText("Status:Success");
        } catch (Exception e) {
            lblStatus.setText("Status:Failed to retrieve data.Review selector settings.");
        }

        return result;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class PageParamDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            String pageParam = txtPaging.getText();
            ds.setPageParam(pageParam);
        }
    }

    private class NumPagesDocListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if (project != null) {
                if (curDataSpec != null && curDataSpec.length() > 0) {
                    DataSpec ds = project.getDataSpec(curDataSpec);
                    ds.setNumPages((Integer) txtNumPages.getValue());
                }
            }
        }
    }

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
    /*
     * private class MainSelectorDocListener extends BaseDocumentListener {
     *
     * @Override protected void doUpdate() { ds.setSelector(txtMainSelector.getText()); } }
     *
     * private class ListEntrySelectorDocListener extends BaseDocumentListener {
     *
     * @Override protected void doUpdate() { ds.setListEntrySelector(txtListEntrySelector.getText()); } }
     */

    private abstract class BaseDocumentListener implements DocumentListener {

        protected DataSpec ds = null;

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
            ds = null;
            if (project != null) {

                if (curDataSpec != null && curDataSpec.length() > 0) {
                    ds = project.getDataSpec(curDataSpec);
                }

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

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3 && project != null && curDataSpec != null) {
                JPopupMenu ctxMenu = new JPopupMenu();
                ActionListener actionListener = new CtxMenuActionListener(treeHtmlDoc.getClosestPathForLocation(e.getX(), e.getY()));
                DataSpec dataSpec = project.getDataSpec(curDataSpec);
                if (dataSpec != null) {
                    for (SelectableItem si : dataSpec.getAllSubItems()) {
                        ctxMenu.add(new CtxMenuItem(si.getName() + " element", si.getName(), actionListener));
                    }
                    ctxMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private class CtxMenuItem extends JMenuItem {

        /**
         *
         */
        private static final long serialVersionUID = 6410627515515543560L;

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

        @SuppressWarnings("unused")
        public String getLabel() {
            return label;
        }

        @SuppressWarnings("unused")
        public void setLabel(String label) {
            this.label = label;
        }

        public String getTagName() {
            return tagName;
        }

        @SuppressWarnings("unused")
        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        @Override
        public String toString() {
            return label;
        }

        private boolean match(String query) {
            boolean result = tagName.indexOf(query) >= 0 || label.indexOf(query) >= 0;
            return result;
        }
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

            if ("root".equals(cmd)) {
                for (int i = 0; i < path.getPathCount(); ++i) {
                    DefaultMutableTreeNode curPathElem = (DefaultMutableTreeNode) path.getPathComponent(i);
                    selector += buildSinglePathElementSelector(curPathElem, true, true);
                }
            } else {
                selector = buildSinglePathElementSelector((DefaultMutableTreeNode) path.getLastPathComponent(), false, true);
            }
            cmbSubItem.setSelectedItem(cmd);
            txtSubItemSelector.setText(selector);
        }

        private String buildSinglePathElementSelector(DefaultMutableTreeNode curPathElem, boolean isPrefixRequired, boolean isSuffixRequired) {

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
                    if (isSuffixRequired) {
                        suffix = ":eq(" + childIndex + ")";
                    }
                }
            }

            result += prefix + ((NodeData) curPathElem.getUserObject()).getTagName() + suffix;

            return result;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cmbSubItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JLabel lblItem;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSubItemName;
    private javax.swing.JLabel lblSubItemSelector;
    private javax.swing.JLabel lblURL;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuOptions;
    private javax.swing.JMenuItem miAccess;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miNew;
    private javax.swing.JMenuItem miOpen;
    private javax.swing.JMenuItem miPurchaseOrder;
    private javax.swing.JMenuItem miSave;
    private javax.swing.JPanel panelAdvanced;
    private javax.swing.JPanel panelBasic;
    private javax.swing.JPanel panelDocument;
    private javax.swing.JPanel panelPaging;
    private javax.swing.JPanel panelProperties;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JRadioButton radioGet;
    private javax.swing.ButtonGroup radioGroup;
    private javax.swing.JRadioButton radioPost;
    private javax.swing.JScrollPane scrollPanel;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JTree treeHtmlDoc;
    private javax.swing.JTextField txtDocQuery;
    private javax.swing.JSpinner txtNumPages;
    private javax.swing.JTextField txtPaging;
    private javax.swing.JTextField txtSubItemSelector;
    private javax.swing.JTextField txtUrl;
    // End of variables declaration//GEN-END:variables
}
