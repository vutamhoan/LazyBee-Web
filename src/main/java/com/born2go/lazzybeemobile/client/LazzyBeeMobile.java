package com.born2go.lazzybeemobile.client;

import com.born2go.lazzybee.gdatabase.client.rpc.DataService;
import com.born2go.lazzybee.gdatabase.client.rpc.DataServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class LazzyBeeMobile implements EntryPoint {

	public static DataServiceAsync data_service = GWT.create(DataService.class);
	public static NoticeBox noticeBox = new NoticeBox("");

	@Override
	public void onModuleLoad() {
		// add MdictionaryView for mdictionnay.html and show it
		if (RootPanel.get("gwt_header_mdic") != null) {
			MDictionaryView m = new MDictionaryView();
		} else if (RootPanel.get("gwt_contentMTestTool") != null) {
			MTestTool testTool = new MTestTool();
			RootPanel.get("gwt_contentMTestTool").add(testTool);
		}
	}

}