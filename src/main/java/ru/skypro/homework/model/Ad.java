package ru.skypro.homework.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ads")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ad {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
    private int price;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
}
