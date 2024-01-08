package com.example.adaptivestreamingplayer.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun readJSONFromAssets(context: Context, path: String): String {
    return try {
        val file = context.assets.open(path)
        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        stringBuilder.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

//fun Context.readJSONFromAssets(fileName: String): String {
//    val inputStream = assets.open(fileName)
//    val size = inputStream.available()
//    val buffer = ByteArray(size)
//    inputStream.read(buffer)
//    inputStream.close()
//    return String(buffer, Charsets.UTF_8)
//}

val bookmarkJson = """
    {
      "status": true,
      "message": "Success",
      "data": {
        "parent": {
          "examId": 3,
          "subjectId": 1,
          "chapterId": 206,
          "topicIds": [
            1982,
            1984,
            1985,
            1987,
            1989,
            1980,
            1983,
            1986,
            1988,
            1981,
            13465
          ],
          "subtenantId": 45,
          "gradeId": 11,
          "flashcardCount": 17
        },
        "flashcards": [
          {
            "flashcardId": 5173,
            "title": "<p>Which scientist, for the first time demonstrated that plants could be grown to maturity in a defined nutrient solution?</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p>Which scientist, for the first time demonstrated that plants could be grown to maturity in a defined nutrient solution?</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>Julius von Sachs</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5174,
            "title": "<p><span style=\"font-size:17px;\">Which essential elements are referred to as structural elements of cells?</span></p><p>&nbsp;</p><p>&nbsp;</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p><span style=\"font-size:17px;\">Which essential elements are referred to as structural elements of cells?</span></p><p>&nbsp;</p><p>&nbsp;</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p><span style=\"font-size:17px;\">Carbon, hydrogen, oxygen and nitrogen</span></p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5175,
            "title": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(32,33,36);font-family:&quot;Montserrat variant0&quot;, Tofu;font-size:17px;\">Which essential nutrient element required by plants in the greatest amount?</span></p><p>&nbsp;</p><p>&nbsp;</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(32,33,36);font-family:&quot;Montserrat variant0&quot;, Tofu;font-size:17px;\">Which essential nutrient element required by plants in the greatest amount?</span></p><p>&nbsp;</p><p>&nbsp;</p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1680274519088?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=9a2ab9e502392599a6f8126fae78ee3d88f8018f9ae11c0c0e2214ca8ebcd2e7",
              "isImageRequired": true,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(32,33,36);font-family:&quot;Montserrat variant0&quot;, Tofu;font-size:17px;\">Nitrogen</span></p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1680274519173?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=0a1af45a3d41f8378207b5ea8efa7f00205a4e1e5b0406b6693bad11be88410f",
              "isImageRequired": true,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5176,
            "title": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(32,33,36);font-family:&quot;Montserrat variant0&quot;, Tofu;font-size:17px;\">Which macronurient is particularly useful in the synthesis of middle lamella and formation of mitotic spindle?</span></p><p>&nbsp;</p><p>&nbsp;</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(32,33,36);font-family:&quot;Montserrat variant0&quot;, Tofu;font-size:17px;\">Which macronurient is particularly useful in the synthesis of middle lamella and formation of mitotic spindle?</span></p><p>&nbsp;</p><p>&nbsp;</p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1680275020495?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=bea1b008e348b46c2f8750be26d6cf1b11b8147f682bf500b07a440d22fd8d53",
              "isImageRequired": true,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(32,33,36);font-family:&quot;Montserrat variant0&quot;, Tofu;font-size:17px;\">Calcium</span></p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1680275020595?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=edf647a7c493278d124393d2a303d3d8dc379d85505062bf9aaa21b32d05d866",
              "isImageRequired": true,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5177,
            "title": "<p><span style=\"font-family:Default;font-size:17px;\">Which macronutrient is a constituent of cell membranes, certain proteins, all nucleic acids and nucleotides, and is required for all phosphorylation reactions?</span></p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p><span style=\"font-family:Default;font-size:17px;\">Which macronutrient is a constituent of cell membranes, certain proteins, all nucleic acids and nucleotides, and is required for all phosphorylation reactions?</span></p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>Phosphorous</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5186,
            "title": "<p><span style=\"font-family:Default;font-size:17px;\">Which macronutrient is a constituent of cell membranes, certain proteins, all nucleic acids and nucleotides, and is required for all phosphorylation reactions?</span></p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p><span style=\"font-family:Default;font-size:17px;\">Which macronutrient is a constituent of cell membranes, certain proteins, all nucleic acids and nucleotides, and is required for all phosphorylation reactions?</span></p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1684850532972?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=1d66a0fd9a72d3a5a37e2bce0d410307fdb98fa5b28d624140ccf8372bdc8aa9",
              "isImageRequired": true,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>Phosphorous</p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1684850533122?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=973f47e57a608b7e3dd8c71611a1050749c053f79a4efe4f911b5869f4fb2306",
              "isImageRequired": true,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5189,
            "title": "<p>Testing for memory flash cards</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p>Testing for memory flash cards</p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1686916239199?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=570163db44ab4218656d5a30f4a9caaf348295fe780e8bcbe35b8fa75826cae1",
              "isImageRequired": true,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>Answer for the memory flash card</p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1686916239393?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=7d9195a12595be310aa0694417804991caf7881d30facf899eb2aee90268b61f",
              "isImageRequired": true,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5194,
            "title": "<p><br><span style=\"background-color:rgb(255,255,255);color:rgb(51,51,51);font-family:Roboto, &quot;Helvetica Neue&quot;, sans-serif;font-size:14px;\">In the World summit, ____ &nbsp;countries pledged their commitment to achieve a significant reduction in the current rate of biodiversity loss at global, regional and local levels by 2010.</span></p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p><br><span style=\"background-color:rgb(255,255,255);color:rgb(51,51,51);font-family:Roboto, &quot;Helvetica Neue&quot;, sans-serif;font-size:14px;\">In the World summit, ____ &nbsp;countries pledged their commitment to achieve a significant reduction in the current rate of biodiversity loss at global, regional and local levels by 2010.</span></p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>190</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5195,
            "title": "<p>Define velocity.</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p>Define velocity.</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>The rate of change in displacement of a body is known as velocity. Velocity is a vector quantity.&nbsp;</p><p><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mover><mi mathvariant=\"normal\">V</mi><mo>→</mo></mover><mo>=</mo><mfrac><mover><mi>dS</mi><mo>→</mo></mover><mi>dt</mi></mfrac></math></p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5199,
            "title": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(51,51,51);font-family:Roboto, &quot;Helvetica Neue&quot;, sans-serif;font-size:14px;\">What are the three characteristics of standard units?</span></p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(51,51,51);font-family:Roboto, &quot;Helvetica Neue&quot;, sans-serif;font-size:14px;\">What are the three characteristics of standard units?</span></p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>The three characteristics of standard units are:</p><p>1) &nbsp; Invariability : The standard unit must be invariable (The distance between the middle finger and the elbow of a person cannot be standard unit of length because it varies from person to person).</p><p>2) Availability : The standard unit should be made easily available for comparing with other quantities.</p><p>3) Indestructibility : The standard unit cannot be destroyed. Even if it is destroyed it can be reproduced easily.</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5200,
            "title": "<p>What is absolute error (or) deviation?</p><p><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>1</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>1</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math></p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p>What is absolute error (or) deviation?</p><p><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>1</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>1</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math></p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>Absolute error is the magnitude of the difference between the arithmetical mean value and an individual measured value.</p><p><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>1</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>1</mn></msub></mrow></mfenced></math>,<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">d</mi><mn>2</mn></msub><mo>=</mo><mfenced close=\"|\" open=\"|\"><mrow><menclose notation=\"top\"><mi mathvariant=\"normal\">x</mi></menclose><mo>-</mo><msub><mi mathvariant=\"normal\">x</mi><mn>2</mn></msub></mrow></mfenced></math>&nbsp;</p><p>&nbsp;</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5201,
            "title": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(51,51,51);font-family:Roboto, &quot;Helvetica Neue&quot;, sans-serif;font-size:14px;\">What is the value of mass of an electron?</span></p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(51,51,51);font-family:Roboto, &quot;Helvetica Neue&quot;, sans-serif;font-size:14px;\">What is the value of mass of an electron?</span></p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>The mass of the The mass of the electron (m<sub>e</sub>) was determined by combining the experimental results with Thomson’s value of&nbsp;<math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><mfrac><mi mathvariant=\"normal\">e</mi><msub><mi mathvariant=\"normal\">m</mi><mi mathvariant=\"normal\">e</mi></msub></mfrac></math>ratio.</p><p><math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">m</mi><mi mathvariant=\"normal\">e</mi></msub><mo>=</mo><mfrac><mi mathvariant=\"normal\">e</mi><mfrac bevelled=\"true\"><mi mathvariant=\"normal\">e</mi><msub><mi mathvariant=\"normal\">m</mi><mi mathvariant=\"normal\">e</mi></msub></mfrac></mfrac><mo>=</mo><mfrac><mrow><mn>1</mn><mo>.</mo><mn>6022</mn><mo>×</mo><msup><mn>10</mn><mrow><mo>-</mo><mn>19</mn></mrow></msup><mi mathvariant=\"normal\">C</mi></mrow><mrow><mn>1</mn><mo>.</mo><mn>758820</mn><mo>×</mo><msup><mn>10</mn><mn>11</mn></msup><mi mathvariant=\"normal\">C</mi><mo>&nbsp;</mo><msup><mi>kg</mi><mrow><mo>-</mo><mn>1</mn></mrow></msup></mrow></mfrac></math></p><p>&nbsp; &nbsp; &nbsp; &nbsp;<math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><mo>=</mo><mn>9</mn><mo>.</mo><mn>1094</mn><mo>×</mo><msup><mn>10</mn><mrow><mo>-</mo><mn>31</mn></mrow></msup><mi>kg</mi></math>electron (m<sub>e</sub>) was determined by combining the experimental results with Thomson’s value of&nbsp;<math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><mfrac><mi mathvariant=\"normal\">e</mi><msub><mi mathvariant=\"normal\">m</mi><mi mathvariant=\"normal\">e</mi></msub></mfrac></math>ratio.</p><p><math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">m</mi><mi mathvariant=\"normal\">e</mi></msub><mo>=</mo><mfrac><mi mathvariant=\"normal\">e</mi><mfrac bevelled=\"true\"><mi mathvariant=\"normal\">e</mi><msub><mi mathvariant=\"normal\">m</mi><mi mathvariant=\"normal\">e</mi></msub></mfrac></mfrac><mo>=</mo><mfrac><mrow><mn>1</mn><mo>.</mo><mn>6022</mn><mo>×</mo><msup><mn>10</mn><mrow><mo>-</mo><mn>19</mn></mrow></msup><mi mathvariant=\"normal\">C</mi></mrow><mrow><mn>1</mn><mo>.</mo><mn>758820</mn><mo>×</mo><msup><mn>10</mn><mn>11</mn></msup><mi mathvariant=\"normal\">C</mi><mo>&nbsp;</mo><msup><mi>kg</mi><mrow><mo>-</mo><mn>1</mn></mrow></msup></mrow></mfrac></math></p><p>&nbsp; &nbsp; &nbsp; &nbsp;<math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><mo>=</mo><mn>9</mn><mo>.</mo><mn>1094</mn><mo>×</mo><msup><mn>10</mn><mrow><mo>-</mo><mn>31</mn></mrow></msup><mi>kg</mi></math></p><p>&nbsp;</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5202,
            "title": "<p>What is the relationship between Frequency of the Incident Photon and the Kinetic Energy of the Emitted Photoelectron?</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p>What is the relationship between Frequency of the Incident Photon and the Kinetic Energy of the Emitted Photoelectron?</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p><math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">E</mi><mi>photon</mi></msub><mo>=</mo><mi mathvariant=\"normal\">Φ</mi><mo>+</mo><msub><mi mathvariant=\"normal\">E</mi><mi>electron</mi></msub></math></p><p><math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><mi>hϑ</mi><mo>=</mo><msub><mi>hϑ</mi><mi>th</mi></msub><mo>+</mo><mstyle displaystyle=\"false\"><mfrac><mn>1</mn><mn>2</mn></mfrac></mstyle><msub><mi mathvariant=\"normal\">m</mi><mi mathvariant=\"normal\">e</mi></msub><msup><mi mathvariant=\"normal\">v</mi><mn>2</mn></msup></math></p><p>E<sub>photon</sub>=incident photon's energy</p><p><math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><mi mathvariant=\"normal\">Φ</mi></math>=metal surface's threshold energy</p><p>E<sub>electron</sub>=photoelectron's kinetic energy<math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><msub><mi mathvariant=\"normal\">E</mi><mi>photon</mi></msub><mo>=</mo><mi mathvariant=\"normal\">Φ</mi><mo>+</mo><msub><mi mathvariant=\"normal\">E</mi><mi>electron</mi></msub></math></p><p><math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><mi>hϑ</mi><mo>=</mo><msub><mi>hϑ</mi><mi>th</mi></msub><mo>+</mo><mstyle displaystyle=\"false\"><mfrac><mn>1</mn><mn>2</mn></mfrac></mstyle><msub><mi mathvariant=\"normal\">m</mi><mi mathvariant=\"normal\">e</mi></msub><msup><mi mathvariant=\"normal\">v</mi><mn>2</mn></msup></math></p><p>E<sub>photon</sub>=incident photon's energy</p><p><math class=\"wrs_chemistry\" xmlns=\"http://www.w3.org/1998/Math/MathML\"><mi mathvariant=\"normal\">Φ</mi></math>=metal surface's threshold energy</p><p>E<sub>electron</sub>=photoelectron's kinetic energy</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5203,
            "title": "<p>What is the definition of Range of a function ?</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p>What is the definition of Range of a function ?</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p><math xmlns=\"http://www.w3.org/1998/Math/MathML\"><mtext>&nbsp;If&nbsp;</mtext><mi>f</mi><mo>:</mo><mi>A</mi><mo stretchy=\"false\">→</mo><mi>B</mi><mtext>&nbsp;is a function, then&nbsp;</mtext><mi>f</mi><mo>(</mo><mi>A</mi><mo>)</mo><mtext>, the set of all&nbsp;</mtext><mi>f</mi><mtext>-images of elements in&nbsp;</mtext><mi>A</mi><mtext>, is called the range of&nbsp;</mtext><mi>f</mi></math></p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5226,
            "title": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(30,42,54);font-family:sans-serif;font-size:14px;\">What is the slope of the graph if current is plotted along y axis and voltage is plotted along x axis ?</span></p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(30,42,54);font-family:sans-serif;font-size:14px;\">What is the slope of the graph if current is plotted along y axis and voltage is plotted along x axis ?</span></p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p><span style=\"background-color:rgb(255,255,255);color:rgb(30,42,54);font-family:Roboto, &quot;Helvetica Neue&quot;, sans-serif;font-size:14px;\">&nbsp;Slope of the I-V graph is conductance. &nbsp;slope I/V=1/R &nbsp;= conductance.</span></p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1701767614812?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=35aa120211267d5effd7599a85286b49ce23c8bb21a6bf5e8273e607cc95d052",
              "isImageRequired": true,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5228,
            "title": "<p>Testing for the memory flash card</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p>Testing for the memory flash card</p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1702296142111?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=3497a8b86cb660573ed7e2b82cf2b29f8b7e245cb5f53a26d5658b753e99f4fc",
              "isImageRequired": true,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>Testing for the memory flash card</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            }
          },
          {
            "flashcardId": 5229,
            "title": "<p>Memory flash card</p>",
            "description": null,
            "summary": null,
            "titleMedia": null,
            "typeId": 4,
            "type": "Memory Flashcard",
            "statusId": null,
            "status": null,
            "front": {
              "text": "<p>Memory flash card</p>",
              "imageURL": "",
              "isImageRequired": false,
              "orientation": "Landscape"
            },
            "back": {
              "text": "<p>Memory flash card</p>",
              "imageURL": "https://il-cms-assets.s3.ap-south-1.amazonaws.com/media/1702296633584?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231222T082306Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIA2R2EX7PZB3M2KJNB%2F20231222%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=2329bcfb6e6554481a29bd61f221b32afc009b22385b6897e08c57ab15f9b51a",
              "isImageRequired": true,
              "orientation": "Landscape"
            }
          }
        ]
      }
    }
""".trimIndent()