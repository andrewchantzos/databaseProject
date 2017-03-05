package form;

import java.sql.SQLIntegrityConstraintViolationException;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.PharmacyDAO;
import daoImpl.PharmacyDAOImpl;
import model.Pharmacy;
import uiTables.PharmacyView;
import validators.CustomValidators;

public class PharmacyForm extends FormLayout {

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

	private FieldGroup fieldGroup;

	public PharmacyForm(PharmacyView myUI) {
		this.myUI = myUI;
		fieldGroup = new FieldGroup();
		fieldGroup.bind(name, name);
		fieldGroup.bind(town, town);
		fieldGroup.bind(streetName, streetName);
		fieldGroup.bind(streetNumber, streetNumber);
		fieldGroup.bind(postalCode, postalCode);
		fieldGroup.bind(phoneNumber, phoneNumber);
		CustomValidators.stringValidator(name);

		streetNumber.addValidator(CustomValidators.streetNumber());
		streetNumber.addValidator(new NullValidator("Street Number cannot be null", false));

		CustomValidators.stringValidator(town);

		CustomValidators.stringValidator(postalCode);

		CustomValidators.stringValidator(streetName);
		CustomValidators.phoneValidator(phoneNumber);

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
		addComponents(name, town, streetName, streetNumber, postalCode, phoneNumber, buttons);
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

	@SuppressWarnings("unchecked")
	private void save() {

		delete.setVisible(false);
		try {
			for (Field<?> field : fieldGroup.getFields())
				((AbstractField<String>) field).setValidationVisible(true);
			this.fieldGroup.commit();
		} catch (CommitException e) {
			// Show all the validate errors:
			Notification.show("Invalid input", Notification.Type.WARNING_MESSAGE);

			return;
		}
		if (insert) {

			try {
				pharmacyDao.insert(pharmacy);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {

			}
		} else
			try {
				pharmacyDao.update(pharmacy);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {

			}

	}

	private void delete() {
		pharmacyDao.delete(pharmacy.getPharmacyId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
