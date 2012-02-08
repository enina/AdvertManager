/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mne.advertmanager.parsergen;

import com.mne.advertmanager.parsergen.model.Project;
import com.mne.advertmanager.util.JSoupTransport;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Nina Eidelshtein and Misha Lebedev
 */
public class NewProjectDialog extends javax.swing.JDialog {

    private boolean isBaseUrlComplete = true;
    private boolean isUserNameComplete = true;
    private boolean isUserFieldComplete = true;
    private boolean isPasswordComplete = true;
    private boolean isPasswordFieldComplete = true;
    private boolean isHomeFolderComplete = false;
    private boolean isCookieComplete = true;
    private boolean isLoginUrlComplete = true;
    private boolean isLogoutUrlComplete = true;
    private boolean isSelectorComplete = true;
    private boolean isTestabale = false;
    private boolean isInputComplete = isTestabale & isHomeFolderComplete;
    private Project project;
    

    public NewProjectDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        project = new Project();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        
        txtBaseUrl.getDocument().addDocumentListener(new BaseURLSelectorDocListener());
        txtCookie.getDocument().addDocumentListener(new CookieDocListener());
        txtLoginFormURL.getDocument().addDocumentListener(new LoginFormURLDocListener());
        txtLogoutURL.getDocument().addDocumentListener(new LogoutURLDocListener());
        txtPassword.getDocument().addDocumentListener(new PasswordDocListener());
        txtPasswordField.getDocument().addDocumentListener(new PasswordFieldDocListener());
        txtSelector.getDocument().addDocumentListener(new SelectorDocListener());
        txtUserField.getDocument().addDocumentListener(new UserFieldDocListener());
        txtUserName.getDocument().addDocumentListener(new UserDocListener());
        JTextField[] textFields = new  JTextField[]{txtBaseUrl,txtCookie,txtLoginFormURL,txtLogoutURL,txtPassword,txtPasswordField,txtSelector,txtUserField,txtUserName};
        
