package com.example.blockchain;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends AppCompatActivity {

    Button back_main_bt,search_bt,visial_bt;
    TextView textView1,textView2,textView3,textView4;
    EditText editText;
    String location_name ="";
    String results = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        back_main_bt = findViewById(R.id.button2);
        search_bt = findViewById(R.id.button);
        visial_bt = findViewById(R.id.button3);
        visial_bt.setVisibility(View.GONE);

        textView1 = findViewById(R.id.textView7);
        textView2 = findViewById(R.id.textView8);
        textView3 = findViewById(R.id.textView9);
        textView4 = findViewById(R.id.textView10);
        editText = findViewById(R.id.editTextNumber);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final String[] lunch = {"", "台北", "新北", "桃園", "台中", "台南", "高雄", "基隆", "新竹", "嘉義", "苗栗", "彰化", "南投", "雲林", "嘉義", "屏東", "宜蘭", "花蓮", "台東", "澎湖"};
        ArrayAdapter<String> lunchList = new ArrayAdapter(CheckActivity.this,android.R.layout.simple_spinner_dropdown_item,lunch);
        spinner.setAdapter(lunchList);

        back_main_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = intent.setClass(CheckActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                visial_bt.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();
                if(text.equals("")){
                    try {
                        File mSDFile = null;
                        if(Environment.getExternalStorageState().equals( Environment.MEDIA_REMOVED)){
                            Toast.makeText(CheckActivity.this , "沒有SD卡!!!" , Toast.LENGTH_SHORT ).show();
                            return ;
                        }
                        else{
                            Editable inputnum = editText.getText();
                            boolean is_in_list = false;
                            String num = "";
                            String productname = "";
                            String sp_chain = "";
                            if(!String.valueOf(inputnum).equals("")){
                                mSDFile = Environment.getExternalStorageDirectory();
                                String path=mSDFile.getPath()+"/Download";
                                File file = new File(path+"/"+"result_1.csv");
                                CSVReader reader = new CSVReader(new FileReader(path+"/"+"result_1.csv"));
                                String[] nextLine;
                                int i = 0;
                                while ((nextLine = reader.readNext()) != null) {
                                    if(nextLine[0].equals(String.valueOf(inputnum))){
                                        num = nextLine[1];
                                        sp_chain =nextLine[2];
                                        productname = nextLine[3];
                                        Toast.makeText(CheckActivity.this , "作業的number為"+num  , Toast.LENGTH_LONG ).show();
                                        AlertDialog.Builder b = new AlertDialog.Builder(CheckActivity.this);
                                        b.setTitle("查詢結果");
                                        b.setMessage("Logno:\n"+inputnum+"\n\n作業的number:\n"+num+"\n\n"+"供應鏈:\n"+sp_chain.replace(",","->\n"));
                                        b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        b.setCancelable(false);
                                        b.create().show();

                                        is_in_list = true;
                                        break;
                                    }
                                }
                                if(is_in_list){ //有找到logno
                                    reader = new CSVReader(new FileReader(path+"/"+"result_3.csv"));
                                    String locations = "";
                                    i = 0;
                                    while ((nextLine = reader.readNext()) != null) {
                                        if(nextLine[0].equals(String.valueOf(inputnum))){
                                            locations = nextLine[1];
//                                            Log.i("OK",locations);
                                            break;
                                        }
                                    }
                                    visial_bt.setVisibility(View.VISIBLE);
                                    visial_bt.setText("GOOGLE MAP");
                                    String finalLocations = locations;
                                    String finalProductname = productname;
                                    visial_bt.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent();
                                            intent = intent.setClass(CheckActivity.this,MapsActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("location", finalLocations);
                                            bundle.putString("name", finalProductname);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
//                                            finish();
                                        }
                                    });
                                }
                                else{ //沒找到logno
                                    Toast.makeText(CheckActivity.this , "沒有找到您要查詢的PLTT logno" , Toast.LENGTH_SHORT ).show();
                                }
                            }
                            else{
                                Toast.makeText(CheckActivity.this , "請輸入PLTT logno查詢或者選定城市進行查詢" , Toast.LENGTH_SHORT ).show();
                            }

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(CheckActivity.this , "Error"  , Toast.LENGTH_SHORT ).show();
                    }
                }
                else{
                    try {
                        File mSDFile = null;
                        if(Environment.getExternalStorageState().equals( Environment.MEDIA_REMOVED)){
                            Toast.makeText(CheckActivity.this , "沒有SD卡!!!" , Toast.LENGTH_SHORT ).show();
                            return ;
                        }
                        else{
                            mSDFile = Environment.getExternalStorageDirectory();
                            String path=mSDFile.getPath()+"/Download";
                            File file = new File(path+"/"+"result_1.csv");
                            CSVReader reader = new CSVReader(new FileReader(path+"/"+"new_result_3.csv"));
                            String[] nextLine;
                            results = "";
                            ArrayList<String> allList = new ArrayList<String>();
                            while ((nextLine = reader.readNext()) != null) {
                                if (nextLine[2].substring(0,2).equals(text)) {
                                    allList.add(nextLine[0]);
                                }
                            }
//                            Log.i("HHH",String.valueOf(allList.size()));
//                            Log.i("HHH",allList.get(0));

                            CSVReader reader2 = new CSVReader(new FileReader(path+"/"+"result_1.csv"));
                            String[] new_nextLine;
                            while ((new_nextLine = reader2.readNext()) != null) {
                                if (allList.contains(new_nextLine[0])) {
                                    results = results + "Logno:" + new_nextLine[0] + "\n產品名稱:"+ new_nextLine[3] + "\n產出組織:" + new_nextLine[4] + "\n產出地址:" + text + "\n作業數量:" + new_nextLine[1] +  "\n\n";
                                }
                            }
//                            Log.i("TTTT",results);

                            visial_bt.setVisibility(View.VISIBLE);
                            visial_bt.setText("TEXTVIEW");
                            visial_bt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent = intent.setClass(CheckActivity.this,TextActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("all_text", results);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(CheckActivity.this , "Error"  , Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                visial_bt.setVisibility(View.GONE);
                editText.setText("");
                location_name = lunch[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}