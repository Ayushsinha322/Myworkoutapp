package com.dangerx.myworkoutapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUsername: EditText = findViewById(R.id.editTextUsername)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val loginButton: Button = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            // Always return true for any credentials
            if (isValidCredentials(username, password)) {
                // Successful login, navigate to the next screen
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                // Create an Intent to start the next activity (e.g., MainActivity)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // Add code to navigate to the next activity here (e.g., startActivity)
            } else {
                // Invalid credentials, show an error message
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        // Replace this with your actual authentication logic
        return true
    }
}
