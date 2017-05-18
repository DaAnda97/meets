package de.meets.gui.views;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.vaadin_archetype_application.MeetsUI;

// Alle entsprechenden Meets anzeigen 
public class MeetingOverview extends MeetsView {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7973265153857834807L;

	public MeetingOverview(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		for(int i = 0; i < 10; i++){
			verticalLayout.addComponent(new Label(""));
			verticalLayout.addComponent(new Label("HIER ERSCHEINEN SPÄTER DIE PASSENDEN MEETS"));
		}
		
		verticalLayout.setMargin(true);
		verticalLayout.setSpacing(true);
		
		setCompositionRoot(verticalLayout);
	}

}
