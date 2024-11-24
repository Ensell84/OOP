package ru.nsu.bondar;

import java.util.List;

/**
 * Класс для нахождения всех вхождений подстроки в файл.
 * Используется алгоритм на основе Z-Функции.
 */
public class SubstringFinder {

    /**
     * Обрабатывает буфер текста используя Z-Функцию для нахождения вхождений паттерна.
     *
     * @param text строка для обработки
     * @param pattern паттерн, используемый для поиска вхождений
     * @param offset текущая позиция в файле
     * @param occurrences список, в который сохраняются найденные вхождения
     */
    private static void processBuffer(String text, String pattern, int offset,
                                      List<Integer> occurrences) {
        String concatenated = pattern + "$" + text;
        int[] z = calculateZFunction(concatenated);
        int pattern_length = pattern.length();

        for (int i = 0; i < z.length; i++) {
            if (z[i] == pattern_length) {
                int position = i - pattern_length - 1;
                occurrences.add(position + offset);
            }
        }
    }

    /**
     * Вычисляет Z-Функцию для строки.
     *
     * @param text input строка
     * @return массив значений Z-Функций
     */
    private static int[] calculateZFunction(String text) {
        int n = text.length();
        int[] z = new int[n];
        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {
            // Если внутри Z-Блока, то или:
            // z[i] = z[i-left] - уже посчитана
            // z[i] = (right - i + 1) + досчитать в while(...)
            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }

            // i > right --> считаем z-фию
            // i внутри Z-Блока и z[i] = z[i-left] --> цикл не отработает
            // i внутри Z-Блока и z[i] = right - i + 1 --> досчитаем значение z[i]
            while (i + z[i] < n && text.charAt(z[i]) == text.charAt(i + z[i])) {
                z[i]++;
            }

            // Вне Z-Блока --> посчитали z[i] --> обновляем left и right
            // i внутри Z-Блока и z[i] = (right - i + 1) + x, x > 0 --> обновляем left и right
            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }
}