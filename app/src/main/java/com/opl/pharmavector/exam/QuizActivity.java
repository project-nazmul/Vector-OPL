package com.opl.pharmavector.exam;

import static com.opl.pharmavector.exam.ExamApi.BASE_URL_EXAM;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.opl.pharmavector.Customer;
import com.opl.pharmavector.Dashboard;
import com.opl.pharmavector.JSONParser;
import com.opl.pharmavector.PersonalExpenses;
import com.opl.pharmavector.R;
import com.opl.pharmavector.ServiceHandler;
import com.opl.pharmavector.model.Patient;
import com.opl.pharmavector.prescriptionsurvey.PrescriptionFollowup2;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

public class QuizActivity extends AppCompatActivity {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    private TextView quizQuestion;
    private RadioGroup radioGroup;
    private RadioButton optionOne;
    private RadioButton optionTwo;
    private RadioButton optionThree;
    private RadioButton optionFour;
    private int currentQuizQuestion;
    private int quizCount;
    public  int scorecount = 0;
    private QuizWrapper firstQuestion;
    private List<QuizWrapper> parsedObject;
    public int counter;
    Button home_button, answer_review_button;
    public Button previousButton;
    TextView textView, scoreview, thank_you_note, alertmessage;
    RelativeLayout exam_layout, time_score_layout, pbar;
    public String name;
    public String ename;
    public String mpo_code;
    public String message_3;
    public String new_version;
    public String myexamid;
    public String myexamtime, myexamtimeleft, user_flag;
    public ProgressBar bar_1, bar_2, bar_3;
    public Button nextButton;
    int mycountdown_timer;
    public int success;
    public String message;
    private String submit_my_score =  BASE_URL_EXAM+"save_exam_result.php";
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.quiz_activity_new);
        initViews();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        AsyncJsonObject asyncObject = new AsyncJsonObject();
        asyncObject.execute("");

        mycountdown_timer = ((Integer.parseInt(myexamtimeleft)) * 60000);
        thank_you_note.setVisibility(View.INVISIBLE);
        home_button.setVisibility(View.INVISIBLE);
        answer_review_button.setVisibility(View.INVISIBLE);

        new CountDownTimer(mycountdown_timer, 1000) {
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), "Time Remaining %02d : %02d ",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                textView.setText(text);
                counter++;
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                thank_you_note.setVisibility(View.VISIBLE);
                thank_you_note.setText("Thank you for Your Participation.\n Please Check Correct Answers.");
                textView.setVisibility(View.INVISIBLE);
                scoreview.setText("Your Score: " + String.valueOf(scorecount));
                pbar.setVisibility(View.GONE);
                exam_layout.setVisibility(View.GONE);
                alertmessage.setVisibility(View.GONE);
                home_button.setVisibility(View.VISIBLE);
                answer_review_button.setVisibility(View.VISIBLE);
                //new GetCategories().execute();
            }
        }.start();

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizActivity.this, Dashboard.class);
                i.putExtra("UserName", name);
                i.putExtra("UserName_2", ename);
                i.putExtra("new_version", new_version);
                i.putExtra("message_3", message_3);
                startActivity(i);
            }
        });

        answer_review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        new GetCategories().execute();
                         //postPrescriptionCount();
                        Intent i = new Intent(QuizActivity.this, ExamAnswerBoard.class);
                        i.putExtra("UserName", name);
                        i.putExtra("UserName_2", ename);
                        i.putExtra("new_version", new_version);
                        i.putExtra("message_3", message_3);
                        i.putExtra("myexamid", myexamid);
                        startActivity(i);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int radioSelected = radioGroup.getCheckedRadioButtonId();
                int userSelection = getSelectedAnswer(radioSelected);
                int correctAnswerForQuestion = firstQuestion.getCorrectAnswer();
                if ((currentQuizQuestion + 2) == quizCount) {
                    nextButton.setText("End Exam");
                }

                if (userSelection == correctAnswerForQuestion) {
                    currentQuizQuestion++;
                    scorecount++;
                    previousButton.setText((currentQuizQuestion + 1) + "/" + quizCount);

                    if (currentQuizQuestion >= quizCount) {
                        thank_you_note.setVisibility(View.VISIBLE);
                        alertmessage.setVisibility(View.GONE);
                        thank_you_note.setText("Thank you for Your Participation\n Please wait for Your Score and Answer Sheet.\nDon't Close this screen.");
                        exam_layout.setVisibility(View.GONE);
                        pbar.setVisibility(View.GONE);
                    } else {
                        uncheckedRadioButton();
                        firstQuestion = parsedObject.get(currentQuizQuestion);
                        quizQuestion.setText(firstQuestion.getQuestion());
                        String[] possibleAnswers = firstQuestion.getAnswers().split("//");
                        optionOne.setText(possibleAnswers[0]);
                        optionTwo.setText(possibleAnswers[1]);
                        optionThree.setText(possibleAnswers[2]);
                        optionFour.setText(possibleAnswers[3]);
                    }
                } else {
                    currentQuizQuestion++;
                    previousButton.setText((currentQuizQuestion + 1) + "/" + quizCount);
                    if (currentQuizQuestion == quizCount) {}
                    if (currentQuizQuestion >= quizCount) {
                        thank_you_note.setVisibility(View.VISIBLE);
                        alertmessage.setVisibility(View.GONE);
                        thank_you_note.setText("Thank you for Your Participation\n Please wait for Answer Sheet.");
                        exam_layout.setVisibility(View.GONE);
                        pbar.setVisibility(View.GONE);
                    } else {
                        uncheckedRadioButton();
                        firstQuestion = parsedObject.get(currentQuizQuestion);
                        quizQuestion.setText(firstQuestion.getQuestion());
                        String[] possibleAnswers = firstQuestion.getAnswers().split("//");
                        optionOne.setText(possibleAnswers[0]);
                        optionTwo.setText(possibleAnswers[1]);
                        optionThree.setText(possibleAnswers[2]);
                        optionFour.setText(possibleAnswers[3]);
                    }
                }
            }
        });
    }

    private void initViews() {
        exam_layout = (RelativeLayout) findViewById(R.id.exam_layout);
        time_score_layout = (RelativeLayout) findViewById(R.id.time_score_layout);
        pbar = (RelativeLayout) findViewById(R.id.pbar);
        quizQuestion = (TextView) findViewById(R.id.quiz_question);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        optionOne = (RadioButton) findViewById(R.id.radio0);
        optionTwo = (RadioButton) findViewById(R.id.radio1);
        optionThree = (RadioButton) findViewById(R.id.radio2);
        optionFour = (RadioButton) findViewById(R.id.radio3);
        previousButton = (Button) findViewById(R.id.previousquiz);
        home_button = (Button) findViewById(R.id.home_button);
        answer_review_button = (Button) findViewById(R.id.answer_review_button);
        alertmessage = (TextView) findViewById(R.id.alertmessage);
        nextButton = (Button) findViewById(R.id.nextquiz);
        textView = (TextView) findViewById(R.id.textView);
        scoreview = (TextView) findViewById(R.id.scoreview);
        thank_you_note = (TextView) findViewById(R.id.thank_you_note);

        Bundle b = getIntent().getExtras();
        assert b != null;
        name = b.getString("mpo_code");
        ename = b.getString("territory_name");
        mpo_code = b.getString("mpo_code");
        new_version = b.getString("new_version");
        message_3 = b.getString("message_3");
        myexamid = b.getString("myexamid");
        myexamtime = b.getString("myexamtime");
        myexamtimeleft = b.getString("myexamtime");
        user_flag = b.getString("user_flag");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncJsonObject extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost( BASE_URL_EXAM+"examquestion.json");
            String jsonResult = " ";
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("mpo_code", name));
                nameValuePairs.add(new BasicNameValuePair("territory_code", name));
                nameValuePairs.add(new BasicNameValuePair("myexamid", myexamid));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            parsedObject = returnParsedJsonObject(result);
            quizCount = parsedObject.size();
            firstQuestion = parsedObject.get(0);
            quizQuestion.setText(firstQuestion.getQuestion());
            String[] possibleAnswers = firstQuestion.getAnswers().split("//");
            previousButton.setText("1" + "/" + quizCount);
            optionOne.setText(possibleAnswers[0]);
            optionTwo.setText(possibleAnswers[1]);
            optionThree.setText(possibleAnswers[2]);
            optionFour.setText(possibleAnswers[3]);
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return answer;
        }
    }

    class GetCategories extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Bundle b = getIntent().getExtras();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("mpo_code", name));
            params.add(new BasicNameValuePair("territory_code", name));
            params.add(new BasicNameValuePair("myexamid", myexamid));
            params.add(new BasicNameValuePair("exam_score", String.valueOf(scorecount)));
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(submit_my_score, ServiceHandler.POST, params);

            if (json != null) {
                try {
                    Log.e("scorejson-->", json);
                } finally {}
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

/*
    public void postPrescriptionCount() {
        ExamApiInterface apiInterface = ExamApi.getApiClient().create(ExamApiInterface.class);
        Call<List<Patient>> call = apiInterface.postScore(name, myexamid, String.valueOf(scorecount));
        call.enqueue(new Callback<List<Patient>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Patient>> call, retrofit2.Response<List<Patient>> response) {
                List<Patient> giftitemCount = response.body();
                if (response.isSuccessful()) {
                    Log.e("response-->", String.valueOf(response.body()));
                } else {
                    Toast.makeText(QuizActivity.this, "Server error! Please try moments later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                postPrescriptionCount();
            }
        });
    }
*/

    private List<QuizWrapper> returnParsedJsonObject(String result) {
        List<QuizWrapper> jsonObject = new ArrayList<QuizWrapper>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        QuizWrapper newItemObject = null;

        try {
            resultObject = new JSONObject(result);
            jsonArray = resultObject.optJSONArray("quiz_questions");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length() - 1; i++) {
                JSONObject jsonChildNode = null;

                try {
                    jsonChildNode = jsonArray.getJSONObject(i);
                    int id = jsonChildNode.getInt("id");
                    String question = jsonChildNode.getString("question");
                    String answerOptions = jsonChildNode.getString("possible_answers");
                    int correctAnswer = jsonChildNode.getInt("correct_answer");
                    newItemObject = new QuizWrapper(id, question, answerOptions, correctAnswer);
                    jsonObject.add(newItemObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    private int getSelectedAnswer(int radioSelected) {
        int answerSelected = 0;
        if (radioSelected == R.id.radio0) {
            answerSelected = 1;
        }
        if (radioSelected == R.id.radio1) {
            answerSelected = 2;
        }
        if (radioSelected == R.id.radio2) {
            answerSelected = 3;
        }
        if (radioSelected == R.id.radio3) {
            answerSelected = 4;
        }
        return answerSelected;
    }

    private void uncheckedRadioButton() {
        radioGroup.clearCheck();
    }

    @Override
    public void onBackPressed() {}
}
