package form;


import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.PatientUpdatableDAO;
import daoImpl.PatientsUpdatableDAOImpl;
import modelQueries.PatientsUpdatable;
import uiViews.PatientsUpdatableView;
import validators.CustomValidators;

public class PatientUpdatableForm extends FormLayout {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private TextField firstName = new TextField("Firstname");

	private TextField lastName = new TextField("Lastname");
	private TextField town = new TextField("Town");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;

	private PatientUpdatableDAO patientDao = new PatientsUpdatableDAOImpl();
	private PatientsUpdatable patient;
	private PatientsUpdatableView myUI;
	private FieldGroup fieldGroup;

	public PatientUpdatableForm(PatientsUpdatableView myUI) {

		fieldGroup = new FieldGroup();
		fieldGroup.bind(firstName, firstName);
		fieldGroup.bind(lastName, lastName);
		fieldGroup.bind(town, town);



		CustomValidators.stringValidator(firstName);

		CustomValidators.stringValidator(lastName);

		CustomValidators.stringValidator(town);

		this.myUI = myUI;

		// Set input prompts
		firstName.setInputPrompt("First Name");
		lastName.setInputPrompt("Last Name");
		town.setInputPrompt("Town");

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(firstName, lastName, town, buttons);
	}

	public void setPatientUpdatable(PatientsUpdatable patient, boolean insert) {
		this.patient = patient;
		this.insert = insert;
		BeanFieldGroup.bindFieldsUnbuffered(patient, this);

		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		firstName.selectAll();
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

			patientDao.insert(patient);
			myUI.updateList();
			setVisible(false);

		} else
			patientDao.update(patient);
		myUI.updateList();
		setVisible(false);

	}

	private void delete() {
		patientDao.delete(patient.getPatientId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
