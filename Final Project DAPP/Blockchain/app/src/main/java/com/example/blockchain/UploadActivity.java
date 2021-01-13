package com.example.blockchain;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.ikovac.timepickerwithseconds.TimePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UploadActivity extends AppCompatActivity {

    int REQUEST_IMAGE_CAPTURE = 1;
    int ACTION_ALBUM_REQUEST_CODE = 2;
    ImageView img_view;
    Button back_main_bt,upload_bt,add_activity_bt;
    Button save_bt,delete_bt,add_bt;
    EditText p_name_edit_text,p_org_edit_text,p_out_date_edit_text,logno_edit_text;
    WebView webView;
    TextView all_activity;
    ScrollView scrollView;
    private boolean is_pic_pick,is_datepick;
    String img_path="";
    String act_name = "";
    String act_begin = "";
    String act_end = "";
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        is_pic_pick = false;

        TextView p_name_text = findViewById(R.id.textView);
        TextView p_org_text = findViewById(R.id.textView2);
        TextView p_out_date_text = findViewById(R.id.textView3);
        TextView logno = findViewById(R.id.textView5);

        all_activity = findViewById(R.id.textView4);
        all_activity.setText("");
        scrollView = findViewById(R.id.scrollView2);

        back_main_bt = findViewById(R.id.button_second);
        upload_bt = findViewById(R.id.button_upload);
        add_activity_bt = findViewById(R.id.button4);
        save_bt = findViewById(R.id.button5);
        delete_bt = findViewById(R.id.button6);
        add_bt = findViewById(R.id.button7);

        webView = findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.loadUrl("file:///android_asset/api.html");

        p_name_edit_text = findViewById(R.id.editTextTextMultiLine);
        p_org_edit_text = findViewById(R.id.editTextTextMultiLine2);
        p_out_date_edit_text = findViewById(R.id.editTextDate);
        logno_edit_text = findViewById(R.id.editTextNumberDecimal);
        img_view = findViewById(R.id.imageView);


        back_main_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent = intent.setClass(UploadActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        upload_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(all_activity.getText().toString().equals("")) {
                    if (!img_path.equals("")) {
                        if (!logno_edit_text.getText().toString().equals("") && !p_name_edit_text.getText().toString().equals("") && !p_org_edit_text.getText().toString().equals("") && !p_out_date_edit_text.getText().toString().equals("")) {
                            openWebView(img_path, logno_edit_text.getText().toString(), p_name_edit_text.getText().toString(), p_org_edit_text.getText().toString(), p_out_date_edit_text.getText().toString());
                        }
                        else{
                            Toast.makeText(UploadActivity.this , "信息缺失，請確保所有訊息都有填寫" , Toast.LENGTH_LONG ).show();
                        }
                    }
                    else{
                        Toast.makeText(UploadActivity.this , "缺失圖片，請拍照或者從相簿中選擇圖片上傳" , Toast.LENGTH_LONG ).show();
                    }
                }
                else{
                    if (!img_path.equals("")) {
                        if (!logno_edit_text.getText().toString().equals("") && !p_name_edit_text.getText().toString().equals("") && !p_org_edit_text.getText().toString().equals("") && !p_out_date_edit_text.getText().toString().equals("")) {
                            String activity_text = (String) all_activity.getText();
                            String[] segment_list = activity_text.split("\n\n");
                            openWebView_act(img_path, logno_edit_text.getText().toString(), p_name_edit_text.getText().toString(), p_org_edit_text.getText().toString(), p_out_date_edit_text.getText().toString(),segment_list);
                        }
                        else{
                            Toast.makeText(UploadActivity.this , "信息缺失，請確保所有訊息都有填寫" , Toast.LENGTH_LONG ).show();
                        }
                    }
                    else{
                        Toast.makeText(UploadActivity.this , "缺失圖片，請拍照或者從相簿中選擇圖片上傳" , Toast.LENGTH_LONG ).show();
                    }
                }
            }
        });

        add_activity_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!img_path.equals("")) {
                    if(is_pic_pick==true && !logno_edit_text.getText().toString().equals("") && !p_name_edit_text.getText().toString().equals("") && !p_org_edit_text.getText().toString().equals("") && !p_out_date_edit_text.getText().toString().equals("")) {
                        add_activity_bt.setVisibility(View.INVISIBLE);
                        p_name_edit_text.setVisibility(View.INVISIBLE);
                        p_org_edit_text.setVisibility(View.INVISIBLE);
                        p_out_date_edit_text.setVisibility(View.INVISIBLE);
                        logno_edit_text.setVisibility(View.INVISIBLE);
                        img_view.setVisibility(View.INVISIBLE);
                        p_name_text.setVisibility(View.INVISIBLE);
                        p_org_text.setVisibility(View.INVISIBLE);
                        logno.setVisibility(View.INVISIBLE);
                        p_out_date_text.setVisibility(View.INVISIBLE);

                        save_bt.setVisibility(View.VISIBLE);
                        delete_bt.setVisibility(View.VISIBLE);
                        add_bt.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.VISIBLE);
                        all_activity.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        delete_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_activity.setText("");
            }
        });

        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(UploadActivity.this);
                final View textEntryView = factory.inflate(R.layout.text_entry,null);

                TextView text1 = (TextView) textEntryView.findViewById(R.id.textView11);
                TextView text2 = (TextView) textEntryView.findViewById(R.id.textView12);
                TextView text3 = (TextView) textEntryView.findViewById(R.id.textView13);

                final EditText input1 = (EditText) textEntryView.findViewById(R.id.editTextTextMultiLine3);
                final EditText input2 = (EditText) textEntryView.findViewById(R.id.editTextDate2);
                final EditText input3 = (EditText) textEntryView.findViewById(R.id.editTextDate3);

                input2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar m_Calendar = Calendar.getInstance();
                        DatePickerDialog datelog = new DatePickerDialog(UploadActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                m_Calendar.set(Calendar.YEAR, year);
                                m_Calendar.set(Calendar.MONTH, month);
                                m_Calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                String myFormat = "yyyy-MM-dd"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                                input2.setText(sdf.format(m_Calendar.getTime()));
                            }
                        }, m_Calendar.get(Calendar.YEAR), m_Calendar.get(Calendar.MONTH), m_Calendar.get(Calendar.DAY_OF_MONTH));

                        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(UploadActivity.this, new MyTimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {
                                m_Calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                m_Calendar.set(Calendar.MINUTE, minute);
                                m_Calendar.set(Calendar.SECOND, seconds);
                                String myFormat = "HH:mm:ss"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                                input2.setText(input2.getText()+" "+sdf.format(m_Calendar.getTime()));
                            }
                        }, m_Calendar.get(Calendar.HOUR_OF_DAY), m_Calendar.get(Calendar.MINUTE), m_Calendar.get(Calendar.SECOND), true);

                        mTimePicker.show();
                        datelog.show();
                    }
                });

                input3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar m_Calendar = Calendar.getInstance();
                        DatePickerDialog datelog = new DatePickerDialog(UploadActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                m_Calendar.set(Calendar.YEAR, year);
                                m_Calendar.set(Calendar.MONTH, month);
                                m_Calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                String myFormat = "yyyy-MM-dd"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                                input3.setText(sdf.format(m_Calendar.getTime()));
                            }
                        }, m_Calendar.get(Calendar.YEAR), m_Calendar.get(Calendar.MONTH), m_Calendar.get(Calendar.DAY_OF_MONTH));

                        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(UploadActivity.this, new MyTimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {
                                m_Calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                m_Calendar.set(Calendar.MINUTE, minute);
                                m_Calendar.set(Calendar.SECOND, seconds);
                                String myFormat = "HH:mm:ss"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                                input3.setText(input3.getText()+" "+sdf.format(m_Calendar.getTime()));
                            }
                        }, m_Calendar.get(Calendar.HOUR_OF_DAY), m_Calendar.get(Calendar.MINUTE), m_Calendar.get(Calendar.SECOND), true);

                        mTimePicker.show();
                        datelog.show();
                    }
                });

                AlertDialog.Builder b = new AlertDialog.Builder(UploadActivity.this);
                b.setTitle("新增PLTT作業");
                b.setView(textEntryView);
                b.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!input1.getText().toString().equals("") && !input2.getText().toString().equals("") && !input3.getText().toString().equals("")){
                            all_activity.setText(all_activity.getText() + "Activity Name:" + input1.getText() + "\n" + "Begin Time:" + input2.getText() + "\n" + "End Time:" + input3.getText() + "\n\n");
                        }
                        else{

                            Toast.makeText(UploadActivity.this , "信息缺失，請確保所有訊息都有填寫" , Toast.LENGTH_LONG ).show();
                        }
                        dialog.dismiss();
                    }
                });
                b.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                b.show();

            }
        });

        save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_bt.setVisibility(View.INVISIBLE);
                delete_bt.setVisibility(View.INVISIBLE);
                add_bt.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
                all_activity.setVisibility(View.INVISIBLE);

                add_activity_bt.setVisibility(View.VISIBLE);
                p_name_edit_text.setVisibility(View.VISIBLE);
                p_org_edit_text.setVisibility(View.VISIBLE);
                p_out_date_edit_text.setVisibility(View.VISIBLE);
                logno_edit_text.setVisibility(View.VISIBLE);
                img_view.setVisibility(View.VISIBLE);
                p_name_text.setVisibility(View.VISIBLE);
                p_org_text.setVisibility(View.VISIBLE);
                logno.setVisibility(View.VISIBLE);
                p_out_date_text.setVisibility(View.VISIBLE);
            }
        });

        img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(UploadActivity.this);
                dialog.setTitle("選擇相片來源");
                dialog.setNegativeButton("相簿照片", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK);
                        pickPictureIntent.setType("image/*");
                        startActivityForResult(pickPictureIntent,ACTION_ALBUM_REQUEST_CODE);
                    }
                });
                dialog.setPositiveButton("相機拍攝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                });
                dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        Calendar m_Calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener datepicker2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                m_Calendar.set(Calendar.YEAR, year);
                m_Calendar.set(Calendar.MONTH, month);
                m_Calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                p_out_date_edit_text.setText(sdf.format(m_Calendar.getTime()));
                is_datepick = true;
            }
        };

        MyTimePickerDialog.OnTimeSetListener mtimePicker = new MyTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {
                m_Calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                m_Calendar.set(Calendar.MINUTE, minute);
                m_Calendar.set(Calendar.SECOND, seconds);
                String myFormat = "HH:mm:ss"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                p_out_date_edit_text.setText(p_out_date_edit_text.getText()+" "+sdf.format(m_Calendar.getTime()));
            }
        };

        p_out_date_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datelog = new DatePickerDialog(UploadActivity.this,datepicker2,m_Calendar.get(Calendar.YEAR),

                        m_Calendar.get(Calendar.MONTH),
                        m_Calendar.get(Calendar.DAY_OF_MONTH));

                MyTimePickerDialog mTimePicker = new MyTimePickerDialog(UploadActivity.this,mtimePicker,m_Calendar.get(Calendar.HOUR_OF_DAY), m_Calendar.get(Calendar.MINUTE), m_Calendar.get(Calendar.SECOND), true);

                mTimePicker.show();
                datelog.show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ACTION_ALBUM_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            try {
                Uri uri = data.getData();
//                Log.i("TAG",uri.getPath());
                ContentResolver cr = this.getContentResolver();
                img_path = getFilePathFromContentUri(uri,cr);
//                Log.i("TAG",img_path);
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                img_view.setImageBitmap(bitmap);
                is_pic_pick = true;

            } catch (Exception e) {
                e.printStackTrace();
                Log.i(String.valueOf(UploadActivity.this), "錯誤");
            }
        }
        else if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK && data!=null){
            try{
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                saveImage(imageBitmap);
                img_view.setImageBitmap(imageBitmap);
                is_pic_pick = true;
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }

        }
    }

    public void saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "pic");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        img_path = appDir +"/"+ fileName;
        Log.i("TAG",img_path);
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                   ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    private void openWebView(String fpath, String logno_num, String product_name, String product_org, String product_date) {
        webView.loadUrl("javascript:upload('" + logno_num + "','" + product_name + "','" + product_org + "','" + product_date +"','"+ img_path + "')");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(UploadActivity.this);
                b.setTitle("系統提示");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });
    }

    private void openWebView_act(String fpath, String logno_num, String product_name, String product_org, String product_date, String[] activity_list) {

        String new_act_string = "";
        for (String s : activity_list){
            String sub_act_string = "";
            String[] sub_activity_list = s.split("\n");
            for(String sub : sub_activity_list){
                sub_act_string = sub_act_string + sub + "$$";
            }
            new_act_string = new_act_string + sub_act_string + "##";
        }
        webView.loadUrl("javascript:upload_act('" + logno_num + "','" + product_name + "','" + product_org + "','" + product_date + "','" + new_act_string + "')");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b2 = new AlertDialog.Builder(UploadActivity.this);
                b2.setTitle("系統提示");
                b2.setMessage(message);
                b2.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b2.setCancelable(false);
                b2.create().show();
                return true;
            }

        });

    }
}