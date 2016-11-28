package de.meets.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

import de.meets.vaadin_archetype_application.Views;

public class Footer extends HorizontalLayout implements View{
	Button impressum = new Button("Impressum");

	@Override
	public void enter(ViewChangeEvent event) {
		impressum.addClickListener(e -> {
			getUI().getNavigator().navigateTo(Views.IMPRESSUM.getView());
		});
		
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponents(impressum);
        this.setMargin(true);
        this.setSpacing(true);
	}

}
