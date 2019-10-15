#Ограничение на уровне метода
Ограничения, действующие на уровне методов, появились в спецификации Bean
Validation 1.1. Существуют ограничения, объявляемые для методов, а также для
конструкторов (геттеры не считаются ограниченными методами). Эти ограничения
могут быть добавлены к параметрам метода (это будут «ограничения параметров»)
или к самому методу («ограничения возвращаемых значений»). Таким образом,
спецификация Bean Validation может использоваться для описания и валидации
соглашения, применяемого с заданным методом или конструктором. Так строится
хорошо известный стиль «Программирование по соглашениям»:
   * предусловия должны выполняться вызывающей стороной еще до вызова мето-
да или конструктора;
    * постусловия гарантированно выполняются для вызывающей стороны после
возврата вызова, направленного к методу или конструктору.

В листинге 3.13 показано несколько способов использования ограничений на
уровне метода. Сервис CardValidator проверяет кредитную карту в соответствии
с конкретным валидационным алгоритмом. Для этого конструктор использует огра-
ничение @NotNull с параметром ValidationAlgorithm. Затем два метода validate воз-
вращают Boolean (валидна кредитная карта или нет?) с ограничением @AssertTrue для
возвращаемого типа и ограничениями @NotNull и @Future у параметров методов.
```xml
public class CardValidator {
    private ValidationAlgorithm validationAlgorithm;
    public CardValidator(@NotNull ValidationAlgorithm validationAlgorithm) {
        this.validationAlgorithm = validationAlgorithm;
    }
    @AssertTrue
    public Boolean validate(@NotNull CreditCard creditCard) {
        return validationAlgorithm.validate(creditCard.getNumber(),
                creditCard.getCtrlNumber());
    }
    @AssertTrue
    public Boolean validate(@NotNull String number, @Future Date expiryDate,
                            Integer controlNumber, String type) {
        return validationAlgorithm.validate(number, controlNumber);
    }
}
```
