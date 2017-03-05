package uiTables;

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

import dao.SellDAO;
import daoImpl.SellDAOImpl;
import form.SellInsertForm;
import form.SellUpdateForm;
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
	private SellInsertForm insertForm = new SellInsertForm(this);
	private SellUpdateForm updateForm = new SellUpdateForm(this);
	private Navigator navigator;
	private TextField filterText = new TextField();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SellView(Navigator navigator) {

		this.setNavigator(navigator);

		List<Sell> sells = sellDao.findAll();

		// setup grid
		grid.setContainerDataSource(new BeanItemContainer(Sell.class, sells));

		grid.setColumnOrder("drugId", "pharmacyId", "price");

		filterText.setInputPrompt("Search");

		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(Sell.class, sellDao.findAllFilter(e.getText())));
		});
		CssLayout filtering = new CssLayout();

		Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
		clearFilterTextBtn.addClickListener(e -> {
			filterText.clear();
			updateList();
		});
		filtering.addComponents(filterText, clearFilterTextBtn);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		HorizontalLayout main = new HorizontalLayout(grid, insertForm, updateForm);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		main.setSpacing(true);
		main.setSizeFull();
		main.setExpandRatio(grid, 1);

		Button addNewSell = new Button("Add new sell");
		addNewSell.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addNewSell.addClickListener(e -> {
			if (insertForm.isVisible()) {
				insertForm.setVisible(false);
			} else {
				grid.select(null);
				insertForm.setSell(new Sell(), true);
				insertForm.init();
				updateForm.init();
			}
		});

		Button home = MyComponents.homeButton(navigator);
		HorizontalLayout toolbar = new HorizontalLayout(home, filtering, addNewSell);
		toolbar.setSpacing(true);
		addComponents(toolbar, main);



	}

	public void updateList() {
		List<Sell> sells = sellDao.findAll();

		// Set list
		grid.setContainerDataSource(new BeanItemContainer<>(Sell.class, sells));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to Sell Table");
		updateList();

		insertForm.init();
		updateForm.init();
		
		setMargin(true);
		setSpacing(true);

		insertForm.setVisible(false);
		updateForm.setVisible(false);

		// form management
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				updateForm.setVisible(false);
			} else {
				if (insertForm.isVisible())
					insertForm.setVisible(false);
				Sell sell = (Sell) e.getSelected().iterator().next();
				updateForm.setSell(sell);
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
