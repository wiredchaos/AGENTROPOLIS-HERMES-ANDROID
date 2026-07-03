package io.agentropolis.hermex.data

import io.agentropolis.hermex.model.UiListItem
import org.junit.Assert.assertEquals
import org.junit.Test

class HermesRepositoryParsingTest {

    private val repo = HermesRepository(FakeStore())

    @Test
    fun parseListItems_supportsNestedDataArray() {
        val body = """
            {
              "data": [
                {"id": "s1", "name": "Session One", "status": "running"}
              ]
            }
        """.trimIndent()

        val items = repo.parseListItems(body)

        assertEquals(listOf(UiListItem(id = "s1", title = "Session One", subtitle = "running")), items)
    }

    @Test
    fun parseChatItems_supportsMessagesArray() {
        val body = """
            {
              "messages": [
                {"role": "user", "content": "hello"},
                {"author": "assistant", "text": "world"}
              ]
            }
        """.trimIndent()

        val items = repo.parseChatItems(body)

        assertEquals(2, items.size)
        assertEquals("user", items[0].role)
        assertEquals("hello", items[0].content)
        assertEquals("assistant", items[1].role)
        assertEquals("world", items[1].content)
    }

    private class FakeStore : SecureStoreStub()
}

open class SecureStoreStub : SecureStoreContract {
    override fun saveConfig(config: io.agentropolis.hermex.model.EndpointConfig) = Unit
    override fun loadConfig(): io.agentropolis.hermex.model.EndpointConfig = io.agentropolis.hermex.model.EndpointConfig()
}
