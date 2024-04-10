package ro.pub.cs.systems.eim.practicaltest01var07

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ro.pub.cs.systems.eim.practicaltest01var07.Constants.Companion.INPUT1
import ro.pub.cs.systems.eim.practicaltest01var07.Constants.Companion.INPUT2
import ro.pub.cs.systems.eim.practicaltest01var07.Constants.Companion.INPUT3
import ro.pub.cs.systems.eim.practicaltest01var07.Constants.Companion.INPUT4
import ro.pub.cs.systems.eim.practicaltest01var07.Constants.Companion.RANDOM_INPUT1
import ro.pub.cs.systems.eim.practicaltest01var07.Constants.Companion.RANDOM_INPUT2
import ro.pub.cs.systems.eim.practicaltest01var07.Constants.Companion.RANDOM_INPUT3
import ro.pub.cs.systems.eim.practicaltest01var07.Constants.Companion.RANDOM_INPUT4
import kotlin.random.Random

class PracticalTest01Var07MainActivity : AppCompatActivity() {

    private lateinit var inputText1: EditText
    private lateinit var inputText2: EditText
    private lateinit var inputText3: EditText
    private lateinit var inputText4: EditText

    private val intentFilter = IntentFilter()


    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                Log.d(Constants.BROADCAST_RECEIVER_TAG, it.action.toString())
                Log.d(Constants.BROADCAST_RECEIVER_TAG, it.getStringExtra("message").toString())

                Log.d("rippp", "")
                val regex = "\\d+".toRegex()
                val numbers = regex.findAll(it.getStringExtra("message").toString()).map { it.value.toInt() }.toList()

                Log.d("Receiver", "onReceive: ${numbers[0]} ${numbers[1]} ${numbers[2]} ${numbers[3]}")

                inputText1.setText(numbers[0].toString())
                inputText2.setText(numbers[1].toString())
                inputText3.setText(numbers[2].toString())
                inputText4.setText(numbers[3].toString())
                Toast.makeText(PracticalTest01Var07MainActivity(), "The service has stopped with the following message: ${numbers.joinToString()}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var07_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startService()

        val buttonSet = findViewById<Button>(R.id.setButton)
         inputText1 = findViewById(R.id.editText1)
         inputText2 = findViewById(R.id.editText2)
         inputText3 = findViewById(R.id.editText3)
         inputText4 = findViewById(R.id.editText4)

        buttonSet.setOnClickListener {
            if (inputText1.text.isNotEmpty() && inputText2.text.isNotEmpty() && inputText3.text.isNotEmpty() && inputText4.text.isNotEmpty()) {
                if (inputText1.text.toString().isDigitsOnly() && inputText3.text.toString().isDigitsOnly() && inputText4.text.toString().isDigitsOnly()
                         && inputText2.text.toString().isDigitsOnly()) {
                    val intent = Intent(this, PracticalTest01Var07SecondaryActivity::class.java)
                    intent.putExtra(Constants.INPUT1, inputText1.text.toString().toInt())
                    intent.putExtra(Constants.INPUT2, inputText2.text.toString().toInt())
                    intent.putExtra(Constants.INPUT3, inputText3.text.toString().toInt())
                    intent.putExtra(Constants.INPUT4, inputText4.text.toString().toInt())
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "All fields must be numbers", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter)
        }
    }

    override fun onPause() {
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }

    override fun onDestroy() {
        val intent = Intent(applicationContext, PracticalTest01Var07Service::class.java)
        applicationContext.stopService(intent)
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startService() {
            Log.d("rippp", "startService: ")
            val intent = Intent(applicationContext, PracticalTest01Var07Service::class.java)
            intent.putExtra(RANDOM_INPUT1, Random.nextInt(0, 100))
            intent.putExtra(RANDOM_INPUT2, Random.nextInt(0, 100))
            intent.putExtra(RANDOM_INPUT3, Random.nextInt(0, 100))
            intent.putExtra(RANDOM_INPUT4, Random.nextInt(0, 100))
            applicationContext.startForegroundService(intent)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INPUT1, inputText1.text.toString())
        outState.putString(INPUT2, inputText2.text.toString())
        outState.putString(INPUT3, inputText3.text.toString())
        outState.putString(INPUT4, inputText4.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey(INPUT1) && savedInstanceState.containsKey(INPUT2)
            && savedInstanceState.containsKey(INPUT3) && savedInstanceState.containsKey(INPUT4) ) {
            inputText1.setText(savedInstanceState.getString(INPUT1))
            inputText2.setText(savedInstanceState.getString(INPUT2))
            inputText3.setText(savedInstanceState.getString(INPUT3))
            inputText4.setText(savedInstanceState.getString(INPUT4))
        }
    }


}