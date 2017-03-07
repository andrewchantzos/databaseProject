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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.ContractDAO;
import daoImpl.ContractDAOImpl;
import model.Contract;
import uiTables.ContractView;
import validators.CustomValidators;

public class ContractUpdateForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private PopupDateField startDate = new PopupDateField("Start Date");
	private PopupDateField endDate = new PopupDateField("End Date");

	private TextField supervisor = new TextField("Supervisor");
	private TextArea text = new TextArea("Description");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");

	private ContractDAO contractDao = new ContractDAOImpl();
	private Contract contract;
	private ContractView myUI;
	private FieldGroup fieldGroup;

	public ContractUpdateForm(ContractView myUI) {
		this.myUI = myUI;

		fieldGroup = new FieldGroup();

		fieldGroup.bind(startDate, startDate);
		fieldGroup.bind(endDate, endDate);
		fieldGroup.bind(supervisor, supervisor);
		fieldGroup.bind(text, text);
		
		startDate.addValidator(new NullValidator("Date cannot be null", false));
		endDate.addValidator(new NullValidator("Date cannot be null", false));
		text.addValidator(CustomValidators.textValidator());
		CustomValidators.stringValidator(supervisor);

		supervisor.setInputPrompt("Supervisor");
		text.setCaption("Contract Description");

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(supervisor, startDate, endDate, text, buttons);
	}

	public void setContract(Contract contract) {
		this.contract = contract;
		BeanFieldGroup.bindFieldsUnbuffered(contract, this);

		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		supervisor.selectAll();
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
			contractDao.update(contract);
			myUI.updateList();
			setVisible(false);
		} catch (SQLIntegrityConstraintViolationException e) {
			Notification.show("UPDATE FAILED", "Update with Invalid ID", Notification.Type.WARNING_MESSAGE);
		}
	}

	private void delete() {
		contractDao.delete(contract.getPharmacyId(), contract.getPharmaceuticalCompanyId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
