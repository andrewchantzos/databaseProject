package form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.DoctorDAO;
import daoImpl.DoctorDAOImpl;
import model.Doctor;
import ui.DoctorView;

public class DoctorForm  extends FormLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField firstName = new TextField("Firstname");
	private TextField lastName = new TextField("Lastname");
	private TextField speciality = new TextField("Speciality");
	private TextField experience = new TextField("Experience");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	
	private DoctorDAO doctorDao = new DoctorDAOImpl();
	private Doctor doctor;
	private DoctorView myUI;
	
	
	public DoctorForm(DoctorView myUI) {
		this.myUI = myUI;

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
	
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
		BeanFieldGroup.bindFieldsUnbuffered(doctor, this);
		
		
		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		firstName.selectAll();
	}
	
	private void save() {
		doctorDao.insert(doctor);
		setVisible(false);
	}
	
	private void delete() {
		doctorDao.delete(doctor.getDoctorId());
		myUI.updateList();
		setVisible(false);
	}
}
