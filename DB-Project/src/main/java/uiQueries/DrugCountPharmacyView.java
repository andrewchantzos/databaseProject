
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import modelQueries.DrugCountPharmacy;
import sqlQueries.Queries;
import uiComponents.MyComponents;

@Theme("mytheme")
public class DrugCountPharmacyView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Queries queries = new Queries();
	private Grid grid = new Grid();
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DrugCountPharmacyView(Navigator navigator) {

		this.setNavigator(navigator);

		List<DrugCountPharmacy> pharmaciesList = queries.drugCountOfPharmacy();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(DrugCountPharmacy.class, pharmaciesList));
		grid.setColumnOrder("pharmacyId", "numberOfDrugs");

		
		filterText.setInputPrompt("Search");
		
		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(DrugCountPharmacy.class, queries.drugCountOfPharmacyFilter(e.getText())));
		});
		
		CssLayout filtering = new CssLayout();
		
		
		Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
		clearFilterTextBtn.addClickListener(e -> {
			filterText.clear();
			updateList();
		});
		filtering.addComponents(filterText, clearFilterTextBtn);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);


		HorizontalLayout main = new HorizontalLayout(grid);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);
		
		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, filtering);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);

		setMargin(true);
		setSpacing(true);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateList() {
		List<DrugCountPharmacy> pharmaciesList = queries.drugCountOfPharmacy();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer(DrugCountPharmacy.class, pharmaciesList));
	
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Drug Count of Pharmacy Table");
		updateList();

	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
