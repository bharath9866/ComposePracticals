package com.example.adaptivestreamingplayer.api

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

val fullText = """
        Reading is a fundamental skill that opens doors to knowledge, imagination, and personal growth. It allows us to explore diverse worlds, understand different perspectives, and expand our vocabulary. From the earliest picture books to complex novels, reading provides endless opportunities for learning and enjoyment. 
        Firstly, reading is crucial for academic success. It enhances vocabulary, improves comprehension skills, and strengthens analytical thinking. By engaging with texts, students develop the ability to grasp complex concepts, evaluate information critically, and express their ideas effectively. These skills are essential not only in the classroom but also in various aspects of life, including professional careers and personal relationships. 
        Secondly, reading fosters creativity and imagination. Through stories, poems, and other forms of literature, readers can escape the ordinary and explore fantastical realms. They can step into the shoes of different characters, experience their emotions, and develop a deeper understanding of human nature. This imaginative capacity can inspire new ideas, spark creativity, and enhance problem-solving abilities. 
        Finally, reading promotes empathy and understanding. By encountering diverse characters and narratives, readers gain insights into different cultures, perspectives, and experiences. They learn to appreciate the richness and complexity of human existence, fostering tolerance and compassion. 
        In conclusion, the importance of reading cannot be overstated. It is a gateway to knowledge, imagination, and personal growth. Encouraging a love of reading from a young age and fostering its continued practice throughout life is essential for individual and societal well-being. 
        Sample Essay 2: The Impact of Technology on Communication
        Technology has revolutionized communication, bringing people closer together while also creating new challenges. From instant messaging to video calls, technology has made it easier than ever to connect with others across geographical boundaries. However, it has also led to a decline in face-to-face interactions and raised concerns about the authenticity of communication. 
        One of the most significant impacts of technology on communication is its speed and convenience. Instant messaging and social media platforms allow for rapid exchange of information, enabling individuals to stay connected in real-time. Video calls enable face-to-face interactions even when individuals are miles apart. This has been particularly beneficial for families and friends who live far from each other or for business professionals who need to collaborate remotely. 
        However, this increased reliance on technology for communication has also led to a decline in face-to-face interactions. People spend more time interacting through screens and less time engaging in meaningful, in-person conversations. This can lead to a sense of isolation and a decrease in social skills. 
        Furthermore, the nature of communication online can sometimes be superficial. The lack of non-verbal cues like body language and tone of voice can lead to misunderstandings. Online communication can also be less personal and authentic, as individuals may present a curated version of themselves. 
        Reading is a fundamental skill that opens doors to knowledge, imagination, and personal growth. It allows us to explore diverse worlds, understand different perspectives, and expand our vocabulary. From the earliest picture books to complex novels, reading provides endless opportunities for learning and enjoyment. 
        Firstly, reading is crucial for academic success. It enhances vocabulary, improves comprehension skills, and strengthens analytical thinking. By engaging with texts, students develop the ability to grasp complex concepts, evaluate information critically, and express their ideas effectively. These skills are essential not only in the classroom but also in various aspects of life, including professional careers and personal relationships. 
        Secondly, reading fosters creativity and imagination. Through stories, poems, and other forms of literature, readers can escape the ordinary and explore fantastical realms. They can step into the shoes of different characters, experience their emotions, and develop a deeper understanding of human nature. This imaginative capacity can inspire new ideas, spark creativity, and enhance problem-solving abilities. 
        Finally, reading promotes empathy and understanding. By encountering diverse characters and narratives, readers gain insights into different cultures, perspectives, and experiences. They learn to appreciate the richness and complexity of human existence, fostering tolerance and compassion. 
        In conclusion, the importance of reading cannot be overstated. It is a gateway to knowledge, imagination, and personal growth. Encouraging a love of reading from a young age and fostering its continued practice throughout life is essential for individual and societal well-being. 
        Sample Essay 2: The Impact of Technology on Communication
        Technology has revolutionized communication, bringing people closer together while also creating new challenges. From instant messaging to video calls, technology has made it easier than ever to connect with others across geographical boundaries. However, it has also led to a decline in face-to-face interactions and raised concerns about the authenticity of communication. 
        One of the most significant impacts of technology on communication is its speed and convenience. Instant messaging and social media platforms allow for rapid exchange of information, enabling individuals to stay connected in real-time. Video calls enable face-to-face interactions even when individuals are miles apart. This has been particularly beneficial for families and friends who live far from each other or for business professionals who need to collaborate remotely. 
        However, this increased reliance on technology for communication has also led to a decline in face-to-face interactions. People spend more time interacting through screens and less time engaging in meaningful, in-person conversations. This can lead to a sense of isolation and a decrease in social skills. 
        Furthermore, the nature of communication online can sometimes be superficial. The lack of non-verbal cues like body language and tone of voice can lead to misunderstandings. Online communication can also be less personal and authentic, as individuals may present a curated version of themselves.
        Reading is a fundamental skill that opens doors to knowledge, imagination, and personal growth. It allows us to explore diverse worlds, understand different perspectives, and expand our vocabulary. From the earliest picture books to complex novels, reading provides endless opportunities for learning and enjoyment. 
        Firstly, reading is crucial for academic success. It enhances vocabulary, improves comprehension skills, and strengthens analytical thinking. By engaging with texts, students develop the ability to grasp complex concepts, evaluate information critically, and express their ideas effectively. These skills are essential not only in the classroom but also in various aspects of life, including professional careers and personal relationships. 
        Secondly, reading fosters creativity and imagination. Through stories, poems, and other forms of literature, readers can escape the ordinary and explore fantastical realms. They can step into the shoes of different characters, experience their emotions, and develop a deeper understanding of human nature. This imaginative capacity can inspire new ideas, spark creativity, and enhance problem-solving abilities. 
        Finally, reading promotes empathy and understanding. By encountering diverse characters and narratives, readers gain insights into different cultures, perspectives, and experiences. They learn to appreciate the richness and complexity of human existence, fostering tolerance and compassion. 
        In conclusion, the importance of reading cannot be overstated. It is a gateway to knowledge, imagination, and personal growth. Encouraging a love of reading from a young age and fostering its continued practice throughout life is essential for individual and societal well-being. 
        Sample Essay 2: The Impact of Technology on Communication
        Technology has revolutionized communication, bringing people closer together while also creating new challenges. From instant messaging to video calls, technology has made it easier than ever to connect with others across geographical boundaries. However, it has also led to a decline in face-to-face interactions and raised concerns about the authenticity of communication. 
        One of the most significant impacts of technology on communication is its speed and convenience. Instant messaging and social media platforms allow for rapid exchange of information, enabling individuals to stay connected in real-time. Video calls enable face-to-face interactions even when individuals are miles apart. This has been particularly beneficial for families and friends who live far from each other or for business professionals who need to collaborate remotely. 
        However, this increased reliance on technology for communication has also led to a decline in face-to-face interactions. People spend more time interacting through screens and less time engaging in meaningful, in-person conversations. This can lead to a sense of isolation and a decrease in social skills. 
        Furthermore, the nature of communication online can sometimes be superficial. The lack of non-verbal cues like body language and tone of voice can lead to misunderstandings. Online communication can also be less personal and authentic, as individuals may present a curated version of themselves.
    """.trimIndent()
