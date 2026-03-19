package web.crawler.demo.util;

import java.util.List;

import web.crawler.demo.domain.Entry;

public class MockData {

        public static final Entry ENTRY_WITH_1_WORD = Entry.builder()
                        .number(2)
                        .title("OpenRocket")
                        .points(333)
                        .comments(67)
                        .build();

        public static final Entry ENTRY_WITH_5_WORDS = Entry.builder()
                        .number(11)
                        .title("This is - a self-explained example")
                        .points(3)
                        .comments(0)
                        .build();

        public static final Entry ENTRY_WITH_12_WORDS = Entry.builder()
                        .number(14)
                        .title("Show HN: Fatal Core Dump – A debugging murder mystery played with GDB ")
                        .points(18)
                        .comments(1)
                        .build();

        public static final Entry ENTRY_WITH_10_WORDS = Entry.builder()
                        .number(21)
                        .title("'The Secret Agent': Exploring a Vibrant, yet Violent Brazil (2025)")
                        .points(121)
                        .comments(58)
                        .build();

        public static final Entry ENTRY_WITH_4_WORDS = Entry.builder()
                        .number(1)
                        .title("A Decade of Slug")
                        .points(399)
                        .comments(34)
                        .build();

        public static final List<Entry> ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES = List.of(
                        ENTRY_WITH_1_WORD,
                        ENTRY_WITH_12_WORDS,
                        ENTRY_WITH_4_WORDS,
                        ENTRY_WITH_5_WORDS,
                        ENTRY_WITH_10_WORDS);

        public static final Entry ENTRY_WITH_4_WORDS_V2 = Entry.builder()
                        .number(15)
                        .title("Machine Payments Protocol (MPP)")
                        .points(120)
                        .comments(64)
                        .build();

        public static final Entry ENTRY_1 = Entry.builder()
                        .number(8)
                        .title("Review: Samsung Galaxy S26 Ultra")
                        .points(43)
                        .comments(32)
                        .build();

        public static final Entry ENTRY_2 = Entry.builder()
                        .number(2)
                        .title("Python 3.15's JIT is now back on track")
                        .points(258)
                        .comments(3944)
                        .build();

        public static final Entry ENTRY_3 = Entry.builder()
                        .number(3)
                        .title("Microsoft's 'unhackable' Xbox One has been hacked by 'Bliss' ")
                        .points(517)
                        .comments(202)
                        .build();

        public static final Entry ENTRY_4 = Entry.builder()
                        .number(4)
                        .title("Get Sh*t Done: A Meta-Prompting, Context Engineering and Spec-Driven Dev System")
                        .points(179)
                        .comments(108)
                        .build();

        public static final Entry ENTRY_5 = Entry.builder()
                        .number(5)
                        .title("Mistral AI Releases Forge")
                        .points(115)
                        .comments(7)
                        .build();

        public static final List<Entry> ENTRY_LIST_WITH_5_ITEMS = List.of(
                        ENTRY_1, ENTRY_2, ENTRY_3, ENTRY_4, ENTRY_5);

        public static final List<Entry> ENTRY_LIST_WITH_10_ITEMS = List.of(
                        ENTRY_1, ENTRY_2, ENTRY_3, ENTRY_4, ENTRY_5,
                        ENTRY_WITH_5_WORDS,
                        ENTRY_WITH_12_WORDS,
                        ENTRY_WITH_10_WORDS,
                        ENTRY_WITH_4_WORDS,
                        ENTRY_WITH_4_WORDS_V2);

        private MockData() {
                throw new IllegalArgumentException("Utility class");
        }
}
