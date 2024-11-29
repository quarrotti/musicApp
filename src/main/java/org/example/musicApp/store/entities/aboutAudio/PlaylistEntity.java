package org.example.musicApp.store.entities.aboutAudio;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.musicApp.store.entities.aboutUser.UserEntity;

import java.util.HashSet;
import java.util.List;

@Table(name = "playlist")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    List<AudioEntity> listOfAudios;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER )
    @JoinColumn
    UserEntity user;

    @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "playlist_genre", joinColumns = @JoinColumn(name = "playlist_id"))
    @Enumerated(EnumType.STRING)
    HashSet<Genre> genres = new HashSet<>();
}
