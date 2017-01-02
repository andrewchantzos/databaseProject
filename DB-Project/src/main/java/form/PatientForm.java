package form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.PatientDAO;
import daoImpl.PatientDAOImpl;
import model.Patient;
import ui.PatientView;

public class PatientForm  extends FormLayout {
	
	/**
	 */
	private static final long serialVersionUID = 1L;
	private TextField firstName = new TextField("Firstname");
	private TextField lastName = new TextField("Firstname");
	private TextField town = new TextField("Town");
	private TextField streetName = new TextField("Street Name");
	private TextField streetNumber = new TextField("Street Number");
	private TextField postalCode = new TextField("Postal Code");
	private TextField phone = new TextField("Phone Number");
	private TextField age = new TextField("Age");
	private TextField doctorId = new TextField("Doctor Id");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;
	
	private PatientDAO patientDao = new PatientDAOImpl();
	private Patient patient;
	private PatientView myUI;
	
	
	public PatientForm(PatientView myUI) {
		this.myUI = myUI;

		// Set input prompts
		firstName.setInputPrompt("First Name");
		lastName.setInputPrompt("Last Name");
		town.setInputPrompt("Town");
		streetName.setInputPrompt("Street");
		streetNumber.setConverter(Integer.class);
		streetNumber.setInputPrompt("0");
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
	
	private void save() {
		if (insert) {
			delete.setVisible(false);
			patientDao.insert(patient);
		}
		else
			patientDao.update(patient);
		myUI.updateList();
		setVisible(false);
	}
	
	private void delete() {
		patientDao.delete(patient.getPatientId());
		myUI.updateList();
		setVisible(false);
	}
}
