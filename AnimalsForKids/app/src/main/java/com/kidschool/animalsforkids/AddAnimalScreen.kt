package com.kidschool.animalsforkids

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kidschool.animalsforkids.ui.theme.AnimalsForKidsTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun AddAnimalScreen(navController: NavController) {
    AnimalsForKidsTheme {
        Scaffold(
            topBar = { AddAnimalTopBar(navController) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AddAnimalForm(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnimalTopBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Add Animals" ) },
        navigationIcon ={
            IconButton( onClick = { navController.navigate(Screen.AnimalScreen.route) }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameRow(nameEnglish: String, onChangeNameEnglish: (String) -> Unit,
            nameMacedonian: String, onChangeNameMacedonian: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = nameMacedonian,
            onValueChange =  onChangeNameMacedonian ,
            placeholder = { Text("Име") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp),
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
        TextField(
            value = nameEnglish,
            onValueChange = onChangeNameEnglish,
            placeholder = { Text("Name") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp),
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstFactRow(factEnglish: String, onChangeFactEnglish: (String) -> Unit,
            factMacedonian: String, onChangeFactMacedonian: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = factMacedonian,
            onValueChange = onChangeFactMacedonian,
            placeholder = { Text("Факт") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp),
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
        TextField(
            value = factEnglish,
            onValueChange = onChangeFactEnglish,
            placeholder = { Text("Fact") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp),
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondFactRow(factEnglish: String, onChangeFactEnglish: (String) -> Unit,
                  factMacedonian: String, onChangeFactMacedonian: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = factMacedonian,
            onValueChange = onChangeFactMacedonian,
            placeholder = { Text("Факт") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp),
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
        TextField(
            value = factEnglish,
            onValueChange = onChangeFactEnglish,
            placeholder = { Text("Fact") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp),
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionRow(descriptionEnglish: String, onChangedescriptionEnglish: (String) -> Unit,
                   descriptionMacedonian: String, onChangedescriptionMacedonian: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = descriptionMacedonian,
            onValueChange = onChangedescriptionMacedonian,
            placeholder = { Text("Опис") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp)
                .height(200.dp),
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
        TextField(
            value = descriptionEnglish,
            onValueChange = onChangedescriptionEnglish,
            placeholder = { Text("Description") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp)
                .height(200.dp),
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}

class ContinentViewModel : ViewModel() {

    private val _selectedMacedonianContinents = MutableStateFlow<List<String>>(emptyList())
    val selectedMacedonianContinents: StateFlow<List<String>> = _selectedMacedonianContinents

    private val _selectedEnglishContinents = MutableStateFlow<List<String>>(emptyList())
    val selectedEnglishContinents: StateFlow<List<String>> = _selectedEnglishContinents

    val continentNames = mapOf(
        "Австралија" to "Australia",
        "Антарктика" to "Antarctica",
        "Африка" to "Africa",
        "Азија" to "Asia",
        "Европа" to "Europe",
        "Јужна Америка" to "South America",
        "Северна Америка" to "North America"
    )

    fun toggleSelection(continent: String) {

        val englishContinent = continentNames[continent] ?: continent

        _selectedMacedonianContinents.update { currentList ->
            if (currentList.contains(continent)) currentList - continent else currentList + continent
        }
        _selectedEnglishContinents.update { currentList ->
            if (currentList.contains(englishContinent)) currentList - englishContinent else currentList + englishContinent
        }
    }
}

@Composable
fun FilterChipContinents(continent: String, viewModel: ContinentViewModel) {
    val selectedMacedonianContinents by viewModel.selectedMacedonianContinents.collectAsState()
    val selectedEnglishContinents by viewModel.selectedEnglishContinents.collectAsState()

    val englishName = viewModel.continentNames[continent] ?: continent
    val selected = selectedMacedonianContinents.contains(continent) ||
            selectedEnglishContinents.contains(englishName)

    FilterChip(
        onClick = { viewModel.toggleSelection(continent) },
        label = {
            Text(continent)
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}

fun saveSelectedImageToInternalStorage(context: Context, imageUri: Uri, pictureName: String) {
    try {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        if (bitmap != null) {
            val file = File(context.filesDir, pictureName)
            val outputStream = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            Log.d("Image Save", "Image saved at: ${file.absolutePath}")
        } else {
            Log.e("Image Save", "Failed to decode bitmap!")
        }
    } catch (e: IOException) {
        e.printStackTrace()
        Log.e("Image Save", "Error saving image: ${e.message}")
    }
}

@Composable
fun AddPhotoButton(context: Context, pictureName: String) {
    var selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri.value = uri
            Log.d("AddPhotoButton", "Selected URI: $uri")
            saveSelectedImageToInternalStorage(context, uri, "$pictureName.png")
        } else {
            Log.e("Image Save", "No image selected!")
        }
    }
    SmallFloatingActionButton(
        onClick = {
            Log.d("AddPhotoButton", "Launching image picker for $pictureName")
            pickImageLauncher.launch("image/*")
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        shape = CircleShape,
        modifier = Modifier.padding(start = 10.dp, end = 12.dp)
    ) {
        Row(modifier = Modifier
            .width(120.dp)
            .padding(18.dp)) {
            Icon(Icons.Filled.Add, "Add Photo", modifier = Modifier.weight(1f))
            Text(text = "Add photo")
        }
    }
}

@Composable
fun SubmitButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }, modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(MaterialTheme.colorScheme.primary)) {
        Text( text = "Save", color = Color.White)
    }
}

@Composable
fun FormText() {
    Text(
        text = "Insert data about the new animal!",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 10.dp)
    )
}

@Composable
fun AddAnimalForm(navController: NavController) {

    var nameEnglish = remember { mutableStateOf("") }
    var nameMacedonian = remember { mutableStateOf("") }
    var firstFactEnglish = remember { mutableStateOf("") }
    var firstFactMacedonian = remember { mutableStateOf("") }
    var secondFactEnglish = remember { mutableStateOf("") }
    var secondFactMacedonian = remember { mutableStateOf("") }
    var descriptionEnglish = remember { mutableStateOf("") }
    var descriptionMacedonian = remember { mutableStateOf("") }
    val context = LocalContext.current

    val continentList = listOf(
        "Австралија",
        "Антарктика",
        "Африка",
        "Азија",
        "Европа",
        "Јужна Америка",
        "Северна Америка"
    )

    val viewModel: ContinentViewModel = viewModel()

    val selectedMacedonianContinents by viewModel.selectedMacedonianContinents.collectAsState()
    val selectedEnglishContinents by viewModel.selectedEnglishContinents.collectAsState()

    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
    ) {
        item {
            FormText()
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            NameRow(nameEnglish = nameEnglish.value,
                    onChangeNameEnglish = { nameEnglish.value = it},
                    nameMacedonian = nameMacedonian.value,
                    onChangeNameMacedonian = { nameMacedonian.value = it})
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            FirstFactRow(factEnglish = firstFactEnglish.value,
                         onChangeFactEnglish = { firstFactEnglish.value = it},
                         factMacedonian = firstFactMacedonian.value,
                         onChangeFactMacedonian = { firstFactMacedonian.value = it})
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            SecondFactRow(factEnglish = secondFactEnglish.value,
                          onChangeFactEnglish = { secondFactEnglish.value = it},
                          factMacedonian = secondFactMacedonian.value,
                          onChangeFactMacedonian = { secondFactMacedonian.value = it})
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            DescriptionRow(descriptionEnglish = descriptionEnglish.value,
                           onChangedescriptionEnglish = { descriptionEnglish.value = it},
                           descriptionMacedonian = descriptionMacedonian.value,
                           onChangedescriptionMacedonian = { descriptionMacedonian.value = it})
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 15.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(viewModel.continentNames.keys.toList()) { continent ->
                    FilterChipContinents(continent, viewModel)
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
        item {
            AddPhotoButton(
                context = LocalContext.current,
                pictureName =  nameEnglish.value.lowercase())
            Spacer(modifier = Modifier.height(50.dp))
        }
        item {
            SubmitButton(onClick = {
                val macedonianContinents = selectedMacedonianContinents.joinToString(", ")
                val englishContinents = selectedEnglishContinents.joinToString(", ")
                val animal = Animal(
                    name = nameEnglish.value to nameMacedonian.value,
                    firstFact = firstFactEnglish.value to firstFactMacedonian.value,
                    secondFact = secondFactEnglish.value to secondFactMacedonian.value,
                    description = descriptionEnglish.value to descriptionMacedonian.value,
                    continent = englishContinents to macedonianContinents
                )
                writeToFile(context, "animals.txt",  animal)
                navController.navigate(Screen.AnimalScreen.route)
            })
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}