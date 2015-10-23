package com.born2go.lazzybeemobile.client;

import java.util.ArrayList;
import java.util.List;

import com.born2go.lazzybee.gdatabase.client.rpc.DataService;
import com.born2go.lazzybee.gdatabase.client.rpc.DataServiceAsync;
import com.born2go.lazzybee.gdatabase.shared.Voca;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.input.MTextBox;

/*
 * MDictionaryView is ui gwt for page mdictionay.html
 * function: search q, show answer to user
 */
public class MDictionaryView extends Widget {
	private MTextBox txtSeach;

	private Button btSearch;
	public final DataServiceAsync dataService = GWT.create(DataService.class);

	public static class DefiContainer {
		List<String> types = new ArrayList<String>();
		String txbMeaning_id;
		String txbExplain_id;
		String txbExam_id;
	}

	public MDictionaryView() {
		designView();
		historyTokenHandler();

	}

	/**
	 * add button gwt search for mdictionary.html add textbox gwt input search
	 * for mdictionary.htlm
	 */
	int divHeight;
	int heightMdic_intro;

	private void designView() {

		// add textbox input search by element id
		txtSeach = new MTextBox();
		txtSeach.getElement().setId("txt_valueSearch");
		txtSeach.setPlaceHolder("Nhập từ muốn tìm...");
		RootPanel.get("inputsearch").add(txtSeach);
		// add button search by element id

		btSearch = new Button();
		btSearch.getElement().setClassName("fa fa-search");
		RootPanel.get("btsearch").add(btSearch);

		txtSeach.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event_) {
				boolean enterPressed = KeyCodes.KEY_ENTER == event_
						.getNativeEvent().getKeyCode();

				if (enterPressed) {
					if (!txtSeach.getText().equals(""))
						Window.Location.assign("/mvdict/#" + txtSeach.getText());
				}
			}
		});
		btSearch.addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				if (!txtSeach.getText().equals(""))
					Window.Location.assign("/mvdict/#" + txtSeach.getText());

			}
		});

	}

	void historyTokenHandler() {
		String path = Window.Location.getPath();
		if (path.contains("mvdict")) {
			// -----
			if (!History.getToken().isEmpty()) {
				DOM.getElementById("mdic_introduction").setAttribute("style",
						"display:none");
				final String history_token = History.getToken();
				txtSeach.setText(history_token);
				searchVoca(history_token);
			} else
				DOM.getElementById("mdic_introduction").setAttribute("style",
						"display:block");

		} else if (path.contains("mtest")) {
			if (RootPanel.get("gwt_contentMTestTool") != null) {
				MTestTool testTool = new MTestTool();
				RootPanel.get("gwt_contentMTestTool").add(testTool);
			}

		}

		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				Window.Location.reload();
			}
		});
	}

	/*
	 * searchVoca in database
	 */
	private void searchVoca(String q) {

		DOM.getElementById("mdic_introduction").setAttribute("style",
				"display:none");
		dataService.findVoca(q, new AsyncCallback<Voca>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Voca result) {
				DOM.getElementById("mdic_introduction").setAttribute("style",
						"display:none");
				if (result != null) {
					RootPanel.get("gwt_contentMdic").add(
							new MVocaView().setVoca(result));
					RootPanel.get("notfoundVoca").clear();
				} else
					notfoundVoca();

			}
		});

	}

	/*
	 * when do not find any question in data, show notification for user
	 */
	private void notfoundVoca() {
		RootPanel.get("notfoundVoca").clear();
		RootPanel.get("notfoundVoca").add(new Label("Không tìm thấy từ"));

	}

}
