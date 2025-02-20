#Стратегия «таблица на конкретный класс»

Если задействуется стратегия «таблица на класс» (или «таблица на конкретный
класс»), то каждая сущность отображается в свою специально отведенную для
этого таблицу, как при использовании стратегии «соединенный подкласс». От-
личие состоит в том, что все атрибуты корневой сущности также будут отобра-
жены в столбцы таблицы дочерней сущности. С позиции базы данных эта стра-
тегия денормализует модель и приводит к тому, что все атрибуты корневой
сущности переопределяются в таблицах всех листовых сущностей, которые на-
следуют от нее. При стратегии «таблица на конкретный класс» нет совместно
используемой таблицы, совместно используемых столбцов и столбца дискрими-
натора. Единственное требование состоит в том, что все таблицы должны со-
вместно пользоваться общим первичным ключом — одинаковым для всех таблиц
в иерархии.

Для отображения нашего примера с применением этой стратегии потребуется
указать TABLE_PER_CLASS в аннотации @Inheritance (листинг 5.61) корневой сущности
(Item).
```xml
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item {
    @Id @GeneratedValue
    protected Long id;
    protected String title;
    protected Float price;
    protected String description;
// Конструкторы, геттеры, сеттеры
}
```
На рис. 5.26 показаны таблицы ITEM, BOOK и CD. Как вы можете видеть, в BOOK и CD
имеются дубликаты столбцов ID, TITLE, PRICE и DESCRIPTION таблицы ITEM. Обратите
внимание, что показанные таблицы не связаны.

Разумеется, помните, что каждая таблица может быть переопределена, если
снабдить каждую сущность аннотацией @Table.