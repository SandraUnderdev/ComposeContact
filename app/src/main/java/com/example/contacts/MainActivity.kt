package com.example.contacts

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.net.URI

class MainActivity : AppCompatActivity() {

   // this private lateinit var rv: RecyclerView
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                 //   .verticalScroll(rememberScrollState())
            ) {
              //  ContactDialog()
                AddContactDialog()
            }
        }
     //   setContentView(R.layout.activity_main)

        repo = Repo()
        viewModelFactory = ContactViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ContactViewModel::class.java)

      // this rv = findViewById(R.id.rv)
     // this   rv.layoutManager = LinearLayoutManager(this)
        //  contactAdapter = DataEntryAdapter(listOfUsers)
        // rv.adapter = contactAdapter
            // this  fab = findViewById(R.id.fab)

        viewModel.listOfContactLiveData.observe(this) {
            contactAdapter = ContactAdapter(it)
      // this      rv.adapter = contactAdapter
        }

//   this     fab.setOnClickListener {
//            showDialog()
//        }
//

// this   private fun showDialog() {
//        dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.data_entry_form)
//
//        nameEdt = dialog.findViewById(R.id.fullName)
//        phoneEdt = dialog.findViewById(R.id.number)
//        previewImage = dialog.findViewById(R.id.img_preview)
//        btnChooseImage = dialog.findViewById(R.id.choose_image)
//        btnAddContact = dialog.findViewById(R.id.add_contact)
//
//        btnChooseImage.setOnClickListener {
//            //   Toast.makeText(this, "should open gallery", Toast.LENGTH_SHORT).show()
//            val galleryIntent =
//                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(galleryIntent, 101)
//        }
//
//        dialog.show()
    }

//  this  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 101 && resultCode == AppCompatActivity.RESULT_OK) {
//            previewImage.visibility = View.VISIBLE
//            previewImage.setImageURI(data?.data)
//
//            btnAddContact.setOnClickListener {
//                val name = nameEdt.text.toString()
//                val phoneNumber = phoneEdt.text.toString()
//                val image = data?.data
//
//                val contact = Contact(
//                    textName = name,
//                    textPhoneNumber = phoneNumber,
//                    profileImage = image,
//                )
//                //       listOfUsers.add(contact)
//                //  contactAdapter.notifyDataSetChanged()
//                viewModel.addContact(contact)
//                dialog.dismiss()
//            }
//            //
//        }
//    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    fun AddContactDialog() {
       val myList by viewModel.listOfContactLiveData.observeAsState()

//        var nameInput by remember { mutableStateOf("") }
//        var numberInput by remember { mutableStateOf("") }
        val txtFieldError = remember { mutableStateOf("") }

       // val listOfContact = mutableListOf<>()
    //    viewModel.addContact(contact = )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .verticalScroll(rememberScrollState())
                    .weight(0.1f)
            ) {
//                Column( modifier = Modifier.fillMaxSize()
//                        .background(color = Color.Blue),
//                    verticalArrangement = Arrangement.SpaceBetween, ) {
//                }

                LazyRow (
                    modifier = Modifier.padding(top = 4.dp)
                )
                {

                    //  val myList = viewModel.listOfContactLiveData.observe(this, Observer {
                //    val list  = viewModel.listOfContactLiveData.observeAsState().value
                  //  itemsIndexed(viewModel.listOfContactLiveData){ index, item ->
                    itemsIndexed(myList.orEmpty()){ index, item ->
                        ContactUiModel(textName = item.textName, textPhoneNumber = item.textPhoneNumber
                         //   ,profileImage = item.profileImage
                    )
                    }
                }

            }

            Column(
                modifier = Modifier
                    .background(color = Color.Green),

                ) {
                val showAlertDialog = remember { mutableStateOf(false) }
                var name by rememberSaveable { mutableStateOf("") }
                var number by rememberSaveable { mutableStateOf("") }
                var image by rememberSaveable { mutableStateOf<Int>(0) }
                val mContent = LocalContext.current
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

                                Column( modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally)
                                {
                                    Image(painter = painterResource(id = R.drawable.circle),
                                 //   Image(painter = painterResource(id = image),
                                        contentDescription = "image_added",
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(100.dp)
                                          .alpha(0f) //invisible
                                    )
                                }
                            }

                        },
                        confirmButton = {
                        Row(
                            modifier = Modifier.padding(top = 4.dp, start = 20.dp, end = 20.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    showAlertDialog.value = false
                                }) {
                                Text("Choose Image")
                            }
                        }

                        },
                        dismissButton = {
                            Row( modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.Center)
                            {
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                     //   showAlertDialog.value = false
                                        if (name.isEmpty() && number.isEmpty()) {
                                            txtFieldError.value = "Field can not be empty"
                                            return@Button
                                        }
                                        Toast.makeText(mContent, name, Toast.LENGTH_LONG).show()
                                       name =  name
                                        number = number
                                        showAlertDialog.value = false
                                      //  setShowDialog(false)
                                        val contact = Contact(
                                            textName = name,
                                            textPhoneNumber = number
                                       //     ,profileImage = image,
                                        )
                                        viewModel.addContact(contact)
                                    }) {
                                    Text("Add Contact")
                                }
                            }
                        }
                    )
                }
            }

        }

    }

    @Composable
  //  @Preview
    fun ContactUiModel(
           // profileImage: Int,
            textName: String,
            textPhoneNumber: String

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        ){
            Image(
                painter = painterResource(id = R.drawable.mov4),
                contentDescription = "movie_display",
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .padding(top = 4.dp, bottom = 4.dp)
            )
            Column(
                modifier = Modifier.padding(top = 10.dp)

            ) {
                Text(text = textName, color = Color.Black, fontSize = 14.sp)
                Text(text = textPhoneNumber, color = Color.Black,  fontSize = 12.sp)
            }
        }
    }
}


