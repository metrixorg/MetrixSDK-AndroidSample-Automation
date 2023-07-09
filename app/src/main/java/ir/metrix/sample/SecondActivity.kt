package ir.metrix.sample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class SecondActivity : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var signOutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        name = findViewById<TextView>(R.id.name)
        email = findViewById<TextView>(R.id.email)
        signOutBtn = findViewById<Button>(R.id.signout)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            name.text = personName
            email.text = personEmail
        }
        signOutBtn.setOnClickListener{ signOut() }
    }

    private fun signOut() {
        gsc.signOut().addOnCompleteListener {
            finish()
            startActivity(Intent(this@SecondActivity, MainActivity::class.java))
        }
    }
}