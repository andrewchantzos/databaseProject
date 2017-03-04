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
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.DrugDAO;
import dao.PharmaceuticalCompanyDAO;
import dao.PharmacyDAO;
import dao.SellDAO;
import daoImpl.DrugDAOImpl;
import daoImpl.PharmaceuticalCompanyDAOImpl;
import daoImpl.PharmacyDAOImpl;
import daoImpl.SellDAOImpl;
import model.Drug;
import model.PharmaceuticalCompany;
import model.Pharmacy;
import model.Sell;
import ui.SellView;
import validators.CustomValidators;

public class SellInsertForm extends FormLayout {

	private static final long serialVersionUID = 1L;
	private TextField price = new TextField("Price");
	private ComboBox drugId = new ComboBox("Drug");
	private ComboBox pharmacyId = new ComboBox("Pharmacy");

	private Button save = new Button("Save");
	private Button delete = new Button("Delete");

	private SellDAO sellDao = new SellDAOImpl();
	private Sell sell;
	private SellView myUI;
	private FieldGroup fieldGroup;

	private PharmacyDAO pharmacyDao = new PharmacyDAOImpl();
	private DrugDAO drugDAO = new DrugDAOImpl();
	private PharmaceuticalCompanyDAO companyDao = new PharmaceuticalCompanyDAOImpl();

	private List<PharmaceuticalCompany> companyList;
	private List<Drug> drugList;
	private List<Pharmacy> pharmacyList;

	public SellInsertForm(SellView myUI) {
		this.myUI = myUI;

		fieldGroup = new FieldGroup();
		fieldGroup.bind(pharmacyId, pharmacyId);
		fieldGroup.bind(price, price);
		fieldGroup.bind(drugId, drugId);

		pharmacyId.addValidator(CustomValidators.idValidator());
		drugId.addValidator(CustomValidators.idValidator());
		price.addValidator(CustomValidators.priceValidator());

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


		price.setConverter(Integer.class);
		price.setInputPrompt("Price");
		drugId.setConverter(Integer.class);
		pharmacyId.setConverter(Integer.class);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);

		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());

		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(save, delete);

		buttons.setSpacing(true);
		addComponents(drugId, pharmacyId, price, buttons);

	}

	public void setSell(Sell sell, boolean insert) {
		this.sell = sell;
		BeanFieldGroup.bindFieldsUnbuffered(sell, this);

		// Show delete button only for persisted clients
		delete.setVisible(false);
		setVisible(true);
		price.selectAll();
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
			sellDao.insert(sell);
			myUI.updateList();
			setVisible(false);
		} catch (SQLIntegrityConstraintViolationException e) {
			Notification.show("ADD FAILED", "Entity with these IDs already exists", Notification.Type.WARNING_MESSAGE);
		}

	}

	private void delete() {
		sellDao.delete(sell.getPharmacyId(), sell.getDrugId());
		myUI.updateList();
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void init() {
		for (Field<?> field : fieldGroup.getFields())
			((AbstractField<String>) field).setValidationVisible(false);
	}
}
