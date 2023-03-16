package com.example.paragraphmerge;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class Text {
	private Long id;
	private String title;
	private String content;

	public Text() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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


	// getters and setters
}
