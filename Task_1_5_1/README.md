## Объектная модель:

Базовый Builder Паттерн, от которого мы будем отталкиваться:
```mermaid
classDiagram
direction TB

    %% Base abstract class
    class Element {
        <<abstract>>
        +serialize() String
        +equals(Object) boolean
    }

    %% Interface
    class MarkdownBuilder {
        <<interface>>
        +reset() void
        +getResult() String
    }

    %% Block elements package
    namespace Block {
        class Table {
            +serialize() String
            +equals(Object) boolean
        }
        
        class TableBuilder {
            <<static>>
            +reset() void
            +getResult() String
        }
    }

    %% Inline elements package
    namespace Inline {
        class Text {
            +serialize() String
            +equals(Object) boolean
        }
        
        class TextBuilder {
            <<static>>
            +reset() void
            +getResult() String
        }
    }

    %% Relationships
    Element <|-- Table
    Element <|-- Text
    MarkdownBuilder <|.. TableBuilder
    MarkdownBuilder <|.. TextBuilder
    Table *-- TableBuilder
    Text *-- TextBuilder
```

В дополнение к данной модели реализуем Director класс, который:
1. Инкапсулирует сложную логику построения элементов markdown
2. Предоставляет готовые методы для создания типовых конструкций (простые таблицы, форматированный текст)
3. Использует builders через их методы для пошаговой сборки элементов
4. Позволяет переиспользовать одинаковые последовательности построения в разных местах программы