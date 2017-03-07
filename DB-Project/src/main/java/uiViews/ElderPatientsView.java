package uiViews;

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

import model.Patient;
import sqlQueries.ViewQueries;
import uiComponents.MyComponents;

@Theme("mytheme")
public class ElderPatientsView extends VerticalLayout implements View {


	private static final long serialVersionUID = 1L;
	private ViewQueries queries = new ViewQueries();
	private Grid grid = new Grid();
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ElderPatientsView(Navigator navigator) {

		this.setNavigator(navigator);

		List<Patient> patients = queries.findElderPatients();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Patient.class, patients));
		
		grid.setColumnOrder("patientId", "firstName", "lastName");

		filterText.setInputPrompt("Search");
		
		
		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(Patient.class, queries.findElderPatients(e.getText())));
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
		
		main.setHeight("15.5cm");


	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateList() {
		List<Patient> patients = queries.findElderPatients();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer(Patient.class, patients));
	
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Elder Patients Table");
		updateList();
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
