package com.example.businesscard;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import com.example.businesscard.SignUpActivity;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "172.30.77.131"; //10.0.2.2 //본인 IP주소를 넣으세요.
    private static String TAG = "phptest"; //phptest log 찍으려는 용도

    private TextView login_id;
    private TextView login_pw;
    public static String id;
    private String username;
    private String userLoginId;
    private String password;

    public static String getId() {
        return id;
    }
    private String userphone;
    private String email;
    private String test = "김민수";
    private Button login_button;
    private MyProfileFragment profile = new MyProfileFragment();
    private Button noAccount_button;

    String result = "";
    StringBuilder resultJson;
    SQLiteDatabase sqlDB;
    Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = (EditText) findViewById(R.id.editText_id);
        login_pw = (EditText) findViewById(R.id.editText_password);
        login_button = (Button) findViewById(R.id.btn_login);
        noAccount_button = (Button) findViewById(R.id.btn_noAccount);


        noAccount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myDBHelper dbHelper = new myDBHelper(LoginActivity.this);
//                dbHelper.onUpgrade(sqlDB,0,1);
//                Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent_main);
                String id = login_id.getText().toString().trim();
                String pw = login_pw.getText().toString().trim();

                //회원가입을 할 때 예외 처리를 해준 것이다.
                if (id.equals("") || pw.equals("")) {
                    Toast.makeText(LoginActivity.this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pw.length() <= 5) {
                        Toast.makeText(LoginActivity.this, "비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (!id.contains("@") || !id.contains(".com")) {
                        Toast.makeText(LoginActivity.this, "아이디에 @ 및 .com을 포함시키세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        InsertData task = new LoginActivity.InsertData(); //PHP 통신을 위한 InsertData 클래스의 task 객체 생성
                        //본인이 접속할 PHP 주소와 보낼 데이터를 입력해준다.
                        task.execute("http://" + IP_ADDRESS + "/login.php", id, pw);
                    }
                }
            }
        });
    }

    class InsertData extends AsyncTask<String,Void,String> { // 통신을 위한 InsertData 생성
        ProgressDialog progressDialog;


        @Override
        protected String doInBackground(String... params) {
            /*
            PHP 파일을 실행시킬 수 있는 주소와 전송할 데이터를 준비한다.
            POST 방식으로 데이터 전달시에는 데이터가 주소에 직접 입력되지 않는다.
            이 값들은 InsertData 객체.excute 에서 매개변수로 준 값들이 배열 개념으로 차례대로 들어가
            값을 받아오는 개념이다.
             */
            String serverURL = (String) params[0];

            String userid = (String)params[1];
            String userpw = (String)params[2];


            /*
            HTTP 메세지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야한다.
            전송할 데이터는 "이름=값" 형식이며 여러 개를 보내야 할 경우에 항목 사이에 &를 추가해준다.
            여기에 적어준 이름들은 나중에 PHP에서 사용하여 값을 얻게 된다.
             */
            String postParameters ="&userid="+ userid +"&userpw="+userpw;

            try{ // HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송한다.
                URL url = new URL(serverURL); //주소가 저장된 변수를 이곳에 입력한다.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000); //5초안에 응답이 오지 않으면 예외가 발생한다.

                httpURLConnection.setConnectTimeout(5000); //5초안에 연결이 안되면 예외가 발생한다.

                httpURLConnection.setRequestMethod("POST"); //요청 방식을 POST로 한다.

                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();

                //전송할 데이터가 저장된 변수를 이곳에 입력한다. 인코딩을 고려해줘야 하기 때문에 UTF-8 형식으로 넣어준다.
                outputStream.write(postParameters.getBytes("UTF-8"));

                Log.d("phpPostParameters데이터 : ",postParameters); //postParameters의 값이 정상적으로 넘어왔나 Log를 찍어줬다.

                outputStream.flush();//현재 버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비운다.
                outputStream.close(); //객체를 닫음으로써 자원을 반납한다.


                int responseStatusCode = httpURLConnection.getResponseCode(); //응답을 읽는다.
                Log.d(TAG, "POST response code-" + responseStatusCode);

                InputStream inputStream;

                if(responseStatusCode == httpURLConnection.HTTP_OK){ //만약 정상적인 응답 데이터 라면
                    inputStream=httpURLConnection.getInputStream();
                    Log.d("php정상: ","정상적으로 출력"); //로그 메세지로 정상적으로 출력을 찍는다.
                }
                else {
                    inputStream = httpURLConnection.getErrorStream(); //만약 에러가 발생한다면
                    Log.d("php비정상: ","비정상적으로 출력"); // 로그 메세지로 비정상적으로 출력을 찍는다.
                }

                // StringBuilder를 사용하여 수신되는 데이터를 저장한다.
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) !=null ) {
                    sb.append(line);

                }

                bufferedReader.close();
                StringBuilder sbTemp1 = new StringBuilder();
                StringBuilder sbTemp2 = new StringBuilder();
                StringBuilder sbTemp3 = new StringBuilder();
                StringBuilder sbTemp4 = new StringBuilder();
                StringBuilder sbTemp5 = new StringBuilder();
                StringBuilder sbTemp6 = new StringBuilder();
                for(int i=1; i<=6; i++){
                    if(i == 1) {
                        String temp = sb.substring(sb.indexOf(":")+1);
                        sbTemp1.append(temp);
                        String temp2 = sbTemp1.substring(0,sbTemp1.indexOf(","));
                        id = temp2.replaceAll("\"", "");
                        Log.d("키값", id);
                    }else if(i == 2){
                        String temp = sbTemp1.substring(sbTemp1.indexOf(":")+1);
                        sbTemp2.append(temp);
                        String temp2 = sbTemp2.substring(0,sbTemp2.indexOf(","));
                        username = temp2.replaceAll("\"", "");
                        Log.d("이름", username);
                    }else if(i == 3){
                        String temp = sbTemp2.substring(sbTemp2.indexOf(":")+1);
                        sbTemp3.append(temp);
                        String temp2 = sbTemp3.substring(0,sbTemp3.indexOf(","));
                        userLoginId = temp2.replaceAll("\"", "");
                        Log.d("아이디", userLoginId);
                    }else if(i == 4){
                        String temp = sbTemp3.substring(sbTemp3.indexOf(":")+1);
                        sbTemp4.append(temp);
                        String temp2 = sbTemp4.substring(0,sbTemp4.indexOf(","));
                        password = temp2.replaceAll("\"", "");
                        Log.d("비밀번호", password);
                    }else if(i == 5){
                        String temp = sbTemp4.substring(sbTemp4.indexOf(":")+1);
                        sbTemp5.append(temp);
                        String temp2 = sbTemp5.substring(0,sbTemp5.indexOf(","));
                        email = temp2.replaceAll("\"", "");
                        Log.d("이메일", email);
                    }else if(i == 6){
                        String temp = sbTemp5.substring(sbTemp5.indexOf(":")+1);
                        sbTemp6.append(temp);
                        String temp2 = sbTemp6.substring(0,sbTemp6.indexOf("}"));
                        userphone = temp2.replaceAll("\"", "");
                        Log.d("전화번호", userphone);
                    }
                }
                result = sb.toString();
                resultJson = sb;

                if(result.equals("false")){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 0);
                }
