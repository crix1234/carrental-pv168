/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditCustomerDialog.java
 *
 * Created on 15.5.2010, 13:03:47
 */

package carrental;

import carrental.models.CustomerStateModel;
import carrental.entities.Address;
import carrental.entities.Customer;

/**
 *
 * @author S
 */
public class EditCustomerDialog extends javax.swing.JDialog {

	private Customer editingCustomer;
    /** Creates new form EditCustomerDialog */
    public EditCustomerDialog(java.awt.Frame parent, boolean modal, Customer customerToEdit) {
        super(parent, modal);
        initComponents();
		editingCustomer = customerToEdit;
		Address currentAddress = customerToEdit.getAddress();
		jTextFieldForename.setText(customerToEdit.getName());
		jTextFieldSurename.setText(customerToEdit.getSurname());
		jTextFieldStreet.setText(currentAddress.getStreet());
		jTextFieldHousenumber.setText(Integer.toString(currentAddress.getHouseNumber()));
		jTextFieldTown.setText(currentAddress.getTown());
		jTextFieldzipcode.setText(currentAddress.getZipcode());
		CustomerStateModel acm = (CustomerStateModel) jComboBoxState.getModel();
		acm.addCountries();
		acm.setSelectedItem(currentAddress.getState());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelForename = new javax.swing.JLabel();
        jLabelSurename = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelAddress = new javax.swing.JLabel();
        jLabelStreet = new javax.swing.JLabel();
        jLabelHousenumber = new javax.swing.JLabel();
        jLabelTown = new javax.swing.JLabel();
        jLabelState = new javax.swing.JLabel();
        jLabelZipcode = new javax.swing.JLabel();
        jTextFieldForename = new javax.swing.JTextField();
        jTextFieldSurename = new javax.swing.JTextField();
        jTextFieldStreet = new javax.swing.JTextField();
        jTextFieldHousenumber = new javax.swing.JTextField();
        jTextFieldTown = new javax.swing.JTextField();
        jTextFieldzipcode = new javax.swing.JTextField();
        jButtonEdit = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jComboBoxState = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jLabelForename.setText(java.util.ResourceBundle.getBundle("carrental/texts",this.getParent().getLocale()).getString("Forename") + ":");
        jLabelForename.setName("jLabelForename"); // NOI18N

        jLabelSurename.setText(java.util.ResourceBundle.getBundle("carrental/texts",this.getParent().getLocale()).getString("Surname") + ":");
        jLabelSurename.setName("jLabelSurename"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jLabelAddress.setText(java.util.ResourceBundle.getBundle("carrental/texts",this.getParent().getLocale()).getString("Address") + ":");
        jLabelAddress.setName("jLabelAddress"); // NOI18N

        jLabelStreet.setText(java.util.ResourceBundle.getBundle("carrental/texts",this.getParent().getLocale()).getString("Street") + ":");
        jLabelStreet.setName("jLabelStreet"); // NOI18N

        jLabelHousenumber.setText(java.util.ResourceBundle.getBundle("carrental/texts",this.getParent().getLocale()).getString("HouseNumber") + ":");
        jLabelHousenumber.setName("jLabelHousenumber"); // NOI18N

        jLabelTown.setText(java.util.ResourceBundle.getBundle("carrental/texts",this.getParent().getLocale()).getString("Town") + ":");
        jLabelTown.setName("jLabelTown"); // NOI18N

        jLabelState.setText(java.util.ResourceBundle.getBundle("carrental/texts",this.getParent().getLocale()).getString("State") + ":");
        jLabelState.setName("jLabelState"); // NOI18N

        jLabelZipcode.setText(java.util.ResourceBundle.getBundle("carrental/texts",this.getParent().getLocale()).getString("Zipcode") + ":");
        jLabelZipcode.setName("jLabelZipcode"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(carrental.CarRentalApp.class).getContext().getResourceMap(EditCustomerDialog.class);
        jTextFieldForename.setText(resourceMap.getString("jTextFieldForename.text")); // NOI18N
        jTextFieldForename.setName("jTextFieldForename"); // NOI18N

        jTextFieldSurename.setText(resourceMap.getString("jTextFieldSurename.text")); // NOI18N
        jTextFieldSurename.setName("jTextFieldSurename"); // NOI18N

        jTextFieldStreet.setText(resourceMap.getString("jTextFieldStreet.text")); // NOI18N
        jTextFieldStreet.setName("jTextFieldStreet"); // NOI18N

        jTextFieldHousenumber.setText(resourceMap.getString("jTextFieldHousenumber.text")); // NOI18N
        jTextFieldHousenumber.setName("jTextFieldHousenumber"); // NOI18N

        jTextFieldTown.setText(resourceMap.getString("jTextFieldTown.text")); // NOI18N
        jTextFieldTown.setName("jTextFieldTown"); // NOI18N

        jTextFieldzipcode.setText(resourceMap.getString("jTextFieldzipcode.text")); // NOI18N
        jTextFieldzipcode.setName("jTextFieldzipcode"); // NOI18N

        jButtonEdit.setText(resourceMap.getString("jButtonEdit.text")); // NOI18N
        jButtonEdit.setName("jButtonEdit"); // NOI18N
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });

        jButtonCancel.setText(resourceMap.getString("jButtonCancel.text")); // NOI18N
        jButtonCancel.setName("jButtonCancel"); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jComboBoxState.setModel(new carrental.models.CustomerStateModel());
        jComboBoxState.setName("jComboBoxState"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelForename)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldForename, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelSurename)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSurename, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                    .addComponent(jLabelAddress)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelStreet)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldStreet, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelTown)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldTown, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelZipcode)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldzipcode, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelState)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxState, 0, 108, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelHousenumber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldHousenumber, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(jButtonCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelForename)
                    .addComponent(jTextFieldForename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSurename)
                    .addComponent(jTextFieldSurename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelAddress)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStreet)
                    .addComponent(jTextFieldStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHousenumber)
                    .addComponent(jTextFieldHousenumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTown)
                    .addComponent(jTextFieldTown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelState)
                    .addComponent(jComboBoxState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelZipcode)
                    .addComponent(jTextFieldzipcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEdit)
                    .addComponent(jButtonCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed
		if (jComboBoxState.getSelectedItem() != null) {
			Address newAddress = new Address(editingCustomer.getAddress().getId(),
					Integer.parseInt(jTextFieldHousenumber.getText()),
					jTextFieldStreet.getText(),
					jTextFieldTown.getText(),
					jComboBoxState.getSelectedItem().toString(),
					jTextFieldzipcode.getText());
			editingCustomer.setName(jTextFieldForename.getText());
			editingCustomer.setSurname(jTextFieldSurename.getText());
			editingCustomer.setAddress(newAddress);
			this.dispose();
		}
	}//GEN-LAST:event_jButtonEditActionPerformed

	private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
		this.dispose();
	}//GEN-LAST:event_jButtonCancelActionPerformed

	// <editor-fold defaultstate="collapsed" desc="Generated Code">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JComboBox jComboBoxState;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelForename;
    private javax.swing.JLabel jLabelHousenumber;
    private javax.swing.JLabel jLabelState;
    private javax.swing.JLabel jLabelStreet;
    private javax.swing.JLabel jLabelSurename;
    private javax.swing.JLabel jLabelTown;
    private javax.swing.JLabel jLabelZipcode;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldForename;
    private javax.swing.JTextField jTextFieldHousenumber;
    private javax.swing.JTextField jTextFieldStreet;
    private javax.swing.JTextField jTextFieldSurename;
    private javax.swing.JTextField jTextFieldTown;
    private javax.swing.JTextField jTextFieldzipcode;
    // End of variables declaration//GEN-END:variables
	// </editor-fold>
}
