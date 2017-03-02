package form;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.DrugDAO;
import dao.PharmaceuticalCompanyDAO;
import daoImpl.DrugDAOImpl;
import daoImpl.PharmaceuticalCompanyDAOImpl;
import model.Drug;
import model.PharmaceuticalCompany;
import ui.DrugView;
import validators.CustomValidators;

public class DrugForm extends FormLayout {

	/**
	 * private int drugId;
	 * 
	 * private String name;
	 * 
	 * private String formula;
	 * 
	 * private int pharmaceuticalCompanyId;
	 */
	private static final long serialVersionUID = 1L;
	private TextField name = new TextField("Name");
	private TextArea formula = new TextArea("Formula");
	private ComboBox pharmaceuticalCompanyId = new ComboBox("Company");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;

	private DrugDAO drugDao = new DrugDAOImpl();
	private PharmaceuticalCompanyDAO companyDao = new PharmaceuticalCompanyDAOImpl();
	private List<PharmaceuticalCompany> companyList;
	
	private Drug drug;
	private DrugView myUI;
	private FieldGroup fieldGroup;

	public DrugForm(DrugView myUI) {
		this.myUI = myUI;

		fieldGroup = new FieldGroup();
		fieldGroup.bind(name, name);
		fieldGroup.bind(formula, formula);
		fieldGroup.bind(pharmaceuticalCompanyId, pharmaceuticalCompanyId);
		pharmaceuticalCompanyId.addValidator(CustomValidators.idValidator());

		pharmaceuticalCompanyId.addFocusListener(new FocusListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FocusEvent event) {
				companyList = companyDao.findAll();	
				for (PharmaceuticalCompany company: companyList) {
					pharmaceuticalCompanyId.addItem(company.getPharmaceuticalCompanyId());
					String companyCaption = company.getPharmaceuticalCompanyId() + ": " + company.getName();
					pharmaceuticalCompanyId.setItemCaption(company.getPharmaceuticalCompanyId(), companyCaption);
				}
			}
		});
		
		CustomValidators.stringValidator(name);

		// Set input prompts
		name.setInputPrompt("Name");
		formula.setInputPrompt("Formula");
		pharmaceuticalCompanyId.setConverter(Integer.class);
		pharmaceuticalCompanyId.setInputPrompt("Company Id");

		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(name, formula, pharmaceuticalCompanyId, buttons);
	}

	public void setDrug(Drug drug, boolean insert) {
		this.drug = drug;
		this.insert = insert;
		BeanFieldGroup.bindFieldsUnbuffered(drug, this);

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
				drugDao.insert(drug);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {
				Notification.show("ADD FAILED", "Add with Invalid ID", Notification.Type.WARNING_MESSAGE);
			}
		} else
			try {
				drugDao.update(drug);
				myUI.updateList();
				setVisible(false);
			} catch (SQLIntegrityConstraintViolationException e) {
				Notification.show("UPDATE FAILED", "Update with Invalid ID", Notification.Type.WARNING_MESSAGE);
			}
	}

	private void delete() {
		drugDao.delete(drug.getDrugId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
