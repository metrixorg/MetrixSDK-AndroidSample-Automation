package ir.metrix.sample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import ir.metrix.analytics.MetrixAnalytics
import java.lang.Exception


class SecondActivity : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    private lateinit var name: TextView
    private lateinit var signOutBtn: Button

    private lateinit var strValue: EditText
    private lateinit var intValue: EditText
    private lateinit var sendEvent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        name = findViewById(R.id.name)
        signOutBtn = findViewById(R.id.sign_out)

        strValue = findViewById(R.id.str)
        intValue = findViewById(R.id.number)
        sendEvent = findViewById(R.id.send_event)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            name.text = acct.displayName
        }

        signOutBtn.setOnClickListener{ signOut() }
        sendEvent.setOnClickListener{
            sendTestEvent(strValue.text.toString(), intValue.text.toString())
        }
    }

    private fun signOut() {
        gsc.signOut().addOnCompleteListener {
            MetrixAnalytics.User.deleteUserCustomId()
            finish()
            startActivity(Intent(this@SecondActivity, MainActivity::class.java))
        }
    }

    private fun sendTestEvent(strValue: String, intValue: String) {
        try {
            MetrixAnalytics.newEvent("TEST_EVENT_SLUG",
                mapOf(
                    "str-test" to strValue,
                    "int-value" to intValue
                )
            )
            Toast.makeText(this, "Sent successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}