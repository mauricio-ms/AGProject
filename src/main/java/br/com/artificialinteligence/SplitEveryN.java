package br.com.artificialinteligence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SplitEveryN {

    private final String regex;

    private SplitEveryN(final Integer n) {
        regex = String.format(".{%s}", n);
    }

    public static SplitEveryN of(final Integer n) {
        return new SplitEveryN(n);
    }

    public List<String> split(final String str) {
        final Matcher matcher = Pattern.compile(regex)
                .matcher(str);

        final List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }

        return list;
    }

    @Override
    public String toString() {
        return "SplitEveryN{" +
                "regex='" + regex + '\'' +
                '}';
    }
}