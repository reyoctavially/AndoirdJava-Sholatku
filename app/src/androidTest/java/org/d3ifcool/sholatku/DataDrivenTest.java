package org.d3ifcool.sholatku;

import android.content.ContentProviderOperation;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.d3ifcool.sholatku.activity.CatatWaktuSholatActivity;
import org.d3ifcool.sholatku.activity.MainActivity;
import org.d3ifcool.sholatku.database.DataContract;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DataDrivenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAps() {

        try {
            SyncHttpClient client = new SyncHttpClient();
            client.get("http://waroengsnack.000webhostapp.com/drivensholatku.csv", new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    try {
                        String raw = new String(response);
                        String[] datasRaw = raw.split("\r\n");
                        for(String dataRaw : datasRaw){
                            String[] data = dataRaw.split(";");

                            String namasholat = data[0];
                            String tanggal = data[1];
                            String waktusholat = data[2];
                            String dikerjakan = data[3];
                            String telat = data[4];
                            String expected = data[5];

                            addSholat(namasholat, tanggal, waktusholat, dikerjakan, telat);

                            if(expected.equals("TRUE")){
                                Thread.sleep(1000);
                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
            });

        }catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    private void addSholat(String namasholat, String tanggal, String waktusholat, String dikerjakan, String telat){
        ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<>();
        contentProviderOperations.add(ContentProviderOperation.newInsert(DataContract.Entry.CONTENT_URI)
                .withValue(DataContract.Entry.COLUMN_SHOLAT, namasholat)
                .withValue(DataContract.Entry.COLUMN_TANGGAL, tanggal)
                .withValue(DataContract.Entry.COLUMN_WAKTU_SHOLAT, waktusholat)
                .withValue(DataContract.Entry.COLUMN_WAKTU_SHOLAT_DIKERJAKAN, dikerjakan)
                .withValue(DataContract.Entry.COLUMN_WAKTU_TELAT, telat)
                .build());
        try {
            getApplicationContext().getContentResolver().applyBatch(DataContract.CONTENT_AUTHORITY, contentProviderOperations);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Tidak bisa menambahkan data", Toast.LENGTH_SHORT).show();
        }
    }
}
