package ui;

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

import queryModels.DoctorWithOldPatients;
import queryModels.PharmacyWithAllDrugsInCity;
import sqlQueries.Queries;
import uiComponents.MyComponents;

@Theme("mytheme")
public class DoctorsWithOldPatientsView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Queries queries = new Queries();
	private Grid grid = new Grid();
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DoctorsWithOldPatientsView(Navigator navigator) {

		this.setNavigator(navigator);

		List<DoctorWithOldPatients> doctorList = queries.doctorsWithOldPatients();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(DoctorWithOldPatients.class, doctorList));
		grid.setColumnOrder("doctorId", "firstName", "lastName");

		
		filterText.setInputPrompt("Search");
		
		
		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(DoctorWithOldPatients.class, queries.doctorsWithOldPatientsFilter(e.getText())));
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

	public void updateList() {
		List<DoctorWithOldPatients> doctorList = queries.doctorsWithOldPatients();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer(DoctorWithOldPatients.class, doctorList));
	
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Doctor with old Patients Table");
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
