
package formSelect;

import java.util.List;

import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import dao.DrugDAO;
import daoImpl.DrugDAOImpl;
import model.Drug;
import sqlQueries.Queries;
import uiQueries.PharmaciesWithDrugInCityView;

public class SelectDrugTown extends FormLayout {

	private static final long serialVersionUID = 1L;
	private ComboBox drugId = new ComboBox("Drug");
	private ComboBox townBox = new ComboBox("Town");

	private Button click = new Button("Click");

	private Queries queries = new Queries();
	private PharmaciesWithDrugInCityView myUI;
	private DrugDAO drugDAO = new DrugDAOImpl();
	private List<Drug> drugList;
	
	public SelectDrugTown(PharmaciesWithDrugInCityView myUI) {
		this.myUI = myUI;
		click.setStyleName(ValoTheme.BUTTON_PRIMARY);
		click.setClickShortcut(KeyCode.ENTER);

		click.addClickListener(e -> click());

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
		
		townBox.addFocusListener(new FocusListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FocusEvent event) {
				List<String>  towns = queries.findTowns();
				for (String town : towns) {
					townBox.addItem(town);
				}
			}
		});
		
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(click);

		buttons.setSpacing(true);
		addComponents(drugId, townBox, buttons);
	}


	private void click() {
		int id = (int) drugId.getValue();
		String town = (String) townBox.getValue();
		myUI.setDrugId(id);
		myUI.setTown(town);
		myUI.updateList(id,town);
		myUI.setVisible(true);
		setVisible(false);
		myUI.getGrid().setVisible(true);
		myUI.getToolbar().setVisible(true);
	}
	
	public void init() {
		drugId.setValue(null);
		townBox.setValue(null);
	}

}
