package com.ke.hs.module.entity

import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MechanicsAdapterTest {

    private val moshi = Moshi.Builder()
        .add(MechanicsAdapter())
        .build()

    @Test
    fun knownMechanicStillParses() {
        val adapter = MechanicsAdapter()

        assertEquals(Mechanics.BattleCry, adapter.fromJson("BATTLECRY"))
    }

    @Test
    fun unknownMechanicFallsBackToUnknown() {
        val adapter = MechanicsAdapter()

        assertEquals(Mechanics.Unknown, adapter.fromJson("FUTURE_UNKNOWN_MECHANIC"))
    }

    @Test
    fun cardWithUnknownMechanicParses() {
        val adapter = moshi.adapter(Card::class.java)
        val json = """
            {
              "name": "Future Test Card",
              "id": "TEST_001",
              "dbfId": 1,
              "mechanics": ["FUTURE_UNKNOWN_MECHANIC"]
            }
        """.trimIndent()

        val card = adapter.fromJson(json)

        assertNotNull(card)
        assertEquals(listOf(Mechanics.Unknown), card!!.mechanics)
    }
}
