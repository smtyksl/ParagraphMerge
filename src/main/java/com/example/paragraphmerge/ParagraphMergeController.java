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
import java.util.UUID;

@RestController

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/merge")
public class ParagraphMergeController {
	private List<Text> texts = new ArrayList<>();
	private List<String> ids = new ArrayList<String>();
	private long elapsedTime;
	@Autowired
	private MergeRepository mergeRepository;

	@Autowired
	private MergedRepository mergedRepository;
	private String mergedText;

	private MergedText mergedTextDTO;

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
		for (var text: textList) {
			System.out.println(text.getId());
			System.out.println(text.getContent());
			System.out.println(text.getTitle());
		}
		mergeRepository.insert(textList );

		return new ResponseEntity<>("Hello World!", HttpStatus.OK);
	}
	@GetMapping("/lastSavedIds")
	public List<String> getlastSavedIds(){
		for ( String id : ids) {
			System.out.println("id ==>> "+ id);
		}
		return ids;
	}
	@PostMapping("/mergeTexts")
	public String mergeTexts(@RequestBody List<String> idList) throws InterruptedException {
		List<Text> fromDBTexts = mergeRepository.findAllById(idList);
		List<String> willBeMergedList = new ArrayList<String>();
		for (Text txt : fromDBTexts)
			willBeMergedList.add(txt.getContent());
		long startTime = System.currentTimeMillis();
		setMergedText(merge(willBeMergedList));
		long endTime = System.currentTimeMillis();
		elapsedTime = endTime -startTime;
		System.out.println("erroorrr"+elapsedTime);
		mergedTextDTO = new MergedText();
		mergedTextDTO.setId( UUID.randomUUID().toString());
		mergedTextDTO.setTexts(idList);
		mergedTextDTO.setMergedText(mergedText);
		mergedRepository.insert(mergedTextDTO);
		return mergedText;
	}
	@GetMapping("/getMergedText")
	public String getMergedText(@RequestBody List<String> idList){
		return mergedText;
	}

	@GetMapping("/getElapsedTime")
	public String getRunningTime()
	{
		return  String.valueOf(elapsedTime);
	}
	public static String merge(List<String> texts) {
		StringBuilder mergedText = new StringBuilder(texts.get(0));
		for (String t: texts
			 ) {
			System.out.println(" *** " + t);
		}
		for (int i = 1; i < texts.size(); i++) {
			String mutualPart = findCommonSuffix(mergedText.toString(), texts.get(i));
			if (mutualPart.isEmpty()) {
				// ortak kısım yok, cümleleri birleştiremeyiz
				return "Couldnt merge texts.";
			}
			mergedText.append(texts.get(i).substring(mutualPart.length()));
		}
		return mergedText.toString();
	}


	private static String findCommonSuffix(String str1, String str2) {
		int minLength = Math.min(str1.length(), str2.length());
		StringBuilder mutualPart = new StringBuilder();
		for (int i = 1; i <= minLength; i++) {
			if (str1.endsWith(str2.substring(0, i))) {
				mutualPart = new StringBuilder(str2.substring(0, i));
			}
		}
		return mutualPart.toString();
	}

	public void setMergedText(String mergedText) {
		this.mergedText = mergedText;
	}
}
