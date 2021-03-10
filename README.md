## Springboot @Value demo

```
@Value ${ } 使用占位符
@Value #{ } SpEL 表达式取值
stringNull: null
stringTest: hello world
string-test2: hello world2
booleanTest: true
listTest: [a, b, c, d]
@Value 不支持 yml 换行 中划线 listTest2: null
@Value dateTest: Fri Jan 01 00:00:00 CST 2021
@Value 不支持松散语法 @ConfigurationProperties支持 使用默认值null stringTest2: null
@Value 不支持复杂类型 @ConfigurationProperties支持 使用默认值null yanlpMap: null
@Value 通过 SpEL 支持map map格式必须在一行 并且用""把map所对应的value包起来 eg. a: "{a: "one", b: "two"}" yanlpMap: {name=yanlp, sex=男}, name: yanlp
@Value 通过 SpEL 支持map list user: {yanlp=[java, js], yanlp2=[go, rails]}
```
