package form;

import java.util.List;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.PatientDAO;
import daoImpl.DoctorDAOImpl;
import daoImpl.PatientDAOImpl;
import model.Doctor;
import model.Patient;
import uiTables.PatientView;
import validators.CustomValidators;

public class PatientForm extends FormLayout {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private TextField firstName = new TextField("Firstname");

	private TextField lastName = new TextField("Lastname");
	private TextField town = new TextField("Town");
	private TextField streetName = new TextField("Street Name");
	private TextField streetNumber = new TextField("Street Number");
	private TextField postalCode = new TextField("Postal Code");
	private TextField phone = new TextField("Phone Number");
	private TextField age = new TextField("Age");
	private ComboBox doctorId = new ComboBox("Doctor");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;

	private PatientDAO patientDao = new PatientDAOImpl();
	private Patient patient;
	private PatientView myUI;
	private FieldGroup fieldGroup;
	private List<Doctor> doctorList;
	private DoctorDAOImpl doctorDao = new DoctorDAOImpl();

	public PatientForm(PatientView myUI) {

		fieldGroup = new FieldGroup();
		fieldGroup.bind(firstName, firstName);
		fieldGroup.bind(lastName, lastName);
		fieldGroup.bind(town, town);
		fieldGroup.bind(streetName, streetName);
		fieldGroup.bind(streetNumber, streetNumber);
		fieldGroup.bind(postalCode, postalCode);
		fieldGroup.bind(phone, phone);
		fieldGroup.bind(age, age);
		fieldGroup.bind(doctorId, doctorId);

		doctorId.addValidator(CustomValidators.idValidator());

		doctorId.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FocusEvent event) {
				doctorList = doctorDao.findAll();
				for (Doctor doctor : doctorList) {
					doctorId.addItem(doctor.getDoctorId());
					String doctorCaption = doctor.getDoctorId() + ": " + doctor.getFirstName() + " "
							+ doctor.getLastName();
					doctorId.setItemCaption(doctor.getDoctorId(), doctorCaption);
				}
			}
		});

		CustomValidators.stringValidator(firstName);

		streetNumber.addValidator(CustomValidators.streetNumber());
		streetNumber.addValidator(new NullValidator("Street Number cannot be null", false));
		CustomValidators.stringValidator(lastName);

		CustomValidators.stringValidator(town);

		CustomValidators.stringValidator(postalCode);

		CustomValidators.stringValidator(streetName);
		CustomValidators.phoneValidator(phone);

		age.addValidator(CustomValidators.ageValidator());

		this.myUI = myUI;

		// Set input prompts
		firstName.setInputPrompt("First Name");
		lastName.setInputPrompt("Last Name");
		town.setInputPrompt("Town");
		streetName.setInputPrompt("Street");
		streetNumber.setConverter(Integer.class);
		streetNumber.setInputPrompt("1");
		postalCode.setInputPrompt("Postal Code");
		phone.setInputPrompt("Phone Number");
		age.setConverter(Integer.class);
		doctorId.setConverter(Integer.class);

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(firstName, lastName, town, streetName, streetNumber, postalCode, phone, age, doctorId, buttons);
	}

	public void setPatient(Patient patient, boolean insert) {
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
