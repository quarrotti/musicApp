package org.example.musicApp.api.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.entities.aboutUser.Role;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @NonNull
    String username;


    String description;

    @NonNull
    String email;

    @NonNull
    String password;

    List<AudioEntity> listOfAudios;


    Collection<Role> roles = new HashSet<>();


}
