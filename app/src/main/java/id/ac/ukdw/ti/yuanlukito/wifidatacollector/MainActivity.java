package id.ac.ukdw.ti.yuanlukito.wifidatacollector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.DataSource;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.WifiStation;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.utilities.WifiStationListAdapter;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.utilities.WifiStationSortByLevel;


public class MainActivity extends ActionBarActivity {

    //Broadcast Receiver (WiFi Scanning)
    private WifiManager wifiManager;
    private IntentFilter intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
    private BroadcastReceiver broadcastReceiver;

    //list view wifi stations
    private ListView listViewWifiStations;
    private List<ScanResult> wifiScanResults;
    private ArrayList<WifiStation> wifiStations;
    private WifiStationListAdapter wifiStationsAdapter;

    //button scan
    private Button buttonScan;
    private Button buttonSave;
    private Button buttonManageData;

    //spinner location
    private Spinner spinnerLocation;
    private ArrayAdapter<String> locationsAdapter;

    //toast message
    private void displayToastStartScan(){
        Toast.makeText(this, "Scanning wifi stations. Please wait for a few moments.", Toast.LENGTH_SHORT).show();
    }

    private void displayToastReceivedScanResult(){
        Toast.makeText(this, "Scan results received.", Toast.LENGTH_SHORT).show();
    }

    private void displayToastInsertWifiDataSuccess(){
        Toast.makeText(this, "Scan results successfully saved.", Toast.LENGTH_SHORT).show();
    }

    private void displayToastInsertWifiDataFailed(){
        Toast.makeText(this, "Cannot save scan result. Please try again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button handling
        buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiManager.startScan();
                displayToastStartScan();
            }
        });

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setEnabled(false);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if we already have scan results
                if(wifiStations != null || wifiStations.size() > 0) {
                    DataSource ds = new DataSource(getApplicationContext());
                    try {
                        ds.open();
                        //get id of selected location (spinner)
                        int locationId = ds.getLocationId((String) spinnerLocation.getSelectedItem());
                        boolean result = ds.insertWifiData(locationId, wifiStations);
                        if(result) {
                            displayToastInsertWifiDataSuccess();
                        }
                        else {
                            displayToastInsertWifiDataFailed();
                        }
                    } catch (SQLException e) {
                        displayToastInsertWifiDataFailed();
                        e.printStackTrace();
                    }

                }
            }
        });

        buttonManageData = (Button) findViewById(R.id.buttonManageData);
        buttonManageData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManageDataActivity.class);
                startActivity(intent);
            }
        });

        //list view and adapter initialization
        listViewWifiStations = (ListView) findViewById(R.id.listViewWifiStation);

        //spinner location
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        DataSource ds = new DataSource(this);
        try {
            ds.open();
            locationsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ds.getLocationArray());
            locationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLocation.setAdapter(locationsAdapter);
            ds.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onPause(){
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void onResume(){
        super.onResume();

        //initialize wifi manager and enable wifi
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);

        //receive wifi scan results
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                displayToastReceivedScanResult();

                wifiScanResults = wifiManager.getScanResults();
                wifiStations = new ArrayList<>();

                if(wifiScanResults.size() > 0)
                    buttonSave.setEnabled(true);

                for(int i=0; i<wifiScanResults.size(); i++){
                    ScanResult sr = wifiScanResults.get(i);
                    WifiStation ws = new WifiStation(sr.BSSID, sr.SSID, sr.frequency, sr.level);
                    wifiStations.add(ws);
                }
                //sort by level
                Collections.sort(wifiStations, new WifiStationSortByLevel());

                wifiStationsAdapter = new WifiStationListAdapter(MainActivity.this, wifiStations);
                listViewWifiStations.setAdapter(wifiStationsAdapter);
            }
        };

        //register broadcast receiver
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}