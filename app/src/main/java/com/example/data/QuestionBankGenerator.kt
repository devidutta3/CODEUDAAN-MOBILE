package com.example.data

data class QuizQuestionItem(
    val question: PracticeQuestion,
    val options: List<String>,
    val correctOptionIndex: Int
)

object QuestionBankGenerator {

    fun generateAllQuestions(): List<PracticeQuestion> {
        val questions = mutableListOf<PracticeQuestion>()

        val languages = listOf("C", "Python", "JavaScript", "Java")

        languages.forEach { lang ->
            questions.addAll(generateQuestionsForLanguage(lang))
        }

        return questions
    }

    private fun generateQuestionsForLanguage(lang: String): List<PracticeQuestion> {
        val list = mutableListOf<PracticeQuestion>()

        val topicsEasy = listOf(
            "Basic I/O & Variables", "Arithmetic & Expressions", "Conditionals & Logic",
            "Loops & Counting", "Simple Functions", "Array Indexing",
            "String Basics", "Math Utilities", "Type Casting", "Bitwise Basics"
        )

        val topicsMedium = listOf(
            "Two Pointers & Arrays", "Matrix Operations", "String Processing",
            "Searching & Sorting", "Recursion & Backtracking", "Data Structures",
            "Object-Oriented Logic", "Bitwise Tricks", "File & I/O Patterns", "Pointers & References"
        )

        val topicsHard = listOf(
            "Dynamic Programming", "Graph Traversal (BFS/DFS)", "Advanced Trees & Heaps",
            "Linked List Mutations", "Complex Pointer Arithmetic", "Greedy Algorithms",
            "Concurrency Concepts", "System Design Snippets", "Bitmask Optimization", "Stack & Queue Logic"
        )

        // Generate 150 Easy Questions for this language
        for (i in 1..150) {
            val topic = topicsEasy[(i - 1) % topicsEasy.size]
            list.add(createQuestion(lang, "Easy", i, topic))
        }

        // Generate 150 Medium Questions for this language
        for (i in 1..150) {
            val topic = topicsMedium[(i - 1) % topicsMedium.size]
            list.add(createQuestion(lang, "Medium", i, topic))
        }

        // Generate 150 Hard Questions for this language
        for (i in 1..150) {
            val topic = topicsHard[(i - 1) % topicsHard.size]
            list.add(createQuestion(lang, "Hard", i, topic))
        }

        return list
    }

    private fun createQuestion(
        lang: String,
        difficulty: String,
        index: Int,
        topic: String
    ): PracticeQuestion {
        val qId = "${lang.lowercase()}_${difficulty.lowercase()}_$index"

        val title = getQuestionTitle(lang, difficulty, index, topic)
        val description = getQuestionDescription(lang, difficulty, index, topic)
        val (input, output) = getExampleInputOutput(difficulty, index)
        val starterCode = getStarterCode(lang, difficulty, index, title)
        val explanation = getExplanation(lang, difficulty, topic)

        return PracticeQuestion(
            id = qId,
            title = title,
            description = description,
            exampleInput = input,
            exampleOutput = output,
            difficulty = difficulty,
            language = lang,
            topic = topic,
            starterCode = starterCode,
            explanation = explanation
        )
    }

    fun buildQuizItem(question: PracticeQuestion): QuizQuestionItem {
        val rawOutput = question.exampleOutput.replace("Output: ", "").trim()
        val correctAns = if (rawOutput.isNotBlank()) rawOutput else "Expected Output"

        val distractor1 = "Syntax Error / Compile Failure"
        val distractor2 = "Runtime Exception / Memory Leak"
        val distractor3 = "Incorrect Calculated Value: 0"

        val options = listOf(correctAns, distractor1, distractor2, distractor3).shuffled()
        val correctIdx = options.indexOf(correctAns).coerceAtLeast(0)

        return QuizQuestionItem(
            question = question,
            options = options,
            correctOptionIndex = correctIdx
        )
    }

