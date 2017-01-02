package form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.PrescriptionDAO;
import daoImpl.PrescriptionDAOImpl;
import model.Prescription;
import ui.PrescriptionView;

public class PrescriptionInsertForm  extends FormLayout {
	
	/**	private int patientId;
	
	private int doctorId;
	
	private int drugId;
	
	private Date date;
	
	private int quantity;
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField patientId = new TextField("Patient Id");
	private TextField doctorId = new TextField("Doctor Id");
	private TextField drugId = new TextField("Drug Id");
	private PopupDateField date = new PopupDateField("Date");
	private TextField quantity = new TextField("Quantity");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;
	
	private PrescriptionDAO prescriptionDao = new PrescriptionDAOImpl();
	private Prescription prescription;
	private PrescriptionView myUI;
	
	
	public PrescriptionInsertForm(PrescriptionView myUI) {
		this.myUI = myUI;

		// Set input prompts
		patientId.setConverter(Integer.class);
		doctorId.setConverter(Integer.class);
		drugId.setConverter(Integer.class);
		quantity.setConverter(Integer.class);
		
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);
		
		buttons.setSpacing(true);
		addComponents(patientId, doctorId, drugId, quantity, date, buttons);
	}
	
	public void setPrescription(Prescription prescription, boolean insert) {
		this.prescription = prescription;
		this.insert = insert;
		BeanFieldGroup.bindFieldsUnbuffered(prescription, this);
		
		
		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		quantity.selectAll();
	}
	
	private void save() {
		if (insert)
			prescriptionDao.insert(prescription);
		else
			prescriptionDao.update(prescription);
		myUI.updateList();
		setVisible(false);
	}
	
	private void delete() {
		prescriptionDao.delete(prescription.getPatientId(), prescription.getDoctorId(), prescription.getDrugId());
		myUI.updateList();
		setVisible(false);
	}
}
