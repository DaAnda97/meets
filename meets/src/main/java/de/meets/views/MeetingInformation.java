package de.meets.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Category;
import de.meets.assets.Meeting;
import de.meets.assets.Member;
import de.meets.vaadin_archetype_application.MeetsUI;

// Informationen zu einem Meet
public class MeetingInformation extends VerticalLayout implements View{

	Member member = MeetsUI.getRegistratedMember();
//	Meeting meeting;
//	Category category;

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
