package com.app.githubjavapop

import com.app.githubjavapop.Requests.TestRequests
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkRepoRequest(){
        val code = TestRequests().checkRepoRequest()
        assertEquals(200, code)
    }

    @Test
    fun checkPullRequest(){
        val code = TestRequests().checkPullRequest()
        assertEquals(200, code)
    }

    @Test
    fun checkProfileRequest(){
        val code = TestRequests().checkProfileRequest()
        assertEquals(200, code)
    }

    
}

