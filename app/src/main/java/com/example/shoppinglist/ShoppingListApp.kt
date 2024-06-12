package com.example.shoppinglist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.ui.theme.ShoppingListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingList() {
    ShoppingListTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
            var showDialog by remember { mutableStateOf(false) }
            var itemId by remember { mutableStateOf(0) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        sItems = sItems + ShoppingItem(
                            id = itemId++,
                            name = "Item $itemId",
                            quantity = 1
                        )
                        showDialog = true
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Add an Item")
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(sItems) { item ->
                        ShoppingItemRow(item = item)
                    }
                }
                if (showDialog) {
                    AlertDialog(onDismissRequest = { showDialog = false },
                        confirmButton = { Button(onClick = {showDialog = false}) {
                            Text("OK")
                        }
                    },
                        text = { Text("I am a Dialog text") }
                        )
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