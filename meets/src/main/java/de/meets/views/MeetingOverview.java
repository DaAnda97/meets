package de.meets.views;

import com.vaadin.navigator.View;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Category;
import de.meets.assets.Meeting;
import de.meets.assets.Member;

// Alle entsprechenden Meets anzeigen anzeigen
public class MeetingOverview extends VerticalLayout implements View{
	
	Member member;
	Meeting meeting;
	Category category;

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
