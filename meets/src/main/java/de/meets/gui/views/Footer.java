package de.meets.gui.views;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

import de.meets.gui.ViewName;
import de.meets.vaadin_archetype_application.MeetsUI;

@Theme("runo")
public class Footer extends VerticalLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1641423676622745180L;

	public Footer(MeetsUI meetsUI) {
		Button btnImpressum = new Button("Impressum");
		btnImpressum.setStyleName(Runo.BUTTON_LINK);
		
		btnImpressum.addClickListener(e -> {
			getUI().getNavigator().navigateTo(ViewName.IMPRESSUM.toString());
		});
		
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponent(btnImpressum);
        this.setMargin(true);
        this.setSpacing(true);
	}

}
