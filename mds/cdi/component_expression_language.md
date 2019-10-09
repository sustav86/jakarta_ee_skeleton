# Компоненты в языке выражений
@javax.inject.Named
* Одна из ключевых возможностей CDI состоит в том, что он связывает уровень
транзакций и веб-уровень. Но, как вы уже могли видеть, одна из первоочередных
характеристик CDI заключается в том, что внедрение зависимостей (DI) полностью
типобезопасно и не зависит от символьных имен. В Java-коде это не вызывает про-
блем, поскольку компоненты не будут разрешимы без символьных имен за преде-
лами Java. В частности, это касается EL-выражений на страницах JSF.

* По умолчанию компонентам CDI не присваивается имя и они неразрешимы
с помощью EL-связывания. Чтобы можно было присвоить компоненту имя, он
должен быть аннотирован встроенным квалификатором @javax.inject.Named, как
показано в листинге 2.22.
```xml
@Named
public class BookService {
    private String title, description;
    private Float price;
    private Book book;
    @Inject
    @ThirteenDigits
    private NumberGenerator numberGenerator;

    public String createBook() {
        book = new Book(title, price, description);
        book.setIsbn(numberGenerator.generateNumber());
        return "customer.xhtml";
    }
}
```
* Квалификатор @Named позволяет получить доступ к компоненту BookService через
его имя (им по умолчанию является имя класса в«верблюжьем регистре» (CamelCase)
с первой строчной буквой).