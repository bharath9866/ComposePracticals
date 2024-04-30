package com.example.adaptivestreamingplayer.ilts.report

import org.junit.Test

class ConvertFromJsonToKeyValueTest {

    @Test
    fun testingJsonToKeyMap(){
        val json = "{\n" +
                "  \"ILQ-1078110\": \"Incorrect\",\n" +
                "  \"ILQ-1078247\": \"Incorrect\",\n" +
                "  \"ILQ-1082640\": \"Incorrect\",\n" +
                "  \"ILQ-1082710\": \"Incorrect\",\n" +
                "  \"ILQ-2499\": \"Incorrect\",\n" +
                "  \"ILQ-2507\": \"Incorrect\",\n" +
                "  \"ILQ-2519\": \"Incorrect\",\n" +
                "  \"ILQ-361205\": \"Incorrect\",\n" +
                "  \"ILQ-363133\": \"Incorrect\",\n" +
                "  \"ILQ-406534\": \"Incorrect\",\n" +
                "  \"ILQ-5262\": \"Incorrect\",\n" +
                "  \"ILQ-738276\": \"Incorrect\",\n" +
                "  \"ILQ-746669\": \"Correct\",\n" +
                "  \"ILQ-8313\": \"Incorrect\"\n" +
                "}"

        val result = ConvertFromJsonToKeyValue.stringToMapOfMap(json)?.map { it.value } ?: listOf()
        println(result)

    }

}