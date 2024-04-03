package com.example.adaptivestreamingplayer.testCases

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class FibonacciUnitTest {
    @Test
    fun testFibonacciZero() {
        val result = Fibonacci.fib(0)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun testFibonacciOne() {
        val result = Fibonacci.fib(1)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun testFibonacciSmallNumbers() {
        val result2 = Fibonacci.fib(2)
        assertThat(result2).isEqualTo(1)

        val result3 = Fibonacci.fib(3)
        assertThat(result3).isEqualTo(2)

        val result4 = Fibonacci.fib(4)
        assertThat(result4).isEqualTo(3)

        val result5 = Fibonacci.fib(5)
        assertThat(result5).isEqualTo(5)
    }

    @Test
    fun testFibonacciLargeNumber() {
        val result = Fibonacci.fib(20)
        assertThat(result).isEqualTo(6765)
    }
}