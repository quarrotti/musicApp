package org.example.musicApp.store.entities.aboutAudio;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
    @Column(columnDefinition = "TIMESTAMP")
    LocalDateTime createdAt;
    @Lob
    byte[] data;

    @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "audio_genre", joinColumns = @JoinColumn(name = "audio_id"))
    @Enumerated(EnumType.STRING)
    Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_audio",
            joinColumns = @JoinColumn(name = "audio_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<UserEntity> users;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity creator;

    @PrePersist
    public void init(){
       this.createdAt = LocalDateTime.now();
    }

}
