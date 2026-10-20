package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuestionBankGenerator
import com.example.data.QuizQuestionItem
import com.example.data.SampleData
import com.example.viewmodel.CodeUdaanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: CodeUdaanViewModel,
    onCloseQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    var quizLanguage by remember { mutableStateOf("C") }
    var quizDifficulty by remember { mutableStateOf("Easy") }
    var isQuizStarted by remember { mutableStateOf(false) }

    // Quiz Questions List (5 Random Questions)
    val quizItems = remember { mutableStateListOf<QuizQuestionItem>() }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val userSelectedAnswers = remember { mutableStateListOf(-1, -1, -1, -1, -1) }
    var isQuizSubmitted by remember { mutableStateOf(false) }

    fun startNewQuiz() {
        val pool = SampleData.practiceQuestions.filter { q ->
            (quizLanguage == "All" || q.language.equals(quizLanguage, ignoreCase = true)) &&
            (quizDifficulty == "All" || q.difficulty.equals(quizDifficulty, ignoreCase = true))
        }

        val selectedRaw = if (pool.size >= 5) pool.shuffled().take(5) else SampleData.practiceQuestions.shuffled().take(5)
        
        quizItems.clear()
        selectedRaw.forEach { rawQ ->
            quizItems.add(QuestionBankGenerator.buildQuizItem(rawQ))
        }

        currentQuestionIndex = 0
        for (i in 0..4) userSelectedAnswers[i] = -1
        isQuizSubmitted = false
        isQuizStarted = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = if (isQuizSubmitted) "Quiz Score Summary" else if (isQuizStarted) "5-Question Quiz Mode" else "Setup Quiz",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onCloseQuiz) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Quiz")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
        )

        if (!isQuizStarted) {
            // Setup Screen Mode
            QuizSetupView(
                selectedLang = quizLanguage,
                onSelectLang = { quizLanguage = it },
                selectedDiff = quizDifficulty,
                onSelectDiff = { quizDifficulty = it },
                onStartClick = { startNewQuiz() }
            )
        } else if (isQuizSubmitted) {
            // Score Summary Screen Mode
            QuizSummaryView(
                userName = viewModel.userName,
                quizItems = quizItems,
                userAnswers = userSelectedAnswers,
                onRetakeClick = { startNewQuiz() },
                onCloseClick = onCloseQuiz
            )
        } else {
            // Paginated Quiz View (Questions 1 to 5)
            val currentItem = quizItems.getOrNull(currentQuestionIndex)
            if (currentItem != null) {
                PaginatedQuizQuestionView(
                    questionIndex = currentQuestionIndex,
                    totalQuestions = 5,
                    item = currentItem,
                    selectedOptionIndex = userSelectedAnswers.getOrElse(currentQuestionIndex) { -1 },
                    onOptionSelect = { optionIdx ->
                        if (currentQuestionIndex in 0..4) {
                            userSelectedAnswers[currentQuestionIndex] = optionIdx
                        }
                    },
                    onPreviousClick = {
                        if (currentQuestionIndex > 0) currentQuestionIndex--
                    },
                    onNextClick = {
                        if (currentQuestionIndex < 4) currentQuestionIndex++
                    },
                    onSubmitClick = {
                        isQuizSubmitted = true
                    }
                )
            }
        }
    }
}

