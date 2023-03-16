package com.example.paragraphmerge;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/texts")
public class ParagraphMergeController {
	private List<Text> texts = new ArrayList<>();

	@GetMapping("/{id}")
	public Text getBookById(@PathVariable Long id) {
		return texts.stream()
				.filter(text -> text.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	@PostMapping
	public Text addText(@RequestBody Text text) {
		texts.add(text);
		System.out.println("text ==>>"+ text);
		return text;
	}



		// getters and setters
}

