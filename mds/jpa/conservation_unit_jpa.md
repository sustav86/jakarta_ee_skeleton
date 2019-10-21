#Единица сохраняемости
Какой JDBC-драйвер вам следует использовать? Как вам нужно подключаться
к базе данных? Каково имя базы данных? Эта информация отсутствует в приводившемся ранее коде. 

Когда класс Main (см. листинг 4.4) создает 
* EntityManagerFactory

он передает имя единицы сохраняемости в качестве параметра; в данном случае
она имеет имя chapter04PU. Единица сохраняемости позволяет EntityManager узнать
тип базы данных для использования и параметры подключения, определенные
в файле persistence.xml, который показан в листинге 4.5 и должен быть доступен
по пути к соответствующему классу.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd"
             version="2.1">
    <persistence-unit name="chapter04PU" transaction-type="RESOURCE_LOCAL">
        <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
        <class>org.agoncal.book.javaee7.chapter04.Book</class>
        <properties>
            <property name="javax.persistence.schema-generation-action"
                      value="drop-and-create"/>
            <property name="javax.persistence.schema-generation-target"
                      value="database"/>
            <property name="javax.persistence.jdbc.driver"
                      value="org.apache.derby.jdbc.ClientDriver"/>
            <property name="javax.persistence.jdbc.url"
                      value="jdbc:derby://localhost:1527/chapter04DB;create=true"/>
            <property name="javax.persistence.jdbc.user" value="APP"/>
            <property name="javax.persistence.jdbc.password" value="APP"/>
        </properties>
    </persistence-unit>
</persistence>
```

Единица сохраняемости chapter04PU определяет JDBC-подключение для базы
данных Derby chapter04DB, которая функционирует на локальном хосте с помощью
порта 1527. К ней подключается пользователь (APP) с применением пароля (APP) по
заданному URL-адресу. Тег <class> дает указание поставщику постоянства управлять
классом Book (существуют и другие теги для неявного или явного обозначения клас-
сов с управляемым постоянством, например <mapping-file>, <jar-file> или 
<exclude-unlisted-classes>). Без единицы сохраняемости сущностями можно манипулировать
как POJO, однако их постоянство при этом обеспечиваться не будет.