@Composable
fun QuizSetupView(
    selectedLang: String,
    onSelectLang: (String) -> Unit,
    selectedDiff: String,
    onSelectDiff: (String) -> Unit,
    onStartClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val languages = listOf("C", "Python", "JavaScript", "Java", "All")
    val difficulties = listOf("Easy", "Medium", "Hard", "All")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Terminal,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CodeUdaan Practice Quiz",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Test your problem-solving skills with 5 random questions generated from our local 1800+ question dataset.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Select Language:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    languages.take(3).forEach { lang ->
                        FilterChip(
                            selected = selectedLang == lang,
                            onClick = { onSelectLang(lang) },
                            label = { Text(lang) },
                            modifier = Modifier.testTag("quiz_setup_lang_$lang")
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    languages.drop(3).forEach { lang ->
                        FilterChip(
                            selected = selectedLang == lang,
                            onClick = { onSelectLang(lang) },
                            label = { Text(lang) },
                            modifier = Modifier.testTag("quiz_setup_lang_$lang")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Select Difficulty:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    difficulties.forEach { diff ->
                        FilterChip(
                            selected = selectedDiff == diff,
                            onClick = { onSelectDiff(diff) },
                            label = { Text(diff) },
                            modifier = Modifier.testTag("quiz_setup_diff_$diff")
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onStartClick,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("start_quiz_launch_btn")
        ) {
            Text("Launch 5-Question Quiz 🚀", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PaginatedQuizQuestionView(
    questionIndex: Int,
    totalQuestions: Int,
    item: QuizQuestionItem,
    selectedOptionIndex: Int,
    onOptionSelect: (Int) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Progress Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question ${questionIndex + 1} of $totalQuestions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = "${item.question.language} • ${item.question.difficulty}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress Bar
        LinearProgressIndicator(
            progress = { (questionIndex + 1) / totalQuestions.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Question Details & Options Container
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = item.question.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.question.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Code Snippet Box
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.question.starterCode,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Select Output / Logic Choice:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 4 Multiple Choice Option Cards
            item.options.forEachIndexed { optionIdx, optionText ->
                val isSelected = selectedOptionIndex == optionIdx

                Card(
                    onClick = { onOptionSelect(optionIdx) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .testTag("quiz_option_${questionIndex}_$optionIdx"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    ),
                    border = if (isSelected)
                        androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                    else
                        androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { onOptionSelect(optionIdx) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Footer Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onPreviousClick,
                enabled = questionIndex > 0,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Previous")
            }

            if (questionIndex < totalQuestions - 1) {
                Button(
                    onClick = onNextClick,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("quiz_next_btn")
                ) {
                    Text("Next Question")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            } else {
                Button(
                    onClick = onSubmitClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier.testTag("submit_quiz_btn")
                ) {
                    Text("Submit Quiz & Score")
                }
            }
        }
    }
}

@Composable
fun QuizSummaryView(
    userName: String,
    quizItems: List<QuizQuestionItem>,
    userAnswers: List<Int>,
    onRetakeClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    var correctCount = 0
    quizItems.forEachIndexed { idx, item ->
        val chosen = userAnswers.getOrElse(idx) { -1 }
        if (chosen == item.correctOptionIndex) {
            correctCount++
        }
    }

    val total = quizItems.size
    val percentage = (correctCount * 100) / total
    val displayName = if (userName.isNotBlank()) userName else "Learner"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Score Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Quiz Results for $displayName",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$correctCount / $total Correct ($percentage%)",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.testTag("quiz_score_text")
                )

                Spacer(modifier = Modifier.height(8.dp))

                val performanceBadge = when {
                    percentage >= 80 -> "Outstanding Achievement! 🏆"
                    percentage >= 60 -> "Good Performance! 👍"
                    else -> "Keep Practicing & Learning! 💪"
                }

                Text(
                    text = performanceBadge,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Detailed Breakdown",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Breakdown items
        quizItems.forEachIndexed { idx, item ->
            val userChosenIdx = userAnswers.getOrElse(idx) { -1 }
            val isCorrect = userChosenIdx == item.correctOptionIndex

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Q${idx + 1}: ${item.question.title}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isCorrect) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                        ) {
                            Text(
                                text = if (isCorrect) "Correct" else "Incorrect",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) Color(0xFF15803D) else Color(0xFFB91C1C),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val chosenText = item.options.getOrNull(userChosenIdx) ?: "No Answer Selected"
                    val correctText = item.options.getOrNull(item.correctOptionIndex) ?: "N/A"

                    Text(
                        text = "Your Answer: $chosenText",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isCorrect) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                    )

                    if (!isCorrect) {
                        Text(
                            text = "Correct Answer: $correctText",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = item.question.explanation,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onRetakeClick,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Retake Quiz")
            }

            Button(
                onClick = onCloseClick,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Back to Practice")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
