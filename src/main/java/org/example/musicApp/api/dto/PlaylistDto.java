package org.example.musicApp.api.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.entities.aboutAudio.Genre;
import org.example.musicApp.store.entities.aboutUser.UserEntity;

import java.util.HashSet;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistDto {

    @NonNull
    String name;

    List<AudioEntity> listOfAudios;

    UserEntity user;

    HashSet<Genre> genres = new HashSet<>();
}
