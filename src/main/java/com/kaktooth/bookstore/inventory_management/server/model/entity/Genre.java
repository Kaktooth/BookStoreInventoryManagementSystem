package com.kaktooth.bookstore.inventory_management.server.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Genre {
  UNIDENTIFIED("Unidentified"),
  SPECULATIVE("Speculative"),
  REALIST("Realist"),
  ADVENTURE("Adventure"),
  COMING_OF_AGE("Coming-of-age"),
  CRIME("Crime"),
  FANTASY("Fantasy"),
  MILITARY("Military"),
  PARANORMAL("Paranormal"),
  ROMANCE("Romance"),
  SCIENCE_FICTION("Science fiction"),
  SUPERNATURAL("Supernatural"),
  WESTERN("Western"),
  HORROR("Horror"),
  HISTORICAL("Historical"),
  ENCYCLOPEDIC("Encyclopedic"),
  ACADEMIC("Academic"),
  HISTORY("History"),
  PHILOSOPHY("Philosophy"),
  COMEDY("Comedy"),
  ESSAY("Essay"),
  JOURNALISM("Journalism"),
  NATURE("Nature"),
  REFERENCE("Reference");

  private final String genreName;
}
