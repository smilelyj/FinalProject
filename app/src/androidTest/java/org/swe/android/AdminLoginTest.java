package org.swe.android;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import org.swe.android.activities.AdminLoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by YongjiLi on 2/16/16.
 */
public class AdminLoginTest extends ActivityInstrumentationTestCase2<AdminLoginActivity> {
    private AdminLoginActivity mActivity;

    public AdminLoginTest() {
        super(AdminLoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // Test that the correct toast message is displayed when an invalid survey ID is entered
    // and the Take Survey button is pressed
    public void testInvalidSurveyIdTakeSurveyButton() throws InterruptedException {
        onView(withId(R.id.edit_text)).perform(typeText("INVALID SURVEY ID"), closeSoftKeyboard());
        Thread.sleep(2000);
        onView(withId(R.id.button_take_survey)).perform(click());
        onView(withText(R.string.toast_retry_enter_survey_id)).inRoot(
                withDecorView(not(getActivity().getWindow().getDecorView()))).check(
                matches(isDisplayed()));
    }

    /*
    // Test that the correct toast message is displayed when an invalid survey ID is entered
    // and the View Results button is pressed
    public void testInvalidSurveyIdViewResultsButton() throws InterruptedException {
        onView(withId(R.id.edit_text)).perform(typeText("INVALID SURVEY ID"), closeSoftKeyboard());
        Thread.sleep(5000);
      // onView(withId(R.id.button_view_results)).perform(click());
        onView(withText(R.string.toast_retry_enter_survey_id)).inRoot(
                withDecorView(not(getActivity().getWindow().getDecorView()))).check(
                matches(isDisplayed()));
    }
    */

    // Test that the correct toast message is displayed when no survey ID is entered
    // and the Take Survey button is pressed
    public void testNoSurveyIdEnteredTakeSurveyButton() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.button_take_survey)).perform(click());
        onView(withText(R.string.toast_please_enter_survey_id)).inRoot(
                withDecorView(not(getActivity().getWindow().getDecorView()))).check(
                matches(isDisplayed()));
    }
    /*
    // Test that the correct toast message is displayed when no survey ID is entered
    // and the View Results button is pressed
    public void testNoSurveyIdEnteredViewResultsButton() throws InterruptedException {
        Thread.sleep(2000);
       // onView(withId(R.id.button_view_results)).perform(click());
        onView(withText(R.string.toast_please_enter_survey_id)).inRoot(
                withDecorView(not(getActivity().getWindow().getDecorView()))).check(
                matches(isDisplayed()));
    }

    */

    // Test that the correct toast message is displayed when the device has no data connection
    // and the Take Survey button is pressed. NOTE: You must turn off mobile data on the device
    // for this test to work.
    public void testTakeSurveyButtonWithoutInternetConnection() throws InterruptedException {
        WifiManager wifiManager = (WifiManager)this.getActivity().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);

        Thread.sleep(100);

        onView(withId(R.id.button_take_survey)).perform(click());
        onView(withText(R.string.toast_internet_connection_required)).inRoot(
                withDecorView(not(getActivity().getWindow().getDecorView()))).check(
                matches(isDisplayed()));

        wifiManager.setWifiEnabled(true);

        Thread.sleep(2000);
    }

    /*
    // Test that the correct toast message is displayed when the device has no data connection
    // and the View Results button is pressed. NOTE: You must turn off mobile data on the device
    // for this test to work.
    public void testViewResultsButtonWithoutInternetConnection() throws InterruptedException {
        WifiManager wifiManager = (WifiManager)this.getActivity().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);

        Thread.sleep(5000);

      //  onView(withId(R.id.button_view_results)).perform(click());
        onView(withText(R.string.toast_internet_connection_required)).inRoot(
                withDecorView(not(getActivity().getWindow().getDecorView()))).check(
                matches(isDisplayed()));

        wifiManager.setWifiEnabled(true);

        Thread.sleep(5000);
    }
    */

    public void testValidSurveyIdTakeSurveyButton() throws InterruptedException {
        onView(withId(R.id.edit_text)).perform(typeText("a"), closeSoftKeyboard());
        Thread.sleep(2000);
        onView(withId(R.id.button_take_survey)).perform(click());
        onView(withId(R.id.layout_activity_survey)) ;           // withId(R.id.my_view) is a ViewMatcher
    }
}