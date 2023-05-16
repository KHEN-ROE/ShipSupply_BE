package com.shipsupply;

import com.shipsupply.domain.Item;
import com.shipsupply.persistence.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShipsupplyApplicationTests {

    @Autowired
    ItemRepository ir;

    @Test
    void contextLoads() {
    }

//    @Test
//    public void addItem() {
//        Item i = new Item();
//        i.setItem("dd");
//        i.setAssembly("dd");
//        i.setMachinery("dd");
//        i.setCompany("dd");
//        i.setPartNo1("dd");
//        i.setLeadtime(10L);
//        ir.save(i);
//    }

}
