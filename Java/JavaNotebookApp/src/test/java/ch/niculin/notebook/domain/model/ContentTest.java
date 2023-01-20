package ch.niculin.notebook.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentTest {

    @Test
    void testValidContent() {
        Content content = new Content("asdfasfasfsafmancajbfoaifascaöf acaoufa cyxcvj<aspof");
        String result = content.content();
        assertEquals("asdfasfasfsafmancajbfoaifascaöf acaoufa cyxcvj<aspof", result);
    }

    @Test
    void testInvalidContent() {
        Content content = new Content("asdfasfasfsafmancajbfoaifascaöf acaoufa cyxcvj<aspof");
        String result = content.content();
        assertNotEquals("asdfasfasfsafmancajbfoaifas", result);
    }
}