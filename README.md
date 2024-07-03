
# Кратчайшие пути в графе. Алгоритм Дейкстры.

Репозиторий мини-проекта бригады №6 (летняя практика 2024):

1. Долотов Никита, гр. 2381;
2. Богатов Илья, гр. 2381;
3. Бочаров Глеб, гр. 2381.

## Спецификация

### Постановка задачи

Разработать приложение на Java с графическим интерфейсом, которое визуализирует работу алгоритма Дейкстры для нахождения кратчайших путей в взвешенном графе с неотрицательными весами рёбер. Приложение должно позволять пользователю строить ориентированный/неориентированный граф, сохранять/загружать граф из файла, запускать алгоритм Дейкстры с заданной начальной вершины и отслеживать (визуально, а также в текстовом виде) шаги его работы.

### Описание возможностей программы

Построение графа:
* Возможность строить ориентированный/неориентированный граф;
* Добавление вершин графа;
* Добавление рёбер между вершинами с указанием их веса;
* Удаление вершин и рёбер по отдельности;
* Полное удаление графа;
* Сохранение и загрузка графа.

Представление графа в файле:
* Формат JSON для хранения графа в файле;
* Вершины представляются объектами с атрибутами *id*, *label*, *x*, *y*, *color*;
* Рёбра представляются объектами с атрибутами *fromV*, *toV*, *weight*, *color*.

Визуализация:
* Выбор начальной вершины для алгоритма Дейкстры;
* Запуск выполнения алгоритма целиком или по шагам с возможностью перемещения между шагами;
* Отображение текущего состояния: текущая обрабатываемая вершина должна быть выделена цветом;
* Релаксация рёбер: ребро, по которому производится релаксация, должно быть выделено цветом;
* Обновление расстояний: минимальные расстояния отображены рядом с каждой вершиной и при их обновлении они выделяются цветом;
* Выводимые текстовые пояснения: номер текущего шага, метка обрабатываемой вершины, рёбра, проверяемые на возможность релаксации, рёбра, по которым производится релаксация, расстояния до вершин, обновлённые после релаксации.

### Псевдокод

```
// V - множество вершин графа
// E - множество рёбер графа
// start - начальная вершина
// dist[] - массив минимальных расстояний от начальной вершины
// used[] - массив обработанных вершин
// w(i) - вес i-го ребра
// e.to - вершина, в которую ведёт ребро e от текущей вершины v

function dijkstra(start):
    for v ∈ V:
        dist[v] = ∞
        used[v] = false
    dist[start] = 0
    for i ∈ V:
        v = null
        for j ∈ V:
            if !used[j] and (v == null or dist[j] < dist[v]):
                v = j
        if dist[v] = ∞:
            break
        used[v] = true
        for e ∈ E (e - edge coming from vertex v):
            if dist[v] + w(e) < dist[e.to]:
                dist[e.to] = dist[v] + w(e)
```

### Интерфейс

![img](readme_resources/interface.png)

1. Edit Mode:
* Добавление вершины - двойной клик ЛКМ;
* Перетаскивание вершины - зажать ЛКМ на вершине и перемещать мышь;
* Добавление ребра - одинарный клик ЛКМ по первой вершине (обводка выделенной вершины станет красной), затем одинарный клик ЛКМ по второй вершине;
* Задать вес ребру - одинарный клик ПКМ по ребру и ввести неотрицательный вес.

2. Delete Mode:
* Удаление вершины - одинарный клик ЛКМ по вершине;
* Удаление ребра - одинарный клик ЛКМ по ребру.

3. Clear the Field:
* Удалить весь построенный граф.

4. Switch Graph Type:
* Очистить поле графа и сменить тип графа на противоположный (ориентированный/неориентированный).

5. Import Graph:
* Загрузить граф из файла.

6. Export Graph:
* Сохранить граф в файл.

7. Run:
* Запустить алгоритм Дейкстры с заданной начальной вершины (начальная вершина задаётся одинарным кликом ЛКМ по вершине).

8. Step Back:
* Возврат к предыдущему шагу работы алгоритма.

8. Step Forward:
* Переход к следующему шагу работы алгоритма.

9. Graph Field:
* Область для построения и визуализации графа.

10. Steps Field:
* Область для отображения выполненных шагов алгоритма в текстовом виде.

## План разработки

1. 28.06.24 - Защита вводного задания, демонстрация, согласование спецификации и плана разработки.
2. 01.07.24 - Сдача прототипа программы, демонстрирующего графический интерфейс.
3. 03.07.24 - Сдача программы версии 1 (возможность строить граф, запустить алгоритм и получить результат без пошагового исполнения с выводом текстовых пояснений работы; тестирование версии 1).
4. 05.07.24 - Сдача программы версии 2 (визуализация пошаговой работы алгоритма Дейкстры, сохранение/загрузка графа из файла, внесение правок по итогам защиты альфа-версии; отладка и тестирование).
5. 08.07.24 - Сдача финальной версии программы (реализация тестов; внесение всех правок) и отчёта о проделанной работе.

## Распределение ролей

* Долотов Никита - API графа, реализация алгоритма, интерфейс.
* Богатов Илья - Визуализация алгоритма, тестирование на всех итерациях, оформление отчёта.
* Бочаров Глеб - выбор задания.
