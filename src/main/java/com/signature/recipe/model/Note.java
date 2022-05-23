package com.signature.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.signature.recipe.data.NoteDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

  private String id;
  private String description;

  public Note(String description) {
    this.id = ObjectId.get().toString();
    this.description = description;
  }

  @JsonIgnore
  public NoteDTO getDTO() {
    return new NoteDTO(id, description);
  }
}