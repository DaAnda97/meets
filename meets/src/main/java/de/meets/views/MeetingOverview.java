package de.meets.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Category;
import de.meets.assets.Meeting;
import de.meets.assets.Member;
import de.meets.vaadin_archetype_application.MeetsUI;

// Alle entsprechenden Meets anzeigen 
public class MeetingOverview extends VerticalLayout implements View{
	
//	Member member = MeetsUI.getRegistratedMember();
//	Meeting meeting;
//	Category category;

	@Override
	public void enter(ViewChangeEvent event) {
		for(int i=0; i<20; i++){
			this.addComponent(new Label(""));
			this.addComponent(new Label("HIER ERSCHEINEN SPÄTER DIE PASSENDEN MEETS"));
		}
	}

}
