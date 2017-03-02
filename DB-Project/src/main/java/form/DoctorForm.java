package form;

import java.sql.SQLIntegrityConstraintViolationException;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
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

import dao.DoctorDAO;
import daoImpl.DoctorDAOImpl;
import model.Doctor;
import ui.DoctorView;
import validators.CustomValidators;

public class DoctorForm extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField firstName = new TextField("Firstname");
	private TextField lastName = new TextField("Lastname");
	private ComboBox speciality = new ComboBox("Speciality");

	private TextField experience = new TextField("Experience");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;

	private DoctorDAO doctorDao = new DoctorDAOImpl();
	private Doctor doctor;
	private DoctorView myUI;
	private FieldGroup fieldGroup;

	public DoctorForm(DoctorView myUI) {
		this.myUI = myUI;
		speciality.addItem("Pathology");
		speciality.addItem("Cardiology");
		speciality.addItem("Gastroenterology");
		speciality.addItem("General practice");
		speciality.addItem("Neurology");
		speciality.addItem("Orthopaedics");
		speciality.addItem("Ophthalmology");
		speciality.addItem("Otorhinolaryngology");
		speciality.addItem("Surgeon");
		speciality.addItem("Paediatrics");
		speciality.addItem("Psychiatry");
		speciality.addItem("Urology");
		
		fieldGroup = new FieldGroup();
		fieldGroup.bind(firstName, firstName);
		fieldGroup.bind(lastName, lastName);
		fieldGroup.bind(experience, experience);
		fieldGroup.bind(speciality, speciality);
		CustomValidators.stringValidator(firstName);
		CustomValidators.stringValidator(lastName);
		// experience.addValidator(new NullValidator("Street Number cannot be
		// null", false));
		experience.addValidator(CustomValidators.experienceValidator());

		// Set input prompts
		firstName.setInputPrompt("First Name");
		lastName.setInputPrompt("Last Name");
		speciality.setInputPrompt("Speciality");
		experience.setConverter(Integer.class);
		experience.setInputPrompt("Experience");

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		buttons.setSpacing(true);
		addComponents(firstName, lastName, speciality, experience, buttons);

	}

	public void setDoctor(Doctor doctor, boolean insert) {
		this.doctor = doctor;
		this.insert = insert;
		BeanFieldGroup.bindFieldsUnbuffered(doctor, this);

		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		firstName.selectAll();
	}

	@SuppressWarnings("unchecked")
	private void save() {
		System.out.println(speciality.getValue().toString());
		speciality.setValue("lol");
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
				doctorDao.insert(doctor);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {

			}
		} else
			try {
				doctorDao.update(doctor);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {

			}

	}

	private void delete() {
		doctorDao.delete(doctor.getDoctorId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
