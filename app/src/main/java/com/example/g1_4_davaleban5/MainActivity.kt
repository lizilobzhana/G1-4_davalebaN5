package com.example.g1_4_davaleban5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPassword2: EditText
    private lateinit var buttonRegistration: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextPassword2 = findViewById(R.id.editTextPassword2)
        buttonRegistration = findViewById(R.id.buttonRegistration)

        buttonRegistration.setOnClickListener{
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val password2 = editTextPassword2.text.toString().trim()

            if (email.isEmpty()){
                editTextEmail.error = "Enter the email"
                return@setOnClickListener
            }else if (!(email.contains("@"))){
                editTextEmail.error = "Enter the correct email"
                return@setOnClickListener
            }else if (password.isEmpty()){
                editTextPassword.error = "Enter the password"
                return@setOnClickListener
            }else if (password.length < 9){
                editTextPassword.error = "The password must contain at least 9 characters"
                return@setOnClickListener
            }else if (!(password.matches("(.*[A-Z].*)".toRegex()))||
                !(password.matches("(.*[0-9].*)".toRegex())) ||
                !(password.matches("^(?=.*[_.()$&@]).*$".toRegex()))){
                editTextPassword.error = "Password is too weak"
                return@setOnClickListener
            }else if (password2 != password){
                editTextPassword2.error = "Those passwords did not match"
                return@setOnClickListener
            }else
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "წარმატებით გაიარეთ რეგისტრაცია", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "ვერ დარეგისტრირდით", Toast.LENGTH_SHORT).show()
                        }
                    }

        }
    }
}