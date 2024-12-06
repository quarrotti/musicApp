package org.example.musicApp.store.entities.aboutAudio;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.musicApp.store.entities.aboutUser.UserEntity;

import java.util.HashSet;
import java.util.Set;

@Table(name = "audio")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AudioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String fileName;
    String fileType;
    @Lob
    byte[] data;

    @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "audio_genre", joinColumns = @JoinColumn(name = "audio_id"))
    @Enumerated(EnumType.STRING)
    Set<Genre> genres = new HashSet<>();

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "playlist_id")
    PlaylistEntity playlist;

}
