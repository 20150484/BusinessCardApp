package com.example.businesscard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.google.android.material.button.MaterialButton;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static String IP_ADDRESS = "172.30.77.131"; //10.0.2.2 //본인 IP주소를 넣으세요.
    private static String TAG = "phptest"; //phptest log 찍으려는 용도

    private TextView signup_id;
    private TextView signup_pwd;
    private TextView signup_pwd2;
    private TextView signup_name;
    private Button signupBtn;
    private ImageView back;
    private EditText signup_phone;
    private ImageView imageView2;

    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_id = (EditText) findViewById(R.id.id);
        signup_pwd = (EditText) findViewById(R.id.password);
        signup_pwd2 = (EditText) findViewById(R.id.password_check);
        signup_name = (EditText) findViewById(R.id.username);
        signup_phone = (EditText) findViewById(R.id.phone);
        signupBtn = (Button) findViewById(R.id.signupBtn);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signup_id.getText().toString().trim();
                String pwd = signup_pwd.getText().toString().trim();
                String pwdcheck = signup_pwd2.getText().toString().trim();
                String name = signup_name.getText().toString().trim();
                String phone = signup_phone.getText().toString().trim();

                //회원가입을 할 때 예외 처리를 해준 것이다.
                if (email.equals("")  || pwd.equals("") || pwdcheck.equals("") || name.equals("")|| phone.equals("") )
                {
                    Toast.makeText(SignUpActivity.this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {

                    if(pwd.equals(pwdcheck)) {
                        if(pwd.length()<=5){
                            Toast.makeText(SignUpActivity.this, "비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!email.contains("@") || !email.contains(".com")){

                            Toast.makeText(SignUpActivity.this, "아이디에 @ 및 .com을 포함시키세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(phone.contains("-") || !(Integer.parseInt(String.valueOf(phone.charAt(1)))==1)){

                            Toast.makeText(SignUpActivity.this,"올바른 전화번호 형식으로 입력해주세요..", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            InsertData task = new InsertData(); //PHP 통신을 위한 InsertData 클래스의 task 객체 생성
                            //본인이 접속할 PHP 주소와 보낼 데이터를 입력해준다.
                            task.execute("http://"+IP_ADDRESS+"/singup.php",email,email,pwd,phone,name);
                            Toast.makeText(SignUpActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "비밀번호가 일치 하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    class InsertData extends AsyncTask<String,Void,String> { // 통신을 위한 InsertData 생성
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행 다이얼로그 생성
            progressDialog = ProgressDialog.show(SignUpActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss(); //onPostExcute 에 오게되면 진행 다이얼로그 취소
            mTextViewResult.setText(result); //xml의 작은 공간인 mTextViewResult에 result값 넣기
            Log.d(TAG, "POST response  - " + result); // result 값 확인하기
        }


        @Override
        protected String doInBackground(String... params) {
            /*
            PHP 파일을 실행시킬 수 있는 주소와 전송할 데이터를 준비한다.
            POST 방식으로 데이터 전달시에는 데이터가 주소에 직접 입력되지 않는다.
            이 값들은 InsertData 객체.excute 에서 매개변수로 준 값들이 배열 개념으로 차례대로 들어가
            값을 받아오는 개념이다.
             */
            String serverURL = (String) params[0];

            String useremail = (String)params[1];
            String userid = (String)params[2];
            String userpw = (String)params[3];
            String userphone = (String)params[4];
            String username = (String)params[5];


            /*
            HTTP 메세지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야한다.
            전송할 데이터는 "이름=값" 형식이며 여러 개를 보내야 할 경우에 항목 사이에 &를 추가해준다.
            여기에 적어준 이름들은 나중에 PHP에서 사용하여 값을 얻게 된다.
             */
            String postParameters ="useremail="+useremail+"&userid="+ userid
                    +"&userpw="+userpw+"&userphone="+userphone+"&username="+username;

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

                Log.d("php 값 :", sb.toString());


                //저장된 데이터를 스트링으로 변환하여 리턴값으로 받는다.
                return  sb.toString();


            }

            catch (Exception e) {

                Log.d(TAG, "InsertData: Error",e);

                return  new String("Error " + e.getMessage());

            }

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}