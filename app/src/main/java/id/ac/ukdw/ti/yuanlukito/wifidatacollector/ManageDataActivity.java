package id.ac.ukdw.ti.yuanlukito.wifidatacollector;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.DataSource;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.Location;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.utilities.LocationListAdapter;


public class ManageDataActivity extends ActionBarActivity {

    private ListView listViewLocationData;
    private Button buttonExportData;
    private LocationListAdapter locationListAdapter;
    private ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);

        listViewLocationData = (ListView) findViewById(R.id.listViewLocationData);
        DataSource ds = new DataSource(this);
        try {
            ds.open();
            locations = ds.getLocationList();
            locationListAdapter = new LocationListAdapter(this, locations);
            listViewLocationData.setAdapter(locationListAdapter);
            ds.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttonExportData = (Button) findViewById(R.id.buttonExportData);
        buttonExportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_data, menu);
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
