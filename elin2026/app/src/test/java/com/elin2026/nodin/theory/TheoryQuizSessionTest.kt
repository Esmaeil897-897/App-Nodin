package com.elin2026.nodin.theory

import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class TheoryQuizSessionTest {
    private fun bank() = listOf(
        TheoryQuestion("q1", "rules", "Question one?", listOf("A", "B", "C"), 1, "Because B"),
        TheoryQuestion("q2", "signs", "Question two?", listOf("Yes", "No"), 0),
        TheoryQuestion("q3", "safety", "Question three?", listOf("Left", "Right"), 1)
    )

    @Test fun sessionTracksAnswersAndScore() {
        val session = TheoryQuizSession.create(bank(), 3, Random(7))
        while (!session.isComplete) {
            val question = session.currentQuestion!!
            session.answer(question.correctOptionIndex)
        }
        assertEquals(3, session.result().correctAnswers)
        assertEquals(100, session.result().scorePercent)
        assertTrue(session.result().isComplete)
    }

    @Test fun incorrectAnswerIsCounted() {
        val session = TheoryQuizSession.create(bank(), 1, Random(2))
        val q = session.currentQuestion!!
        val wrong = (q.options.indices).first { it != q.correctOptionIndex }
        assertFalse(session.answer(wrong))
        assertEquals(0, session.correctCount)
        assertEquals(1, session.result().incorrectAnswers)
    }

    @Test fun questionCountIsCappedAtBankSize() {
        assertEquals(3, TheoryQuizSession.create(bank(), 20, Random(1)).questions.size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun duplicateIdsAreRejected() {
        val duplicate = bank() + bank().first()
        TheoryQuizSession.create(duplicate)
    }

    @Test(expected = IllegalStateException::class)
    fun completedSessionCannotAcceptAnotherAnswer() {
        val session = TheoryQuizSession.create(bank(), 1, Random(4))
        session.answer(session.currentQuestion!!.correctOptionIndex)
        session.answer(0)
    }
}