//
//
//
                else {
                    Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent_main);
//                    Intent intent_profile = new Intent(LoginActivity.this, MyProfileFragment.class);
//                    intent_profile.putExtra("name",username);
                }

                //저장된 데이터를 스트링으로 변환하여 리턴값으로 받는다.
                return  sb.toString();
            }

            catch (Exception e) {

                Log.d(TAG, "InsertData: Error",e);

                return  new String("Error " + e.getMessage());

            }

        }
    }
    public static class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "businesscard", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
//                onCreate(db);
                db.execSQL("CREATE TABLE IF NOT EXISTS Member ( Id CHAR(30) PRIMARY KEY,Password CHAR(30), Name CHAR(30)," +
                        " Email CHAR(30), Phone CHAR(30));");
//                db.execSQL("DROP TABLE IF EXISTS CardModel");
                db.execSQL("CREATE TABLE IF NOT EXISTS MyCard(UserName CHAR(30), Company CHAR(30),Department CHAR(30), Address CHAR(30)," +
                        "Position CHAR(30), Mobile CHAR(30), Tel CHAR(30), Fax CHAR(30), Email CHAR(30) PRIMARY KEY, Homepage CHAR(30))");
                db.execSQL("CREATE TABLE IF NOT EXISTS CardModel(UserName CHAR(30), Company CHAR(30),Department CHAR(30), Address CHAR(30)," +
                        "Position CHAR(30), Mobile CHAR(30), Tel CHAR(30), Fax CHAR(30), Email CHAR(30) PRIMARY KEY, Homepage CHAR(30))");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("여기", e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS Member");
//            db.execSQL("DROP TABLE IF EXISTS CardModel");
            onCreate(db);

        }

    }

}