package com.ute.bookstoreonlinebe.dtos.book;

import com.ute.bookstoreonlinebe.models.embadded.EmbeddedCategory;
import com.ute.bookstoreonlinebe.models.embadded.EmbeddedDescription;
import com.ute.bookstoreonlinebe.models.embadded.EmbeddedPublishers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDto {
    private String name;

    private String author;

    private List<EmbeddedPublishers> publishers = new ArrayList<>();

    private List<EmbeddedDescription> description;

    private float price;

    private List<String> image_URLs = new ArrayList<>();

    private long quantity;

    private Set<EmbeddedCategory> fallIntoCategories = new HashSet<>();
}