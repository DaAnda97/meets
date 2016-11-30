package de.meets.views;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

import de.meets.vaadin_archetype_application.Views;

public class Footer extends HorizontalLayout {
	Button impressum = new Button("Impressum");

	public Footer() {
		impressum.addClickListener(e -> {
			getUI().getNavigator().navigateTo(Views.IMPRESSUM.getView());
		});
		
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponents(impressum);
        this.setMargin(true);
        this.setSpacing(true);
	}

}
