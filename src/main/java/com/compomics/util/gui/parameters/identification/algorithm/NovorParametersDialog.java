package com.compomics.util.gui.parameters.identification.algorithm;

import com.compomics.util.examples.BareBonesBrowserLaunch;
import com.compomics.util.gui.parameters.identification.IdentificationAlgorithmParameter;
import com.compomics.util.parameters.identification.tool_specific.NovorParameters;
import java.awt.Dialog;
import javax.swing.SwingConstants;
import com.compomics.util.gui.parameters.identification.AlgorithmParametersDialog;

/**
 * Dialog for editing Novor advanced settings. ¨
 *
 * @author Harald Barsnes
 */
public class NovorParametersDialog extends javax.swing.JDialog implements AlgorithmParametersDialog {

    /**
     * Empty default constructor
     */
    public NovorParametersDialog() {
    }

    /**
     * True if the dialog was canceled by the user.
     */
    private boolean canceled = false;
    /**
     * Boolean indicating whether the settings can be edited by the user.
     */
    private boolean editable;

    /**
     * Creates a new NovorSettingsDialog with a frame as owner.
     *
     * @param parent the parent frame
     * @param novorParameters the Novor parameters
     * @param editable boolean indicating whether the settings can be edited by
     * the user
     */
    public NovorParametersDialog(java.awt.Frame parent, NovorParameters novorParameters, boolean editable) {
        super(parent, true);
        this.editable = editable;
        initComponents();
        setUpGUI();
        populateGUI(novorParameters);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    /**
     * Creates a new NovorSettingsDialog with a dialog as owner.
     *
     * @param owner the dialog owner
     * @param parent the parent frame
     * @param novorParameters the Novor parameters
     * @param editable boolean indicating whether the settings can be edited by
     * the user
     */
    public NovorParametersDialog(Dialog owner, java.awt.Frame parent, NovorParameters novorParameters, boolean editable) {
        super(owner, true);
        this.editable = editable;
        initComponents();
        setUpGUI();
        populateGUI(novorParameters);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    /**
     * Sets up the GUI.
     */
    private void setUpGUI() {
        fragmentationMethodCmb.setRenderer(new com.compomics.util.gui.renderers.AlignedListCellRenderer(SwingConstants.CENTER));
        massAnalyzerCmb.setRenderer(new com.compomics.util.gui.renderers.AlignedListCellRenderer(SwingConstants.CENTER));
        fragmentationMethodCmb.setEnabled(editable);
        massAnalyzerCmb.setEnabled(editable);
    }

    /**
     * Populates the GUI using the given settings.
     *
     * @param novorParameters the parameters to display
     */
    private void populateGUI(NovorParameters novorParameters) {
        fragmentationMethodCmb.setSelectedItem(novorParameters.getFragmentationMethod());
        massAnalyzerCmb.setSelectedItem(novorParameters.getMassAnalyzer());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        novorPanel = new javax.swing.JPanel();
        fragmentationMethodLabel = new javax.swing.JLabel();
        fragmentationMethodCmb = new javax.swing.JComboBox();
        massAnalyzerLabel = new javax.swing.JLabel();
        massAnalyzerCmb = new javax.swing.JComboBox();
        cancelButton = new javax.swing.JButton();
        openDialogHelpJButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Novor Advanced Settings");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        backgroundPanel.setBackground(new java.awt.Color(230, 230, 230));

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        novorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("De Novo Settings"));
        novorPanel.setOpaque(false);

        fragmentationMethodLabel.setText("Fragmentation Method");

        fragmentationMethodCmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "HCD", "CID" }));

        massAnalyzerLabel.setText("Mass Analyzer");

        massAnalyzerCmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Trap", "TOF", "FT" }));

        javax.swing.GroupLayout novorPanelLayout = new javax.swing.GroupLayout(novorPanel);
        novorPanel.setLayout(novorPanelLayout);
        novorPanelLayout.setHorizontalGroup(
            novorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(novorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(novorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(novorPanelLayout.createSequentialGroup()
                        .addComponent(fragmentationMethodLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fragmentationMethodCmb, 0, 140, Short.MAX_VALUE))
                    .addGroup(novorPanelLayout.createSequentialGroup()
                        .addComponent(massAnalyzerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(massAnalyzerCmb, 0, 140, Short.MAX_VALUE)))
                .addContainerGap())
        );
        novorPanelLayout.setVerticalGroup(
            novorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(novorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(novorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fragmentationMethodLabel)
                    .addComponent(fragmentationMethodCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(novorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(massAnalyzerLabel)
                    .addComponent(massAnalyzerCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        openDialogHelpJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/help.GIF"))); // NOI18N
        openDialogHelpJButton.setToolTipText("Open the Novor web page");
        openDialogHelpJButton.setBorder(null);
        openDialogHelpJButton.setBorderPainted(false);
        openDialogHelpJButton.setContentAreaFilled(false);
        openDialogHelpJButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                openDialogHelpJButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                openDialogHelpJButtonMouseExited(evt);
            }
        });
        openDialogHelpJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDialogHelpJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(novorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(openDialogHelpJButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );

        backgroundPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(novorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(openDialogHelpJButton)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Save the settings and close the dialog.
     *
     * @param evt
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        boolean valid = validateParametersInput(true);
        if (valid) {
            dispose();
        }
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * Close the dialog without saving.
     *
     * @param evt
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        canceled = true;
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Open the Novor web page.
     *
     * @param evt
     */
    private void openDialogHelpJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDialogHelpJButtonActionPerformed
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        BareBonesBrowserLaunch.openURL("https://rapidnovor.com");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_openDialogHelpJButtonActionPerformed

    /**
     * Change the icon into a hand cursor.
     *
     * @param evt
     */
    private void openDialogHelpJButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openDialogHelpJButtonMouseEntered
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_openDialogHelpJButtonMouseEntered

    /**
     * Change the cursor back to the default cursor.
     *
     * @param evt
     */
    private void openDialogHelpJButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openDialogHelpJButtonMouseExited
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_openDialogHelpJButtonMouseExited

    /**
     * Close the dialog without saving.
     *
     * @param evt
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cancelButtonActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox fragmentationMethodCmb;
    private javax.swing.JLabel fragmentationMethodLabel;
    private javax.swing.JComboBox massAnalyzerCmb;
    private javax.swing.JLabel massAnalyzerLabel;
    private javax.swing.JPanel novorPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JButton openDialogHelpJButton;
    // End of variables declaration//GEN-END:variables

    /**
     * Inspects the parameters validity.
     *
     * @param showMessage if true an error messages are shown to the users
     * @return a boolean indicating if the parameters are valid
     */
    public boolean validateParametersInput(boolean showMessage) {
        boolean valid = true;
        okButton.setEnabled(valid);
        return valid;
    }

    /**
     * Returns the Novor parameters as set by the user.
     *
     * @return the Novor parameters as set by the user
     */
    public NovorParameters getInput() {
        NovorParameters novorParameters = new NovorParameters();
        novorParameters.setFragmentationMethod((String) fragmentationMethodCmb.getSelectedItem());
        novorParameters.setMassAnalyzer((String) massAnalyzerCmb.getSelectedItem());
        return novorParameters;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public IdentificationAlgorithmParameter getParameters() {
        return getInput();
    }
}
