
package uiQueries;

import java.util.List;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import formSelect.SelectDrugTown;
import model.Pharmacy;
import sqlQueries.Queries;
import uiComponents.MyComponents;

@Theme("mytheme")
public class PharmaciesWithDrugInCityView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Queries queries = new Queries();
	private Grid grid = new Grid();
	private Navigator navigator;
	private int drugId;
	private String town;
	private List<Pharmacy> pharmacies;

	private TextField filterText = new TextField();
	private SelectDrugTown form = new SelectDrugTown(this);

	private HorizontalLayout toolbar;


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PharmaciesWithDrugInCityView(Navigator navigator) {

		this.setNavigator(navigator);

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Pharmacy.class, pharmacies));

		filterText.setInputPrompt("Search");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(Pharmacy.class, queries.pharmaciesWithDrugInCityFilter(drugId, town, e.getText())));
		});
		CssLayout filtering = new CssLayout();

		Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
		clearFilterTextBtn.addClickListener(e -> {
			filterText.clear();
			updateList(drugId, town);
		});
		filtering.addComponents(filterText, clearFilterTextBtn);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		VerticalLayout main = new VerticalLayout(grid, form);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		setMargin(true);
		setSpacing(true);

		Button home = MyComponents.homeButton(navigator);
		toolbar = new HorizontalLayout(home, filtering);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);
		
		main.setHeight("15.5cm");

	}

	public void updateList(int id, String town) {
		List<Pharmacy> pharmacies = queries.pharmaciesWithDrugInCity(id, town);

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Pharmacy.class, pharmacies));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		form.init();
		grid.setVisible(false);
		toolbar.setVisible(false);
		form.setVisible(true);
	}
	
	public Queries getQueries() {
		return queries;
	}

	public void setQueries(Queries queries) {
		this.queries = queries;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

	public int getDrugId() {
		return drugId;
	}

	public void setDrugId(int drugId) {
		this.drugId = drugId;
	}

	public List<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(List<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}

	public TextField getFilterText() {
		return filterText;
	}

	public void setFilterText(TextField filterText) {
		this.filterText = filterText;
	}

	public SelectDrugTown getForm() {
		return form;
	}

	public void setForm(SelectDrugTown form) {
		this.form = form;
	}

	public HorizontalLayout getToolbar() {
		return toolbar;
	}

	public void setToolbar(HorizontalLayout toolbar) {
		this.toolbar = toolbar;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
}