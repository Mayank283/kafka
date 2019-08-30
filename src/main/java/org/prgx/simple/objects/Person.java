package org.prgx.simple.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class Person
{
    @JsonProperty
    private String name;

    @JsonProperty
    private int age;

    @JsonProperty
    public String getName()
    {
        return name;
    }

    @JsonProperty
    public void setName(String name)
    {
        this.name = name;
    }

    @JsonProperty
    public int getAge()
    {
        return age;
    }

    @JsonProperty
    public void setAge(int age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}