    private fun getQuestionTitle(lang: String, difficulty: String, idx: Int, topic: String): String {
        val easyTitles = listOf(
            "Print User Greeting & Inputs", "Check Even or Odd Number", "Calculate Area of Circle",
            "Find Maximum of Two Values", "Sum of First N Natural Numbers", "Celsius to Fahrenheit Converter",
            "Count Digits in an Integer", "Reverse a Three-Digit Number", "Check Leap Year Condition",
            "Multiplication Table Generator", "Check Positive, Negative or Zero", "Calculate Simple Interest",
            "Swap Two Variables Without Temp", "Find Smallest Element in Array", "Check Vowel or Consonant"
        )

        val mediumTitles = listOf(
            "Reverse Array In-Place", "Matrix Transpose & Multiplication", "Palindrome String Verification",
            "Binary Search Algorithm", "Bubble & Selection Sort Logic", "Fibonacci Series via Recursion",
            "Find Duplicate Elements in Array", "Count Word Frequency in Text", "Factorial using Tail Recursion",
            "Merge Two Sorted Arrays", "Rotate Array by K Positions", "Check Anagram Strings",
            "Find Second Largest Number", "GCD & LCM of Two Integers", "Remove Duplicates from List"
        )

        val hardTitles = listOf(
            "Linked List Inversion & Cycle Detection", "Longest Common Subsequence (DP)", "0/1 Knapsack Problem Optimization",
            "Graph Shortest Path (Dijkstra)", "N-Queens Backtracking Solver", "Longest Palindromic Substring",
            "Binary Tree Level Order Traversal", "Implement LRU Cache Mechanism", "Max Subarray Sum (Kadane's Algo)",
            "Serialize and Deserialize Binary Tree", "Merge K Sorted Lists", "Sudoku Solver Engine",
            "Bitmask Subset Generation", "Evaluate Reverse Polish Notation", "Detect Loop in Singly Linked List"
        )

        val baseList = when (difficulty) {
            "Easy" -> easyTitles
            "Medium" -> mediumTitles
            else -> hardTitles
        }

        val baseTitle = baseList[(idx - 1) % baseList.size]
        return "$baseTitle #$idx ($lang)"
    }

    private fun getQuestionDescription(lang: String, difficulty: String, idx: Int, topic: String): String {
        return "[$lang - $difficulty] Solve $topic problem #$idx. Write a clean $lang program to implement the required business logic with $difficulty efficiency constraints."
    }

    private fun getExampleInputOutput(difficulty: String, idx: Int): Pair<String, String> {
        val num1 = (idx * 3) % 50 + 1
        val num2 = (idx * 7) % 50 + 2
        return when (difficulty) {
            "Easy" -> "Input: $num1, $num2" to "Output: ${num1 + num2}"
            "Medium" -> "Input: Array[$num1, $num2, ${num1 + 5}]" to "Output: Result = ${num1 * 2}"
            else -> "Input: Matrix / Graph Node ($num1)" to "Output: Path Length = ${num1 + num2}"
        }
    }

    private fun getStarterCode(lang: String, difficulty: String, idx: Int, title: String): String {
        return when (lang) {
            "C" -> """
#include <stdio.h>
#include <stdlib.h>

// Problem: $title
void solve() {
    int a = $idx, b = ${idx + 5};
    // Implement logic here
    printf("Result: %d\n", a + b);
}

int main() {
    solve();
    return 0;
}
            """.trimIndent()

            "Python" -> """
# Problem: $title
def solve(n=$idx):
    # Implement $difficulty solution in Python
    result = [i * 2 for i in range(n % 10 + 1)]
    return result

print(solve())
            """.trimIndent()

            "JavaScript" -> """
// Problem: $title
function solve(input = $idx) {
    // Implement $difficulty JS algorithm
    const result = input % 2 === 0 ? "Even" : "Odd";
    return result;
}

console.log(solve());
            """.trimIndent()

            "Java" -> """
// Problem: $title
public class Solution {
    public static void main(String[] args) {
        int val = $idx;
        System.out.println("Processing " + val);
    }
}
            """.trimIndent()

            else -> "// Starter Code"
        }
    }

    private fun getExplanation(lang: String, difficulty: String, topic: String): String {
        return "Language: $lang | Difficulty: $difficulty | Focus: $topic. Implement standard algorithmic approaches adhering to time & space constraints."
    }
}
