package com.geroimzx.ranobe.model;

import javax.persistence.*;

@Entity
@Table(name = "COMMENTS_DB")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ranobeVolume_id")
    private RanobeVolume ranobeVolume;
}
