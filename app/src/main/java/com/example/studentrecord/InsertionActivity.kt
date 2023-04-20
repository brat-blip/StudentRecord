package com.example.studentrecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var studentName: EditText
    private lateinit var studentclass: EditText
    private lateinit var studentrollno: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        studentName = findViewById(R.id.Nameview)
        studentclass = findViewById(R.id.Classview)
        studentrollno = findViewById(R.id.secview)
        btnSaveData = findViewById(R.id.button)

        dbRef = FirebaseDatabase.getInstance().getReference("Students")

        btnSaveData.setOnClickListener {
            saveStudentData()
        }

    }

   private  fun saveStudentData(){
       //getting value
       val name = studentName.text.toString()
       val classroom = studentclass.text.toString()
       val rollno = studentrollno.text.toString()

       if(name.isEmpty()){
           studentName.error = "please enter name"
       }
       if(classroom.isEmpty()){
           studentclass.error = "please enter class"
       }
       if(rollno.isEmpty()){
           studentrollno.error = "please enter rollno"
       }
       val studentId = dbRef.push().key!!

       val student = Student(studentId, name, classroom, rollno)
       dbRef.child(studentId).setValue(student)
           .addOnCompleteListener {
               Toast.makeText(this,"data inserted successfully",Toast.LENGTH_SHORT).show()
               studentName.text.clear()
               studentclass.text.clear()
               studentrollno.text.clear()

           }.addOnFailureListener {err->
               Toast.makeText(this,"error: ${err.message}",Toast.LENGTH_SHORT).show()

           }

   }
}