package com.hw.total_beg_course.Domain;

public class Zone {
    public String name;

    public float expected_pulse;

    public int expected_tempo_from;
    public int expected_tempo_to;

    public float expected_speed_from;
    public float expected_speed_to;

    public Zone(String name, float expected_pulse, int expected_tempo_from, int expected_tempo_to, float expected_speed_from, float expected_speed_to) {
        this.name = name;
        this.expected_pulse = expected_pulse;
        this.expected_tempo_from = expected_tempo_from;
        this.expected_tempo_to = expected_tempo_to;
        this.expected_speed_from = expected_speed_from;
        this.expected_speed_to = expected_speed_to;
    }
}
