# PROJEKT SERWIS AUKCYJNY

Prosty serwis aukcyjny wykonany w architekturze 3-warstwowej. Strona ma umożliwiać rejestrowanie się, wystawianie przedmiotów na aukcje oraz licytowanie ich. 

## Wykorzystane technologie
- Warstwa webowa: PHP / Ajax
- Warstwa biznesowa: Java
- Warstwa bazy danych: MySQL

## Do zrobienia
- Warstwa webowa
  - [ ] GUI
  - [x] logowanie / rejestracja
- Warstwa biznesowa (Rest API)
  - [x] postawienie serwera
  - [x] polacznenie z baza
  - [x] pobieranie informacji z bazy (poprawic nadpisywanie)
  - [x] ogarniecie metody POST
  - [x] ogarniecie metody PUT
  - [x] zapisywanie informacji do bazy
- Warstwa bazy danych
  - [x] Stworzenie bazy danych

## Funkcjonalnosć
- [x] logowanie
- [x] rejestracja
- [ ] edycja danych osobowych
- [ ] dodawanie ogloszenia
- [ ] edycja ogloszenia
- [ ] licytacja
- [ ] kup teraz
- [ ] dodawanie komentarzy
- [x] wyswietlanie profilu uzytkownika
- [ ] usuwanie komentarzy (admin)
- [ ] usuwanie ofert
- [ ] listowanie ofert
- [ ] wyswietlanie ofert

## Zapytania do API
### /Users
- localhost:8080/users
- POST, PUT/{id}:
```json
{
  "login": "string",
  "hashPassword": "string",
  "firstName": "string",
  "lastName": "string",
  "email": "string@xd.pl",
  "city": "miasto",
  "address": "ulica 12",
  "phone": "123123123",
  "zipCode": "12-345"
}
```
### /Offers
- localhost:8080/offers
- GET localhost:8080/offers?owner_id=1&buyer_id=1&category=gry - id wlasciciela, id kupujacego, kategoria
- POST, PUT/{id}:
```json
{
      "title": "string",
      "description": "string",
      "picture_path": "string",
      "owner_id": "string",
      "prices": {
        "best_price": 0,
        "minimal_price": 0,
        "buy_now_price": 0,
        "currency": "PLN"
      },
      "buyer_id": "opcjonalne, do kupna dopiero (bardziej potrzebne w put)",
      "weight": 0,
      "size": "12x34x56",
      "shipment": "string",
      "category": "string"
    }
```
### /Comments
- localhost:8080/comments
- GET localhost:8080/comments?giver_id=1&receiver_id=3&offer_id=1 - odpowiednio id dajacego komentarz, odbiorcy komentarza, oferty
- POST, PUT/{id}:
```json
{
      "giverId": "string",
      "recieverId": "string",
      "offerId": "string",
      "commentText": "string",
      "positive": false,
    }
```
### /Bids
- localhost:8080/bids
- GET localhost:8080/bids?bidderId=1 - pobiera wszystkie bidy użytkownika
- GET localhost:8080/bids?offerId=1 - pobiera wszystkie bidy oferty
- GET http://localhost:8080/bids?bidderId=1&offerId=1 - jednoczenie wszystkie bidy uzytkownika do danej oferty
- POST, PUT/{id}
```json
{
  "offerId": "string",
  "bidderId": "string",
  "price": 0
}
```
