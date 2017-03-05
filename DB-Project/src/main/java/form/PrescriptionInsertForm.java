package form;

import java.sql.SQLIntegrityConstraintViolationException;
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
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.DrugDAO;
import dao.PatientDAO;
import dao.PrescriptionDAO;
import daoImpl.DoctorDAOImpl;
import daoImpl.DrugDAOImpl;
import daoImpl.PatientDAOImpl;
import daoImpl.PrescriptionDAOImpl;
import model.Doctor;
import model.Drug;
import model.Patient;
import model.Prescription;
import uiTables.PrescriptionView;
import validators.CustomValidators;

public class PrescriptionInsertForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private ComboBox patientId = new ComboBox("Patient");
	private ComboBox doctorId = new ComboBox("Doctor");
	private ComboBox drugId = new ComboBox("Drug");
	private PopupDateField date = new PopupDateField("Date");
	private TextField quantity = new TextField("Quantity");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");

	private PrescriptionDAO prescriptionDao = new PrescriptionDAOImpl();
	private Prescription prescription;
	private PrescriptionView myUI;
	private FieldGroup fieldGroup;

	private PatientDAO patientDAO = new PatientDAOImpl();
	private DoctorDAOImpl doctorDao = new DoctorDAOImpl();
	private DrugDAO drugDAO = new DrugDAOImpl();

	private List<Drug> drugList;
	private List<Patient> patientList;
	private List<Doctor> doctorList;

	public PrescriptionInsertForm(PrescriptionView myUI) {
		this.myUI = myUI;
		fieldGroup = new FieldGroup();
		fieldGroup.bind(patientId, patientId);
		fieldGroup.bind(doctorId, doctorId);
		fieldGroup.bind(drugId, drugId);
		fieldGroup.bind(date, date);
		fieldGroup.bind(quantity, quantity);

		date.addValidator(new NullValidator("Date cannot be null", false));

		doctorId.addValidator(CustomValidators.idValidator());
		patientId.addValidator(CustomValidators.idValidator());
		drugId.addValidator(CustomValidators.idValidator());
		quantity.addValidator(CustomValidators.quantityValidator());

		patientId.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FocusEvent event) {
				patientList = patientDAO.findAll();
				for (Patient patient : patientList) {
					patientId.addItem(patient.getPatientId());
					String patientCaption = patient.getPatientId() + ": " + patient.getFirstName() + " "
							+ patient.getLastName();
					patientId.setItemCaption(patient.getPatientId(), patientCaption);
				}
			}
		});

		drugId.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FocusEvent event) {
				drugList = drugDAO.findAll();
				for (Drug drug : drugList) {
					drugId.addItem(drug.getDrugId());
					String drugCaption = drug.getDrugId() + ": " + drug.getName();
					drugId.setItemCaption(drug.getDrugId(), drugCaption);
				}
			}
		});

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

		BeanFieldGroup.bindFieldsUnbuffered(prescription, this);

		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		quantity.selectAll();
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
		try {
			prescriptionDao.insert(prescription);
			myUI.updateList();
			setVisible(false);
		} catch (SQLIntegrityConstraintViolationException e) {
			Notification.show("ADD FAILED", "Entity with these IDs already exists", Notification.Type.WARNING_MESSAGE);
		}
	}

	private void delete() {
		prescriptionDao.delete(prescription.getPatientId(), prescription.getDoctorId(), prescription.getDrugId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
