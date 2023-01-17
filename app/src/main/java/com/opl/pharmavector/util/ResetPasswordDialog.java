package com.opl.pharmavector.util;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opl.pharmavector.Login;
import com.opl.pharmavector.remote.ApiClient;
import com.opl.pharmavector.remote.ApiInterface;
import com.opl.pharmavector.model.Patient;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.opl.pharmavector.R;

public  class ResetPasswordDialog extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_forget_password, container, false);

        EditText email = v.findViewById(R.id.email);
        Button resetEmailButton = v.findViewById(R.id.resetButton);
        ImageView iv_loading = v.findViewById(R.id.iv_loading);
        TextView pleasewait=v.findViewById(R.id.pleasewait);
        TextView forget_password_description=v.findViewById(R.id.forget_password_description);


        iv_loading.setVisibility(View.GONE);
        pleasewait.setVisibility(View.GONE);

        resetEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText())) {
                    Toast.makeText(getActivity(), "Phone Number field is empty", Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().toString().length()<11 && email.getText().toString().length()>11) {
                    Toast.makeText(getActivity(), "Enter a Valid Phone Number", Toast.LENGTH_SHORT).show();
                }
                else {
                    pleasewait.setVisibility(View.VISIBLE);
                    String phonenumber = email.getText().toString().trim();
                    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<Patient> call = apiInterface.sendotpforpassword(phonenumber);
                    call.enqueue(new Callback<Patient>() {
                        @Override
                        public void onResponse(Call<Patient> call, Response<Patient> response) {
                            assert response.body() != null;
                            String value = response.body().getValue();
                            String message = response.body().getMassage();
                            Log.e("smsResponseValue==>",value);
                            Log.e("smsResponseMessage==>",message);

                            if (value.equals("1")) {
                                pleasewait.setVisibility(View.GONE);
                                iv_loading.setVisibility(View.GONE);
                                String otp = response.body().getPhone_number();
                                String ffrole = response.body().getDoctorCode();
                                Intent createAccountIntent = new Intent(getContext(), SubmitNewPassActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("phoneNumber",email.getText().toString().trim());
                                bundle.putString("otpSLnumber",otp);
                                bundle.putString("ffrole",ffrole);
                                createAccountIntent.putExtras(bundle);
                                startActivity(createAccountIntent);

                            }else if (value.equals("2")) {
                                iv_loading.setVisibility(View.GONE);
                                pleasewait.setVisibility(View.VISIBLE);
                                pleasewait.setText(message);
                            }else {
                                pleasewait.setVisibility(View.GONE);
                                iv_loading.setVisibility(View.GONE);
                                Intent loginIntent = new Intent(getContext(), Login.class);
                                startActivity(loginIntent);
                            }

                        }

                        @Override
                        public void onFailure(Call<Patient> call, Throwable t) {

                        }
                    });


                }
            }
        });

        return v;

    }



}
