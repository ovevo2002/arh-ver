package ru.arh.athletics.domain;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ZoneResolver {

    private final Integer rfthrTempo;

    private final Double rfthrPulse;

    private int age;

    public ZoneResolver(Integer rfthrTempo, Double rfthrPulse, int age) {
        this.rfthrTempo = rfthrTempo;
        this.rfthrPulse = rfthrPulse;
        this.age = age;
    }

    public Zone getForIntensityLevel(String level) {
        int lvl = this.levelStringToLevelOrder(level);
        int pulse = this.getPulseForLevel(lvl);
        Optional<Range<Double>> tempo = this.getTempoForLevel(lvl);
        Double tempoFrom = tempo.map(Range::getFrom).orElse(null);
        Double tempoTo = tempo.map(Range::getTo).orElse(null);
        return new Zone(level, pulse, tempoFrom, tempoTo, this.convertTempoToSpeed(tempoFrom),
                this.convertTempoToSpeed(tempoTo));
    }

    private int getPulseForLevel(int level) {
        Double percentage = percentageForPulse(level);
        if (this.rfthrPulse == null) {
            // Определяем через "ПУЛЬСОВЫЕ ЗОНЫ ВОЗРАСТ"

            Double defaultPulse = (220 - this.age) * 0.65 + 30;
            return (int)(defaultPulse * (percentage));
        }

        Double rtfhr = this.rfthrPulse * 0.95;
        return (int)(rtfhr * percentage);

    }

    private Double percentageForPulse(int level) {
        Double percentage = 1.0;
        switch (level) {
        case 1:
            percentage = 0.85;
            break;
        case 2:
            percentage = 0.89;
            break;
        case 3:
            percentage = 0.94;
            break;
        case 4:
            percentage = 1.0;
            break;
        case 5:
            percentage = 1.02;
            break;
        case 6:
            percentage = 1.06;
            break;
        }
        return percentage;
    }

    private Optional<Range<Double>> getTempoForLevel(int level) {
        if (this.rfthrTempo == null) {
            return Optional.empty();
        } else {
            // Определяем через "ТЕМПОВЫЕ ЗОНЫ"
            Double rfthr = this.rfthrTempo * 1.05;

            return Optional.of(new Range<>(percentageForTempo(level - 1) * rfthr, percentageForTempo(level) * rfthr));
        }
    }

    private Double percentageForTempo(int level) {
        Double percentage = 1.0;
        switch (level) {
        case 0:
            percentage = 0.0;
            break;
        case 1:
            percentage = 1.29;
            break;
        case 2:
            percentage = 1.13;
            break;
        case 3:
            percentage = 1.05;
            break;
        case 4:
            percentage = 1.0;
            break;
        case 5:
            percentage = 0.96;
            break;
        case 6:
            percentage = 0.9;
            break;
        }
        return percentage;
    }

    private Double convertTempoToSpeed(Double tempo) {
        if (tempo == 0) {
            return null;
        }

        return (60.0 * 60) / tempo;
    }

    private int levelStringToLevelOrder(String level) {
        switch (level) {
        case "1":
            return 1;
        case "2":
            return 2;
        case "3":
            return 3;
        case "4":
            return 4;
        case "5a":
            return 5;
        case "5b":
            return 6;
        case "5c":
            return 7;
        }

        throw new IllegalStateException("Uncknown zone " + level);
    }

    @Data
    @AllArgsConstructor
    private static class Range<T> {

        private T from;

        private T to;
    }
}
