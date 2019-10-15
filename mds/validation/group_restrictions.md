#Группы
Когда компонент валидируется, в ходе проверки одновременно проверяются все
его ограничения. Но что, если вам требуется частично валидировать компонент
(проверить часть его ограничений) или управлять порядком валидации конкретных
ограничений? Здесь нам пригодятся группы. Группы позволяют сформировать
набор тех ограничений, которые будут проверяться в ходе валидации.
На уровне кода группа представляет собой обычный пустой интерфейс.
```xml
public interface Payment {}
```
На уровне бизнес-логики группа имеет определенное значение. Например, ра-
бочая последовательность Payment (Платеж) подсказывает, что атрибуты, относя-
щиеся к этой группе, будут валидироваться на этапе оплаты в рамках заказа това-
ра. Чтобы применить эту группу с набором ограничений, нужно использовать
атрибут groups и передать ему этот интерфейс:
```xml
@Past(groups = {Payment.class, Delivery.class})
private Date deliveryDate;
```
В каждой ограничивающей аннотации должен определяться элемент groups.
Если ни одна группа не указана, то считается объявленной задаваемая по умолча-
нию группа javax.validation.groups.Default. Так, следующие ограничения являют-
ся эквивалентными и входят в состав группы Default:
```xml
@NotNull
private Long id;
@Past(groups = Default.class)
private Date creationDate;
```
Вновь рассмотрим случай с предшествующим использованием, в котором мы
применяли аннотацию @ChronologicalDates, и задействуем в нем группы. В клас-
се Order из листинга 3.18 содержится несколько дат, позволяющих отслеживать
ход процесса заказа: creationDate, paymentDate и deliveryDate. Когда вы только
создаете заказ на покупку, устанавливается атрибут creationDate, но не paymentDate
и deliveryDate. Две эти даты нам потребуется валидировать позже, на другом
этапе рабочего процесса, но не одновременно с creationDate. Применяя группы,
можно валидировать creationDate одновременно с группой, проверяемой по
умолчанию (поскольку для этой аннотации не указана никакая группа, для нее
по умолчанию действует javax.validation.groups.Default). Атрибут paymentDate
будет валидироваться на этапе Payment, а deliveryDate и @ChronologicalDates — на
этапе Delivery.
```xml
@ChronologicalDates(groups = Delivery.class)
public class Order {
    @NotNull
    private Long id;
    @NotNull @Past
    private Date creationDate;
    private Double totalAmount;
    @NotNull(groups = Payment.class) @Past(groups = Payment.class)
    private Date paymentDate;
    @NotNull(groups = Delivery.class) @Past(groups = Delivery.class)
    private Date deliveryDate;
    private List<OrderLine> orderLines;
// Конструкторы, геттеры, сеттеры
}
```
Как видите, в ходе валидации вам всего лишь следует явно указать, какие имен-
но группы вы хотите проверить, и поставщик валидации из Bean Validation вы-
полнит частичную проверку.