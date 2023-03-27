package com.example.paragraphmerge;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "merge")
public class Text {
	@Id
	private String id;
	private String title;
	private String content;

	public Text( String content) {
		this.content = content;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return "Text{" +
				"id='" + id + '\'' +
				", content='" + content + '\'' +
				'}';
	}

}
