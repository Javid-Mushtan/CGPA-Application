package com.example.cgpaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cgpaapp.ui.theme.CGPAAppTheme

data class Semester(val grade:String, val credit: Int)
class MainActivity : ComponentActivity() {
    private var semesters: MutableList<Semester?> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CGPAAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CGPA(semesters)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CGPA(semesters: MutableList<Semester?>) {

    var grade1 by remember { mutableStateOf("") }
    var credit1 by remember { mutableStateOf<Int?>(null) }
    var grade2 by remember { mutableStateOf("") }
    var credit2 by remember { mutableStateOf<Int?>(null) }
    var grade3 by remember { mutableStateOf("") }
    var credit3 by remember { mutableStateOf<Int?>(null) }
    var grade4 by remember { mutableStateOf("") }
    var credit4 by remember { mutableStateOf<Int?>(null) }
    var cgpa by remember { mutableDoubleStateOf(0.0) }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize().verticalScroll(scrollState)
        .padding(10.dp, top = 20.dp, end = 10.dp)) {
        Text(
            text = "CGPA Calculator", modifier = Modifier.fillMaxWidth(),
            style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.poppins_semibold)),color = Color(0xFF000000))
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))

        SubjectText(subject = "Subject 1")
        GradeTextField(grade = (grade1), onValueChange = {grade1=it})
        Spacer8dp()
        CreditTextField(credit1) { credit1 = it }
        Spacer8dp()

        SubjectText(subject = "Subject 2")
        GradeTextField(grade = (grade2), onValueChange = {grade2=it})
        Spacer8dp()
        CreditTextField(credit2) { credit2 = it }
        Spacer8dp()
        SubjectText(subject = "Subject 3")
        GradeTextField(grade = (grade3), onValueChange = {grade3=it})
        Spacer8dp()
        CreditTextField(credit3) { credit3 = it }
        Spacer8dp()
        SubjectText(subject = "Subject 4")
        GradeTextField(grade = (grade4), onValueChange = {grade4=it})
        Spacer8dp()
        CreditTextField(credit4) { credit4 = it }
        Row(){
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween){
                Button(
                    onClick = {
                        val currentSemester = mutableListOf<Semester?>()

                        if(grade1.isNotEmpty() && credit1 != null){
                             currentSemester.add(Semester(grade1.uppercase(), credit1 ?: 0))
                        }

                        if(grade2.isNotEmpty() && credit2 != null){
                            currentSemester.add(Semester(grade2.uppercase(), credit2 ?: 0))
                        }

                        if(grade3.isNotEmpty() && credit3 != null){
                            currentSemester.add(Semester(grade3.uppercase(), credit3 ?: 0))
                        }

                        if(grade4.isNotEmpty() && credit4 != null){
                            currentSemester.add(Semester(grade4.uppercase(), credit4 ?: 0))
                        }

                        semesters.addAll(currentSemester)

                        var totalGradePoints = 0.0
                        var totalCredits = 0

                        semesters.forEach { semester ->
                            val gradePoint = when (semester?.grade) {
                                "A+" -> 4.0
                                "A" -> 4.0
                                "A-" -> 3.7
                                "B+" -> 3.3
                                "B" -> 3.0
                                "B-" -> 2.7
                                "C+" -> 2.3
                                "C" -> 2.0
                                "D" -> 1.0
                                else -> 0.0
                            }
                            totalGradePoints += gradePoint * (semester?.credit ?: 0)
                            totalCredits += semester?.credit ?:0
                        }

                        cgpa = if(totalCredits > 0) totalGradePoints / totalCredits else 0.0

                        grade1 = ""
                        credit1 = null
                        grade2 = ""
                        credit2 = null
                        grade3 = ""
                        credit3 = null
                        grade4 = ""
                        credit4 = null
                    }, colors = ButtonDefaults.buttonColors(
                    Color.Blue
                    ), shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Calculate GPA", fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium))
                    )
                }
                Surface( modifier = Modifier
                    .width(175.dp)
                    .wrapContentHeight(), color = Color(0xFF263238), shape = RoundedCornerShape(15.dp)) {
                    Text(modifier = Modifier.padding(start = 10.dp), text = "Your All Time\nCGPA : $cgpa ", style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_medium)), fontSize = 16.sp, color = Color.White,
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = 5.dp))
            }

            Surface( modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 4.dp,bottom = 50.dp), color = Color(0xFF263238), shape = RoundedCornerShape(15.dp)) {
                Column() {
                    Text(modifier = Modifier.padding(start = 10.dp), text = "Previous Semester", style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_medium)), fontSize = 16.sp, color = Color(0xFFFFFFFF),
                        )
                    )

                    if(semesters.isNotEmpty()) {
                        for (semester in semesters)
                            Text(
                                text = "Gread: ${semester?.grade} , Credit: ${semester?.credit}",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                    }
                    }
                }
            }
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
        }
    }


@Composable
fun GradeTextField(grade: String, onValueChange: (String) -> Unit) {
    TextField(
        value = grade,
        onValueChange = { text ->
            onValueChange(text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp),
        label = { Text(text = "Enter Grade", color = Color.White, fontSize = 12.sp) },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color(0xFF7E57C2),
        ),
        shape = RoundedCornerShape(15.dp),
        textStyle = TextStyle(fontSize = 12.sp, color = Color.White))
}

@Composable
fun Spacer8dp(){
    Spacer(modifier = Modifier.padding(top = 8.dp))
}

@Composable
fun CreditTextField(credit: Int?, onValueChange: (Int?) -> Unit){
    TextField(value = credit?.toString() ?:"", onValueChange = {text ->
        onValueChange(text.toIntOrNull())
    }, modifier = Modifier
        .fillMaxWidth()
        .height(47.dp),
        label = { Text(text = "Enter Credit", color = Color.Black, fontSize = 12.sp )}
        ,colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color(0xFFCCCCFF),
        ), shape = RoundedCornerShape(15.dp),
        textStyle = TextStyle(fontSize = 12.sp, color = Color.White))
}

@Preview(showBackground = true)
@Composable
fun CGPAPreview() {
    CGPAAppTheme {
        CGPA(semesters = mutableListOf())
    }
}

@Composable
fun SubjectText(subject :String){
    Text(
        text = subject, modifier = Modifier.fillMaxWidth(),
        style = TextStyle(fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),color = Color.Black)
    )
}


