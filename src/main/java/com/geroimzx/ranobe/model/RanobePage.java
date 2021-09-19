package com.geroimzx.ranobe.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="RANOBE_PAGE_DB")
public class RanobePage {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    //Title of ranobe
    @NotNull
    @Size(min = 4)
    @Column(unique = true, nullable = false)
    private String name;

    //Alternative titles
    private String alternativeNames;

    //Release year
    private int releaseYear;

    //Status of ranobe
    private StatusEnum status;

    //Country of ranobe
    private CountryEnum country;

    //Genres list
    @Column
    @ElementCollection(targetClass=Genre.class)
    @Enumerated(EnumType.STRING)
    @Fetch(value = FetchMode.SELECT)
    private List<Genre> genresList;

    //Volume in the original
    private int volumeOrig;

    //Volume on the site
    private int volume;

    //description
    private String description;

    private String posterFileUrl;

    //List of volumes for this ranobe
    @OneToMany(targetEntity=RanobeVolume.class, fetch = FetchType.LAZY, mappedBy = "ranobePage", cascade = CascadeType.REMOVE)
    private List<RanobeVolume> volumes;

    public RanobePage() {

    }

    public RanobePage(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RanobePage(Long id, String name, String posterFileUrl) {
        this.id = id;
        this.name = name;
        this.posterFileUrl = posterFileUrl;
    }

    public RanobePage(Long id, String name, String alternativeNames, int releaseYear, StatusEnum status, CountryEnum country, List<Genre> genresList, int volumeOrig, int volume, String description, String posterFileUrl, Long v_id, String v_name, int v_volumeNum) {
        this.id = id;
        this.name = name;
        this.alternativeNames = alternativeNames;
        this.releaseYear = releaseYear;
        this.status = status;
        this.country = country;
        this.genresList = genresList;
        this.volumeOrig = volumeOrig;
        this.volume = volume;
        this.description = description;
        this.posterFileUrl = posterFileUrl;
    }

    public RanobePage(String name, String alternativeNames, int releaseYear, StatusEnum status, CountryEnum country, List<Genre> genresList, int volumeOrig, int volume, String description, String posterFileUrl, List<RanobeVolume> volumes) {
        this.name = name;
        this.alternativeNames = alternativeNames;
        this.releaseYear = releaseYear;
        this.status = status;
        this.country = country;
        this.genresList = genresList;
        this.volumeOrig = volumeOrig;
        this.volume = volume;
        this.description = description;
        this.posterFileUrl = posterFileUrl;
        this.volumes = volumes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public List<Genre> getGenresList() {
        return genresList;
    }

    public void setGenresList(List<Genre> genresList) {
        this.genresList = genresList;
    }

    public int getVolumeOrig() {
        return volumeOrig;
    }

    public void setVolumeOrig(int volumeOrig) {
        this.volumeOrig = volumeOrig;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public List<RanobeVolume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<RanobeVolume> volumes) {
        this.volumes = volumes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterFileUrl() {
        return posterFileUrl;
    }

    public void setPosterFileUrl(String posterFileUrl) {
        this.posterFileUrl = posterFileUrl;
    }
}
