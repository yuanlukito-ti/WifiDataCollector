package id.ac.ukdw.ti.yuanlukito.wifidatacollector.utilities;

import java.util.Comparator;

import id.ac.ukdw.ti.yuanlukito.wifidatacollector.model.WifiStation;

/**
 * Created by Yuan Lukito on 06-Apr-15.
 */
public class WifiStationSortByLevel implements Comparator<WifiStation> {
    @Override
    public int compare(WifiStation lhs, WifiStation rhs) {
        return rhs.getLevel() - lhs.getLevel();
    }
}
