package form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.PharmaceuticalCompanyDAO;
import daoImpl.PharmaceuticalCompanyDAOImpl;
import model.PharmaceuticalCompany;
import ui.CompanyView;

public class CompanyForm extends FormLayout{
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
	
	
	public CompanyForm(CompanyView myUI) {
		this.myUI = myUI;

		// Set input prompts
		//phone.setConverter(Long.class);
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
	
	private void save() {
		if (insert) {
			delete.setVisible(false);
			companyDao.insert(company);
		}
		else
			companyDao.update(company);
		myUI.updateList();
		setVisible(false);
	}
	
	private void delete() {
		companyDao.delete(company.getPharmaceuticalCompanyId());
		myUI.updateList();
		setVisible(false);
	}
}
