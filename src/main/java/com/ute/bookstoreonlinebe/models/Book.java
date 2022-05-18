package com.ute.bookstoreonlinebe.models;

import com.ute.bookstoreonlinebe.models.embadded.EmbeddedDescription;
import com.ute.bookstoreonlinebe.models.embadded.EmbeddedCategory;
import com.ute.bookstoreonlinebe.models.embadded.EmbeddedPublishers;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "books")
@TypeAlias("Product")
public class Book {
    @Id
    private String id;

    private String name;

    private String author;

    private List<EmbeddedPublishers> publishers = new ArrayList<>();

    private List<EmbeddedDescription> description = new ArrayList<>();;

    private float price;

    private List<String> image_URLs = new ArrayList<>();

    private long quantity;

    private Set<EmbeddedCategory> fallIntoCategories = new HashSet<>();

    private boolean enable = true;
}
