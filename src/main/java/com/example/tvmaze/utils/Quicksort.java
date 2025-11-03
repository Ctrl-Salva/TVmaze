package com.example.tvmaze.utils;

import java.util.List;

import com.example.tvmaze.models.Serie;

public class Quicksort {

    public static void quickSort(List<Serie> list, int low, int high) {
        if (low < high) {
            int p = partition(list, low, high);
            quickSort(list, low, p - 1);
            quickSort(list, p + 1, high);
        }
    }

    private static int partition(List<Serie> list, int low, int high) {
        Serie pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compareSeries(list.get(j), pivot) > 0) { // decrescente por nota
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    private static void swap(List<Serie> list, int i, int j) {
        Serie temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // 🧠 Critério de comparação
    private static int compareSeries(Serie a, Serie b) {
        int cmp = Double.compare(a.getNota() == null ? 0.0 : a.getNota(),
                                 b.getNota() == null ? 0.0 : b.getNota());
        if (cmp != 0) return cmp; // compara notas (decrescente)

        cmp = a.getNome().compareToIgnoreCase(b.getNome());
        if (cmp != 0) return -cmp; // nomes (crescente)

        return Integer.compare(a.getSerieId(), b.getSerieId());
    }
}
