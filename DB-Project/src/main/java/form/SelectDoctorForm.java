
package form;

import java.util.List;

import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import dao.DoctorDAO;
import daoImpl.DoctorDAOImpl;
import model.Doctor;
import ui.PatientsOfDoctorView;

public class SelectDoctorForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private ComboBox doctorId = new ComboBox("Doctor");

	private Button click = new Button("Click");

	private PatientsOfDoctorView myUI;
	private DoctorDAO doctorDao = new DoctorDAOImpl();
	private List<Doctor> doctorList;
	private Doctor doctor;
	
	public SelectDoctorForm(PatientsOfDoctorView myUI, Doctor doctor) {
		this.myUI = myUI;
		this.doctor = doctor;
		click.setStyleName(ValoTheme.BUTTON_PRIMARY);
		click.setClickShortcut(KeyCode.ENTER);

		click.addClickListener(e -> click());

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
		
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(click);

		buttons.setSpacing(true);
		addComponents(doctorId, buttons);
	}

	public void setDoctor(Doctor doctor) {
		//this.doctor = doctor;
	}

	@SuppressWarnings("unchecked")
	private void click() {
		int id = (int) doctorId.getValue();
		doctor = doctorDao.findById(id);
		myUI.setVisible(true);
		myUI.updateList(id);
		setVisible(false);
		myUI.getGrid().setVisible(true);
		myUI.getToolbar().setVisible(true);
		myUI.setDoctorId(id);
	}
	
	public void init() {
		doctorId.setValue(null);
	}

}
