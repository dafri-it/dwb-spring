package de.dafri.dwb.data;

import java.util.Date;

public record EventModel(Long id, String nr, Date begin, Date end, String place) {
}
