package id.ac.ukdw.ti.yuanlukito.wifidatacollector.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import id.ac.ukdw.ti.yuanlukito.wifidatacollector.R;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.DataCollectionTime;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.DataSource;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.Location;
import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.LocationData;

/**
 * Created by Yuan Lukito on 22-Apr-15.
 */
public class LocationListAdapter extends BaseAdapter {
    private ArrayList<Location> locations;
    private Activity activity;
    private LayoutInflater inflater;

    public LocationListAdapter(Activity activity, ArrayList<Location> locations){
        this.activity = activity;
        this.locations = locations;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Location getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.location_list_item, null);
        Location ld = locations.get(position);

        //get pagi, siang, sore
        int pagi = 0;
        int siang = 0;
        int sore = 0;

        DataSource ds = new DataSource(ApplicationContextProvider.getContext());
        try {
            ds.open();
            pagi = ds.getCountOfLocationData(ld.getId(), DataCollectionTime.PAGI);
            siang = ds.getCountOfLocationData(ld.getId(), DataCollectionTime.SIANG);
            sore = ds.getCountOfLocationData(ld.getId(), DataCollectionTime.SORE);
            ds.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int progressPagi = (int)((pagi / 10.0) * 100);
        if(progressPagi > 100)
            progressPagi = 100;
        int progressSiang = (int)((siang / 10.0) * 100);
        if(progressSiang > 100)
            progressSiang = 100;
        int progressSore = (int) ((siang / 10.0) * 100);
        if(progressSore > 100)
            progressSore = 100;

        TextView textViewLocationName = (TextView) convertView.findViewById(R.id.textViewLocationName);
        TextView textViewPagi = (TextView) convertView.findViewById(R.id.textViewPagi);
        TextView textViewSiang = (TextView) convertView.findViewById(R.id.textViewSiang);
        TextView textViewSore = (TextView) convertView.findViewById(R.id.textViewSore);
        ProgressBar progressBarPagi = (ProgressBar) convertView.findViewById(R.id.progressBarPagi);
        ProgressBar progressBarSiang = (ProgressBar) convertView.findViewById(R.id.progressBarSiang);
        ProgressBar progressBarSore = (ProgressBar) convertView.findViewById(R.id.progressBarSore);
        ImageView imageViewProgress = (ImageView) convertView.findViewById(R.id.imageViewProgress);

        textViewLocationName.setText(ld.getName());
        textViewPagi.setText("Pagi: " + pagi + " data");
        textViewSiang.setText("Siang: " + siang + " data");
        textViewSore.setText("Sore: " + sore + " data");

        progressBarPagi.setProgress(progressPagi);
        progressBarSiang.setProgress(progressSiang);
        progressBarSore.setProgress(progressSore);

        if(pagi >= 10 && siang >= 10 && sore >= 10)
            imageViewProgress.setImageResource(R.drawable.notification_done);
        else
            imageViewProgress.setImageResource(R.drawable.notification_warning);

        return convertView;
    }
}
