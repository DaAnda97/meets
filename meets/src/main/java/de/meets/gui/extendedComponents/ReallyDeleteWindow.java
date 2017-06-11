package de.meets.gui.extendedComponents;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ReallyDeleteWindow extends Window {

	public ReallyDeleteWindow(String title, int id) {
		super(title);
		
		Label lInformation = new Label("Bist du dir sicher, dass du dein Meeting " + title + " löschen möchtest?");
		
		HorizontalLayout deleteLayout = new HorizontalLayout();
		deleteLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		deleteLayout.setSizeFull();
		Button bDelete = new Button("Ja");
		bDelete.addClickListener(e -> {
			
			close();
		});
		deleteLayout.addComponent(bDelete);

		HorizontalLayout cancelLayout = new HorizontalLayout();
		cancelLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		cancelLayout.setSizeFull();
		Button bCancel = new Button("Abbrechen");
		bCancel.addClickListener(e -> {
			close();
		});
		cancelLayout.addComponent(bCancel);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		buttonLayout.setWidth(75, Unit.PERCENTAGE);
		buttonLayout.addComponents(deleteLayout, cancelLayout);
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeUndefined();
		layout.addComponents(lInformation, buttonLayout);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		layout.setMargin(true);
		this.setContent(layout);
		
		
		center();
		this.setWidth(25, Unit.PERCENTAGE);
		this.setHeight(20, Unit.PERCENTAGE);
	}
	
}
