package de.meets.gui.views;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Category;
import de.meets.assets.Location;
import de.meets.assets.Meeting;
import de.meets.assets.Member;
import de.meets.gui.MeetsView;
import de.meets.gui.ViewName;
import de.meets.gui.extendedComponents.SucessPopup;
import de.meets.services.GeoData;
import de.meets.services.MaxValueValidator;
import de.meets.services.TimeValidator;
import de.meets.vaadin_archetype_application.MeetsUI;

// Informationen zu einem Meet
public class CreateMeeting extends MeetsView {
	
	private TextField tfTitle = new TextField("Titel");
	private TextArea tfDescription = new TextArea("Beschreibung");
	private TextField tfCategory = new TextField("Kategorie");
	private DateField tfDate = new DateField("Datum");
	private TextField tfTime = new TextField("Uhrzeit");
	private TextField tfLocation = new TextField("Veranstaltungsort");
	private TextField tfMaxMembers = new TextField("Teilnehmeranzahl");

	private List<AbstractField> inputFields = new ArrayList<AbstractField>();

	Meeting passedMeeting;
	boolean meetingWasPassed;

	public CreateMeeting(ViewName viewName, MeetsUI meetsUI) {
		super(viewName, meetsUI);

		inputFields.add(tfCategory);
		inputFields.add(tfDescription);
		inputFields.add(tfLocation);
		inputFields.add(tfMaxMembers);
		inputFields.add(tfTime);
		inputFields.add(tfTitle);

		for (AbstractField<Object> each : inputFields) {
			each.setWidth(25, Unit.EM);
		}

		// ---------------------- InputLayout --------------------------
		VerticalLayout inputLayout = new VerticalLayout();
		inputLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		inputLayout.setSizeFull();
		inputLayout.setMargin(true);
		inputLayout.setSpacing(true);

		tfTitle.setIcon(FontAwesome.TICKET);
		tfTitle.setRequired(true);
		inputLayout.addComponent(tfTitle);

		tfDescription.setIcon(FontAwesome.BOOK);
		tfDescription.setRequired(false);
		tfDescription.setRows(8);
		inputLayout.addComponent(tfDescription);

		tfCategory.setIcon(FontAwesome.BOOKMARK);
		tfCategory.setRequired(true);
		inputLayout.addComponent(tfCategory);

		// ---------------- Date and Time -------------------
		HorizontalLayout dateLayout = new HorizontalLayout();
		dateLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		dateLayout.setSizeFull();
		tfDate.setIcon(FontAwesome.CALENDAR);
		tfDate.setRequired(true);
		tfDate.setDateFormat("dd-MM-yyyy");
		dateLayout.addComponent(tfDate);
		
		HorizontalLayout timeLayout = new HorizontalLayout();
		timeLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		timeLayout.setSizeFull();
		tfTime.setIcon(FontAwesome.CLOCK_O);
		tfTime.setWidth(5, Unit.EM);
		tfTime.setRequired(true);
		tfTime.addValidator(new TimeValidator("Keine korrekte Zeitangabe"));
		timeLayout.addComponent(tfTime);

		HorizontalLayout dateAndTimeLayout = new HorizontalLayout();
		dateAndTimeLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		dateAndTimeLayout.setWidth(15, Unit.PERCENTAGE);
		dateAndTimeLayout.addComponents(dateLayout, timeLayout);
		// ---------------- End Date and Time ---------------
		inputLayout.addComponent(dateAndTimeLayout);

		tfLocation.setIcon(FontAwesome.LOCATION_ARROW);
		tfLocation.setRequired(true);
		inputLayout.addComponent(tfLocation);

		tfMaxMembers.setIcon(FontAwesome.USER_PLUS);
		tfMaxMembers.setRequired(true);
		tfMaxMembers.addValidator(new MaxValueValidator("Keine Zahl zwischen 1 und 1 Mrd"));
		inputLayout.addComponent(tfMaxMembers);

		// --------------------- ButtonLayout ----------------------
		HorizontalLayout saveLayout = new HorizontalLayout();
		saveLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		saveLayout.setSizeFull();
		Button bSave = new Button("Speichern");
		bSave.addClickListener(e -> {
			save();
		});
		saveLayout.addComponent(bSave);

		HorizontalLayout cancelLayout = new HorizontalLayout();
		cancelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		cancelLayout.setSizeFull();
		Button bCancel = new Button("Abbrechen");
		bCancel.addClickListener(e -> {
			getUI().getNavigator().navigateTo(ViewName.OVERVIEW.toString());
		});
		cancelLayout.addComponent(bCancel);
		
		HorizontalLayout deleteLayout = new HorizontalLayout();
		deleteLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		deleteLayout.setSizeFull();
		Button bDelete = new Button("Löschen");
		bDelete.addClickListener(e -> {
			// TODO delete
		});
		deleteLayout.addComponent(bDelete);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		buttonLayout.setWidth(25, Unit.PERCENTAGE);
		buttonLayout.addComponents(saveLayout, cancelLayout, deleteLayout);

		// -------------------------- MainLayout -------------------------------

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		mainLayout.setSizeFull();
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.addComponents(inputLayout, buttonLayout);
		setCompositionRoot(mainLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		for (AbstractField<String> each : inputFields) {
			each.setValue("");
		}
		tfDate.setValue(new Date());
		
		tfTitle.focus();
		
		if (event.getParameters() == null | event.getParameters().isEmpty()) {
			meetingWasPassed = false;
		} else {
			meetingWasPassed = true;
			String param = event.getParameters().split("/")[0]; // only first
																// param
			System.out.println("############ MOIN I BIMS, DER BARAMETER " + event.getParameters());
			try {
				int meetingID = Integer.parseInt(param);
				passedMeeting = getMeetingManager().get(meetingID);
			} catch (Exception e) {
				Label errorMessage = new Label("Ungültier Parameter! Hör auf in der Adresszeile herumzuspielen!");
				setCompositionRoot(errorMessage);
				return;
			}

			tfTitle.setValue(passedMeeting.getTitle());
			tfDescription.setValue(passedMeeting.getDescription());
			tfCategory.setValue(passedMeeting.getTitle());
			tfDate.setValue(passedMeeting.getDate());
			tfTime.setValue(passedMeeting.getTime().toString());
			tfLocation.setValue(passedMeeting.getLocation().getCity());
			tfMaxMembers.setValue(passedMeeting.getMaxMembers() + "");

		}
	}

	private void save() {
		// test, weather the inputs are valid
		boolean notValid = false;
		for (AbstractField<String> each : inputFields) {
			if (each.isRequired() & each.getValue().equals("")) {
				each.setComponentError(new UserError("Dieses Feld ist ein Pflichtfeld"));
				notValid = true;
			}
			if (!each.isValid()) {
				notValid = true;
			}
		}
		if (notValid){
			return;
		}
		
		if (meetingWasPassed) {
			updateMeeting();
		} else {
			createNewMeeting();
		}
		
	}

	private void createNewMeeting() {
		Member creater = getRegistratedMember();
		Location createdLocation = getRegistratedMember().getPosition();

		// setValues
		String title = tfTitle.getValue();
		String description = tfDescription.getValue();
		Date date = tfDate.getValue();

		Category category = new Category(tfCategory.getValue().toUpperCase());
		if (getCategoryManager().get(category.getTitle()) == null) {
			getCategoryManager().add(category);
			System.out.println("Instert into DB: " + category);
		}
		category = getCategoryManager().get(tfCategory.getValue());

		String hourAndMinute[] = tfTime.getValue().split(":");
		Time time = new Time(Integer.parseInt(hourAndMinute[0]), Integer.parseInt(hourAndMinute[1]), 0);

		Location location;
		try {
			GeoData geoData = new GeoData();
			location = geoData.getCoordinatesFromAdress(tfLocation.getValue());
		} catch (Exception e) {
			tfLocation.setComponentError(new UserError("Deine Adresse konnte nicht gefinden werden."));
			return;
		}
		if (getLocationManager().get(location.getCity()) == null) {
			getLocationManager().add(location);
			System.out.println("Instert into DB: " + location);
		}
		location = getLocationManager().get(location.getCity());

		int maxMembers = Integer.parseInt(tfMaxMembers.getValue());

		Meeting newMeeting = new Meeting(title, description, category, date, time, location, creater, maxMembers, createdLocation);
		getMeetingManager().add(newMeeting);
		
		getUI().addWindow(new SucessPopup(newMeeting.getTitle(), "erstellt"));
	}

	private void updateMeeting() {
		passedMeeting.setTitle(tfTitle.getValue());
		passedMeeting.setDescription(tfDescription.getValue());
		passedMeeting.setDate(tfDate.getValue());

		String hourAndMinute[] = tfTime.getValue().split(":");
		Time time = new Time(Integer.parseInt(hourAndMinute[0]), Integer.parseInt(hourAndMinute[1]), 0);
		passedMeeting.setTime(time);

		Category category = new Category(tfCategory.getValue().toUpperCase());
		if (getCategoryManager().get(category.getTitle()) == null) {
			getCategoryManager().add(category);
			System.out.println("Instert into DB: " + category);
		}
		category = getCategoryManager().get(tfCategory.getValue());
		passedMeeting.setCategory(category);

		Location location;
		if (passedMeeting.getLocation().getCity().equals(tfLocation.getValue())) {
			location = passedMeeting.getLocation();
		} else {
			try {
				GeoData geoData = new GeoData();
				location = geoData.getCoordinatesFromAdress(tfLocation.getValue());
			} catch (Exception e) {
				tfLocation.setComponentError(new UserError("Deine Adresse konnte nicht gefinden werden."));
				return;
			}
		}
		if (getLocationManager().get(location.getCity()) == null) {
			getLocationManager().add(location);
			System.out.println("Instert into DB: " + location);
		}
		location = getLocationManager().get(location.getCity());
		passedMeeting.setLocation(location);
		
		getMeetingManager().update(passedMeeting);
		getUI().addWindow(new SucessPopup(passedMeeting.getTitle(), "aktualisiert"));
	}

}
