package de.meets.gui.views;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Meeting;
import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.vaadin_archetype_application.MeetsUI;

// Informationen zu einem Meet
public class CreateMeeting extends MeetsView {
	private Meeting meeting;
	
	private TextField tfTitle = new TextField("Titel");
	private TextField tfDescription = new TextField("Beschreibung");
	private TextField tfCategory = new TextField("Kategorie");
	private TextField tfDate = new TextField("Datum");
	private TextField tfTime = new TextField("Uhrzeit");
	private TextField tfLocation = new TextField("Veranstaltungsort");
	private TextField tfCreater = new TextField("Veranstalter");
	private TextField tfMaxMembers = new TextField("Höchstanzahl Teilnehmer");
	
	
	public CreateMeeting(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);
		
		
		
		
		
		
		
		Panel componentPanel = new Panel();
		VerticalLayout componentPanelLayout = new VerticalLayout();
		componentPanelLayout.addComponents();
		componentPanelLayout.setMargin(true);
		componentPanelLayout.setSpacing(true);
		componentPanelLayout.setSizeFull();
		componentPanel.setContent(componentPanelLayout);
		setCompositionRoot(componentPanel);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null){
			String param = event.getParameters().split(";")[0]; // get only the first parameter
			try {
				int meetingID = Integer.parseInt(param);
				meeting = getMeetingManager().get(meetingID);
			} catch (Exception e) {
				Label errorMessage = new Label("Ungültier Parameter! Hör auf in der Adresszeile herumzuspielen!");
				setCompositionRoot(errorMessage);
				return;
			}
		} else {
			meeting = new Meeting();
		}
		
		
	}
	
	
}
