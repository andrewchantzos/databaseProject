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

import dao.SellDAO;
import daoImpl.SellDAOImpl;
import form.SellForm;
import model.Sell;
import uiComponents.MyComponents;


@Theme("mytheme")
public class SellView extends VerticalLayout implements View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SellDAO sellDao = new SellDAOImpl();
	private Grid grid = new Grid();
	private SellForm form = new SellForm(this);
	private Navigator navigator;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SellView(Navigator navigator) {

		this.setNavigator(navigator);

        
		List<Sell> sells = sellDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Sell.class, sells));

		grid.setColumnOrder("drugId", "companyId", "pharmacyId", "price");

		CssLayout filtering = new CssLayout();

		HorizontalLayout main = new HorizontalLayout(grid, form);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);


		
		Button addNewSell = new Button("Add new sell");
		addNewSell.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewSell.addClickListener(e -> {
			grid.select(null);
			form.setSell(new Sell(), true);
		});



		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, addNewSell);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);

		setMargin(true);
		setSpacing(true);

		form.setVisible(false);
		
		
		// form management
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				form.setVisible(false);
			} else {
				Sell sell = (Sell) e.getSelected().iterator().next();
				form.setSell(sell, false);
			}
		});
	
	}

	public void updateList() {
		List<Sell> sells = sellDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Sell.class, sells));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Sell Table");
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

}