@Preview
@Composable
fun AutoScrollTextView() {
    // Simple states to track what's happening
    var isStreaming by remember { mutableStateOf(false) }
    var currentText by remember { mutableStateOf("") }
    var streamingKey by remember { mutableStateOf(0) } // Key to control LaunchedEffect
    val scrollState = rememberScrollState()
    // Function to start the streaming effect
    fun startStreaming() {
        isStreaming = true
        currentText = ""
        streamingKey++ // Update key to restart LaunchedEffect
    }

    // Function to stop streaming
    fun stopStreaming() {
        isStreaming = false
        streamingKey++ // Update key to cancel LaunchedEffect
    }

    // Function to clear everything
    fun clearText() {
        isStreaming = false
        currentText = ""
        streamingKey++ // Update key to cancel LaunchedEffect
    }

    // This effect runs when streamingKey changes (not when touch events happen)
    LaunchedEffect(streamingKey) {
        if (isStreaming) {
            // Split text into individual words
            val words = fullText.split(Regex("\\s+"))
            var buildingText = ""

            // Add each word one by one
            for (word in words) {
                if (!isActive || !isStreaming) break

                // Add the next word
                buildingText = if (buildingText.isEmpty()) {
                    word
                } else {
                    "$buildingText $word"
                }
                currentText = buildingText

                // Wait a bit before showing next word (like typing delay)
                val waitTime = when {
                    word.endsWith(".") || word.endsWith("!") || word.endsWith("?") ->
                        Random.nextLong(50, 100) // Much faster - sentences
                    word.endsWith(",") || word.endsWith(";") || word.endsWith(":") ->
                        Random.nextLong(25, 50)  // Much faster - commas
                    word.length > 8 ->
                        Random.nextLong(15, 40)  // Much faster - long words
                    else ->
                        Random.nextLong(10, 30)   // Much faster - normal words
                }
                delay(waitTime)

                // Check if still active after delay
                if (!isActive || !isStreaming) break

                // Auto-scroll to bottom in a separate coroutine to prevent blocking
                launch {
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            }

            // Finished streaming
            isStreaming = false
        }
    }

        // The UI layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Optimized Auto-Scroll Text Demo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Control buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            // Start button
            Button(
                onClick = { startStreaming() },
                enabled = !isStreaming // Disable when already streaming
            ) {
                Text("Start Streaming")
            }

            // Clear button
            Button(onClick = { clearText() }) {
                Text("Clear")
            }

            // Stop button
            Button(
                onClick = { stopStreaming() },
                enabled = isStreaming // Only enabled when streaming
            ) {
                Text("Stop")
            }
        }

        // Text display area
        Card(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = currentText,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                fontFamily = FontFamily.Default,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4f
            )
        }
    }
}