package de.meets.gui.views;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

import de.meets.vaadin_archetype_application.MeetsUI;

public class Footer extends HorizontalLayout {
	Button impressum = new Button("Impressum");

	public Footer(MeetsUI meetsUI) {
		impressum.addClickListener(e -> {
			getUI().getNavigator().navigateTo(Impressum.NAME);
		});
		
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponents(impressum);
        this.setMargin(true);
        this.setSpacing(true);
	}

}
