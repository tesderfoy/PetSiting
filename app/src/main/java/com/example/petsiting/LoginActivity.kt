package com.example.petsiting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val ownerLogin: EditText = findViewById(R.id.user_login)
        val ownerPassword: EditText = findViewById(R.id.user_password)
        val autBtn = findViewById<Button>(R.id.button_Aut)
        val goRegister = findViewById<TextView>(R.id.goReg)
        goRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java);
            startActivity(intent);
        }


        autBtn.setOnClickListener(){
            val login= ownerLogin.text.toString().trim()
            val password = ownerPassword.text.toString().trim()

            firebaseAuth.signInWithEmailAndPassword(login, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "Успешная авторизация", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RegisterActivity::class.java);
                    startActivity(intent);
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

}


