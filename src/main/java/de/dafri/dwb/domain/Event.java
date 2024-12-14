package de.dafri.dwb.domain;

import java.util.Date;

public record Event(String nr, Date begin, Date end, String place) {
}
