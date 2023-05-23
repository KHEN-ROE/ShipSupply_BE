package com.shipsupply.service;

import com.shipsupply.domain.Item;
import com.shipsupply.persistence.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public String getCategory(Map<String, String> data) {
        String flaskUrl = "http://localhost:5000/api/item/predict/classify";

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP Headers 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> map= new HashMap<>();
        map.put("a", data.get("Machinery"));
        map.put("b", data.get("Assembly"));
        map.put("c", data.get("PartNo1"));
        map.put("d", data.get("Item"));

        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, request, String.class);

        return response.getBody();
    }
}
