package com.example.paragraphmerge;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "merge")
public class MergedText {
    @Id
    private String id;
    private List<String> textsId;
    private String mergedText;

    public List<String> getTexts() {
        return textsId;
    }

    public void setTexts(List<String> texts) {
        this.textsId = texts;
    }

    public String getMergedText() {
        return mergedText;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return  this.id;
    }
    public void setMergedText(String mergedText) {
        this.mergedText = mergedText;
    }
}

