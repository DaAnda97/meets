package de.meets.gui.views;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Meeting;
import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.services.TimeValidator;
import de.meets.vaadin_archetype_application.MeetsUI;

// Informationen zu einem Meet
public class CreateMeeting extends MeetsView {
	private Meeting meeting;
	
	private TextField tfTitle = new TextField("Titel");
	private TextField tfDescription = new TextField("Beschreibung");
	private TextField tfCategory = new TextField("Kategorie");
	private DateField tfDate = new DateField("Datum");
	private TextField tfTime = new TextField("Uhrzeit");
	private TextField tfLocation = new TextField("Veranstaltungsort");
	private TextField tfMaxMembers = new TextField("Höchstanzahl Teilnehmer");
	
	
	public CreateMeeting(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);
		
		Panel componentPanel = new Panel();
		VerticalLayout componentPanelLayout = new VerticalLayout();
		
		tfTitle.setIcon(FontAwesome.TICKET);
		tfTitle.setRequired(true);
		componentPanelLayout.addComponent(tfTitle);
		
		tfDescription.setIcon(FontAwesome.BOOK);
		tfDescription.setRequired(true);
		componentPanelLayout.addComponent(tfDescription);
		
		tfCategory.setIcon(FontAwesome.BOOKMARK);
		tfCategory.setRequired(true);
		componentPanelLayout.addComponent(tfCategory);
		
		tfDate.setRequired(true);
		tfDate.setDateFormat("dd-MM-yyyy");
		componentPanelLayout.addComponent(tfDate);
		
		tfTime.setIcon(FontAwesome.CLOCK_O);
		tfTime.setRequired(true);
		tfTime.addValidator(new TimeValidator("Keine korrekte Zeitangabe"));
		componentPanelLayout.addComponent(tfTime);
		
		tfLocation.setIcon(FontAwesome.LOCATION_ARROW);
		tfLocation.setRequired(true);
		componentPanelLayout.addComponent(tfLocation);
		
		tfMaxMembers.setIcon(FontAwesome.USER_PLUS);
		tfMaxMembers.setRequired(true);
		componentPanelLayout.addComponent(tfMaxMembers);
		
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
