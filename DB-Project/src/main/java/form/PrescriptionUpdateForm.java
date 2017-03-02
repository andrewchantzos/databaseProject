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
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.PrescriptionDAO;
import daoImpl.PrescriptionDAOImpl;
import model.Prescription;
import ui.PrescriptionView;
import validators.CustomValidators;

public class PrescriptionUpdateForm extends FormLayout {

	private static final long serialVersionUID = 1L;

	private PopupDateField date = new PopupDateField("Date");
	private TextField quantity = new TextField("Quantity");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");

	private PrescriptionDAO prescriptionDao = new PrescriptionDAOImpl();
	private Prescription prescription;
	private PrescriptionView myUI;
	private FieldGroup fieldGroup;

	public PrescriptionUpdateForm(PrescriptionView myUI) {
		this.myUI = myUI;
		fieldGroup = new FieldGroup();

		fieldGroup.bind(date, date);
		fieldGroup.bind(quantity, quantity);

		date.addValidator(new NullValidator("Date cannot be null", false));

		quantity.addValidator(CustomValidators.quantityValidator());

		quantity.setConverter(Integer.class);

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(quantity, date, buttons);
	}

	public void setPrescription(Prescription prescription) {
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
			prescriptionDao.update(prescription);
			myUI.updateList();
			setVisible(false);
		} catch (SQLIntegrityConstraintViolationException e) {
			Notification.show("UPDATE FAILED", "Update with Invalid ID", Notification.Type.WARNING_MESSAGE);
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
