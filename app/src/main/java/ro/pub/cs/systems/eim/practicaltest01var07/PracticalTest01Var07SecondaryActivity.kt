package ro.pub.cs.systems.eim.practicaltest01var07

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var07SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var07_secondary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val input1 = intent.getIntExtra(Constants.INPUT1, 0)
        val input2 = intent.getIntExtra(Constants.INPUT2, 0)
        val input3 = intent.getIntExtra(Constants.INPUT3, 0)
        val input4 = intent.getIntExtra(Constants.INPUT4, 0)

        val text1 = findViewById<TextView>(R.id.textView1)
        val text2 = findViewById<TextView>(R.id.textView2)
        val text3 = findViewById<TextView>(R.id.textView3)
        val text4 = findViewById<TextView>(R.id.textView4)

        text1.text = input1.toString()
        text2.text = input2.toString()
        text3.text = input3.toString()
        text4.text = input4.toString()

        val sumButton = findViewById<Button>(R.id.buttonSum)
        val productButton = findViewById<Button>(R.id.buttonProduct)


        sumButton.setOnClickListener {
            val sum = input1 + input2 + input3 + input4
            Toast.makeText(this, "Sum is $sum", Toast.LENGTH_LONG).show()
        }

        productButton.setOnClickListener {
            val product = input1 * input2 * input3 * input4
            Toast.makeText(this, "Product is $product", Toast.LENGTH_LONG).show()
        }
    }
}