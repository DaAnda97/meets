package de.meets.vaadin_archetype_application;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

import de.meets.assets.Category;
import de.meets.assets.Meeting;
import de.meets.assets.Member;
import de.meets.djbc.SQLDatabaseAgent;

// Informationen zu einem Meet
public class MeetingInformation extends VerticalLayout implements View{
	SQLDatabaseAgent sqlDatabaseAgent = NavigatorUI.SQL_DATABASE_AGENT;
	Member member;
	Meeting meeting;
	Category category;

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
