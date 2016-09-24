package br.senai.informatica.todolist.controller;

import java.net.URI;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



import br.senai.informatica.todolist.dao.ItemDAO;
import br.senai.informatica.todolist.modelo.ItemLista;

@RestController
public class ItemRestController {
	
	@Autowired
	private ItemDAO itemDAO ;
	
	
	@RequestMapping(value="/item/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> marcarFeito(@PathVariable("id") long idItem, @RequestBody String strFeito){
		try {
			JSONObject job = new JSONObject(strFeito);
			itemDAO.marcarFeito(idItem, job.getBoolean("feito"));
			HttpHeaders responseHeader = new HttpHeaders();
			URI location = new URI("/item/"+idItem);
			responseHeader.setLocation(location);
			
			return new ResponseEntity<Void>(responseHeader, HttpStatus.OK);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/lista/{idLista}/item", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ItemLista> addItem(@PathVariable long idLista, @RequestBody ItemLista item){
		
		try {
		itemDAO.inserir(idLista, item);		
		URI location = new URI("/item/"+item.getId());
		return ResponseEntity.created(location).body(item);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}