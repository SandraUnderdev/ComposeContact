package com.example.contacts

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.Contact
import com.example.myproject.ContactAdapter
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var fab: FloatingActionButton

    private lateinit var nameEdt: EditText
    private lateinit var phoneEdt: EditText
    private lateinit var previewImage: ImageView
    private lateinit var btnChooseImage: Button
    private lateinit var btnAddContact: Button
    private lateinit var dialog: Dialog

    private lateinit var contactAdapter: ContactAdapter
    // val listOfUsers = mutableListOf<DataEntryContact>()

    private lateinit var repo: Repo
    private lateinit var viewModelFactory: ContactViewModelFactory
    private lateinit var viewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            contactUi()
        }
        setContentView(R.layout.activity_main)

        repo = Repo()
        viewModelFactory = ContactViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ContactViewModel::class.java)

        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        //  contactAdapter = DataEntryAdapter(listOfUsers)
        // rv.adapter = contactAdapter
        fab = findViewById(R.id.fab)

        viewModel.listOfContactLiveData.observe(this) {
            contactAdapter = ContactAdapter(it)
            rv.adapter = contactAdapter
        }

        fab.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.data_entry_form)

        nameEdt = dialog.findViewById(R.id.fullName)
        phoneEdt = dialog.findViewById(R.id.number)
        previewImage = dialog.findViewById(R.id.img_preview)
        btnChooseImage = dialog.findViewById(R.id.choose_image)
        btnAddContact = dialog.findViewById(R.id.add_contact)

        btnChooseImage.setOnClickListener {
            //   Toast.makeText(this, "should open gallery", Toast.LENGTH_SHORT).show()
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 101)
        }

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == AppCompatActivity.RESULT_OK) {
            previewImage.visibility = View.VISIBLE
            previewImage.setImageURI(data?.data)

            btnAddContact.setOnClickListener {
                val name = nameEdt.text.toString()
                val phoneNumber = phoneEdt.text.toString()
                val image = data?.data

                val contact = Contact(
                    textName = name,
                    textPhoneNumber = phoneNumber,
                    profileImage = image,
                )
                //       listOfUsers.add(contact)
                //  contactAdapter.notifyDataSetChanged()
                viewModel.addContact(contact)
                dialog.dismiss()
            }
            //
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    fun contactUi() {
        1
        //  var medicationName by rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(0.1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Blue),
                    verticalArrangement = Arrangement.SpaceBetween,

                    ) {
                }
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")
                Text(text = "Hey")

            }
            Column(
                modifier = Modifier
                    .background(color = Color.Green),

                ) {
                val showAlertDialog = remember { mutableStateOf(false) }
                var name by rememberSaveable { mutableStateOf("") }
                var number by rememberSaveable { mutableStateOf("") }
                Button(onClick = {
                    showAlertDialog.value = true

                }) {

                }

                if (showAlertDialog.value) {

                    AlertDialog(
                        onDismissRequest = {
                            showAlertDialog.value = false
                        },
                        text = {
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                TextField(
                                    value = name,
                                    onValueChange = {name = it },
                                    placeholder = { Text(text = "Enter Name") },
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                        .background(Color.Transparent)
                                )
                                TextField(
                                    value = number,
                                    onValueChange = {number = it },
                                    placeholder = { Text(text = "Enter Number") },
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                        .background(Color.White)
                                )

                                Image(painter = painterResource(id = R.drawable.circle),
                                    contentDescription = "btn_add",
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(200.dp)
                                    )
                            }

                        },

                        confirmButton = {

                            Button(
                                onClick = {
                                    showAlertDialog.value = false
                                }) {
                                Text("Choose Image")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    showAlertDialog.value = false
                                }) {
                                Text("Add Contact")
                            }
                        }
                    )
                }
            }

        }
//
//           Image(
//               painter = painterResource(id = R.drawable.circle) ,
//               contentDescription = "btn_add",
//               modifier = Modifier
//                   .width(80.dp)
//                   .height(50.dp)
//                   .clickable {
//
//                   }

    }

}


