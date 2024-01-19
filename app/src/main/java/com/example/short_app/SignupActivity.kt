package com.example.short_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.short_app.databinding.ActivitySignupBinding
import com.example.short_app.model.UserModel
import com.example.short_app.util.UiUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener{
            singup()
        }

        binding.goToLoginBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }

    fun setInprogress(inProgress : Boolean){
        if(inProgress){
            binding.progressBar.visibility= View.VISIBLE
            binding.submitBtn.visibility=View.GONE
        }else{
            binding.progressBar.visibility=View.GONE
            binding.submitBtn.visibility=View.VISIBLE
        }
    }
    fun singup(){
        val  email = binding.emailInput.text.toString();
        val password = binding.passwordInput.text.toString();
        val confirmPassword = binding.confromInput.text.toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.setError("Email is not valid")
        }
        if(password.length<6){
            binding.passwordInput.setError("Minimum 6 character required")
        }
        if(password!=confirmPassword){
            binding.confromInput.setError("Password not matched")
            return
        }

        singupWithFirebase(email,password)
    }
    fun singupWithFirebase(email:String,password:String){
        setInprogress(true)

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email,password
        ).addOnSuccessListener {
            it.user?.let { user->
                val userModel = UserModel(user.uid,email.substringBefore("@"))
                Firebase.firestore.collection("users")
                    .document(user.uid)
                    .set(userModel).addOnSuccessListener {
                        UiUtil.showToast(applicationContext,"Account Created successfully")
                        setInprogress(false)
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()

                    }
            }
        }.addOnFailureListener{
            UiUtil.showToast(applicationContext,it.localizedMessage?:"Something went wrong")
            setInprogress(false)
        }

    }
}