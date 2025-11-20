package com.banking.utiltest;

import com.util.IdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IdGeneratorTest {

    @Test
    public void testGenerateAccountNumber() {
        String acc = IdGenerator.generateAccountNumber("John Doe");
        assertNotNull(acc);
        assertEquals(7, acc.length());
    }

    @Test
    public void testGenerateTransactionId() {
        String id = IdGenerator.generateTransactionId();
        assertTrue(id.startsWith("TXN-"));
    }
}
