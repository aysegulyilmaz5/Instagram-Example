package com.aysegulyilmaz.kotlin_instagram.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aysegulyilmaz.kotlin_instagram.databinding.ActivityMainBinding
import com.aysegulyilmaz.kotlin_instagram.view.FeedActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        if(currentUser != null ){
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun signin(view: View){
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        if(email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter email and password!",Toast.LENGTH_LONG).show()

        }else {
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent = Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }


    }
    fun signup(view:View){
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if(email.equals("") || password.equals("")){
            Toast.makeText(this,"Enter email and password!",Toast.LENGTH_LONG).show()

        }else{
            //Kullandığımız listener cevabı dinliyor bu sayede cevap geldiğinde bu işlemleri yapabiliyoruz
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                //success
                val intent = Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(intent)
                finish() //bir daha kullanıcının buraya geri dönmesine gerek kalmayacağı için finish diyoruz

            }.addOnFailureListener {
                //firebase hata verirse bunu kullanıyoruz
                //localized message kullanıcının anlayabildiği dilden hata mesajını yazdır demek
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
            //firebase sunucularına yollanacak ve bir cevap döndürecek bu normal yazdığımız kodlar gibi hızlı olmayacak
            //bunu arka planda asenkron yapmamız lazım böylece kullanıcı arayüzü bloklanmıyor
            //cevap dönünce intent yapmak istediğimiz için buraya direkt intent yazamıyoruz
        }


    }
}