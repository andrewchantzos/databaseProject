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

import dao.PatientUpdatableDAO;
import daoImpl.PatientsUpdatableDAOImpl;
import form.PatientUpdatableForm;
import modelQueries.PatientsUpdatable;
import uiComponents.MyComponents;

@Theme("mytheme")
public class PatientsUpdatableView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PatientUpdatableDAO patientDao = new PatientsUpdatableDAOImpl();
	private Grid grid = new Grid();
	private PatientUpdatableForm form = new PatientUpdatableForm(this);
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PatientsUpdatableView(Navigator navigator) {

		this.setNavigator(navigator);
		List<PatientsUpdatable> patients = patientDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(PatientsUpdatable.class, patients));
		grid.removeColumn("patientId");
		filterText.setInputPrompt("Search");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(PatientsUpdatable.class, patientDao.findAllFilter(e.getText())));
		});
		CssLayout filtering = new CssLayout();

		Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
		clearFilterTextBtn.addClickListener(e -> {
			filterText.clear();
			updateList();
		});
		filtering.addComponents(filterText, clearFilterTextBtn);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		HorizontalLayout main = new HorizontalLayout(grid, form);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		Button addNewPatientsUpdatable = new Button("Add new patient");
		addNewPatientsUpdatable.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewPatientsUpdatable.addClickListener(e -> {
			if (form.isVisible()) {
				form.setVisible(false);
			} else {
				grid.select(null);
				form.setPatientUpdatable(new PatientsUpdatable(), true);
				form.init();
			}
		});

		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, filtering, addNewPatientsUpdatable);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);
	}

	public void updateList() {
		List<PatientsUpdatable> patients = patientDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(PatientsUpdatable.class, patients));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to PatientsUpdatable Table");
		updateList();

		form.init();
		
		setMargin(true);
		setSpacing(true);

		form.setVisible(false);

		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				form.setVisible(false);
			} else {
				PatientsUpdatable patient = (PatientsUpdatable) e.getSelected().iterator().next();
				form.setPatientUpdatable(patient, false);
			}
		});

	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
