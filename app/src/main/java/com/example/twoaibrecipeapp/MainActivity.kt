package com.example.twoaibrecipeapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val Title = findViewById<View>(R.id.edt1) as EditText
        val Author = findViewById<View>(R.id.edt2) as EditText
        val Ingredents = findViewById<View>(R.id.edt3) as EditText
        val Instruction = findViewById<View>(R.id.edt4) as EditText
        val savebtn = findViewById<View>(R.id.btn1) as Button

        savebtn.setOnClickListener {
            var f = RecipeDetails.Datum(
                Title.text.toString(),
                Author.text.toString(),
                Ingredents.text.toString(),
                Instruction.text.toString())

            addReceipe(f, onResult = {
                Title.setText("")
                Author.setText("")
                Ingredents.setText("")
                Instruction.setText("")
                Toast.makeText(applicationContext, "Recipe Save Success", Toast.LENGTH_SHORT).show();
            })
        }
    }

    fun addReceipe(userData: RecipeDetails.Datum, onResult: (RecipeDetails?) -> Unit)
    {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null)
        {
            apiInterface.addRecipie(userData).enqueue(object : Callback<RecipeDetails>
            {
                override fun onResponse(call: Call<RecipeDetails>, response: Response<RecipeDetails>)
                {
                    onResult(response.body())

                }

                override fun onFailure(call: Call<RecipeDetails>, t: Throwable)
                { onResult(null)
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();


                }
            })
        }
    }

    fun viewreceipe(view: android.view.View) {
        intent = Intent(applicationContext, ViewRecipes::class.java)
        startActivity(intent)
    }
}