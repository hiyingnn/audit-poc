package com.example.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
public abstract class References {
  List<Reference> references;

}
