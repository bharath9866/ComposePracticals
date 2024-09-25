package com.example.adaptivestreamingplayer.vernacular

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.utils.ImageVector
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.currentLanguageId
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.slSubjectName
import com.example.adaptivestreamingplayer.utils.onClickWithoutRipple

@Preview
@Composable
private fun LanguageBarPreview() {
    LanguageBar(
        items = arrayListOf(
            Language(1, "English", index = 0),
            Language(2, "Hindi", 1),
            Language(3, "Hinglish", 2),
            Language(4, "Telugu", 3)
        ),
        onSelectedLanguageId = {

        }
    )
}

@Composable
fun LanguageBar(modifier: Modifier = Modifier, items: ArrayList<Language>, onSelectedLanguageId: (Int) -> Unit = {}) {

    var currentLanguageId :Int? by remember { mutableStateOf(null) }
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by remember {
        mutableIntStateOf(
            items.find { it.languageID == currentLanguageId }?.index ?: 0
        )
    }

    LaunchedEffect(selectedIndex) {
        currentLanguageId.takeIf { it != null && it != -1 } ?: items[selectedIndex].languageID?.let {
            currentLanguageId = it
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ImageVector(
                modifier = Modifier.size(24.dp),
                imageModifier = Modifier,
                imageVector = R.drawable.ic_language_icon,
                contentAlignment = Alignment.Center,
                colorFilter = ColorFilter.tint(subjectTextColor(slSubjectName))
            )
            Column {
                RowContent(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(39.dp))
                        .clickable { showDropdown = !showDropdown }
                        .border(
                            width = 1.dp,
                            color = subjectTextColor(slSubjectName),
                            shape = RoundedCornerShape(39.dp)
                        ),
                    items = items,
                    textColor = subjectTextColor(slSubjectName),
                    selectedIndex = selectedIndex,
                    languageName = items.find { it.languageID == currentLanguageId }?.languageName ?: ""
                )
                // Second row (Dropdown content)
                if (showDropdown) {
                    DropdownContent(items, subjectTextColor(slSubjectName),
                        onItemSelected = { index ->
                            selectedIndex = index
                            items[index].languageID?.let {
                                currentLanguageId = it
                                onSelectedLanguageId(it)
                            }
                            showDropdown = false
                        },
                        onDismissRequest = {
                            showDropdown = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RowContent(
    modifier: Modifier,
    items: ArrayList<Language>,
    textColor: Color,
    selectedIndex:Int,
    languageName:String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = items.maxByOrNull { it.languageName?.length?:0 }?.languageName?:"",
                modifier = Modifier.padding(start = 7.5.dp, top = 4.5.dp, bottom = 4.5.dp),
                fontWeight = FontWeight.Medium,
                color = Color.Transparent
            )
            Text(
                text = languageName,
                modifier = Modifier.padding(start = 7.5.dp, top = 4.5.dp, bottom = 4.5.dp),
                fontWeight = FontWeight.Medium,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
        ImageVector(
            modifier = Modifier
                .size(24.dp)
                .padding(end = 3.5.dp),
            imageModifier = Modifier.size(24.dp),
            imageVector = R.drawable.ic_dropdown_icon,
            colorFilter = ColorFilter.tint(textColor)
        )
    }
}

@Composable
fun DropdownContent(
    items: ArrayList<Language>,
    textColor: Color,
    onItemSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    Box {
        Popup(
            alignment = Alignment.TopStart,
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(19.5.dp))
                    .border(
                        width = 1.dp,
                        color = textColor,
                        shape = RoundedCornerShape(19.5.dp)
                    )
                    .background(subjectColorInCompose(slSubjectName)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items.filter { it.languageID != currentLanguageId }.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .onClickWithoutRipple {
                                onItemSelected(items.indexOf(item))
                            },
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.languageName ?: "",
                            modifier = Modifier.padding(
                                vertical = 4.5.dp,
                                horizontal = 19.5.dp
                            ),
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}