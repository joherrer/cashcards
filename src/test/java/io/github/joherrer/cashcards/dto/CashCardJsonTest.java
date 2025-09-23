package io.github.joherrer.cashcards.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    @Autowired
    private JacksonTester<TestCashCard> testJson;

    @Autowired
    private JacksonTester<TestCashCard[]> testJsonList;

    private CashCard[] cashCards;
    private TestCashCard[] testCashCards;

    @BeforeEach
    void setUp() {
        cashCards = new CashCard[] {
                new CashCard(99L, new BigDecimal("123.45"), "sarah1"),
                new CashCard(100L, new BigDecimal("1.00"), "sarah1"),
                new CashCard(101L, new BigDecimal("150.00"), "sarah1")
        };

        testCashCards = new TestCashCard[] {
                new TestCashCard(99L, new BigDecimal("123.45"), "sarah1"),
                new TestCashCard(100L, new BigDecimal("1.00"), "sarah1"),
                new TestCashCard(101L, new BigDecimal("150.00"), "sarah1")
        };
    }

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = cashCards[0];

        assertThat(json.write(cashCard)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": 99,
                    "amount": 123.45,
                    "owner": "sarah1"
                }
                """;

        assertThat(testJson.parse(expected))
                .isEqualTo(testCashCards[0]);

        assertThat(testJson.parseObject(expected).id()).isEqualTo(99L);
        assertThat(testJson.parseObject(expected).amount()).isEqualByComparingTo(new BigDecimal("123.45"));
        assertThat(testJson.parseObject(expected).owner()).isEqualTo("sarah1");
    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json");
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expected = """
                [
                     {"id": 99, "amount": 123.45 , "owner": "sarah1"},
                     {"id": 100, "amount": 1.00 , "owner": "sarah1"},
                     {"id": 101, "amount": 150.00, "owner": "sarah1"}
                ]
                """;

        assertThat(testJsonList.parse(expected)).isEqualTo(testCashCards);
    }
}
