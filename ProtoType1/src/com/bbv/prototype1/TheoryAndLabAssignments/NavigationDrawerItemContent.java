package com.bbv.prototype1.TheoryAndLabAssignments;
/**
 * A class for storing the Content of the items that will be shown in the 
 * NavigationDrawer
 * @author Andre
 *
 */
public class NavigationDrawerItemContent {

	private String[] _list;
	private String _Title;

	public String[] getStringList() {
		return _list;
	}

	public void setStringList(String[] list) {
		_list = list;
	}

	public String getTitle() {
		return _Title;
	}

	public void setTitle(String Title) {
		_Title = Title;
	}

}
