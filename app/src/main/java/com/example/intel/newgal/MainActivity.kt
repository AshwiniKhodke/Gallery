package com.example.intel.newgal

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    var dbSq: DatabaseHelper=DatabaseHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var dbSq: DatabaseHelper=DatabaseHelper(this)


            /*et_title = findViewById(R.id.et_title)
            et_content = findViewById(R.id.et_content)*/


            val imgVw=findViewById(R.id.imgVw) as ImageView
            /* imgVw.setOnClickListener{
                 startActivity(Intent(this@NoteActivity,Gallery::class.java))
             }*/


            imgVw.setOnClickListener(object : View.OnClickListener {

                override fun onClick(v: View) {

                    selectImage()

                }

            })

        }




        private fun selectImage() {


            val options=arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")


            val builder=AlertDialog.Builder(this@MainActivity)

            builder.setTitle("Add Photo!")

            builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                if (options[item] == "Take Photo") {
                    val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, 101)
                }

                /* val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                  val f=File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg")

                 intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f))

                 startActivityForResult(intent, 1)*/
                else if (options[item] == "Choose from Gallery") {

                    val intent=Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                    startActivityForResult(intent, 2)


                } else if (options[item] == "Cancel") {

                    dialog.dismiss()

                }
            }).show()


        }

        public override fun onActivityResult(requestcode: Int, resultcode: Int, intent: Intent) {
            super.onActivityResult(requestcode, resultcode, intent)
            if (resultcode == Activity.RESULT_OK) {
                if (requestcode == 101) {


                    val photo=intent.extras!!.get("data") as Bitmap
                    val stream=ByteArrayOutputStream()
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val byteArray=stream.toByteArray()
                    Log.d("check", dbSq.insertData(byteArray).toString().plus(" "))
                }
            }
        }
    }



/* val cameraIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
 startActivityForResult(cameraIntent, 101)


}

public override fun onActivityResult(requestcode: Int, resultcode: Int, intent: Intent) {
 super.onActivityResult(requestcode, resultcode, intent)
 if (resultcode == Activity.RESULT_OK) {
     if (requestcode == 101) {


         val photo=intent.extras!!.get("data") as Bitmap
         val stream=ByteArrayOutputStream()
         photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
         val byteArray=stream.toByteArray()
         Log.d("check", dbSq.insertData(byteArray).toString().plus(" "))


     }
 }
}
}*/
