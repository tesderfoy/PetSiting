package com.example.petsiting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petsiting.DB.Owner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val ownerLogin: EditText = findViewById(R.id.user_login)
        val ownerPassword: EditText = findViewById(R.id.user_password)
        val ownerName:EditText = findViewById(R.id.user_name)
        val ownerSurname: EditText = findViewById(R.id.user_surname)
        val userPhone: EditText = findViewById(R.id.user_phone)
        val ownerAddress: EditText = findViewById(R.id.user_address)

        val buttonReg: Button = findViewById(R.id.button_Reg)

        val backAut = findViewById<ImageButton>(R.id.backAut)

        backAut.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
        }

        buttonReg.setOnClickListener{
            val login = ownerLogin.text.toString().trim()
            val password = ownerPassword.text.toString().trim()
            val name = ownerName.text.toString().trim()
            val surname = ownerSurname.text.toString().trim()
            val phone = userPhone.text.toString().trim()
            val address = ownerAddress.text.toString().trim();
            if(login==""|| password==""|| name==""|| surname=="" || phone==""||address==""){
                Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show()

            }else {


                firebaseAuth.createUserWithEmailAndPassword(login, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = firebaseAuth.currentUser?.uid
                            val owner =
                                Owner(userId, name, surname, login, password, phone, address)

                            usersRef.child(userId!!).setValue(owner)
                                .addOnCompleteListener {
                                    finish()
                                }
                        } else {
                            Log.e("RegisterActivity", "Ошибка", task.exception)

                        }
                    }
            }
        }
    }
}







