package de.dafri.dwb.data.model;

public record CategoryRelationModel(Long parentId, Long childId, int sort) {
}
