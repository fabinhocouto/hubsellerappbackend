package com.br.hubsellerappbackend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemUtil {
	public static String extrairItemId(String link) {
        try {

            // 1️⃣ Primeiro tenta pegar do wid=
            Pattern widPattern = Pattern.compile("wid=(MLB\\d+)");
            Matcher widMatcher = widPattern.matcher(link);

            if (widMatcher.find()) {
                return widMatcher.group(1);
            }

            // 2️⃣ Padrão: MLB-123456
            Pattern dashPattern = Pattern.compile("MLB-(\\d+)");
            Matcher dashMatcher = dashPattern.matcher(link);

            if (dashMatcher.find()) {
                return "MLB" + dashMatcher.group(1);
            }

            // 3️⃣ Padrão antigo
            Pattern pattern = Pattern.compile("(?<!/p/)MLB(\\d+)");
            Matcher matcher = pattern.matcher(link);

            if (matcher.find()) {
                return "MLB" + matcher.group(1);
            }

            return "MLB12345678";

        } catch (Exception e) {
            return "MLB12345678";
        }
    }
}
