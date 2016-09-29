Sovelluksen aihe on muistilista, jonne kirjautuneet käyttäjät voivat lisätä, muokata ja poistaa muistettavia tehtäviä. Luodut tehtävät näkyvät sivulla järjestettynä siten, että tärkein tehtävä on ensimmäisenä. Tehtävillä voi olla useita tageja joiden mukaan niitä voi luokitella. Näiden tagien avulla voi katsella tietyn kategorian tehtäviä selaamalla tehtäviä tagin avulla.

Käyttäjä joka ei ole kirjautunut pystyy:
  - Kirjautumaan sisään
  - Luomaan tunnuksen
  - Selaamaan etusivulla näytettäviä kaikkia tehtäviä
  - Valitsemaan tagin ja suodattamaan niiden avulla näytettyjä tehtäviä

Kirjautunut käyttäjä pystyy edellisten lisäksi myös:
  - Lisäämään uuden tehtävän
  - Muokata luodun tehtävän nimeä, kuvausta ja prioriteettiä
  - Lisätä tehtävälle tagi
  - Poistaa jokin tehtävän tageista
  - Luoda kokonaan uusi tagi
  - Poistaa tagi sovelluksesta
  - Kirjautua ulos
  
Sovelluksen adminkäyttäjä pystyy aiempien ohella myös
  - Tarkastelemaan sovellukseen luotuja tunnuksia
  - Poistamaan luotuja tunnuksia

Continuous deployment on sovellukseen konfiguroitu Herokun dashboardin tarjoaman CD toiminnallisuuden avulla. Kun master branchiin lisätään jotain uutta, Heroku odottaa että Travis buildi suoritetaan onnistuneesti, jonka jälkeen uusin versio siirtyy suoraan Herokuun.

Adminkäyttäjän nimi ja salasana ovat admin.
