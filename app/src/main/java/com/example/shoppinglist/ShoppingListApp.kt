package com.example.shoppinglist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme


@Composable
fun ShoppingList() {
    ShoppingListTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
            var showDialog by remember { mutableStateOf(false) }
            var itemId by remember { mutableStateOf(0) }
            var itemName by remember { mutableStateOf("") }
            var itemQuantity by remember { mutableStateOf("") }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Start a List")
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    items(sItems) { item ->
                        ShoppingItemRow(item = item)
                    }
                }
                if (showDialog) {
                    AlertDialog(onDismissRequest = { showDialog = false },
                        confirmButton =
                        { Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(onClick = {
                                if (itemName.isNotBlank()) {
                                    val newItem = ShoppingItem(
                                        id = sItems.size+1,
                                        name = itemName,
                                        quantity = itemQuantity.toInt()
                                    )
                                    sItems = sItems + newItem
                                    showDialog = false
                                    itemName = ""
                                }
                            }) {
                                Text("Add")}
                            Button(onClick = {showDialog = false}) {
                                Text("Cancel")
                            }
                        }

                    },
                        title = { Text("Add an Item") },
                        text = {
                            Column {
                                OutlinedTextField(value = itemName,
                                    onValueChange = {itemName = it},
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                                OutlinedTextField(value = itemQuantity,
                                    onValueChange = {itemQuantity = it},
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                            }
                        })
                    }
                }
            }
        }
    }

@Composable
fun ShoppingItemRow(item: ShoppingItem) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Quantity: ${item.quantity}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    var editing: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun Preview() {
    ShoppingList()
}