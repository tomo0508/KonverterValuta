package tjuri.example.com.jsongetparse;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class KunaFragment extends Fragment {

    Spinner spinner;
    Spinner spinner1;

    ArrayList<String> valutetList;
    ArrayList<String> valutetSelling;
    ArrayList<String> valutetBuying;
    ArrayList<String> valutetMedian;
    ArrayList<String> valutetValue;
    int sp = 0;
    int sp1 = 0;

    EditText etUnos;
    EditText etUnos1;

    RadioButton rbSelling;
    RadioButton rbMedian;
    RadioButton rbBuying;


    double izracun;
    DecimalFormat df;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rView = inflater.inflate(R.layout.fragment_kuna, container, false);

        return rView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidgets();
        new getRates().execute();
        setlistener();
        //etListener();
        df = new DecimalFormat("#.##");

    }


    private class getRates extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast.makeText(getContext(), "Json Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            HttpHandler httpHandler = new HttpHandler();
            String url = "http://hnbex.eu/api/v1/rates/daily/?date=" + formattedDate;
            String jsonStr = httpHandler.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) try {
                JSONArray valute = new JSONArray(jsonStr);
                for (int i = 0; i < valute.length(); i++) {


                    JSONObject jsonobject = valute.getJSONObject(i);
                    String code = jsonobject.getString("currency_code");
                    String selling = jsonobject.getString("selling_rate");
                    String buying = jsonobject.getString("buying_rate");
                    String median = jsonobject.getString("median_rate");
                    String value = jsonobject.getString("unit_value");

                    valutetList.add(code);
                    valutetSelling.add(selling);
                    valutetBuying.add(buying);
                    valutetValue.add(value);
                    valutetMedian.add(median);

                }


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ArrayAdapter adapter = new ArrayAdapter<>(
                    getContext(), android.R.layout.simple_list_item_1, valutetList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
            spinner1.setAdapter(adapter);
            spinner1.setSelection(12);
            super.onPostExecute(aVoid);
        }
    }

    public void setlistener() {

        getView().findViewById(R.id.btnPreracunaj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double selling;
                double selling1;
                double buying;
                double buying1;
                double median;
                double median1;
                int value;
                int value1;
                sp = spinner.getSelectedItemPosition();
                sp1 = spinner1.getSelectedItemPosition();
                double unos;


                if (rbSelling.isChecked()) {

                    selling = Double.parseDouble(valutetSelling.get(sp));
                    selling1 = Double.parseDouble(valutetSelling.get(sp1));


                    if (!etUnos.getText().toString().equals("") && etUnos.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos.getText().toString());
                        if (value == value1) {


                            izracun = (selling / selling1) * unos;
                            etUnos1.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((selling / selling1) * unos) * value1;
                            etUnos1.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (selling / selling1) * unos / value;
                            etUnos1.setText(df.format(izracun));

                        }

                    } else if (!etUnos1.getText().toString().equals("") && etUnos1.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos1.getText().toString());
                        if (value == value1) {
                            izracun = ((selling1 / selling) * unos);
                            etUnos.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((selling1 / selling) * unos) / value1;
                            etUnos.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (selling1 / selling) * unos * value;
                            etUnos.setText(df.format(izracun));

                        }
                    }


                } else if (rbBuying.isChecked()) {

                    buying = Double.parseDouble(valutetBuying.get(sp));
                    buying1 = Double.parseDouble(valutetBuying.get(sp1));


                    if (!etUnos.getText().toString().equals("") && etUnos.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos.getText().toString());
                        if (value == value1) {


                            izracun = (buying / buying1) * unos;
                            etUnos1.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((buying / buying1) * unos) * value1;
                            etUnos1.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (buying / buying1) * unos / value;
                            etUnos1.setText(df.format(izracun));

                        }

                    } else if (!etUnos1.getText().toString().equals("") && etUnos1.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos1.getText().toString());
                        if (value == value1) {
                            izracun = ((buying1 / buying) * unos);
                            etUnos.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((buying1 / buying) * unos) / value1;
                            etUnos.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (buying1 / buying) * unos * value;
                            etUnos.setText(df.format(izracun));

                        }
                    }


                } else if (rbMedian.isChecked() && etUnos1.getText() != null) {

                    median = Double.parseDouble(valutetMedian.get(sp));
                    median1 = Double.parseDouble(valutetMedian.get(sp1));


                    if (!etUnos.getText().toString().equals("") && etUnos.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos.getText().toString());
                        if (value == value1) {


                            izracun = (median / median1) * unos;
                            etUnos1.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((median / median1) * unos) * value;
                            etUnos1.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (median / median1) * unos / value1;
                            etUnos1.setText(df.format(izracun));

                        }

                    } else if (!etUnos1.getText().toString().equals("") && etUnos1.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos1.getText().toString());
                        if (value == value1) {
                            izracun = ((median1 / median) * unos);
                            etUnos.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((median1 / median) * unos) / value1;
                            etUnos.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (median1 / median) * unos * value;
                            etUnos.setText(df.format(izracun));

                        }
                    }


                }

            }
        });
    }

    public void etListener() {

        etUnos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double selling;
                double selling1;
                double buying;
                double buying1;
                double median;
                double median1;
                int value;
                int value1;
                sp = spinner.getSelectedItemPosition();
                sp1 = spinner1.getSelectedItemPosition();
                double unos;


                if (rbSelling.isChecked()) {

                    selling = Double.parseDouble(valutetSelling.get(sp));
                    selling1 = Double.parseDouble(valutetSelling.get(sp1));


                    if (!etUnos.getText().toString().equals("") && etUnos.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos.getText().toString());
                        if (value == value1) {


                            izracun = (selling / selling1) * unos;
                            etUnos1.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((selling / selling1) * unos) * value1;
                            etUnos1.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (selling / selling1) * unos / value;
                            etUnos1.setText(df.format(izracun));

                        }

                    } else if (!etUnos1.getText().toString().equals("") && etUnos1.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos1.getText().toString());
                        if (value == value1) {
                            izracun = ((selling1 / selling) * unos);
                            etUnos.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((selling1 / selling) * unos) / value1;
                            etUnos.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (selling1 / selling) * unos * value;
                            etUnos.setText(df.format(izracun));

                        }
                    }


                } else if (rbBuying.isChecked()) {

                    buying = Double.parseDouble(valutetBuying.get(sp));
                    buying1 = Double.parseDouble(valutetBuying.get(sp1));


                    if (!etUnos.getText().toString().equals("") && etUnos.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos.getText().toString());
                        if (value == value1) {


                            izracun = (buying / buying1) * unos;
                            etUnos1.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((buying / buying1) * unos) * value1;
                            etUnos1.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (buying / buying1) * unos / value;
                            etUnos1.setText(df.format(izracun));

                        }

                    } else if (!etUnos1.getText().toString().equals("") && etUnos1.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos1.getText().toString());
                        if (value == value1) {
                            izracun = ((buying1 / buying) * unos);
                            etUnos.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((buying1 / buying) * unos) / value1;
                            etUnos.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (buying1 / buying) * unos * value;
                            etUnos.setText(df.format(izracun));

                        }
                    }


                } else if (rbMedian.isChecked() && etUnos1.getText() != null) {

                    median = Double.parseDouble(valutetMedian.get(sp));
                    median1 = Double.parseDouble(valutetMedian.get(sp1));


                    if (!etUnos.getText().toString().equals("") && etUnos.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos.getText().toString());
                        if (value == value1) {


                            izracun = (median / median1) * unos;
                            etUnos1.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((median / median1) * unos) * value;
                            etUnos1.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (median / median1) * unos / value1;
                            etUnos1.setText(df.format(izracun));

                        }

                    } else if (!etUnos1.getText().toString().equals("") && etUnos1.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos1.getText().toString());
                        if (value == value1) {
                            izracun = ((median1 / median) * unos);
                            etUnos.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((median1 / median) * unos) / value1;
                            etUnos.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (median1 / median) * unos * value;
                            etUnos.setText(df.format(izracun));

                        }
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etUnos1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double selling;
                double selling1;
                double buying;
                double buying1;
                double median;
                double median1;
                int value;
                int value1;
                sp = spinner.getSelectedItemPosition();
                sp1 = spinner1.getSelectedItemPosition();
                double unos;


                if (rbSelling.isChecked()) {

                    selling = Double.parseDouble(valutetSelling.get(sp));
                    selling1 = Double.parseDouble(valutetSelling.get(sp1));


                    if (!etUnos.getText().toString().equals("") && etUnos.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos.getText().toString());
                        if (value == value1) {


                            izracun = (selling / selling1) * unos;
                            etUnos1.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((selling / selling1) * unos) * value1;
                            etUnos1.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (selling / selling1) * unos / value;
                            etUnos1.setText(df.format(izracun));

                        }

                    } else if (!etUnos1.getText().toString().equals("") && etUnos1.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos1.getText().toString());
                        if (value == value1) {
                            izracun = ((selling1 / selling) * unos);
                            etUnos.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((selling1 / selling) * unos) / value1;
                            etUnos.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (selling1 / selling) * unos * value;
                            etUnos.setText(df.format(izracun));

                        }
                    }


                } else if (rbBuying.isChecked()) {

                    buying = Double.parseDouble(valutetBuying.get(sp));
                    buying1 = Double.parseDouble(valutetBuying.get(sp1));


                    if (!etUnos.getText().toString().equals("") && etUnos.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos.getText().toString());
                        if (value == value1) {


                            izracun = (buying / buying1) * unos;
                            etUnos1.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((buying / buying1) * unos) * value1;
                            etUnos1.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (buying / buying1) * unos / value;
                            etUnos1.setText(df.format(izracun));

                        }

                    } else if (!etUnos1.getText().toString().equals("") && etUnos1.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos1.getText().toString());
                        if (value == value1) {
                            izracun = ((buying1 / buying) * unos);
                            etUnos.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((buying1 / buying) * unos) / value1;
                            etUnos.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (buying1 / buying) * unos * value;
                            etUnos.setText(df.format(izracun));

                        }
                    }


                } else if (rbMedian.isChecked() && etUnos1.getText() != null) {

                    median = Double.parseDouble(valutetMedian.get(sp));
                    median1 = Double.parseDouble(valutetMedian.get(sp1));


                    if (!etUnos.getText().toString().equals("") && etUnos.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos.getText().toString());
                        if (value == value1) {


                            izracun = (median / median1) * unos;
                            etUnos1.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((median / median1) * unos) * value;
                            etUnos1.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (median / median1) * unos / value1;
                            etUnos1.setText(df.format(izracun));

                        }

                    } else if (!etUnos1.getText().toString().equals("") && etUnos1.hasFocus()) {
                        value = Integer.parseInt(valutetValue.get(sp));
                        value1 = Integer.parseInt(valutetValue.get(sp1));
                        unos = Double.parseDouble(etUnos1.getText().toString());
                        if (value == value1) {
                            izracun = ((median1 / median) * unos);
                            etUnos.setText(df.format(izracun));
                        } else if (value == 1 && value1 == 100) {
                            izracun = ((median1 / median) * unos) / value1;
                            etUnos.setText(df.format(izracun));

                        } else if (value == 100 && value1 == 1) {
                            izracun = (median1 / median) * unos * value;
                            etUnos.setText(df.format(izracun));

                        }
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    public void initWidgets() {
        spinner = getView().findViewById(R.id.spFrom);
        spinner1 = getView().findViewById(R.id.spTo);
        etUnos = getView().findViewById(R.id.etUnos);
        etUnos1 = getView().findViewById(R.id.etUnos1);
        rbSelling = getView().findViewById(R.id.rbSelling);
        rbMedian = getView().findViewById(R.id.rbMedian);
        rbBuying = getView().findViewById(R.id.rbBuying);
        valutetList = new ArrayList<>();
        valutetSelling = new ArrayList<>();
        valutetBuying = new ArrayList<>();
        valutetMedian = new ArrayList<>();
        valutetValue = new ArrayList<>();
        rbMedian.setChecked(true);

    }

}





