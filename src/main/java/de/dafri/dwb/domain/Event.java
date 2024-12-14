package de.dafri.dwb.domain;

import java.time.LocalDateTime;

public record Event(String nr, LocalDateTime begin, LocalDateTime end, String place) {
}
