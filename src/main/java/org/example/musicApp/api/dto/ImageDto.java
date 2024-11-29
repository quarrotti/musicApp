package org.example.musicApp.api.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.musicApp.store.entities.aboutUser.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageDto {

    @NonNull
    String fileName;

    String fileType;

    @NonNull
    byte[] data;


    UserEntity user;
}
