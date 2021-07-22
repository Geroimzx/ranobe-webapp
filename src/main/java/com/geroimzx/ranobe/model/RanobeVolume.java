package com.geroimzx.ranobe.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "RANOBE_VOLUME_DB")
public class RanobeVolume {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ranobePage_id")
    private RanobePage ranobePage;

    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    @Min(0)
    @Column(unique = true)
    private int volumeNum;

    @Column(columnDefinition="TEXT", length=524288)
    @Basic(fetch = FetchType.LAZY)
    @NotNull
    private String text;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ranobeVolume")
    @Basic(fetch = FetchType.LAZY)
    private List<Comment> comments;

    public RanobeVolume() {

    }

    public RanobeVolume(RanobePage ranobePage, String name, int volumeNum, String text, List<Comment> comments) {
        this.ranobePage = ranobePage;
        this.name = name;
        this.volumeNum = volumeNum;
        this.text = text;
        this.comments = comments;
    }

    public RanobeVolume(String name, int volumeNum, String text) {
        this.name = name;
        this.volumeNum = volumeNum;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RanobePage getRanobePage() {
        return ranobePage;
    }

    public void setRanobePage(RanobePage ranobePage) {
        this.ranobePage = ranobePage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolumeNum() {
        return volumeNum;
    }

    public void setVolumeNum(int volumeNum) {
        this.volumeNum = volumeNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
