package com.example.demo.enumDemo;
import lombok.Getter;  // 引入lombok,是一个简化代码的工具
import lombok.Setter;

/**
 * @description: 六国的枚举
 * @author: leitao
 * @create: 2020-01-03 10:30
 */
@Getter
public enum Country {
    // 枚举的具体数据
    ONE(1,"韩"),
    TWO(2,"赵"),
    THREE(3,"魏"),
    FORE(4,"楚"),
    FIVE(5,"燕"),
    SIX(6,"齐");

    // 设置两个属性，分别对应枚举中的两个属性： 序号和名字, 同时可以使用@getter自动生成get方法
    private int countryCount;
    private String countryName;


    // 定义构造函数，相当于定义枚举的属性有哪些值
    Country(int countryCount, String countryName) {
        this.countryCount = countryCount;
        this.countryName = countryName;
    }

    // 获取枚举的值
    public static Country find_counntry(int index)  {
        Country[] countryArr = Country.values();
        for (Country eachCountry : countryArr) {
            if (index == eachCountry.getCountryCount()) {
                return eachCountry;
            }
        }
        return null;
    }
}
