package com.example.paragraphmerge;

import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.management.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/texts")
public class ParagraphMergeController {
	private List<Text> texts = new ArrayList<>();
	private List<String> ids = new ArrayList<String>();

	@Autowired
	private MergeRepository mergeRepository;
	private String mergedText;

	private  MergingAlgoritm ma;


/*
	@GetMapping("/{id}")
	public Text getBookById(@PathVariable Long id) {
		return texts.stream()
				.filter(text -> text.getId().equals(id))
				.findFirst()
				.orElse(null);
	}*/

	@PostMapping
	public Text addText(@RequestBody Text text) {
		texts.add(text);
		System.out.println("text ==>>"+ text);
		return text;
	}
	@PostMapping("/saveTexts")
	public ResponseEntity<String> saveTexts(@RequestBody List<Text> textList) {
		// Here you can perform any operations you need to save the texts based on the provided IDs
		// For example, you could save the texts with the corresponding IDs to a database or external system

		// Then you can return an appropriate HTTP status code based on the results
		if (textList.size() < 1)
			return new ResponseEntity<>("Text List is Empty!" , HttpStatus.NOT_ACCEPTABLE);
		for ( Text txt : textList) {
			if( (txt.getId().equals(null) || txt.getId().equals("")) ||
					(txt.getTitle().equals(null) || txt.getTitle().equals("")) ||
					(txt.getContent().equals(null) || txt.getContent().equals(""))){
				return new ResponseEntity<>("Some contents are Empty!" , HttpStatus.NOT_ACCEPTABLE);
			}
		}
		ids.clear();
		for (var txt : textList){
			ids.add(txt.getId());
		}


		mergeRepository.insert(textList);

		return new ResponseEntity<>("Hello World!", HttpStatus.OK);
	}
	@GetMapping("/lastSavedIds")
	public List<String> getlastSavedIds(){
		return ids;
	}
	@PostMapping("/mergeTexts")
	public List<String> mergeTexts(List<String> idList){
		return idList;
	}
	@GetMapping("/getMergedText")
	public String getMergedText(List<String> idList){
		return mergedText;
	}

}
