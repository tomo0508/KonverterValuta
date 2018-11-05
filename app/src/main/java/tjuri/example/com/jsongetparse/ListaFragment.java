package tjuri.example.com.jsongetparse;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class ListaFragment extends Fragment {
    ArrayList<String> array;
    ListView lv;
TextView tvCode;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rView = inflater.inflate(R.layout.fragment_lista, container, false);

        return rView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
           // lv = (getView()).findViewById(R.id.lvTecaj);
            array = bundle.getStringArrayList("valute");
           /* ArrayAdapter adapter = new ArrayAdapter<>(
                    getContext(), android.R.layout.simple_list_item_1, array);
            lv.setAdapter(adapter);// handle your code here.*/



           tvCode= getView().findViewById(R.id.tvCode);
            for (String str: array){
                tvCode.setText(str.toString());
            }

        }


    }
}

