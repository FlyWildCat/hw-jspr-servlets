#Домашнее задание к занятию «2.1. Servlet Containers»

CRUD
Легенда

В рамках лекции мы реализовали практически полноценный In-Memory CRUD (Create Read Update Delete) сервер на базе сервлетов. Этому серверу не хватает двух вещей:

Причесать код (вынести методы в константы, убрать дублирующийся код)
Реализовать репозиторий (пока вместо репозитория установлена заглушка)
Задача
Осуществите рефакторинг кода
Реализуйте репозиторий с учётом того, что методы репозитория могут вызываться конкурентно (т.е. в разных потоках)
Как должен работать save:

Если от клиента приходит пост с id = 0, значит это создание нового поста - вы сохраняете его в списке и присваиваете ему новый id (достаточно хранить счётчик с целым числом и увеличивать на 1 при создании каждого нового поста)
Если от клиента приходит пост с id != 0, значит это сохранение (обновление) существующего поста - вы ищете его в списке по id и обновляете (продумайте самостоятельно, что вы будете делать, если поста с таким id не оказалось: здесь могут быть разные стратегии)
