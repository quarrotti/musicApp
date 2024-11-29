package org.example.musicApp.api.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.musicApp.store.entities.aboutAudio.Genre;
import org.example.musicApp.store.entities.aboutUser.UserEntity;

import java.util.HashSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AudioDto {

    @NonNull
    String name;

    @NonNull
    byte[] data;

    @NonNull
    HashSet<Genre> genres = new HashSet<>();


    UserEntity user;
}
