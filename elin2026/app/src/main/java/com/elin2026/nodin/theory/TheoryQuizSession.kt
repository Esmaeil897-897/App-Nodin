package com.elin2026.nodin.theory

import kotlin.random.Random

data class TheoryQuestion(
    val id: String,
    val category: String,
    val prompt: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String = ""
) {
    init {
        require(id.isNotBlank()) { "Question id cannot be blank" }
        require(prompt.isNotBlank()) { "Question prompt cannot be blank" }
        require(options.size in 2..6) { "A theory question must have 2–6 options" }
        require(options.all { it.isNotBlank() }) { "Options cannot be blank" }
        require(correctOptionIndex in options.indices) { "Correct answer index is out of range" }
    }
}

data class QuizResult(
    val totalQuestions: Int,
    val answeredQuestions: Int,
    val correctAnswers: Int
) {
    val incorrectAnswers: Int get() = answeredQuestions - correctAnswers
    val scorePercent: Int
        get() = if (answeredQuestions == 0) 0 else (correctAnswers * 100) / answeredQuestions
    val isComplete: Boolean get() = answeredQuestions == totalQuestions
}

/** A deterministic, UI-independent quiz session. Each question can be answered once. */
class TheoryQuizSession private constructor(val questions: List<TheoryQuestion>) {
    private val answers = linkedMapOf<String, Int>()
    val currentIndex: Int get() = answers.size
    val isComplete: Boolean get() = answers.size == questions.size
    val currentQuestion: TheoryQuestion? get() = questions.getOrNull(currentIndex)
    val answeredCount: Int get() = answers.size
    val correctCount: Int
        get() = answers.count { (id, selected) ->
            questions.first { it.id == id }.correctOptionIndex == selected
        }

    fun answer(optionIndex: Int): Boolean {
        check(!isComplete) { "Quiz is already complete" }
        val question = currentQuestion ?: error("No current question")
        require(optionIndex in question.options.indices) { "Selected option is out of range" }
        answers[question.id] = optionIndex
        return optionIndex == question.correctOptionIndex
    }

    fun selectedAnswer(questionId: String): Int? = answers[questionId]

    fun result(): QuizResult = QuizResult(
        totalQuestions = questions.size,
        answeredQuestions = answers.size,
        correctAnswers = correctCount
    )

    companion object {
        fun create(
            questionBank: List<TheoryQuestion>,
            questionCount: Int = 10,
            random: Random = Random.Default
        ): TheoryQuizSession {
            require(questionBank.isNotEmpty()) { "Question bank is empty" }
            require(questionBank.map { it.id }.distinct().size == questionBank.size) {
                "Question ids must be unique"
            }
            require(questionCount > 0) { "Question count must be positive" }
            return TheoryQuizSession(questionBank.shuffled(random).take(questionCount))
        }
    }
}
