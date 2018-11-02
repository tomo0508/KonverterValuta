package tjuri.example.com.jsongetparse;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {


    private Context context;
    private String tabTiles[]= new String[]{"Konverter", "Lista"};

    public FragmentAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context=context;
    }
    @Override
    public int getCount(){
        return tabTiles.length;
    }
    @Override
    public CharSequence getPageTitle(int position){
        return tabTiles[position];
    }

    @Override
    public Fragment getItem(int position){
        Fragment fr= null;
        switch(position){
            case 0:
                fr= new KunaFragment();
                break;
            case 1:
                fr= new ListaFragment();
                break;

        }
        return fr;
    }
}
