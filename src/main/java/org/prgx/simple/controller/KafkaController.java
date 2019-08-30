package org.prgx.simple.controller;

import org.prgx.simple.objects.Person;
import org.prgx.simple.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */

@RestController
public class KafkaController
{
    @Autowired
    private KafkaService mKafkaService;

    @GetMapping("/send")
    public void method()
    {
        Person person = new Person();
        person.setAge(12);
        person.setName("lala");
        mKafkaService.sendMessageToBroker(person);
    }
}
