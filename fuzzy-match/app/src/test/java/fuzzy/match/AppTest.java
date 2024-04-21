package fuzzy.match;

import java.util.List;
import org.junit.jupiter.api.Test;
import me.xdrop.fuzzywuzzy.FuzzySearch;

class AppTest {
    @Test
    void test() {

        List<String> names = List.of(
                "cat", "catalog", "apple", "people", "Google", "Google Map", "Gmail",
                "Google Meets");

        System.out.println(FuzzySearch.extractOne("Google", names));
        // -> (string: Google, score: 100, index: 4)

        // 大文字小文字は同一視されてる
        System.out.println(FuzzySearch.extractOne("google", names));
        // -> (string: Google, score: 100, index: 4)

        // 全角半角はダメ
        System.out.println(FuzzySearch.extractOne("Ｇｏｏｇｌｅ", names));
        // -> (string: Google Map, score: 9, index: 5)

        // 数文字違いは似たものとして判断
        System.out.println(FuzzySearch.extractOne("gogle", names));
        // -> (string: Google, score: 91, index: 4)
        System.out.println(FuzzySearch.extractOne("gooogle", names));
        // -> (string: Google, score: 92, index: 4)
        System.out.println(FuzzySearch.extractOne("googke", names));
        // -> (string: Google, score: 83, index: 4)
        System.out.println(FuzzySearch.extractOne("foofle", names));
        // -> (string: Google, score: 67, index: 4)

        System.out.println(FuzzySearch.extractSorted("google", names));
        // (string: Google, score: 100, index: 4),
        // (string: Google Map, score: 90, index: 5),
        // (string: Google Meets, score: 90, index: 7),
        // (string: people, score: 50, index: 3),
        // (string: apple, score: 36, index: 2),
        // (string: Gmail, score: 36, index: 6),
        // (string: catalog, score: 31, index: 1),
        // (string: cat, score: 0, index: 0)
    }
}
