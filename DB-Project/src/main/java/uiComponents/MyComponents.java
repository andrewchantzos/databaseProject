package uiComponents;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

public class MyComponents {

	
	
	public static Button homeButton(Navigator navigator) {
				
		Button home = new Button("Home", FontAwesome.HOME);

		home.addClickListener(e -> {
			navigator.navigateTo("StartingView");
		});
		return home;
	}

}
