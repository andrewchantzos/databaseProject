
package formSelect;

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
import uiQueries.PatientsOfDoctorView;

public class SelectDoctorForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private ComboBox doctorId = new ComboBox("Doctor");

	private Button click = new Button("Click");

	private PatientsOfDoctorView myUI;
	private DoctorDAO doctorDao = new DoctorDAOImpl();
	private List<Doctor> doctorList;
	
	public SelectDoctorForm(PatientsOfDoctorView myUI) {
		this.myUI = myUI;
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


	private void click() {
		int id = (int) doctorId.getValue();
		myUI.setDoctorId(id);
		myUI.setVisible(true);
		myUI.updateList(id);
		setVisible(false);
		myUI.getGrid().setVisible(true);
		myUI.getToolbar().setVisible(true);
	}
	
	public void init() {
		doctorId.setValue(null);
	}

}
