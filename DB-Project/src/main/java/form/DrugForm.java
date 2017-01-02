package form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import dao.DrugDAO;
import daoImpl.DrugDAOImpl;
import model.Drug;
import ui.DrugView;

public class DrugForm  extends FormLayout {
	
	/**
	 * 	private int drugId;
	
	private String name;
	
	private String formula;
	
	private int pharmaceuticalCompanyId;
	 */
	private static final long serialVersionUID = 1L;
	private TextField name = new TextField("Name");
	private TextArea formula = new TextArea("Formula");
	private TextField pharmaceuticalCompanyId = new TextField("Company Id");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private boolean insert = false;
	
	private DrugDAO drugDao = new DrugDAOImpl();
	private Drug drug;
	private DrugView myUI;
	
	
	public DrugForm(DrugView myUI) {
		this.myUI = myUI;

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
	
	private void save() {
		if (insert) {
			delete.setVisible(false);
			drugDao.insert(drug);
		}
		else
			drugDao.update(drug);
		myUI.updateList();
		setVisible(false);
	}
	
	private void delete() {
		drugDao.delete(drug.getDrugId());
		myUI.updateList();
		setVisible(false);
	}
}
