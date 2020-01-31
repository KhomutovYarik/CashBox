//package com.example.cashbox;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.os.AsyncTask;
//import android.view.View;
//
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
//public class SMSAuth extends AsyncTask<Void,Void,Void> {
//    private ProgressDialog dialog;
//    private String phone;
//    private Activity activity;
//    FirebaseAuth fbauth;
//    String sentCode;
//    public SMSAuth(MainActivity activity, String phone)
//    {
//        this.activity = activity;
//        dialog = new ProgressDialog(activity);
//        this.phone = phone;
//    }
//
//    @Override
//    protected void onPreExecute(){
//        dialog.setMessage("Doing something, please wait.");
//        dialog.show();
//    }
//
//    @Override
//    protected Void doInBackground(Void ... args){
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void result) {
//        if (dialog.isShowing()) {
//            dialog.dismiss();
//        }
//    }
//    private void sendVerificationCode()
//    {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phone,        // Phone number to verify
//                120,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                activity,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//    }
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            sentCode = s;
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
//            builder.setTitle("Код отправлен")
//                    .setMessage("Код подтверждения отправлен на указанный Вами номер телефона")
//                    .setCancelable(false)
//                    .setPositiveButton("ОК",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    code.requestFocus();
//                                    dialog.cancel();
//                                }
//                            });
//            AlertDialog alert = builder.create();
//            alert.show();
//
//            phoneNumber.setEnabled(false);
//            send.setVisibility(View.INVISIBLE);
//            codeLbl.setVisibility(View.VISIBLE);
//            code.setVisibility(View.VISIBLE);
//            resend.setVisibility(View.VISIBLE);
//            signin.setVisibility(View.VISIBLE);
//        }
//    };
//}