        for(JTextField tf:textFields)
            tf.setText(tf.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMethod = new javax.swing.JLabel();
        cmbMethod = new javax.swing.JComboBox();
        btnTestConnection = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnCreate = new javax.swing.JButton();
        lblHomeFolder = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        lblUserField = new javax.swing.JLabel();
        txtUserField = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        lblPasswordField = new javax.swing.JLabel();
        txtPasswordField = new javax.swing.JTextField();
        txtBaseUrl = new javax.swing.JTextField();
        lblBaseUrl = new javax.swing.JLabel();
        btnSelectHome = new javax.swing.JButton();
        lblSelector = new javax.swing.JLabel();
        txtSelector = new javax.swing.JTextField();
        lblCookie = new javax.swing.JLabel();
        txtCookie = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtLoginFormURL = new javax.swing.JFormattedTextField();
        lblLogout = new javax.swing.JLabel();
        txtLogoutURL = new javax.swing.JTextField();
        panelStatus = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();

        setTitle("New project");
        setLocationByPlatform(true);
        setModal(true);
        setName("new");
        setResizable(false);

        lblMethod.setLabelFor(cmbMethod);
        lblMethod.setText("Method");

        cmbMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "get", "post" }));

        btnTestConnection.setText("TestConnection");
        btnTestConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onTestConnection(evt);
            }
        });

        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onCreate(evt);
            }
        });

        lblHomeFolder.setToolTipText("select home folder");
        lblHomeFolder.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                onHomeFolderChange(evt);
            }
        });

        lblUser.setText("User Name");

        txtUserName.setText("vasya");
        txtUserName.setToolTipText("enter user name");

        lblPassword.setText("Password");

        lblUserField.setText("User field name");

        txtUserField.setText("j_username");
        txtUserField.setToolTipText("enter username field name");

        txtPassword.setText("vasya");
        txtPassword.setToolTipText("enter password");

        lblPasswordField.setText("Password field name");

        txtPasswordField.setText("j_password");
        txtPasswordField.setToolTipText("enter password field name");

        txtBaseUrl.setText("http://localhost:8080/AdvertManager");
        txtBaseUrl.setToolTipText("enter base url");

        lblBaseUrl.setLabelFor(txtBaseUrl);
        lblBaseUrl.setText("Base Url");

        btnSelectHome.setText("Select Home");
        btnSelectHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSelect(evt);
            }
        });

        lblSelector.setLabelFor(txtSelector);
        lblSelector.setText("Target Selector");

        txtSelector.setText("html > body div[id=content]");

        lblCookie.setText("SessionID CookieName");

        txtCookie.setText("JSESSIONID");

        jLabel1.setText("Login Form URL");

        txtLoginFormURL.setText("j_spring_security_check");

        lblLogout.setText("Logout URL");

        txtLogoutURL.setText("j_spring_security_logout");

        panelStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblStatus.setText("Status");

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStatusLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblStatus))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSelectHome)
                            .addComponent(lblMethod)
                            .addComponent(lblCookie)
                            .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUserField)
                            .addComponent(lblBaseUrl)
                            .addComponent(jLabel1)
                            .addComponent(lblLogout)
                            .addComponent(lblPassword)
                            .addComponent(lblPasswordField))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtUserField)
                                    .addComponent(txtUserName, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCookie, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHomeFolder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBaseUrl)
                                    .addComponent(txtLoginFormURL, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(cmbMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblSelector)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSelector))
                                    .addComponent(txtLogoutURL, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnCreate)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPasswordField)
                                    .addComponent(txtPassword))
                                .addGap(6, 6, 6))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTestConnection)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(7, 7, 7))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSelectHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblHomeFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSelector)
                                    .addComponent(txtSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMethod)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBaseUrl)
                            .addComponent(txtBaseUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtLoginFormURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLogout)
                            .addComponent(txtLogoutURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCookie)
                            .addComponent(txtCookie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUser)
                            .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUserField)
                            .addComponent(txtUserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPassword)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPasswordField)
                            .addComponent(txtPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTestConnection)
                            .addComponent(btnCreate))))
                .addComponent(panelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCookie, txtLogoutURL});

        jLabel5.getAccessibleContext().setAccessibleName("statusMessage");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onTestConnection(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onTestConnection
        String status = validateSetupData(project);

        lblStatus.setText(status);
        setButtonsState();
    }//GEN-LAST:event_onTestConnection

    private String validateSetupData(Project proj) {
        Document doc = null;
        Connection con = null;

        try {
            project.setValid(false);
            con = JSoupTransport.login(proj);
            if (con == null) {
                return "Status:Failed to login";
            }

            doc = JSoupTransport.retrieveDocument(con, proj.getBaseURL(), proj.getMethod());

            if (doc == null) {
                return "Status:Cannot retrieved document from url:" + proj.getBaseURL();
            }
            Element targetElem = doc.select(proj.getSelector()).first();
            if (targetElem != null) {
                project.setValid(true);
                return "Status:Success";
            } else {
                return "Status:Failed to get target content";
            }
        } catch (Exception e) {
            return "Status:Failure.Exception:" + e.getClass().getSimpleName() + ".Message:" + e.getMessage();
        } finally {
            JSoupTransport.logout(con, proj);
        }
    }

    private void onCreate(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onCreate
        setVisible(false);
    }//GEN-LAST:event_onCreate

    private void onSelect(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSelect
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.showOpenDialog(this);
        File homeFolder = fc.getSelectedFile();
        lblHomeFolder.setText(homeFolder.getAbsolutePath());
    }//GEN-LAST:event_onSelect

    private void onHomeFolderChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_onHomeFolderChange
        isHomeFolderComplete = lblHomeFolder.getText().length() > 0;
    }

    public Project getProject() {
        return project;
    }

    private void setButtonsState() {
        isTestabale =   isBaseUrlComplete  & isSelectorComplete  & isUserNameComplete &
                        isLoginUrlComplete & isLogoutUrlComplete & isCookieComplete   &
                        isUserNameComplete & isUserFieldComplete & isPasswordComplete &
                        isPasswordFieldComplete;

        isInputComplete = isTestabale & isHomeFolderComplete;
        btnTestConnection.setEnabled(isTestabale);
        btnCreate.setEnabled(isInputComplete & project.isValid());
    }//GEN-LAST:event_onHomeFolderChange

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
            }
            setButtonsState();
        }

        protected abstract void doUpdate();
    }

    private class BaseURLSelectorDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            isBaseUrlComplete = txtBaseUrl.getText().length()>0;
            project.setBaseURL(txtBaseUrl.getText());
        }
    }

    private class CookieDocListener extends BaseDocumentListener {


        @Override
        protected void doUpdate() {
            isCookieComplete = txtCookie.getText().length()>0;
            project.setCookieName(txtCookie.getText());
        }
    }

    private class LoginFormURLDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            isLoginUrlComplete = txtLoginFormURL.getText().length()>0;
            project.setLoginFormUrl(txtLoginFormURL.getText());
        }
    }

    private class LogoutURLDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            isLogoutUrlComplete = txtLogoutURL.getText().length() > 0;
            project.setLogoutUrl(txtLogoutURL.getText());
        }
    }

    private class PasswordDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            isPasswordComplete = txtPassword.getPassword().length>0;
            project.setPassword(new String(txtPassword.getPassword()));
        }
    }

    private class PasswordFieldDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            isPasswordFieldComplete=txtPasswordField.getText().length() > 0;
            project.setPasswordField(txtPasswordField.getText());
        }
    }

    private class UserDocListener extends BaseDocumentListener {

        @Override
        protected void doUpdate() {
            isUserNameComplete = txtUserName.getText().length()>0;
            project.setUsername(txtUserName.getText());
        }
    }

    private class UserFieldDocListener extends BaseDocumentListener {
        @Override
        protected void doUpdate() {
            isUserFieldComplete = txtUserField.getText().length()>0;
            project.setUserField(txtUserField.getText());
        }
    }

    private class SelectorDocListener extends BaseDocumentListener {
        @Override
        protected void doUpdate() {
            isSelectorComplete = txtSelector.getText().length()>0;
            project.setSelector(txtSelector.getText());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnSelectHome;
    private javax.swing.JButton btnTestConnection;
    private javax.swing.JComboBox cmbMethod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblBaseUrl;
    private javax.swing.JLabel lblCookie;
    private javax.swing.JLabel lblHomeFolder;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPasswordField;
    private javax.swing.JLabel lblSelector;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserField;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JTextField txtBaseUrl;
    private javax.swing.JTextField txtCookie;
    private javax.swing.JFormattedTextField txtLoginFormURL;
    private javax.swing.JTextField txtLogoutURL;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPasswordField;
    private javax.swing.JTextField txtSelector;
    private javax.swing.JTextField txtUserField;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
