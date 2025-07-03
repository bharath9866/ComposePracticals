package com.example.adaptivestreamingplayer.olympiad

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
private fun SkillsAssessmentTablePreview() {
    val list = listOf(
        Skill(
            name = "Problem Solving",
            subSkills = listOf(
                SubSkill("Simplifying Problem", 50, "yellow"),
                SubSkill("Coding-Decoding", 25, "red"),
                SubSkill("Visual Problem Solving", 100, "blue"),
                SubSkill("Eliminating Possibility", 33, "yellow"),
                SubSkill("Numeric Pattern", 50, "yellow"),
                SubSkill("Equations", 25, "red"),
                SubSkill("Simplifying Problems", 0, "red"),
                SubSkill("Visual Solving Problem", 100, "blue"),
                SubSkill("Analysing Data", 50, "yellow")
            ),
            stanine = 4
        )
    )
    SkillsAssessmentTable(list)
}

@Composable
fun SkillsAssessmentTable(skills: List<Skill>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        item {
            SkillsTableHeader()
        }

        // Skills data
        skills.forEach { skill ->
            items(skill.subSkills.size) { index ->
                SkillsTableRow(
                    skillName = if (index == 0) skill.name else "",
                    subSkill = skill.subSkills[index],
                    stanine = if (index == 0) skill.stanine.toString() else "",
                    isFirstInGroup = index == 0
                )
            }
        }
    }
}

@Composable
fun SkillsTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE5E7EB), RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .border(1.dp, Color(0xFFD1D5DB))
            .padding(16.dp)
    ) {
        listOf("Skill", "Sub-Skill", "Score %", "Stanine").forEach { header ->
            Text(
                text = header,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF374151)
            )
        }
    }
}

@Composable
fun SkillsTableRow(
    skillName: String,
    subSkill: SubSkill,
    stanine: String,
    isFirstInGroup: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color(0xFFE5E7EB))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = skillName,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = Color(0xFF374151)
        )

        Text(
            text = subSkill.name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color(0xFF374151)
        )

        Text(
            text = subSkill.score.toString(),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = when (subSkill.scoreColor) {
                "yellow" -> Color(0xFFF59E0B)
                "red" -> Color(0xFFEF4444)
                "blue" -> Color(0xFF3B82F6)
                else -> Color(0xFF374151)
            }
        )

        Text(
            text = stanine,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color(0xFF374151)
        )
    }
}

data class SubSkill(
    val name: String,
    val score: Int,
    val scoreColor: String
)

data class Skill(
    val name: String,
    val subSkills: List<SubSkill>,
    val stanine: Int
)