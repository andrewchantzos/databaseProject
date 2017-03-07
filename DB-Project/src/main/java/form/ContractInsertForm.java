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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.ContractDAO;
import dao.PharmaceuticalCompanyDAO;
import dao.PharmacyDAO;
import daoImpl.ContractDAOImpl;
import daoImpl.PharmaceuticalCompanyDAOImpl;
import daoImpl.PharmacyDAOImpl;
import model.Contract;
import model.PharmaceuticalCompany;
import model.Pharmacy;
import uiTables.ContractView;
import validators.CustomValidators;

public class ContractInsertForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private ComboBox pharmaceuticalCopmanyId = new ComboBox("Company");
	private ComboBox pharmacyId = new ComboBox("Pharmacy");
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
	
	private PharmaceuticalCompanyDAO companyDao = new PharmaceuticalCompanyDAOImpl();
	private PharmacyDAO pharmacyDao = new PharmacyDAOImpl();
	private List<Pharmacy> pharmacyList;
	private List<PharmaceuticalCompany> companyList;

	
	public ContractInsertForm(ContractView myUI) {
		this.myUI = myUI;
		fieldGroup = new FieldGroup();
		fieldGroup.bind(pharmaceuticalCopmanyId, pharmaceuticalCopmanyId);
		fieldGroup.bind(pharmacyId, pharmacyId);
		fieldGroup.bind(startDate, startDate);
		fieldGroup.bind(endDate, endDate);
		fieldGroup.bind(supervisor, supervisor);
		fieldGroup.bind(text, text);

		startDate.addValidator(new NullValidator("Date cannot be null", false));
		endDate.addValidator(new NullValidator("Date cannot be null", false));

		pharmaceuticalCopmanyId.addValidator(CustomValidators.idValidator());
		pharmacyId.addValidator(CustomValidators.idValidator());
		text.addValidator(CustomValidators.textValidator());

		pharmaceuticalCopmanyId.addFocusListener(new FocusListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FocusEvent event) {
				companyList = companyDao.findAll();	
				for (PharmaceuticalCompany company: companyList) {
					pharmaceuticalCopmanyId.addItem(company.getPharmaceuticalCompanyId());
					String companyCaption = company.getPharmaceuticalCompanyId() + ": " + company.getName();
					pharmaceuticalCopmanyId.setItemCaption(company.getPharmaceuticalCompanyId(), companyCaption);
				}
			}
		});
		
		pharmacyId.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FocusEvent event) {
				pharmacyList = pharmacyDao.findAll();
				for (Pharmacy pharmacy : pharmacyList) {
					pharmacyId.addItem(pharmacy.getPharmacyId());
					String pharmacyCaption = pharmacy.getPharmacyId() + ": " + pharmacy.getName();
					pharmacyId.setItemCaption(pharmacy.getPharmacyId(), pharmacyCaption);
				}
			}
		});
		
		CustomValidators.stringValidator(supervisor);

		// Set input prompts
		pharmaceuticalCopmanyId.setConverter(Integer.class);
		pharmacyId.setConverter(Integer.class);
		pharmaceuticalCopmanyId.setInputPrompt("Company Id");
		pharmacyId.setInputPrompt("Pharmacy Id");

		supervisor.setInputPrompt("Supervisor");
		text.setCaption("Contract Description");

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(pharmaceuticalCopmanyId, pharmacyId, supervisor, startDate, endDate, text, buttons);
	}

	public void setContract(Contract contract, boolean insert) {
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
			contractDao.insert(contract);
			myUI.updateList();
			setVisible(false);
		} catch (SQLIntegrityConstraintViolationException e) {
			Notification.show("ADD FAILED", "Entity with these IDs already exists", Notification.Type.WARNING_MESSAGE);
		}
	}

	private void delete() {
		contractDao.delete(contract.getPharmacyId(), contract.getPharmaceuticalCopmanyId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}