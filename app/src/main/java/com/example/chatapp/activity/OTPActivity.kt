package com.example.chatapp.activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.chatapp.MainActivity
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityNumberBinding
import com.example.chatapp.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOtpactivityBinding
    lateinit var auth : FirebaseAuth
    private lateinit var verificationId : String
//    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        // get storedVerificationId from the intent
        val storedVerificationId= intent.getStringExtra("storedVerificationId")
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Please wait..")
        builder.setTitle("Loading")
        builder.setCancelable(false)

//        dialog = builder.create()
//        dialog.show()
//        val phoneNumber = "+91"+intent.getStringExtra("number")
//
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
//                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onVerificationFailed(p0: FirebaseException) {
//                    dialog.dismiss()
//                    Toast.makeText(this@OTPActivity,"Please try Again",Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
//                    super.onCodeSent(p0, p1)
//
//                    dialog.dismiss()
//                    verificationId = p0
//                }
//
//            }).build()

//        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.button.setOnClickListener {
//            dialog.show()
            val otp=binding.otp.text!!.toString()
            if(otp.isEmpty()) {

                Toast.makeText(this, "Please enter otp", Toast.LENGTH_SHORT).show()
            }
            else{
//                dialog.show()
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }

        }

    }
    // verifies if the code matches sent by firebase
    // if success start the new activity in our case it is main Activity
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this , ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
