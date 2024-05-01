package com.example.storygen
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class StoryGeneratorTest {

    @Test
    fun generateStory_validPrompt_returnsGeneratedStory() = runBlocking {
        val storyGenerator = StoryGenerator()
        val prompt = "Once upon a time"
        val generatedStory = storyGenerator.generateStory(prompt)

        assertTrue(generatedStory.isNotEmpty())
        assertTrue(generatedStory.contains(prompt))
    }


}