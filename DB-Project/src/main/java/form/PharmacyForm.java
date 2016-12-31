package form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.PharmacyDAO;
import daoImpl.PharmacyDAOImpl;
import model.Pharmacy;
import ui.PharmacyView;

public class PharmacyForm  extends FormLayout {
	
	/**

	 */
	private static final long serialVersionUID = 1L;
	private TextField name = new TextField("Name");
	private TextField town = new TextField("Town");
	private TextField streetName = new TextField("Street Name");
	private TextField streetNumber = new TextField("Street Number");
	private TextField postalCode = new TextField("Postal Code");
	private TextField phoneNumber = new TextField("Phone Number");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;
	
	private PharmacyDAO pharmacyDao = new PharmacyDAOImpl();
	private Pharmacy pharmacy;
	private PharmacyView myUI;
	
	
	public PharmacyForm(PharmacyView myUI) {
		this.myUI = myUI;

		// Set input prompts
		name.setInputPrompt("Name");
		town.setInputPrompt("Town");
		streetName.setInputPrompt("Street");
		streetNumber.setConverter(Integer.class);
		streetNumber.setInputPrompt("0");
		postalCode.setInputPrompt("Postal Code");
		phoneNumber.setInputPrompt("Phone Number");
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		
		buttons.setSpacing(true);
		addComponents(name, town, streetName, streetNumber, postalCode, phoneNumber,  buttons);
	}
	
	public void setPharmacy(Pharmacy Pharmacy, boolean insert) {
		this.pharmacy = Pharmacy;
		this.insert = insert;
		BeanFieldGroup.bindFieldsUnbuffered(Pharmacy, this);
		
		
		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		name.selectAll();
	}
	
	private void save() {
		if (insert)
			pharmacyDao.insert(pharmacy);
		else
			pharmacyDao.update(pharmacy);
		myUI.updateList();
		setVisible(false);
	}
	
	private void delete() {
		pharmacyDao.delete(pharmacy.getPharmacyId());
		myUI.updateList();
		setVisible(false);
	}
}
