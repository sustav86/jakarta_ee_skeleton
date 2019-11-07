#@Temporal
При использовании JPA, как только класс окажется снабжен аннотацией @Entity,
все его атрибуты будут автоматически отображены в таблице. Если вам не нужно,
чтобы атрибут отображался, вы можете воспользоваться аннотацией 
* @javax.persistence.Transient 

или ключевым Java-словом transient. К примеру, возьмем упо-
минавшуюся выше сущность Customer и добавим атрибут age (листинг 5.17). По-
скольку возраст может быть автоматически вычислен исходя из даты рождения,
атрибут age не нужно отображать и, следовательно, он может быть временным.
```xml
@Entity
public class Customer {
    @Id @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Transient
    private Integer age;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
// Конструкторы, геттеры, сеттеры
}
```
В результате атрибут age не нужно отображать в каком-либо столбце AGE.