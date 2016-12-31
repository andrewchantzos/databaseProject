package ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class StartingView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final String MAINVIEW = "main";

	public StartingView(Navigator navigator) {

		MenuBar menu = new MenuBar();


		// Define a common menu command for all the menu items.
		@SuppressWarnings("serial")
		MenuBar.Command mycommand = new MenuBar.Command() {
			public void menuSelected(MenuItem selectedItem) {
				String view = selectedItem.getText() + "View";
				navigator.navigateTo(view);
			}
		};

		MenuItem tables = menu.addItem("Tables", null);
		tables.addItem("Doctor", mycommand);
		tables.addItem("Patient", mycommand);
		tables.addItem("Prescription", mycommand);
		tables.addItem("Contract", mycommand);
		tables.addItem("Drug", mycommand);
		tables.addItem("Pharmacy", mycommand);
		tables.addItem("Sell", mycommand);
		tables.addItem("Company", mycommand);

		MenuItem queries = menu.addItem("Queries", null);

		addComponent(menu);
		setComponentAlignment(menu, Alignment.MIDDLE_CENTER);


	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
