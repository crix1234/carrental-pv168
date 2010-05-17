/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.models;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Jerrycek
 */
public class AddCarStateModel extends AbstractListModel implements ComboBoxModel {

	String[] State = {"Ok", "Rented", "Damaged", "Totaled"};
	String selection = null;

	public Object getElementAt(int index) {
		return State[index];
	}

	public int getSize() {
		return State.length;
	}

	public void setSelectedItem(Object anItem) {
		selection = (String) anItem; // to select and register an
	} // item from the pull-down list

	// Methods implemented from the interface ComboBoxModel
	public Object getSelectedItem() {
		return selection; // to add the selection to the combo box
	}
}
