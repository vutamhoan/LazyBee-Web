package com.born2go.lazzybeemobile.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.born2go.lazzybee.gdatabase.shared.nonentity.Test;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class MTestTool extends Composite {

	private static TestToolUiBinder uiBinder = GWT
			.create(TestToolUiBinder.class);

	interface TestToolUiBinder extends UiBinder<Widget, MTestTool> {
	}

	@UiField
	HTMLPanel container;
	@UiField
	HTMLPanel htmlResultTest;
	@UiField
	HTMLPanel testgood;
	@UiField
	HTMLPanel testmedium;
	@UiField
	HTMLPanel testbad;
	@UiField
	HTMLPanel actionForm;
	@UiField
	HTMLPanel shareForm;
	@UiField
	Label testgoodlv;
	@UiField
	Label testmediumlv;
	@UiField
	Label testbadlv;
	@UiField
	Label lbTest1;
	@UiField
	Label lbTest2;
	@UiField
	Label resultTestGood;
	@UiField
	Label resultTestMedium;
	@UiField
	Label resultTestBad;
	@UiField
	Anchor btnAgainTesting;
	@UiField
	Anchor btnNextTesting;

	Label checkTotal;

	private int totalCheck = 0; // Tong so tu user biet
	private int testLevel = 2; // Level test default khi bat dau
	private Map<String, Boolean> testMap = new HashMap<String, Boolean>();

	private int current_random;
	Element btnStartTesting;

	public MTestTool() {
		initWidget(uiBinder.createAndBindUi(this));
		container.setStyleName("mainMTestTool");
		actionForm.setStyleName("actionForm");
		shareForm.setStyleName("actionForm");
		btnStartTesting = RootPanel.get("btnStartTesting").getElement();
		Event.sinkEvents(btnStartTesting, Event.ONCLICK);
		Event.setEventListener(btnStartTesting, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				if (Event.ONCLICK == event.getTypeInt()) {
					DOM.getElementById("htmlIntroTest").setAttribute("style",
							"display:none");
					getTestByLevel(testLevel);
				}

			}
		});

	}

	private void getTestByLevel(int level) {
		String test[] = null;
		int test_index = Random.nextInt(10);
		while (test_index == current_random) {
			test_index = Random.nextInt(10);
		}
		current_random = test_index;
		switch (level) {
		case 1:
			test = Test.getTestLv1()[test_index].split(",");
			break;
		case 2:
			test = Test.getTestLv2()[test_index].split(",");
			break;
		case 3:
			test = Test.getTestLv3()[test_index].split(",");
			break;
		case 4:
			test = Test.getTestLv4()[test_index].split(",");
			break;
		case 5:
			test = Test.getTestLv5()[test_index].split(",");
			break;
		case 6:
			test = Test.getTestLv6()[test_index].split(",");
			break;
		default:
			break;
		}

		List<String> ltest = new ArrayList<String>();
		for (int i = 0; i <= 19; i++) {
			ltest.add(test[i]);
		}
		startTesting(ltest);
	}

	private void addTestVoca(HTMLPanel vocaShowPanel, final String v) {
		testMap.put(v, false);
		final HTMLPanel form = new HTMLPanel("");
		Label vocaQ = new Label(v);
		Label vocaLv = new Label("Lv: " + testLevel);
		form.add(vocaQ);
		form.add(vocaLv);
		form.setStyleName("MTestTool_Obj5");
		vocaQ.setStyleName("itesttool_vocaq");
		vocaLv.setStyleName("i_testtool_vocaLv");
		vocaShowPanel.add(form);
		Anchor btnForm = new Anchor();
		btnForm.setStyleName("i_testtool_btnForm");
		form.add(btnForm);
		// -----
		btnForm.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (testMap.get(v)) {
					form.getElement().setAttribute("style", "background: gray");
					totalCheck--;
				} else {
					form.getElement().setAttribute("style",
							"background: forestgreen");
					totalCheck++;
				}
				testMap.put(v, !testMap.get(v));
				checkTotal.setText("B: " + totalCheck + " / 20");
			}
		});
	}

	private void startTesting(List<String> test) {
		container.clear();
		totalCheck = 0;
		testMap.clear();
		HTMLPanel testInfoPanel = new HTMLPanel("");
		HTMLPanel vocaShowPanel = new HTMLPanel("");
		HTMLPanel controlPanel = new HTMLPanel("");
		container.add(testInfoPanel);
		container.add(vocaShowPanel);
		container.add(controlPanel);
		testInfoPanel.setStyleName("MTestTool_Obj1");
		testInfoPanel.getElement().setAttribute("style",
				"padding: 10px; overflow: hidden;");

		Label total = new Label("Tổng: " + test.size() + " Từ");
		Label info = new Label(
				"(Đây là bài tự kiểm tra, hãy click để chọn các từ bạn đã biết)");
		checkTotal = new Label("B: " + totalCheck + " / 20");
		total.getElement().setAttribute("style",
				"float: left; font-weight: bold;");
		info.setStyleName("i_testtool_info");
		checkTotal.setStyleName("i_testtool_checkTotal");
		testInfoPanel.add(total);
		testInfoPanel.add(checkTotal);
		testInfoPanel.add(info);
		Anchor btnComplete = new Anchor("Hoàn Thành");
		Anchor btnQuit = new Anchor("Dừng Bài Test");
		controlPanel.add(btnComplete);
		controlPanel.add(btnQuit);
		controlPanel.setStyleName("i_testt_controlPanel");
		btnComplete.setStyleName("MTestTool_Obj3");
		btnQuit.setStyleName("MTestTool_Obj4");
		vocaShowPanel.getElement().setAttribute("style",
				"text-align: center; margin-bottom:40px;");
		// -----
		for (String v : test)
			addTestVoca(vocaShowPanel, v);
		// -----
		btnComplete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getTestResult();
			}
		});

		btnQuit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				container.clear();
				DOM.getElementById("htmlIntroTest").setAttribute("style",
						"display:block");
			}
		});
	}

	private void getTestResult() {
		container.clear();
		container.add(htmlResultTest);
		htmlResultTest.setVisible(true);
		DOM.getElementById("table-app").setAttribute("style", "display:block;");

		testgood.setVisible(false);
		testmedium.setVisible(false);
		testbad.setVisible(false);
		if (totalCheck >= 15) {
			testgood.setVisible(true);
			testgoodlv.setText("Level: " + testLevel);
			resultTestGood.setText(totalCheck + " / 20");
			lbTest1.setText("Bạn đã hoàn thành tốt bài kiểm tra!");
			if (testLevel + 1 <= 6) {
				lbTest2.setText("Note: Bạn nên tiếp tục làm bài test Level "
						+ (testLevel + 1) + " hoặc bắt đầu học từ Level "
						+ (testLevel + 1) + ".");
				btnNextTesting.setText("Tiếp Tục - Lv " + (testLevel + 1));
				btnNextTesting.getElement().setAttribute("style", "");
			} else {
				lbTest2.setText("Note: Bạn đã có một vốn tiếng anh rất xuất sắc. Bạn có thể dùng LazzyBee để ôn tập lại vốn từ của mình.");
				btnNextTesting.getElement().setAttribute("style",
						"background: silver");
			}
		} else if (totalCheck < 15 && totalCheck >= 10) {
			testmedium.setVisible(true);
			testmediumlv.setText("Level: " + testLevel);
			resultTestMedium.setText(totalCheck + " / 20");
			lbTest1.setText("Bạn đã hoàn thành bài kiểm tra ở mức trung bình!");
			lbTest2.setText("Note: Bạn nên bắt đầu học từ Level " + testLevel
					+ ".");
			btnNextTesting.setText("Tiếp Tục");
			btnNextTesting.getElement().setAttribute("style",
					"background: silver");
		} else {
			testbad.setVisible(true);
			testbadlv.setText("Level: " + testLevel);
			resultTestBad.setText(totalCheck + " / 20");
			lbTest1.setText("Bạn đã không hoàn thành tốt bài kiểm tra!");
			if (testLevel - 1 != 0) {
				lbTest2.setText("Note: Bạn nên tiếp tục làm bài test Level "
						+ (testLevel - 1) + " hoặc bắt đầu học từ Level "
						+ (testLevel - 1) + ".");
				btnNextTesting.setText("Tiếp Tục - Lv " + (testLevel - 1));
				btnNextTesting.getElement().setAttribute("style", "");
			} else {
				lbTest2.setText("Note: Bạn nên bắt đầu học từ Level 1");
				btnNextTesting.getElement().setAttribute("style",
						"background: silver");
			}
		}
	}

	/*
	 * @UiHandler("btnStartTesting") void onBtnStartTestingClick(ClickEvent e) {
	 * getTestByLevel(testLevel); }
	 */
	@UiHandler("btnAgainTesting")
	void onBtnAgainTestingClick(ClickEvent e) {
		getTestByLevel(testLevel);
	}

	@UiHandler("btnNextTesting")
	void onBtnNextTestingClick(ClickEvent e) {
		if (totalCheck >= 15) {
			if (testLevel + 1 <= 6) {
				testLevel++;
				getTestByLevel(testLevel);
			}
		} else if (totalCheck < 15 && totalCheck >= 10) {

		} else {
			if (testLevel - 1 != 0) {
				testLevel--;
				getTestByLevel(testLevel);
			}
		}
	}

}
