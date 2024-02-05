package org.d3ifcool.sholatku;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.os.SystemClock;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.d3ifcool.sholatku.activity.CatatWaktuSholatActivity;
import org.d3ifcool.sholatku.activity.DoaActivity;
import org.d3ifcool.sholatku.activity.JadwalkuActivity;
import org.d3ifcool.sholatku.activity.KiblatActivity;
import org.d3ifcool.sholatku.activity.MainActivity;
import org.d3ifcool.sholatku.activity.SholatkuActivity;
import org.d3ifcool.sholatku.activity.TasbihActivity;
import org.d3ifcool.sholatku.data.Doa;
import org.d3ifcool.sholatku.data.Sholat;
import org.d3ifcool.sholatku.database.DataContract;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();

        assertEquals("org.d3ifcool.sholatku", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<MainActivity> testRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void TestJadwal(){
        onView(withId(R.id.tv_sholat_sekarang));
        onView(withId(R.id.tv_waktu_shubuh));
        onView(withId(R.id.tv_waktu_dzuhur));
        onView(withId(R.id.tv_waktu_ashar));
        onView(withId(R.id.tv_waktu_maghrib));
        onView(withId(R.id.tv_waktu_isya));
        onView(withId(R.id.scrollView2)).perform(swipeUp());
        SystemClock.sleep(2000);
    }

    @Test
    public void SholatBerikutnya(){
        onView(withId(R.id.tv_timer));
        onView(withId(R.id.tv_sholat_berikutnya));
        onView(withId(R.id.scrollView2)).perform(swipeUp());
        SystemClock.sleep(2000);
    }

    @Test
    public void TestKiblat(){
        onView(withId(R.id.iv_kompas));
        onView(withId(R.id.iv_panah));
        SystemClock.sleep(2000);
    }

    @Test
    public void TestDoa(){
        onView(withId(R.id.list_doa)).perform(swipeUp());
        SystemClock.sleep(1000);
        onView(withId(R.id.list_doa)).perform(swipeDown());
        SystemClock.sleep(2000);

        onView(withId(R.id.list_doa))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); // melakukan click pada list pertama
        onView(isDisplayed());
        SystemClock.sleep(2000);

        pressBack();

        onView(withId(R.id.list_doa))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click())); // melakukan click pada list kedua
        onView(withId(R.id.iv_detail_doa));
        SystemClock.sleep(2000);
    }

    @Test
    public void TestKeterlambatan(){
        onView(withId(R.id.list_sholatku)).perform(swipeUp());
        SystemClock.sleep(1000);
        onView(withId(R.id.list_sholatku)).perform(swipeDown());
        SystemClock.sleep(2000);
    }

    @Test
    public void TestTasbih(){
        for (int i = 0; i < 33 ; i++) {
            onView(withId(R.id.cv_klik_layar)).perform(click());
        }
        SystemClock.sleep(2000);
        onView(withId(R.id.btn_tasbih_selanjutnya)).perform(click());
        for (int i = 0; i < 33 ; i++) {
            onView(withId(R.id.cv_klik_layar)).perform(click());
        }
        SystemClock.sleep(2000);
        onView(withId(R.id.btn_tasbih_selanjutnya)).perform(click());
        for (int i = 0; i < 33 ; i++) {
            onView(withId(R.id.cv_klik_layar)).perform(click());
        }
        SystemClock.sleep(2000);
        onView(withId(R.id.btn_tasbih_selanjutnya)).perform(click());
    }

    @Test
    public void UiTestSholatku(){
        // cek beranda
        onView(withId(R.id.tv_waktusholat)); // Cek waktu sholat saat ini
        SystemClock.sleep(2000);

        onView(withId(R.id.iv_sholat)).perform(click()); // melakukan click pada menu sholat
        SystemClock.sleep(2000);
        onView(withId(R.id.list_sholat))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); // melakukan click pada list pertama
        SystemClock.sleep(2000);

        // cek jadwal sholat
        onView(withId(R.id.tv_sholat_sekarang));
        onView(withId(R.id.tv_waktu_shubuh));
        onView(withId(R.id.tv_waktu_dzuhur));
        onView(withId(R.id.tv_waktu_ashar));
        onView(withId(R.id.tv_waktu_maghrib));
        onView(withId(R.id.tv_waktu_isya));
        onView(withId(R.id.tv_timer));
        onView(withId(R.id.tv_sholat_berikutnya));
        onView(withId(R.id.scrollView2)).perform(swipeUp());
        SystemClock.sleep(2000);

        pressBack();

//        onView(withId(R.id.list_sholat))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click())); // melakukan click pada list kedua
//        SystemClock.sleep(2000);
//
//        // cek arah kiblat
//        onView(withId(R.id.iv_kompas));
//        onView(withId(R.id.iv_panah));
//        SystemClock.sleep(2000);
//
//        pressBack();
        pressBack();

        // cek doa harian
        onView(withId(R.id.iv_doa)).perform(click()); // melakukan click pada menu doa
        SystemClock.sleep(2000);
        onView(withId(R.id.list_doa)).perform(swipeUp());
        SystemClock.sleep(1000);
        onView(withId(R.id.list_doa)).perform(swipeDown());
        SystemClock.sleep(2000);

        onView(withId(R.id.list_doa))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); // melakukan click pada list pertama
        onView(isDisplayed());
        SystemClock.sleep(2000);

        pressBack();

        onView(withId(R.id.list_doa))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, click())); // melakukan click pada list kedua
        onView(withId(R.id.iv_detail_doa));
        SystemClock.sleep(2000);

        pressBack();
        pressBack();

        // cek tasbih digital
        onView(withId(R.id.iv_tasbih)).perform(click()); // melakukan click pada menu tasbih
        SystemClock.sleep(2000);
        for (int i = 0; i < 33 ; i++) {
            onView(withId(R.id.cv_klik_layar)).perform(click());
        }
        SystemClock.sleep(2000);
        onView(withId(R.id.btn_tasbih_selanjutnya)).perform(click());
        for (int i = 0; i < 33 ; i++) {
            onView(withId(R.id.cv_klik_layar)).perform(click());
        }
        SystemClock.sleep(2000);
        onView(withId(R.id.btn_tasbih_selanjutnya)).perform(click());
        for (int i = 0; i < 33 ; i++) {
            onView(withId(R.id.cv_klik_layar)).perform(click());
        }
        SystemClock.sleep(2000);
        onView(withId(R.id.btn_tasbih_selanjutnya)).perform(click());

        // cek data sholatku
        onView(withId(R.id.iv_sholatku)).perform(click()); // melakukan click pada menu sholatku
        SystemClock.sleep(2000);
        onView(withId(R.id.list_sholatku)).perform(swipeUp());
        SystemClock.sleep(1000);
        onView(withId(R.id.list_sholatku)).perform(swipeDown());
        SystemClock.sleep(2000);
        pressBack();

        // cek about
        onView(withId(R.id.action_about)).perform(click());
        onView(isDisplayed());
        SystemClock.sleep(2000);
        pressBack();
        SystemClock.sleep(2000);
    }
}
