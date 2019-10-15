#Дескрипторы развертывания
* Как и большинство других технологий, входящих в состав Java EE 7, при валидации
компонентов мы можем определять метаданные с помощью аннотаций (как это
делалось выше) и с помощью XML. В Bean Validation у нас может быть несколько
опциональных файлов, которые располагаются в каталоге META-INF. Первый файл,
validation.xml, может применяться приложениями для уточнения некоторых осо-
бенностей поведения валидации компонентов (в частности, поведения действу-
ющего по умолчанию поставщика валидации компонентов, интерполятора сообще-
ний, а также конкретных свойств). Кроме того, у вас может быть несколько файлов,
описывающих объявления ограничений для ваших компонентов. Как и все дескрип-
торы развертывания в Java EE 7, XML переопределяет аннотации.
* В листинге 3.19 показан дескриптор развертывания validation.xml, имеющий кор-
невой XML-элемент validation-config. Гораздо важнее, что здесь определяется внешний
файл для отображения ограничений: constraints.xml (показан в листинге 3.20).
```xml
<?xml version="1.0" encoding="UTF-8"?>
<validation-config
        xmlns="http://jboss.org/xml/ns/javax/validation/configuration"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://jboss.org/xml/ns/javax/validation/configuration
        validation-configuration-1.1.xsd"
        version="1.1">
<constraint-mapping>META-INF/constraints.xml</constraint-mapping>
</validation-config>
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<constraint-mappings
        xmlns="http://jboss.org/xml/ns/javax/validation/mapping"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://jboss.org/xml/ns/javax/validation/mapping
        validation-mapping-1.1.xsd"
        version="1.1">
<bean class="org.agoncal.book.javaee7.chapter03.Book" ignore-annotations="false">
<field name="title">
<constraint annotation="javax.validation.constraints.NotNull">
<message>Title should not be null</message>
</constraint>
</field>
<field name="price">
<constraint annotation="javax.validation.constraints.NotNull"/>
<constraint annotation="javax.validation.constraints.Min">
<element name="value">2</element>
</constraint>
</field>
<field name="description">
<constraint annotation="javax.validation.constraints.Size">
<element name="max">2000</element>
</constraint>
</field>
</bean>
</constraint-mappings>
```
Файл constraints.xml из листинга 3.20 определяет метаданные для объявления
ограничений, используемых с классом Book. Сначала он применяет ограничение
@NotNull к атрибуту title и заново задает выводимое по умолчанию сообщение об
ошибке («Название не может быть пустым»). К атрибуту price применяются два
разных ограничения, его минимальное значение равно 2. Ситуация напоминает
код из листинга 3.1, где метаданные определялись с помощью аннотаций.
