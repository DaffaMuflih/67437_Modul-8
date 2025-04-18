package com.example.week08.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StudentRegistrationScreen(viewModel: StudentViewModel = viewModel()) {
    var studentId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }
    var currentPhone by remember { mutableStateOf("") }
    var phoneList by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        TextField(value = studentId, onValueChange = { studentId = it }, label = { Text("Student ID") })
        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        TextField(value = program, onValueChange = { program = it }, label = { Text("Program") })

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = currentPhone,
                onValueChange = { currentPhone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if (currentPhone.isNotBlank()) {
                    phoneList = phoneList + currentPhone
                    currentPhone = ""
                }
            }, modifier = Modifier.padding(start = 8.dp)) {
                Text("Add")
            }
        }

        if (phoneList.isNotEmpty()) {
            Text("Phone Numbers:", style = MaterialTheme.typography.labelLarge)
            phoneList.forEach {
                Text("- $it")
            }
        }

        Button(onClick = {
            viewModel.addStudent(Student(studentId, name, program), phoneList)
            studentId = ""
            name = ""
            program = ""
            phoneList = listOf()
        }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Submit")
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Text("Student List", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(viewModel.students) { student ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("ID: ${student.id}")
                    Text("Name: ${student.name}")
                    Text("Program: ${student.program}")
                    Divider()
                }
            }
        }
    }
}
