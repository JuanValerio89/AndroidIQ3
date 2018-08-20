package com.havr.iq3.envios.envios.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.havr.iq3.envios.envios.Fragments.FragmentDos;
import com.havr.iq3.envios.envios.Fragments.FragmentTres;
import com.havr.iq3.envios.envios.Fragments.FragmentoUno;

public class PagerAdapter extends FragmentStatePagerAdapter{

    private int numberofTabs;

    public PagerAdapter(FragmentManager fm, int numberofTabs) {
        super(fm);
        this.numberofTabs = numberofTabs;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentoUno();
            case 1:
                return new FragmentDos();
            case 2:
                return new FragmentTres();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numberofTabs;
    }
}
