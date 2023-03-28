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

	@PostMapping
	public Text addText(@RequestBody Text text) {
		texts.add(text);
		System.out.println("text ==>>"+ text);
		return text;
	}
	@PostMapping("/saveTexts")
	public ResponseEntity<String> saveTexts(@RequestBody List<String> textList) {
		if (textList.size() < 1)
			return new ResponseEntity<>("Text List is Empty!" , HttpStatus.NOT_ACCEPTABLE);

		List<Text> textSList = new ArrayList<Text>();
		for (var txt: textList)
			textSList.add(new Text(txt));

		ids.clear();
		var savedList = mergeRepository.insert(textSList);
		for (var txt: savedList) {
			ids.add(txt.getId());
			System.out.println(txt.toString());
			System.out.println(" save texts id "+ txt.getId());
		}
		return new ResponseEntity<>("Hello World!", HttpStatus.OK);
	}
	@GetMapping("/lastSavedIds")
	public List<String> getlastSavedIds(){
		for ( String id : ids) {
			System.out.println(" last saved id ==>> "+ id);
		}
		return ids;
	}
	@PostMapping("/mergeTexts")
	public String mergeTexts()  {
		System.out.println( "merged  ids : "+ ids);
		List<Text> fromDBTexts = mergeRepository.findAllById(ids);
		List<String> willBeMergedList = new ArrayList<String>();
		for (Text txt : fromDBTexts)
			willBeMergedList.add(txt.getContent());
		long startTime = System.currentTimeMillis();
		setMergedText(merge(willBeMergedList));
		long endTime = System.currentTimeMillis();
		elapsedTime = endTime - startTime;
		System.out.println("Elapsed time " +elapsedTime);
		System.out.println("MErged Text " + mergedText);

		mergedTextDTO = new MergedText();
		mergedTextDTO.setTexts(ids);
		mergedTextDTO.setMergedText(mergedText);
		mergedRepository.insert(mergedTextDTO);
		return mergedText;
	}
	@GetMapping("/getMergedText")
	public String getMergedText(){
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
