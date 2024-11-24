package ru.nsu.bondar;

/**
 * Class for finding substrings in large text files using Z-function.
 */
public class SubstringFinder {

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