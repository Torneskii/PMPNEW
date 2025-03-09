package com.kidschool.animalsforkids

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kidschool.animalsforkids.ui.theme.AnimalsForKidsTheme
import com.kidschool.animalsforkids.ui.theme.LanguageStateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

@Composable
fun AnimalScreen(navController: NavController) {
    AnimalsForKidsTheme {
        Scaffold(
            topBar = { AnimalTopBar() },
            floatingActionButton = { AddAnimalButton(navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                SearchBar()
                AnimalList(LocalContext.current)
            }

        }
    }
}

fun readFileFromStorage(context: Context, fileName: String): List<Animal> {
    val file = File(context.filesDir, fileName)
    return parseAnimalData(file.readText())
}

fun parseAnimalData(fileContent: String): List<Animal> {
    val lines = fileContent.lines().filter { it.isNotBlank() }
    val animals = mutableListOf<Animal>()

    for (i in lines.indices step 2) {
        if (i + 1 < lines.size) {
            val englishPart = lines[i].split(";").map { it.trim() }
            val macedonianPart = lines[i + 1].split(";").map { it.trim() }

            animals.add(Animal(
                name = Pair(englishPart[0], macedonianPart[0]),
                firstFact = Pair(englishPart[1], macedonianPart[1]),
                secondFact = Pair(englishPart[2], macedonianPart[2]),
                description = Pair(englishPart[3], macedonianPart[3]),
                continent = Pair(englishPart[4], macedonianPart[4]),
            ))
        }
    }
    return animals
}

fun writeToFile(context: Context, fileName: String, animal:Animal) {
    val file = File(context.filesDir, fileName)

    val newEntry = """
         ${animal.name.first} ; ${animal.firstFact.first} ; ${animal.secondFact.first} ; ${animal.description.first} ; ${animal.continent.first} ;
         ${animal.name.second} ; ${animal.firstFact.second} ; ${animal.secondFact.second} ; ${animal.description.second} ; ${animal.continent.second} ; 
       """.trimIndent()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            file.appendText("\n$newEntry")
            Log.d("FileWrite", "File path: ${file.absolutePath}")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalTopBar(viewModel: LanguageStateViewModel = viewModel()) {
    val icon: Painter = painterResource(id = R.drawable.global)
    TopAppBar(
        title = { Text(text = if(viewModel.globalLanguageVariable.value) "Animals" else "Животни") },
        navigationIcon ={
            IconButton( onClick = {}) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {
                viewModel.toggleGlobalLanguageVariable()
            }) {
                Image(
                    painter = icon, contentDescription = "Language"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    val viewModel: LanguageStateViewModel = viewModel()
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(text = if(viewModel.globalLanguageVariable.value) "Search" else "Пребарај") },
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }
    )
}

@Composable
fun AddAnimalButton(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate(Screen.AddAdnimalScreen.route)},
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary,
        shape = CircleShape
    ) {
        Icon(Icons.Filled.Add, "Add animal")
    }
}

@Composable
fun AnimalList(context: Context) {

    val viewModel: LanguageStateViewModel = viewModel()

    val animals = remember { readFileFromStorage(context, "animals.txt") }
    val defaultImage = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.placeholder)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
    ) {
        items(animals) { animal ->
            val imageFilePath = "/data/user/0/com.kidschool.animalsforkids/files/${animal.name.first.lowercase()}.png"
            val imageFile = File(imageFilePath)
            val imageBitmap = if (imageFile.exists()) {
                BitmapFactory.decodeFile(imageFilePath)
            } else {
                defaultImage
            }
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxWidth()
                    .clickable {  }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    bitmap = imageBitmap.asImageBitmap(),
                    contentDescription = animal.name.first,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(80.dp).clip(CircleShape)
                )
                Column (modifier = Modifier.weight(1f)){
                    Text(
                        text = if(viewModel.globalLanguageVariable.value) animal.name.first else animal.name.second,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = if(viewModel.globalLanguageVariable.value) animal.description.first else animal.description.second,
                        fontSize = 15.sp,
                        maxLines = 3,
                        fontStyle = FontStyle.Italic,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Row (
                    modifier = Modifier.padding(5.dp)
                ){

                    val showDialog = remember { mutableStateOf(false) }
                    SmallFloatingActionButton(
                        onClick = {
                            showDialog.value = true
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                        shape = CircleShape
                    ) {
                        Icon(Icons.Filled.AccountCircle, "See more")
                    }
                    if (showDialog.value) {
                        SimpleDialog(
                            onDismissRequest = { showDialog.value = false},
                            animal,
                            imageBitmap
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SimpleDialog(onDismissRequest: () -> Unit, animal: Animal, imageBitmap: Bitmap) {

    val viewModel: LanguageStateViewModel = viewModel()

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp).height(600.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    bitmap = imageBitmap.asImageBitmap(),
                    contentDescription = animal.name.first
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(top = 6.dp, start = 12.dp)
                ) {
                    Text(
                        text = if (viewModel.globalLanguageVariable.value) animal.name.first else animal.name.second,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(top = 1.dp, start = 12.dp, bottom = 6.dp)
                ) {
                    Text(
                        text = if(viewModel.globalLanguageVariable.value) animal.name.second else animal.name.first,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star, contentDescription = "Fact"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (viewModel.globalLanguageVariable.value) animal.firstFact.first else animal.firstFact.second,
                            maxLines = 3,
                            fontSize = 16.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(modifier = Modifier.weight(1f).padding(start = 16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star, contentDescription = "Fact"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (viewModel.globalLanguageVariable.value) animal.secondFact.first else animal.secondFact.second,
                            maxLines = 3,
                            lineHeight = 18.sp,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 1.dp, start = 12.dp, bottom = 6.dp)
                ) {
                    Text(
                        text = if (viewModel.globalLanguageVariable.value) animal.description.first else animal.description.second,
                        fontSize = 16.sp,
                        lineHeight =16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Justify
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, bottom = 6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn, contentDescription = "Location Icon",
                        modifier = Modifier.size(32.dp).padding(end = 10.dp),
                    )
                    Text(
                        text = if (viewModel.globalLanguageVariable.value) animal.continent.first else animal.continent.second,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }

            }
        }
    }
}


