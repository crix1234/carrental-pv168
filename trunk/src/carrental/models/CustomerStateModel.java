/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Jerrycek
 */
public class CustomerStateModel extends AbstractListModel implements ComboBoxModel {

	private List<String> countries = new ArrayList<String>();
	String selection = null;

	public void addCountries() {
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale locale : locales) {
			if (locale.getDisplayCountry().length() > 0) {
				if (!countries.contains(locale.getDisplayCountry()))
				countries.add(locale.getDisplayCountry());
			}
		}
		Collections.sort(countries);
	}

	public Object getElementAt(int index) {
		return countries.get(index);
	}

	public int getSize() {
		return countries.size();
	}

	public void setSelectedItem(Object anItem) {
		selection = (String) anItem; // to select and register an
	} // item from the pull-down list

	// Methods implemented from the interface ComboBoxModel
	public Object getSelectedItem() {
		return selection; // to add the selection to the combo box
	}
}

