package dev.grupo.pc02_20200057_19200146

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class LOGIN : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //credenciales usuario = usuariopc2@gmail.com
        // contraaseña = 123456
        val etemail: EditText = findViewById(R.id.etusuario)
        val etpassword: EditText = findViewById(R.id.etcontraseña)
        val btnlogin: Button = findViewById(R.id.btnlogin)
        val auth = FirebaseAuth.getInstance()
//
        btnlogin.setOnClickListener {
            val email = etemail.text.toString()
            val password = etpassword.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    task ->
                    if(task.isSuccessful){
                        Snackbar.make(findViewById(android.R.id.content), "Inicio de sesión exitoso", Snackbar.LENGTH_SHORT).show()
                    //startActivity(Intent(this, MainActivity::class.java))
                    }else{
                        Snackbar.make(findViewById(android.R.id.content), "Inicio de sesión fallido", Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }
}