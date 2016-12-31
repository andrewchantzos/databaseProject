package ui;

import java.util.List;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import dao.PatientDAO;
import daoImpl.PatientDAOImpl;
import form.PatientForm;
import model.Patient;
import uiComponents.MyComponents;

@Theme("mytheme")
public class PatientView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PatientDAO patientDao = new PatientDAOImpl();
	private Grid grid = new Grid();
	private PatientForm form = new PatientForm(this);
	private Navigator navigator;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PatientView(Navigator navigator) {

		this.setNavigator(navigator);

        
		List<Patient> patients = patientDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Patient.class, patients));
		grid.setColumnOrder("patientId", "firstName", "lastName");

		CssLayout filtering = new CssLayout();

		HorizontalLayout main = new HorizontalLayout(grid, form);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);


		
		Button addNewPatient = new Button("Add new patient");
		addNewPatient.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewPatient.addClickListener(e -> {
			grid.select(null);
			form.setPatient(new Patient(), true);
		});



		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, addNewPatient);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);

		setMargin(true);
		setSpacing(true);

		form.setVisible(false);
		

		
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				form.setVisible(false);
			} else {
				Patient patient = (Patient) e.getSelected().iterator().next();
				form.setPatient(patient, false);
			}
		});
	
	}

	public void updateList() {
		List<Patient> patients = patientDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Patient.class, patients));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Patient Table");
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}
