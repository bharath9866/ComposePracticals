package com.example.adaptivestreamingplayer.vernacular

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.utils.ImageVector
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.slSubjectName
import com.example.adaptivestreamingplayer.utils.onClickWithoutRipple
import com.google.gson.annotations.SerializedName

@Composable
fun DropdownList(items: ArrayList<Language>, onSelectedLanguageId: (Int) -> Unit) {

    var showDropdown by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ImageVector(
                modifier = Modifier.size(24.dp),
                imageModifier = Modifier,
                imageVector = R.drawable.ic_language_icon,
                contentAlignment = Alignment.Center
            )
            Column {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(39.dp))
                        .clickable { showDropdown = !showDropdown }
                        .border(1.dp, color = subjectTextColor(slSubjectName), shape = RoundedCornerShape(39.dp))
                        .padding(vertical = 4.5.dp)
                        .padding(start = 7.5.dp, end = 3.5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    Text(
                        text = items[selectedIndex].languageName?:"",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontWeight = FontWeight.Medium,
                        color = subjectTextColor(slSubjectName)
                    )
                    ImageVector(
                        modifier = Modifier.size(24.dp),
                        imageModifier = Modifier,
                        imageVector = R.drawable.ic_dropdown_icon,
                        colorFilter = ColorFilter.tint(subjectTextColor(slSubjectName))
                    )
                }

                // dropdown list
                if(showDropdown) {
                    Popup(
                        alignment = Alignment.TopStart,
                        onDismissRequest = { showDropdown = false }
                    ) {
                        ImageVector(

                        )
                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .clip(RoundedCornerShape(19.5.dp))
                                .border(
                                    width = 1.dp,
                                    color = subjectTextColor(slSubjectName),
                                    shape = RoundedCornerShape(19.5.dp)
                                )
                                .background(subjectColorInCompose(slSubjectName)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            items.filterIndexed { index, item -> index != selectedIndex  }.onEachIndexed { index, item ->
                                Text(
                                    text = items[index].languageName?:"",
                                    modifier = Modifier
                                        .onClickWithoutRipple {
                                            selectedIndex = items.indexOf(item)
                                            selectedIndex = index
                                            onSelectedLanguageId(item.languageID?:0)
                                            showDropdown = false
                                        }
                                        .padding(vertical = 4.5.dp, horizontal = 7.5.dp),
                                    color = subjectTextColor(slSubjectName)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

fun subjectTextColor(subject: String): Color = when (subject.trim().uppercase()) {
    "PHYSICS", "PHYSICS XI", "PHYSICS XII" -> Color(0xFFBE5E22)
    "CHEMISTRY", "CHEMISTRY XI", "CHEMISTRY XII" -> Color(0xFFBE3F53)
    "MATHEMATICS", "MATHS" -> Color(0xFF136884)
    "BIOLOGY", "BIOLOGY XI", "BIOLOGY XII" -> Color(0xFF578A43)
    "ZOOLOGY", "ZOOLOGY XI", "ZOOLOGY XII" -> Color(0xFF663D29)
    "BOTANY", "BOTANY XI", "BOTANY XII" -> Color(0xFF106446)
    else -> Color(0xFF3298E5)
}

fun subjectColorInCompose(subjectName:String):Color = when(subjectName.trim().uppercase()){
    "PHYSICS", "PHYSICS XI", "PHYSICS XII" -> Color(0xFFFFAD7A)
    "CHEMISTRY", "CHEMISTRY XI", "CHEMISTRY XII" -> Color(0xFFD85A6E)
    "MATHEMATICS", "MATHS" -> Color(0xFF288BAC)
    "BIOLOGY", "BIOLOGY XI", "BIOLOGY XII" -> Color(0xFF8EC37A)
    "ZOOLOGY", "ZOOLOGY XI", "ZOOLOGY XII" -> Color(0xFF7C5A49)
    "BOTANY", "BOTANY XI", "BOTANY XII" -> Color(0xFF229F72)
    else -> Color(0xFFB7DEFF)
}

data class Language(
    @SerializedName("languageID") var languageID: Int? = null,
    @SerializedName("languageName") var languageName: String? = null,
    var index:Int? = null
)