package form;

import java.sql.SQLIntegrityConstraintViolationException;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.PharmaceuticalCompanyDAO;
import daoImpl.PharmaceuticalCompanyDAOImpl;
import model.PharmaceuticalCompany;
import uiTables.CompanyView;
import validators.CustomValidators;

public class CompanyForm extends FormLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField name = new TextField("Name");
	private TextField phoneNumber = new TextField("Phone Number");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;

	private PharmaceuticalCompanyDAO companyDao = new PharmaceuticalCompanyDAOImpl();
	private PharmaceuticalCompany company;
	private CompanyView myUI;
	private FieldGroup fieldGroup;

	public CompanyForm(CompanyView myUI) {
		this.myUI = myUI;

		fieldGroup = new FieldGroup();
		fieldGroup.bind(name, name);
		fieldGroup.bind(phoneNumber, phoneNumber);

		CustomValidators.stringValidator(name);
		CustomValidators.phoneValidator(phoneNumber);

		name.setInputPrompt("Name");
		phoneNumber.setInputPrompt("Phone Number");

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(name, phoneNumber, buttons);
	}

	public void setCompany(PharmaceuticalCompany company, boolean insert) {
		this.company = company;
		this.insert = insert;
		BeanFieldGroup.bindFieldsUnbuffered(company, this);

		// Show delete button only for persisted clients
		delete.setVisible(true);
		setVisible(true);
		name.selectAll();
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

			try {
				companyDao.insert(company);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {

			}
		} else
			try {
				companyDao.update(company);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {

			}
	}

	private void delete() {
		companyDao.delete(company.getPharmaceuticalCompanyId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
