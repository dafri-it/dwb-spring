package de.dafri.dwb.data.model;

import java.util.Date;

public record EventModel(Long id, String nr, Date begin, Date end, String place) {
}
